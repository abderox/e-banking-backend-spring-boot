package com.adria.projetbackend.services.Virement;

import com.adria.projetbackend.dtos.NewVirementDto;
import com.adria.projetbackend.exceptions.runTimeExpClasses.*;
import com.adria.projetbackend.models.*;
import com.adria.projetbackend.repositories.VirementRepository;
import com.adria.projetbackend.services.Benificiare.IBenificiareService;
import com.adria.projetbackend.services.Client.IClientServices;
import com.adria.projetbackend.services.Compte.ICompteService;
import com.adria.projetbackend.services.Email.EmailDetails;
import com.adria.projetbackend.services.Email.EmailService;
import com.adria.projetbackend.services.Transaction.ITransactionService;
import com.adria.projetbackend.services.User.IUserService;
import com.adria.projetbackend.utils.UtilsMethods.UtilsMethods;
import com.adria.projetbackend.utils.constants.GlobalSettings;
import com.adria.projetbackend.utils.enums.TypeTransaction;
import com.adria.projetbackend.utils.enums.TypeVirement;
import com.adria.projetbackend.utils.storage.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class VirementService implements IVirementService {

    @Autowired
    VirementRepository virementRepository;

    @Autowired
    ICompteService compteService;

    @Autowired
    ITransactionService transactionService;

    @Autowired
    IBenificiareService benificiareService;

    @Autowired
    private EmailService emailService;

    @Autowired
    IClientServices clientService;

    @Autowired
    IUserService userService;

    @Autowired
    RedisRepository redisRepository;


    @Transactional
    public NewVirementDto effectuerVirement(NewVirementDto newVirementDto, Long idClient, String username) throws ParseException {

        if(!userService.verifyOTP(username,newVirementDto.getOtp()))
        {
            throw new OtpNotValidException("OTP provided is not valid !");
        }
        else {
            redisRepository.deleteOtp(newVirementDto.getOtp());
        }

        Compte myCompte = compteService.consulterCompteByRibAndClientId(newVirementDto.getRibEmetteur( ), idClient);
        if ( !myCompte.isInclusVirement( ) )
            throw new IllegitimateToMakeTransfers("TRANSFER OPERATION IS NOT ALLOWED FOR THIS ACCOUNT");

        if ( newVirementDto.getMontant( ) < GlobalSettings.MIN_TRANSFER_AMOUNT )
            throw new IllegitimateToMakeTransfers("TRANSFER AMOUNT MUST BE GREATER THAN " + GlobalSettings.MIN_TRANSFER_AMOUNT);
        if ( newVirementDto.getMontant( ) > GlobalSettings.MAX_TRANSFER_AMOUNT )
            throw new IllegitimateToMakeTransfers("TRANSFER AMOUNT MUST BE LESS THAN " + GlobalSettings.MAX_TRANSFER_AMOUNT);

        Benificiaire benificiaire = benificiareService.consulterBenificiaireByRib(newVirementDto.getRibBenificiaire( ), idClient);

        Transaction tx = new Transaction( );

        Compte compte = compteService.consulterCompteByRib(benificiaire.getRib( ));
        final double soldeInitial = myCompte.getSolde( );

        tx.setMontant(newVirementDto.getMontant( ));
        tx.setReferenceTransaction(UtilsMethods.generateRefTransaction(transactionService.getLatestRow( ).toString( )
                , idClient.toString( ), TypeTransaction.VIREMENT));
        tx.setType(TypeTransaction.VIREMENT);
        tx.setCompte(myCompte);
        tx.setDateExecution(new SimpleDateFormat("yyyy-MM-dd").parse(newVirementDto.getDateExecution( )));


        String today = new SimpleDateFormat("MMM-dd-yyyy ").format(new Date( ));
        String date_ = new SimpleDateFormat("MMM-dd-yyyy ").format(tx.getDateExecution( ));
        if ( tx.getDateExecution( ).before(new Date( )) && !date_.equals(today) )
            throw new NotValidDateExp("DATE EXECUTION MUST BE TODAY OR AFTER");
        tx.setExecuted(today.equals(date_));

        transactionService.effectuerTransaction(tx);


        if ( today.equals(date_) ) {

            double mySolde = compteService.updateCompte(myCompte, newVirementDto.getMontant( ), TypeTransaction.RETRAIT);
            double hisSolde = compteService.updateCompte(compte, newVirementDto.getMontant( ), TypeTransaction.DEPOT);
            Client client = myCompte.getClient( );
            String bankName = client.getAgence( ).getBanque( ).getRaisonSociale( );
            String hisBank = compte.getClient( ).getAgence( ).getBanque( ).getRaisonSociale( );


            String status
                    = emailService.sendSimpleMail(new EmailDetails(client.getEmail( ),
                    "Hello again , we are just letting you know , that the operation of transferring is completed successfully  .\n\nFrom your account labelled with : "
                            + myCompte.getIntituleCompte( ) + "\nIdentified with : " + myCompte.getRib( ) + "\nTo : " + benificiaire.getNom( )
                            + benificiaire.getRib( ) + "\n\nAmount : -" + newVirementDto.getMontant( ) + " MAD\n\nDate : "
                            + new SimpleDateFormat("MMM-dd-yyyy ").format(new Date( )) + "\nBalance before the operation :" + soldeInitial +
                            " MAD\nBalance after update : " + mySolde + " MAD"
                            + "\n\nBest regards , \nBeta-"
                            + bankName,
                    "Hello from Beta-" + bankName + " ," +
                            " " + client.getUsername( ) + "!",
                    ""));

            String status_
                    = emailService.sendSimpleMail(new EmailDetails(compte.getClient( ).getEmail( ),
                    "Hello again , we are reaching you out to let you know that You are receiving a transfer . following the transaction referenced by : "
                            + tx.getReferenceTransaction( ) + "\n\nFrom account labelled with : "
                            + myCompte.getIntituleCompte( ) + "\nIdentified with : " + myCompte.getRib( ) + "\nTo : " + "Your account : "
                            + benificiaire.getRib( ) + "\n\nAmount : +" + newVirementDto.getMontant( ) + " MAD\n\nDate : "
                            + new SimpleDateFormat("MMM-dd-yyyy ").format(new Date( )) +
                            " \nBalance after update : " + hisSolde + " MAD"
                            + "\n\nBest regards , \nBeta-"
                            + bankName,
                    "Hello from Beta-" + hisBank + " ," +
                            " " + compte.getClient( ).getUsername( ) + "!",
                    ""));
        }


        Virement virement = new Virement( );

        virement.setBenificiaire(benificiaire);
        virement.setTransaction(tx);
        virement.setType(TypeVirement.UNITAIRE);
        virement.setPeriodic(newVirementDto.isApplyPeriodicity( ));
        virementRepository.save(virement);

        if ( newVirementDto.isApplyPeriodicity( ) ) {

            if ( !benificiaire.isApplyPeriodicity( ) ) {
                benificiareService.majBenificiaire(benificiaire, !benificiaire.getPeriodicity( ).equals("O"));
            }

            if ( !benificiaire.getPeriodicity( ).equals("O") && today.equals(date_) ) {
                virementAvecPeriodicite(tx.getDateExecution( ), tx.getMontant( ), myCompte, benificiaire);
            }
        }


        return newVirementDto;
    }


    @Transactional
    public void modefierVirement(NewVirementDto newVirementDto, Long idClient) throws ParseException {

        Virement virement = virementRepository.findByTransaction_ReferenceTransaction(newVirementDto.getReferenceTransaction( ));
        if ( virement == null )
            throw new TransactionExp("VIREMENT NOT FOUND");
        if ( !virement.getTransaction( ).getCompte( ).getClient( ).getId( ).equals(idClient) )
            throw new TransactionExp("YOU ARE NOT ALLOWED TO MODIFY THIS VIREMENT");
        if ( virement.getTransaction( ).isExecuted( ) )
            throw new TransactionExp("CANNOT MODIFY EXECUTED VIREMENT");
        if ( virement.getTransaction( ).getDateExecution( ).before(new Date( )) )
            throw new TransactionExp("CANNOT MODIFY VIREMENT WITH DATE EXECUTION BEFORE TODAY");
        if ( virement.getTransaction( ).getDateExecution( ).equals(new Date( )) )
            throw new TransactionExp("THIS VIREMENT IS SHCEDULED FOR TODAY , CANNOT MODIFY IT");

        String today = new SimpleDateFormat("MMM-dd-yyyy ").format(new Date( ));
        Date toDate = new SimpleDateFormat("MMM-dd-yyyy ").parse(newVirementDto.getDateExecution( ));
        String date_ = new SimpleDateFormat("MMM-dd-yyyy ").format(toDate);

        if ( toDate.before(new Date( )) && !date_.equals(today) )
            throw new NotValidDateExp("DATE EXECUTION MUST BE TODAY OR AFTER");
        virement.setPeriodic(newVirementDto.isApplyPeriodicity( ));
        virement.getTransaction( ).setDateExecution(toDate);
        if(newVirementDto.getMontant()<GlobalSettings.MIN_TRANSFER_AMOUNT)
            throw new BalanceMustBePositive("AMOUNT MUST BE GREATER THAN "+GlobalSettings.MIN_TRANSFER_AMOUNT+" MAD");
        virement.getTransaction( ).setMontant(newVirementDto.getMontant( ));
        transactionService.effectuerTransaction(virement.getTransaction( ));
        virementRepository.save(virement);

    }


    public void saveVirement(Virement virement) {
        virementRepository.save(virement);
    }

    public void virementAvecPeriodicite(Date currentDate, double montant, Compte myCompte, Benificiaire benificiaire) {
        if ( !benificiaire.getPeriodicity( ).equals("O") && benificiaire.isApplyPeriodicity( ) ) {
            String periodicity = benificiaire.getPeriodicity( );
            Date nextExecutionDate = UtilsMethods.getDateAfterPeriod(currentDate, periodicity);

            Transaction tx_ = new Transaction( );
            tx_.setMontant(montant);
            tx_.setReferenceTransaction(UtilsMethods.generateRefTransaction(transactionService.getLatestRow( ).toString( )
                    , myCompte.getClient( ).getId( ).toString( ), TypeTransaction.VIREMENT));
            tx_.setType(TypeTransaction.VIREMENT);
            tx_.setCompte(myCompte);
            tx_.setExecuted(false);
            tx_.setDateExecution(nextExecutionDate);
            transactionService.effectuerTransaction(tx_);

            Virement virement2 = new Virement( );
            virement2.setBenificiaire(benificiaire);
            virement2.setTransaction(tx_);
            virement2.setType(TypeVirement.UNITAIRE);
            virement2.setPeriodic(true);
            saveVirement(virement2);
        }

    }


}

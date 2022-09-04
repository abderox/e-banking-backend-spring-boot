package com.adria.projetbackend.services.Virement;

import com.adria.projetbackend.dtos.NewVirementDto;
import com.adria.projetbackend.exceptions.runTimeExpClasses.IllegitimateToMakeTransfers;
import com.adria.projetbackend.exceptions.runTimeExpClasses.NoSuchBenificException;
import com.adria.projetbackend.exceptions.runTimeExpClasses.NotValidDateExp;
import com.adria.projetbackend.models.*;
import com.adria.projetbackend.repositories.VirementRepository;
import com.adria.projetbackend.services.Benificiare.IBenificiareService;
import com.adria.projetbackend.services.Client.IClientServices;
import com.adria.projetbackend.services.Compte.ICompteService;
import com.adria.projetbackend.services.Email.EmailDetails;
import com.adria.projetbackend.services.Email.EmailService;
import com.adria.projetbackend.services.Transaction.ITransactionService;
import com.adria.projetbackend.utils.UtilsMethods.UtilsMethods;
import com.adria.projetbackend.utils.enums.TypeTransaction;
import com.adria.projetbackend.utils.enums.TypeVirement;
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


    @Transactional
    public NewVirementDto effectuerVirement(NewVirementDto newVirementDto, Long idClient) throws ParseException {

        Compte myCompte = compteService.consulterCompteByRibAndClientId(newVirementDto.getRibEmetteur(), idClient);
        if(!myCompte.isInclusVirement()) throw new IllegitimateToMakeTransfers("TRANSFER OPERATION IS NOT ALLOWED FOR THIS ACCOUNT");

        Benificiaire benificiaire = benificiareService.consulterBenificiaireByRib(newVirementDto.getRibBenificiaire( ), idClient);

        Transaction tx = new Transaction( );

        Compte compte = compteService.consulterCompteByRib(benificiaire.getRib());
        final double soldeInitial = myCompte.getSolde( );

        tx.setMontant(newVirementDto.getMontant( ));
        tx.setReferenceTransaction(UtilsMethods.generateRefTransaction(transactionService.getLatestRow( ).toString( )
                , idClient.toString( ), TypeTransaction.VIREMENT));
        tx.setType(TypeTransaction.VIREMENT);
        tx.setCompte(myCompte);
        tx.setDateExecution(new SimpleDateFormat("yyyy-MM-dd").parse(newVirementDto.getDateExecution( )));


        String today = new SimpleDateFormat("MMM-dd-yyyy ").format(new Date( ));
        String date_ = new SimpleDateFormat("MMM-dd-yyyy ").format(tx.getDateExecution( ));
        if(tx.getDateExecution().before(new Date()) && !date_.equals(today)) throw new NotValidDateExp("DATE EXECUTION MUST BE TODAY OR AFTER");
        tx.setExecuted(today.equals(date_));

        transactionService.effectuerTransaction(tx);


        if(today.equals(date_)) {

           double mySolde =  compteService.updateCompte(myCompte, newVirementDto.getMontant( ), TypeTransaction.RETRAIT);
            compteService.updateCompte(compte, newVirementDto.getMontant( ), TypeTransaction.DEPOT);
            Client client = myCompte.getClient();
            String bankName = client.getAgence().getBanque().getRaisonSociale();
            String status
                    = emailService.sendSimpleMail(new EmailDetails(client.getEmail( ),
                    "Hello again , we are just letting you know , that the operation of transferring is completed successfully  .\n\nFrom your account labelled with : "
                            + myCompte.getIntituleCompte( ) + "\nIdentified with : " + myCompte.getRib( ) + "\nTo : " + benificiaire.getNom()
                            + benificiaire.getRib() + "\n\nAmount : -" + newVirementDto.getMontant( ) + " MAD\n\nDate : "
                            + new SimpleDateFormat("MMM-dd-yyyy ").format(new Date( ))  + "\nBalance before the operation :"+soldeInitial +
                            " MAD\nBalance after update : " + mySolde +" MAD"
                            +"\n\nBest regards , \nBeta-"
                            + bankName,
                    "Hello from Beta-" + bankName + " ," +
                            " " + client.getUsername( ) + "!",
                    ""));
        }

        Virement virement = new Virement( );

        virement.setBenificiaire(benificiaire);
        virement.setTransaction(tx);
        virement.setType(TypeVirement.UNITAIRE);

        // ? save virement
        virementRepository.save(virement);
        return newVirementDto;
    }


}

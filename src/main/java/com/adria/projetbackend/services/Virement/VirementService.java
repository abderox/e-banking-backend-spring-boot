package com.adria.projetbackend.services.Virement;

import com.adria.projetbackend.dtos.NewVirementDto;
import com.adria.projetbackend.exceptions.runTimeExpClasses.NoSuchBenificException;
import com.adria.projetbackend.exceptions.runTimeExpClasses.NotValidDateExp;
import com.adria.projetbackend.models.Benificiaire;
import com.adria.projetbackend.models.Transaction;
import com.adria.projetbackend.models.Virement;
import com.adria.projetbackend.repositories.VirementRepository;
import com.adria.projetbackend.services.Benificiare.IBenificiareService;
import com.adria.projetbackend.services.Compte.ICompteService;
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


    @Transactional
    public NewVirementDto effectuerVirement(NewVirementDto newVirementDto, Long idClient) throws ParseException {

        Benificiaire benificiaire = benificiareService.consulterBenificiaireByRib(newVirementDto.getRibBenificiaire( ), idClient);

        Transaction tx = new Transaction( );

        tx.setMontant(newVirementDto.getMontant( ));
        tx.setReferenceTransaction(UtilsMethods.generateRefTransaction(transactionService.getLatestRow( ).toString( )
                , idClient.toString( ), TypeTransaction.VIREMENT));
        tx.setType(TypeTransaction.VIREMENT);
        tx.setCompte(compteService.consulterCompteByRib(newVirementDto.getRibBenificiaire()));
        tx.setDateExecution(new SimpleDateFormat("yyyy-MM-dd").parse(newVirementDto.getDateExecution( )));

        // ? start checks if the date is valid
        String today = new SimpleDateFormat("MMM-dd-yyyy ").format(new Date( ));
        String date_ = new SimpleDateFormat("MMM-dd-yyyy ").format(tx.getDateExecution( ));
        if(tx.getDateExecution().before(new Date()) && !date_.equals(today)) throw new NotValidDateExp("DATE EXECUTION MUST BE TODAY OR AFTER");
        tx.setExecuted(today.equals(date_));
        // ? end checks if the date is valid


        // ? wa validate the transaction
        transactionService.effectuerTransaction(tx);

        // ! here we validate the transaction and update the account of the beneficiary .
//        if(today.equals(date_)) transactionService.effectuerTransaction(tx);

        Virement virement = new Virement( );

        virement.setBenificiaire(benificiaire);
        virement.setTransaction(tx);
        virement.setType(TypeVirement.UNITAIRE);

        // ? save virement
        virementRepository.save(virement);
        return newVirementDto;
    }


}
package com.adria.projetbackend.services.Virement;

import com.adria.projetbackend.dtos.NewVirementDto;
import com.adria.projetbackend.models.Benificiaire;
import com.adria.projetbackend.models.Compte;
import com.adria.projetbackend.models.Virement;

import java.text.ParseException;
import java.util.Date;

public interface IVirementService {
    NewVirementDto effectuerVirement(NewVirementDto newVirementDto, Long idClient,String username) throws ParseException;
    void saveVirement(Virement virement);
    void virementAvecPeriodicite(Date currentDate, double montant, Compte myCompte, Benificiaire benificiaire);
    void modefierVirement(NewVirementDto newVirementDto, Long idClient) throws ParseException;
}

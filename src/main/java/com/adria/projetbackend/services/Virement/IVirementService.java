package com.adria.projetbackend.services.Virement;

import com.adria.projetbackend.dtos.NewVirementDto;

import java.text.ParseException;

public interface IVirementService {
    NewVirementDto effectuerVirement(NewVirementDto newVirementDto, Long idClient) throws ParseException;
}

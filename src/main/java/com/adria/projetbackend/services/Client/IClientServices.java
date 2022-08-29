package com.adria.projetbackend.services.Client;

import com.adria.projetbackend.dtos.ClientDetailsDto;
import com.adria.projetbackend.models.Client;
import com.adria.projetbackend.utils.enums.TypeStatus;

public interface IClientServices {

   ClientDetailsDto getClientDto(Long id);

}

package com.adria.projetbackend.services.Client;

import com.adria.projetbackend.dtos.BanquierDetailsDto;
import com.adria.projetbackend.dtos.ClientDetailsDto;
import com.adria.projetbackend.exceptions.runTimeExpClasses.NoSuchBanquierException;
import com.adria.projetbackend.exceptions.runTimeExpClasses.NoSuchCustomerException;
import com.adria.projetbackend.models.Banquier;
import com.adria.projetbackend.models.Client;
import com.adria.projetbackend.repositories.ClientRepository;
import com.adria.projetbackend.utils.enums.TypeStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService implements IClientServices {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    ModelMapper modelMapper;


    public ClientDetailsDto getClientDto(Long id) {
        if ( idExists(id) )
        {
            Client banquier = clientRepository.findById(id).get();
            List<String> authorities = new ArrayList<>();
            banquier.getRoles().forEach(role -> authorities.add(role.getName().toString()));
            ClientDetailsDto banquierDto = convertToDto(banquier);
            banquierDto.setRoles(authorities.toArray(new String[authorities.size()]));
            return banquierDto;
        }

        else
            throw new NoSuchCustomerException("Customer with that identity is not found");
    }

    private ClientDetailsDto convertToDto(Client client) {
        return modelMapper.map(client, ClientDetailsDto.class);
    }

    private boolean idExists(Long id) {
        return clientRepository.existsById(id);
    }

}

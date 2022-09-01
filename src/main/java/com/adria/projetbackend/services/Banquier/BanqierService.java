package com.adria.projetbackend.services.Banquier;

import com.adria.projetbackend.dtos.BanquierDetailsDto;
import com.adria.projetbackend.dtos.ClientRegistration;
import com.adria.projetbackend.exceptions.runTimeExpClasses.NoSuchBanquierException;
import com.adria.projetbackend.models.Banquier;
import com.adria.projetbackend.models.Client;
import com.adria.projetbackend.models.Role;
import com.adria.projetbackend.repositories.BanquierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class BanqierService implements IBanquierService {

    @Autowired
    private BanquierRepository banquierRepository;

    @Autowired
    ModelMapper modelMapper;


    public Banquier registerBanquier(Banquier banquier) {
        if ( !banquierExists(banquier.getIdentifiantBanquier( )) )
            return banquierRepository.save(banquier);
        else
            return getBanquier(banquier.getIdentifiantBanquier( ));

    }

    public Banquier getBanquier(String identifiant) {
        if ( banquierExists(identifiant) )
            return banquierRepository.findByIdentifiantBanquier(identifiant);
        else
            throw new NoSuchBanquierException("Banquier with that identity is not found");
    }

    public Banquier getBanquier(Long id){
        if ( banquierExistsById(id) )
            return banquierRepository.findById(id).get();
        else
            throw new NoSuchBanquierException("Banquier with that identity is not found");
    }

    public BanquierDetailsDto getBanquierDto(Long id) {
        if ( banquierExistsById(id) )
        {
            Banquier banquier = banquierRepository.findById(id).get();
            List<String> authorities = new ArrayList<>();
            banquier.getRoles().forEach(role -> authorities.add(role.getName().toString()));
            BanquierDetailsDto banquierDto = convertToDto(banquier);
            banquierDto.setRoles(authorities.toArray(new String[authorities.size()]));
            banquierDto.setBankName(banquier.getAgence().getBanque().getRaisonSociale());
            return banquierDto;
        }

        else
            throw new NoSuchBanquierException("Banquier with that identity is not found");
    }

    public boolean banquierExists(String identifiant) {
        return banquierRepository.existsByIdentifiantBanquier(identifiant);
    }

    public boolean banquierExistsById(Long id) {
        return banquierRepository.existsById(id);
    }
    private Banquier convertToModel(BanquierDetailsDto banquierdto) {
        return modelMapper.map(banquierdto, Banquier.class);
    }

    private BanquierDetailsDto convertToDto(Banquier banquier) {
        return modelMapper.map(banquier, BanquierDetailsDto.class);
    }


}

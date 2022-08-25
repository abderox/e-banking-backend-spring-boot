package com.adria.projetbackend.services.BackOffice;


import com.adria.projetbackend.dtos.ClientRegistration;
import com.adria.projetbackend.exceptions.runTimeExpClasses.CustmerAlreadyExistsException;
import com.adria.projetbackend.exceptions.runTimeExpClasses.NoSuchCustomerException;
import com.adria.projetbackend.models.Address;
import com.adria.projetbackend.models.Client;
import com.adria.projetbackend.repositories.ClientRepository;
import com.adria.projetbackend.services.AddressService;
import com.adria.projetbackend.services.AgenceService;
import com.adria.projetbackend.services.Client.IClientServices;
import com.adria.projetbackend.services.RoleService;
import com.adria.projetbackend.utils.UtilsMethods.UtilsMethods;
import com.adria.projetbackend.utils.enums.TypePieceID;
import com.adria.projetbackend.utils.enums.TypeSituationFam;
import com.adria.projetbackend.utils.enums.TypeStatus;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;


@Service
public class BackOfficeService implements IBackOfficeServices {

    Logger LOGGER = LoggerFactory.getLogger(BackOfficeService.class);
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AgenceService agenceService;

    @Autowired
    IClientServices clientService;

    @Autowired
    AddressService addressService;

    @Autowired
    RoleService roleService;


    @Transactional
    public Client ajouterNouveauClient(ClientRegistration clientRegistration) throws ParseException {

        Client client = convertToModel(clientRegistration);

        if ( emailExists(client.getEmail( )) ||
            usernameExists(client.getUsername( )) ||
            phoneExists(client.getTelephone( )) ||
            idPiecesExists(client.getNumPieceIdentite()))
        {
            throw new CustmerAlreadyExistsException("Trying to save already existing info including : username/email/phoneNumber/identityPiece" );
        }


        client.setTypePieceID(TypePieceID.getTypePieceID(clientRegistration.getTypepiece()));
        client.setRoles(roleService.stringToEntityRoles(clientRegistration.getRoles( )));
        client.setAgence(agenceService.getAgence(clientRegistration.getCodeAgence( )));
        client.setStatus(TypeStatus.getStatus(clientRegistration.getStatusProfile( )));
        client.setDateNaissance(UtilsMethods.stringToDate(clientRegistration.getDate_birth()));
        client.setSituationFamilial(TypeSituationFam.getTypeSituationFam(clientRegistration.getFamilystatus()));

        client.setAddress(addressService.save(new Address(
                clientRegistration.getProvincAddress( ) )));


        Long lastIdInDb = clientRepository.findTopByOrderByIdDesc( ) != null ? clientRepository.findTopByOrderByIdDesc( ).getId( ) : 0;
        client.setIdentifiantClient(UtilsMethods.generateClientId(
                client.getAgence( ).getCode( ),
                (lastIdInDb + 1) + "",
                client.getAgence( ).getBanque( ).getId( ).toString( )));

        client.setPassword(passwordEncoder.encode(client.getPassword( )));
        LOGGER.debug(client.toString( ));
        return clientRepository.save(client);

    }

    @Transactional(readOnly = true)
    public Client consulterClientById(Long id) {
        if ( idExists(id) ) {
            return clientRepository.findById(id).get( );
        } else throw new NoSuchCustomerException("CUSTOMER WITH THAT ID DOES NOT EXIST");

    }

    @Transactional(readOnly = true)
    public Client consulterClientByUsername(String username) {
        if ( usernameExists(username) ) {
            return clientRepository.findByUsername(username);
        } else throw new NoSuchCustomerException("CUSTOMER WITH THAT USERNAME DOES NOT EXIST");
    }

    @Transactional(readOnly = true)
    public Client consulterClientByEmail(String email) {
        if ( emailExists(email) ) {
            return clientRepository.findByEmail(email);
        } else {
            throw new NoSuchCustomerException("CUSTOMER WITH THAT EMAIL DOES NOT EXIST");
        }
    }



    @Transactional(readOnly = true)
    public Client modifierClient(Client client) {

        if ( !idExists(client.getId( )) && !usernameExists(client.getUsername( )) ) {
            throw new NoSuchCustomerException("CUSTOMER  DOES NOT EXIST");
        }
        return clientRepository.save(client);
    }

    @Transactional
    public void supprimerClient(String clientIdentity) {
        if ( identifiantClientExists(clientIdentity) ) {
            clientRepository.deleteByIdentifiantClient(clientIdentity);
        } else {
            throw new NoSuchCustomerException("CAN NOT DELETE NOTIONAL CUSTOMER");
        }
    }


    @Transactional(readOnly = true)
    public List<Client> consulterTousClients() {
        return clientRepository.findAll( );
    }

    public Client consulterClientByIdentifiant(String clientIdentity) {
        if ( identifiantClientExists(clientIdentity) ) {
            return clientRepository.findByIdentifiantClient(clientIdentity);
        } else throw new NoSuchCustomerException("CUSTOMER WITH SUCH IDENTIFIER DOES NOT EXIST");
    }

    public boolean identifiantClientExists(String identifiantClient) {
        return clientRepository.existsByIdentifiantClient(identifiantClient);
    }

    public boolean idExists(Long id) {
        return clientRepository.existsById(id);
    }

    public boolean usernameExists(String username) {
        return clientRepository.existsByUsername(username);
    }

    public boolean emailExists(String email) {
        return clientRepository.existsByEmail(email);
    }

    public boolean phoneExists(String phone) {
        return clientRepository.existsClientByTelephone(phone);
    }

    public boolean idPiecesExists(String idp) {
        return clientRepository.existsByNumPieceIdentite(idp);
    }

    private Client convertToModel(ClientRegistration clientRegistration) {
        return modelMapper.map(clientRegistration, Client.class);
    }


}

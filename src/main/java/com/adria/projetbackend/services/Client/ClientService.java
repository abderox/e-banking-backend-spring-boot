package com.adria.projetbackend.services.Client;

import com.adria.projetbackend.dtos.BanquierDetailsDto;
import com.adria.projetbackend.dtos.ClientDetailsDto;
import com.adria.projetbackend.dtos.NewBenificiare;
import com.adria.projetbackend.exceptions.runTimeExpClasses.DomesticBenifOnlyExp;
import com.adria.projetbackend.exceptions.runTimeExpClasses.NoSuchBanquierException;
import com.adria.projetbackend.exceptions.runTimeExpClasses.NoSuchCustomerException;
import com.adria.projetbackend.models.*;
import com.adria.projetbackend.repositories.ClientRepository;
import com.adria.projetbackend.services.BackOffice.IBackOfficeServices;
import com.adria.projetbackend.services.Benificiare.IBenificiareService;
import com.adria.projetbackend.utils.enums.TypeStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService implements IClientServices {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IBenificiareService benificiareService;


    public ClientDetailsDto getClientDto(Long id) {
        if ( idExists(id) ) {
            Client client = clientRepository.findById(id).get( );
            List<String> authorities = new ArrayList<>( );
            client.getRoles( ).forEach(role -> authorities.add(role.getName( ).toString( )));
            ClientDetailsDto clientDto = convertToDto(client);
            clientDto.setRoles(authorities.toArray(new String[authorities.size( )]));
            clientDto.setBankName(client.getAgence( ).getBanque( ).getRaisonSociale( ));
            return clientDto;
        } else
            throw new NoSuchCustomerException("Customer with that identity is not found");
    }


    private List<Transaction> getTransactionsOfClient(Client client) {

        List<Compte> comptes = client.getComptes( );
        List<Transaction> transactions = new ArrayList<>( );
        for (Compte compte : comptes) {
            transactions.addAll(compte.getTransactions( ));
        }
        return transactions;
    }

    @Transactional(readOnly = true)
    public List<Compte> consulterToutesLesComptes(Long identity) {
        Client client = consulterClientById(identity);
        if ( client == null ) {
            throw new NoSuchCustomerException("CUSTOMER WITH SUCH IDENTIFIER DOES NOT EXIST");
        }
        return client.getComptes( );
    }

    @Transactional(readOnly = true)
    public List<Transaction> consulterToutesLesTransactions(Long clientIdentity) {
        Client client = consulterClientById(clientIdentity);
        if ( client != null ) {
            return getTransactionsOfClient(client);
        } else {
            throw new NoSuchCustomerException("CUSTOMER WITH SUCH IDENTIFIER DOES NOT EXIST IN THIS AGENCE");
        }
    }


    @Transactional(readOnly = true)
    public Client consulterClientById(Long id) {
        if ( idExists(id) ) {
            return clientRepository.findById(id).get( );
        } else throw new NoSuchCustomerException("CUSTOMER WITH THAT ID DOES NOT EXIST");

    }


    @Transactional
    public Benificiaire ajouterBenificiaire(Long clientId, NewBenificiare benificiaire) {
        Client client = consulterClientById(clientId);
        if ( benificiaire == null )
            throw new DomesticBenifOnlyExp("VALUES PROVIDED ARE EMPTY ... ");
        if ( benificiareService.existsByRib(benificiaire.getRib( )) ) {
            throw new DomesticBenifOnlyExp("BENIFICIAIRE ALREADY EXISTS TRY WITH ANOTHER VALID RIB");
        } else {
            Benificiaire benificiaire1 = convertToEntity(benificiaire);
            benificiaire1.setClient(client);
            benificiaire1.setNature("DOMESTIQUE");
            benificiareService.ajouterBenificiaire(benificiaire1);
            return benificiaire1;
        }
    }

    private Benificiaire convertToEntity(NewBenificiare benificiaire) {
        return modelMapper.map(benificiaire, Benificiaire.class);
    }


    private ClientDetailsDto convertToDto(Client client) {
        return modelMapper.map(client, ClientDetailsDto.class);
    }

    public boolean idExists(Long id) {
        return clientRepository.existsById(id);
    }

}

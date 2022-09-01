package com.adria.projetbackend.services.BackOffice;

/**
 * @autor abderox
 */

import com.adria.projetbackend.dtos.ClientRegistration;
import com.adria.projetbackend.dtos.ClientsDto;
import com.adria.projetbackend.dtos.NewCompteDto;
import com.adria.projetbackend.exceptions.runTimeExpClasses.CustmerAlreadyExistsException;
import com.adria.projetbackend.exceptions.runTimeExpClasses.CustomerHasAccountException;
import com.adria.projetbackend.exceptions.runTimeExpClasses.NoSuchCustomerException;
import com.adria.projetbackend.models.Address;
import com.adria.projetbackend.models.Client;
import com.adria.projetbackend.models.Compte;
import com.adria.projetbackend.models.Transaction;
import com.adria.projetbackend.repositories.ClientRepository;
import com.adria.projetbackend.repositories.CompteRepository;
import com.adria.projetbackend.services.AddressService;
import com.adria.projetbackend.services.AgenceService;
import com.adria.projetbackend.services.Client.IClientServices;
import com.adria.projetbackend.services.Compte.ICompteService;
import com.adria.projetbackend.services.Email.EmailDetails;
import com.adria.projetbackend.services.Email.EmailService;
import com.adria.projetbackend.services.RoleService;
import com.adria.projetbackend.services.Transaction.ITransactionService;
import com.adria.projetbackend.utils.UtilsMethods.UtilsMethods;
import com.adria.projetbackend.utils.enums.TypePieceID;
import com.adria.projetbackend.utils.enums.TypeSituationFam;
import com.adria.projetbackend.utils.enums.TypeStatus;
import com.adria.projetbackend.utils.enums.TypeTransaction;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
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
    ICompteService compteService;

    @Autowired
    ITransactionService transactionService;

    @Autowired
    AddressService addressService;

    @Autowired
    RoleService roleService;

    @Autowired
    private EmailService emailService;


    @Transactional
    public ClientRegistration ajouterNouveauClient(ClientRegistration clientRegistration) throws ParseException {

        Client client = convertToModel(clientRegistration);

        if ( emailExists(client.getEmail( )) ||
                usernameExists(client.getUsername( )) ||
                phoneExists(client.getTelephone( )) ||
                idPiecesExists(client.getNumPieceIdentite( )) ) {
            throw new CustmerAlreadyExistsException("Trying to save already existing info including : username/email/phoneNumber/identityPiece");
        }


        client.setTypePieceID(TypePieceID.getTypePieceID(clientRegistration.getTypepiece( )));
        client.setRoles(roleService.stringToEntityRoles(clientRegistration.getRoles( )));
        client.setAgence(agenceService.getAgence(clientRegistration.getCodeAgence( )));
        client.setStatus(TypeStatus.getStatus(clientRegistration.getStatusProfile( )));
        client.setDateNaissance(UtilsMethods.stringToDate(clientRegistration.getDate_birth( )));
        client.setSituationFamilial(TypeSituationFam.getTypeSituationFam(clientRegistration.getFamilystatus( )));
        client.setUsername(clientRegistration.getEmail( ).split("@")[0]);

        client.setAddress(addressService.save(new Address(
                clientRegistration.getProvincAddress( ), clientRegistration.getVilleAddress( ), clientRegistration.getRegionAddress( ))));


        Long lastIdInDb = clientRepository.findTopByOrderByIdDesc( ) != null ? clientRepository.findTopByOrderByIdDesc( ).getId( ) : 0;
        client.setIdentifiantClient(UtilsMethods.generateClientId(
                client.getAgence( ).getCode( ),
                (lastIdInDb + 1) + "",
                client.getAgence( ).getBanque( ).getId( ).toString( )));

        String temporaryPassword = UtilsMethods.generatePassword(10);
        client.setPassword(passwordEncoder.encode(temporaryPassword));

        clientRepository.save(client);
        String bankName = client.getAgence( ).getBanque( ).getRaisonSociale( );
        String status
                = emailService.sendSimpleMail(new EmailDetails(client.getEmail( ), "Welcome to Beta-" + bankName + " ,and Thank you for choosing us ! \nHere is a temporary password for your account : " + temporaryPassword + " \nYou must change it after you login.\nDISCLAIMER : YOUR CLIENT SPACE WILL NOT BE AVAILABLE UNLESS YOUR ACCOUNT IS ACTIVATED .\n\nBest regards , \n" + bankName,
                "Welcome to Beta-" + bankName + " ," +
                        " " + client.getUsername( ) + "!",
                ""));

        LOGGER.debug(client.toString( ));
        return clientRegistration;

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
    public List<ClientsDto> consulterTousClients(String agenceCode) {

        List<Client> clients = agenceService.getClientsOfAgence(agenceCode);
        return modelMapper.map(clients, new TypeToken<List<ClientsDto>>( ) {
        }.getType( ));

    }

    public Client consulterClientByIdentifiant(String clientIdentity) {
        if ( identifiantClientExists(clientIdentity) ) {
            return clientRepository.findByIdentifiantClient(clientIdentity);
        } else throw new NoSuchCustomerException("CUSTOMER WITH SUCH IDENTIFIER DOES NOT EXIST");
    }


    @Transactional
    public Compte addFirstAccount(NewCompteDto newCompteDto) {
        Client client = consulterClientByIdentifiant(newCompteDto.getIdentifiantClient( ));
        Transaction transaction = new Transaction( );
        if ( client == null ) {
            throw new NoSuchCustomerException("CUSTOMER WITH SUCH IDENTIFIER DOES NOT EXIST");
        }
        if ( client.getComptes( ).size( ) > 0 ) {
            throw new CustomerHasAccountException("CUSTOMER HAS ALREADY HIS FIRST  ACCOUNT");
        }

        Compte compte = convertToModel(newCompteDto);
        compte.setClient(client);
        Long lastIdInDb = compteService.getLatestRow( );
        compte.setRib(UtilsMethods.generateAccountNumber(
                client.getAgence( ).getCode( ), client.getId( ).toString( )
                , client.getAgence( ).getBanque( ).getId( ).toString( )
                , (lastIdInDb + 1) + ""));
        compte.setIntituleCompte("Compte courant");
        transaction.setCompte(compte);
        transaction.setMontant(newCompteDto.getSolde( ));
        transaction.setDateExecution(new Date( ));
        transaction.setType(TypeTransaction.DEPOT);
        transaction.setReferenceTransaction(UtilsMethods.generateRefTransaction(transactionService.getLatestRow( ).toString( )
                , client.getId( ).toString( ), TypeTransaction.DEPOT));

        if ( transaction.getMontant( ) > 0 ) {
            client.setStatus(TypeStatus.getStatus(newCompteDto.getStatusClient( )));
        } else {
            client.setStatus(TypeStatus.DESACTIVE);
        }


        transactionService.effectuerTransaction(transaction);

        clientRepository.save(client);

        String bankName = client.getAgence( ).getBanque( ).getRaisonSociale( );
        if ( client.getStatus( ) == TypeStatus.ACTIVE ) {
            String status
                    = emailService.sendSimpleMail(new EmailDetails(client.getEmail( ),
                    "Hello again , we are just apprising you , that your account is fully activated .\nYour account is labelled : "
                            + compte.getIntituleCompte( ) + "\nIdentified with : " + compte.getRib( ) + "\n\nYour initializing balance : "
                            + compte.getSolde( ) + "DH , following the transaction identified with : " + transaction.getReferenceTransaction( ) + "\n\nBest regards , \nBeta-"
                            + bankName,
                    "Hello from Beta-" + bankName + " ," +
                            " " + client.getUsername( ) + "!",
                    ""));
        } else {
            String status
                    = emailService.sendSimpleMail(new EmailDetails(client.getEmail( ),
                    "Hello again , we are just apprising you , that your account is not yet activated .\nYour account is labelled : "
                            + compte.getIntituleCompte( ) + "\nIdentified with : " + compte.getRib( ) + "\n\nPlease make sure to consult your banker for any issues . :\n\nBest regards , \nBeta-"
                            + bankName,
                    "Hello from Beta-" + bankName + " ," +
                            " " + client.getUsername( ) + "!",
                    ""));
        }


        return compteService.ajouterCompteV2(compte);
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

    private Compte convertToModel(NewCompteDto newCompteDto) {
        return modelMapper.map(newCompteDto, Compte.class);
    }


}

package com.adria.projetbackend.services.BackOffice;

/**
 * @autor abderox
 */

import com.adria.projetbackend.dtos.*;
import com.adria.projetbackend.exceptions.runTimeExpClasses.*;
import com.adria.projetbackend.models.*;
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
import com.adria.projetbackend.utils.constants.GlobalSettings;
import com.adria.projetbackend.utils.enums.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


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
    public ClientRegistration ajouterNouveauClient(ClientRegistration clientRegistration, String codeAgence) throws ParseException {

        if ( !clientRegistration.getCodeAgence( ).equals(codeAgence) ) {
            throw new NoSuchCustomerException("WARNING : YOU HAVE NO ACCESS TO THIS AGENCE !");
        }

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


    public Client consulterClientById(Long id) {
        return clientService.consulterClientById(id);
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

    @Transactional(readOnly = true)
    public List<ClientsDto> consulterTousNouveauxClients(String agenceCode) {
        List<Client> clients = agenceService.getClientsByAgenceWithStatusDesactivated(agenceCode, TypeStatus.DESACTIVE);
        List<Client> clientsFiltered = clients.stream( ).filter(client -> client.getComptes( ).size( ) < 1).collect(Collectors.toList( ));
        return modelMapper.map(clientsFiltered, new TypeToken<List<ClientsDto>>( ) {
        }.getType( ));
    }

    public Client consulterClientByIdentifiant(String clientIdentity) {
        if ( identifiantClientExists(clientIdentity) ) {
            return clientRepository.findByIdentifiantClient(clientIdentity);
        } else throw new NoSuchCustomerException("CUSTOMER WITH SUCH IDENTIFIER DOES NOT EXIST");
    }


    @Transactional
    public Compte addFirstAccount(NewCompteDto newCompteDto, String agenceCode) {
        Client client = consulterClientByIdentifiant(newCompteDto.getIdentifiantClient( ));
        Transaction transaction = new Transaction( );

        if ( client == null )
            throw new NoSuchCustomerException("CUSTOMER WITH SUCH IDENTIFIER DOES NOT EXIST");

        if ( !client.getAgence( ).getCode( ).equals(agenceCode) )
            throw new NoSuchCustomerException("YOU HAVE NO ACCESS TO THIS AGENCY !");

        if ( client.getComptes( ).size( ) > 0 )
            throw new CustomerHasAccountException("CUSTOMER HAS ALREADY HIS FIRST  ACCOUNT");


        Compte compte = convertToModel(newCompteDto);
        compte.setClient(client);
        compte.setInclusVirement(newCompteDto.isInclusVirement( ));
        Long lastIdInDb = compteService.getLatestRow( );
        compte.setRib(UtilsMethods.generateAccountNumber(
                client.getAgence( ).getCode( ), client.getId( ).toString( )
                , client.getAgence( ).getBanque( ).getId( ).toString( )
                , (lastIdInDb + 1) + ""));
        compte.setIntituleCompte("Compte courant");
        transaction.setCompte(compte);
        transaction.setMontant(newCompteDto.getSolde( ));
        transaction.setExecuted(true);
        transaction.setDateExecution(new Date( ));
        transaction.setType(TypeTransaction.DEPOT);
        transaction.setReferenceTransaction(UtilsMethods.generateRefTransaction(transactionService.getLatestRow( ).toString( )
                , client.getId( ).toString( ), TypeTransaction.DEPOT));

        if ( transaction.getMontant( ) >= GlobalSettings.MIN_DEPOSIT_AMOUNT ) {
            client.setStatus(TypeStatus.getStatus(newCompteDto.getStatusClient( )));
        } else if ( transaction.getMontant( ) < GlobalSettings.MIN_DEPOSIT_AMOUNT && transaction.getMontant( ) > 0 ) {
            throw new InsufficientDepositException("INSUFFICIENT DEPOSIT AMOUNT");
        } else {
            client.setStatus(TypeStatus.DESACTIVE);
        }


        String bankName = client.getAgence( ).getBanque( ).getRaisonSociale( );
        if ( client.getStatus( ) == TypeStatus.ACTIVE ) {
            client.setRoles(new HashSet<>(Arrays.asList(roleService.getRole(RolesE.ROLE_ACTIVE_CLIENT))));
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
                            + compte.getIntituleCompte( ) + "\nIdentified with : " + compte.getRib( ) + "\n\nPlease make sure to consult your banker for any issues .\n\nBest regards , \nBeta-"
                            + bankName,
                    "Hello from Beta-" + bankName + " ," +
                            " " + client.getUsername( ) + "!",
                    ""));
        }

        clientRepository.save(client);
        compteService.ajouterCompteV2(compte);
        transactionService.effectuerTransaction(transaction);

        return compte;
    }

    @Transactional
    public Compte addAccount(NewCompteDto newCompteDto, String agenceCode) {
        Client client = consulterClientByIdentifiant(newCompteDto.getIdentifiantClient( ));

        if ( client == null )
            throw new NoSuchCustomerException("CUSTOMER WITH SUCH IDENTIFIER DOES NOT EXIST");

        if ( !client.getAgence( ).getCode( ).equals(agenceCode) )
            throw new NoSuchCustomerException("YOU HAVE NO ACCESS TO THIS AGENCY !");

        if ( client.getStatus( ) == TypeStatus.DESACTIVE )
            throw new CustomerHasAccountException("CUSTOMER IS NOT ACTIVE");

        if ( client.getComptes( ).size( ) > GlobalSettings.MAX_ACCOUNTS_PER_CLIENT )
            throw new CustomerHasAccountException("CUSTOMER HAS ALREADY MAXIMUM ACCOUNTS");


        Compte compte = convertToModel(newCompteDto);
        compte.setClient(client);
        compte.setInclusVirement(newCompteDto.isInclusVirement( ));
        Long lastIdInDb = compteService.getLatestRow( );
        compte.setRib(UtilsMethods.generateAccountNumber(
                client.getAgence( ).getCode( ), client.getId( ).toString( )
                , client.getAgence( ).getBanque( ).getId( ).toString( )
                , (lastIdInDb + 1) + ""));
        compte.setIntituleCompte(newCompteDto.getTypeCompte( ));

        Transaction transaction = null;
        if ( newCompteDto.getSolde( ) >= GlobalSettings.MIN_DEPOSIT_AMOUNT ) {
            transaction = new Transaction( );
            transaction.setCompte(compte);
            transaction.setExecuted(true);
            transaction.setMontant(newCompteDto.getSolde( ));
            transaction.setDateExecution(new Date( ));
            transaction.setType(TypeTransaction.DEPOT);
            transaction.setReferenceTransaction(UtilsMethods.generateRefTransaction(transactionService.getLatestRow( ).toString( )
                    , client.getId( ).toString( ), TypeTransaction.DEPOT));

            compte.setSolde(newCompteDto.getSolde( ));
        } else if ( newCompteDto.getSolde( ) < GlobalSettings.MIN_DEPOSIT_AMOUNT && newCompteDto.getSolde( ) > 0 ) {
            throw new InsufficientDepositException("THE MINIMUM DEPOSIT AMOUNT IS : " + GlobalSettings.MIN_DEPOSIT_AMOUNT + "DH");
        } else {
            compte.setSolde(0.0);
        }
        compteService.ajouterCompteV2(compte);

        String transactionMsg = "";

        if ( transaction != null ) {
            transactionService.effectuerTransaction(transaction);
            transactionMsg = ", following the transaction identified with : " + transaction.getReferenceTransaction( );
        }

        String bankName = client.getAgence( ).getBanque( ).getRaisonSociale( );

        String status
                = emailService.sendSimpleMail(new EmailDetails(client.getEmail( ),
                "Hello again , we are just informing you , that you have just added a new account to your collection  .\nYour account is labelled : "
                        + compte.getIntituleCompte( ) + "\nIdentified with : " + compte.getRib( ) + "\n\nYour initializing balance : "
                        + compte.getSolde( ) + " DH " + transactionMsg + "\n\nBest regards , \nBeta-"
                        + bankName,
                "Hello from Beta-" + bankName + " ," +
                        " " + client.getUsername( ) + "!",
                ""));

        return compte;
    }

    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsOfClient(Client client) {

        List<Compte> comptes = client.getComptes( );
        List<Transaction> transactions = new ArrayList<>( );
        for (Compte compte : comptes) {
            transactions.addAll(compte.getTransactions( ));
        }
        return transactions;
    }

    @Transactional(readOnly = true)
    public List<CompteClientDto> consulterToutesLesComptes(String telephone,String identity, String  agenceCode) {

        Client client = null;

        if(identity!=null)
        {
             client = consulterClientByIdentifiant(identity);
        }
        else
        {
             client = consulterClientByTelephone(telephone);
        }


        if ( client == null ) {
            throw new NoSuchCustomerException("CUSTOMER WITH SUCH IDENTIFIER OR MOBILE DOES NOT EXIST");
        }
        if ( !client.getAgence( ).getCode( ).equals(agenceCode) ) {
            throw new NoSuchCustomerException("CUSTOMER WITH SUCH IDENTIFIER DOES NOT EXIST IN THIS AGENCY");
        }
        return modelMapper.map(client.getComptes( ), new TypeToken<List<CompteClientDto>>( ) {
        }.getType( ));
    }

    @Override
    public void editClient(EditClient editClient, String agenceCode) {
        Client client = consulterClientByIdentifiant(editClient.getIdentifiantClient( ));
        if ( client == null )
            throw new NoSuchCustomerException("CUSTOMER WITH SUCH IDENTIFIER DOES NOT EXIST");

        if ( !client.getAgence( ).getCode( ).equals(agenceCode) )
            throw new NoSuchCustomerException("YOU HAVE NO ACCESS TO THIS AGENCY !");

        client.setStatus(TypeStatus.getStatus(editClient.getStatusClient( )));

        if(client.getStatus()!=TypeStatus.ACTIVE)
        {
            client.setRoles(new HashSet<>(Collections.singletonList(roleService.getRole(RolesE.ROLE_CLIENT))));
        }
        else {
            client.setRoles(new HashSet<>(Collections.singletonList(roleService.getRole(RolesE.ROLE_ACTIVE_CLIENT))));
            String status
                    = emailService.sendSimpleMail(new EmailDetails(client.getEmail( ),
                    "Hello again , we are just letting you know , that your status is returned back 'activated' and you have have full access over the digital e-banking services  .\n\nFor any other information , consult your nearest agency !  ."
                            + "\n\nBest regards , \nBeta-"
                            + client.getAgence().getBanque().getRaisonSociale(),
                    "Hello from Beta-" + client.getAgence().getBanque().getRaisonSociale() + " ," +
                            " " + client.getUsername( ) + "!",
                    ""));
        }
        if(client.getStatus()==TypeStatus.SUSPENDU)
        {
            String status
                    = emailService.sendSimpleMail(new EmailDetails(client.getEmail( ),
                    "Hello again , we are just letting you know , that you are suspended and have no longer access to your digital e-banking services  .\n\nPlease contact your bank to get more information about this matter ."
                            + "\n\nBest regards , \nBeta-"
                            + client.getAgence().getBanque().getRaisonSociale(),
                    "Hello from Beta-" + client.getAgence().getBanque().getRaisonSociale() + " ," +
                            " " + client.getUsername( ) + "!",
                    ""));
        }
        clientRepository.save(client);


    }

    @Transactional(readOnly = true)
    public Client consulterClientByTelephone(String telephone) {
        return clientRepository.findByTelephone(telephone);
    }

    @Transactional(readOnly = true)
    public List<Transaction> consulterToutesLesTransactions(String clientIdentity, String codeAgence) {
        Client client = consulterClientByIdentifiant(clientIdentity);
        if ( client.getAgence( ).getCode( ).equals(codeAgence) ) {
            return getTransactionsOfClient(client);
        } else {
            throw new NoSuchCustomerException("CUSTOMER WITH SUCH IDENTIFIER DOES NOT EXIST IN THIS AGENCY");
        }
    }

    public boolean identifiantClientExists(String identifiantClient) {
        return clientRepository.existsByIdentifiantClient(identifiantClient);
    }

    @Transactional
    public void majCompte(CompteClientDto compteClientDto, String agenceCode) {

        Compte compte = compteService.consulterCompteByRib(compteClientDto.getRibCompte( ));

        if ( compte == null )
            throw new NoSuchAccountException("ACCOUNT WITH SUCH RIB DOES NOT EXIST");

        if ( !compte.getClient( ).getAgence( ).getCode( ).equals(agenceCode) )
            throw new NoSuchAccountException("YOU HAVE NO ACCESS TO THIS ACCOUNT");
        
        if(compteClientDto.getMontant()<GlobalSettings.MIN_DEPOSIT_AMOUNT && compteClientDto.getMontant()>0)
            throw new InsufficientDepositException("THE MINIMUM DEPOSIT AMOUNT IS : " + GlobalSettings.MIN_DEPOSIT_AMOUNT + "DH");
        if(compteClientDto.getMontant()==0)
        {
            compte.setInclusVirement(compte.isInclusVirement( ));
            compte.setBloqued(compteClientDto.isBloqued());
            compteService.save(compte);
        }
        else
        {
            Transaction transaction = new Transaction( );
            transaction.setCompte(compte);
            transaction.setMontant(compteClientDto.getMontant( ));
            transaction.setDateExecution(new Date( ));
            transaction.setType(TypeTransaction.DEPOT);
            transaction.setExecuted(true);
            transaction.setReferenceTransaction(UtilsMethods.generateRefTransaction(transactionService.getLatestRow( ).toString( )
                    , compte.getClient( ).getId( ).toString( ), TypeTransaction.DEPOT));
            compte.setBloqued(compteClientDto.isBloqued());
            compte.setInclusVirement(compte.isInclusVirement( ));
            compteService.updateCompte(compte, compteClientDto.getMontant(), TypeTransaction.DEPOT);
            transactionService.effectuerTransaction(transaction);
        }


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

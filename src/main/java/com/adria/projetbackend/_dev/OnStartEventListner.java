// * @author abderox on 23/08/2022
// * @project projetbackend

package com.adria.projetbackend._dev;
import com.adria.projetbackend.models.*;
import com.adria.projetbackend.services.*;
import com.adria.projetbackend.utils.enums.RolesE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class OnStartEventListner {

    Logger logger = LoggerFactory.getLogger(OnStartEventListner.class);
    boolean alreadySetup;

    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BanqierService banquierService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private BankService bankService;
    @Autowired
    private AgenceService agenceService;


    @Transactional
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) throws ParseException {
        if ( alreadySetup )
            return;

        List<RolesE> rolesS = Arrays.asList(
                RolesE.ROLE_ADMIN,
                RolesE.ROLE_USER
        );

        addAllRoles(rolesS);


        // ! here to the bottom is for testing purpose


        // ! les addresses de test
        Address address = new Address( );
        address.setVille("Agadir");
        address.setProvince("Chtouka Ait Baha");
        address.setRegion("Souss Massa");

        Address address2 = new Address( );
        address2.setVille("Marrakech");
        address2.setProvince("Marrakech");
        address2.setRegion("Marrakech");

        Address address3 = new Address( );
        address3.setVille("Rabat");
        address3.setProvince("Rabat");
        address3.setRegion("Rabat");

        Address address4 = new Address( );
        address4.setVille("Fes");
        address4.setProvince("Fes");
        address4.setRegion("Fes");

        Address address5 = new Address( );
        address5.setVille("Tanger");
        address5.setProvince("Tanger");
        address5.setRegion("Tanger");

        Address add1 = addressService.save(address);
        Address add2 = addressService.save(address2);
        Address add3 = addressService.save(address3);
        Address add4 = addressService.save(address4);
        Address add5 = addressService.save(address5);


        // ! banques de test

        Banque banque = new Banque( );
        banque.setTelephone("+21697899999");
        banque.setEmail("bmce@gmail.com");
        banque.setRaisonSociale("BMCE");
        banque.setRue("paradise street ");
        banque.setAddress(add3);
        bankService.addBank(banque);


        // ! les agences de test

        Agence agence = new Agence( );

        agence.setAddress(add1);
        agence.setEmail("agenceBelfaa@gmail.com");
        agence.setRue("talghazite");
        agence.setCode("0010");
        agence.setTelephone("0522222222");
        agence.setBanque(banque);

        Agence agence2 = new Agence( );

        agence2.setAddress(add2);
        agence2.setEmail("agenceGueliz@gmail.com");
        agence2.setRue("Gueliz");
        agence2.setCode("0123");
        agence2.setTelephone("0525222222");
        agence2.setBanque(banque);

        Agence agence3 = new Agence( );

        agence3.setAddress(add1);
        agence3.setEmail("agenceBiougra@gmail.com");
        agence3.setRue("alboraq");
        agence3.setCode("0735");
        agence3.setTelephone("0528222222");
        agence3.setBanque(banque);

        Agence agence4 = new Agence( );

        agence4.setAddress(add3);
        agence4.setEmail("agenceMaarif@gmail.com");
        agence4.setRue("maarif");
        agence4.setCode("0001");
        agence4.setTelephone("0528222272");
        agence4.setBanque(banque);

        Agence agence5 = new Agence( );

        agence5.setAddress(add4);
        agence5.setEmail("agenceQarawin@gmail.com");
        agence5.setRue("Qarawin");
        agence5.setCode("0013");
        agence5.setTelephone("0528222273");
        agence5.setBanque(banque);

        // ? assign agences to banque

        Agence ag1 = agenceService.addAgence(agence);
        Agence ag2 = agenceService.addAgence(agence2);
        Agence ag3 = agenceService.addAgence(agence3);
        Agence ag4 = agenceService.addAgence(agence4);
        Agence ag5 = agenceService.addAgence(agence5);


        bankService.addBank(banque);

        // ? banquiers de test

        // ! les roles de test
        Role adminRole = roleService.getRole(RolesE.ROLE_ADMIN);
        Role userRole = roleService.getRole(RolesE.ROLE_USER);
        String lUUID = String.format("%040d", new BigInteger(UUID.randomUUID( ).toString( ).replace("-", "").substring(1, 15), 16)).replace("0", "");
        Long uuid = Long.parseLong("13189534386754627");

        // ! les banquiers de test


        Banquier banquier = new Banquier( );

        banquier.setUsername("banquierGamma");
        banquier.setPassword(passwordEncoder.encode("banquier"));
        banquier.setRoles(new HashSet<>(Arrays.asList(adminRole, userRole)));
        banquier.setEmail("banquierGamma@gmail.com");
        banquier.setPosition("directeur");
        banquier.setFirstName("Hassan");
        banquier.setLastName("Bouzid");
        banquier.setStatus("active");
        banquier.setTelephone("+21696691545");
        banquier.setIdentifiantBanquier(uuid + "");
        banquier.setRue("rue de la paix");
        banquier.setDateEmbauche(chooseDate(2022, 8, 19));
        banquier.setAddress(add3);
        banquier.setAgence(ag4);


        Banquier banquier2 = new Banquier( );

        banquier2.setUsername("banquier2");
        banquier2.setPassword(passwordEncoder.encode("banquier2"));
        banquier2.setRoles(new HashSet<>(Arrays.asList(adminRole, userRole)));
        banquier2.setEmail("banquier2@gmail.com");
        banquier2.setPosition("accueil");
        banquier2.setFirstName("farid");
        banquier2.setLastName("morih");
        banquier2.setStatus("active");
        banquier2.setTelephone("+21696699799");
        banquier2.setIdentifiantBanquier((uuid + 1) + "");
        banquier2.setDateEmbauche(chooseDate(2022, 5, 12));
        banquier2.setRue("talghazite");
        banquier2.setAddress(add1);
        banquier2.setAgence(ag1);


        Banquier banquier4 = new Banquier( );


        banquier4.setUsername("banquier4");
        banquier4.setPassword(passwordEncoder.encode("banquier4"));
        banquier4.setRoles(new HashSet<>(Arrays.asList(adminRole, userRole)));
        banquier4.setEmail("banquier4@gmail.com");
        banquier4.setPosition("accueil");
        banquier4.setFirstName("abdelhadi");
        banquier4.setLastName("mouzafir");
        banquier4.setStatus("active");
        banquier4.setTelephone("+21696695792");
        banquier4.setIdentifiantBanquier((uuid + 3) + "");
        banquier4.setDateEmbauche(chooseDate(2022, 1, 12));
        banquier4.setRue("dwar jdid");
        banquier4.setAddress(add1);
        banquier4.setAgence(ag1);


        Banquier banquier3 = new Banquier( );

        banquier3.setUsername("banquier3");
        banquier3.setPassword(passwordEncoder.encode("banquier3"));
        banquier3.setRoles(new HashSet<>(Arrays.asList(adminRole, userRole)));
        banquier3.setEmail("banquier3@gmail.com");
        banquier3.setPosition("directeur");
        banquier3.setFirstName("Mohamed");
        banquier3.setLastName("bomdyan");
        banquier3.setIdentifiantBanquier((uuid + 2) + "");
        banquier3.setStatus("active");
        banquier3.setTelephone("+21696699998");
        banquier3.setRue("Jnane awrad");
        banquier4.setDateEmbauche(chooseDate(2022, 10, 20));
        banquier3.setAddress(add2);
        banquier3.setAgence(ag2);

        banquierService.registerBanquier(banquier);
        banquierService.registerBanquier(banquier3);
        banquierService.registerBanquier(banquier2);
        banquierService.registerBanquier(banquier4);


        alreadySetup = true;
        logger.info("Initializing database...");
        logger.info("Created Bankers");
        logger.info("****************************************************************************************");
        logger.info("*\t\t\t\t\t\t\t*");
        logger.info("*\t\t\t\t\t\t\t*");
        logger.info("*\t\t\t\t\t\t\t*");
        logger.info("*\t"+ banquier +"\t*");
        logger.info("*\t"+ banquier3 +"\t*");
        logger.info("*\t"+ banquier2 +"\t*");
        logger.info("*\t"+ banquier4 +"\t*");
        logger.info("*\t\t\t\t\t\t\t*");
        logger.info("*\t\t\t\t\t\t\t*");
        logger.info("*\t\t\t\t\t\t\t*");
        logger.info("****************************************************************************************");

    }

    @Transactional
    Role createRoleIfNotFound(RolesE name) {
        return roleService.save(name);
    }

    @Transactional
    void addAllRoles(List<RolesE> roles) {
        for (RolesE role : roles) {
            createRoleIfNotFound(role);
            logger.info("Role " + role.name( ) + " created");
        }
    }


    public Date chooseDate(int year, int month, int day) throws ParseException {
        SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd");
        return f2.parse(year + "-" + month + "-" + day);
    }

    public String generateClientId(String codeAgence , Long idClient , Long idBanque )
    {
        String identifiant = String.format("%040d", new BigInteger(String.valueOf(idClient),8)).substring(0,5);
        return codeAgence + identifiant + idBanque;
    }


}









package com.adria.projetbackend;

import com.adria.projetbackend.models.UserE;
import com.adria.projetbackend.repositories.AddressRepository;
import com.adria.projetbackend.repositories.AgenceRepository;
import com.adria.projetbackend.repositories.BanqueRepository;
import com.adria.projetbackend.services.AgenceService;
import com.adria.projetbackend.repositories.ClientRepository;
import com.adria.projetbackend.services.User.UserService;
import com.adria.projetbackend.utils.enums.TypeTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@SpringBootTest
class ProjetBackendApplicationTests {

    @Autowired
    private AgenceRepository agenceRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private BanqueRepository banqueRepository;
    @Autowired
    private AgenceService agenceService;


    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UserService userService;

    @Test
    void contextLoads() throws ParseException {


//        Client client = new Client();
//        client.setUsername("client");
//        client.setPassword("client");
//        client.setEmail("client");
//        client.setDateNaissance(new Date());
//        client.setMetier("client");
//
//        Client cl = clientRepository.findClientByUsername("ade");
//        System.out.println(cl.getUsername());
//        System.out.println(cl.getPassword());
//        System.out.println(cl.getEmail());
//        System.out.println(cl.getCreatedAt());
//
//        User User = userRepository.findUserByUsername("ade");
//        String lUUID = String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", "").substring(1,15), 16)).replace("0","");
//        Long uuid = Long.parseLong(lUUID);
//        System.out.println(uuid);

//        System.out.println(UtilsMethods.generateClientId("0011","495","36") );
//        System.out.println(UtilsMethods.generateClientId("0011","320","36") );
//
//        UserE user = userService.findByUsername("banquier3");
//        System.out.println(user.getUsername());
//        System.out.println(user.getPassword());
//        System.out.println(generateAccountNumber("0011","36","1","340") );

//        System.out.println(generateRefTransaction("120","36",TypeTransaction.VIREMENT) );

//        System.out.println("date : after a week "+updateDate(new Date( ),"W"));
//        System.out.println("date : after a fortnight "+updateDate(new Date( ),"F"));
//        System.out.println("date : after a month "+updateDate(new Date( ),"M"));

    }

    public Date updateDate(Date date, String periodicity)
    {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate newLocalDate = null;
        switch (periodicity)
        {
            case "D":
                newLocalDate = localDate.plusDays(1);
                break;
            case "F" :
                newLocalDate = localDate.plusWeeks(2 );
                break;
            case "M":
                newLocalDate = localDate.plusMonths(1);
                break;
            case "W":
                newLocalDate = localDate.plusWeeks(1);
                break;
            default:
                newLocalDate = localDate;
                break;
        }
        assert newLocalDate != null;
        return Date.from(newLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

}

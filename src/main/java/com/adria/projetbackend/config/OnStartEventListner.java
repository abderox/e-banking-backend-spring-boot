//package com.adria.projetbackend.config;
//
//import com.adria.projetbackend.models.Banquier;
//import com.adria.projetbackend.models.Role;
//import com.adria.projetbackend.services.RoleService;
//import com.adria.projetbackend.utils.enums.RolesE;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//
//@Component
//public class OnStartEventListner {
//
//    Logger logger = LoggerFactory.getLogger(OnStartEventListner.class);
//    boolean alreadySetup;
//
//    @Autowired
//    private RoleService roleService;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//
//    @Transactional
//    @EventListener
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        if ( alreadySetup )
//            return;
//
//        List<RolesE> rolesS = Arrays.asList(
//                RolesE.ROLE_ADMIN,
//                RolesE.ROLE_USER
//        );
//
//        addAllRoles(rolesS);
//
//        alreadySetup = true;
//        logger.info("Roles are now ready!");
//
//        // ! for testing purpose
//        Role adminRole = roleService.getRole(RolesE.ROLE_ADMIN);
//        Role userRole = roleService.getRole(RolesE.ROLE_USER);
//
//        Banquier banquier = new Banquier();
//        banquier.setUsername("banquier");
//        banquier.setPassword(passwordEncoder.encode("banquier"));
//        banquier.setRoles(new HashSet<>( Arrays.asList(adminRole, userRole) ));
//        banquier.setEmail("banquier@gmail.com");
//        banquier.setPosition("directeur");
//        banquier.setFirstName("Hassan");
//        banquier.setLastName("Bouzid");
//        banquier.setStatus("active");
//        banquier.setTelephone("+21696699999");
//        banquier.setIdentifiantBanquier("BANQUE");
//
//
//    }
//    @Transactional
//    Role createRoleIfNotFound (RolesE name){
//       return roleService.save(name);
//    }
//    void addAllRoles (List < RolesE > roles) {
//        for (RolesE role : roles) {
//            createRoleIfNotFound(role);
//            logger.info("Role " + role.name() + " created");
//        }
//    }
//
//
//
//
//
//
//}

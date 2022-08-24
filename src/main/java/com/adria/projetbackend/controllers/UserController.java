package com.adria.projetbackend.controllers;


import com.adria.projetbackend.services.RoleService;
import com.adria.projetbackend.services.User.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    ModelMapper modelMapper;

}

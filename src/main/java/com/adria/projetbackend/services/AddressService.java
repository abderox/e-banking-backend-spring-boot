package com.adria.projetbackend.services;


import com.adria.projetbackend.models.Address;
import com.adria.projetbackend.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address save(Address address) {
        if ( !addressExists(address.getProvince( )) ) {
            return addressRepository.save(address);
    }
        return addressRepository.findByProvinceContains(address.getProvince( ));
    }

    public Address getAddress(String province){
        return addressRepository.findByProvinceContains(province);
    }

    public boolean addressExists(String province){
        return addressRepository.existsByProvinceContains(province);
    }
}

package com.librarymanagement.demo.service;

import com.librarymanagement.demo.model.Address;

import java.util.List;

public interface AddressService {
    Address save(Address address);
    Address retrieve(int id);
    List<Address> retrieveAll();
    Address update(Address updatedAddress);
    void delete(int id);
}

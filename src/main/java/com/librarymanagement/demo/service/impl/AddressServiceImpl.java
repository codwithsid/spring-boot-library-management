package com.librarymanagement.demo.service.impl;

import com.librarymanagement.demo.exception.addressException.AddressNotFoundException;
import com.librarymanagement.demo.model.Address;
import com.librarymanagement.demo.repository.AddressRepository;
import com.librarymanagement.demo.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address retrieve(int id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address not found with ID: " + id));
    }

    @Override
    public List<Address> retrieveAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address update(Address updatedAddress) {
        if(!addressRepository.existsById(updatedAddress.getAddressId()))
            throw new AddressNotFoundException("Address not found");
        return addressRepository.save(updatedAddress);
    }

    @Override
    public void delete(int id) {
        if (!addressRepository.existsById(id)) {
            throw new AddressNotFoundException("Cannot delete. Address not found with ID: " + id);
        }
        addressRepository.deleteById(id);
    }
}

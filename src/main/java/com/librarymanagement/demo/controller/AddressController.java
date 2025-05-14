package com.librarymanagement.demo.controller;

import com.librarymanagement.demo.exception.addressException.AddressNotFoundException;
import com.librarymanagement.demo.model.Address;
import com.librarymanagement.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library-management/v1/address")
public class AddressController {
    @Autowired
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<Address> save(@RequestBody Address address) {
        Address saved = addressService.save(address);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> retrieve(@PathVariable int id) {
        Address address = addressService.retrieve(id);
        if(address==null)
                throw new AddressNotFoundException("Address not found with ID: " + id);  // Let global exception handler handle this
        return ResponseEntity.ok(address);


    }

    @GetMapping
    public ResponseEntity<List<Address>> retrieveAll() {
        List<Address> addresses = addressService.retrieveAll();
        return ResponseEntity.ok(addresses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> update(@PathVariable int id, @RequestBody Address address) {
        address.setAddressId(id);  // Ensure the ID is updated in the request
        Address updated = addressService.update(address);
        if (updated == null) {
            throw new AddressNotFoundException("Error while updating address with ID: " + id);  // Let global exception handler handle this
        }
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

package com.example.inventoryManagement.controller;


import com.example.inventoryManagement.exception.ResourceNotFoundException;
import com.example.inventoryManagement.model.Product;
import com.example.inventoryManagement.model.Supplier;
import com.example.inventoryManagement.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v3")
public class SupplierController {
    @Autowired
    public SupplierRepository supplierRepository;

    @GetMapping("/suppliers")
    public List<Supplier> getAllSuppliers(){
        return supplierRepository.findAll();
    }

    @PostMapping("/suppliers")
    public Supplier createSupplier(@RequestBody Supplier supplier){
        return supplierRepository.save(supplier);
    }

    @GetMapping("/suppliers/{id}")
    public ResponseEntity<Supplier> getSupplierById (@PathVariable Long id){

        Supplier supplier;
        supplier = supplierRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Supplier does not exist with id: "+ id));
        return ResponseEntity.ok(supplier);
    }

    @DeleteMapping("/suppliers/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long id){
        Supplier supplier;
        supplier = supplierRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Supplier does not exist with id: "+ id));

        supplierRepository.delete(supplier);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @PutMapping ("/suppliers/{id}")
    public ResponseEntity<Supplier> updateSupplier (@PathVariable Long id,@RequestBody Supplier supplierDetails) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier does not exist with id: " + id));

        supplier.setName(supplierDetails.getName());
        supplier.setEmail(supplierDetails.getEmail());
        supplier.setContactPerson(supplierDetails.getContactPerson());
        supplier.setPhone(supplierDetails.getPhone());

        Supplier updatedSupplier = supplierRepository.save(supplier);
        return ResponseEntity.ok(updatedSupplier);
    }
}

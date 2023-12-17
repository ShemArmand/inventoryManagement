package com.example.inventoryManagement.controller;


import com.example.inventoryManagement.exception.ResourceNotFoundException;
import com.example.inventoryManagement.model.Sale;
import com.example.inventoryManagement.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class SaleController {
    @Autowired
    private SaleRepository saleRepository;

    @GetMapping("/sales")
    public List<Sale> getAllSales(){
        return saleRepository.findAll();
    }

    @PostMapping("/sales")
    public Sale createSale(@RequestBody Sale sale){
        return saleRepository.save(sale);
    }

    @GetMapping("/sales/{id}")
    public ResponseEntity<Sale> getSaleById (@PathVariable Long id){

        Sale sale;
        sale = saleRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Sale does not exist with id: "+ id));
        return ResponseEntity.ok(sale);
    }

    @DeleteMapping("/sales/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteSale(@PathVariable Long id){
        Sale sale = saleRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Sale does not exist with id: "+id));

        saleRepository.delete(sale);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @PutMapping ("/sales/{id}")
    public ResponseEntity<Sale> updatesale (@PathVariable Long id, @RequestBody Sale saleDetails) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale does not exist with id: " + id));
        sale.setSaleDate(saleDetails.getSaleDate());
        sale.setUser(saleDetails.getUser());
        sale.setProduct(saleDetails.getProduct());
        sale.setQuantitySold(saleDetails.getQuantitySold());
        sale.setTotalPrice(saleDetails.getTotalPrice());

        Sale updatedProduct = saleRepository.save(sale);
        return ResponseEntity.ok(updatedProduct);
    }
}

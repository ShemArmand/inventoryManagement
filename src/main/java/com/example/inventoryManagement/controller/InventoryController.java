package com.example.inventoryManagement.controller;

import com.example.inventoryManagement.exception.ResourceNotFoundException;
import com.example.inventoryManagement.model.Inventory;
import com.example.inventoryManagement.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v4")
public class InventoryController {
    @Autowired
    private InventoryRepository inventoryRepository;

    @GetMapping("/inventories")
    public List<Inventory> getAllInventories(){
        return inventoryRepository.findAll();
    }

    @PostMapping("/inventories")
    public Inventory createInventory(@RequestBody Inventory inventory){
        return inventoryRepository.save(inventory);
    }

    @GetMapping("/inventories/{id}")
    public ResponseEntity<Inventory> getInventoryById (@PathVariable Long id){

        Inventory inventory;
        inventory = inventoryRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Inventory does not exist with id: "+ id));
        return ResponseEntity.ok(inventory);
    }

    @DeleteMapping("/inventories/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteInventory(@PathVariable Long id){
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Inventory does not exist with id: "+id));

        inventoryRepository.delete(inventory);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @PutMapping ("/inventories/{id}")
    public ResponseEntity<Inventory> updateInventory (@PathVariable Long id, @RequestBody Inventory inventoryDetails) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory does not exist with id: " + id));
        inventory.setQuantity(inventoryDetails.getQuantity());
        inventory.setProduct(inventoryDetails.getProduct());

        Inventory updatedInventory = inventoryRepository.save(inventory);
        return ResponseEntity.ok(updatedInventory);
    }

}

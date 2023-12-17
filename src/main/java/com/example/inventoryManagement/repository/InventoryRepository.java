package com.example.inventoryManagement.repository;

import com.example.inventoryManagement.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository <Inventory,Long> {
}

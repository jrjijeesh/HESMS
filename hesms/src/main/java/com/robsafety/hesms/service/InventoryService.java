package com.robsafety.hesms.service;

import com.robsafety.hesms.domain.InventoryType;
import com.robsafety.hesms.repository.InventoryRepository;
import com.robsafety.hesms.domain.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jijeesh on 4/9/2017.
 */

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> getInventory(Integer limit,Integer offset){
        return inventoryRepository.findAll(limit,offset);
    }

    public List<Inventory> getInventory(){
        return inventoryRepository.findAll();
    }

    public List<InventoryType> getInventoryTypeList() {
        return inventoryRepository.getInventoryType();
    }


    public InventoryType saveInventoryType(InventoryType inventoryType) {
        return inventoryRepository.saveInventoryType(inventoryType);
    }

    public Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.saveInventory(inventory);
    }
}

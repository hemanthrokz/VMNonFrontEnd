package com.VendingMachine.VendingMachine01.service;

import com.VendingMachine.VendingMachine01.dao.InitialBalanceDAOImp;
import com.VendingMachine.VendingMachine01.dao.InventoryDAOImp;
import com.VendingMachine.VendingMachine01.dto.InventoryDTO;
import com.VendingMachine.VendingMachine01.model.InitialBalanceAndPurchaseHistory;
import com.VendingMachine.VendingMachine01.model.Inventry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServices {

     private  InventoryDAOImp repository;

    private  InitialBalanceDAOImp initialBalanceDAOImp;


    public AdminServices(InventoryDAOImp repository, InitialBalanceDAOImp initialBalanceDAOImp) {
        this.repository = repository;
        this.initialBalanceDAOImp=initialBalanceDAOImp;
    }

    public int saveInventory(InventoryDTO inventry){ return repository.save(inventry); }

    public int updateInventory(Inventry inventry,int productId){
        return repository.update(inventry,productId);
    }

    public int deleteProductById(int productId){
        return repository.deleteById(productId);
    }
/////////////////////////

    public List<InitialBalanceAndPurchaseHistory> getListOfAllPurchaseHistory(){
        return allProductToPurchaseHistory(initialBalanceDAOImp.getAllPurchaseHistory());
    }

    public List<InitialBalanceAndPurchaseHistory> allProductToPurchaseHistory(List<InitialBalanceAndPurchaseHistory> allInvProduct){
        return  allInvProduct.stream().map(this::productToUserProductHistory).collect(Collectors.toList());
    }

    public InitialBalanceAndPurchaseHistory productToUserProductHistory(InitialBalanceAndPurchaseHistory inventory){
        return new InitialBalanceAndPurchaseHistory(inventory.getId(),inventory.getProductId(),inventory.getProduct(),inventory.getProductPrice(),inventory.getCustomerInputAmount(), inventory.getVendingMachineBalance(), inventory.getInitialBalance());
    }

    /////////////////////////

}

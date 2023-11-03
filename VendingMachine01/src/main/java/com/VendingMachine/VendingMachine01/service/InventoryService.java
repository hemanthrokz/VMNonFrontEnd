package com.VendingMachine.VendingMachine01.service;

import com.VendingMachine.VendingMachine01.customeexception.InsufficientInitialBalanceException;
import com.VendingMachine.VendingMachine01.customeexception.InsufficientInputCashException;
import com.VendingMachine.VendingMachine01.customeexception.ProductIdNotFoundException;
import com.VendingMachine.VendingMachine01.customeexception.ProductUnavialableException;
import com.VendingMachine.VendingMachine01.dao.InitialBalanceDAOImp;
import com.VendingMachine.VendingMachine01.dao.InventoryDAOImp;
import com.VendingMachine.VendingMachine01.dto.CustomerInputDTO;
import com.VendingMachine.VendingMachine01.dto.InventoryDTO;
import com.VendingMachine.VendingMachine01.dto.VendingMachineOutputDTO;
import com.VendingMachine.VendingMachine01.model.InitialBalanceAndPurchaseHistory;

import com.VendingMachine.VendingMachine01.model.Inventry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    InventoryDAOImp repository;

    InitialBalanceDAOImp initialBalanceDAOImp;

    private static Logger log = LoggerFactory.getLogger(InventoryService.class);

    public InventoryService(InventoryDAOImp repository, InitialBalanceDAOImp initialBalanceDAOImp) {
        this.repository = repository;
        this.initialBalanceDAOImp = initialBalanceDAOImp;
    }

    public List<InventoryDTO> getListOfAllInventory(){
        return allProductToUserInventory(repository.findAll());
    }
//////////////////////////////////////////////////////



    ///////////////////////////////////////////////////
    public List<InventoryDTO> allProductToUserInventory(List<Inventry> allInvProduct){
        return  allInvProduct.stream().map(this::productToUserProduct).collect(Collectors.toList());
    }


    public InventoryDTO getProductById(int productId){
        log.info("product id in get product by id == {} ",productId);
        if(repository.findById(productId).isEmpty()){
            throw new ProductIdNotFoundException("invalid product id given in url ....!!!!");
        }else if (repository.findById(productId).get(0).getProductInventryCount() < 1) {
            throw new ProductUnavialableException(repository.findById(productId).get(0).getName() + " is Out of Stock..!!");
        }
        return productToUserProduct(repository.findById(productId).get(0));
    }

    public InventoryDTO productToUserProduct(Inventry inventory){
        return new InventoryDTO(inventory.getProductId(),inventory.getName(),inventory.getProductPrice(),inventory.getProductInventryCount());
    }


    //to purchase the product method
    public VendingMachineOutputDTO purchaseProduct(CustomerInputDTO customerInputDTO) {
        int productId = customerInputDTO.getProductId();
        int inputPrice = customerInputDTO.getPrice();

        log.info("product id in purchase product == {} ",productId);

        getProductById(productId);
        Inventry inventory = repository.findById(productId).get(0);
        if (inputPrice < inventory.getProductPrice()) {
            throw new InsufficientInputCashException(inputPrice + " rupees is not enough for " + inventory.getName());
        }
        int change = inputPrice  - inventory.getProductPrice();

        if (initialBalanceDAOImp.getChange().getInitialBalance()< change) {
            throw new InsufficientInitialBalanceException("Sorry No Change!!");
        }
        log.info("change amount for the purchase == {} ",change);

        int newInitialBalance = initialBalanceDAOImp.getChange().getInitialBalance() - change;

        log.info("new balance in the vending machine == {}",newInitialBalance);

        ///creating new object to populate the initial balance table and the record table
        InitialBalanceAndPurchaseHistory currentTransaction = new InitialBalanceAndPurchaseHistory(0, inventory.getProductId(), inventory.getName(), inventory.getProductPrice(), inputPrice, change, newInitialBalance);

        //calling update stock method form the inventorydaoimplementation class
        repository.updatedStock(inventory.getProductId(), inventory.getProductInventryCount() - 1);
        initialBalanceDAOImp.saveTransaction(currentTransaction);
        return new VendingMachineOutputDTO(inventory.getName(), inventory.getProductPrice(), change);

    }

}

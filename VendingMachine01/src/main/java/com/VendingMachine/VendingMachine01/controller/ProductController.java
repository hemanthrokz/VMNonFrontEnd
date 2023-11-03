package com.VendingMachine.VendingMachine01.controller;

import com.VendingMachine.VendingMachine01.dto.CustomerInputDTO;
import com.VendingMachine.VendingMachine01.dto.InventoryDTO;
import com.VendingMachine.VendingMachine01.dto.VendingMachineOutputDTO;
import com.VendingMachine.VendingMachine01.model.InitialBalanceAndPurchaseHistory;
import com.VendingMachine.VendingMachine01.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@Tag(name = "CUSTOMER PROCESS")
public class ProductController {

    private InventoryService inventoryService;

    private static Logger log = LoggerFactory.getLogger(ProductController.class);

    public ProductController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }


    @GetMapping("/")
    @Operation(summary = "CUSTOMER PROCESS--Get LIST OF ALL Inventry item")
    public List<InventoryDTO> getListOfAllInventory() {
        return inventoryService.getListOfAllInventory();
    }



    @GetMapping("/product/{id}")
    @Operation(summary = "CUSTOMER PROCESS--Get Inventry item by id")
    public InventoryDTO getProductById(@PathVariable int id) {
        return inventoryService.getProductById(id);
    }
    /////////////////////////////////////////////////
//
//    @GetMapping("/product/{productCount}")
//    @Operation(summary = "CUSTOMER PROCESS--Get list of Inventry item by avialiability")
//    public List<InventoryDTO> getProductByInventryCount(@PathVariable int productCount) {
//        return inventoryService.getProductByInventryCount(productCount);
//    }

    ///////////////////////////////////////////////////////////
    @PutMapping("/product")
    @Operation(summary = "CUSTOMER PROCESS--purchase Inventry item")
    public VendingMachineOutputDTO ProductPurchase(@RequestBody CustomerInputDTO customerInputDTO) {
        log.info("product id in purchase controller == {} ",customerInputDTO.getProductId());
        log.info("product price in purchase controller == {} ",customerInputDTO.getPrice());

        return   inventoryService.purchaseProduct(customerInputDTO );
    }

}

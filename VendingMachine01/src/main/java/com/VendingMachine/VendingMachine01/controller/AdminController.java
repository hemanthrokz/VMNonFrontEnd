package com.VendingMachine.VendingMachine01.controller;

import com.VendingMachine.VendingMachine01.dto.InventoryDTO;
import com.VendingMachine.VendingMachine01.model.InitialBalanceAndPurchaseHistory;
import com.VendingMachine.VendingMachine01.service.AdminServices;
import com.VendingMachine.VendingMachine01.model.Inventry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "ADMIN PROCESS")
public class AdminController {

    private AdminServices adminServices;


    public AdminController( AdminServices adminServices) {
        this.adminServices = adminServices;
    }


////////////////////////////////////////////////////////

    @DeleteMapping("/products/{id}")
    @Operation(summary = "ADMIN PROCESS--DELETE  Inventory item ")
    public String deleteProductById(@PathVariable int id) {
        return adminServices.deleteProductById(id)+" Product(s) delete from the database";
    }
//
//    @PostMapping("/products")
//    @Operation(summary = "ADMIN PROCESS--Add  Inventory item ")
//    public String saveInventory(@RequestBody Inventry.InventoryBuilder e) {
//        return adminServices.saveInventory(e) + " product added successfully";
//    }

    @GetMapping("/purchasehistory")
    @Operation(summary = "CUSTOMER PROCESS--Get LIST OF ALL Inventry item")
    public List<InitialBalanceAndPurchaseHistory> getListOfAllPurchaseHistory() {
        return adminServices.getListOfAllPurchaseHistory();
    }


    @PostMapping("/products")
    @Operation(summary = "ADMIN PROCESS--Add  Inventory item ")
    public String saveInventory(@RequestBody InventoryDTO e) {
        return adminServices.saveInventory(e) + " product added successfully";
    }

    @PutMapping("/productsput/{id}")
    @Operation(summary = "ADMIN PROCESS--Update  Inventory item ")
    public String updateInventory(@RequestBody Inventry e, @PathVariable int id) {
        return adminServices.updateInventory(e, id)+" Product (s) updated successfully";
    }








}

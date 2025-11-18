package com.example.capston1.Controller;

import com.example.capston1.Api.ApiResponse;
import com.example.capston1.Model.MerchantStock;
import com.example.capston1.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("api/v1/merchantStock")
@RestController
@RequiredArgsConstructor
public class MerchantStockController {

    private final MerchantStockService merchantStockService;


    @GetMapping("/get")
    public ResponseEntity<?> getMerchantStocks () {
        if (merchantStockService.getMerchantStocks().isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("No merchant stock found"));
        }
        return ResponseEntity.status(200).body(merchantStockService.getMerchantStocks());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchantStock (@RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        if (merchantStockService.addMerchantStock(merchantStock) == 2) {
            return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
        }
        else if (merchantStockService.addMerchantStock(merchantStock) == 3) {
            return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
        }
        merchantStockService.addMerchantStock(merchantStock);
        return ResponseEntity.status(200).body(new ApiResponse("Merchant stock added successfully"));
    }



    @PutMapping("'/update/{id}")
    public ResponseEntity<?> updateMerchantStock (@PathVariable String id, @RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        if (merchantStockService.updateMerchantStock(id, merchantStock) == 1) {
            return ResponseEntity.status(400).body(new ApiResponse("Merchant stock not found"));
        }
        else if (merchantStockService.updateMerchantStock(id, merchantStock) == 2) {
            return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
        }
        else if (merchantStockService.updateMerchantStock(id, merchantStock) == 3) {
            return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Merchant stock updated successfully"));
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchantStock (@PathVariable String id) {
        if (merchantStockService.deleteMerchantStock(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant stock deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant stock not found"));
    }



    @PutMapping("/add-stock/{merchantId}/{productId}/{amount}")
    public ResponseEntity<?> addToStock (@PathVariable String merchantId, @PathVariable String productId, @PathVariable int amount) {
        if (merchantStockService.addToStock(merchantId, productId, amount) == 1) {
            return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
        }
        else if (merchantStockService.addToStock(merchantId, productId, amount) == 3) {
            return ResponseEntity.status(400).body(new ApiResponse("The merchant doesn't have the product"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Stock updated successfully"));
    }



//---------------------EXTRA-----------------------------------------------------

    @GetMapping("/get-stock/{merchantId}")
    public ResponseEntity<?> getOutOfStock (@PathVariable String merchantId) {
        ArrayList<MerchantStock> outStock = merchantStockService.getOutOfStock(merchantId);

        if (outStock == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
        }

        if (outStock.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("Nothing is out of stock"));
        }
        return ResponseEntity.status(200).body(outStock);
    }

}

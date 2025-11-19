package com.example.capston1.Controller;

import com.example.capston1.Api.ApiResponse;
import com.example.capston1.Model.Merchant;
import com.example.capston1.Model.User;
import com.example.capston1.Service.MerchantService;
import com.example.capston1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/merchant")
@RestController
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;
    private final ProductService productService;


    @GetMapping("/get")
    public ResponseEntity<?> getMerchant() {
        if (merchantService.getMerchants().isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("No merchant found"));
        }
        return ResponseEntity.status(200).body(merchantService.getMerchants());
    }



    @PostMapping("/add")
    public ResponseEntity<?> addMerchants(@RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        merchantService.addMerchants(merchant);
        return ResponseEntity.status(200).body(new ApiResponse("Merchant added successfully"));
    }



    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchants (@PathVariable String id, @RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        if (merchantService.updateMerchants(id, merchant)) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchants (@PathVariable String id) {
        if (merchantService.deleteMerchants(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
    }
}
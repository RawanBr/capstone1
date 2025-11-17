package com.example.capston1.Controller;

import com.example.capston1.Api.ApiResponse;
import com.example.capston1.Model.Product;
import com.example.capston1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/product")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;



    @GetMapping("/get")
    public ResponseEntity<?> getProducts () {
        if (productService.getProducts().isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("No product found"));
        }
        return ResponseEntity.status(200).body(productService.getProducts());
    }



    @PostMapping("/add")
    public ResponseEntity<?> addProduct (@RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        if (productService.addProduct(product)) {
            return ResponseEntity.status(200).body(new ApiResponse("Product added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Category not found"));
    }



    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct (@PathVariable String id, @RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        if (productService.updateProduct(id, product) == 2) {
            return ResponseEntity.status(400).body(new ApiResponse("Category not found"));
        }
        else if (productService.updateProduct(id, product) == 3) {
            return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Product updated successfully"));
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct (@PathVariable String id) {
     if (productService.deleteProduct(id)) {
         return ResponseEntity.status(200).body(new ApiResponse("Product deleted successfully"));
     }
        return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
    }


//-------------------------------Extra------------------------------------------

    @GetMapping("/search/{id}")
    public ResponseEntity<?> searchByCategory (@PathVariable String id) {
        if (productService.searchByCategory(id).isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("No product found"));
        }
        return ResponseEntity.status(200).body(productService.searchByCategory(id));
    }

}

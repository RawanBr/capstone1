package com.example.capston1.Controller;

import com.example.capston1.Api.ApiResponse;
import com.example.capston1.Model.Product;
import com.example.capston1.Model.User;
import com.example.capston1.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;



    @GetMapping("/get")
    public ResponseEntity<?> getUsers () {
        if (userService.getUsers().isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("No users found"));
        }
        return ResponseEntity.status(200).body(userService.getUsers());
    }



    @PostMapping("/add")
    public ResponseEntity<?> addUser (@RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("User added successfully"));
    }



    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser (@PathVariable String id, @RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        if (userService.updateUser(id, user)) {
            return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User not found"));
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser (@PathVariable String id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User not found"));
    }



    @PutMapping("/buy/{userId}/{productId}/{merchantId}")
    public ResponseEntity<?> buy (@PathVariable String userId, @PathVariable String productId, @PathVariable String merchantId) {
        int result = userService.buy(userId, productId, merchantId);

        switch (result) {
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("User not found"));
            case 2:
                return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
            case 3:
                return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
            case 4:
                return ResponseEntity.status(400).body(new ApiResponse("The merchant doesn't have the product"));
            case 5:
                return ResponseEntity.status(400).body(new ApiResponse("The product is out of stock"));
            case 6:
                return ResponseEntity.status(400).body(new ApiResponse("User doesn't have enough balance"));
            case 7:
                return ResponseEntity.status(200).body(new ApiResponse("Buying made successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Invalid operation"));

    }

//------------------EXTRA----------------------------------------------------------------

    @PutMapping("/add-balance/{userId}/{amount}")
    public ResponseEntity<?> addBalance (@PathVariable String userId, @PathVariable int amount) {
        int result = userService.addBalance(userId, amount);

        if (result == 1) {
        return ResponseEntity.status(400).body("User not found");
        }
        return ResponseEntity.status(200).body(new ApiResponse("Balance added successfully"));
    }



    @GetMapping("/get-affordable/{userId}/{categoryId}")
    public ResponseEntity<?> getAffordableProducts (@PathVariable String userId, @PathVariable String categoryId) {
        ArrayList<Product> affordable = userService.getAffordableProducts(userId, categoryId);

        if (affordable == null) {
            return ResponseEntity.status(400).body("User not found");
        }
        else if (affordable.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("No affordable product found for this user"));
        }
        return ResponseEntity.status(200).body(affordable);
    }



    @GetMapping("/get-reminding/{userId}/{productId}")
    public ResponseEntity<?> remainingBalance (@PathVariable String userId, @PathVariable String productId) {
        double result = userService.remainingBalance(userId, productId);

        if (result == 1) {
            return ResponseEntity.status(400).body(new ApiResponse("User not found"));
        }
        else if (result == 2) {
            return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
        }
        else if (result == 3) {
            return ResponseEntity.status(400).body(new ApiResponse("User doesn't have enough balance"));
        }
        else {
            return ResponseEntity.status(200).body(result);
        }
    }

}

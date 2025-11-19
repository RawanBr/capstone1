package com.example.capston1.Controller;

import com.example.capston1.Api.ApiResponse;
import com.example.capston1.Model.Category;
import com.example.capston1.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("/get")
    public ResponseEntity<?> getCategories () {
        if (categoryService.getCategories().isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("No categories found"));
        }
        return ResponseEntity.status(200).body(categoryService.getCategories());
    }



    @PostMapping("/add")
    public ResponseEntity<?> addCategory (@RequestBody @Valid Category category, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        categoryService.addCategory(category);
        return ResponseEntity.status(200).body(new ApiResponse("Category added successfully"));
    }



    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory (@PathVariable String id, @RequestBody @Valid Category category, Errors errors) {
      if (errors.hasErrors()) {
          String message = errors.getFieldError().getDefaultMessage();
          return ResponseEntity.status(400).body(new ApiResponse(message));
      }
     if (categoryService.updateCategory(id, category)) {
         return ResponseEntity.status(200).body(new ApiResponse("Category added successfully"));
     }
     return ResponseEntity.status(400).body(new ApiResponse("Category not found"));
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory (@PathVariable String id) {
        if (categoryService.deleteCategory(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("Category deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Category not found"));
    }











}

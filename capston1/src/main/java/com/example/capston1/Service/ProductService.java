package com.example.capston1.Service;

import com.example.capston1.Model.Category;
import com.example.capston1.Model.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final CategoryService categoryService;

    ArrayList<Product> products = new ArrayList<>();


    public ArrayList<Product> getProducts () {
        return products;
    }



    public boolean addProduct (Product product) {
        for (int i = 0; i < categoryService.getCategories().size(); i++) {
            if (product.getCategoryId().equals(categoryService.getCategories().get(i).getId())) {
                products.add(product);
                return true;
            }
        }
        return false;
    }



    public int updateProduct (String id, Product product) {
        for (int i = 0; i < products.size(); i++) {

            if (products.get(i).getId().equals(id)) {

                // check if category exists
                boolean categoryFound = false;
                for (int j = 0; j < categoryService.getCategories().size(); j++) {
                    if (product.getCategoryId().equals(categoryService.getCategories().get(j).getId())) {
                        categoryFound = true;
                        break;
                    }
                }

                if (!categoryFound) {
                    //category not found
                    return 2;
                }

                products.set(i, product);
                //update success
                return 1;
            }
        }
        //product id not found
        return 3;
    }



    public boolean deleteProduct (String id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.remove(i);
                return true;
            }
        }
        return false;
    }



    //-------------------------------EXTRA--------------------

    public ArrayList<Product> searchByCategory (String categoryId) {
        ArrayList<Product> products1 = new ArrayList<>();

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getCategoryId().equals(categoryId)) {
                products1.add(products.get(i));
            }
        }
        return products1;
    }








}

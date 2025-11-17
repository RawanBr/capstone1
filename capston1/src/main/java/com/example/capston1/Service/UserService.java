package com.example.capston1.Service;

import com.example.capston1.Model.Merchant;
import com.example.capston1.Model.MerchantStock;
import com.example.capston1.Model.Product;
import com.example.capston1.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class UserService {

    private final ProductService productService;
    private final MerchantStockService merchantStockService;
    private final MerchantService merchantService;


    ArrayList<User> users = new ArrayList<>();


    public ArrayList<User> getUsers () {
        return users;
    }



    public void addUser (User user) {
        users.add(user);
    }



    public boolean updateUser (String id, User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.set(i, user);
                return true;
            }
        }
        return false;
    }



    public boolean deleteUser (String id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.remove(i);
                return true;
            }
        }
        return false;
    }



    public int buy(String userId, String productId, String merchantId) {
        User user = null;
        Product product = null;
        Merchant merchant = null;
        MerchantStock stock = null;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(userId)) {
                user = users.get(i);
                break;
            }
        }
        if (user == null) {
            // User not found
            return 1;
        }

        for (int i = 0; i < productService.getProducts().size(); i++) {
            if (productService.getProducts().get(i).getId().equals(productId)) {
                product = productService.getProducts().get(i);
                break;
            }
        }
        if (product == null) {
            // Product not found
            return 2;
        }

        for (int i = 0; i < merchantService.getMerchants().size(); i++) {
            if (merchantService.getMerchants().get(i).getId().equals(merchantId)) {
                merchant = merchantService.getMerchants().get(i);
                break;
            }
        }
        if (merchant == null) {
            // Merchant not found
            return 3;
        }

        for (int i = 0; i < merchantStockService.getMerchantStocks().size(); i++) {
            MerchantStock s = merchantStockService.getMerchantStocks().get(i);
            if (s.getMerchantId().equals(merchantId) && s.getProductId().equals(productId)) {
                stock = s;
                break;
            }
        }
        if (stock == null) {
            // Merchant doesn't have the product
            return 4;
        }

        if (stock.getStock() <= 0) {
            return 5; // Stock is 0
        }

        if (user.getBalance() < product.getPrice()) {
            // Insufficient balance
            return 6;
        }

        stock.setStock(stock.getStock() - 1);
        user.setBalance(user.getBalance() - product.getPrice());

        return 7; // Buy successful
    }




    //----------------------------EXTRA---------------------------------------

    public boolean addBalance (String userId, int amount) {
        for (int i = 0; i < getUsers().size(); i++) {
            if (users.get(i).getId().equals(userId)) {
                users.get(i).setBalance(users.get(i).getBalance() + amount);
                return true;
            }
        }
        return false;
    }



    public ArrayList<Product> getAffordableProducts (String userId) {
        ArrayList<Product> affordable = new ArrayList<>();
        User user = null;

        for (int i = 0; i < getUsers().size(); i++) {
            if (users.get(i).getId().equals(userId)) {
                user = users.get(i);
                break;
            }
        }

        if (user == null) {
            return affordable;
        }

        for (int i = 0; i < productService.getProducts().size(); i++) {
            if (productService.getProducts().get(i).getPrice() <= user.getBalance()) {
                affordable.add(productService.getProducts().get(i));
            }
        }
        return affordable;
    }



    public double remainingBalance (String userId, String productId) {
        User user = null;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(userId)) {
                user = users.get(i);
                break;
            }
        }

        if (user == null) {
            //if user not found
            return 1;
        }

        Product product = null;

        for (int i = 0; i < productService.getProducts().size(); i++) {
            if (productService.getProducts().get(i).getId().equals(productId)) {
                product = productService.getProducts().get(i);
                break;
            }
        }
        if (product == null) {
            //if product not found
            return 2;
        }

        if (user.getBalance() < product.getPrice()) {
            return 3;
        }

        return user.getBalance() - product.getPrice();
    }





}

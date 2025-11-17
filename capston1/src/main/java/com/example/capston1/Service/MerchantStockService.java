package com.example.capston1.Service;

import com.example.capston1.Model.Merchant;
import com.example.capston1.Model.MerchantStock;
import com.example.capston1.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class MerchantStockService {

    private final MerchantService merchantService;
    private final ProductService productService;

    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    public ArrayList<MerchantStock> getMerchantStocks() {
        return merchantStocks;
    }



    public int addMerchantStock(MerchantStock merchantStock) {
        //check the product id
        Product product = null;
        for (int i = 0; i < productService.getProducts().size(); i++) {
            if (productService.getProducts().get(i).getId().equals(merchantStock.getProductId())) {
                product = productService.getProducts().get(i);
                break;
            }
        }
        if (product == null) {
            //product id not found
            return 3;
        }


        //check the merchant id
        Merchant merchant = null;
        for (int i = 0; i < merchantService.getMerchants().size(); i++) {
            if (merchantService.getMerchants().get(i).getId().equals(merchantStock.getMerchantId())) {
                merchant = merchantService.getMerchants().get(i);
                break;
            }
        }
        if (merchant == null) {
            //merchant id not found
            return 2;
        }

        for (MerchantStock ms : merchantStocks) {
            if (ms.getMerchantId().equals(merchantStock.getMerchantId()) &&
                ms.getProductId().equals(merchantStock.getProductId())) {
                //if the product already existing
                return 4;
            }
        }

        //added successfully
        merchantStocks.add(merchantStock);
        return 1;
    }



        public int updateMerchantStock(String id, MerchantStock merchantStock) {

        MerchantStock Stock = null;
        int index = 0;

        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)) {
                Stock = merchantStocks.get(i);
                index = i;
                break;
            }
        }
        if (Stock == null) {
            // merchant stock id not found
            return 1;
        }

        Product product = null;
        for (int i = 0; i < productService.getProducts().size(); i++) {
            if (productService.getProducts().get(i).getId().equals(merchantStock.getProductId())) {
                product = productService.getProducts().get(i);
                break;
            }
        }
        if (product == null) {
            // product id not found
            return 2;
        }

        Merchant merchant = null;
        for (int i = 0; i < merchantService.getMerchants().size(); i++) {
            if (merchantService.getMerchants().get(i).getId().equals(merchantStock.getMerchantId())) {
                merchant = merchantService.getMerchants().get(i);
                break;
            }
        }
        if (merchant == null) {
            // merchant id not found
            return 3;
        }

        merchantStocks.set(index, merchantStock);
        // updated successfully
        return 4;
    }



    public boolean deleteMerchantStock (String id) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)) {
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }



    public int addToStock(String merchantId, String productId, int amount) {
        //check the product id
        Product product = null;
        for (int i = 0; i < productService.getProducts().size(); i++) {
            if (productService.getProducts().get(i).getId().equals(productId)) {
                product = productService.getProducts().get(i);
                break;
            }
        }
        if (product == null) {
            //product not found
            return 1;
        }

        //check if the merchant have the product
        for (int i = 0; i < getMerchantStocks().size(); i++) {
            if (getMerchantStocks().get(i).getMerchantId().equals(merchantId) &&
                    getMerchantStocks().get(i).getProductId().equals(productId)) {
                //update the stock
                getMerchantStocks().get(i).setStock(getMerchantStocks().get(i).getStock() + amount);
                return 2;
            }
        }
        //merchant doesn't have the product
        return 3;
    }



    //--------------------EXTRA---------------------------------------

    public ArrayList<MerchantStock> getOutOfStock (String merchantId) {
        ArrayList<MerchantStock> outStock = new ArrayList<>();

        for (int i = 0; i < getMerchantStocks().size(); i++) {
            if (getMerchantStocks().get(i).getMerchantId().equals(merchantId)) {
                if (getMerchantStocks().get(i).getStock() < 1) {
                    outStock.add(getMerchantStocks().get(i));
                }
            }
        }
        return outStock;
    }







}

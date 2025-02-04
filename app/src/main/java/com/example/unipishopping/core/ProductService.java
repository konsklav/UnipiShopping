package com.example.unipishopping.core;

import com.example.unipishopping.domain.Product;
import com.example.unipishopping.domain.Purchase;
import com.example.unipishopping.domain.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ProductService implements ProductCallbacks{
    private final DatabaseReference productReference;

    public ProductService() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        productReference = database.getReference("products");
    }

    // Check https://github.com/Noveboi/UnipiShopping/issues/12#issuecomment-2635238969
    public void order(int productId, User buyer){
        searchProduct(productId, buyer,this);
    }

    // You can transfer the searchProduct() code in order()
    public void searchProduct(int productId, User buyer, ProductCallbacks callbacks){
        productReference.child(String.valueOf(productId)).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                DataSnapshot snapshot = task.getResult();
                if (snapshot.exists()) {
                    Product product = snapshot.getValue(Product.class);
                    callbacks.onProductFound(product, buyer);
                    return;
                }
                callbacks.onProductNotFound();
            }
            callbacks.onProductNotFound();
        });
    }

    // Implementing the ProductCallbacks interface is redundant.
    // You can handle the product found/not found logic directly in the order() method
    @Override
    public void onProductFound(Product product, User buyer) {
        Purchase purchase = new Purchase(product.getId(), LocalDateTime.now().toInstant(ZoneOffset.ofHours(2)).toEpochMilli());
        buyer.addPurchase(purchase);
    }

    @Override
    public void onProductNotFound() {
        // TODO!
    }
}

package com.example.GSneaker.repositories;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.GSneaker.models.Product;

import java.util.ArrayList;
import java.util.List;

public class CartRepo {
    private  String TAG = "CartRepo";
    private MutableLiveData<List<Product>> mutableCart = new MutableLiveData<>();
    private MutableLiveData<Double> mutableTotalPrice = new MutableLiveData<>();
    private int cartCount;

    public LiveData<List<Product>> getCart() {
        if (mutableCart.getValue() == null) {
            initCart();
        }
        return mutableCart;
    }

     public void setCart(List<Product> list){
        mutableCart.setValue(list);
     }

    public void initCart() {
        mutableCart.setValue(new ArrayList<Product>());
        calculateCartTotal();
    }

    public boolean addItemToCart(Product addproduct) {
        if (mutableCart.getValue() == null) {
            initCart();
        }

        List<Product> productList = new ArrayList<>(mutableCart.getValue());
        for (Product product: productList) {
            if (product.getId().equals(addproduct.getId())) {
                int index = productList.indexOf(product);
                product.setQuantity(product.getQuantity() + 1);
                productList.set(index, product);

                mutableCart.setValue(productList);
                calculateCartTotal();
                return true;
            }
        }
        Product product = new Product(addproduct);
        productList.add(product);
        mutableCart.setValue(productList);
        calculateCartTotal();
        return true;
    }

    public void removeItemFromCart(Product product) {
        if (mutableCart.getValue() == null) {
            return;
        }
        List<Product> productList = new ArrayList<>(mutableCart.getValue());
        productList.remove(product);
        mutableCart.setValue(productList);
        calculateCartTotal();
    }

    public  void changeQuantity(Product product, int quantity) {
        if (mutableCart.getValue() == null) return;

        List<Product> productList = new ArrayList<>(mutableCart.getValue());

        Product updatedItem = new Product(product, quantity);
        productList.set(productList.indexOf(product), updatedItem);

        mutableCart.setValue(productList);
        calculateCartTotal();
    }

    private void calculateCartTotal() {
        if (mutableCart.getValue() == null) return;
        double total = 0.0;
        List<Product> productList = mutableCart.getValue();
        for (Product product: productList) {
            total += Float.parseFloat(product.getPrice()) * product.getQuantity();
        }
        mutableTotalPrice.setValue(total);
    }

    public LiveData<Double> getTotalPrice() {
        if (mutableTotalPrice.getValue() == null) {
            mutableTotalPrice.setValue(0.0);
        }
        return mutableTotalPrice;
    }


    public int getCartCount() {
        return cartCount;
    }

    public void setCartCount(int cartCount) {
        this.cartCount = cartCount;
    }
}

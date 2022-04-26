package com.example.GSneaker.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.GSneaker.models.Product;
import com.example.GSneaker.repositories.CartRepo;
import com.example.GSneaker.repositories.ShopRepo;

import java.util.List;

public class ShopViewModel extends ViewModel {
    ShopRepo shopRepo = new ShopRepo();
    CartRepo cartRepo = new CartRepo();

    public LiveData<List<Product>> getProducts() {
        return shopRepo.getProducts();
    }

    public LiveData<List<Product>> getCart() {
        return cartRepo.getCart();
    }

    public void setProducts(List<Product> list) {
        shopRepo.setProducts(list);
    }


    public void setCart(List<Product> list) {
        if (list != null) {
            cartRepo.setCart(list);
//            List<Product> shopList = getCart().getValue();
//            for (Product p : list) {
//                if (p.getQuantity() > 0) {
//                    shopList.get(Integer.parseInt(p.getId()) - 1).setQuantity(1);
//                }
//
//            }
//            setProducts(shopList);
        }
    }

    public boolean addItemToCart(Product product) {
        cartRepo.addItemToCart(product);
        shopRepo.addItemToCart(product);
        return true;
    }

    public void removeItemFromCart(Product cartItem) {
        cartRepo.removeItemFromCart(cartItem);
        shopRepo.itemRemovedFromCart(cartItem);
    }

    public void changeQuantity(Product cartItem, int quantity) {
        cartRepo.changeQuantity(cartItem, quantity);
    }

    public LiveData<Double> getTotalPrice() {
        return cartRepo.getTotalPrice();
    }

    public void resetCart() {
        cartRepo.initCart();
    }

    public int getCartCount() {
        return this.getCart().getValue().size();
    }

}

package com.example.GSneaker.repositories;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.GSneaker.models.Product;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ShopRepo {
    private String TAG = "ShopRepo";
    private MutableLiveData<List<Product>> mutableProductList = new MutableLiveData<>();
    ;

    public LiveData<List<Product>> getProducts() {
        if (mutableProductList == null) {
            loadProducts();
        }
        return mutableProductList;
    }

    public void setProducts(List<Product> productList) {
        this.mutableProductList.setValue(productList);
    }

    /*
     * Modify an item of shop's products
     * */
    public void itemRemovedFromCart(Product p) {
        List<Product> productList = new ArrayList<>(mutableProductList.getValue());
        for (Product product : productList) {
            if (product.getId().equals(p.getId())) {
                int index = productList.indexOf(product);
                product.setQuantity(0);
                productList.set(index, product);
                mutableProductList.setValue(productList);
                break;
            }
        }
//        mutableProductList.setValue(productList);
    }

    public boolean addItemToCart(Product p) {
        List<Product> productList = new ArrayList<>(mutableProductList.getValue());
        for (Product product : productList) {
            if (product.getId().equals(p.getId())) {
                int index = productList.indexOf(product);
                product.setQuantity(1);
                productList.set(index, product);
                mutableProductList.setValue(productList);
                break;
            }
        }
        return true;
    }

    private void loadProducts() {
        List<Product> productList = getListProduct();
        mutableProductList.setValue(productList);
    }

    private List<Product> getListProduct() {
        List<Product> list = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray = obj.getJSONArray("shoes");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject shoeObj = jsonArray.getJSONObject(i);
                String image = shoeObj.getString("image");
                String name = shoeObj.getString("name");
                String id = shoeObj.getString("id");
                String description = shoeObj.getString("description");
                String price = shoeObj.getString("price");
                String color = shoeObj.getString("color");
                Product product = new Product(id, image, name, description, price, color);
                list.add(product);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            String file = "res/raw/shoes.json"; // res/raw/test.txt also work.
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}

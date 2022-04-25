package com.example.GSneaker.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Product {
    private int quantity;
    private boolean isAddToCart;
    private String id;
    private String image;
    private String name;
    private String description;
    private String price;
    private String color;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product(String id, String image, String name, String description, String price, String color) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.description = description;
        this.price = price;
        this.color = color;
        this.quantity = 0;
    }

    public Product(String id, String image, String name, String description, String price, String color, int quantity) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.description = description;
        this.price = price;
        this.color = color;
        this.quantity = quantity;
    }

    public Product(Product p) {
        this.id = p.id;
        this.image = p.image;
        this.name = p.name;
        this.description = p.description;
        this.price = p.price;
        this.color = p.color;
        this.quantity = p.quantity;
    }

    public Product(Product p, int quantity) {
        this(p);
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isAddToCart() {
        return isAddToCart;
    }

    public void setAddToCart(boolean addToCart) {
        isAddToCart = addToCart;
    }

    public Product parseObject(String json) {
        Product product = null;
        try {
            JSONObject shoeObj = new JSONObject(json);
            String image = shoeObj.getString("image");
            String name = shoeObj.getString("name");
            String id = shoeObj.getString("id");
            String description = shoeObj.getString("description");
            String price = shoeObj.getString("price");
            String color = shoeObj.getString("color");
            int quantity = shoeObj.getInt("quantity");
            if (quantity > 0) product.isAddToCart = true;
            product = new Product(id, image, name, description, price, color, quantity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public String toString() {
        return "{" +
                "quantity=" + quantity +
                ", isAddToCart=" + isAddToCart +
                ", id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}

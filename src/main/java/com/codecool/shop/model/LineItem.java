package com.codecool.shop.model;

import com.codecool.shop.model.Product;

public class LineItem {
    private Product product;
    private int quantity;
    private float totalPrice;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float price) {
        this.totalPrice = price;
    }
}

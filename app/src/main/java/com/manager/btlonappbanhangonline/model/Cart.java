package com.manager.btlonappbanhangonline.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_tb")
public class Cart {
    @NonNull
    @PrimaryKey
    String idProduct;
    String nameProduct;
    String costProduct;
    String imageProduct;
    int quantity;

    public Cart() {
    }

    public Cart(String idProduct, String nameProduct, String costProduct, String imageProduct, int quantity) {
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.costProduct = costProduct;
        this.imageProduct = imageProduct;
        this.quantity = quantity;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getCostProduct() {
        return costProduct;
    }

    public void setCostProduct(String costProduct) {
        this.costProduct = costProduct;
    }

    public String getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(String imageProduct) {
        this.imageProduct = imageProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

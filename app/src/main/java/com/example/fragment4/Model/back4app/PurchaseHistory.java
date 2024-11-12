package com.example.fragment4.Model.back4app;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

@ParseClassName("PurchaseHistory")
public class PurchaseHistory extends ParseObject {

    private String objectId;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String userId) {
        this.user_id = userId;
    }

    public String getitem_name() {
        return item_name;
    }

    public void setitem_name(String item_name) {
        this.item_name = item_name;
    }

    public double getitem_price() {
        return item_price;
    }

    public void setitem_price(double item_price) {
        this.item_price = item_price;
    }

    public int getquantity() {
        return quantity;
    }

    public void setquantity(int quantity) {
        this.quantity = quantity;
    }

    public int getitem_image() {
        return item_image;
    }

    public void setitem_image(int item_image) {
        this.item_image = item_image;
    }

    public PurchaseHistory(String objectId, Date createdAt, Date updatedAt, String userId, String item_name, double item_price, int quantity, int item_image) {
        this.objectId = objectId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user_id = userId;
        this.item_name = item_name;
        this.item_price = item_price;
        this.quantity = quantity;
        this.item_image = item_image;
    }

    private Date createdAt;
    private Date updatedAt;
    private String user_id;
    private String item_name;
    private double item_price;
    private int quantity;
    private int item_image;

    // Getters and Setters for each field
    // ...
}

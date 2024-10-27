package com.example.fragment4.Model;

import java.io.Serializable;

public class HomeDataModel implements Serializable {
    private String text, des,price,image;



    public String getText() {
        return text;
    }

    public String getDes() {
        return des;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public HomeDataModel (String text, String des, String price, String image) {
        this.text = text;
        this.des = des;
        this.price = price;
        this.image = image;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }



}
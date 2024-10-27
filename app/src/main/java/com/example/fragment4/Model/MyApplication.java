package com.example.fragment4.Model;

import android.app.Application;

import com.example.fragment4.Model.Food;

import java.util.ArrayList;

public class MyApplication extends Application {
    private static MyApplication instance;
    private ArrayList<Food> cartList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;

    }
    public ArrayList<Food> getCartList() {
        return cartList;
    }

    public void setCartList(ArrayList<Food> cartList) {
        this.cartList = cartList;
    }





}
package com.example.fragment4;

import static android.content.Context.MODE_PRIVATE;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fragment4.Model.Food;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Fragment_Profile extends Fragment {

    private RecyclerView recyclerView;
    private HistoryCartAdapter adapter;
    private ArrayList<Food> historyList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        recyclerView = view.findViewById(R.id.hrvItemsInCart);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Fetch history data from your data source
        historyList = getPurchaseHistory();

        adapter = new HistoryCartAdapter(getContext(), historyList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_home, menu); // Assuming menu_home.xml defines options for Fragment_First
    }

    private ArrayList<Food> getCartListFromSharedPreferences() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("purchaseHistory", MODE_PRIVATE);
        String historyJson = sharedPref.getString("lastPurchase", "");

        ArrayList<Food> cartList = new ArrayList<>();
        if (!historyJson.isEmpty()) {
            Type type = new TypeToken<ArrayList<Food>>() {}.getType();
            cartList = new Gson().fromJson(historyJson, type);
        }

        return cartList;

//        return new ArrayList<>(); // Empty list if no purchase history found
    }

    private ArrayList<Food> getPurchaseHistory() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("purchaseHistory", MODE_PRIVATE);
        String historyJson = sharedPref.getString("lastPurchase", "");

        historyList = new Gson().fromJson(historyJson, new TypeToken<ArrayList<Food>>() {}.getType());
        return historyList;
    }

}
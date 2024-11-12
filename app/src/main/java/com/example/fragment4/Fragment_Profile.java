package com.example.fragment4;

import static android.content.Context.MODE_PRIVATE;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fragment4.Model.Food;

import com.example.fragment4.Model.back4app.PurchaseHistory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.ItemTouchHelper;

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

        // Fetch history data from Back4App
        fetchPurchaseHistoryFromBack4App();

        // if the below is enabled it shows purchases from local file
        // historyList = getPurchaseHistory();


        adapter = new HistoryCartAdapter(getContext(), historyList);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_home, menu); // Assuming menu_home.xml defines options for Fragment_First
    }


    private void fetchPurchaseHistoryFromBack4App() {
        historyList = new ArrayList<>();

        // Securely retrieve the anonymous user ID
        String anonymousUserId = getResources().getString(R.string.anonymous_user_id); // Replace with your secure storage method

        // Build ParseQuery for PurchaseHistory class
        ParseQuery<PurchaseHistory> query = ParseQuery.getQuery(PurchaseHistory.class);
        query.whereEqualTo("user_id", anonymousUserId);  // Filter by anonymous user ID

        query.findInBackground(new FindCallback<PurchaseHistory>() {
            @Override
            public void done(List<PurchaseHistory> objects, ParseException e) {
                if (e == null) {
                    historyList.clear();  // Clear existing data before populating
                    for (ParseObject purchaseObject : objects) {
                        String itemName = purchaseObject.getString("item_name");
                        double itemPrice = purchaseObject.getDouble("item_price");
                        int quantity = purchaseObject.getInt("quantity");
                        int itemImage = purchaseObject.getInt("item_image");  // assuming "item_image" is a String in your schema

                        // Create a new PurchaseHistory object with the extracted data

                        Food food = new Food(itemName, itemImage, "dump", itemPrice, quantity);

                        historyList.add(food);
                    }
                    adapter.notifyDataSetChanged();  // Update adapter with retrieved data
                } else {
                    Log.e("Fragment_Profile", "Error fetching purchase history: " + e.getMessage());
//                    Toast.makeText(getContext(), "Error fetching history!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private ArrayList<Food> getPurchaseHistory() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("purchaseHistory", MODE_PRIVATE);
        String historyJson = sharedPref.getString("lastPurchase", "");

        historyList = new Gson().fromJson(historyJson, new TypeToken<ArrayList<Food>>() {
        }.getType());
        return historyList;
    }


    private class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

        private HistoryCartAdapter adapter;

        public SwipeToDeleteCallback(HistoryCartAdapter adapter) {
            super(0, ItemTouchHelper.LEFT); // Only allow left swipes
            this.adapter = adapter;
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            // Not used in this case (no drag and drop functionality)
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            adapter.removeItem(position); // Remove item from data and adapter
        }

        @Override
        public int getDragDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            // Not used in this case (no drag and drop functionality)
            return 0;
        }
    }
}


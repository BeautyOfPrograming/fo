package com.example.fragment4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;


import com.example.fragment4.Model.Food;
import com.example.fragment4.Model.MyApplication;
import com.example.fragment4.Model.OnRemoveItemClickListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import android.util.Log; // Import for logging (optional)
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This activity represents the cart screen where users can view and manage their cart items.
 */
public class CartActivity extends AppCompatActivity implements OnRemoveItemClickListener {

    /**
     * A reference to the MyApplication class (if applicable).
     */
    private MyApplication app;

    /**
     * The list of Food objects representing the current cart items.
     */
    private ArrayList<Food> cartList;
    private ArrayList<Food> historyList;

    /**
     * The adapter for the RecyclerView displaying the cart items.
     */
    private CartAdapter adapter;

    private TextView totalOfItem;
    private TextView totalPrice;
    private View checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ParseObject firstObject = new ParseObject("FirstClass");
        firstObject.put("message", "Hey ! First message from android. Parse is now connected");
        firstObject.saveInBackground(e -> {
            if (e != null) {
                Log.e("MainActivity", e.getLocalizedMessage());
            } else {
                Log.d("MainActivity", "Object saved.");
            }
        });


        setContentView(R.layout.activity_card); // Set the layout for this activity
        totalOfItem = findViewById(R.id.txtTotalFee);
        totalPrice = findViewById(R.id.textView20);
        checkout = findViewById(R.id.button2);


        //Fetch the existing purchase history from SharedPreferences
        // historyList = getCartListFromSharedPreferences();
        // Get the cart list from shared preferences
        cartList = getCartListFromSharedPreferences();

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save cart as history
                historyList = cartList;
                saveCartAsHistory(cartList);

                // Additional actions (e.g., clear cart, navigate to confirmation)
            }
        });
        // Check if the cartList is not null and contains items
        if (cartList != null && !cartList.isEmpty()) {
            // Initialize the RecyclerView and set the CartAdapter
            RecyclerView recyclerView = findViewById(R.id.rvItemsInCart); // Replace with your RecyclerView ID
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new CartAdapter(this, cartList);
            adapter.setItemsTotalTextView(totalOfItem);
            adapter.setTotalPriceTextView(totalPrice);
            adapter.setOnRemoveItemClickListener(this);
            recyclerView.setAdapter(adapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback());
            itemTouchHelper.attachToRecyclerView(recyclerView);

            // Notify adapter of data change
            calculateTotals(cartList);
            adapter.notifyDataSetChanged();
        } else {
            // Handle the case where the cart is empty (e.g., display message or hide RecyclerView)
            Log.d("CartActivity", "Cart is empty."); // Optional logging for debugging
        }


    }

//    private ArrayList<Food> getPurchaseHistory() {
////
//
//    }

    private void saveCartAsHistory(ArrayList<Food> cartList) {
        if (cartList != null && !cartList.isEmpty()) {
            for (Food item : cartList) {
                ParseObject purchaseHistory = new ParseObject("PurchaseHistory");

                // Assuming you're using Parse User authentication
                String userId = "0";
                purchaseHistory.put("user_id", userId);

                purchaseHistory.put("item_name", item.getTitle());
                purchaseHistory.put("item_price", item.getFee());
                purchaseHistory.put("quantity", item.getNumberInCart());
                purchaseHistory.put("item_image", item.getPic() );

                purchaseHistory.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.d("CartActivity", "Purchase history saved successfully!");
                        } else {
                            Log.e("CartActivity", "Error saving purchase history: " + e.getMessage(), e);
                            Toast.makeText(CartActivity.this, "Error saving history to Back4App!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            // save to history sharedpreferences
            saveToHistory();
            // Clear the current cart after successful save
            cartList.clear();
            saveCartListToSharedPreferences(cartList);  // Save the empty cart
            adapter.notifyDataSetChanged();  // Update adapter to reflect empty cart
            Toast.makeText(this, "Cart saved as history", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Cart is already empty!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveToHistory() {

        // Get existing purchase history (if any)
        ArrayList<Food> existingHistory = getPurchaseHistory();

        // Create a new list to hold the complete history
        ArrayList<Food> completeHistory = new ArrayList<>();

        // Append current cart to history
        if (existingHistory != null) {
            completeHistory.addAll(existingHistory);
        }
        completeHistory.addAll(cartList);

        // Convert the complete history to JSON string
        String historyJson = new Gson().toJson(completeHistory);

        // Save the JSON string to purchaseHistory
        SharedPreferences sharedPref = getSharedPreferences("purchaseHistory", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("lastPurchase", historyJson);
        editor.apply();

    }

    private void calculateTotals(ArrayList<Food> cartList) {
        // Retrieve item data from your data source
        ArrayList<Food> cartLis = cartList;

        // Calculate item total
        double itemTotalPrice = 0;
        double totalofitem = 0;
        for (Food item : cartLis) {
            itemTotalPrice += item.getFee() * item.getNumberInCart();
            totalofitem += item.getNumberInCart();
        }


        // Update the UI
        totalOfItem.setText(totalofitem + "");
        totalPrice.setText(itemTotalPrice + "");
    }

    private double calculateDeliveryFee(double itemTotal) {
        // Adjust this logic based on your delivery fee criteria
        if (itemTotal > 100) {
            return 0; // Free delivery for orders over $100
        } else {
            return 5; // Fixed delivery fee
        }
    }

    private double calculateTax(double itemTotal) {
        // Adjust this logic based on your region's tax rate
        double taxRate = 0.08; // 8% tax rate
        return itemTotal * taxRate;
    }

    /**
     * Retrieves the cart list from shared preferences.
     *
     * @return An ArrayList of Food objects representing the cart items, or an empty list if none exist.
     */
    private ArrayList<Food> getCartListFromSharedPreferences() {

        SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        String cartListString = prefs.getString("cart_list", "");

        ArrayList<Food> cartList = new ArrayList<>();
        if (!cartListString.isEmpty()) {
            try {
                Type type = new TypeToken<ArrayList<Food>>() {
                }.getType();
                cartList = new Gson().fromJson(cartListString, type);
            } catch (Exception e) {
                Log.e("CartActivity", "Error getting cart list from shared preferences: " + e.getMessage());
            }
        }

        return cartList;
    }

    /**
     * Saves the current cart list to shared preferences.
     *
     * @param cartList The ArrayList of Food objects representing the cart items.
     */
    private void saveCartListToSharedPreferences(ArrayList<Food> cartList) {
        SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String cartListString = new Gson().toJson(cartList);
        editor.putString("cart_list", cartListString);
        editor.apply();
    }


    private ArrayList<Food> getPurchaseHistory() {
        SharedPreferences sharedPref = getSharedPreferences("purchaseHistory", MODE_PRIVATE);
        String historyJson = sharedPref.getString("lastPurchase", "");

        ArrayList<Food> historyList = new Gson().fromJson(historyJson, new TypeToken<ArrayList<Food>>() {
        }.getType());
        return historyList;
    }

    /**
     * Handles the removal of an item from the cart. (Implementation required)
     *
     * @param position The position of the removed item in the cart list.
     */
    @Override
    public void onRemoveItem(int position) {
        // Implement logic to remove the item from cartList and update the adapter
        cartList.remove(position);
        adapter.notifyItemRemoved(position); // Notify adapter about data change

        // Recalculate totals and update UI
        calculateTotals(cartList);
        // Update shared preferences with the modified cart list
        saveCartListToSharedPreferences(cartList);
    }

    private class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

        public SwipeToDeleteCallback() {
            super(0, ItemTouchHelper.LEFT); // Only allow left swipes
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            // Not used in this case (no drag and drop functionality)
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            onRemoveItem(position); // Call your existing onRemoveItem method
        }

        @Override
        public int getDragDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            // Not used in this case (no drag and drop functionality)
            return 0;
        }
    }

}
package com.example.fragment4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fragment4.Model.Food;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;


import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * This activity displays details of a food item and allows adding it to the cart.
 *
 */
public class DetailActivity extends AppCompatActivity {

    private ImageView detailImage;
    private TextView detailTitle, detailPrice, detailDescription, detailNum;
    private int currentNum = 1; // Initial quantity
    private Button btnAddToCart;
    private ArrayList<Food> cartList; // List to store cart items (consider using Application class)
    private View btnGotoCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        detailImage = findViewById(R.id.imgFoodDetails);
        detailTitle = findViewById(R.id.txtFoodName); // Assuming txtFoodName exists in your layout
        detailPrice = findViewById(R.id.txtFoodPrice); // Assuming txtFoodPrice exists in your layout
        detailDescription = findViewById(R.id.txtDescription);
        detailNum = findViewById(R.id.txtNum); // Assuming txtNum exists in your layout
        cartList = new ArrayList<>();  // Initialize cart list

        // Handle clicking on "Add to Cart" button
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add item to cart logic here
                String title = detailTitle.getText().toString();
                double price = Double.parseDouble(detailPrice.getText().toString());
                String image = getIntent().getStringExtra("image"); // Assuming image URL is passed through Intent
                int quantity = Integer.parseInt(detailNum.getText().toString());

                // Get the existing cart list from shared preferences
                cartList = getCartListFromSharedPreferences();

                // Add the new item to the cart list
                cartList.add(new Food(title, R.drawable.pop_1, detailDescription.getText().toString(), price, quantity));

                // Save the updated cart list to shared preferences
                saveCartListToSharedPreferences(cartList);

                // (Optional) Update the cart list in the Application class (if implemented)

                Toast.makeText(getApplicationContext(), title + " به سبد خرید اضافه شد", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle clicking on "Go to Cart" button
        btnGotoCard = findViewById(R.id.btnGoToCart);
        btnGotoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the CartActivity
                Intent intent = new Intent(DetailActivity.this, CartActivity.class);

                // Consider passing the cart list data using a more efficient method than Serializable (e.g., Parcelable)
                intent.putExtra("cartList", cartList);
                startActivity(intent);

                // (Optional) Clear the temporary cart list in this activity
                // cartList.clear();
            }
        });

        // Get the Intent that started this activity
        Intent intent = getIntent();

        // Check if the Intent contains the expected data (optional)
        if (intent.hasExtra("image") && intent.hasExtra("description")
                && intent.hasExtra("title") && intent.hasExtra("price")) {

            // Retrieve data from Intent extras


            String description = intent.getStringExtra("description");
            String title = intent.getStringExtra("title");
            String price = intent.getStringExtra("price");


       
            // Get the image resource ID from the Intent
            int imageResourceId = getIntent().getIntExtra("image", -1); // Get the resource ID (or -1 if not found)
            // Convert the resource ID to a URI
            Uri imageUri = Uri.parse("android.resource://" + getPackageName() + "/" + imageResourceId);
            // Load the image using Picasso
            Picasso.get().load(imageUri).into(detailImage);


            detailTitle.setText(title);
            detailPrice.setText(price);
            detailDescription.setText(description);
            detailNum.setText(String.valueOf(currentNum)); // Set initial quantity

            // Implement button click listeners (similar to FoodDetailsFragment)
            findViewById(R.id.btnPlus).setOnClickListener(v -> {
                currentNum++;
                detailNum.setText(String.valueOf(currentNum));
            });

            findViewById(R.id.btnMin).setOnClickListener(v -> {
                if (currentNum > 1) {
                    currentNum--;
                }
                detailNum.setText(String.valueOf(currentNum));
            });

        } else {
            // Handle the case where data is missing (optional)
            Toast.makeText(this, "Error: Data not found!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Retrieves the cart list from shared preferences.
     *
     * @return The ArrayList of Food objects representing the cart items.
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
                e.printStackTrace();
            }
        }
        return cartList;
    }

    /**
     * Saves the cart list to shared preferences.
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
}
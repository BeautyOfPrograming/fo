package com.example.fragment4;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.getIntent;

import android.content.Context;


import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;


import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragment4.Model.Food;
import com.example.fragment4.Model.OnRemoveItemClickListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.ItemTouchHelper;

/**
 * This class represents an adapter for displaying cart items in a RecyclerView.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> implements OnRemoveItemClickListener {

    /**
     * The context of the activity or fragment using this adapter.
     */
    private Context context;

    /**
     * The list of Food objects representing the cart items.
     */
    private ArrayList<Food> data;

    private TextView itemsTotalTextView;
    private TextView totalPriceTextView;

    public void setItemsTotalTextView(TextView itemsTotalTextView) {
        this.itemsTotalTextView = itemsTotalTextView;
    }

    public void setTotalPriceTextView(TextView totalPriceTextView) {
        this.totalPriceTextView = totalPriceTextView;
    }

    /**
     * An interface to handle item removal events from the adapter.
     */
    private OnRemoveItemClickListener onRemoveItemClickListener;

    /**
     * Sets the on-remove-item click listener for the adapter.
     *
     * @param listener The listener to be notified when an item is removed.
     */
    public void setOnRemoveItemClickListener(OnRemoveItemClickListener listener) {
        this.onRemoveItemClickListener = listener;
    }

    /**
     * Constructor for the CartAdapter.
     *
     * @param context The context of the activity or fragment.
     * @param data    The list of Food objects representing cart items.
     */
    public CartAdapter(Context context, ArrayList<Food> data) {
        this.context = context;
        this.data = data;
    }

    /**
     * Creates a new CartViewHolder instance by inflating the cart item layout.
     *
     * @param parent   The ViewGroup where the inflated view will be added.
     * @param viewType The view type for the current position.
     * @return A new CartViewHolder instance.
     */
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    /**
     * Binds the data for a specific cart item to the corresponding view holder.
     *
     * @param holder   The CartViewHolder instance to bind data to.
     * @param position The position of the item in the data list.
     */
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Food food = data.get(position);
        holder.txtTitleCart.setText(food.getTitle());
//        holder.picCart.setImageResource(food.getPic());
        Log.e("HH", food.getPic() + "");
        holder.feeEachItem.setText(String.valueOf(food.getFee()));
        holder.totalEachItem.setText(String.valueOf(food.getFee() * food.getNumberInCart())); // Assuming total is same as fee for now
        holder.numItems.setText(String.valueOf(food.getNumberInCart()));


        int imageResourceId = food.getPic(); // Get the resource ID (or -1 if not found)
        // Convert the resource ID to a URI
        Uri imageUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + imageResourceId);
        // Load the image using Picasso
        Picasso.get().load(imageUri).into(holder.picCart);

        holder.plusBtnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Food food = data.get(position);
                food.setNumberInCart(food.getNumberInCart() + 1);
                data.set(position, food);
                notifyItemChanged(position);
                updateTotal();
            }
        });
        holder.minBtnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Food food = data.get(position);
                if (food.getNumberInCart() > 1) {
                    food.setNumberInCart(food.getNumberInCart() - 1);
                    data.set(position, food);
                    notifyItemChanged(position);
                    updateTotal();
                } else {
                    // Handle removing the item if quantity reaches 0
                    onRemoveItem(position);
                }
            }
        });
    }

    private void updateTotal() {
        int totalItems = 0;
        double totalPrice = 0.0;
        for (Food item : data) {
            totalItems += item.getNumberInCart();
            totalPrice += item.getFee() * item.getNumberInCart();
        }

        if (itemsTotalTextView != null) {
            itemsTotalTextView.setText("Items Total: " + totalItems);
        }

        if (totalPriceTextView != null) {
            totalPriceTextView.setText("Total: $" + totalPrice);
        }

        // Update shared preferences
        saveCartListToSharedPreferences(data);
    }

    /**
     * Returns the total number of items in the cart data list.
     *
     * @return The number of items in the cart.
     */
    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * Handles the removal of an item from the cart data list and updates the adapter.
     *
     * @param position The position of the item to be removed.
     */
    @Override
    public void onRemoveItem(int position) {
        data.remove(position);
        notifyItemRemoved(position); // Notify adapter about data change

        // Recalculate Items Total
        int totalItems = 0;
        for (Food item : data) {
            totalItems += item.getNumberInCart();
        }

        // Recalculate Total Price
        double totalPrice = 0.0;
        for (Food item : data) {
            totalPrice += item.getFee() * item.getNumberInCart();
        }

        // Update text views
        if (itemsTotalTextView != null) {
            itemsTotalTextView.setText("Items Total: " + totalItems);
        }

        if (totalPriceTextView != null) {
            totalPriceTextView.setText("Total: $" + totalPrice);
        }

        // Update shared preferences
        saveCartListToSharedPreferences(data);
    }

    /**
     * Saves the cart list to shared preferences for persistence.
     *
     * @param cartList The list of Food objects representing the cart items.
     */
    private void saveCartListToSharedPreferences(ArrayList<Food> cartList) {
        SharedPreferences prefs = context.getSharedPreferences("my_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String cartListString = new Gson().toJson(cartList);
        editor.putString("cart_list", cartListString);
        editor.apply();
    }

    /**
     * The ViewHolder class for the CartAdapter.
     */
    public class CartViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitleCart;
        public ImageView picCart;
        public TextView feeEachItem;
        public TextView totalEachItem;
        public TextView numItems;
        public ImageView minBtnCart;
        public ImageView plusBtnCart;

        public CartViewHolder(View itemView) {
            super(itemView);
            txtTitleCart = itemView.findViewById(R.id.txtTitleCart);
            picCart = itemView.findViewById(R.id.picCart);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            numItems = itemView.findViewById(R.id.numItems);
            minBtnCart = itemView.findViewById(R.id.minBtnCart);
            plusBtnCart = itemView.findViewById(R.id.plusBtnCart);
        }
    }


}
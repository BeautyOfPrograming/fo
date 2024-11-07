package com.example.fragment4;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragment4.Model.Food;
import com.example.fragment4.Model.OnRemoveItemClickListener;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * This class represents an adapter for displaying cart items in a RecyclerView.
 */
public class HistoryCartAdapter extends RecyclerView.Adapter<HistoryCartAdapter.CartViewHolder>  {

    /**
     * The context of the activity or fragment using this adapter.
     */
    private Context context;

    /**
     * The list of Food objects representing the cart items.
     */
    private ArrayList<Food> data;
    private ArrayList<Food> historyList;

    private TextView itemsTotalTextView;
    private TextView totalPriceTextView;

    public void removeItem(int position) {
        historyList.remove(position);
        notifyItemRemoved(position); // Notify adapter about data change
    }
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
     * @param data The list of Food objects representing cart items.
     */
    public HistoryCartAdapter(Context context, ArrayList<Food> data) {
        this.context = context;
        this.data = data;
        this.historyList = data;
    }

    /**
     * Creates a new CartViewHolder instance by inflating the cart item layout.
     *
     * @param parent The ViewGroup where the inflated view will be added.
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
     * @param holder The CartViewHolder instance to bind data to.
     * @param position The position of the item in the data list.
     */
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Food food = data.get(position);
        holder.txtTitleCart.setText(food.getTitle());
        holder.picCart.setImageResource(food.getPic());
        holder.feeEachItem.setText(String.valueOf(food.getFee()));
        holder.totalEachItem.setText(String.valueOf(food.getFee() * food.getNumberInCart())); // Assuming total is same as fee for now
        holder.numItems.setText(String.valueOf(food.getNumberInCart()));

    }

    /**
     * Returns the total number of items in the cart data list.
     *
     * @return The number of items in the cart.
     */
    @Override
    public int getItemCount() {
        return historyList.size();
    }

    /**
     * Handles the removal of an item from the cart data list and updates the adapter.
     *
     * @param position The position of the item to be removed.
     */

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
        public ImageView remove;

        public CartViewHolder(View itemView) {
            super(itemView);
            txtTitleCart = itemView.findViewById(R.id.txtTitleCart);
            picCart = itemView.findViewById(R.id.picCart);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            numItems = itemView.findViewById(R.id.numItems);
            remove = itemView.findViewById(R.id.minBtnCart);
        }
    }
}
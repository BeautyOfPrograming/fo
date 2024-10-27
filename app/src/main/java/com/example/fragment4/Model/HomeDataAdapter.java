package com.example.fragment4.Model;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fragment4.DetailActivity;
import com.squareup.picasso.Picasso;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragment4.R;

import java.util.List;
public class HomeDataAdapter extends RecyclerView.Adapter<HomeDataAdapter.ViewHolder> {

    private List<HomeDataModel> dataList;
    private Context context;


    public HomeDataAdapter(List<HomeDataModel> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeDataModel item = dataList.get(position);
        holder.textView.setText(item.getText());
        holder.des.setText(item.getDes());
        holder.price.setText(" هزار تومان" + item.getPrice());

        Picasso.get().load(item.getImage()).into(holder.id_product_image);

        // ... other view holder setup

        // Set click listener on the item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the clicked item position
                int clickedPosition = holder.getAdapterPosition();

                // Retrieve data for the clicked item
                HomeDataModel clickedItem = dataList.get(clickedPosition);

                // Use public methods to access data
                String image = clickedItem.getImage();
                String title = clickedItem.getText();
                String price = clickedItem.getPrice();
                String description = clickedItem.getDes();

                // Intent to navigate to DetailActivity
                Intent intent = new Intent(context, DetailActivity.class);

                // Put data in the Intent (optional, but recommended for complex objects)
                intent.putExtra("image", image); // Send image URL
                intent.putExtra("title", title); // Send image URL
                intent.putExtra("price", price); // Send image URL
                intent.putExtra("description", description); // Send description

                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private TextView des;
        private TextView price;
        private ImageView id_product_image, id_product_add_tocard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.id_product_name);
            des = itemView.findViewById(R.id.id_product_description);
            price = itemView.findViewById(R.id.id_product_price);
            id_product_image = itemView.findViewById(R.id.id_product_image);
            id_product_add_tocard = itemView.findViewById(R.id.id_product_add_tocard);
        }
    }
}
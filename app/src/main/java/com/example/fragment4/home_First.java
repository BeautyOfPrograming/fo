package com.example.fragment4;

import static android.app.PendingIntent.getActivity;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fragment4.Model.HomeDataAdapter;
import com.example.fragment4.Model.HomeDataModel;

import java.util.ArrayList;
import java.util.List;
// previouse images,detail image,image in history,persian text,down screen,,image at the same size
// 
public class home_First extends Fragment {

    private RecyclerView recyclerView;
    private HomeDataAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        // Find the RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);

        // Create a Layout Manager for horizontal scrolling
       // recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Populate data (replace with your actual data fetching logic)
        List<HomeDataModel> dataList = new ArrayList<>();
        int imageResourceId1 = R.drawable.pizza1;
        int imageResourceId2 = R.drawable.chickenkebab2;
        int imageResourceId3 = R.drawable.meqo7;
        int imageResourceId4 = R.drawable.chicken4;
        int imageResourceId5 = R.drawable.eggs5;
        int imageResourceId6 = R.drawable.sandiwich6;
        int imageResourceId7 = R.drawable.soya8;
        int imageResourceId8 = R.drawable.rosted3;
        dataList.add(new HomeDataModel("پیتزا", "پیتزا خانواده متوسط", "45", imageResourceId1));
        dataList.add(new HomeDataModel("کباب", "تک نفره", "55",  imageResourceId2));
        dataList.add(new HomeDataModel("میگو", " غذای دریایی خزر", "15",imageResourceId3 ));
        dataList.add(new HomeDataModel("مرغ تازه", " غذای خوشمزه", "09", imageResourceId4));
        dataList.add(new HomeDataModel("تخم مرغ", " غذای نگو", "10", imageResourceId5));
        dataList.add(new HomeDataModel("ساندویچ", " وای ازمزش", "16", imageResourceId6));
        dataList.add(new HomeDataModel("سویا", " غذای به به", "18", imageResourceId7));
        dataList.add(new HomeDataModel("کباب بریان", " غدای", "15", imageResourceId8));

//        https://parade.com/.image/c_limit%2Ccs_srgb%2Cfl_progressive%2Cq_auto:good%2Cw_700/MTkwNTgxMjE5MjE2NDY3ODM2/salmon-citrus-skewers.jpg
        // Create and set the adapter
        adapter = new HomeDataAdapter(dataList,getContext());
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_home, menu); // Assuming menu_home.xml defines options for Fragment_First
    }
}
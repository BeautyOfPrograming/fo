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
        dataList.add(new HomeDataModel("پیتزا", "پیتزا خانواده متوسط", "45", "https://th.bing.com/th/id/OIP.qkUEGyxIRxgvP4E60NjdsgHaG0?rs=1&pid=ImgDetMain"));
        dataList.add(new HomeDataModel("کباب", "تک نفره", "55", "https://www.deliciousmeetshealthy.com/wp-content/uploads/2018/06/Mediterranean-Chicken-Kebabs-1.jpg"));
        dataList.add(new HomeDataModel("میگو", " غذای دریایی خزر", "15", "https://th.bing.com/th/id/R.f273cc472f9c5d106091befac0534041?rik=zixY0vsBS2hujA&riu=http%3a%2f%2fharfetaze.com%2fwp-content%2fuploads%2f2018%2f01%2fmeigoo-2.jpg&ehk=uzeg%2bqrdZI%2bxVMOYn6JMEzJu2FX%2ffWXav4kzA1SjWfM%3d&risl=&pid=ImgRaw&r=0"));
        dataList.add(new HomeDataModel("مرغ تازه", " غذای خوشمزه", "09", "https://th.bing.com/th/id/OIP.81wUmwhNaxQdzOmX5f374AHaE8?rs=1&pid=ImgDetMain"));
        dataList.add(new HomeDataModel("تخم مرغ", " غذای نگو", "10", "https://th.bing.com/th/id/R.43a2b355cc7e5388c5a232347142ba84?rik=wSPuTQEaN1oyAA&pid=ImgRaw&r=0"));
        dataList.add(new HomeDataModel("ساندویچ", " وای ازمزش", "16", "https://th.bing.com/th/id/R.50805909493317e78c5c099d59797c82?rik=5Rb21NiaB%2fis8A&pid=ImgRaw&r=0"));
        dataList.add(new HomeDataModel("سویا", " غذای به به", "18", "https://rasekhoon.net/_files/images/article/5d4da426-08ae-41f8-8703-4538585eb08d.jpg"));
        dataList.add(new HomeDataModel("کباب بریان", " غدای", "15", "https://th.bing.com/th/id/OIP._FZjxy8E4dWRqYjU9IDVUQHaHa?w=1060&h=1060&rs=1&pid=ImgDetMain"));

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
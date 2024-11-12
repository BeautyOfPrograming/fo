package com.example.fragment4;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.HashMap;
import java.util.Map;
// home show main food in a recyvleview
// detail page
// add to baseket
// buy zarinpal whole data with out registeration
//---------------- choose color pngs, change color of navigation and card, take recycle view height to bottom nav
// previous images,detail image,image in history,persian text,down screen,,image at the same size,use local save data when purchasing is done
//  most delicious food for all people
public class MainActivity extends AppCompatActivity {

    private FrameLayout container;
    private BottomNavigationView bottomNavigationView;

    private Fragment fragmentHome, fragmentSecond, fragmentThird;

    public static final int NAVIGATION_HOME = R.id.navigation_home;
    public static final int NAVIGATION_FAVORITE = R.id.navigation_favourite;
    public static final int NAVIGATION_PROFILE = R.id.navigation_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        container = findViewById(R.id.container);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        fragmentHome = new homeOfFood();
        fragmentSecond = new Fragment_favourite();
        fragmentThird = new Fragment_Profile();  // Assuming Fragment_Profile is for the profile

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    // Create a map to associate menu item IDs with their corresponding fragments
                    Map<Integer, Fragment> fragmentMap = new HashMap<>();
                    fragmentMap.put(NAVIGATION_HOME, fragmentHome);
                    fragmentMap.put(NAVIGATION_FAVORITE, fragmentSecond);
                    fragmentMap.put(NAVIGATION_PROFILE, fragmentThird);

                    // Get the fragment associated with the selected item
                    Fragment fragment = fragmentMap.get(item.getItemId());

                    // If a fragment is found, replace it
                    if (fragment != null) {
                        replaceFragment(fragment);
                        return true;
                    }

                    // If no fragment is found, return false
                    return false;
                }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }
}
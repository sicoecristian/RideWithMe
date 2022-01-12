package com.app.ridewithme;

import android.os.Bundle;
import android.view.MenuItem;

import com.app.ridewithme.fragments.AddARide;
import com.app.ridewithme.fragments.AvailableRides;
import com.app.ridewithme.fragments.MyAccount;
import com.app.ridewithme.fragments.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import com.app.ridewithme.databinding.ActivityLogedInScreenBinding;

public class LogedInScreenActivity extends AppCompatActivity {

    private ActivityLogedInScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLogedInScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(navListener);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

//
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
       // NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_loged_in_screen);
       // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
       // NavigationUI.setupWithNavController(binding.navView, navController);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            selectedFragment = new AvailableRides();
                            break;
                        case R.id.navigation_addA_ride:
                            selectedFragment = new AddARide();
                            break;
                        case R.id.navigation_notifications:
                            selectedFragment = new NotificationsFragment();
                            break;
                        case R.id.navigation_accountInfo:
                            selectedFragment = new MyAccount();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

                    return true;
                }
            };

}
package com.example.physicalterms;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.physicalterms.fragments.DefinitionFragment;
import com.example.physicalterms.fragments.FormulaFragment;
import com.example.physicalterms.fragments.TaskFragment;
import com.example.physicalterms.fragments.TermFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView main_bottom_menu;
    private NavDestination currentVisibleFragment;
    private NavHostFragment navHostFragment;
    private NavController navController;
    // Layout Manager
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    // Linear Layout Manager
    LinearLayoutManager HorizontalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.setLocale(this);
        MainActivity ma = this;
        if(!App.isIsLanguageSelected()) {
            startActivity(new Intent(MainActivity.this, WelcomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
        else {
            setContentView(R.layout.activity_main);
            setUpNavigation();
        }
    }

    void setUpNavigation() {
        MainActivity ma = this;
        main_bottom_menu = findViewById(R.id.bottomNavigationView);
        main_bottom_menu.setVisibility(View.VISIBLE);
        navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            NavigationUI.setupWithNavController(main_bottom_menu,
                    (navHostFragment).getNavController());
        }

        navController = Objects.requireNonNull(navHostFragment).getNavController();
        currentVisibleFragment = navController.getCurrentDestination();
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                Log.e(Constants.APP_TAG, "onDestinationChanged: " + destination.getLabel());
                //отслеживания фпагмента на главном экране
                currentVisibleFragment = destination;
            }
        });
    }

}
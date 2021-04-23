package com.hartonostudio.donasiappsmadania.FiturDonasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.hartonostudio.donasiappsmadania.Menu.Home.HomeFragment;
import com.hartonostudio.donasiappsmadania.Menu.Notification.NotifictionFragment;
import com.hartonostudio.donasiappsmadania.Menu.Program.ProgramFragment;
import com.hartonostudio.donasiappsmadania.R;
import com.hartonostudio.donasiappsmadania.midtrans.Midtrans;

import java.util.Objects;

import nl.joery.animatedbottombar.AnimatedBottomBar;



    public class MainActivity extends AppCompatActivity {


        private static final String TAG = MainActivity.class.getSimpleName();

        private Toolbar toolbar;
        AnimatedBottomBar animatedBottomBar;
        FragmentManager fragmentManager;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            setToolbar();
            initViews(savedInstanceState);
            initComponentsNavHeader();
        }

        private void setToolbar() {
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setTitle(0);
        }

        @SuppressLint("NonConstantResourceId")
        private void initViews(Bundle savedInstanceState) {
            /**
             * Menu Bottom Navigation Drawer
             * */


            animatedBottomBar.setOnTabSelectListener((lastIndex, lastTab, newIndex, newTab) -> {
                Fragment fragment = null;
                switch (newTab.getId()) {
                    case R.id.navigation_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.navigation_program:
                        fragment = new ProgramFragment();
                        break;
                    case R.id.navigation_notifications:
                        fragment = new NotifictionFragment();
                        break;
                    case R.id.navigation_profil:
                        fragment = new ProgramFragment();
                        break;
                }

                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
                            .commit();
                } else {
                    Log.e(TAG, "Error in creating Fragment");
                }
            });

            /**
             * Menu Navigation Drawer
             **/
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.setDrawerIndicatorEnabled(false);
            toggle.setToolbarNavigationClickListener(view -> drawer.openDrawer(GravityCompat.START));
            toggle.setHomeAsUpIndicator(R.drawable.ic_drawer);
            toggle.syncState();
        }

        private void initComponentsNavHeader(){
            NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setItemIconTintList(null); //disable tint on each icon to use color icon svg
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_weeds:
                            Pesan("Menu Weeds");
                            break;
                        case R.id.nav_insects:
                            Pesan("Menu Insects");
                            break;
                        case R.id.nav_diseases:
                            Pesan("Menu Diseases");
                            break;
                        case R.id.nav_products:
                            Pesan("Menu Products");
                            break;
                        case R.id.nav_help:
                            Pesan("Menu Help");
                            break;
                    }

                    DrawerLayout drawer = findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }

                private void Pesan(String pesan) {
                    Toast.makeText(MainActivity.this, pesan, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onBackPressed() {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

}


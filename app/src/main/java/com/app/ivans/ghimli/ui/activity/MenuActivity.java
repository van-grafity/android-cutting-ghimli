package com.app.ivans.ghimli.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewbinding.ViewBinding;

import com.app.ivans.ghimli.databinding.ActivityMenuBinding;
import com.app.ivans.ghimli.ui.fragment.AboutFragment;
import com.app.ivans.ghimli.ui.fragment.HomeFragmentInterface;
import com.app.ivans.ghimli.R;
import com.app.ivans.ghimli.base.BaseActivity;
import com.app.ivans.ghimli.net.API;
import com.app.ivans.ghimli.ui.fragment.CutPieceStockFragment;
import com.app.ivans.ghimli.ui.fragment.CutterFragment;
import com.app.ivans.ghimli.ui.fragment.HomeFragment;
import com.app.ivans.ghimli.ui.fragment.LayerFragment;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.BuildConfig;
import de.hdodenhof.circleimageview.CircleImageView;

public class MenuActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MenuActivity";
    private ActivityMenuBinding binding;
    private CircleImageView circleImageView;

    private Fragment currentFragment = null;

    private HomeFragmentInterface homeFragmentInterface = null;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;

    @NonNull
    @Override
    protected ViewBinding createViewBinding(LayoutInflater layoutInflater) {
        binding = ActivityMenuBinding.inflate(layoutInflater);
        return binding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "onCreate: DEBUG");
        }
        drawer = binding.drawerLayout;
        Toolbar toolbar = binding.included.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawer.addDrawerListener(toggle);
        binding.navView.setNavigationItemSelectedListener(this);

        View headerContainer = binding.navView.getHeaderView(0);
        circleImageView = headerContainer.findViewById(R.id.profile_image);
//        circleImageView.setOnClickListener(this);
        TextView userName = headerContainer.findViewById(R.id.nameOfUser);
        userName.setText(API.currentUser(MenuActivity.this).getName());
        TextView userEmail = headerContainer.findViewById(R.id.emailOfUser);
        userEmail.setText(API.currentUser(MenuActivity.this).getEmail());

        if (savedInstanceState == null) {
            Fragment fragment = HomeFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_container, fragment);
            ft.commit();
            currentFragment = fragment;
            binding.navView.setCheckedItem(R.id.nav_home);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (menuItem.isChecked()) {
            menuItem.setChecked(false);
        } else {
            menuItem.setChecked(true);
        }
        if (id == R.id.nav_home) {
//            clearStack();
            getSupportActionBar().setTitle("Home");
            Fragment homeFragment = HomeFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_container, homeFragment);
            ft.commit();
            currentFragment = homeFragment;
            menuItem.setChecked(true);
        } else if (id == R.id.nav_layer) {
            getSupportActionBar().setTitle("Layer");
            Fragment layerFragment = LayerFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_container, layerFragment);
            ft.commit();
            currentFragment = layerFragment;
//            startActivity(new Intent(MenuActivity.this, HomeActivity.class));
            menuItem.setChecked(true);
        } else if (id == R.id.nav_cutter) {
            getSupportActionBar().setTitle("Cutter");
            Fragment cutterFragment = CutterFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_container, cutterFragment);
            ft.commit();
            currentFragment = cutterFragment;
            menuItem.setChecked(true);
        } else if (id == R.id.nav_cut_piece_stock) {
            getSupportActionBar().setTitle("Cut Piece Stock");
            Fragment cutPieceStockFragment = CutPieceStockFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_container, cutPieceStockFragment);
            ft.commit();
            currentFragment = cutPieceStockFragment;
            menuItem.setChecked(true);
        } else if (id == R.id.nav_about) {
            getSupportActionBar().setTitle("Tentang");
            Fragment aboutFragment = AboutFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_container, aboutFragment);
            ft.commit();
            currentFragment = aboutFragment;
            menuItem.setChecked(true);
        } else if (id == R.id.nav_logout) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage(R.string.want_to_logout)
                    .setPositiveButton(R.string.ok, (dialog, which) -> {
                        API.logOut(MenuActivity.this);
                        Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton(R.string.cancel, null)
                    .show();
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.grey_dark));
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.grey_dark));
        }
        binding.navView.setCheckedItem(id);
        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    
    private void clearStack() {
        int backStackEntry = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntry > 0) {
            for (int i = 0; i < backStackEntry; i++) {
                getSupportFragmentManager().popBackStackImmediate();
            }
        }

        if (getSupportFragmentManager().getFragments() != null && getSupportFragmentManager().getFragments().size() > 0) {
            for (int i = 0; i < getSupportFragmentManager().getFragments().size(); i++) {
                Fragment mFragment = getSupportFragmentManager().getFragments().get(i);
                if (mFragment != null && mFragment.isAdded()) {
                    getSupportFragmentManager().beginTransaction().remove(mFragment).commit();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            closeApplication();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    private void closeApplication() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(R.string.want_to_exit)
                .setPositiveButton(R.string.ok, (dialog, which) -> finish())
                .setNegativeButton(R.string.cancel, null)
                .show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.grey_dark));
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.grey_dark));
    }
}
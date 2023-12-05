package com.app.ivans.ghimli;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewbinding.ViewBinding;

import com.app.ivans.ghimli.base.BaseActivity;
import com.app.ivans.ghimli.databinding.ActivityMenuBinding;
import com.app.ivans.ghimli.net.API;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
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
        Toolbar toolbar = binding.included.toolbar;
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        drawer = binding.drawerLayout;
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {

            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                view.bringToFront();
                view.requestLayout();
            }
            
            @Override
            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
            }
        };
        toggle.setDrawerIndicatorEnabled(true);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawer.isDrawerOpen(GravityCompat.START)){
                    drawer.closeDrawer(GravityCompat.START);
                }else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);

        View headerContainer = binding.navView.getHeaderView(0);
        circleImageView = headerContainer.findViewById(R.id.profile_image);
//        circleImageView.setOnClickListener(this);
        TextView userName = headerContainer.findViewById(R.id.nameOfUser);
        userName.setText(API.currentUser(MenuActivity.this).getName());
        TextView userEmail = headerContainer.findViewById(R.id.emailOfUser);
        userEmail.setText(API.currentUser(MenuActivity.this).getEmail());

        Fragment fragment = HomeFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_container, fragment);
        ft.commit();
        currentFragment = fragment;

        // binding.navView.setCheckedItem(R.id.nav_home);
        this.getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                if (currentFragment instanceof HomeFragment) {
                    binding.navView.setCheckedItem(R.id.nav_home);
                    getSupportActionBar().setTitle("Home");
                } else if (currentFragment instanceof LayerFragment) {
                    binding.navView.setCheckedItem(R.id.nav_layer);
                    getSupportActionBar().setTitle("Layer");
                } else if (currentFragment instanceof CutterFragment) {
                    binding.navView.setCheckedItem(R.id.nav_cutter);
                    getSupportActionBar().setTitle("Cutter");
                } else if (currentFragment instanceof CutPieceStockFragment) {
                    binding.navView.setCheckedItem(R.id.nav_cut_piece_stock);
                    getSupportActionBar().setTitle("Cut Piece Stock");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
            clearStack();
            getSupportActionBar().setTitle("Home");
            Fragment homeFragment = HomeFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_container, homeFragment);
            ft.commit();
            currentFragment = homeFragment;
            menuItem.setChecked(true);
        } else if (id == R.id.nav_layer) {
            clearStack();
            getSupportActionBar().setTitle("Layer");
            Fragment layerFragment = LayerFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_container, layerFragment);
            ft.commit();
            currentFragment = layerFragment;
            menuItem.setChecked(true);
        } else if (id == R.id.nav_cutter) {
            clearStack();
            getSupportActionBar().setTitle("Cutter");
            Fragment cutterFragment = CutterFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_container, cutterFragment);
            ft.commit();
            currentFragment = cutterFragment;
            menuItem.setChecked(true);

        } else if (id == R.id.nav_cut_piece_stock) {
            clearStack();
            getSupportActionBar().setTitle("Cut Piece Stock");
            Fragment cutPieceStockFragment = CutPieceStockFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_container, cutPieceStockFragment);
            ft.commit();
            currentFragment = cutPieceStockFragment;
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

    // onPostCreate
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
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
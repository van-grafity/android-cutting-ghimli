package com.app.ivans.ghimli.ui.activity;

import static com.app.ivans.ghimli.utils.Extension.isWifiAlertEnabled;
import static com.app.ivans.ghimli.utils.Extension.setCustomePositionView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewbinding.ViewBinding;

import com.app.ivans.ghimli.MainActivity;
import com.app.ivans.ghimli.R;
import com.app.ivans.ghimli.base.BaseActivity;
import com.app.ivans.ghimli.databinding.ActivityMenuBinding;
import com.app.ivans.ghimli.model.BodyNotification;
import com.app.ivans.ghimli.net.API;
import com.app.ivans.ghimli.ui.CheckedFragment;
import com.app.ivans.ghimli.ui.fragment.AboutFragment;
import com.app.ivans.ghimli.ui.fragment.CutPieceStockFragment;
import com.app.ivans.ghimli.ui.fragment.CutterFragment;
import com.app.ivans.ghimli.ui.fragment.HomeFragment;
import com.app.ivans.ghimli.ui.fragment.HomeFragmentInterface;
import com.app.ivans.ghimli.ui.fragment.LayerFragment;
import com.app.ivans.ghimli.utils.NetworkFunctions;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import de.hdodenhof.circleimageview.BuildConfig;
import de.hdodenhof.circleimageview.CircleImageView;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MenuActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, CheckedFragment {
    private static final String TAG = "MenuActivity";
    private ActivityMenuBinding binding;
    private CircleImageView circleImageView;

    private Fragment currentFragment = null;

    private HomeFragmentInterface homeFragmentInterface = null;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private NetworkFunctions mNetworkFunctions;

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

        mNetworkFunctions = new NetworkFunctions(MenuActivity.this);

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
        toggle.setDrawerIndicatorEnabled(false);

        toggle.setHomeAsUpIndicator(R.drawable.ic_menu_drawer);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
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

//        https://stackoverflow.com/questions/7384678/how-to-create-socket-connection-in-android
        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //Replace below IP with the IP of that device in which server socket open.
                    //If you change port then change the port number in the server side code also.
                    Socket s = new Socket("http://cutting.glaindonesia.lan", 8000);

                    OutputStream out = s.getOutputStream();

                    PrintWriter output = new PrintWriter(out);

                    output.println("active");
                    output.flush();
                    BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    final String st = input.readLine();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MenuActivity.this, "Connected From Server : "+st, Toast.LENGTH_SHORT).show();
                        }
                    });

                    output.close();
                    out.close();
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        updateSearchMenuItemVisibility(menu);
        return true;
    }

    private void updateSearchMenuItemVisibility(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                startActivity(new Intent(MenuActivity.this, SearchActivity.class));
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: registerReceiver");
        LocalBroadcastManager.getInstance(this);
        registerReceiver(networkChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: unregisterReceiver");
        unregisterReceiver(networkChangeReceiver);
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
            ft.replace(R.id.frame_container, homeFragment).commit();
            currentFragment = homeFragment;
            menuItem.setChecked(true);
        } else if (id == R.id.nav_layer) {
            clearStack();
            getSupportActionBar().setTitle("Layer");
            Fragment layerFragment = LayerFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_container, layerFragment).commit();
            currentFragment = layerFragment;
            menuItem.setChecked(true);
        } else if (id == R.id.nav_cutter) {
            clearStack();
            getSupportActionBar().setTitle("Cutter");
            Fragment cutterFragment = CutterFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_container, cutterFragment).commit();
            currentFragment = cutterFragment;
            menuItem.setChecked(true);
        } else if (id == R.id.nav_cut_piece_stock) {
            clearStack();
            getSupportActionBar().setTitle("Cut Piece");
            Fragment cutPieceStockFragment = CutPieceStockFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_container, cutPieceStockFragment).commit();
            currentFragment = cutPieceStockFragment;
        } else if (id == R.id.nav_about) {
            clearStack();
            getSupportActionBar().setTitle("Tentang");
            Fragment aboutFragment = AboutFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_container, aboutFragment).commit();
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

    private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            BodyNotification bodyNotification = new BodyNotification();
            Log.i(TAG, "fcmReceiver: Type " + bodyNotification.getType());
            Log.i(TAG, "fcmReceiver: IType " + intent.getStringExtra("type"));

            int networkState = NetworkFunctions.getConnectionStatus(context);

            switch (networkState) {
                case NetworkFunctions.WIFI:
                    break;
                case NetworkFunctions.MOBILE:
                    break;
                case NetworkFunctions.NOT_CONNECTED:
                    break;
            }

            if (networkState != NetworkFunctions.WIFI && isWifiAlertEnabled) {
                isWifiAlertEnabled = false;
                mNetworkFunctions.show(NetworkFunctions.NetworkStatus.DISCONNECTED);
                setCustomePositionView(R.layout.view_disconnected, MenuActivity.this, findViewById(R.id.rlViewDisconnected), Gravity.CENTER_VERTICAL | Gravity.TOP);
                Log.i(TAG, "onReceive: tvIpAddress " + NetworkFunctions.getIpAddress(context));
            }
            if (networkState == NetworkFunctions.WIFI && !isWifiAlertEnabled) {
                isWifiAlertEnabled = true;
                mNetworkFunctions.show(NetworkFunctions.NetworkStatus.CONNECTED);
                setCustomePositionView(R.layout.view_connected, MenuActivity.this, findViewById(R.id.rlViewConnected), Gravity.CENTER_VERTICAL | Gravity.TOP);
                Log.i(TAG, "onReceive: tvIpAddress " + NetworkFunctions.getIpAddress(context));
            }
        }
    };

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
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

    @Override
    public void setCheckedItem(int position) {
        binding.navView.getMenu().getItem(position).setChecked(true);
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
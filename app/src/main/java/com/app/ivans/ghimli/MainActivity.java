package com.app.ivans.ghimli;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.app.ivans.ghimli.databinding.ActivityMainBinding;
import com.app.ivans.ghimli.model.User;
import com.app.ivans.ghimli.utils.Extension;

import br.com.kots.mob.complex.preferences.ComplexPreferences;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(MainActivity.this));
        setContentView(binding.getRoot());

        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(this, Extension.PREF_USER, MODE_PRIVATE);
        mUser = complexPreferences.getObject(Extension.PREF_USER_KEY, User.class);
        if (mUser == null || mUser.getId() == 0) {
            startActivity(new Intent(getBaseContext(), LoginActivity.class));
            finish();
            return;
        } else {
            startActivity(new Intent(getBaseContext(), HomeActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

        alertDialogBuilder.setTitle(getString(R.string.app_name));
        alertDialogBuilder
                .setMessage("Apakah Anda yakin ingin keluar dari Aplikasi?")
                .setCancelable(true)
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                })
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
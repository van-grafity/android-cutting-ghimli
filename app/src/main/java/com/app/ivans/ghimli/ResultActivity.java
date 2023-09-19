package com.app.ivans.ghimli;

import static com.app.ivans.ghimli.utils.Extension.KEYWORD;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewbinding.ViewBinding;

import com.app.ivans.ghimli.adapter.CuttingOrderRecordAdapter;
import com.app.ivans.ghimli.base.BaseActivity;
import com.app.ivans.ghimli.databinding.ActivityResultBinding;
import com.app.ivans.ghimli.databinding.ToolbarBinding;
import com.app.ivans.ghimli.model.CuttingOrderRecord;
import com.app.ivans.ghimli.net.FAPI;
import com.app.ivans.ghimli.utils.Extension;
import com.app.ivans.ghimli.viewmodel.CuttingViewModel;

import java.util.ArrayList;

public class ResultActivity extends BaseActivity {
    private ActivityResultBinding binding;
    private CuttingOrderRecordAdapter mCuttingOrderRecordAdapter;
    private ArrayList<CuttingOrderRecord> mCuttingOrderRecords;
    private CuttingViewModel mCuttingViewModel;
    ToolbarBinding toolbarBinding;

    @NonNull
    @Override
    protected ViewBinding createViewBinding(LayoutInflater layoutInflater) {
        binding = ActivityResultBinding.inflate(layoutInflater);
        toolbarBinding = binding.toolbar;
        return binding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbarBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        String keyword = intent.getStringExtra(KEYWORD);

        toolbarBinding.ivLogoStore.setVisibility(View.GONE);
        toolbarBinding.tvTitle.setVisibility(View.GONE);
        toolbarBinding.txtSearch.setVisibility(View.VISIBLE);
        toolbarBinding.txtSearch.setText(keyword);

        mCuttingViewModel = new ViewModelProvider(ResultActivity.this).get(CuttingViewModel.class);

        toolbarBinding.txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (isNetworkConnected(getApplicationContext())) {
            search(keyword);
        }
    }

    private void search(String query) {
        binding.listOfSearchedList.setHasFixedSize(true);
        binding.listOfSearchedList.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 4));

        mCuttingViewModel.searchCuttingOrderLiveData(FAPI.getToken(ResultActivity.this), query).observe(ResultActivity.this, apiResponse -> {
            mCuttingOrderRecords = apiResponse.getData().getCuttingOrderRecords();
            if (mCuttingOrderRecords.isEmpty()) {
                binding.listOfSearchedList.setVisibility(View.GONE);
                binding.tvNotFound.setVisibility(View.VISIBLE);
                Toast.makeText(ResultActivity.this, "No Result", Toast.LENGTH_SHORT).show();
            } else {
                if (isNetworkConnected()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hideLoading();
                        }
                    });
                    mCuttingOrderRecordAdapter = new CuttingOrderRecordAdapter(mCuttingOrderRecords, ResultActivity.this, new CuttingOrderRecordAdapter.OnItemClickListener() {
                        @Override
                        public void OnClick(View view, int position, CuttingOrderRecord model) {
                            Intent intent = new Intent(ResultActivity.this, CuttingOrderRecordDetailActivity.class);
                            intent.putExtra(Extension.CUTTING_ORDER_RECORD, model);
                            startActivity(intent);
                        }
                    });

                    mCuttingOrderRecordAdapter.setItems(apiResponse.getData().getCuttingOrderRecords());
                    binding.listOfSearchedList.setAdapter(mCuttingOrderRecordAdapter);
                    mCuttingOrderRecordAdapter.notifyDataSetChanged();
                } else {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            hideLoading();
                        }
                    });
                    try {
                        AlertDialog alertDialog = new AlertDialog.Builder(ResultActivity.this).create();
                        alertDialog.setTitle("Sorry");
                        alertDialog.setMessage("Periksa koneksi internet anda");
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();

                    } catch (Exception exception) {
                        Log.e("requestPackage", "" + exception);
                        AlertDialog alertDialog = new AlertDialog.Builder(ResultActivity.this).create();
                        alertDialog.setTitle("Sorry");
                        alertDialog.setMessage("Terjadi kesalahan pada koneksi internet anda");
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isActive()) {
                if (getCurrentFocus() != null) {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        } catch (Exception exception) {
            Log.e("HIDEKEYBOARD", "" + exception);
        }
    }
}
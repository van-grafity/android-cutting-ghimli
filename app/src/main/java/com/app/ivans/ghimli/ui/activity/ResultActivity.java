package com.app.ivans.ghimli.ui.activity;

import static com.app.ivans.ghimli.utils.Extension.KEYWORD;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewbinding.ViewBinding;

import com.app.ivans.ghimli.adapter.CuttingAdapter;
import com.app.ivans.ghimli.base.BaseActivity;
import com.app.ivans.ghimli.databinding.ActivityResultBinding;
import com.app.ivans.ghimli.databinding.ToolbarBinding;
import com.app.ivans.ghimli.model.CuttingOrderRecord;
import com.app.ivans.ghimli.net.API;
import com.app.ivans.ghimli.ui.viewmodel.CuttingOrderViewModel;
import com.app.ivans.ghimli.utils.Extension;

import java.util.ArrayList;

public class ResultActivity extends BaseActivity {
    private ActivityResultBinding binding;
    private CuttingAdapter mCuttingAdapter;
    private ArrayList<CuttingOrderRecord> mCuttingOrderRecords;
    private CuttingOrderViewModel mCuttingOrderViewModel;
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

        mCuttingOrderViewModel = new ViewModelProvider(ResultActivity.this).get(CuttingOrderViewModel.class);
        Extension.showLoading(ResultActivity.this);
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

        mCuttingAdapter = new CuttingAdapter(ResultActivity.this, new CuttingAdapter.ItemAdapterOnClickHandler() {
            @Override
            public void onClick(CuttingOrderRecord cuttingOrder, View view, int position) {
                Intent intent = new Intent(ResultActivity.this, CuttingOrderRecordDetailActivity.class);
                intent.putExtra("cuttingOrder", cuttingOrder);
                startActivity(intent);
            }
        });

        mCuttingOrderViewModel.init(ResultActivity.this, API.getToken(ResultActivity.this), query);
        mCuttingOrderViewModel.getCuttingOrderPagedList().observe(this, cuttingOrderRecords -> {
            mCuttingAdapter.submitList(cuttingOrderRecords);
            binding.listOfSearchedList.setAdapter(mCuttingAdapter);
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
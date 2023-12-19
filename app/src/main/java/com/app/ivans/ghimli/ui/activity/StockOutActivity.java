package com.app.ivans.ghimli.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewbinding.ViewBinding;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.app.ivans.ghimli.adapter.TransferAdapter;
import com.app.ivans.ghimli.base.BaseActivity;
import com.app.ivans.ghimli.databinding.ActivityStockOutBinding;
import com.app.ivans.ghimli.databinding.ToolbarBinding;
import com.app.ivans.ghimli.helper.CuttingTicketDBHelper;
import com.app.ivans.ghimli.model.CuttingTicket;
import com.app.ivans.ghimli.utils.Extension;

import java.util.ArrayList;
import java.util.List;

public class StockOutActivity extends BaseActivity {
    private ActivityStockOutBinding binding;
    private ToolbarBinding toolbarBinding;
    private TransferAdapter mAdapter;
    private CuttingTicketDBHelper mDbHelper;

    @NonNull
    @Override
    protected ViewBinding createViewBinding(LayoutInflater layoutInflater) {
        binding = ActivityStockOutBinding.inflate(layoutInflater);
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
        toolbarBinding.tvTitle.setVisibility(View.VISIBLE);
        toolbarBinding.tvTitle.setText("Stock Out");

//        init
        mDbHelper = new CuttingTicketDBHelper(StockOutActivity.this);

        loadDataCuttingTicket();

        binding.fabScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StockOutActivity.this, ScanQrActivity.class);
                intent.putExtra(Extension.CUTTING_QR, "TRANSFER");
                startActivity(intent);
                finish();
            }
        });
    }

    public void loadDataCuttingTicket() {
        binding.rvTransferNote.setLayoutManager(new GridLayoutManager(StockOutActivity.this, 2, LinearLayoutManager.VERTICAL, false));
        List<CuttingTicket> records = mDbHelper.getAllCuttingTickets();
        mAdapter = new TransferAdapter(StockOutActivity.this, records);
        binding.rvTransferNote.setAdapter(mAdapter);
    }
}
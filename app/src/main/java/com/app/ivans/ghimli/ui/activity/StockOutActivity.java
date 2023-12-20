package com.app.ivans.ghimli.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewbinding.ViewBinding;

import com.app.ivans.ghimli.adapter.TransferAdapter;
import com.app.ivans.ghimli.base.BaseActivity;
import com.app.ivans.ghimli.databinding.ActivityStockOutBinding;
import com.app.ivans.ghimli.databinding.ToolbarBinding;
import com.app.ivans.ghimli.helper.CuttingTicketDBHelper;
import com.app.ivans.ghimli.model.APIResponse;
import com.app.ivans.ghimli.model.CuttingTicket;
import com.app.ivans.ghimli.net.API;
import com.app.ivans.ghimli.ui.viewmodel.CuttingViewModel;
import com.app.ivans.ghimli.utils.Extension;

import java.util.List;

public class StockOutActivity extends BaseActivity {
    private ActivityStockOutBinding binding;
    private ToolbarBinding toolbarBinding;
    private TransferAdapter mAdapter;
    private CuttingTicketDBHelper mDbHelper;
    private CuttingViewModel cuttingViewModel;
    public List<CuttingTicket> records;

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
        cuttingViewModel = new ViewModelProvider(StockOutActivity.this).get(CuttingViewModel.class);
//        init
        mDbHelper = new CuttingTicketDBHelper(StockOutActivity.this);

        binding.rvTransferNote.setLayoutManager(new GridLayoutManager(StockOutActivity.this, 2, LinearLayoutManager.VERTICAL, false));
        records = mDbHelper.getAllCuttingTickets();
        mAdapter = new TransferAdapter(StockOutActivity.this, records);
        binding.rvTransferNote.setAdapter(mAdapter);



        binding.btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StockOutActivity.this, ScanQrActivity.class);
                intent.putExtra(Extension.CUTTING_QR, "TRANSFER");
                startActivity(intent);
                finish();
            }
        });

        // public LiveData<APIResponse> bundleTransferMultipleLiveData(String auth, String[] serialNumber, String transactionType, int location) {
        //     productResponseData = favoriteRepository.bundleTransferMultipleResponse(auth, serialNumber, transactionType, location);
        //     return productResponseData;
        // }

//        cuttingViewModel.bundleTransferLiveData(API.getToken(CuttingTicketDetailActivity.this), binding.etSerialNumber.getText().toString(), binding.btnSwitch.getText().toString(), location).observe(CuttingTicketDetailActivity.this, new Observer<APIResponse>() {
//            @Override
//            public void onChanged(APIResponse apiResponse) {
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CuttingTicketDetailActivity.this);
//                alertDialogBuilder
//                        .setMessage(apiResponse.getMessage())
//                        .setCancelable(false)
//                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                finish();
//                            }
//                        });
//
//                AlertDialog alertDialog = alertDialogBuilder.create();
//                alertDialog.setCanceledOnTouchOutside(false);
//                alertDialog.show();
//            }
//        });


        String[] serialNumber = new String[records.size()];
        binding.btnStockOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < records.size(); i++) {
                    serialNumber[i] = records.get(i).getSerialNumber();
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        Extension.showLoading(StockOutActivity.this);
                    }
                });
                cuttingViewModel.bundleTransferMultipleLiveData(API.getToken(StockOutActivity.this), serialNumber, "OUT", 3).observe(StockOutActivity.this, new Observer<APIResponse>() {
                    @Override
                    public void onChanged(APIResponse apiResponse) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Extension.dismissLoading();
                            }
                        });
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StockOutActivity.this);
                        alertDialogBuilder
                                .setMessage(apiResponse.getMessage())
                                .setCancelable(false)
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        mDbHelper.deleteCuttingTickets();
                                        finish();
                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                    }
                });
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
}
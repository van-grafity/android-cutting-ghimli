package com.app.ivans.ghimli.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.app.ivans.ghimli.model.BundleStatus;
import com.app.ivans.ghimli.model.CuttingTicket;
import com.app.ivans.ghimli.net.API;
import com.app.ivans.ghimli.ui.viewmodel.CuttingViewModel;
import com.app.ivans.ghimli.utils.Extension;

import java.util.ArrayList;
import java.util.List;

public class StockOutActivity extends BaseActivity implements AdapterView.OnItemSelectedListener{
    private ActivityStockOutBinding binding;
    private ToolbarBinding toolbarBinding;
    private TransferAdapter mAdapter;
    private CuttingTicketDBHelper mDbHelper;
    private CuttingViewModel cuttingViewModel;
    public List<CuttingTicket> records;
    private String stat;
    int location = 0;
    private ArrayAdapter<String> statues;
    private ArrayList<String> items;

    @NonNull
    @Override
    protected ViewBinding createViewBinding(LayoutInflater layoutInflater) {
        binding = ActivityStockOutBinding.inflate(layoutInflater);
        toolbarBinding = binding.toolbar;
        return binding;
    }

    // public TransferAdapter(Context mContext, List<CuttingTicket> mItems, TransferAdapter.onItemClickListener clickedDelete) {
    //     this.mContext = mContext;
    //     this.mItems = mItems;
    //     this.mClickedDelete = clickedDelete;
    // }

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
        binding.spStatus.setOnItemSelectedListener(this);
//        init
        mDbHelper = new CuttingTicketDBHelper(StockOutActivity.this);

        binding.rvTransferNote.setLayoutManager(new GridLayoutManager(StockOutActivity.this, 2, LinearLayoutManager.VERTICAL, false));
        records = mDbHelper.getAllCuttingTickets();
        mAdapter = new TransferAdapter(StockOutActivity.this, records, new TransferAdapter.onItemClickListener() {
            @Override
            public void onClick(View view, int position, CuttingTicket ticket) {
                mDbHelper.deleteCuttingTicket(ticket.getId());
                mAdapter.removeItem(position);
                Toast.makeText(StockOutActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                mAdapter.notifyDataSetChanged();
            }
        });
        binding.rvTransferNote.setAdapter(mAdapter);


        loadDataBundleStatus();
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

                cuttingViewModel.bundleTransferMultipleLiveData(API.getToken(StockOutActivity.this), serialNumber, "OUT", location).observe(StockOutActivity.this, new Observer<APIResponse>() {
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

    private void loadDataBundleStatus() {
        items = new ArrayList<>();
        BundleStatus status = new BundleStatus();
        status.setId(55);
        status.setStatus("-");
        status.setDescription("-");
        items.add(status.getStatus());

        binding.spStatus.setVisibility(View.GONE);
        binding.progressLocation.setVisibility(View.VISIBLE);
        cuttingViewModel.getBundleStatusLiveData(API.getToken(StockOutActivity.this)).observe(StockOutActivity.this, new Observer<APIResponse>() {
            @Override
            public void onChanged(APIResponse apiResponse) {
//                Toast.makeText(CuttingTicketDetailActivity.this, apiResponse.getData().getBundleStatus().get(1).getStatus(), Toast.LENGTH_SHORT).show();  
                for (int x = 0; x < apiResponse.getData().getBundleStatus().size(); x++) {
                    if (apiResponse.getData().getBundleStatus().get(x).getId() != 1) {
                        items.add(apiResponse.getData().getBundleStatus().get(x).getStatus());
                        location = apiResponse.getData().getBundleStatus().get(x).getId();
                    }
                }
                statues = new ArrayAdapter<String>(StockOutActivity.this, android.R.layout.simple_spinner_item, items);

                statues.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spStatus.setAdapter(statues);
                binding.spStatus.setVisibility(View.VISIBLE);
                binding.progressLocation.setVisibility(View.GONE);

                binding.spStatus.setSelection(0);
            }
        });
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                mDbHelper.deleteCuttingTickets();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        stat = parent.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
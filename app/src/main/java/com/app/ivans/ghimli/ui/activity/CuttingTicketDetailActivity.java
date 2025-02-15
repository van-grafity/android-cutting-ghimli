package com.app.ivans.ghimli.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.app.ivans.ghimli.R;
import com.app.ivans.ghimli.base.BaseActivity;
import com.app.ivans.ghimli.databinding.ActivityCuttingTicketDetailBinding;
import com.app.ivans.ghimli.databinding.ToolbarBinding;
import com.app.ivans.ghimli.model.APIResponse;
import com.app.ivans.ghimli.model.BundleStatus;
import com.app.ivans.ghimli.net.API;
import com.app.ivans.ghimli.ui.viewmodel.CuttingViewModel;
import com.app.ivans.ghimli.utils.Extension;

import java.util.ArrayList;

public class CuttingTicketDetailActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    private ActivityCuttingTicketDetailBinding binding;
    private ToolbarBinding toolbarBinding;
    private CuttingViewModel cuttingViewModel;
    private String mSerialNumber;
    private String partStr;
    private ArrayList<String> items;
    private ArrayAdapter<String> statues;
    int location = 0;
    private String stat;

    @NonNull
    @Override
    protected ViewBinding createViewBinding(LayoutInflater layoutInflater) {
        binding = ActivityCuttingTicketDetailBinding.inflate(layoutInflater);
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

        toolbarBinding.tvTitleLarge.setVisibility(View.VISIBLE);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                mSerialNumber = null;
            } else {
                mSerialNumber = extras.getString("serialNumber");
//                binding.tvCtNumber.setText(mSerialNumber);
                toolbarBinding.tvTitleLarge.setText("Stock In");
            }
        }

        partStr = mSerialNumber.substring(mSerialNumber.length() - 2);

        cuttingViewModel = new ViewModelProvider(CuttingTicketDetailActivity.this).get(CuttingViewModel.class);
        binding.spStatus.setOnItemSelectedListener(this);
        runOnUiThread(new Runnable() {
            public void run() {
                Extension.showLoading(CuttingTicketDetailActivity.this);
            }
        });
        cuttingViewModel.getCuttingTicketDetailLiveData(API.getToken(CuttingTicketDetailActivity.this), mSerialNumber).observe(CuttingTicketDetailActivity.this, new Observer<APIResponse>() {
            @Override
            public void onChanged(APIResponse apiResponse) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Extension.dismissLoading();
                    }
                });

                binding.etSize.setText(apiResponse.getData().getCuttingTicket().getSize().getSize());
                binding.etNoLayingSheet.setText(apiResponse.getData().getCuttingTicket().getCuttingOrderRecord().getLayingPlanningDetail().getNoLayingSheet());
                binding.etTableNumber.setText(String.valueOf(apiResponse.getData().getCuttingTicket().getCuttingOrderRecord().getLayingPlanningDetail().getTableNumber()));
                binding.etSerialNumber.setText(String.valueOf(apiResponse.getData().getCuttingTicket().getSerialNumber()));
                binding.etGlNumber.setText(apiResponse.getData().getCuttingTicket().getCuttingOrderRecord().getLayingPlanningDetail().getLayingPlanning().getGl().getGlNo());
                binding.etBuyer.setText(apiResponse.getData().getCuttingTicket().getCuttingOrderRecord().getLayingPlanningDetail().getLayingPlanning().getBuyer().getName());
                binding.etStyle.setText(apiResponse.getData().getCuttingTicket().getCuttingOrderRecord().getLayingPlanningDetail().getLayingPlanning().getStyle().getStyle());
                binding.etColor.setText(apiResponse.getData().getCuttingTicket().getCuttingOrderRecord().getLayingPlanningDetail().getLayingPlanning().getColor().getName());
                binding.etLayer.setText(String.valueOf(apiResponse.getData().getCuttingTicket().getLayer()));
                binding.etFabricRollNo.setText(String.valueOf(apiResponse.getData().getCuttingTicket().getCuttingOrderRecordDetail().getFabricRoll()));
                binding.etFabricPO.setText(apiResponse.getData().getCuttingTicket().getCuttingOrderRecord().getLayingPlanningDetail().getLayingPlanning().getFabricPo());
                binding.etFabricType.setText(apiResponse.getData().getCuttingTicket().getCuttingOrderRecord().getLayingPlanningDetail().getLayingPlanning().getFabricType().getName());
                binding.etFabricConsumption.setText(apiResponse.getData().getCuttingTicket().getCuttingOrderRecord().getLayingPlanningDetail().getLayingPlanning().getFabricCons().getName());
            }
        });

        loadDataBundleStatus();

        binding.btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CuttingTicketDetailActivity.this, MenuActivity.class));
                finish();
            }
        });
//        binding.btnSwitch.setClickable(false);
//        binding.btnSwitch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (binding.btnSwitch.getText().toString().equals("IN")) {
//                    binding.btnSwitch.setText("OUT");
//                } else {
//                    binding.btnSwitch.setText("IN");
//                }
//            }
//        });
        binding.btnSwitch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(CuttingTicketDetailActivity.this, "Stock In Cutting",Toast.LENGTH_SHORT).show();
                binding.btnSwitch.setClickable(false);
                return false;
            }
        });

        binding.btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Extension.showLoading(CuttingTicketDetailActivity.this);
                    }
                });
                cuttingViewModel.bundleTransferLiveData(API.getToken(CuttingTicketDetailActivity.this), binding.etSerialNumber.getText().toString(), binding.btnSwitch.getText().toString(), location).observe(CuttingTicketDetailActivity.this, new Observer<APIResponse>() {
                    @Override
                    public void onChanged(APIResponse apiResponse) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CuttingTicketDetailActivity.this);
                        alertDialogBuilder
                                .setMessage(apiResponse.getMessage())
                                .setCancelable(false)
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
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

    private void loadDataBundleStatus() {
        items = new ArrayList<>();
        BundleStatus status = new BundleStatus();
        status.setId(55);
        status.setStatus("-");
        status.setDescription("-");
        items.add(status.getStatus());

        cuttingViewModel.getBundleStatusLiveData(API.getToken(CuttingTicketDetailActivity.this)).observe(CuttingTicketDetailActivity.this, new Observer<APIResponse>() {
            @Override
            public void onChanged(APIResponse apiResponse) {
//                Toast.makeText(CuttingTicketDetailActivity.this, apiResponse.getData().getBundleStatus().get(1).getStatus(), Toast.LENGTH_SHORT).show();
//                for (int x = 0; x < apiResponse.getData().getBundleStatus().size(); x++) {
//
//
//                }
                items.add(apiResponse.getData().getBundleStatus().get(0).getStatus());
                location = apiResponse.getData().getBundleStatus().get(0).getId();
                statues = new ArrayAdapter<String>(CuttingTicketDetailActivity.this, android.R.layout.simple_spinner_item, items);
                statues.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spStatus.setAdapter(statues);

                binding.spStatus.setSelection(0);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        stat = parent.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
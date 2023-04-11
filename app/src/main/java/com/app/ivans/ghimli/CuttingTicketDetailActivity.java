package com.app.ivans.ghimli;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.app.ivans.ghimli.base.BaseActivity;
import com.app.ivans.ghimli.databinding.ActivityCuttingTicketDetailBinding;
import com.app.ivans.ghimli.databinding.ToolbarBinding;
import com.app.ivans.ghimli.model.APIResponse;
import com.app.ivans.ghimli.net.API;
import com.app.ivans.ghimli.utils.Extension;
import com.app.ivans.ghimli.viewmodel.CuttingViewModel;

public class CuttingTicketDetailActivity extends BaseActivity {
    private ActivityCuttingTicketDetailBinding binding;
    private ToolbarBinding toolbarBinding;
    private CuttingViewModel cuttingViewModel;
    private String mSerialNumber;
    private String partStr;

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
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

        toolbarBinding.tvTitleLarge.setVisibility(View.VISIBLE);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                mSerialNumber = null;
            } else {
                mSerialNumber = extras.getString("serialNumber");
//                binding.tvCtNumber.setText(mSerialNumber);
                toolbarBinding.tvTitleLarge.setText(mSerialNumber);
            }
        }

        partStr = mSerialNumber.substring(mSerialNumber.length() - 2);

        cuttingViewModel = new ViewModelProvider(CuttingTicketDetailActivity.this).get(CuttingViewModel.class);
        runOnUiThread(new Runnable() {
            public void run() {
                Extension.showLoading(CuttingTicketDetailActivity.this);
            }
        });
        cuttingViewModel.getCuttingTicketDetailLiveData(API.getToken(CuttingTicketDetailActivity.this), Integer.parseInt(partStr)).observe(CuttingTicketDetailActivity.this, new Observer<APIResponse>() {
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

        binding.btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CuttingTicketDetailActivity.this, HomeActivity.class));
                finish();
            }
        });
    }
}
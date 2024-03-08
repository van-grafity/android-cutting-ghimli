package com.app.ivans.ghimli.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.app.ivans.ghimli.R;
import com.app.ivans.ghimli.adapter.FabricStickerAdapter;
import com.app.ivans.ghimli.base.BaseActivity;
import com.app.ivans.ghimli.databinding.ActivityCuttingOrderRecordDetailBinding;
import com.app.ivans.ghimli.databinding.ToolbarBinding;
import com.app.ivans.ghimli.model.CuttingOrderRecord;
import com.app.ivans.ghimli.model.CuttingOrderRecordDetail;
import com.app.ivans.ghimli.utils.Extension;
import com.app.ivans.ghimli.ui.viewmodel.CuttingViewModel;

import java.util.ArrayList;

public class CuttingOrderRecordDetailActivity extends BaseActivity {
    private static final String TAG = "CuttingOrderRecordDetai";
    private ActivityCuttingOrderRecordDetailBinding binding;
    private ToolbarBinding toolbarBinding;
    private Dialog mDialog;
    private String mSerialNumber;
    private CuttingViewModel cuttingViewModel;
    private CuttingOrderRecord mCuttingOrderRecord;
    private ArrayList<CuttingOrderRecordDetail> mItemSticker;

    @NonNull
    @Override
    protected ViewBinding createViewBinding(LayoutInflater layoutInflater) {
        binding = ActivityCuttingOrderRecordDetailBinding.inflate(layoutInflater);
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
        mDialog = new Dialog(this);
        cuttingViewModel = new ViewModelProvider(CuttingOrderRecordDetailActivity.this).get(CuttingViewModel.class);
        mCuttingOrderRecord = (CuttingOrderRecord) getIntent().getParcelableExtra(Extension.CUTTING_ORDER_RECORD);

        toolbarBinding.tvTitle.setText("Cutting Order Record");
        binding.tvSerialNumber.setText(mCuttingOrderRecord.getSerialNumber());
        loadDataStickerFabric();
    }

    public void loadDataStickerFabric() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CuttingOrderRecordDetailActivity.this, LinearLayoutManager.VERTICAL, false);
        runOnUiThread(new Runnable() {
            public void run() {
                Extension.showLoading(CuttingOrderRecordDetailActivity.this);
            }
        });
        mItemSticker = new ArrayList<>();

        mItemSticker = (ArrayList<CuttingOrderRecordDetail>) mCuttingOrderRecord.getCuttingOrderRecordDetail();
        if (mItemSticker.size() == 0){
            binding.ivIsEmpty.setVisibility(View.VISIBLE);
            binding.tvSerialNumber.setVisibility(View.GONE);
            binding.rvStickerFabric.setVisibility(View.GONE);
        }
        FabricStickerAdapter cuttingOrderRecordAdapter = new FabricStickerAdapter(mItemSticker, CuttingOrderRecordDetailActivity.this, new FabricStickerAdapter.OnItemClickListener() {
            @Override
            public void OnClick(View view, int position, CuttingOrderRecordDetail model) {

                viewIntroDragnDrop(model);
            }
        });

        binding.rvStickerFabric.setLayoutManager(layoutManager);
        runOnUiThread(new Runnable() {
            public void run() {
                Extension.dismissLoading();
            }
        });
        binding.rvStickerFabric.setAdapter(cuttingOrderRecordAdapter);

    }

    private void viewIntroDragnDrop(CuttingOrderRecordDetail model) {
        mDialog.setContentView(R.layout.dialog_detail_cor);
        LinearLayout canceledOutside = mDialog.findViewById(R.id.demo_hold_item);
        TextView fbRoll = mDialog.findViewById(R.id.tv_fabric_roll);
        TextView fbBatch = mDialog.findViewById(R.id.tv_fabric_batch);
        TextView color = mDialog.findViewById(R.id.tv_color);
        TextView yardage = mDialog.findViewById(R.id.tv_yardage);
        TextView weight = mDialog.findViewById(R.id.tv_weight);
        TextView layer = mDialog.findViewById(R.id.tv_layer);
        TextView joint = mDialog.findViewById(R.id.tv_joint);
        TextView balanceEnd = mDialog.findViewById(R.id.tv_balanceEnd);
        TextView remark = mDialog.findViewById(R.id.tv_remark);
        TextView opt = mDialog.findViewById(R.id.tv_operator);

        fbRoll.setText(model.getFabricRoll());
        fbBatch.setText(model.getFabricBatch());
        color.setText(model.getColor().getName());
        yardage.setText(String.valueOf(model.getYardage()));
        weight.setText(String.valueOf(model.getWeight()));
        layer.setText(String.valueOf(model.getLayer()));
        joint.setText(String.valueOf(model.getJoint()));
        balanceEnd.setText(String.valueOf(model.getBalanceEnd()));
        remark.setText(model.getRemarks());
        opt.setText(model.getOperator());

        Window window = mDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        canceledOutside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
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
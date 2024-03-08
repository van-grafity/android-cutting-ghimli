package com.app.ivans.ghimli;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.app.ivans.ghimli.databinding.ActivityCuttingOrderBinding;
import com.app.ivans.ghimli.databinding.ToolbarBinding;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItems;

public class CuttingOrderActivity extends AppCompatActivity {
    private ActivityCuttingOrderBinding binding;
    private ViewPagerItemAdapter mAdapter;
    private ToolbarBinding toolbarBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCuttingOrderBinding.inflate(LayoutInflater.from(this));
        toolbarBinding = binding.toolbar;
        setContentView(binding.getRoot());

        setSupportActionBar(toolbarBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbarBinding.tvTitle.setVisibility(View.VISIBLE);
        toolbarBinding.tvTitle.setText("Cutting Order Record");

        mAdapter = new ViewPagerItemAdapter(ViewPagerItems.with(this)
                .add("Cutting Order Record", R.layout.fragment_cutting_order)
                .add("Fabric Roll", R.layout.fragment_fabric_roll)
                .create());

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(mAdapter);

        SmartTabLayout viewPagerTab = findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
    }
}
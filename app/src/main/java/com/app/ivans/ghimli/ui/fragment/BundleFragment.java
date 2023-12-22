package com.app.ivans.ghimli.ui.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.ivans.ghimli.R;
import com.app.ivans.ghimli.adapter.CuttingAdapter;
import com.app.ivans.ghimli.adapter.TransferAdapter;
import com.app.ivans.ghimli.model.CuttingOrderRecord;
import com.app.ivans.ghimli.model.CuttingTicket;
import com.app.ivans.ghimli.net.API;
import com.app.ivans.ghimli.ui.activity.CuttingOrderRecordDetailActivity;
import com.app.ivans.ghimli.ui.activity.ScanQrActivity;
import com.app.ivans.ghimli.ui.activity.StockOutActivity;
import com.app.ivans.ghimli.ui.viewmodel.CutPieceStockViewModel;
import com.app.ivans.ghimli.ui.viewmodel.CuttingOrderViewModel;
import com.app.ivans.ghimli.utils.Extension;

import java.util.ArrayList;

public class BundleFragment extends Fragment {

    private CutPieceStockViewModel mViewModel;
    private TransferAdapter mAdapter;
    private ArrayList<CuttingTicket> mItems;
    private ArrayList<CuttingTicket> mNewItems;
    private CuttingAdapter cuttingAdapter;
    private RecyclerView rvCutPiece;
    private CuttingOrderViewModel cuttingOrderViewModel;
    private boolean isDataLoaded = false;

    public static BundleFragment newInstance() {
        return new BundleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bundle, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.bundle);
        rvCutPiece = view.findViewById(R.id.rvCutPiece);
        Extension.showLoading(getActivity());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CutPieceStockViewModel.class);
        cuttingOrderViewModel = new ViewModelProvider(this).get(CuttingOrderViewModel.class);
        // TODO: Use the ViewModel
        rvCutPiece.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        cuttingAdapter = new CuttingAdapter(getActivity(), new CuttingAdapter.ItemAdapterOnClickHandler() {
            @Override
            public void onClick(CuttingOrderRecord cuttingOrder, View view, int position) {
                Intent intent = new Intent(getActivity(), CuttingOrderRecordDetailActivity.class);
                intent.putExtra(Extension.CUTTING_ORDER_RECORD, cuttingOrder);
                startActivity(intent);
            }
        });
        if (cuttingOrderViewModel.getCuttingOrderPagedList() != null) {

            cuttingAdapter.submitList(cuttingOrderViewModel.getCuttingOrderPagedList().getValue());

            rvCutPiece.setAdapter(cuttingAdapter);
        } else {
            if (!isDataLoaded) {
                // Make the API call
                loadCutPieceData();
                isDataLoaded = true;
            }
        }
    }

    private void loadCutPieceData() {
        cuttingOrderViewModel.init(getActivity(), API.getToken(getActivity()), "", "2", "2");
        cuttingOrderViewModel.getCuttingOrderPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<CuttingOrderRecord>>() {
            @Override
            public void onChanged(PagedList<CuttingOrderRecord> cuttingOrderRecords) {
                cuttingAdapter.submitList(cuttingOrderRecords);
                rvCutPiece.setAdapter(cuttingAdapter);
            }
        });
    }

}
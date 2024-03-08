package com.app.ivans.ghimli.ui.viewmodel;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.app.ivans.ghimli.model.CuttingOrderRecord;
import com.app.ivans.ghimli.net.CuttingOrderRecordDataSource;
import com.app.ivans.ghimli.net.CuttingOrderRecordDataSourceFactory;

public class CuttingOrderViewModel extends ViewModel {
    private static final String TAG = "CuttingOrderViewModel";
    private LiveData<PagedList<CuttingOrderRecord>> cuttingOrderPagedList;
    LiveData<PageKeyedDataSource<Integer, CuttingOrderRecord>> liveDataSource;
    private CuttingOrderRecordDataSourceFactory cuttingOrderRecordDataSourceFactory;
    
    public void init(Context context, String authorization, String search, String statusLayer, String statusCut) {
        cuttingOrderRecordDataSourceFactory = new CuttingOrderRecordDataSourceFactory(context, authorization, search, statusLayer, statusCut);
        liveDataSource = cuttingOrderRecordDataSourceFactory.getCuttingOrderLiveDataSource();
        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(CuttingOrderRecordDataSource.PAGE_SIZE)
                .build();
        cuttingOrderPagedList = (new LivePagedListBuilder(cuttingOrderRecordDataSourceFactory, config)).build();
    }

    public LiveData<PagedList<CuttingOrderRecord>> getCuttingOrderPagedList() {
        return cuttingOrderPagedList;
    }

    public void refresh() {
        cuttingOrderRecordDataSourceFactory.getCuttingOrderLiveDataSource().getValue().invalidate();
    }
}

//    public void init(Context context, String authorization, String search) {

//    }
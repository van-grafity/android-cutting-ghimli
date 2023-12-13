package com.app.ivans.ghimli.net;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.app.ivans.ghimli.model.CuttingOrderRecord;

public class CuttingOrderRecordDataSourceFactory extends DataSource.Factory {
    private MutableLiveData<PageKeyedDataSource<Integer, CuttingOrderRecord>>cuttingOrderLiveDataSource = new MutableLiveData<>();
    
    private Context context;
    private String authorization;
    private String search;
    private String mStatusLayer;
    private String mStatusCut;

    public CuttingOrderRecordDataSourceFactory(Context context, String authorization, String search, String statusLayer, String statusCut) {
        this.context = context;
        this.authorization = authorization;
        this.search = search;
        this.mStatusLayer = statusLayer;
        this.mStatusCut = statusCut;
    }
    
    @Override
    public DataSource<Integer, CuttingOrderRecord> create() {
        CuttingOrderRecordDataSource cuttingOrderRecordDataSource = new CuttingOrderRecordDataSource(context, authorization, search, mStatusLayer, mStatusCut);
        cuttingOrderLiveDataSource.postValue(cuttingOrderRecordDataSource);
        return cuttingOrderRecordDataSource;
    }

    // Getter for Cutting Order live DataSource
    public MutableLiveData<PageKeyedDataSource<Integer, CuttingOrderRecord>> getCuttingOrderLiveDataSource() {
        return cuttingOrderLiveDataSource;
    }
}
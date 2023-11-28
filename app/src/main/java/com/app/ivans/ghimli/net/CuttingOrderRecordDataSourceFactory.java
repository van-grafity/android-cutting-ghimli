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

    public CuttingOrderRecordDataSourceFactory(Context context, String authorization, String search) {
        this.context = context;
        this.authorization = authorization;
        this.search = search;
    }
    
    @Override
    public DataSource<Integer, CuttingOrderRecord> create() {
        CuttingOrderRecordDataSource cuttingOrderRecordDataSource = new CuttingOrderRecordDataSource(context, authorization, search);
        cuttingOrderLiveDataSource.postValue(cuttingOrderRecordDataSource);
        return cuttingOrderRecordDataSource;
    }

    // Getter for Cutting Order live DataSource
    public MutableLiveData<PageKeyedDataSource<Integer, CuttingOrderRecord>> getCuttingOrderLiveDataSource() {
        return cuttingOrderLiveDataSource;
    }
}
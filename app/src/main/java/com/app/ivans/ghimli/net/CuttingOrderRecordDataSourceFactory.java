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

    public CuttingOrderRecordDataSourceFactory(Context context) {
        this.context = context;
    }

    @Override
    public DataSource<Integer, CuttingOrderRecord> create() {
        CuttingOrderRecordDataSource cuttingOrderRecordDataSource = new CuttingOrderRecordDataSource(context, "Bearer 2881|npL6oVdwsu99ffWgaelfffWpJ0S0T3CNEj4Ln9kkb35cdbae", "OPBODY-S752-01-001");
        cuttingOrderLiveDataSource.postValue(cuttingOrderRecordDataSource);
        return cuttingOrderRecordDataSource;
    }

    // Getter for Cutting Order live DataSource
    public MutableLiveData<PageKeyedDataSource<Integer, CuttingOrderRecord>> getCuttingOrderLiveDataSource() {
        return cuttingOrderLiveDataSource;
    }
}
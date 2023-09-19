package com.app.ivans.ghimli.net;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.app.ivans.ghimli.model.CuttingOrderRecord;

public class CuttingOrderRecordDataSourceFactory extends DataSource.Factory {

    // Creating the mutable live database
    private MutableLiveData<PageKeyedDataSource<Integer, CuttingOrderRecord>> productLiveDataSource = new MutableLiveData<>();

    public static CuttingOrderRecordDataSource cuttingOrderRecordDataSource;

    private Context mContext;
    private String mAuth;

    public CuttingOrderRecordDataSourceFactory(Context context, String auth) {
        this.mContext = context;
        this.mAuth = auth;

    }

    @Override
    public DataSource<Integer, CuttingOrderRecord> create() {
        // Getting our Data source object
        cuttingOrderRecordDataSource = new CuttingOrderRecordDataSource(mContext, mAuth);

        // Posting the Data source to get the values
        productLiveDataSource.postValue(cuttingOrderRecordDataSource);

        // Returning the Data source
        return cuttingOrderRecordDataSource;
    }


    // Getter for Product live DataSource
    public MutableLiveData<PageKeyedDataSource<Integer, CuttingOrderRecord>> getMutableLiveData() {
        return productLiveDataSource;
    }
}
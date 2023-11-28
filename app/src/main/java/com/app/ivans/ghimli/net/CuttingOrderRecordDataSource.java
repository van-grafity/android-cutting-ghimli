package com.app.ivans.ghimli.net;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.app.ivans.ghimli.model.APIResponse;
import com.app.ivans.ghimli.model.CuttingOrderRecord;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CuttingOrderRecordDataSource extends PageKeyedDataSource<Integer, CuttingOrderRecord> {
    private static final int FIRST_PAGE = 1;
    public static final int PAGE_SIZE = 20;
    private Context context;
    private String search;
    private String authorization;

    public CuttingOrderRecordDataSource(Context context, String authorization, String search) {
        this.context = context;
        this.search = search;
        this.authorization = authorization;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, CuttingOrderRecord> callback) {
        FAPI.service().getCuttingOrder(authorization, PAGE_SIZE, FIRST_PAGE, search).enqueue(new APICallback<APIResponse>(context) {
            @Override
            protected void onSuccess(APIResponse apiResponse) {
                callback.onResult(apiResponse.getData().getCuttingOrderRecords(), null, FIRST_PAGE + 1);
            }

            @Override
            protected void onError(BadRequest error) {
                Log.d("DataSource", "onError: " + error.errors);
            }
        });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, CuttingOrderRecord> callback) {
        FAPI.service().getCuttingOrder(authorization, PAGE_SIZE, params.key, search).enqueue(new APICallback<APIResponse>(context) {
            @Override
            protected void onSuccess(APIResponse apiResponse) {
                Integer key = (params.key > 1) ? params.key - 1 : null;
                callback.onResult(apiResponse.getData().getCuttingOrderRecords(), key);
            }

            @Override
            protected void onError(BadRequest error) {
                Log.d("DataSource", "onError: " + error.errors);
            }
        });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, CuttingOrderRecord> callback) {
        FAPI.service().getCuttingOrder(authorization, PAGE_SIZE, params.key, search).enqueue(new APICallback<APIResponse>(context) {
            @Override
            protected void onSuccess(APIResponse apiResponse) {
                Integer key = apiResponse.getData().getCuttingOrderRecords().size() > 0 ? params.key + 1 : null;
                callback.onResult(apiResponse.getData().getCuttingOrderRecords(), key);
            }

            @Override
            protected void onError(BadRequest error) {
                Log.d("DataSource", "onError: " + error.errors);
            }
        });
    }
}
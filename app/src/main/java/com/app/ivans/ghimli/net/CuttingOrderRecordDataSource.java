package com.app.ivans.ghimli.net;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.app.ivans.ghimli.model.APIResponse;
import com.app.ivans.ghimli.model.CuttingOrderRecord;

public class CuttingOrderRecordDataSource extends PageKeyedDataSource<Integer, CuttingOrderRecord> {
    private static final int FIRST_PAGE = 1;
    public static final int PAGE_SIZE = 10;
    private Context mContext;
    private String mAuth;

    public CuttingOrderRecordDataSource(Context context, String auth) {
        this.mContext = context;
        this.mAuth = auth;

    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, CuttingOrderRecord> callback) {
//        FAPI.service().getCuttingOrder(mAuth, FIRST_PAGE).enqueue(new APICallback<APIResponse>(mContext) {
//            @Override
//            public void onSuccess(APIResponse apiResponse) {
//                if (apiResponse != null) {
//                    callback.onResult(apiResponse.getData().getCuttingOrderRecords(), null, FIRST_PAGE + 1);
//                }
//            }
//
//            @Override
//            protected void onError(BadRequest error) {
//                Log.v("onFailure", "Failed to get Products");
//            }
//        });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, CuttingOrderRecord> callback) {
//        FAPI.service().getCuttingOrder(mAuth, params.key).enqueue(new APICallback<APIResponse>(mContext) {
//            @Override
//            public void onSuccess(APIResponse apiResponse) {
//                if (apiResponse != null) {
//                    Integer key = (params.key > 1) ? params.key - 1 : null;
//                    callback.onResult(apiResponse.getData().getCuttingOrderRecords(), key);
//                }
//            }
//
//            @Override
//            protected void onError(BadRequest error) {
//                Log.v("onFailure", "Failed to get Products");
//            }
//        });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, CuttingOrderRecord> callback) {
//        FAPI.service().getCuttingOrder(mAuth, params.key).enqueue(new APICallback<APIResponse>(mContext) {
//            @Override
//            public void onSuccess(APIResponse apiResponse) {
//                if (apiResponse != null) {
//                    Integer key = (params.key < apiResponse.getData().getLastPage()) ? params.key + 1 : null;
//                    callback.onResult(apiResponse.getData().getCuttingOrderRecords(), key);
//                }
//            }
//
//            @Override
//            protected void onError(BadRequest error) {
//                Log.v("onFailure", "Failed to get Products");
//            }
//        });
    }
}
package com.app.ivans.ghimli.net;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.app.ivans.ghimli.model.APIResponse;
import com.app.ivans.ghimli.model.CuttingOrderRecord;

public class CuttingOrderRecordDataSource extends PageKeyedDataSource<Integer, CuttingOrderRecord> {
    private static final int FIRST_PAGE = 1;
    public static final int PAGE_SIZE = 20;
    private Context mContext;
    private String mAuth;

    public CuttingOrderRecordDataSource(Context context, String auth) {
        this.mContext = context;
        this.mAuth = auth;

    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, CuttingOrderRecord> callback) {
        FAPI.service().getCuttingOrder(mAuth).enqueue(new APICallback<APIResponse>(mContext) {
            @Override
            protected void onSuccess(APIResponse apiResponse) {
                if (apiResponse != null) {
                    callback.onResult(apiResponse.getData().getCuttingOrderRecords(), null, FIRST_PAGE + 1);
                }
            }

            @Override
            protected void onError(BadRequest error) {
                Log.v("onFailure", "Failed to get Products");
            }
        });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, CuttingOrderRecord> callback) {
        FAPI.service().getCuttingOrder(mAuth).enqueue(new APICallback<APIResponse>(mContext) {
            @Override
            protected void onSuccess(APIResponse apiResponse) {
                Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                if (apiResponse != null) {
                    // Passing the loaded database and the previous page key
                    callback.onResult(apiResponse.getData().getCuttingOrderRecords(), adjacentKey);
                }
            }

            @Override
            protected void onError(BadRequest error) {
                Log.v("onFailure", "Failed to previous Products");
            }
        });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, CuttingOrderRecord> callback) {
        FAPI.service().getCuttingOrder(mAuth).enqueue(new APICallback<APIResponse>(mContext) {
            @Override
            protected void onSuccess(APIResponse apiResponse) {
                if (apiResponse != null) {
                    // If the response has next page, increment the next page number
                    Integer key = apiResponse.getData().getCuttingOrderRecords().size() == PAGE_SIZE ? params.key + 1 : null;

                    // Passing the loaded database and next page value
                    callback.onResult(apiResponse.getData().getCuttingOrderRecords(), key);
                }
            }

            @Override
            protected void onError(BadRequest error) {
                Log.v("onFailure", "Failed to get next Products");
            }
        });
    }
}

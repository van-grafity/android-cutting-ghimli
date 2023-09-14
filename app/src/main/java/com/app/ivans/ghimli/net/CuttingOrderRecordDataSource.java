package com.app.ivans.ghimli.net;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.app.ivans.ghimli.model.APIResponse;
import com.app.ivans.ghimli.model.CuttingOrderRecord;

// {
    //     "status": 200,
    //     "message": "Cutting Order Record retrieved successfully.",
    //     "data": {
    //         "cutting_order_records": [
    //             {
    //                 "id": 5895,
    //                 "serial_number": "COR-63544-00-SCBWMSMH95BODY-S258-01-003",
    //                 "laying_planning_detail_id": 7644,
    //                 "id_status_layer": 1,
    //                 "id_status_cut": 1,
    //                 "created_by": 1,
    //                 "is_pilot_run": 0,
    //                 "created_at": "2023-09-12T10:01:01.000000Z",
    //                 "updated_at": "2023-09-12T10:01:01.000000Z",
    //                 "status_layer": {
    //                     "id": 1,
    //                     "name": "not completed",
    //                     "created_at": "2023-06-15T03:37:59.000000Z",
    //                     "updated_at": "2023-06-15T03:37:59.000000Z"
    //                 },
    //                 "cutting_order_record_detail": []
    //             },
    //             {
    //                 "id": 5894,
    //                 "serial_number": "COR-63544-00-SCBWMSMH95BODY-S258-01-002",
    //                 "laying_planning_detail_id": 7643,
    //                 "id_status_layer": 1,
    //                 "id_status_cut": 1,
    //                 "created_by": 1,
    //                 "is_pilot_run": 0,
    //                 "created_at": "2023-09-12T09:59:06.000000Z",
    //                 "updated_at": "2023-09-12T09:59:06.000000Z",
    //                 "status_layer": {
    //                     "id": 1,
    //                     "name": "not completed",
    //                     "created_at": "2023-06-15T03:37:59.000000Z",
    //                     "updated_at": "2023-06-15T03:37:59.000000Z"
    //                 },
    //                 "cutting_order_record_detail": []
    //             }
    //         ],
    //         "current_page": 2,
    //                 "last_page": 523,
    //                 "prev_page_url": "http://192.168.45.32/cutting-ticket-app/public/api/cutting-orders?page=1",
    //                 "next_page_url": "http://192.168.45.32/cutting-ticket-app/public/api/cutting-orders?page=3",
    //                 "total": 5230
    //     }
    // }

    // @GET("cutting-orders")
    // // getCuttingOrder paginate
    // Call<APIResponse> getCuttingOrder(@Header("Authorization") String authorization, @Path("page") int page);
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
    FAPI.service().getCuttingOrder(mAuth, FIRST_PAGE).enqueue(new APICallback<APIResponse>(mContext) {
        @Override
        public void onSuccess(APIResponse apiResponse) {
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
        FAPI.service().getCuttingOrder(mAuth, params.key).enqueue(new APICallback<APIResponse>(mContext) {
            @Override
            public void onSuccess(APIResponse apiResponse) {
                if (apiResponse != null) {
                    Integer key = (params.key > 1) ? params.key - 1 : null;
                    callback.onResult(apiResponse.getData().getCuttingOrderRecords(), key);
                }
            }
            
            @Override
            protected void onError(BadRequest error) {
                Log.v("onFailure", "Failed to get Products");
            }
        });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, CuttingOrderRecord> callback) {
        FAPI.service().getCuttingOrder(mAuth, params.key).enqueue(new APICallback<APIResponse>(mContext) {
            @Override
            public void onSuccess(APIResponse apiResponse) {
                if (apiResponse != null) {
                    Integer key = (params.key < apiResponse.getData().getLastPage()) ? params.key + 1 : null;
                    callback.onResult(apiResponse.getData().getCuttingOrderRecords(), key);
                }
            }
            
            @Override
            protected void onError(BadRequest error) {
                Log.v("onFailure", "Failed to get Products");
            }
        });
    }
}
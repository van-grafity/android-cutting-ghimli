package com.app.ivans.ghimli.viewmodel;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.app.ivans.ghimli.model.APIResponse;
import com.app.ivans.ghimli.model.CuttingOrderRecord;
import com.app.ivans.ghimli.model.CuttingOrderRecordDetail;
import com.app.ivans.ghimli.net.CuttingOrderRecordDataSource;
import com.app.ivans.ghimli.net.CuttingOrderRecordDataSourceFactory;
import com.app.ivans.ghimli.repository.CuttingRepository;


public class CuttingViewModel extends ViewModel {

    private CuttingRepository favoriteRepository;
    private LiveData<APIResponse> productResponseData;
    public LiveData<PagedList<APIResponse>> cuttingPagedList;

    public LiveData<PagedList<CuttingOrderRecord>> moviePagedList;
    private LiveData<PageKeyedDataSource<Integer, CuttingOrderRecord>> liveDataSource;

    public CuttingViewModel() {
        Context context = new Activity();
        favoriteRepository = new CuttingRepository(context);
    }

    public LiveData<APIResponse> createOptCuttingOrderLiveData(String auth, String serialNumber, String fabricRoll, String fabricBatch, String color, double yardage, double weight, int layer, String joint, String balanceEnd, String remarks, String operator) {
        productResponseData = favoriteRepository.createOptCuttingOrderResponse(auth, serialNumber, fabricRoll, fabricBatch, color, yardage, weight, layer, joint, balanceEnd, remarks, operator);
        return productResponseData;
    }

    public LiveData<APIResponse> createOptCuttingOrderObj(String auth, CuttingOrderRecordDetail cuttingOrderRecordDetail) {
        productResponseData = favoriteRepository.createOptCuttingOrderResponseObj(auth, cuttingOrderRecordDetail);
        return productResponseData;
    }

    //    public class CuttingOrderRecordDataSource extends PageKeyedDataSource<Integer, CuttingOrderRecord> {
//        private static final int FIRST_PAGE = 1;
//        public static final int PAGE_SIZE = 10;
//        private Context mContext;
//        private String mAuth;
//
//        public CuttingOrderRecordDataSource(Context context, String auth) {
//            this.mContext = context;
//            this.mAuth = auth;
//
//        }
//
//        @Override
//        public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, CuttingOrderRecord> callback) {
//            FAPI.service().getCuttingOrder(mAuth, FIRST_PAGE).enqueue(new APICallback<APIResponse>(mContext) {
//                @Override
//                public void onSuccess(APIResponse apiResponse) {
//                    if (apiResponse != null) {
//                        callback.onResult(apiResponse.getData().getCuttingOrderRecords(), null, FIRST_PAGE + 1);
//                    }
//                }
//
//                @Override
//                protected void onError(BadRequest error) {
//                    Log.v("onFailure", "Failed to get Products");
//                }
//            });
//        }
//
//        @Override
//        public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, CuttingOrderRecord> callback) {
//            FAPI.service().getCuttingOrder(mAuth, params.key).enqueue(new APICallback<APIResponse>(mContext) {
//                @Override
//                public void onSuccess(APIResponse apiResponse) {
//                    if (apiResponse != null) {
//                        Integer key = (params.key > 1) ? params.key - 1 : null;
//                        callback.onResult(apiResponse.getData().getCuttingOrderRecords(), key);
//                    }
//                }
//
//                @Override
//                protected void onError(BadRequest error) {
//                    Log.v("onFailure", "Failed to get Products");
//                }
//            });
//        }
//
//        @Override
//        public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, CuttingOrderRecord> callback) {
//            FAPI.service().getCuttingOrder(mAuth, params.key).enqueue(new APICallback<APIResponse>(mContext) {
//                @Override
//                public void onSuccess(APIResponse apiResponse) {
//                    if (apiResponse != null) {
//                        Integer key = (params.key < apiResponse.getData().getLastPage()) ? params.key + 1 : null;
//                        callback.onResult(apiResponse.getData().getCuttingOrderRecords(), key);
//                    }
//                }
//
//                @Override
//                protected void onError(BadRequest error) {
//                    Log.v("onFailure", "Failed to get Products");
//                }
//            });
//        }
//    }

//    public class CuttingOrderRecordDataSourceFactory extends DataSource.Factory {
//
//        // Creating the mutable live database
//        private MutableLiveData<PageKeyedDataSource<Integer, CuttingOrderRecord>> productLiveDataSource = new MutableLiveData<>();
//
//        public static CuttingOrderRecordDataSource cuttingOrderRecordDataSource;
//
//        private Context mContext;
//        private String mAuth;
//
//        public CuttingOrderRecordDataSourceFactory(Context context, String auth) {
//            this.mContext = context;
//            this.mAuth = auth;
//
//        }
//
//        @Override
//        public DataSource<Integer, CuttingOrderRecord> create() {
//            // Getting our Data source object
//            cuttingOrderRecordDataSource = new CuttingOrderRecordDataSource(mContext, mAuth);
//
//            // Posting the Data source to get the values
//            productLiveDataSource.postValue(cuttingOrderRecordDataSource);
//
//            // Returning the Data source
//            return cuttingOrderRecordDataSource;
//        }
//
//
//        // Getter for Product live DataSource
//        public MutableLiveData<PageKeyedDataSource<Integer, CuttingOrderRecord>> getMutableLiveData() {
//            return productLiveDataSource;
//        }
//    }

// @GET("cutting-orders")
// // getCuttingOrder paginate
// Call<APIResponse> getCuttingOrder(@Header("Authorization") String authorization, @Path("page") int page);

    public void loadCuttingOrder(String auth, Context context) {
        CuttingOrderRecordDataSourceFactory cuttingOrderRecordDataSourceFactory = new CuttingOrderRecordDataSourceFactory(context, auth);
        liveDataSource = cuttingOrderRecordDataSourceFactory.getMutableLiveData();

        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(CuttingOrderRecordDataSource.PAGE_SIZE)
                        .build();

        cuttingPagedList = (new LivePagedListBuilder(cuttingOrderRecordDataSourceFactory, config)).build();
    }

    public LiveData<PagedList<APIResponse>> getCuttingPagedList() {
    return cuttingPagedList;
}

    public LiveData<APIResponse> getColorLiveData(String auth) {
        productResponseData = favoriteRepository.getColorResponse(auth);
        return productResponseData;
    }

    public LiveData<APIResponse> getCuttingOrderBySerialNumberLiveData(String auth, String serialNumber) {
        productResponseData = favoriteRepository.getCuttingOrderBySerialNumberResponse(auth, serialNumber);
        return productResponseData;
    }

    public LiveData<APIResponse> getLayingPlanningBySerialNumberLiveData(String auth, String serialNumber) {
        productResponseData = favoriteRepository.getLayingPlanningBySerialNumberResponse(auth, serialNumber);
        return productResponseData;
    }

    public LiveData<APIResponse> postStatusCutBySerialNumberLiveData(String auth, String serialNumber, String status) {
        productResponseData = favoriteRepository.postStatusCutResponse(auth, serialNumber, status);
        return productResponseData;
    }
    
    public LiveData<APIResponse> searchCuttingOrderLiveData(String auth, String serialNumber) {
        productResponseData = favoriteRepository.searchCuttingOrderResponse(auth, serialNumber);
        return productResponseData;
    }

    public LiveData<APIResponse> getRemarksLiveData(String auth) {
        productResponseData = favoriteRepository.getRemarksResponse(auth);
        return productResponseData;
    }

    public LiveData<APIResponse> getCuttingTicketDetailLiveData(String auth, int id) {
        productResponseData = favoriteRepository.getCuttingTicketDetailResponse(auth, id);
        return productResponseData;
    }
}
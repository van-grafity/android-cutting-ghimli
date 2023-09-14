package com.app.ivans.ghimli.repository;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.ivans.ghimli.R;
import com.app.ivans.ghimli.model.APIResponse;
import com.app.ivans.ghimli.model.CuttingOrderRecordDetail;
import com.app.ivans.ghimli.net.API;
import com.app.ivans.ghimli.net.APICallback;
import com.app.ivans.ghimli.net.BadRequest;
import com.app.ivans.ghimli.net.FAPI;
import com.app.ivans.ghimli.utils.Extension;


public class CuttingRepository {
    private Context mContext;

    public CuttingRepository(Context context) {
        this.mContext = context;
    }

    public LiveData<APIResponse> createOptCuttingOrderResponse(String auth, String serialNumber, String fabricRoll, String fabricBatch, String color, double yardage, double weight, int layer, String joint, String balanceEnd, String remarks, String operator) {
        final MutableLiveData<APIResponse> mutableLiveData = new MutableLiveData<>();
        FAPI.service().createOptCuttingOrder(auth, serialNumber, fabricRoll, fabricBatch, color,yardage, weight, layer, joint, balanceEnd, remarks, operator).enqueue(new APICallback<APIResponse>(mContext) {
            @Override
            protected void onSuccess(APIResponse apiResponse) {
                mutableLiveData.setValue(apiResponse);
            }

            @Override
            protected void onError(BadRequest error) {
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    public void run() {
                        Extension.dismissLoading();
                    }
                });
                AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                alertDialog.setTitle(mContext.getString(R.string.sorry));
                alertDialog.setMessage(error.errorDetails);
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, mContext.getString(R.string.dialog_ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
        return mutableLiveData;
    }

    public LiveData<APIResponse> createOptCuttingOrderResponseObj(String auth, CuttingOrderRecordDetail cuttingOrderRecordDetail) {
        final MutableLiveData<APIResponse> mutableLiveData = new MutableLiveData<>();
        FAPI.service().createOptCuttingOrderObj(auth, cuttingOrderRecordDetail).enqueue(new APICallback<APIResponse>(mContext) {
            @Override
            protected void onSuccess(APIResponse apiResponse) {
                mutableLiveData.setValue(apiResponse);
            }

            @Override
            protected void onError(BadRequest error) {
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    public void run() {
                        Extension.dismissLoading();
                    }
                });
                AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                alertDialog.setTitle(mContext.getString(R.string.sorry));
                alertDialog.setMessage(error.errorDetails);
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, mContext.getString(R.string.dialog_ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
        return mutableLiveData;
    }

//    public LiveData<APIResponse> getCuttingOrderResponse(String auth) {
//        final MutableLiveData<APIResponse> mutableLiveData = new MutableLiveData<>();
//        FAPI.service().getCuttingOrder(auth).enqueue(new APICallback<APIResponse>(mContext) {
//            @Override
//            protected void onSuccess(APIResponse apiResponse) {
//                mutableLiveData.setValue(apiResponse);
//            }
//
//            @Override
//            protected void onError(BadRequest error) {
//                AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
//                alertDialog.setTitle(mContext.getString(R.string.sorry));
//                alertDialog.setMessage(error.errorDetails);
//                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, mContext.getString(R.string.dialog_ok),
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                alertDialog.show();
//            }
//        });
//        return mutableLiveData;
//    }

    public LiveData<APIResponse> getColorResponse(String auth) {
        final MutableLiveData<APIResponse> mutableLiveData = new MutableLiveData<>();
        API.retrofit().getColor(auth).enqueue(new APICallback<APIResponse>(mContext) {
            @Override
            protected void onSuccess(APIResponse apiResponse) {
                mutableLiveData.setValue(apiResponse);
            }

            @Override
            protected void onError(BadRequest error) {

            }
        });
        return mutableLiveData;
    }

    public LiveData<APIResponse> getCuttingOrderBySerialNumberResponse(String auth, String serialNumber) {
        final MutableLiveData<APIResponse> mutableLiveData = new MutableLiveData<>();
        FAPI.service().getCuttingOrderBySerialNumber(auth, serialNumber).enqueue(new APICallback<APIResponse>(mContext) {
            @Override
            protected void onSuccess(APIResponse apiResponse) {
                mutableLiveData.setValue(apiResponse);
            }

            @Override
            protected void onError(BadRequest error) {

            }
        });
        return mutableLiveData;
    }

    public LiveData<APIResponse> getLayingPlanningBySerialNumberResponse(String auth, String serialNumber) {
        final MutableLiveData<APIResponse> mutableLiveData = new MutableLiveData<>();
        FAPI.service().getLayingPlanningBySerialNumber(auth, serialNumber).enqueue(new APICallback<APIResponse>(mContext) {
            @Override
            protected void onSuccess(APIResponse apiResponse) {
                mutableLiveData.setValue(apiResponse);
            }

            @Override
            protected void onError(BadRequest error) {

            }
        });
        return mutableLiveData;
    }

    public LiveData<APIResponse> postStatusCutResponse(String auth, String serialNumber, String status) {
        final MutableLiveData<APIResponse> mutableLiveData = new MutableLiveData<>();
        FAPI.service().postStatusCut(auth, serialNumber, status).enqueue(new APICallback<APIResponse>(mContext) {
            @Override
            protected void onSuccess(APIResponse apiResponse) {
                mutableLiveData.setValue(apiResponse);
            }

            @Override
            protected void onError(BadRequest error) {

            }
        });
        return mutableLiveData;
    }

    public LiveData<APIResponse> searchCuttingOrderResponse(String auth, String serialNumber) {
        final MutableLiveData<APIResponse> mutableLiveData = new MutableLiveData<>();
        FAPI.service().searchCuttingOrder(auth, serialNumber).enqueue(new APICallback<APIResponse>(mContext) {
            @Override
            protected void onSuccess(APIResponse apiResponse) {
                mutableLiveData.setValue(apiResponse);
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    public void run() {
                        Extension.dismissLoading();
                    }
                });
            }

            @Override
            protected void onError(BadRequest error) {
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    public void run() {
                        Extension.dismissLoading();
                    }
                });
                AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                alertDialog.setTitle(mContext.getString(R.string.sorry));
                alertDialog.setMessage(error.errorDetails);
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, mContext.getString(R.string.dialog_ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
        return mutableLiveData;
    }

    public LiveData<APIResponse> getRemarksResponse(String auth) {
        final MutableLiveData<APIResponse> mutableLiveData = new MutableLiveData<>();
        FAPI.service().getRemarks(auth).enqueue(new APICallback<APIResponse>(mContext) {
            @Override
            protected void onSuccess(APIResponse apiResponse) {
                mutableLiveData.setValue(apiResponse);
            }

            @Override
            protected void onError(BadRequest error) {

            }
        });
        return mutableLiveData;
    }

    public LiveData<APIResponse> getCuttingTicketDetailResponse(String auth, int id) {
        final MutableLiveData<APIResponse> mutableLiveData = new MutableLiveData<>();
        FAPI.service().getCuttingTicketDetail(auth, id).enqueue(new APICallback<APIResponse>(mContext) {
            @Override
            protected void onSuccess(APIResponse apiResponse) {
                mutableLiveData.setValue(apiResponse);
            }

            @Override
            protected void onError(BadRequest error) {

            }
        });
        return mutableLiveData;
    }
}

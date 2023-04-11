package com.app.ivans.ghimli.repository;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.ivans.ghimli.model.APIResponse;
import com.app.ivans.ghimli.net.API;
import com.app.ivans.ghimli.net.APICallback;
import com.app.ivans.ghimli.net.BadRequest;
import com.app.ivans.ghimli.net.FAPI;
import com.google.gson.JsonElement;

import java.util.Map;
import java.util.Set;

public class UserRepository {
    private static final String TAG = "UserRepository";
    private Context mContext;

    public UserRepository(Context context) {
        this.mContext = context;
    }

    public LiveData<APIResponse> getUsersResponse(String auth) {
        final MutableLiveData<APIResponse> mutableLiveData = new MutableLiveData<>();
        API.retrofit().getUsers(auth).enqueue(new APICallback<APIResponse>(mContext) {
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

    public LiveData<APIResponse> userSignUpResponse(String name,String email, String password, String confirmPassword) {
        final MutableLiveData<APIResponse> mutableLiveData = new MutableLiveData<>();
        FAPI.service().userSignUp(name, email, password, confirmPassword).enqueue(new APICallback<APIResponse>(mContext) {
            @Override
            protected void onSuccess(APIResponse response) {
                mutableLiveData.setValue(response);
            }


            @Override
            protected void onError(BadRequest error) {
                if (error.code == 400) {
                        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                        alertDialog.setTitle("Sorry");
                        alertDialog.setMessage(error.errorDetails);
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                } else {
                    try {
                        Set<Map.Entry<String, JsonElement>> entries = error.errors.entrySet();//will return members of your object
                        for (Map.Entry<String, JsonElement> entry : entries) {
                            if (entry.getKey().matches("email")) {
                                Toast.makeText(mContext, entry.getValue().getAsString(), Toast.LENGTH_SHORT).show();
                            }
                            if (entry.getKey().matches("password")) {
                                Toast.makeText(mContext, entry.getValue().getAsString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (Exception exception) {
                        Log.e("loginAPI", "" + exception);
                        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                        alertDialog.setTitle("Sorry");
                        alertDialog.setMessage(error.errorDetails);
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }
            }
        });

        return mutableLiveData;
    }

    public LiveData<APIResponse> getUserLoginResponse(String email, String password) {
        final MutableLiveData<APIResponse> mutableLiveData = new MutableLiveData<>();
        FAPI.service().userSignIn(email, password).enqueue(new APICallback<APIResponse>(mContext) {
            @Override
            protected void onSuccess(APIResponse response) {
                mutableLiveData.setValue(response);
            }


            @Override
            protected void onError(BadRequest error) {
                if (error.code == 400) {
                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle("Sorry");
                    alertDialog.setMessage(error.errorDetails);
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                } else {
                    try {
                        Set<Map.Entry<String, JsonElement>> entries = error.errors.entrySet();//will return members of your object
                        for (Map.Entry<String, JsonElement> entry : entries) {
                            if (entry.getKey().matches("email")) {
                                Toast.makeText(mContext, entry.getValue().getAsString(), Toast.LENGTH_SHORT).show();
                            }
                            if (entry.getKey().matches("password")) {
                                Toast.makeText(mContext, entry.getValue().getAsString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (Exception exception) {
                        Log.e("loginAPI", "" + exception);
                        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                        alertDialog.setTitle("Sorry");
                        alertDialog.setMessage(error.errorDetails);
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }
            }
        });

        return mutableLiveData;
    }
}
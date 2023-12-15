package com.app.ivans.ghimli.net;

//import com.app.ivans.ghimli.BuildConfig;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;

import com.app.ivans.ghimli.BuildConfig;
import com.app.ivans.ghimli.HomeActivity;
import com.app.ivans.ghimli.LoginActivity;
import com.app.ivans.ghimli.R;
import com.app.ivans.ghimli.model.User;
import com.app.ivans.ghimli.utils.DateDeserializer;
import com.app.ivans.ghimli.utils.Extension;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import br.com.kots.mob.complex.preferences.ComplexPreferences;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    private static Converter<ResponseBody, BadRequest> ERROR_CONVERTER;
    private static boolean sessionError = false;

    public static APIService retrofit() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.ROOT_URL + "/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(APIService.class);
    }

    static Converter<ResponseBody, BadRequest> getErrorConverter() {
        return ERROR_CONVERTER;
    }

    public static void setSessionError(boolean sessionError) {
        API.sessionError = sessionError;
    }

    public static void setToken(Context context, String token) {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context, Extension.PREF_TOKEN, MODE_PRIVATE);
        complexPreferences.putObject(Extension.PREF_TOKEN_KEY, token);
        complexPreferences.commit();
    }

    public static String getToken(Context context) {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context, Extension.PREF_TOKEN, MODE_PRIVATE);
        return "Bearer" + " " + complexPreferences.getObject(Extension.PREF_TOKEN_KEY, String.class);
    }

    public static void setUser(Context context, User user) {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context, Extension.PREF_USER, MODE_PRIVATE);
        complexPreferences.putObject(Extension.PREF_USER_KEY, user);
        complexPreferences.commit();
    }

    public static User currentUser(Context context) {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context, Extension.PREF_USER, MODE_PRIVATE);
        return complexPreferences.getObject(Extension.PREF_USER_KEY, User.class);
    }

    public static void logOut(Activity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder
                .setMessage("Apakah Anda yakin ingin keluar ?")
                .setCancelable(true)
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                })
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ComplexPreferences complexPrefenreces = ComplexPreferences.getComplexPreferences(activity, Extension.PREF_USER, MODE_PRIVATE);
                        complexPrefenreces.putObject(Extension.PREF_USER_KEY, new User());
                        complexPrefenreces.commit();

                        activity.startActivity(new Intent(activity, LoginActivity.class));
                        activity.finish();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

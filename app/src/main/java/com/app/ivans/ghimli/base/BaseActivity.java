package com.app.ivans.ghimli.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.app.ivans.ghimli.utils.Extension;

//, VM extends BaseViewModel
public abstract class BaseActivity<BINDING extends ViewBinding> extends AppCompatActivity {

    //    protected VM viewModel;
    protected BINDING binding;
    private ProgressDialog mProgressDialog;

//    @NonNull
//    protected abstract VM createViewModel();

    @NonNull
    protected abstract BINDING createViewBinding(LayoutInflater layoutInflater);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        binding = createViewBinding(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        viewModel = createViewModel();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void showLoading() {
        hideLoading();
        mProgressDialog = Extension.showLoadingDialog(this);
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    public boolean isNetworkConnected() {
        return isNetworkConnected(getApplicationContext());
    }

//    public void openActivityOnTokenExpire() {
//        startActivity(LoginActivity.newIntent(this));
//        finish();
//    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }
}

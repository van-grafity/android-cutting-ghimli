package com.app.ivans.ghimli.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.app.ivans.ghimli.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import java.io.File;

public class Extension {
    private static final String TAG = "Extension";
    public static final String ROOT_URL = "http://192.168.5.57/";
    public static final String BEARER = "Bearer";
    private static Dialog dialog;
    public static final String AUTH = BEARER + " " + "5KdXFx1ZsUTtRXzzkWvoFaKgnFva2WKPFCSBX92J";
    public static final String PREF_TOKEN = "pref:token";
    public static final String PREF_TOKEN_KEY = "token";
    public static final String PREF_USER = "pref:user";
    public static final String PREF_USER_KEY = "user";
    public static final String KEYWORD = "keyword";
    public static final String HISTORY_DATA = "history_data";

    public static final String CUTTING_ORDER_RECORD = "cutting_order_record";

    public static void showLoading(final Activity context) {
        try {
            dialog = new Dialog(context, R.style.darkPopupAnimation);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loading_progressbar);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
            lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(lp);

            final TextView tvLoading = dialog.findViewById(R.id.tvLoading);
            ProgressBar indeterminateBar = dialog.findViewById(R.id.indeterminateBar);
            indeterminateBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, android.R.color.holo_blue_dark), PorterDuff.Mode.SRC_ATOP);
            tvLoading.setText(R.string.loading);

            if (!dialog.isShowing()) {
                dialog.show();
            }
        } catch (Exception exception) {
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
        }
    }

    public static void dismissLoading() {
        try {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception exception) {
            Log.e("DISMISS LOADING", "" + exception);
        }
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        Sprite doubleBounce = new DoubleBounce();
        progressDialog.setIndeterminateDrawable(doubleBounce);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public void showLoading(Context context, ProgressDialog progressDialog) {
        hideLoading(progressDialog);
        progressDialog = Extension.showLoadingDialog(context);
    }

    public static void hideLoading(ProgressDialog progressDialog) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    public static void setImage(Context context, ImageView view, String url) {
        try {
            if (url != null) {
                Glide.with(context)
                        .load(R.drawable.black_background)
                        .load(url)
                        .apply(RequestOptions.centerCropTransform())
                        .apply(RequestOptions.errorOf(R.drawable.black_background))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setImage(Context context, ImageView view, int drawable) {
        try {
            Glide.with(context)
                    .load(drawable)
                    .apply(RequestOptions.centerCropTransform())
                    .apply(RequestOptions.errorOf(R.drawable.black_background))
                    .into(view);
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }



    public static void setImage(Context context, ImageView view, File file) {
        try {
            if (file != null) {
                Glide.with(context)
                        .load(R.drawable.black_background)
                        .load(file)
                        .apply(RequestOptions.centerCropTransform())
                        .apply(RequestOptions.errorOf(R.drawable.black_background))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setImageFitCenter(Context context, ImageView view, String url) {
        try {
            if (url != null) {
                Glide.with(context)
                        .load(R.drawable.black_background)
                        .into(view);

                Glide.with(context)
                        .load(url)
                        .apply(RequestOptions.fitCenterTransform())
                        .apply(RequestOptions.errorOf(R.drawable.black_background))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setImageFitCenter(Context context, ImageView view, Integer drawable) {
        try {
            if (drawable != null) {
                Glide.with(context)
                        .load(R.drawable.black_background)
                        .into(view);

                Glide.with(context)
                        .load(drawable)
                        .apply(RequestOptions.fitCenterTransform())
                        .apply(RequestOptions.errorOf(R.drawable.black_background))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setImageFitCenter(Context context, ImageView view, File url) {
        try {
            if (url != null) {
                Glide.with(context)
                        .load(R.drawable.black_background)
                        .into(view);

                Glide.with(context)
                        .load(url)
                        .apply(RequestOptions.fitCenterTransform())
                        .apply(RequestOptions.errorOf(R.drawable.black_background))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setImageCenterCrop(Context context, ImageView view, int drawable) {
        try {

            Glide.with(context)
                    .load(R.drawable.black_background)
                    .into(view);

            Glide.with(context)
                    .load(drawable)
                    .apply(RequestOptions.centerCropTransform())
                    .apply(RequestOptions.errorOf(R.drawable.black_background))
                    .into(view);

        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setCircleImage(Context context, ImageView view, String url) {
        try {

            Glide.with(context)
                    .load(R.drawable.black_background)
                    .into(view);

            if (url != null) {

                Glide.with(context)
                        .load(url)
                        .apply(RequestOptions.circleCropTransform())
                        .apply(RequestOptions.errorOf(R.drawable.black_background))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setCircleImage(Context context, ImageView view, int image) {
        try {
            Glide.with(context)
                    .load(image)
                    .apply(RequestOptions.circleCropTransform())
                    .apply(RequestOptions.errorOf(R.drawable.black_background))
                    .into(view);
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setCircleImage(Context context, ImageView view, File file) {
        try {
            if (file != null) {
                Glide.with(context)
                        .load(R.drawable.black_background)
                        .into(view);

                Glide.with(context)
                        .load(file)
                        .apply(RequestOptions.circleCropTransform())
                        .apply(RequestOptions.errorOf(R.drawable.black_background))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setCircleStoreImage(Context context, ImageView view, String url) {
        try {

            Glide.with(context)
                    .load(R.drawable.black_background)
                    .into(view);

            if (url != null && url != "") {

                Glide.with(context)
                        .load(url)
                        .apply(RequestOptions.circleCropTransform())
                        .apply(RequestOptions.errorOf(R.drawable.black_background))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setCircleProfileImage(Context context, ImageView view, String url) {
        try {

            Glide.with(context)
                    .load(R.drawable.black_background)
                    .into(view);

            if (url != null && url != "") {

                Glide.with(context)
                        .load(url)
                        .apply(RequestOptions.circleCropTransform())
                        .apply(RequestOptions.errorOf(R.drawable.black_background))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setCircleProfileImage(Context context, ImageView view, int image) {
        try {
            Glide.with(context)
                    .load(image)
                    .apply(RequestOptions.circleCropTransform())
                    .apply(RequestOptions.errorOf(R.drawable.black_background))
                    .into(view);
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setCircleProfileImage(Context context, ImageView view, File file) {
        try {
            if (file != null) {
                Glide.with(context)
                        .load(R.drawable.black_background)
                        .into(view);

                Glide.with(context)
                        .load(file)
                        .apply(RequestOptions.circleCropTransform())
                        .apply(RequestOptions.errorOf(R.drawable.black_background))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setRoundedCornerImage(Context context, ImageView view, String url) {
        try {
            if (url != null) {
                Glide.with(context)
                        .load(url)
                        .apply(new RequestOptions().transforms(new RoundedCorners(20)))
                        .apply(RequestOptions.errorOf(R.drawable.black_background))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setRoundedCornerImageFitCenter(Context context, ImageView view, String url) {
        try {
            if (url != null) {
                Glide.with(context)
                        .load(url)
                        .apply(new RequestOptions().transforms(new RoundedCorners(20), new FitCenter()))
                        .apply(RequestOptions.errorOf(R.drawable.black_background))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setRoundedCornerImageCenterCrop(Context context, ImageView view, String url) {
        try {
            if (url != null) {
                Glide.with(context)
                        .load(url)
                        .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(20)))
                        .apply(RequestOptions.errorOf(R.drawable.black_background))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setRoundedCornerStoreBannerImage(Context context, ImageView view, String url) {
        try {
            Glide.with(context)
                    .load(R.drawable.black_background)
                    .apply(new RequestOptions().transforms(new FitCenter(), new RoundedCorners(20)))
                    .into(view);

            if (url != null && !url.equals("")) {
                Glide.with(context)
                        .load(url)
                        .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(20)))
                        .apply(RequestOptions.errorOf(R.drawable.black_background))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setProfileImage(Context context, ImageView view, String url) {
        try {
            if (url != null) {
                Glide.with(context)
                        .load(R.drawable.black_background)
                        .load(url)
                        .apply(RequestOptions.centerCropTransform())
                        .apply(RequestOptions.errorOf(R.drawable.black_background))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setProfileImage(Context context, ImageView view, File file) {
        try {
            if (file != null) {
                Glide.with(context)
                        .load(R.drawable.black_background)
                        .load(file)
                        .apply(RequestOptions.centerCropTransform())
                        .apply(RequestOptions.errorOf(R.drawable.black_background))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }
}

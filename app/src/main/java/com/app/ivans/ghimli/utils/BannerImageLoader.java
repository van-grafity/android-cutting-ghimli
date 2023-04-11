package com.app.ivans.ghimli.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

public class BannerImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Extension.setImage(context, imageView, path.hashCode());
        DisplayMetrics displayMetrics = new DisplayMetrics();
    }


}
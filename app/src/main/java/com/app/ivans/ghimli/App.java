package com.app.ivans.ghimli;

import android.app.Application;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;

import com.app.ivans.ghimli.utils.InternetUtil;

import br.com.kots.mob.complex.preferences.ComplexPreferences;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/Lato-Medium.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        InternetUtil.init(this);
    }


}


package com.app.ivans.ghimli;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.app.ivans.ghimli.databinding.ActivityDashboardAnalyticsBinding;
import com.app.ivans.ghimli.model.APIResponse;
import com.app.ivans.ghimli.model.FGL;
import com.app.ivans.ghimli.model.LayingPlanning;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DashboardAnalyticsActivity extends AppCompatActivity {
    private static final String TAG = "DashboardAnalyticsActiv";
    private ActivityDashboardAnalyticsBinding binding;
    private LineDataSet totalsDataSet;
    ArrayList<LayingPlanning> myItems = new ArrayList<>();
    private ArrayList totals;
    private ArrayList<String> dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardAnalyticsBinding.inflate(LayoutInflater.from(DashboardAnalyticsActivity.this));
        setContentView(binding.getRoot());

        totals = new ArrayList();
        dates = new ArrayList();

        Gson gson = new Gson();
        APIResponse datas = gson.fromJson(loadJSONFromRes(), APIResponse.class);
        Log.i(TAG, "onCreate: json " + datas.getData().getGl().get(0).getBuyer());

        for (int i = 0; i < datas.getData().getGl().size(); i++) {
            FGL FGL = datas.getData().getGl().get(i);
            Log.i(TAG, "onCreate: " + FGL.getBuyer());
            totals.add(new Entry(i, (float) FGL.getTotal()));
            dates.add(FGL.getDate());

        }

        setDataTotal();
//        getJsonFileFromLocally();
    }

    public void setDataTotal() {
        XAxis xAxis = binding.chart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.mAxisMaximum = (float) dates.size() - 1;
        xAxis.mAxisMinimum = 0f;
        xAxis.setDrawLimitLinesBehindData(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.RED);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = binding.chart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        //leftAxis.axisMaximum = (y.size - 1).toFloat()
        leftAxis.mAxisMinimum = 0f;
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawLimitLinesBehindData(false);
        //leftAxis.setValueFormatter { value, _ ->
        //moneys[value.toInt()]
        //}

        totalsDataSet = new LineDataSet(totals, "Totals");
        totalsDataSet.setDrawIcons(false);
        totalsDataSet.enableDashedLine(10f, 5f, 0f);
        totalsDataSet.enableDashedHighlightLine(10f, 5f, 0f);
        totalsDataSet.setColors(Color.parseColor("#00B082"));
        totalsDataSet.setCircleColor(Color.parseColor("#00B082"));
        totalsDataSet.setLineWidth(1f);
        totalsDataSet.setCircleRadius(3f);
        totalsDataSet.setDrawCircleHole(false);
        totalsDataSet.setValueTextSize(9f);
        totalsDataSet.setDrawFilled(true);
        totalsDataSet.setDrawValues(false);
        totalsDataSet.setFormLineWidth(1f);
        float[] floatArray = new float[]{10f, 5f};
        totalsDataSet.setFormLineDashEffect(new DashPathEffect(floatArray, 0f));
        totalsDataSet.setFormSize(15f);

        if (Utils.getSDKInt() >= 18) {
            //val drawable = ContextCompat.getDrawable(context!!, R.drawable.fade_blue)
//            ContextCompat drawable2 = ContextCompat.getDrawable(DashboardAnalyticsActivity.this, getResources().getDrawable(0));
            totalsDataSet.setFillDrawable(null);
        } else {
            totalsDataSet.setFillColor(Color.parseColor("#00B082"));
        }

        ArrayList dataSets = new ArrayList<>();
        dataSets.add(totalsDataSet);
        LineData data = new LineData(dataSets);

        binding.chart.setData(data);
        binding.chart.invalidate();
    }

    public String loadJSONFromRes() {
        String data = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.data);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            data = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return data;
    }

    private void getJsonFileFromLocally() {
        try {

            JSONObject jsonObject = new JSONObject(loadJSONFromRes());
            String ftotalDiscount = jsonObject.getString("ftotal_discount");

            Log.i(TAG, "getJsonFileFromLocally: ftotalDiscount " + ftotalDiscount);


            JSONArray jsonArray = jsonObject.getJSONArray("items");

            for (int i = 0; i < jsonArray.length(); i++) {
                LayingPlanning itemsModel = new LayingPlanning();

                JSONObject jsonObjectItems = jsonArray.getJSONObject(i);

                String itemProductName = jsonObjectItems.getString("product_name");

//                itemsModel.setName("" + itemProductName);

                myItems.add(itemsModel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
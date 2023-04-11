package com.app.ivans.ghimli.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.app.ivans.ghimli.R;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


public class NetworkFunctions {
    private static final String TAG = "NetworkFunctions";

    private Context mContext;
    public static final int NOT_CONNECTED = 0;
    public static final int WIFI = 1;
    public static final int MOBILE = 2;

    public NetworkFunctions(Context mContext) {
        this.mContext = mContext;
    }

    public enum NetworkStatus {
        DISCONNECTED,
        CONNECTED
    }

    public void show(NetworkStatus type) {
//        LayoutInflater myInflater = LayoutInflater.from(mContext);
//        View view = myInflater.inflate(R.layout.dialog_network_status, null);
//        ButterKnife.bind(this, view);

        Log.i(TAG, "show: " + messageInfo(mContext, type));
//        Toast toast = new Toast(mContext);
//        toast.setGravity(Gravity.CENTER | Gravity.TOP, 0, 50);
//        toast.setView(view);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.show();
        if (type == NetworkStatus.CONNECTED) {
            Log.i(TAG, "show: Connected");
        } else {
            Log.i(TAG, "show: Disconnected");
        }
    }

    public String messageInfo(Context context, NetworkStatus status) {
        String msg = "";
        if (status.equals(NetworkStatus.CONNECTED)) {
            Log.i(TAG, "messageInfo: msg " + msg + "" + context.getString(R.string.connected));
//            msg += context.getString(R.string.network_connected);

        }
        if (status.equals(NetworkStatus.DISCONNECTED)) {
            Log.i(TAG, "messageInfo: msg " + msg + "" + context.getString(R.string.not_connected));
        }
        return msg;
    }

    public static int getConnectionStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return WIFI;
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return MOBILE;
        }
        return NOT_CONNECTED;
    }

    public static String getConnectionStatusString(Context context) {
        int connectionStatus = NetworkFunctions.getConnectionStatus(context);
        if (connectionStatus == NetworkFunctions.WIFI)
            return "Connected to Wifi";
        if (connectionStatus == NetworkFunctions.MOBILE)
            return "Connected to Mobile Data";
        return "No internet connection";
    }

    public static String getWifiName(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String wifiName = wifiManager.getConnectionInfo().getSSID();
        if (wifiName != null) {
            if (!wifiName.contains("unknown ssid") && wifiName.length() > 2) {
                if (wifiName.startsWith("\"") && wifiName.endsWith("\""))
                    wifiName = wifiName.subSequence(1, wifiName.length() - 1).toString();
                return wifiName;
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public static String getMobileNetworkName(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String networkName = tm.getNetworkOperatorName();
        if (networkName != null) {
            return networkName;
        }
        return "";
    }

    public static String getGateway(Context context) {
        WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return NetworkFunctions.intToIp(wm.getDhcpInfo().gateway);
    }

    public static String getIpAddress(Context context) {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface.getNetworkInterfaces();

            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces.nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface.getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip = inetAddress.getHostAddress();
                    } else {
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }
        return ip;
    }

    public static String intToIp(int i) {
        return ((i >> 24) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                (i & 0xFF);
    }

    public void showWifiDialog(Context context) {
        AlertDialog wifiDialog = new AlertDialog.Builder(context). //create a dialog
                setTitle("No Wifi Connection").
                setMessage("No Wifi connection detected. What would you like to do?").
                setNeutralButton("Activate Wifi", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) { //the user clicked yes
                        activateWifi(); //activate the device's Wifi and bring up the Wifi settings screen
                    }
                }).setNegativeButton("Settings", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mContext.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                }).setPositiveButton("Nothing", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setCancelable(false).create();
        wifiDialog.show(); //show the dialog
    }

    public void activateWifi() {
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
    }
}

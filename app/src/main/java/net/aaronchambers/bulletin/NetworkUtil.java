package net.aaronchambers.bulletin;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class NetworkUtil {

    public static String getMacAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = null;

        if (wifiManager != null)
            wifiInfo = wifiManager.getConnectionInfo();

        if (wifiInfo != null)
            return wifiInfo.getBSSID();

        return "ERROR";
    }

    public static String getSSID(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = null;

        if (wifiManager != null)
            wifiInfo = wifiManager.getConnectionInfo();
        Log.i("SSID", wifiInfo.getSSID());
        if (wifiInfo != null)
            return wifiInfo.getSSID();


        return "ERROR";
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return netInfo.isConnected();
    }
}

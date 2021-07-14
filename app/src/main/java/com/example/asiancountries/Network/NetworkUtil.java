package com.example.asiancountries.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class NetworkUtil {
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getConnectivityStatusString(Context context) {
        String status = null;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                Number downSpeed = networkCapabilities.getLinkDownstreamBandwidthKbps();
                return String.valueOf(downSpeed);
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                if (isConnectedFast(context)) {
                    status = "fast";
                } else {
                    status = "slow";
                }
            }
        } else {
            status = "0";
            return status;
        }
        return status;
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }

    public static boolean isConnectedFast(Context context) {
        NetworkInfo info = NetworkUtil.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }
}

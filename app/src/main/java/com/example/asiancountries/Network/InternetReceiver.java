package com.example.asiancountries.Network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class InternetReceiver extends BroadcastReceiver {

    private  getConnection connection;
    public InternetReceiver(){}
    public InternetReceiver(getConnection connection) {
        this.connection = connection;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        String status=NetworkUtil.getConnectivityStatusString(context);
        if(status.equals("0")){
            status="No internet connection";
            connection.getNoConnectionValue(status);
        }
        else{
            connection.getYesConnectionValue("Online",status);
        }
    }
    public interface getConnection{
        void getNoConnectionValue(String text);
        void getYesConnectionValue(String online,String text);
    }
}

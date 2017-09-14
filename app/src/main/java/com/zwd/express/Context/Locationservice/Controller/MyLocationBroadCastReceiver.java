package com.zwd.express.Context.Locationservice.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyLocationBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        context.startService(new Intent(context,MyLocationService.class));
    }
}

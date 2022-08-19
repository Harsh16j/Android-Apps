package com.example.application2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class RestaurantsBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context,"Restaurants Broadcast Receiver", Toast.LENGTH_SHORT).show();
        context.startActivity(
                new Intent(context,Restaurants_Activity.class));
    }
}

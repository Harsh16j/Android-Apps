package com.example.application2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AttractionsBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startActivity(
                new Intent(context, Attractions_Activity.class));
        //Toast.makeText(context,"Attractions Broadcast Receiver", Toast.LENGTH_SHORT).show();
    }
}


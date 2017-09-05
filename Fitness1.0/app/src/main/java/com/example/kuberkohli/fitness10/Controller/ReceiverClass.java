package com.example.kuberkohli.fitness10.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.kuberkohli.fitness10.View.InitialViewActivity;

/**
 * Created by kuberkohli on 4/27/17.
 */

public class ReceiverClass extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        /*Sent when the user is present after
         * device wakes up (e.g when the keyguard is gone)
         * */
        if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){
            Intent i = new Intent();
            i.setClassName("com.example.kuberkohli.fitness10", "com.example.kuberkohli.fitness10.View.InitialViewActivity");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);

        }
        /*Device is shutting down. This is broadcast when the device
         * is being shut down (completely turned off, not sleeping)
         * */
        else if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }
}

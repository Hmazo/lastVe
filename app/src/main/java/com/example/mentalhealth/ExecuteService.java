package com.example.mentalhealth;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ExecuteService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        CustumDialogClass cdd=new CustumDialogClass(context);
        cdd.show();
    }
}

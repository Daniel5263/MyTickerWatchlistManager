package com.example.mytickerwatchlistmanager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus != null) {
                SmsMessage[] messages = new SmsMessage[pdus.length];
                StringBuilder smsText = new StringBuilder();
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    smsText.append(messages[i].getMessageBody());
                }

                Intent mainActivityIntent = new Intent(context, MainActivity.class);
                mainActivityIntent.setAction(Intent.ACTION_SEND);
                mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                Bundle smsBundle = new Bundle();
                smsBundle.putString("smsText", smsText.toString());
                mainActivityIntent.putExtras(smsBundle);

                context.startActivity(mainActivityIntent);
            }
        }
    }
}

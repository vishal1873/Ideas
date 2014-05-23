package com.specialkid.main;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ServiceAlarm extends BroadcastReceiver {

	
	
	@Override
	public void onReceive(Context context, Intent arg1) {
		
		Log.d("ServiceAlarm", "Inside ServiceAlarm");
		

		PendingIntent service = null; 
        Intent intentForService = new Intent(context.getApplicationContext(), DataService.class);
        
        final AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        
        final Calendar time = Calendar.getInstance();
        time.set(Calendar.MINUTE, 0);
        time.set(Calendar.SECOND, 0); 
        time.set(Calendar.MILLISECOND, 0);
        
        if (service == null) {
            service = PendingIntent.getService(context, 0,
                    intentForService,    PendingIntent.FLAG_CANCEL_CURRENT);
        }

        alarmManager.setRepeating(AlarmManager.RTC, time.getTimeInMillis(), 60000, service);

		 
		
	}

}

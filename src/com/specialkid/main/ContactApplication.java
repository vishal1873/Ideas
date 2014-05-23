package com.specialkid.main;



import android.app.Application;
import android.content.Intent;

public class ContactApplication extends Application {

		
	public void onCreate ()
	{
		// 
						
		// Start the DataService. It should ideally start through service alarm
		//but there seems to be some problem when using it on the main device
		
		Intent intent = new Intent(this, DataService.class);
		startService(intent);
	}
	

	
}

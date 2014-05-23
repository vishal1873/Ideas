package com.specialkid.main;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

/*
 This is the main screen. 
 This will be like a main menu for the application
*/
public class MainActivity extends Activity {
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.main_entry_grid_layout);

	    GridView gridview = (GridView) findViewById(R.id.maingridview);
	    gridview.setAdapter(new ImageAdapter(this));

	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
				
				Intent myIntent = new Intent(MainActivity.this,ServiceProviderListView.class);
				startActivity(myIntent); 
			}
	    });

		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

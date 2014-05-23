package com.specialkid.main;


import java.util.List;






import android.os.AsyncTask;
import android.os.Bundle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.TextView;

public class ServiceProviderListView extends Activity {

	private ListView list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts_layout);
		list = (ListView) findViewById(R.id.listView);
	

    }
	
	@Override
	protected void onResume () {
		super.onResume();
		
		//This needs to be see whether the service needs to be called again.
		//This is inefficient.
		
		Intent intent = new Intent(this, DataService.class);
		startService(intent);
		
		
		ProviderInfoTask t = new ProviderInfoTask ();
		t.execute();

    }
	
	
    /*
     * This task gets the info from the database 
     */
	private class ProviderInfoTask extends AsyncTask<Void, Void, Void> {

		private InfoDataSource dataSource = new InfoDataSource(ServiceProviderListView.this);
		private List<Info> infolist;

		@Override
		protected Void doInBackground(Void... params) {
			try {
				dataSource.open();
				infolist = dataSource.getAllInfo();

			} catch (Exception e) {
				Log.e("ItemFeed", "Error loading JSON", e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			InfoAdapter adapter = new InfoAdapter(ServiceProviderListView.this,infolist) ;
			list.setAdapter(adapter);
		}
	}

}

class InfoAdapter extends ArrayAdapter<Info> {
	int size = 1;
	Context context;

	

	InfoAdapter(Context c, List <Info> items) {
		super(c, R.layout.single_row_in_contact_list, items);
		this.context = c;
	}	

	public View getView(int position, View convertView, ViewGroup parent) {

		// Need to implement the view holder pattern.
		// There is a question why does not the List holds a cache of these
		// views.
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = inflater.inflate(R.layout.single_row_in_contact_list,
				parent, false);
		
	    Info p = getItem(position);
	    
		TextView myTitle = (TextView) row.findViewById(R.id.textView1);
		myTitle.setText(p.getName());
		TextView myDescription = (TextView) row.findViewById(R.id.textView2);
		myDescription.setText(p.getPhone());
		return row;
	}

}

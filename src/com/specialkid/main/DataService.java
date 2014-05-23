package com.specialkid.main;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;



public class DataService extends Service {

	
	//TODO: To see if datasources are being closed properly
	private InfoDataSource dataSource;

	@Override
	public void onCreate() {
		super.onCreate();

		// The service is running as separate process.
		dataSource = new InfoDataSource(this);
		dataSource.open();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		AppScriptTask task = new AppScriptTask();
		task.execute();

		Log.d("Data Service", "Inside onStartCommand new ");
		return Service.START_NOT_STICKY;

	}

	private class AppScriptTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				HttpClient hc = new DefaultHttpClient();

				String version = getVersion();

				String URL = "https://script.google.com/macros/s/AKfycbyRdUf6kv3JEyLH0zFANQv4qXtld8pMsoGMKVxsmCAYGHUfkBQ/exec?version="
						+ version;
				HttpGet get = new HttpGet(URL);
				HttpResponse rp = hc.execute(get);

				if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

					String result = EntityUtils.toString(rp.getEntity());
					if (isDataonServerNew(result)) {
						dropDatabase();
						addToDatabase(result);
					}

				}
			} catch (Exception e) {
				Log.e("ItemFeed", "Error loading JSON", e);
			}
			return null;
		}

		private void dropDatabase() {
			dataSource.deleteDataFromInfoTable();

		}

		private void addToDatabase(String result) throws IOException,
				JSONException {
			

			JSONArray objects = new JSONArray(result);

			for (int i = 0; i < objects.length(); i++) {
				JSONObject session = objects.getJSONObject(i);

				dataSource.createInfo(session.getString("name"),
						session.getString("phone"),
						session.getString("version"));

			}

		}

		private boolean isDataonServerNew(String result) {

			try {
				JSONArray objects = new JSONArray(result);

				for (int i = 0; i < objects.length(); i++) {

					JSONObject session = objects.getJSONObject(i);
					Integer ver = (Integer) session.get("version");

					if (ver.intValue() == -1)

					{
						System.out.println("Before returning ");
						return false;
					}

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				return false;
			}

			return true;
			// TODO Auto-generated method stub

		}

		private String getVersion() {

			List<Info> list = dataSource.getAllInfo();

			if (list.isEmpty())
				return "0";
			else
				return list.get(0).getVersion();

		}

		@Override
		protected void onPostExecute(Void result) {
			// This should give some visual indicator that activity is still
			// loading.
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}

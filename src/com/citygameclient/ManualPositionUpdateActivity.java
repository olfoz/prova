package com.citygameclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ManualPositionUpdateActivity extends Activity{

	TextView textView;
	Spinner spinner;
	EditText ePos;
	EditText nPos;
	static String updateUrl ="http://laghenga.altervista.org/citygame/insertpeoplepos.php";
	static String listUrl ="http://laghenga.altervista.org/citygame/peoplelist.php";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		 setContentView(R.layout.update_person_pos_man_act);
		 textView = new TextView(this);
		 new SpinnerFillTask().execute(listUrl);
	   
	}
	private class SpinnerFillTask extends AsyncTask<String, Void, String> {
		 @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            textView.setText("Loading...");
	            setContentView(textView);
	        }

		    protected String doInBackground(String... urls) {
		        try {
		            return httpResult(urls[0]);
		        } catch (Exception e) {
		            return null;
		        }
		    }

		    protected void onPostExecute(String result) {
		    		setContentView(R.layout.update_person_pos_man_act);
				    spinner= (Spinner)findViewById(R.id.spinner1);
				    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(ManualPositionUpdateActivity.this, android.R.layout.simple_spinner_dropdown_item, JSONParser(result));
				    spinner.setAdapter(spinnerArrayAdapter);	

					
					       
					ePos = (EditText) findViewById(R.id.epos_value);
					nPos = (EditText) findViewById(R.id.npos_value);
				    final Button button = (Button) findViewById(R.id.button_invia);
			        button.setOnClickListener(new View.OnClickListener() {
			            public void onClick(View v) {
			            	String user =String.valueOf(spinner.getSelectedItem());
			            	String longitude= ePos.getText().toString();
			            	String latitude= nPos.getText().toString();
			            	 new updateTask().execute(updateUrl,user,longitude,latitude);
			            }
			        });
			     }
		}
	
	public String httpResult(String url){
		 HttpClient httpClient = new DefaultHttpClient();
		 HttpPost httpPost = new HttpPost(url);
		 List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
		 HttpResponse httpResponse;
		 InputStream is;
		try {
				httpPost.setEntity(new UrlEncodedFormEntity(nameValue));
				httpResponse = httpClient.execute(httpPost);
				is = httpResponse.getEntity().getContent();
			} 
		catch (ClientProtocolException e1) {
				is=null;
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		catch (IOException e1) {
				is=null;
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		return convertStreamToString(is);	
		}
	 
	 private String convertStreamToString(InputStream is) {
		    /*
		     * To convert the InputStream to String we use the BufferedReader.readLine()
		     * method. We iterate until the BufferedReader return null which means
		     * there's no more data to read. Each line will appended to a StringBuilder
		     * and returned as String.
		     */
		    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		    StringBuilder sb = new StringBuilder();

		    String line = null;
		    try {
		        while ((line = reader.readLine()) != null) {
		            sb.append(line + "\n");
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            is.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		    return sb.toString();
		}
	 public  ArrayList<String> JSONParser(String JSONSource){
			ArrayList<String> resultArray= new ArrayList<String>();
			JSONArray jsonArray;
			try {
				jsonArray = new JSONArray(JSONSource);
				for (int i=0;i<jsonArray.length();i++){
					JSONObject jsonData= jsonArray.getJSONObject(i);
					String currPerson= jsonData.getString("NICKNAME");
					resultArray.add(currPerson);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return resultArray;
		}
	 
	 public String httpPostValues(String url,List<NameValuePair> nameValueList){
		 HttpClient httpClient = new DefaultHttpClient();
		 HttpPost httpPost = new HttpPost(url);
		 HttpResponse httpResponse;
		 InputStream is;
		try {
				httpPost.setEntity(new UrlEncodedFormEntity(nameValueList));
				httpResponse = httpClient.execute(httpPost);
				is = httpResponse.getEntity().getContent();
			} 
		catch (ClientProtocolException e1) {
				is=null;
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		catch (IOException e1) {
				is=null;
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		return convertStreamToString(is);	
		}

	 private class updateTask extends AsyncTask<String, Void, String> {
		 @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            textView.setText("Loading...");
	            setContentView(textView);
	        }

		    protected String doInBackground(String... param) {
		        try {
		   		 	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		   		 	nameValuePairs.add(new BasicNameValuePair("NICKNAME",param[1]));
		   		 	nameValuePairs.add(new BasicNameValuePair("EPOS",param[2]));
		   		 	nameValuePairs.add(new BasicNameValuePair("NPOS",param[3]));

		            return httpPostValues(param[0],nameValuePairs);
		        } catch (Exception e) {
		            return null;
		        }
		    }

		    protected void onPostExecute(String result) {
		    	setContentView(R.layout.webpage_source_display);
		    	TextView websourceDisplay=(TextView)findViewById(R.id.websource);
	    	    websourceDisplay.setText(result);
			     }
		}

}

package com.citygameclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ManualPositionUpdateActivity extends Activity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		 setContentView(R.layout.update_person_pos_man_act);
	    InputStream is= getInputStreamFromUrl("http://laghenga.altervista.org/citygame/peoplelist.php");
	    String inputString=convertStreamToString( is);
	    Spinner spinner= (Spinner)findViewById(R.id.spinner1);
	    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, JSONParser(inputString));
	    spinner.setAdapter(spinnerArrayAdapter);
	    final Button button = (Button) findViewById(R.id.button_invia);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	//http post
            	try{
            	        HttpClient httpclient = new DefaultHttpClient();
            	        HttpPost httppost = new HttpPost("http://laghenga.altervista.org/citygame/insertpeoplepos.php");
            	        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            	        nameValuePairs.add(new BasicNameValuePair("NICKNAME","Alscocco"));
            	        nameValuePairs.add(new BasicNameValuePair("EPOSITION","2"));
            	        nameValuePairs.add(new BasicNameValuePair("NPOSITION","3"));
            	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            	        HttpResponse response = httpclient.execute(httppost);
            	        HttpEntity entity = response.getEntity();
            	        InputStream is = entity.getContent();
            	}catch(Exception e){
            	        Log.e("log_tag", "Error in http connection "+e.toString());
            	}
            	//convert response to string
            }
        });

	}

	 public InputStream getInputStreamFromUrl(String url) {
		  InputStream content = null;
		  try {
		    HttpClient httpclient = new DefaultHttpClient();
		    HttpResponse response = httpclient.execute(new HttpGet(url));
		    content = response.getEntity().getContent();
		  } catch (Exception e) {
		    Log.w("[GET REQUEST]", "Network exception", e);
		  }
		    return content;
		}
	 
	 public String convertStreamToString(InputStream is) {
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
}

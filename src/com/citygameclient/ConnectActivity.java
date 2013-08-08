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
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
 
public class ConnectActivity extends Activity {
	 @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    setContentView(R.layout.webpage_source_display);
	    new HttpTask().execute("http://laghenga.altervista.org/citygame/peoplelist.php");
	    //new HttpTask().execute("http://192.168.1.52/umana-magazzino-web/LoginService?username=2&password=2");
	    
	  }

	 
	 private class HttpTask extends AsyncTask<String, Void, String> {


		    protected String doInBackground(String... urls) {
		        try {
		            HttpClient httpClient = new DefaultHttpClient();
		            HttpPost httpPost = new HttpPost(urls[0]);

		            List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
		            httpPost.setEntity(new UrlEncodedFormEntity(nameValue));

		            HttpResponse httpResponse = httpClient.execute(httpPost);

		            HttpEntity httpEntity = httpResponse.getEntity();

		            InputStream is = httpEntity.getContent();
		            return convertStreamToString( is);

		        } catch (Exception e) {
		            return null;
		        }
		        
		    }

		    protected void onPostExecute(String result) {
		    	try {
		            if(result == null)
						
							throw new Exception("result is null");
		            else {
			    		TextView websourceDisplay=(TextView)findViewById(R.id.websource);
			    	    websourceDisplay.setText(result);
			    	}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    	
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
			 
		}

	
	
	 
    }

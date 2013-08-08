package com.citygameclient;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
 
public class DisplayJsonPersonActivity extends Activity {
	 ArrayList<Person> personArray =new ArrayList<Person>();
	 TextView textView;

	 @Override
	  public void onCreate(Bundle savedInstanceState) {
			    super.onCreate(savedInstanceState);

	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		 setContentView(R.layout.activity_person_list);
		 textView = new TextView(this);
		 new HttpTask().execute("http://laghenga.altervista.org/citygame/peoplelist.php");

	 	    
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
	 
	 private class HttpTask extends AsyncTask<String, Void, String> {
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
		    	try {
		            if(result == null)
						
							throw new Exception("result is null");
		            else {
		            	ArrayList<Person> resultArray= new ArrayList<Person>();
		        		JSONArray jsonArray;
		        		try {
		        				jsonArray = new JSONArray(result);
		        				for (int i=0;i<jsonArray.length();i++){
		        					JSONObject jsonData= jsonArray.getJSONObject(i);
		        					Person currPerson= new Person();
		        					currPerson.nickname=jsonData.getString("NICKNAME");
		        					currPerson.EPos=jsonData.getDouble("EPOSITION");
		        					currPerson.NPos=jsonData.getDouble("NPOSITION");
		        					resultArray.add(currPerson);
		        				}
		        				
		        			} catch (JSONException e) {
		        				// TODO Auto-generated catch block
		        				e.printStackTrace();
		        			}
		        		
		        		setContentView(R.layout.activity_person_list);
		        	    ListView listView = (ListView) findViewById(R.id.list);
		        	    PersonAdapter adapter = new PersonAdapter(DisplayJsonPersonActivity.this, R.layout.listview_person_raw, resultArray);
		        	    listView.setAdapter(adapter);
			    	}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    	
		    }

			 
			

			 
				
		}

	
	
	 
    }

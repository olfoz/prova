package com.citygameclient;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
 
public class DisplayJsonPersonActivity extends Activity {
	
	ArrayList<Person> personArray =new ArrayList<Person>();


	

	 @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		 setContentView(R.layout.activity_person_list);
	    InputStream is= getInputStreamFromUrl("http://laghenga.altervista.org/citygame/peoplelist.php");
	    String inputString=convertStreamToString( is);
	    
	    ArrayList<Person> myList = JSONParser(inputString);
	    ListView listView = (ListView) findViewById(R.id.list);
	    PersonAdapter adapter = new PersonAdapter(this, R.layout.listview_person_raw, myList);
	    listView.setAdapter(adapter);
	  }


	 public static InputStream getInputStreamFromUrl(String url) {
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
	 
	 private static String convertStreamToString(InputStream is) {
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
	 
	private ArrayList<Person> JSONParser(String JSONSource){
		ArrayList<Person> resultArray= new ArrayList<Person>();
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(JSONSource);
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
		return resultArray;
	}
	 
    }

package com.citygameclient;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class DisplayListActivity extends ListActivity {
	String Lista[]={"ConnectActivity","DisplayListActivity","map"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Lista));
	}


}
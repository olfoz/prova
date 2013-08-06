package com.citygameclient;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainMenu extends ListActivity {
	String Lista[]={"DisplayListActivity","ConnectActivity","DisplayJsonStringsActivity","DisplayJsonPersonActivity","ManualPositionUpdateActivity"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Lista));
	}

	protected void onListItemClick(ListView lv,View v, int position,long id){
		super.onListItemClick(lv, v, position, id) ;
		String openClass=Lista[position];
		try{
			Class selected = Class.forName("com.citygameclient."+openClass);
			Intent selectedIntent = new Intent(this,selected);
			startActivity (selectedIntent);
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	}
}
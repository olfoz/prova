package com.citygameclient;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PersonAdapter extends ArrayAdapter<Person> {

    private Context context;
    int layoutResourceId;   
    ArrayList<Person> items = null;
    
    public PersonAdapter(Context context, int layoutResourceId, ArrayList<Person> items) {
        super(context, layoutResourceId, items);
        this.context = context;
        this.layoutResourceId=layoutResourceId;
        this.items=items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PersonHolder holder;
        if (row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new PersonHolder();
            holder.nicknameHolder = (TextView)row.findViewById(R.id.nickname);
            holder.eposHolder = (TextView)row.findViewById(R.id.epos);
            holder.nposHolder=(TextView)row.findViewById(R.id.npos);

            row.setTag(holder);
        }
        else {
        	holder=(PersonHolder)row.getTag();
        }

        Person item = getItem(position);
        if (item!= null) {
           holder.nicknameHolder.setText(item.nickname);
           holder.nposHolder.setText(String.format( "longitude: %.6f", item.EPos));
           holder.eposHolder.setText(String.format( "latitude: %.6f", item.NPos));    
            }
         

        return row;
    }
    
    static class PersonHolder
    {

        TextView nicknameHolder;
        TextView eposHolder;  
        TextView nposHolder;
        
    }
}

package com.example.proyectonotas;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class miArrayAdapter extends ArrayAdapter {

    public miArrayAdapter(Context context, int resource, List<String> objects){
        super(context, resource, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        TextView tv = (TextView) super.getView(position, convertView, parent);
        tv.setTextSize(20.0f);

        tv.setTextColor(Color.BLACK);
        return tv;
    }

}

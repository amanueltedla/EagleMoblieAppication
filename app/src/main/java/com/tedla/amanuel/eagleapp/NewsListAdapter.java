package com.tedla.amanuel.eagleapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by dVentus-hq on 6/17/2017.
 */
public class NewsListAdapter  extends ArrayAdapter<String>  {
    private final Context context;
    private final String[] NewsHeaders;
    private final String[] NewsDetail;

    public NewsListAdapter(Context context, String[] newsHeaders, String[] newsDetail) {
        super(context,R.layout.news_list,newsHeaders);
        this.context = context;
        NewsHeaders = newsHeaders;
        NewsDetail = newsDetail;
    }




    @Override
    public View getView(int position, View convertview, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.news_list,parent, false);
        TextView newsHead = (TextView) rowView.findViewById(R.id.NewsHead);
        TextView newsDetail = (TextView) rowView.findViewById(R.id.NewsDetail);
        newsHead.setText(NewsHeaders[position]);
        newsDetail.setText(NewsDetail[position]);
        return rowView;
    }
}

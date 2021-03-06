package com.tedla.amanuel.eagleapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tedla.amanuel.eagleapp.model.NewsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dVentus-hq on 6/17/2017.
 */
public class NewsListAdapter  extends ArrayAdapter<NewsModel>  {
    private final Context context;
    private final List<NewsModel> newsModels;

    public NewsListAdapter(Context context,List<NewsModel> newsModels ) {
        super(context,R.layout.news_list,newsModels);
        this.context = context;
        this.newsModels = newsModels;
    }




    @Override
    public View getView(int position, View convertview, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.news_list,parent, false);
        TextView newsHead = (TextView) rowView.findViewById(R.id.NewsHead);
        TextView newsDetail = (TextView) rowView.findViewById(R.id.NewsDetail);
        newsHead.setText(this.newsModels.get(position).getTitle());
        newsDetail.setText(this.newsModels.get(position).getContent());
        return rowView;
    }
}

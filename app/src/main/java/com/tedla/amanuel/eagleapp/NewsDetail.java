package com.tedla.amanuel.eagleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.tedla.amanuel.eagleapp.model.NewsModel;
import com.tedla.amanuel.eagleapp.model.VacancyModel;

public class NewsDetail extends AppCompatActivity {
   private Toolbar toolbar;
    private TextView newsHeader;
    private TextView newsDetail;
    private NewsModel newsModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("News Detail");
        newsHeader = (TextView) findViewById(R.id.newsTitle);
        newsModel = (NewsModel) getIntent().getSerializableExtra("News");
        newsDetail = (TextView) findViewById(R.id.newsDetail);
        newsHeader.setText(newsModel.getTitle());
        newsDetail.setText(newsModel.getContent());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

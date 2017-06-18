package com.tedla.amanuel.eagleapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class News extends Fragment {
    public ListView newsListView;
    private String[] newsHeaders;
    private String[] newsDetails;
    public News() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("News");
        newsHeaders = new String[NewsData.newsList.length];
        newsDetails = new String[NewsData.newsList.length];


        for(int i=0;i<NewsData.newsList.length;i++){
            newsHeaders[i] = NewsData.newsList[i].getNewsTitle();
            newsDetails[i] = NewsData.newsList[i].getNewsDetail();
        }
        newsListView = (ListView) rootView.findViewById(R.id.newsListView);
        NewsListAdapter newsListAdapter = new NewsListAdapter(getActivity(),newsHeaders,newsDetails);
        newsListView.setAdapter(newsListAdapter);
        return rootView;
    }

}

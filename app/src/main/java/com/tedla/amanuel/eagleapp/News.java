package com.tedla.amanuel.eagleapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                openNewsDetail(position);
            }

        });
        return rootView;
    }

    private void openNewsDetail(int position) {
        Intent intent = new Intent(getActivity(), NewsDetail.class);
        //intent.putExtra("Vacancy", vacancyModels.get(position));
        this.startActivity(intent);
    }

}

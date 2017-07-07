package com.tedla.amanuel.eagleapp;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.tedla.amanuel.eagleapp.database.DatabaseHandler;
import com.tedla.amanuel.eagleapp.model.BaseURL;
import com.tedla.amanuel.eagleapp.model.NewsModel;
import com.tedla.amanuel.eagleapp.model.VacancyModel;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class News extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public ListView newsListView;
    private List<NewsModel> newsModels;
    private NewsListAdapter newsListAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String NEWS_REQUEST_URL = BaseURL.baseUrl + "/news";
    private SQLiteDatabase db;
    private DatabaseHandler dbHandler;
    public News() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("News");
        try {
            dbHandler = new DatabaseHandler(getActivity());

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        db = dbHandler.getWritableDatabase();
        newsModels = new ArrayList<>();
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#fd7f45"));
        newsListView = (ListView) rootView.findViewById(R.id.newsListView);
        newsListAdapter = new NewsListAdapter(getActivity(),newsModels);
        newsListView.setAdapter(newsListAdapter);
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                openNewsDetail(position);
            }

        });
        swipeRefreshLayout.setRefreshing(false);
        this.newsRequest();
        return rootView;
    }

    public void newsRequest() {
        //progressDialog.setMessage("Loading...");
        // progressDialog.show();
        newsModels.clear();
        newsModels.addAll(dbHandler.getNews(db));
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, NEWS_REQUEST_URL, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d("NEWS_REQUEST", response.toString());
                Gson gson = new Gson();
                //Response response = gson.fromJson(yourJsonString, Response.class);
                try{
                    NewsModel[] newsModelArray = gson.fromJson(response.toString(), NewsModel[].class);
                    dbHandler.clearNews(db);
                    dbHandler.insertNews(db, Arrays.asList(newsModelArray));
                    newsModels.clear();
                    newsModels.addAll(dbHandler.getNews(db));
                    refreshListView();
                }
                catch (Exception e){
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                }


                swipeRefreshLayout.setRefreshing(false);
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("NEWS_REQUEST", "Error: " + error.getMessage());
                        Toast.makeText(getActivity(), BaseURL.networkErrorText, Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String auth = "Bearer " + LoginPage.loginResponseModel.getToken();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", auth);
                return headers;
            }

        };

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(getRequest, NEWS_REQUEST_URL);
    }

    private void openNewsDetail(int position) {
        Intent intent = new Intent(getActivity(), NewsDetail.class);
        intent.putExtra("News", newsModels.get(position));
        this.startActivity(intent);
    }

    private void refreshListView() {
        newsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        this.newsRequest();
    }
}

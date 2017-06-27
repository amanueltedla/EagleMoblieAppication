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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tedla.amanuel.eagleapp.database.DatabaseHandler;
import com.tedla.amanuel.eagleapp.model.BaseURL;
import com.tedla.amanuel.eagleapp.model.VacancyModel;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public ListView vacancyListView;
    private String[] jobTitles;
    private String[] companeNames;
    private String[] jobCategories;
    private int[] companyIcons;
    private int[] readIcons;
    private static final String JSON_ARRAY_REQUEST_URL = BaseURL.baseUrl + "/vacancies";
    private static final String TAG = "Home";
    private VacancyListAdapter vacancyListAdapter;
    private List<VacancyModel> vacancyModels;
    private SwipeRefreshLayout swipeLayout;
    private SQLiteDatabase db;
    private DatabaseHandler dbHandler;


    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("Vacancy");
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(Color.parseColor("#fd7f45"));
        vacancyModels = new ArrayList<>();
        jobCategories = new String[Vacancy.vacancyList.length];
        jobTitles = new String[Vacancy.vacancyList.length];
        companeNames = new String[Vacancy.vacancyList.length];
        companyIcons = new int[Vacancy.vacancyList.length];
        readIcons = new int[Vacancy.vacancyList.length];
        swipeLayout.setRefreshing(true);
        for (int i = 0; i < Vacancy.vacancyList.length; i++) {
            jobCategories[i] = Vacancy.vacancyList[i].getCategory();
            jobTitles[i] = Vacancy.vacancyList[i].getJobTitle();
            companeNames[i] = Vacancy.vacancyList[i].getCompanyName();
            companyIcons[i] = Vacancy.vacancyList[i].getCompanyIconId();
            readIcons[i] = Vacancy.vacancyList[i].getReadIconId();
        }
        vacancyListView = (ListView) rootView.findViewById(R.id.vacancyListView);
        vacancyListAdapter = new VacancyListAdapter(getActivity(), vacancyModels, companyIcons, readIcons);
        vacancyListView.setAdapter(vacancyListAdapter);
        vacancyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                openVacancyDetail(position);
            }

        });
        setHasOptionsMenu(true);
        try {
            dbHandler = new DatabaseHandler(getActivity());

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        db = dbHandler.getWritableDatabase();
        this.volleyJsonArrayRequest(JSON_ARRAY_REQUEST_URL);
        return rootView;

    }

    private void openVacancyDetail(int position) {
        Intent intent = new Intent(getActivity(), VacancyDetailAct.class);
        intent.putExtra("Vacancy", vacancyModels.get(position));
        this.startActivity(intent);
    }

    public void volleyJsonArrayRequest(String url) {
        vacancyModels.clear();
        vacancyModels.addAll(dbHandler.getVacancy(db));
        refreshListView();
        //progressDialog.setMessage("Loading...");
        // progressDialog.show();
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                Gson gson = new Gson();
                //Response response = gson.fromJson(yourJsonString, Response.class);
                try{
                    VacancyModel[] vacancyModelsarray = gson.fromJson(response.toString(), VacancyModel[].class);
                    vacancyModels.clear();
                    vacancyModels.addAll(Arrays.asList(vacancyModelsarray));
                    refreshListView();
                    dbHandler.clearVacancy(db);
                    dbHandler.insertVacancy(db,Arrays.asList(vacancyModelsarray));
                }
                catch (Exception e){
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                }


                swipeLayout.setRefreshing(false);
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                        swipeLayout.setRefreshing(false);
                    }
                });

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(getRequest,JSON_ARRAY_REQUEST_URL);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i=1;
        for(VacancyModel vacancyModel:vacancyModels){
            TTS.speakWords("Vacancy " + i);
            TTS.speakWords("Position");
            TTS.speakWords(vacancyModel.getPosition());
            TTS.speakWords("Experience");
            TTS.speakWords(vacancyModel.getExprience());
            TTS.speakWords("Category");
            TTS.speakWords(vacancyModel.getJob_category());
            i++;
        }
        return super.onOptionsItemSelected(item);
    }



    private void refreshListView() {
        vacancyListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        this.volleyJsonArrayRequest(JSON_ARRAY_REQUEST_URL);
    }

}

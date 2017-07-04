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
import com.tedla.amanuel.eagleapp.database.DatabaseHandler;
import com.tedla.amanuel.eagleapp.model.BaseURL;
import com.tedla.amanuel.eagleapp.model.UserStatus;
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
    private static final String NORMAL_VACANCY_LIST = BaseURL.baseUrl + "/vacancies";
    private static final String OPEN_VACANCY_LIST = BaseURL.baseUrl + "/vacancies/open";
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
        try {
            dbHandler = new DatabaseHandler(getActivity());

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        db = dbHandler.getWritableDatabase();
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(Color.parseColor("#fd7f45"));
        vacancyModels = new ArrayList<>();

        vacancyListView = (ListView) rootView.findViewById(R.id.vacancyListView);
        vacancyListAdapter = new VacancyListAdapter(getActivity(), vacancyModels);
        vacancyListView.setAdapter(vacancyListAdapter);
        vacancyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                openVacancyDetail(position);
            }

        });
        setHasOptionsMenu(true);
        if(UserStatus.login) {
            this.volleyJsonArrayRequest(NORMAL_VACANCY_LIST);
        }
        else{
            this.volleyJsonArrayRequest(OPEN_VACANCY_LIST);
        }
        return rootView;

    }

    private void openVacancyDetail(int position) {
        dbHandler.updateSeenVacancy(db,vacancyModels.get(position).get_id());
        Intent intent = new Intent(getActivity(), VacancyDetailAct.class);
        intent.putExtra("Vacancy", vacancyModels.get(position));
        this.startActivity(intent);
    }

    public void volleyJsonArrayRequest(String url) {
        vacancyModels.clear();
        vacancyModels.addAll(dbHandler.getVacancy(db));
        refreshListView();
        //progressDialog.setMessage("Loawsawaqqaing...");
        // progressDialog.show();
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                Gson gson = new Gson();
                //Response response = gson.fromJson(yourJsonString, Response.class);
                try{
                    VacancyModel[] vacancyModelsarray = gson.fromJson(response.toString(), VacancyModel[].class);
                    dbHandler.clearVacancy(db);
                    dbHandler.insertVacancy(db,Arrays.asList(vacancyModelsarray));
                    vacancyModels.clear();
                    vacancyModels.addAll(dbHandler.getVacancy(db));
                    refreshListView();
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
                        Toast.makeText(getActivity(), BaseURL.networkErrorText, Toast.LENGTH_SHORT).show();
                        swipeLayout.setRefreshing(false);
                    }
                });

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(getRequest, NORMAL_VACANCY_LIST);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i=1;
        for(VacancyModel vacancyModel:vacancyModels){
            TTS.speakWords("Vacancy " + i);
            TTS.speakWords("Position");
            TTS.speakWords(vacancyModel.getPosition());
            TTS.speakWords("Level");
            TTS.speakWords(vacancyModel.getExprience());
            TTS.speakWords("Category");
            TTS.speakWords(vacancyModel.getCategory());
            i++;
        }
        return super.onOptionsItemSelected(item);
    }



    private void refreshListView() {
        vacancyListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        if(UserStatus.login) {
            this.volleyJsonArrayRequest(NORMAL_VACANCY_LIST);
        }
        else{
            this.volleyJsonArrayRequest(OPEN_VACANCY_LIST);
        }
    }


}

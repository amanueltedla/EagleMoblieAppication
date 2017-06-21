package com.tedla.amanuel.eagleapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.tedla.amanuel.eagleapp.model.VacancyModel;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {
    public ListView vacancyListView;
    private String[] jobTitles;
    private String[] companeNames;
    private String[] jobCategories;
    private int[] companyIcons;
    private int[] readIcons;
    private static final String JSON_ARRAY_REQUEST_URL = "http://162.243.114.225:9090/vacancies";
    private static final String TAG = "Home";
    private VacancyListAdapter vacancyListAdapter;
    private List<VacancyModel> vacancyModels;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("Home");
        vacancyModels = new ArrayList<>();
        jobCategories = new String[Vacancy.vacancyList.length];
        jobTitles = new String[Vacancy.vacancyList.length];
        companeNames = new String[Vacancy.vacancyList.length];
        companyIcons = new int[Vacancy.vacancyList.length];
        readIcons = new int[Vacancy.vacancyList.length];
        this.volleyJsonArrayRequest(JSON_ARRAY_REQUEST_URL);
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
        return rootView;

    }

    private void openVacancyDetail(int position) {
        Intent intent = new Intent(getActivity(), VacancyDetailAct.class);
        intent.putExtra("Vacancy", vacancyModels.get(position));
        this.startActivity(intent);
    }

    public void volleyJsonArrayRequest(String url) {

        String REQUEST_TAG = "com.androidtutorialpoint.volleyJsonArrayRequest";
        //progressDialog.setMessage("Loading...");
        // progressDialog.show();
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
//                LayoutInflater li = LayoutInflater.from(MainActivity.this);
//                showDialogView = li.inflate(R.layout.show_dialog, null);
//                outputTextView = (TextView)showDialogView.findViewById(R.id.text_view_dialog);
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
//                alertDialogBuilder.setView(showDialogView);
//                alertDialogBuilder
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                            }
//                        })
//                        .setCancelable(false)
//                        .create();
                Gson gson = new Gson();
                //Response response = gson.fromJson(yourJsonString, Response.class);
                VacancyModel [] vacancyModelsarray = gson.fromJson(response.toString(), VacancyModel[].class);
                vacancyModels.clear();
                vacancyModels.addAll(Arrays.asList(vacancyModelsarray));
                refreshListView();
//                outputTextView.setText(vacancyModels[0].get_id());
//                alertDialogBuilder.show();
//                progressDialog.hide();
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(getRequest, REQUEST_TAG);
    }

    private void refreshListView() {
        vacancyListAdapter.notifyDataSetChanged();
    }

}

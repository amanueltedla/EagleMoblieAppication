package com.tedla.amanuel.eagleapp;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.tedla.amanuel.eagleapp.model.LoginResponseModel;
import com.tedla.amanuel.eagleapp.model.UserStatus;
import com.tedla.amanuel.eagleapp.model.VacancyModel;
import android.support.v7.widget.SearchView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FreeVacancy extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public ListView vacancyListView;
    private static final String OPEN_VACANCY_LIST = BaseURL.baseUrl + "/vacancies/open";
    private static final String TAG = "FreeVacancy";
    private VacancyListAdapter vacancyListAdapter;
    private List<VacancyModel> vacancyModels;
    private SwipeRefreshLayout swipeLayout;
    private SQLiteDatabase db;
    private DatabaseHandler dbHandler;
    private SearchView searchView;


    public FreeVacancy() {
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
        populateListViewFromDataBase();
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
        this.onRefresh();
        return rootView;

    }

    private void populateListViewFromDataBase(){
        if(vacancyModels != null && dbHandler != null && vacancyListAdapter != null){
        vacancyModels.clear();
        vacancyModels.addAll(dbHandler.getFreeVacancy(db));
        refreshListView();
        }
    }

    private void openVacancyDetail(int position) {
        dbHandler.updateSeenFreeVacancy(db, vacancyModels.get(position).get_id());
        Intent intent = new Intent(getActivity(), VacancyDetailAct.class);
        intent.putExtra("Vacancy", vacancyModels.get(position));
        this.startActivity(intent);
    }

    public void volleyJsonArrayRequest(String url) {
        vacancyModels.clear();
        vacancyModels.addAll(dbHandler.getFreeVacancy(db));
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
                    dbHandler.clearFreeVacancy(db);
                    dbHandler.insertFreeVacancy(db,Arrays.asList(vacancyModelsarray));
                    vacancyModels.clear();
                    vacancyModels.addAll(dbHandler.getFreeVacancy(db));
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
        AppSingleton.getInstance(getActivity()).addToRequestQueue(getRequest, OPEN_VACANCY_LIST);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        final MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                // Toast like print
                //UserFeedback.show( "SearchOnQueryTextSubmit: " + query);
//                if( ! searchView.isIconified()) {
//                    searchView.setIconified(true);
//                }
                //myActionMenuItem.collapseActionView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                populateListViewFromDataBase();
                List<VacancyModel> searchedVModels = new ArrayList<>();
                for(VacancyModel v:vacancyModels){
                    if(v.getPosition().contains(s) || v.getLevel().contains(s)|| v.getCategory().contains(s)){
                        searchedVModels.add(v);
                    }

                }
                vacancyModels.clear();
                vacancyModels.addAll(searchedVModels);
                refreshListView();
                // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
                return false;
            }
        });
                if( ! searchView.isIconified()) {
                    searchView.setIconified(true);
                }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
           // populateListViewFromDataBase();
        }
        else{
            if(TTS.myTTS !=null){
                TTS.myTTS.stop();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_narration) {
            int i = 1;
            for (VacancyModel vacancyModel : vacancyModels) {
                TTS.speakWords("Vacancy " + i);
                TTS.speakWords("Position");
                TTS.speakWords(vacancyModel.getPosition());
                TTS.speakWords("Level");
                TTS.speakWords(vacancyModel.getLevel());
                TTS.speakWords("Category");
                TTS.speakWords(vacancyModel.getCategory());
                i++;
            }
        }

        return super.onOptionsItemSelected(item);
    }



    private void refreshListView() {
        vacancyListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
            this.volleyJsonArrayRequest(OPEN_VACANCY_LIST);

    }


}

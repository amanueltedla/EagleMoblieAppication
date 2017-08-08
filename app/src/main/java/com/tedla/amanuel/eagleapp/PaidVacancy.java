package com.tedla.amanuel.eagleapp;


import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.tedla.amanuel.eagleapp.database.DatabaseHandler;
import com.tedla.amanuel.eagleapp.model.ActivationRequestModel;
import com.tedla.amanuel.eagleapp.model.ActivationResopnseModel;
import com.tedla.amanuel.eagleapp.model.BaseURL;
import com.tedla.amanuel.eagleapp.model.LoginResponseModel;
import com.tedla.amanuel.eagleapp.model.UserModel;
import com.tedla.amanuel.eagleapp.model.UserStatus;
import com.tedla.amanuel.eagleapp.model.VacancyModel;
import com.tedla.amanuel.eagleapp.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaidVacancy extends Fragment implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener{
    public ListView vacancyListView;
    public static final String PAID_VACANCY_LIST = BaseURL.baseUrl + "/vacancies";
    public static final String LOGIN_REQUEST_URL = BaseURL.baseUrl + "/users/login";
    public static final String USER_VACANCY = BaseURL.baseUrl + "/vacancies/searchByCategory?category=";
    public static final String ACTIVATION = BaseURL.baseUrl + "/key/activate";
    private static final String TAG = "PaidVacancy";
    private VacancyListAdapter vacancyListAdapter;
    private List<VacancyModel> vacancyModels;
    private SwipeRefreshLayout swipeLayout;
    private SQLiteDatabase db;
    private DatabaseHandler dbHandler;
    private SearchView searchView;
    private Dialog dialog;
    private Button dialogSubmittButton;
    private EditText dialogActivationText;
    private ProgressBar dialogprogress;
    private int selectedPostion;


    public PaidVacancy() {
        // Required empty public constructor
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
                if( ! searchView.isIconified()) {
                    searchView.setIconified(true);
                }
               // myActionMenuItem.collapseActionView();
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

        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_paid_vacancy, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("Vacancy");
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.activation_dialog);
        dialogSubmittButton = (Button) dialog.findViewById(R.id.submitButton);
        dialogSubmittButton.setOnClickListener(this);
        dialogActivationText = (EditText) dialog.findViewById(R.id.activationEditText);
        dialogprogress = (ProgressBar) dialog.findViewById(R.id.activationProgress);
        try {
            dbHandler = new DatabaseHandler(getActivity());

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        db = dbHandler.getWritableDatabase();
        swipeLayout = (SwipeRefreshLayout) root.findViewById(R.id.swiperefresh);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(Color.parseColor("#fd7f45"));
        vacancyModels = new ArrayList<>();
        populateListViewFromDataBase();
        vacancyListView = (ListView) root.findViewById(R.id.vacancyListView);
        vacancyListAdapter = new VacancyListAdapter(getActivity(), vacancyModels);
        vacancyListView.setAdapter(vacancyListAdapter);
        vacancyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                UserModel userModel = dbHandler.getUser(db);
                if(userModel != null && userModel.getStatus()!= null && userModel.getStatus().equalsIgnoreCase("active")){
                    openVacancyDetail(position);
                }
                 else {
                    dialog.setTitle("Enter Activation Code");
                    dialogprogress.setVisibility(View.INVISIBLE);
                    dialog.show();
                    selectedPostion = position;
                }
            }
        });
        setHasOptionsMenu(true);
        this.onRefresh();
        //this.volleyJsonArrayRequest(PAID_VACANCY_LIST);
        return root;

    }
    private void populateListViewFromDataBase(){
        if(vacancyModels != null && dbHandler != null && vacancyListAdapter != null){
            vacancyModels.clear();
            vacancyModels.addAll(dbHandler.getPaidVacancy(db));
            refreshListView();
        }
    }

    private void openVacancyDetail(int position) {
        dbHandler.updateSeenPaidVacancy(db, vacancyModels.get(position).get_id());
        Intent intent = new Intent(getActivity(), VacancyDetailAct.class);
        intent.putExtra("Vacancy", vacancyModels.get(position));
        this.startActivity(intent);
    }

    public void openVacancyRequest(String url) {
        populateListViewFromDataBase();
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
                    dbHandler.clearPaidVacancy(db);
                    dbHandler.insertPaidVacancy(db, Arrays.asList(vacancyModelsarray));
                    populateListViewFromDataBase();
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
        AppSingleton.getInstance(getActivity()).addToRequestQueue(getRequest, PAID_VACANCY_LIST);
    }


    public void searchVacancyRequest(String url) {
        populateListViewFromDataBase();
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
                    dbHandler.insertPaidVacancy(db, Arrays.asList(vacancyModelsarray));
                    populateListViewFromDataBase();
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
        AppSingleton.getInstance(getActivity()).addToRequestQueue(getRequest, PAID_VACANCY_LIST);
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
    private void refreshListView() {
        vacancyListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        UserModel userModel = dbHandler.getUser(db);
        populateListViewFromDataBase();
        if(UserStatus.login && UserStatus.loginResponseModel.getUser() != null
                && UserStatus.loginResponseModel.getUser().getCustomer() != null
                && UserStatus.loginResponseModel.getUser().getCustomer().getJob_category() != null
                && UserStatus.loginResponseModel.getUser().getCustomer().getJob_category().size() > 0) {

            for(String category: UserStatus.loginResponseModel.getUser().getCustomer().getJob_category()){
                try {
                    this.searchVacancyRequest(USER_VACANCY + URLEncoder.encode(category, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        }

        else if(userModel != null){
            try {
                this.login(userModel);
            } catch (JSONException e) {
                Toast toast = Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else{
            this.openVacancyRequest(PAID_VACANCY_LIST);
      }

    }

    public void login(final UserModel user) throws JSONException {

        Gson gson = new Gson();
        String json = gson.toJson(user,UserModel.class);
        Log.d("myTag", json);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, LOGIN_REQUEST_URL, new JSONObject(gson.toJson(user,UserModel.class)),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        UserModel userModel = dbHandler.getUser(db);
                        if(userModel != null) {
                            Gson gson = new Gson();
                            LoginPage.loginResponseModel = gson.fromJson(response.toString(), LoginResponseModel.class);
                            UserStatus.login = true;
                            if (LoginPage.loginResponseModel != null) {
                                UserStatus.loginResponseModel = LoginPage.loginResponseModel;
                            }
                            dbHandler.clearPaidVacancy(db);
                            onRefresh();
                            //Toast.makeText(getActivity(),"Login successful",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        UserStatus.login = false;
                        Toast.makeText(getActivity(),"Unable to Login",Toast.LENGTH_LONG).show();
                        swipeLayout.setRefreshing(false);
                    }
                })
        {

        };
        AppSingleton.getInstance(getActivity()).addToRequestQueue(request, LOGIN_REQUEST_URL);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == dialogSubmittButton.getId()){
            try {
                activateUser(UserStatus.loginResponseModel,dialogActivationText.getText().toString());
                dialogprogress.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private void activateUser(LoginResponseModel loginResponseModel,String activationCode) throws JSONException {
        Gson gson = new Gson();
        ActivationRequestModel aRM = new ActivationRequestModel();
        if(loginResponseModel.getUser() != null
                && loginResponseModel.getUser().getCustomer() != null
                && loginResponseModel.getUser().getCustomer().get_id() != null) {
            aRM.setCustomer_id(loginResponseModel.getUser().getCustomer().get_id());
        }
        //aRM.setCustomer_level(""+loginResponseModel.getLevel());
        aRM.setCustomer_level("1");
        aRM.setKey(activationCode);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, ACTIVATION, new JSONObject(gson.toJson(aRM,ActivationRequestModel.class)),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dialogprogress.setVisibility(View.INVISIBLE);
                        ActivationResopnseModel arm = new ActivationResopnseModel();
                        Gson gson = new Gson();
                        arm = gson.fromJson(response.toString(), ActivationResopnseModel.class);
                        if(arm.getMsg() != null){
                            Toast.makeText(getActivity(),arm.getMsg(),Toast.LENGTH_LONG).show();
                        }
                        openVacancyDetail(selectedPostion);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialogprogress.setVisibility(View.INVISIBLE);
                        String errorResponse = null;
                        NetworkResponse response = error.networkResponse;
                        if(response !=null) {
                            errorResponse = new String(response.data);
                            errorResponse = Util.trimMessage(errorResponse, "message");
                        }
                        if(errorResponse != null){
                            Toast.makeText(getActivity(),errorResponse,Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getActivity(),"Internet Error",Toast.LENGTH_LONG).show();
                        }

                    }
                })
        {

        };
        AppSingleton.getInstance(getActivity()).addToRequestQueue(request, ACTIVATION);

    }
}

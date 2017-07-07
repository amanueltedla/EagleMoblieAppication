package com.tedla.amanuel.eagleapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.tedla.amanuel.eagleapp.model.AddCategoryModel;
import com.tedla.amanuel.eagleapp.model.BaseURL;
import com.tedla.amanuel.eagleapp.model.JobCategoryModel;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryChoice extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private static final String ADD_CATEGORY_REQUEST_URL = BaseURL.baseUrl + "/customers/category";
    private static final String CATEGORIES_REQUEST_URL = BaseURL.baseUrl + "/jobcategories";
    private List<JobCategoryModel> jobCategoryModels;
    private Button register;
    private AddCategoryModel addCategoryModel;
    private String customerID;
    private GridLayout gridLayout;
    private static final int columnCount = 1;
    private List<CheckBox> checkBoxes;
    private ProgressBar categoryProgress;
    private int categoryCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_choice);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        jobCategoryModels = new ArrayList<>();
        toolbar.setTitle("Job Category");
        customerID = getIntent().getStringExtra("CustomerID");
        register = (Button) findViewById(R.id.RegisterButton);
        register.setVisibility(View.INVISIBLE);
        register.setOnClickListener(this);
        setSupportActionBar(toolbar);
        gridLayout = (GridLayout) findViewById(R.id.parentLayout);
        checkBoxes = new ArrayList<>();
        addCategoryModel = new AddCategoryModel();
        categoryProgress = (ProgressBar) findViewById(R.id.categoryProgress);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        categoriesRequest();
    }

    public void addCategoryToCustomer(final AddCategoryModel model) throws JSONException {

        Gson gson = new Gson();
        String json = gson.toJson(model, AddCategoryModel.class);
        Log.d("myTag", json);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, ADD_CATEGORY_REQUEST_URL, new JSONObject(gson.toJson(model, AddCategoryModel.class)),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        categoryProgress.setVisibility(View.INVISIBLE);
                        if(categoryCount == checkBoxes.size()){
                            openLoginPage();
                        }
                        Toast.makeText(getBaseContext(), "Successful", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        categoryProgress.setVisibility(View.INVISIBLE);
                        Toast.makeText(getBaseContext(), "not working", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String auth = "Bearer " + LoginPage.loginResponseModel.getToken();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", auth);
                return headers;
            }

        };
        AppSingleton.getInstance(getBaseContext()).addToRequestQueue(request, ADD_CATEGORY_REQUEST_URL);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.RegisterButton) {
            categoryCount = 0;
            for (CheckBox checkBox : checkBoxes) {
                categoryCount++;
                categoryProgress.setVisibility(View.VISIBLE);
                if (checkBox.isChecked()) {
                    //                   Toast.makeText(getBaseContext(), checkBox.getText(), Toast.LENGTH_LONG).show();
                    addCategoryModel.setCustomerId(this.customerID);
                    addCategoryModel.setJob_category(checkBox.getTag().toString());
                    try {
                        this.addCategoryToCustomer(addCategoryModel);
                    } catch (JSONException e) {
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    public void categoriesRequest() {
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, CATEGORIES_REQUEST_URL, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                categoryProgress.setVisibility(View.INVISIBLE);
                Gson gson = new Gson();
                //Response response = gson.fromJson(yourJsonString, Response.class);
                try {
                    JobCategoryModel[] jobCategoryModels2 = gson.fromJson(response.toString(), JobCategoryModel[].class);
                    jobCategoryModels.clear();
                    jobCategoryModels.addAll(Arrays.asList(jobCategoryModels2));
                    setupCheckboxes();
                    register.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        categoryProgress.setVisibility(View.INVISIBLE);
                        Toast.makeText(getBaseContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
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
        AppSingleton.getInstance(getBaseContext()).addToRequestQueue(getRequest, CATEGORIES_REQUEST_URL);
    }

    private void setupCheckboxes() {
        gridLayout.removeAllViews();

        gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        gridLayout.setColumnCount(columnCount);
        gridLayout.setRowCount(jobCategoryModels.size());
        CheckBox checkBox;
        checkBoxes.clear();
        for (int i = 0; i < jobCategoryModels.size(); i++) {

            checkBox = new CheckBox(getBaseContext());
            checkBox.setText(jobCategoryModels.get(i).getName());
            checkBox.setTextColor(Color.BLACK);
            gridLayout.addView(checkBox, i);
            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.height = GridLayout.LayoutParams.WRAP_CONTENT;
            param.width = GridLayout.LayoutParams.WRAP_CONTENT;
            param.rightMargin = 5;
            param.topMargin = 15;
            param.setGravity(Gravity.CENTER);
            param.columnSpec = GridLayout.spec(0);
            param.rowSpec = GridLayout.spec(i);
            checkBox.setLayoutParams(param);
            int states[][] = {{android.R.attr.state_checked}, {}};
            int colors[] = {Color.parseColor("#fd7f45"), Color.BLACK};
            CompoundButtonCompat.setButtonTintList(checkBox, new ColorStateList(states, colors));
            checkBox.setTag(jobCategoryModels.get(i).get_id());
            checkBoxes.add(checkBox);
        }
    }

    private void openLoginPage() {
        Intent intent = new Intent(this, LoginPage.class);
        //intent.putExtra("Vacancy", vacancyModels.get(position));
        this.startActivity(intent);
    }


}

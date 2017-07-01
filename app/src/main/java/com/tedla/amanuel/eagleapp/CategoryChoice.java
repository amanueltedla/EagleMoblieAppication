package com.tedla.amanuel.eagleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.tedla.amanuel.eagleapp.model.AddCategoryModel;
import com.tedla.amanuel.eagleapp.model.BaseURL;
import com.tedla.amanuel.eagleapp.model.UserModel;
import com.tedla.amanuel.eagleapp.model.VacancyModel;

import org.json.JSONException;
import org.json.JSONObject;

public class CategoryChoice extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private static final String ADD_CATEGORY_REQUEST_URL = BaseURL.baseUrl + "/customers/category";
    private Button register;
    private AddCategoryModel addCategoryModel;
    private String customerID;
    private GridLayout gridLayout;
    private static final int column = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_choice);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Job Category");
        customerID = getIntent().getStringExtra("CustomerID");
        register = (Button) findViewById(R.id.RegisterButton);
        register.setOnClickListener(this);
        setSupportActionBar(toolbar);
        gridLayout = (GridLayout) findViewById(R.id.parentLayout);
        gridLayout.removeAllViews();
        addCategoryModel = new AddCategoryModel();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




    }

    public void addCategoryToCustomer(final AddCategoryModel model) throws JSONException {

        Gson gson = new Gson();
        String json = gson.toJson(model,AddCategoryModel.class);
        Log.d("myTag", json);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, ADD_CATEGORY_REQUEST_URL, new JSONObject(gson.toJson(model,AddCategoryModel.class)),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getBaseContext(),"Successful",Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getBaseContext(),"not working",Toast.LENGTH_LONG).show();
                    }
                })
        {

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
        if(v.getId() == R.id.RegisterButton){
            addCategoryModel.setCustomerId(this.customerID);
            addCategoryModel.setJob_category("594c1a6ce0f8570004f7a059");
            try {
                this.addCategoryToCustomer(addCategoryModel);
            } catch (JSONException e) {
                Toast.makeText(getBaseContext(),"Error",Toast.LENGTH_LONG).show();
            }
        }
    }
}

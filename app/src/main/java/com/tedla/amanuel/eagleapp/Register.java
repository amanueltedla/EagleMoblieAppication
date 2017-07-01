package com.tedla.amanuel.eagleapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tedla.amanuel.eagleapp.model.BaseURL;
import com.tedla.amanuel.eagleapp.model.SignUpResponseModel;
import com.tedla.amanuel.eagleapp.model.UserModel;
import com.tedla.amanuel.eagleapp.model.VacancyModel;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Register extends Fragment implements View.OnClickListener {
    private Button next;
    private EditText userID;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText birthDate;
    private EditText gender;
    private EditText mobile;
    private EditText city;
    private EditText country;
    private EditText password;
    private EditText confirmPassword;
    private SignUpResponseModel responseModel;
    private static final String SIGNUP_REQUEST_URL = BaseURL.baseUrl + "/users/signup";
    public Register() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).setActionBarTitle("Register");
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        next = (Button) rootView.findViewById(R.id.nextButton);
        userID = (EditText) rootView.findViewById(R.id.userID);
        firstName = (EditText) rootView.findViewById(R.id.firstName);
        lastName = (EditText) rootView.findViewById(R.id.lastName);
        email = (EditText) rootView.findViewById(R.id.email);
        birthDate = (EditText) rootView.findViewById(R.id.birthDate);
        //gender = (EditText) rootView.findViewById(R.id.gender);
        mobile = (EditText) rootView.findViewById(R.id.moblie);
        city = (EditText) rootView.findViewById(R.id.city);
        country = (EditText) rootView.findViewById(R.id.country);
        password = (EditText) rootView.findViewById(R.id.password);
        confirmPassword = (EditText) rootView.findViewById(R.id.confirmPassword);
        next.setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.nextButton) {
            final UserModel userModel = new UserModel();
            userModel.setPassword(password.getText().toString());
            userModel.setUser_name(userID.getText().toString());
            userModel.setFirst_name(firstName.getText().toString());
            userModel.setLast_name(lastName.getText().toString());
            userModel.setMobile(mobile.getText().toString());
            userModel.setUser_type("customer");
            try {
                RegisterUser(userModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public void RegisterUser(final UserModel user) throws JSONException {


       UserModel[] userModel = new UserModel[1];
        userModel[0] = user;

        Gson gson = new Gson();
        String json = gson.toJson(user,UserModel.class);
        Log.d("myTag", json);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, SIGNUP_REQUEST_URL, new JSONObject(gson.toJson(user,UserModel.class)),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getActivity(),"Successful",Toast.LENGTH_LONG).show();
                        Gson gson = new Gson();
                        //Response response = gson.fromJson(yourJsonString, Response.class);
                        responseModel = gson.fromJson(response.toString(), SignUpResponseModel.class);
                        OpenCategoryChoice();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"not working",Toast.LENGTH_LONG).show();
                    }
                })
        {

        };
//        request.setRetryPolicy(new
//
//                DefaultRetryPolicy(60000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppSingleton.getInstance(getActivity()).addToRequestQueue(request, SIGNUP_REQUEST_URL);
    }

    private void OpenCategoryChoice() {
        Intent intent = new Intent(getActivity(), CategoryChoice.class);
        intent.putExtra("CustomerID", responseModel.get_id());
        this.startActivity(intent);
    }
}

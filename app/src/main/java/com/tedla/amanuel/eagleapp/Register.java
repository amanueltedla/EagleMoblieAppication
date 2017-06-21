package com.tedla.amanuel.eagleapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tedla.amanuel.eagleapp.model.UserModel;

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

    private static final String SIGNUP_REQUEST_URL = "http://162.243.114.225:9090/users/signup";
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
        gender = (EditText) rootView.findViewById(R.id.gender);
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
            userModel.setUser_type("Customer");
            RegisterUser(userModel);
            OpenCategoryChoice();
        }
    }

    public void RegisterUser(final UserModel user){
        StringRequest postRequest = new StringRequest(Request.Method.POST, SIGNUP_REQUEST_URL, new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(), "Register Successful", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(getActivity(),error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("password", user.getPassword());
                params.put("user_name", user.getUser_name());
                params.put("first_name", user.getFirst_name());
                params.put("last_name", user.getLast_name());
                params.put("user_type", user.getUser_type());
                params.put("mobile", user.getMobile());
                return params;
            }
        };
        AppSingleton.getInstance(getActivity()).addToRequestQueue(postRequest, SIGNUP_REQUEST_URL);
    }

    private void OpenCategoryChoice() {
        Intent intent = new Intent(getActivity(), CategoryChoice.class);
        //intent.putExtra("Vacancy", vacancyModels.get(position));
        this.startActivity(intent);
    }
}

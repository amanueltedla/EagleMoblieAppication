package com.tedla.amanuel.eagleapp;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tedla.amanuel.eagleapp.model.BaseURL;
import com.tedla.amanuel.eagleapp.model.JobCategoryModel;
import com.tedla.amanuel.eagleapp.model.SignUpResponseModel;
import com.tedla.amanuel.eagleapp.model.UserModel;
import com.tedla.amanuel.eagleapp.model.VacancyModel;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
    private EditText mobile;
    private EditText city;
    private EditText password;
    private EditText confirmPassword;
    private SignUpResponseModel responseModel;
    private Spinner genderSpinner;
    private Spinner levelSpinner;
    private EditText experience;
    private Calendar myCalendar;
    private ProgressBar registerProgress;
    private DatePickerDialog.OnDateSetListener date;
    private static final String SIGNUP_REQUEST_URL = BaseURL.baseUrl + "/users/signup";
    private static final String CATEGORIES_REQUEST_URL = BaseURL.baseUrl + "/jobcategories";
    private List<JobCategoryModel> jobCategoryModels;
    private GridLayout gridLayout;
    private static final int columnCount = 1;
    private List<CheckBox> checkBoxes;
    private Dialog dialog;
    public Register() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).setActionBarTitle("Register");
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        setHasOptionsMenu(true);
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.job_category_dialog);
        gridLayout = (GridLayout) dialog.findViewById(R.id.parentLayout);
        myCalendar = Calendar.getInstance();
        jobCategoryModels = new ArrayList<>();
        checkBoxes = new ArrayList<>();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        registerProgress = (ProgressBar) rootView.findViewById(R.id.registerProgress);
        registerProgress.setVisibility(View.INVISIBLE);
        genderSpinner = (Spinner) rootView.findViewById(R.id.genderSpinner);
        levelSpinner = (Spinner) rootView.findViewById(R.id.levelSpinner);
        experience = (EditText) rootView.findViewById(R.id.experience);
        next = (Button) rootView.findViewById(R.id.nextbutton);
        userID = (EditText) rootView.findViewById(R.id.userID);
        firstName = (EditText) rootView.findViewById(R.id.firstName);
        lastName = (EditText) rootView.findViewById(R.id.lastName);
        email = (EditText) rootView.findViewById(R.id.email);
        birthDate = (EditText) rootView.findViewById(R.id.birthDate);
        birthDate.setOnClickListener(this);
        //gender = (EditText) rootView.findViewById(R.id.gender);
        mobile = (EditText) rootView.findViewById(R.id.moblie);
        city = (EditText) rootView.findViewById(R.id.city);
        password = (EditText) rootView.findViewById(R.id.password);
        confirmPassword = (EditText) rootView.findViewById(R.id.confirmPassword);
        next.setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.nextbutton) {
//            final UserModel userModel = new UserModel();
//            userModel.setPassword(password.getText().toString());
//            userModel.setUser_name(userID.getText().toString());
//            userModel.setFirst_name(firstName.getText().toString());
//            userModel.setLast_name(lastName.getText().toString());
//            userModel.setMobile(mobile.getText().toString());
//            userModel.setUser_type("customer");
//            userModel.setEmail(email.getText().toString());
//            userModel.setDate_of_birth(birthDate.getText().toString());
//            userModel.setCity(city.getText().toString());
//            userModel.setMobile(mobile.getText().toString());
//            userModel.setGender(genderSpinner.getSelectedItem().toString());
//            userModel.setExprience(experience.getText().toString());
//            userModel.setLevel(levelSpinner.getSelectedItem().toString());
//            registerProgress.setVisibility(View.VISIBLE);
//            try {
//                RegisterUser(userModel);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

            // custom dialog


            dialog.setTitle("Choose Job Categories");
            categoriesRequest();
//            // set the custom dialog components - text, image and button
//            TextView text = (TextView) dialog.findViewById(R.id.text);
//            text.setText("Android custom dialog example!");
//            ImageView image = (ImageView) dialog.findViewById(R.id.image);
//            image.setImageResource(R.drawable.ic_launcher);
//
//            Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
//            // if button is clicked, close the custom dialog
//            dialogButton.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });

            dialog.show();

        }
        else if(view.getId() == R.id.birthDate){
            new DatePickerDialog(getActivity(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }


    public void categoriesRequest() {
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, CATEGORIES_REQUEST_URL, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                //categoryProgress.setVisibility(View.INVISIBLE);
                Gson gson = new Gson();
                //Response response = gson.fromJson(yourJsonString, Response.class);
                try {
                    JobCategoryModel[] jobCategoryModels2 = gson.fromJson(response.toString(), JobCategoryModel[].class);
                    jobCategoryModels.clear();
                    jobCategoryModels.addAll(Arrays.asList(jobCategoryModels2));
                    setupCheckboxes();
                    //register.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //categoryProgress.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<>();
//                String auth = "Bearer " + LoginPage.loginResponseModel.getToken();
//                headers.put("Content-Type", "application/json");
//                headers.put("Authorization", auth);
//                return headers;
//            }

        };

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(getRequest, CATEGORIES_REQUEST_URL);
    }



    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        birthDate.setText(sdf.format(myCalendar.getTime()));
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
                        registerProgress.setVisibility(View.INVISIBLE);
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
                        registerProgress.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(),"Couldn't Register",Toast.LENGTH_LONG).show();
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


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
    private void OpenCategoryChoice() {
        Intent intent = new Intent(getActivity(), CategoryChoice.class);
        intent.putExtra("CustomerID", responseModel.get_id());
        this.startActivity(intent);
    }

    private void setupCheckboxes() {
        gridLayout.removeAllViews();

        gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        gridLayout.setColumnCount(columnCount);
        gridLayout.setRowCount(jobCategoryModels.size());
        CheckBox checkBox;
        checkBoxes.clear();
        for (int i = 0; i < jobCategoryModels.size(); i++) {

            checkBox = new CheckBox(getActivity());
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
            checkBox.setTag(jobCategoryModels.get(i).getName());
            checkBoxes.add(checkBox);
        }
    }
}

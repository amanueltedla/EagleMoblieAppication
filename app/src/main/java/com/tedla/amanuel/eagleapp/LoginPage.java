package com.tedla.amanuel.eagleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.tedla.amanuel.eagleapp.model.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {
    private static final String LOGIN_REQUEST_URL = "https://sleepy-savannah-82444.herokuapp.com/users/login";
    private Button login;
    private EditText userID;
    private EditText password;
    private UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        login = (Button) findViewById(R.id.loginButton);
        userID = (EditText) findViewById(R.id.userID);
        password = (EditText) findViewById(R.id.password);
        userModel = new UserModel();
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.loginButton){
            userModel.setUser_name(userID.getText().toString());
            userModel.setPassword(password.getText().toString());
            try {
                this.login(userModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void login(final UserModel user) throws JSONException {


        UserModel[] userModel = new UserModel[1];
        userModel[0] = user;

        Gson gson = new Gson();
        String json = gson.toJson(user,UserModel.class);
        Log.d("myTag", json);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, LOGIN_REQUEST_URL, new JSONObject(gson.toJson(user,UserModel.class)),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getBaseContext(),"Successful",Toast.LENGTH_LONG).show();
                        openMainActivity();
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
//        request.setRetryPolicy(new
//
//                DefaultRetryPolicy(60000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppSingleton.getInstance(getBaseContext()).addToRequestQueue(request, LOGIN_REQUEST_URL);
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra("Vacancy", vacancyModels.get(position));
        this.startActivity(intent);
    }

}

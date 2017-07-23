package com.tedla.amanuel.eagleapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.tedla.amanuel.eagleapp.database.DatabaseHandler;
import com.tedla.amanuel.eagleapp.model.BaseURL;
import com.tedla.amanuel.eagleapp.model.LoginResponseModel;
import com.tedla.amanuel.eagleapp.model.SignUpResponseModel;
import com.tedla.amanuel.eagleapp.model.UserModel;
import com.tedla.amanuel.eagleapp.model.UserStatus;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {
    private static final String LOGIN_REQUEST_URL = BaseURL.baseUrl + "/users/login";
    private Button login;
    private EditText userID;
    private EditText password;
    private UserModel userModel;
    private ProgressBar loginProgress;
    private SQLiteDatabase db;
    private DatabaseHandler dbHandler;

    public static LoginResponseModel loginResponseModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        loginResponseModel = new LoginResponseModel();
        login = (Button) findViewById(R.id.loginButton);
        loginProgress = (ProgressBar) findViewById(R.id.loginProgressBar);
        loginProgress.setVisibility(View.INVISIBLE);
        userID = (EditText) findViewById(R.id.userID);
        password = (EditText) findViewById(R.id.password);
        userModel = new UserModel();
        login.setOnClickListener(this);
        try {
            dbHandler = new DatabaseHandler(this);

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        db = dbHandler.getWritableDatabase();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.loginButton){
            userModel.setUser_name(userID.getText().toString());
            userModel.setPassword(password.getText().toString());
            try {
                this.login(userModel);
                this.loginProgress.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
                        dbHandler.clearUser(db);
                        dbHandler.clearPaidVacancy(db);
                        dbHandler.insertUser(db, user);
                        Toast.makeText(getBaseContext(),"Successful",Toast.LENGTH_LONG).show();
                        loginProgress.setVisibility(View.INVISIBLE);
                        Gson gson = new Gson();
                        loginResponseModel = gson.fromJson(response.toString(), LoginResponseModel.class);
                        UserStatus.login = true;
                        if(loginResponseModel != null){
                            UserStatus.loginResponseModel = loginResponseModel;
                        }
                        openMainActivity();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loginProgress.setVisibility(View.INVISIBLE);
                        UserStatus.login = false;
                        Toast.makeText(getBaseContext(),"Cannot Login",Toast.LENGTH_LONG).show();
                    }
                })
        {

        };
        AppSingleton.getInstance(getBaseContext()).addToRequestQueue(request, LOGIN_REQUEST_URL);
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra("Vacancy", vacancyModels.get(position));
        this.startActivity(intent);
    }

}

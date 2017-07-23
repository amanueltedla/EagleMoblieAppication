package com.tedla.amanuel.eagleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.tedla.amanuel.eagleapp.model.ActivationModel;
import com.tedla.amanuel.eagleapp.model.SignUpResponseModel;
import com.tedla.amanuel.eagleapp.model.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

public class Activation extends AppCompatActivity implements View.OnClickListener {
    private Button submitt;
    private TextView continueText;
    private static final String ACTIVATION_URL = "";
    private ProgressBar activationProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);
        submitt = (Button) findViewById(R.id.submitButton);
        activationProgress = (ProgressBar) findViewById(R.id.activationProgress);
        submitt.setOnClickListener(this);
        continueText = (TextView) findViewById(R.id.continueText);
        continueText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.submitButton){
            try {
                activateUser(new ActivationModel());
                activationProgress.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                Toast.makeText(getBaseContext(),"Couldn't Activate",Toast.LENGTH_LONG).show();
            }
        }
        else if(v.getId() == R.id.continueText){

        }
    }

    public void activateUser(final ActivationModel activationModel) throws JSONException {
        Gson gson = new Gson();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, ACTIVATION_URL, new JSONObject(gson.toJson(activationModel,UserModel.class)),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        activationProgress.setVisibility(View.INVISIBLE);
                        Gson gson = new Gson();
//                        responseModel = gson.fromJson(response.toString(), SignUpResponseModel.class);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        activationProgress.setVisibility(View.INVISIBLE);
                        Toast.makeText(getBaseContext(),"Couldn't Register",Toast.LENGTH_LONG).show();
                    }
                })
        {

        };
        AppSingleton.getInstance(getBaseContext()).addToRequestQueue(request, ACTIVATION_URL);
    }

}

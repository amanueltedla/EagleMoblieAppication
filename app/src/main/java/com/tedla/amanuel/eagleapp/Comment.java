package com.tedla.amanuel.eagleapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.tedla.amanuel.eagleapp.model.BaseURL;
import com.tedla.amanuel.eagleapp.model.CommentModel;
import com.tedla.amanuel.eagleapp.model.CommentResponseModel;
import com.tedla.amanuel.eagleapp.model.SignUpResponseModel;
import com.tedla.amanuel.eagleapp.model.UserModel;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class Comment extends Fragment implements View.OnClickListener{
    private static final String COMMENT_SUBMIT_URL = BaseURL.baseUrl + "/comments";
    private EditText comment;
    private Button submitt;
    private CommentResponseModel commentResponseModel;
    public Comment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).setActionBarTitle("Comment");
        View root = inflater.inflate(R.layout.fragment_comment, container, false);
        comment = (EditText) root.findViewById(R.id.commentText);
        submitt = (Button) root.findViewById(R.id.submitButton);
        submitt.setOnClickListener(this);
        return root;
    }

    public void SubmitComment(final CommentModel comment) throws JSONException {

        Gson gson = new Gson();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, COMMENT_SUBMIT_URL, new JSONObject(gson.toJson(comment,CommentModel.class)),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gson = new Gson();
                            commentResponseModel = gson.fromJson(response.toString(), CommentResponseModel.class);
                            Toast.makeText(getActivity(), "Successful", Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e){
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
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
        AppSingleton.getInstance(getActivity()).addToRequestQueue(request, COMMENT_SUBMIT_URL);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.submitButton){

        }
    }
}

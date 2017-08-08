package com.tedla.amanuel.eagleapp;


import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
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
import com.tedla.amanuel.eagleapp.model.CommentModel;
import com.tedla.amanuel.eagleapp.model.CommentResponseModel;
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
    private SQLiteDatabase db;
    private DatabaseHandler dbHandler;
    private ProgressBar commentProgress;
    private Dialog dialog;
    private Button dialogSubmittButton;
    private EditText dialogPhoneText;
    public Comment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).setActionBarTitle("Comment");
        View root = inflater.inflate(R.layout.fragment_comment, container, false);
        setHasOptionsMenu(true);
        try {
            dbHandler = new DatabaseHandler(getActivity());

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.comment_phone_dialog);
        dialogSubmittButton = (Button) dialog.findViewById(R.id.dialogSubmitButton);
        dialogSubmittButton.setOnClickListener(this);
        dialogPhoneText = (EditText) dialog.findViewById(R.id.phoneEditText);
        commentProgress = (ProgressBar) root.findViewById(R.id.commentProgressBar);
        commentProgress.setVisibility(View.INVISIBLE);
        db = dbHandler.getWritableDatabase();
        comment = (EditText) root.findViewById(R.id.commentText);
        submitt = (Button) root.findViewById(R.id.submitButton);
        submitt.setOnClickListener(this);
        return root;
    }

    public void submitComment(final CommentModel comment) throws JSONException {

        Gson gson = new Gson();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, COMMENT_SUBMIT_URL, new JSONObject(gson.toJson(comment,CommentModel.class)),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            commentProgress.setVisibility(View.INVISIBLE);
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
                        commentProgress.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(),"Internet Error",Toast.LENGTH_LONG).show();
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
            CommentModel commentModel = new CommentModel();
            UserModel userModel = dbHandler.getUser(db);
            if(userModel != null){
                commentModel.setContact(userModel.getUser_name());
                commentModel.setContent(comment.getText().toString());
                try {
                    commentProgress.setVisibility(View.VISIBLE);
                    submitComment(commentModel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
           else{
                dialog.setTitle("Enter Your Phone Number");
                dialog.show();
            }

        }
        else if (v.getId() == R.id.dialogSubmitButton){
            dialog.dismiss();
            CommentModel commentModel = new CommentModel();
            commentModel.setContent(comment.getText().toString());
            commentModel.setContact(dialogPhoneText.getText().toString());
            try {
                commentProgress.setVisibility(View.VISIBLE);
                submitComment(commentModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_narration).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}

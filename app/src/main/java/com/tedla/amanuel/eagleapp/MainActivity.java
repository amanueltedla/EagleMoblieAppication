package com.tedla.amanuel.eagleapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.speech.tts.TextToSpeech;
import android.content.Intent;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.tedla.amanuel.eagleapp.database.DatabaseHandler;
import com.tedla.amanuel.eagleapp.model.BaseURL;
import com.tedla.amanuel.eagleapp.model.LoginResponseModel;
import com.tedla.amanuel.eagleapp.model.UserModel;
import com.tedla.amanuel.eagleapp.model.UserStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnInitListener{
    private static final String LOGIN_REQUEST_URL = BaseURL.baseUrl + "/users/login";
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private int MY_DATA_CHECK_CODE = 0;
    private static TextToSpeech myTTS;
    private NavigationView navigationView;
    private View headerview;
    private TextView siginOut;
    private TextView userName;
    private AlertDialog.Builder alertDialog;
    private static final String SIGNINTEXT = "Sign In";
    private static final String SIGNOUTTEXT = "Sign Out";
    private static final String DIALOGTEXT = "Are you sure you want to sign out?";
    private SQLiteDatabase db;
    private DatabaseHandler dbHandler;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            dbHandler = new DatabaseHandler(getBaseContext());

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(getBaseContext(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        db = dbHandler.getWritableDatabase();
        UserModel userModel = dbHandler.getUser(db);
        if(userModel != null && LoginPage.loginResponseModel == null){
            try {
                this.login(userModel);
            } catch (JSONException e) {
                Toast toast = Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        alertDialog = new AlertDialog.Builder(MainActivity.this);

        alertDialog.setTitle(SIGNOUTTEXT);
        alertDialog.setMessage(DIALOGTEXT);
        alertDialog.setPositiveButton(SIGNOUTTEXT, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
               dbHandler.clearUser(db);
                siginOut.setText(SIGNINTEXT);
                userName.setText("");
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
        navigationView = (NavigationView) findViewById(R.id.nvView);
        headerview = navigationView.getHeaderView(0);
        siginOut = (TextView) headerview.findViewById(R.id.navSignOut);
        siginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(siginOut.getText().toString().equalsIgnoreCase(SIGNOUTTEXT)){
                    alertDialog.show();

                }
                else{
                    //mDrawer.closeDrawer(GravityCompat.START);
                    openLoginPage();
                }
            }
        });
        userName = (TextView) headerview.findViewById(R.id.navUserName);
        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);
        // Insert the fragment by replacing any existing fragment
        Class fragmentClass = Home.class;
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        if(userModel == null){
            siginOut.setText(SIGNINTEXT);
            userName.setText("");
        }
        else{
            siginOut.setText(SIGNOUTTEXT);
            userName.setText(userModel.getUser_name());
        }
    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private void openLoginPage() {
        Intent intent = new Intent(this, LoginPage.class);
        //intent.putExtra("Vacancy", vacancyModels.get(position));
        this.startActivity(intent);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = Home.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = News.class;
                break;
            case R.id.nav_third_fragment:
                fragmentClass = Comment.class;
                break;
            case R.id.nav_fourth_fragment:
                fragmentClass = About.class;
                break;
            case R.id.nav_fifth_fragment:
                fragmentClass = Register.class;
                break;
            default:
                fragmentClass = Home.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    public void setActionBarTitle(String title){
       toolbar.setTitle(title);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            myTTS.setLanguage(Locale.US);
            TTS.myTTS = myTTS;
        }
        else if (status == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                myTTS = new TextToSpeech(this, this);
            }
            else {
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
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

                        Gson gson = new Gson();
                        LoginPage.loginResponseModel = gson.fromJson(response.toString(), LoginResponseModel.class);
                        UserStatus.login = true;
                        Toast.makeText(getBaseContext(),"Login successful",Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        UserStatus.login = false;
                        Toast.makeText(getBaseContext(),"Couldn't Sign In",Toast.LENGTH_LONG).show();
                    }
                })
        {

        };
        AppSingleton.getInstance(getBaseContext()).addToRequestQueue(request, LOGIN_REQUEST_URL);
    }
}

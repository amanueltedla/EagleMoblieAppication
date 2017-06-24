package com.tedla.amanuel.eagleapp;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.tedla.amanuel.eagleapp.model.VacancyModel;

import java.util.Locale;

public class VacancyDetailAct extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private TextView postionTextView;
    private TextView descriptionTextView;
    private TextView experienceTextView;
    private TextView qualificationsTextView;
    private TextView statusTextView;
    private TextView dueDateTextView;
    private TextView salaryTextView;
    private TextView numberReqTextView;
    private TextView contactTextView;
    private TextView mobileTextView;
    private TextView emailTextView;
    private TextView levelTextView;
    private TextView categoryTextView;
    private static TextToSpeech myTTS;
    private Toolbar toolbar;
    private VacancyModel vacancyModel;
    private int MY_DATA_CHECK_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy_detail);
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Vacancy Detail");
        vacancyModel = (VacancyModel) getIntent().getSerializableExtra("Vacancy");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        postionTextView = (TextView) findViewById(R.id.title);
        descriptionTextView = (TextView) findViewById(R.id.description);
        experienceTextView = (TextView) findViewById(R.id.experience);
        qualificationsTextView = (TextView) findViewById(R.id.qualification);
        statusTextView = (TextView) findViewById(R.id.status);
        dueDateTextView = (TextView) findViewById(R.id.dueDate);
        salaryTextView = (TextView) findViewById(R.id.salary);
        numberReqTextView = (TextView) findViewById(R.id.numReq);
        contactTextView = (TextView) findViewById(R.id.contact);
        mobileTextView = (TextView) findViewById(R.id.moblie);
        emailTextView = (TextView) findViewById(R.id.email);
        levelTextView = (TextView) findViewById(R.id.level);
        categoryTextView = (TextView) findViewById(R.id.category);
        updateUI();
    }

    private void updateUI() {
        postionTextView.setText(vacancyModel.getPosition());
        descriptionTextView.setText(vacancyModel.getDescription());
        experienceTextView.setText(vacancyModel.getExprience());
        qualificationsTextView.setText(vacancyModel.getQualifications());
        statusTextView.setText(vacancyModel.getStatus());
        dueDateTextView.setText(vacancyModel.getDue_date());
        salaryTextView.setText(vacancyModel.getSalary());
        numberReqTextView.setText(vacancyModel.getNumber_required());
        contactTextView.setText(vacancyModel.getContact());
        mobileTextView.setText(vacancyModel.getMobile());
        emailTextView.setText(vacancyModel.getEmail());
        levelTextView.setText(vacancyModel.getLevel());
        categoryTextView.setText(vacancyModel.getJob_category().get(0).getName());
//        if(vacancyModel.getPosition() != null || !vacancyModel.getPosition().equals("")){
//            TTS.speakWords("Position " + vacancyModel.getPosition());
//        }
//        if(vacancyModel.getJob_category().get(0).getName() != null || !vacancyModel.getJob_category().get(0).getName().equals("")){
//            TTS.speakWords("Category " + vacancyModel.getJob_category().get(0).getName());
//        }
//        if(vacancyModel.getExprience() != null || !vacancyModel.getExprience().equals("")){
//            TTS.speakWords("Experience " + vacancyModel.getExprience());
//        }
//        if(vacancyModel.getQualifications() != null || !vacancyModel.getQualifications().equals("")){
//            TTS.speakWords("Qualification " + vacancyModel.getQualifications());
//        }
//        if(vacancyModel.getStatus() != null || !vacancyModel.getStatus().equals("")){
//            TTS.speakWords("Status " + vacancyModel.getStatus());
//        }
//        if(vacancyModel.getDue_date() != null || !vacancyModel.getDue_date().equals("")){
//            TTS.speakWords("Due Date " + vacancyModel.getDue_date());
//        }
//        if(vacancyModel.getSalary() != null || !vacancyModel.getSalary().equals("")){
//            TTS.speakWords("Salary " + vacancyModel.getSalary());
//        }
//        if(vacancyModel.getNumber_required() != null || !vacancyModel.getNumber_required().equals("")){
//            TTS.speakWords("Number Required " + vacancyModel.getNumber_required());
//        }
//        if(vacancyModel.getContact() != null || !vacancyModel.getContact().equals("")){
//            TTS.speakWords("Contact " + vacancyModel.getContact());
//        }
//        if(vacancyModel.getMobile() != null || !vacancyModel.getMobile().equals("")){
//            TTS.speakWords("Mobile " + vacancyModel.getMobile());
//        }
//        if(vacancyModel.getEmail() != null || !vacancyModel.getEmail().equals("")){
//            TTS.speakWords("Email " + vacancyModel.getEmail());
//        }
//        if(vacancyModel.getLevel() != null || !vacancyModel.getLevel().equals("")){
//            TTS.speakWords("Level " + vacancyModel.getLevel());
//        }
//        if(vacancyModel.getDescription() != null || !vacancyModel.getDescription().equals("")){
//            TTS.speakWords("Description " + vacancyModel.getDescription());
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        StringBuilder str = new StringBuilder();

        this.speakWords("Position " + vacancyModel.getPosition());
        speakWords("Category " + vacancyModel.getJob_category().get(0).getName());
        speakWords("Experience " + vacancyModel.getExprience());
        speakWords("Qualification " + vacancyModel.getQualifications());


        speakWords("Status " + vacancyModel.getStatus());

        speakWords("Due Date " + vacancyModel.getDue_date());

        speakWords("Salary " + vacancyModel.getSalary());

        speakWords("Number Required " + vacancyModel.getNumber_required());


        speakWords("Contact " + vacancyModel.getContact());

        speakWords("Mobile " + vacancyModel.getMobile());

        speakWords("Email " + vacancyModel.getEmail());

        speakWords("Level " + vacancyModel.getLevel());

        speakWords("Description " + vacancyModel.getDescription());

        Toast.makeText(this, "fadfad", Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            myTTS.setLanguage(Locale.US);
        } else if (status == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }

    public static void speakWords(String speech) {
        myTTS.speak(speech, android.speech.tts.TextToSpeech.QUEUE_ADD, null);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                myTTS = new TextToSpeech(this, this);
            } else {
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

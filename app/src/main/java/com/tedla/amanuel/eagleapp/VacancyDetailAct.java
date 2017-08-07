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
import com.tedla.amanuel.eagleapp.util.ISO8601;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class VacancyDetailAct extends AppCompatActivity {
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
    private Toolbar toolbar;
    private VacancyModel vacancyModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy_detail);
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

    private void updateUI(){
        postionTextView.setText(vacancyModel.getPosition());
        descriptionTextView.setText(vacancyModel.getDescription());
        experienceTextView.setText(vacancyModel.getExprience());
        qualificationsTextView.setText(vacancyModel.getQualifications());
        statusTextView.setText(vacancyModel.getStatus());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            dueDateTextView.setText(dateFormat.format(ISO8601.toCalendar(vacancyModel.getDue_date()).getTime()));
        } catch (ParseException e) {
           dueDateTextView.setText(vacancyModel.getDue_date());
        }
        salaryTextView.setText(vacancyModel.getSalary());
        numberReqTextView.setText(vacancyModel.getNumber_required());
        contactTextView.setText(vacancyModel.getContact());
        mobileTextView.setText(vacancyModel.getMobile());
        emailTextView.setText(vacancyModel.getEmail());
        levelTextView.setText(vacancyModel.getLevel());
        categoryTextView.setText(vacancyModel.getCategory());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_narration) {

            TTS.speakWords("Position " + vacancyModel.getPosition());
            TTS.speakWords("Category " + vacancyModel.getCategory());
            TTS.speakWords("Experience " + vacancyModel.getExprience());
            TTS.speakWords("Qualification " + vacancyModel.getQualifications());


            TTS.speakWords("Status " + vacancyModel.getStatus());

            TTS.speakWords("Due Date " + vacancyModel.getDue_date());

            TTS.speakWords("Salary " + vacancyModel.getSalary());

            TTS.speakWords("Number Required " + vacancyModel.getNumber_required());


            TTS.speakWords("Contact " + vacancyModel.getContact());

            TTS.speakWords("Mobile " + vacancyModel.getMobile());

            TTS.speakWords("Email " + vacancyModel.getEmail());

            TTS.speakWords("Level " + vacancyModel.getLevel());

            TTS.speakWords("Description " + vacancyModel.getDescription());
        } else {
            onBackPressed();
        }
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

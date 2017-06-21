package com.tedla.amanuel.eagleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.tedla.amanuel.eagleapp.model.VacancyModel;

public class VacancyDetailAct extends AppCompatActivity {
    TextView postionTextView;
    TextView descriptionTextView;
    TextView experienceTextView;
    TextView qualificationsTextView;
    TextView statusTextView;
    TextView dueDateTextView;
    TextView salaryTextView;
    TextView numberReqTextView;
    TextView contactTextView;
    TextView mobileTextView;
    TextView emailTextView;
    TextView levelTextView;
    TextView categoryTextView;
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
        salaryTextView = (TextView)  findViewById(R.id.salary);
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
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

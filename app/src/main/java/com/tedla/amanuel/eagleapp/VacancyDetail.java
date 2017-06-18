package com.tedla.amanuel.eagleapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class VacancyDetail extends Fragment {
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



    public VacancyDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vacancy_detail, container, false);
        postionTextView = (TextView) rootView.findViewById(R.id.jobTitle);
        descriptionTextView = (TextView) rootView.findViewById(R.id.description);
        experienceTextView = (TextView) rootView.findViewById(R.id.experience);
        qualificationsTextView = (TextView) rootView.findViewById(R.id.qualification);
        statusTextView = (TextView) rootView.findViewById(R.id.status);
        dueDateTextView = (TextView) rootView.findViewById(R.id.dueDate);
        salaryTextView = (TextView) rootView.findViewById(R.id.salary);
        numberReqTextView = (TextView) rootView.findViewById(R.id.numReq);
        contactTextView = (TextView) rootView.findViewById(R.id.contact);
        mobileTextView = (TextView) rootView.findViewById(R.id.moblie);
        emailTextView = (TextView) rootView.findViewById(R.id.email);
        levelTextView = (TextView) rootView.findViewById(R.id.level);
        categoryTextView = (TextView) rootView.findViewById(R.id.category);
        return rootView;
    }

}

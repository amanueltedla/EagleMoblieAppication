package com.tedla.amanuel.eagleapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {
    public ListView vacancyListView;
    private String[] jobTitles;
    private String[] companeNames;
    private String[] jobCategories;
    private int[] companyIcons;
    private int[] readIcons;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("Home");
        jobCategories = new String[Vacancy.vacancyList.length];
        jobTitles = new String[Vacancy.vacancyList.length];
        companeNames = new String[Vacancy.vacancyList.length];
        companyIcons = new int[Vacancy.vacancyList.length];
        readIcons = new int[Vacancy.vacancyList.length];

        for(int i=0;i<Vacancy.vacancyList.length;i++){
            jobCategories[i] = Vacancy.vacancyList[i].getCategory();
            jobTitles[i] = Vacancy.vacancyList[i].getJobTitle();
            companeNames[i] = Vacancy.vacancyList[i].getCompanyName();
            companyIcons[i] = Vacancy.vacancyList[i].getCompanyIconId();
            readIcons[i] = Vacancy.vacancyList[i].getReadIconId();
        }
     vacancyListView = (ListView) rootView.findViewById(R.id.vacancyListView);
     VacancyListAdapter vacancyListAdapter = new VacancyListAdapter(getActivity(),jobTitles,companeNames,jobCategories,companyIcons,readIcons);
     vacancyListView.setAdapter(vacancyListAdapter);
        vacancyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Long time = Calendar.getInstance().getTimeInMillis();
                //dbHandler.UpdateRecentlyViewedCourses(db,courseName[position],time);
                //openActivity(position);
            }
        });
        return rootView;

    }

}

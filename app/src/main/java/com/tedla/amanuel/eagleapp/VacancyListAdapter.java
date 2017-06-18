package com.tedla.amanuel.eagleapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by dVentus-hq on 6/17/2017.
 */
public class VacancyListAdapter extends ArrayAdapter<String>  {
    private final Context context;
    private final String[] jobTitles;
    private final String[] companyNames;
    private final String[] jobCategories;
    private final int[] icons;
    private final int[] readIcons;

    public VacancyListAdapter(Context context, String[] jobTitles, String[] companyNames, String[] jobCategories, int[] icons, int[] readIcons) {
        super(context,R.layout.vacancy_list,jobTitles);
        this.context = context;
        this.jobTitles = jobTitles;
        this.companyNames = companyNames;
        this.jobCategories = jobCategories;
        this.icons = icons;
        this.readIcons = readIcons;
    }



    @Override
    public View getView(int position, View convertview, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.vacancy_list,parent, false);
        TextView jobTitle = (TextView) rowView.findViewById(R.id.jobTitle);
        TextView companyName = (TextView) rowView.findViewById(R.id.companyName);
        TextView category = (TextView) rowView.findViewById(R.id.category);
        ImageView companyIcon = (ImageView) rowView.findViewById(R.id.companyIcon);
        ImageView readIcon = (ImageView) rowView.findViewById(R.id.readIcon);
        jobTitle.setText(jobTitles[position]);
        companyName.setText(companyNames[position]);
        category.setText(jobCategories[position]);
        Drawable companyIconDrawable = context.getResources().getDrawable(icons[position]);
        companyIcon.setImageDrawable(companyIconDrawable);
        Drawable readIconDrawable = context.getResources().getDrawable(readIcons[position]);
        readIcon.setImageDrawable(readIconDrawable);
        return rowView;
    }
}

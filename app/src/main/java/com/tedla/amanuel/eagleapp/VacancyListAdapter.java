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

import com.tedla.amanuel.eagleapp.model.VacancyModel;

import java.util.List;

/**
 * Created by dVentus-hq on 6/17/2017.
 */
public class VacancyListAdapter extends ArrayAdapter<VacancyModel>  {
    private final Context context;
   // private final String[] jobTitles;
    private final List<VacancyModel> vacancyModels;
    private final int[] icons;
    private final int[] readIcons;

    public VacancyListAdapter(Context context, List<VacancyModel> vacancyModels, int[] icons, int[] readIcons) {
        super(context,R.layout.vacancy_list,vacancyModels);
        this.context = context;
        //this.jobTitles = jobTitles;
        this.vacancyModels = vacancyModels;
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

        jobTitle.setText(this.vacancyModels.get(position).getPosition());
        companyName.setText(this.vacancyModels.get(position).getExprience());
        category.setText(this.vacancyModels.get(position).getJob_category());
        //Drawable companyIconDrawable = context.getResources().getDrawable(icons[position]);
        //companyIcon.setImageDrawable(companyIconDrawable);
        //Drawable readIconDrawable = context.getResources().getDrawable(readIcons[position]);
        //readIcon.setImageDrawable(readIconDrawable);
        return rowView;
    }
}

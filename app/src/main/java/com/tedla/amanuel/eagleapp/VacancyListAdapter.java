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


    public VacancyListAdapter(Context context, List<VacancyModel> vacancyModels) {
        super(context,R.layout.vacancy_list,vacancyModels);
        this.context = context;
        //this.jobTitles = jobTitles;
        this.vacancyModels = vacancyModels;

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
        if(vacancyModels.get(position).getSeen() == 1){
            readIcon.setVisibility(View.INVISIBLE);
        }

        switch (this.vacancyModels.get(position).getPosition().toLowerCase().charAt(0)){

            case 'a':
                companyIcon.setImageResource(R.drawable.icon_a);
                break;
            case 'b':
                companyIcon.setImageResource(R.drawable.icon_b);
                break;
            case 'c':
                companyIcon.setImageResource(R.drawable.icon_c);
                break;
            case 'd':
                companyIcon.setImageResource(R.drawable.icon_d);
                break;
            case 'e':
                companyIcon.setImageResource(R.drawable.icon_e);
                break;
            case 'f':
                companyIcon.setImageResource(R.drawable.icon_f);
                break;
            case 'g':
                companyIcon.setImageResource(R.drawable.icon_g);
                break;
            case 'h':
                companyIcon.setImageResource(R.drawable.icon_h);
                break;
            case 'i':
                companyIcon.setImageResource(R.drawable.icon_i);
                break;
            case 'j':
                companyIcon.setImageResource(R.drawable.icon_j);
                break;
            case 'k':
                companyIcon.setImageResource(R.drawable.icon_k);
                break;
            case 'l':
                companyIcon.setImageResource(R.drawable.icon_l);
                break;
            case 'm':
                companyIcon.setImageResource(R.drawable.icon_m);
                break;
            case 'n':
                companyIcon.setImageResource(R.drawable.icon_n);
                break;
            case 'o':
                companyIcon.setImageResource(R.drawable.icon_o);
                break;
            case 'p':
                companyIcon.setImageResource(R.drawable.icon_p);
                break;
            case 'q':
                companyIcon.setImageResource(R.drawable.icon_q);
                break;
            case 'r':
                companyIcon.setImageResource(R.drawable.icon_r);
                break;
            case 's':
                companyIcon.setImageResource(R.drawable.icon_s);
                break;
            case 't':
                companyIcon.setImageResource(R.drawable.icon_t);
                break;
            case 'u':
                companyIcon.setImageResource(R.drawable.icon_u);
                break;
            case 'v':
                companyIcon.setImageResource(R.drawable.icon_v);
                break;
            case 'w':
                companyIcon.setImageResource(R.drawable.icon_w);
                break;
            case 'x':
                companyIcon.setImageResource(R.drawable.icon_x);
                break;
            case 'y':
                companyIcon.setImageResource(R.drawable.icon_y);
                break;
            case 'z':
                companyIcon.setImageResource(R.drawable.icon_z);
                break;
        }
        //Drawable companyIconDrawable = context.getResources().getDrawable(icons[position]);
        //companyIcon.setImageDrawable(companyIconDrawable);
        //Drawable readIconDrawable = context.getResources().getDrawable(readIcons[position]);
        //readIcon.setImageDrawable(readIconDrawable);
        return rowView;
    }
}

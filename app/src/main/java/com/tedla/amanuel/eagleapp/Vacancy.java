package com.tedla.amanuel.eagleapp;

/**
 * Created by dVentus-hq on 6/17/2017.
 */
public class Vacancy {
    private String jobTitle;
    private String companyName;
    private String category;
    private int companyIconId;
    private int readIconId;
public static final Vacancy[] vacancyList = {
        new Vacancy("Senior Finance Officer","GOAL Ethiopia","Accounting and Finance",R.drawable.ford_logo,R.drawable.dot),
        new Vacancy("Public Works Officer","Catholic Relief Services","Engineering",R.drawable.ford_logo,R.drawable.dot),
        new Vacancy("Senior Finance Coordinator","Save The Children","Education",R.drawable.ford_logo,R.drawable.dot),
        new Vacancy("Public Works Officer","Green Way Farms PLC","Sales and Marketing",R.drawable.ford_logo,R.drawable.dot),
        new Vacancy("Communications Assistant","EATA","Sales and Marketing",R.drawable.ford_logo,R.drawable.dot),
};
    public Vacancy(String jobTitle, String companyName, String category, int companyIconId, int readIconId) {
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.category = category;
        this.companyIconId = companyIconId;
        this.readIconId = readIconId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCategory() {
        return category;
    }

    public int getCompanyIconId() {
        return companyIconId;
    }

    public int getReadIconId() {
        return readIconId;
    }
}

package com.tedla.amanuel.eagleapp.model;

import java.util.List;

/**
 * Created by dVentus-hq on 7/6/2017.
 */
public class CustomerModel {
    private String _id;
    private String profile;
    private List<JobCategoryModel> job_category;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<JobCategoryModel> getJob_category() {
        return job_category;
    }

    public void setJob_category(List<JobCategoryModel> job_category) {
        this.job_category = job_category;
    }


}

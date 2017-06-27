package com.tedla.amanuel.eagleapp.model;

import java.util.List;

/**
 * Created by dVentus-hq on 6/24/2017.
 */
public class SignUpResponseModel {
    private String _id;
    private ProfileModel profile;

    public List<JobCategoryModel> getJob_category() {
        return job_category;
    }

    public void setJob_category(List<JobCategoryModel> job_category) {
        this.job_category = job_category;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public ProfileModel getProfile() {
        return profile;
    }

    public void setProfile(ProfileModel profile) {
        this.profile = profile;
    }

    private List<JobCategoryModel> job_category;
}

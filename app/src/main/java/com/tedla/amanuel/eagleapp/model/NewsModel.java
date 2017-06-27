package com.tedla.amanuel.eagleapp.model;

import java.io.Serializable;

/**
 * Created by dVentus-hq on 6/27/2017.
 */
public class NewsModel implements Serializable {
    private String _id;
    private String content;
    private String title;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

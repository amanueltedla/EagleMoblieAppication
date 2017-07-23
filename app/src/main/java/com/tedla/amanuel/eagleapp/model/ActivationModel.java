package com.tedla.amanuel.eagleapp.model;

import java.io.Serializable;

/**
 * Created by dVentus-hq on 7/23/2017.
 */
public class ActivationModel implements Serializable {
    private String key;
    private String customer_level;
    private String customer_id;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCustomer_level() {
        return customer_level;
    }

    public void setCustomer_level(String customer_level) {
        this.customer_level = customer_level;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }
}

package com.nailonline.client.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Roman T. on 20.12.2016.
 */

public class MasterLocation extends RealmObject {
    @PrimaryKey
    private int masterLocationId;
    private Integer masterId;
    private String address;
    private String comments;
    private String label;
    private float lat;
    private float lng;
    private Integer eRegionId;

    public int getMasterLocationId() {
        return masterLocationId;
    }

    public void setMasterLocationId(int masterLocationId) {
        this.masterLocationId = masterLocationId;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public Integer geteRegionId() {
        return eRegionId;
    }

    public void seteRegionId(Integer eRegionId) {
        this.eRegionId = eRegionId;
    }
}

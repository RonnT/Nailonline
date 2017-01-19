package com.nailonline.client.entity;

import io.realm.annotations.PrimaryKey;

/**
 * Created by Roman T. on 29.12.2016.
 */

public class Region  { //extends RealmObject {

    @PrimaryKey
    private int eRegionId;
    private String eRegionLabel;
    private Integer eRegionBelt;
    //private RealmList<RealmModel[]> eRegionBounds;

    public int geteRegionId() {
        return eRegionId;
    }

    public void seteRegionId(int eRegionId) {
        this.eRegionId = eRegionId;
    }

    public String geteRegionLabel() {
        return eRegionLabel;
    }

    public void seteRegionLabel(String eRegionLabel) {
        this.eRegionLabel = eRegionLabel;
    }

    public Integer geteRegionBelt() {
        return eRegionBelt;
    }

    public void seteRegionBelt(Integer eRegionBelt) {
        this.eRegionBelt = eRegionBelt;
    }
}

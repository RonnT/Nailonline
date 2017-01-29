package com.nailonline.client.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Roman T. on 29.01.2017.
 */

public class Present extends RealmObject{

    @PrimaryKey
    private int presentId;
    private String label;
    private Integer masterId;
    private String photoName;
    private Long presentCreated;
    private Integer presentEnable;

    public int getPresentId() {
        return presentId;
    }

    public void setPresentId(int presentId) {
        this.presentId = presentId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public Long getPresentCreated() {
        return presentCreated;
    }

    public void setPresentCreated(Long presentCreated) {
        this.presentCreated = presentCreated;
    }

    public Integer getPresentEnable() {
        return presentEnable;
    }

    public void setPresentEnable(Integer presentEnable) {
        this.presentEnable = presentEnable;
    }
}

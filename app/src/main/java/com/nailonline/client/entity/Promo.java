package com.nailonline.client.entity;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Roman on 10.12.2016.
 */

public class Promo extends RealmObject implements Serializable {

    @PrimaryKey
    private int promoId;
    private String body;
    private Long endDate;
    private String label;
    private Integer masterId;
    private String photoName;
    private Long promoCreated;
    private Integer promoState;
    private Long startDate;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
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

    public Long getPromoCreated() {
        return promoCreated;
    }

    public void setPromoCreated(Long promoCreated) {
        this.promoCreated = promoCreated;
    }

    public int getPromoId() {
        return promoId;
    }

    public void setPromoId(int promoId) {
        this.promoId = promoId;
    }

    public Integer getPromoState() {
        return promoState;
    }

    public void setPromoState(Integer promoState) {
        this.promoState = promoState;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }
}

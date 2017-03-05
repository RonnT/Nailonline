package com.nailonline.client.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Roman on 11.12.2016.
 */

public class Skill extends RealmObject{

    @PrimaryKey
    private Integer skillId;
    private Integer duration;
    private String label;
    private Integer masterId;
    private String photoName;
    private Integer presentId;
    private Integer price;
    private Integer templateId;
    private Integer unitId;
    private Integer userBonus;
    private Integer bonusPay;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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

    public Integer getPresentId() {
        return presentId;
    }

    public void setPresentId(Integer presentId) {
        this.presentId = presentId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getUserBonus() {
        return userBonus;
    }

    public void setUserBonus(Integer userBonus) {
        this.userBonus = userBonus;
    }

    public Integer getBonusPay() {
        return bonusPay;
    }

    public void setBonusPay(Integer bonusPay) {
        this.bonusPay = bonusPay;
    }
}

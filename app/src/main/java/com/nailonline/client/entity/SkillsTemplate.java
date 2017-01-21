package com.nailonline.client.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Roman T. on 20.01.2017.
 */

public class SkillsTemplate extends RealmObject {

    @PrimaryKey
    private Integer templateId;
    private Integer avDuration;
    private Integer avPrice;
    private Integer unitId;
    private String label;

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getAvDuration() {
        return avDuration;
    }

    public void setAvDuration(Integer avDuration) {
        this.avDuration = avDuration;
    }

    public Integer getAvPrice() {
        return avPrice;
    }

    public void setAvPrice(Integer avPrice) {
        this.avPrice = avPrice;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "SkillsTemplate{" +
                "templateId=" + templateId +
                ", avDuration=" + avDuration +
                ", avPrice=" + avPrice +
                ", unitId=" + unitId +
                ", label='" + label + '\'' +
                '}';
    }
}

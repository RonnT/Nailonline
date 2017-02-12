package com.nailonline.client.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Roman T. on 11.02.2017.
 */

public class DutyChart extends RealmObject{

    @PrimaryKey
    private int dutyId;
    private String type;
    private String days;
    private long startDate;
    private int masterId;
    private long finishDate;
    private int order;
    private int isOn;

    public void setDutyId(int dutyId) {
        this.dutyId = dutyId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public void setFinishDate(long finishDate) {
        this.finishDate = finishDate;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setIsOn(int isOn) {
        this.isOn = isOn;
    }

    public int getDutyId() {
        return dutyId;
    }

    public String getType() {
        return type;
    }

    public String getDays() {
        return days;
    }

    public long getStartDate() {
        return startDate;
    }

    public long getFinishDate() {
        return finishDate;
    }

    public int getOrder() {
        return order;
    }

    public int getIsOn() {
        return isOn;
    }

    public int getMasterId() {
        return masterId;
    }

    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }

    @Override
    public String toString() {
        return "DutyChart{" +
                "dutyId=" + dutyId +
                ", type='" + type + '\'' +
                ", days='" + days + '\'' +
                ", startDate=" + startDate +
                ", masterId=" + masterId +
                ", finishDate=" + finishDate +
                ", order=" + order +
                ", isOn=" + isOn +
                '}';
    }
}

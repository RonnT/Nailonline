package com.nailonline.client.entity;

import android.graphics.Color;

/**
 * Created by Roman T. on 19.02.2017.
 */

public enum JobState {
    REQUESTED(1, "#FA6800", "Забронирована"),
    ACCEPTED(2, "#6D8764", "Подтверждена"),
    POSTPONED(3, "#647687", "Перенесена"),
    REJECTED(4, "#5D5D64", "Отклонена"),
    COMPLETE(5, "#608A83", "Выполнена"),
    CANCELED(6, "#B15D64", "Отменена"),
    PAID(7, "#9689a4", "Оплачена"),
    NOT_PAID(8, "#8A8960", "Не оплачена"),
    EXPIRED(9, "#888888", "Просрочена");

    JobState(int stateId, String color, String stateText){
        id = stateId;
        this.color = color;
        text = stateText;
    }

    private int id;
    private String color;
    private String text;

    public int getId() {
        return id;
    }

    public int getColor() {
        return Color.parseColor(color);
    }

    public String getText() {
        return text;
    }

    public static JobState getById(int id){
        for (JobState state : values()){
            if (state.getId() == id) return state;
        }
        return null;
    }
}

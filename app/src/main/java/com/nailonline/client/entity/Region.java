package com.nailonline.client.entity;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Roman T. on 29.12.2016.
 */

public class Region extends RealmObject {

    @PrimaryKey
    private int eRegionId;
    private String eRegionLabel;
    private Integer eRegionBelt;
    private String eRegionBounds;

    @Ignore
    private List<LatLng> coordsList;

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

    public String geteRegionBounds() {
        return eRegionBounds;
    }

    public void seteRegionBounds(String eRegionBounds) {
        this.eRegionBounds = eRegionBounds;
    }

    public List<LatLng> getCoordsList() {
        if (coordsList == null) {
            coordsList = new ArrayList<>();
            try {
                JSONArray boundsArray = new JSONArray(eRegionBounds);
                for (int i = 0; i < boundsArray.length(); i++) {
                    //[56.8574,60.6149]
                    JSONArray coordsArray = new JSONArray(boundsArray.get(i).toString());
                    double lat = coordsArray.getDouble(0);
                    double lng = (float) coordsArray.getDouble(1);
                    coordsList.add(new LatLng(lat, lng));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return coordsList;
    }
}

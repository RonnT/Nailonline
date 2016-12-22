package com.nailonline.client.master;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nailonline.client.R;
import com.nailonline.client.entity.Master;
import com.nailonline.client.entity.MasterLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman T. on 22.12.2016.
 */

public class MasterMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    private MapView mapView;
    private List<Master> masterList;
    private List<Marker> markerList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_master_map, container, false);
        masterList = ((MasterTabActivity)getActivity()).getMasterList();
        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        setupMap();
    }

    protected void setupMap(){
        for (Master master : masterList){
            if (master.getMasterLocation() != null) addMarker(master);
        }
        setBounds();
    }

    protected void addMarker(Master masterItem) {
        MasterLocation location = masterItem.getMasterLocation();
        Drawable iconDrawable = VectorDrawableCompat.create(getResources(), R.drawable.master_marker, null);
        BitmapDescriptor markerIcon = getMarkerIconFromDrawable(iconDrawable);
        Marker addedMarker = map.addMarker(
                new MarkerOptions()
                        .position(new LatLng(location.getLat(), location.getLng()))
                        .title(null)
                        .icon(markerIcon)
                        .anchor(0.5F, 0.5F)
        );
        addedMarker.setTag(masterItem);
        markerList.add(addedMarker);
    }

    protected void setBounds() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (Marker marker : markerList) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();

        // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, (int) getActivity().getResources().getDimension(R.dimen.map_bounds_padding));

        map.animateCamera(cu);
    }


    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}

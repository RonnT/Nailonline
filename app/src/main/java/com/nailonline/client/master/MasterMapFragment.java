package com.nailonline.client.master;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.nailonline.client.BuildConfig;
import com.nailonline.client.R;
import com.nailonline.client.entity.Master;
import com.nailonline.client.entity.MasterLocation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by Roman T. on 22.12.2016.
 */

@RuntimePermissions
public class MasterMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

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
        map.setInfoWindowAdapter(new MasterInfoWindowAdpater());
        map.setOnInfoWindowLongClickListener(null);
        map.setOnInfoWindowClickListener(this);
        map.getUiSettings().setMapToolbarEnabled(false);
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (marker.getTag() != null) {
                    ((MasterTabActivity) getActivity()).makeNewOrder(((Master)marker.getTag()));
                }
            }
        });
        MasterMapFragmentPermissionsDispatcher.addMyLocationWithCheck(this);
        setupMap();
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    @SuppressWarnings("All")
    void addMyLocation(){
        map.setMyLocationEnabled(true);
    }

    protected void setupMap(){
        for (Master master : masterList){
            if (master.getMasterLocation() != null) addMarker(master);
        }
        setBounds();
    }

    protected void addMarker(Master masterItem) {
        MasterLocation location = masterItem.getMasterLocation();
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.master_marker);
        Marker addedMarker = map.addMarker(
                new MarkerOptions()
                        .position(new LatLng(location.getLat(), location.getLng()))
                        .title(masterItem.getMasterFirstName())
                        .icon(icon)
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

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(getActivity(),
                "Info Window clicked@" + marker.getId(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MasterMapFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private class MasterInfoWindowAdpater implements GoogleMap.InfoWindowAdapter {
        private final View mymarkerview;

        MasterInfoWindowAdpater() {
            mymarkerview = getActivity().getLayoutInflater()
                    .inflate(R.layout.view_master_info_window, null);
        }

        public View getInfoWindow(Marker marker) {
            return null;
        }

        public View getInfoContents(Marker marker) {
            render(marker, mymarkerview);
            return mymarkerview;
        }

        private void render(Marker marker, View view) {
            Master masterItem = (Master) marker.getTag();
            ImageView photo = (ImageView) view.findViewById(R.id.master_photo);
            TextView masterName = (TextView) view.findViewById(R.id.master_name);
            TextView masterAddress = (TextView) view.findViewById(R.id.master_address);
            Picasso.with(getActivity())
                    .load(BuildConfig.SERVER_IMAGE_MASTER_PHOTO + masterItem.getMasterPhoto())
                    .into(photo);
            masterName.setText(masterItem.getMasterFirstName());
            masterAddress.setText(masterItem.getMasterLocation().getAddress());
        }
    }
}

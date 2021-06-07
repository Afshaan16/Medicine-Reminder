package com.mhss.gomed.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mhss.gomed.R;

public class MedicalFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    Context mContext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_medical, container, false);
        mContext = root.getContext();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return root;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String[] name = {"sameer medical store","welness forever","Sanjay Medical store","shayaan medical","Flora chemist","Medcure medical","Navya medical","healthcare medical","Salman medical Store","Siddhi medical store","Zayan medical store","shree medical Store","Jain mangal murti medical store","Bijal Medical & General Store","Well Life Chemist"};
        double[] lati = {19.2825815,19.282338,19.2798805,19.2831162,19.2802839,19.2837052,19.2820166,19.2815835,19.2814992,19.2858194,19.2829853,19.2814992,19.2828461,19.285984,19.286027};
        double[] longi = {72.8556572,72.8606495,72.8604636,72.8566411,72.8634855,72.8608027,72.8612908,72.8622217,72.8566411,72.8544363,72.8566411,72.8566411,72.8607005,72.86122,72.861059};
        for(int i = 0 ; i < name.length;i++) {
            LatLng sydney = new LatLng(lati[i], longi[i]);
            mMap.addMarker(new MarkerOptions().position(sydney).title(name[i]));
            mMap.setMinZoomPreference(16.0f);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }

}
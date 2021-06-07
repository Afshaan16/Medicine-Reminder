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

public class DoctorFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    Context mContext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_doctor, container, false);

        mContext = root.getContext();
        // Map Code
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return root;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String[] name = {"Dr.Musani","Dr.S.v.Naik","Dr.Imran Shaikh","Dr.Hussain","Dr.Murthy","Dr.Hidayat","Dr.Abhijeet","Dr.Ashish Shetty","Dr.Alimchandani","Fayth Clinic","Riddhi Clinic","Genesis Clinic","Anay Clinic","Shree Sai Clinic","Dr Vigils","Mahavir Clinic","Umair Clinic","Smile Forever Dental clinic","Safilan Clinic"};
        double[] lati = {19.27578345,19.27826166,19.28472342,19.27659468,19.28306793,19.28430777,19.28131488,19.28812311,19.29602886,19.28239263,19.27565801,19.27713244,19.28140345,19.29608368,19.27493365,19.27904206,19.285911,19.285958,19.2859252};
        double[] longi = {72.86336727,72.86388801,72.86222853,72.87104003,72.86635125,72.85922017,72.86909809,72.86855975,72.8607476,72.87709078,72.872168,72.87893313,72.86390612,72.87491859,72.86214226,72.86546,72.8615,72.861456,72.861049};
        for(int i = 0 ; i < name.length;i++) {
            LatLng pointer = new LatLng(lati[i], longi[i]);
            mMap.addMarker(new MarkerOptions().position(pointer).title(name[i]));
            mMap.setMinZoomPreference(15.0f);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(pointer));
        }
    }

}
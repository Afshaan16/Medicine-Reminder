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

public class HospitalFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    Context mContext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hospital, container, false);
        mContext = root.getContext();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return root;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String[] name = {"Shah Lifeline Hospital","Wockhardt Hospital","Suvarna Hospital","Indira Gandhi Municipal Hospital","Vipasana Hospital","Lifeline Hospital","Life Care Hospital","Tanwar Hospital","Bhaktivedanta Hospital ","Sai Aashirwad Hopsital","Sheth P V doshi Hospital","Ansh Hospital","Family Hospital","Gayatri Hospital","Global MultiSpeciality","Tunga Hospital"};
        double[] lati = {19.29478102,19.28430701,19.28787308,19.27418194,19.28320431,19.29496218,19.28412001,19.29386345,19.27010712,19.28628268,19.27825444,19.30071458,19.29672498,19.28154408,19.29355482,19.29225556};
        double[] longi = {72.85603511,72.86223435,72.86601967,72.86088912,72.86590227,72.85606156,72.86580504,72.86255026,72.87049823,72.86358269,72.86459475,72.87188,72.86460967,72.8602723,72.87095717,72.86236686};
        for(int i = 0 ; i < name.length;i++) {
            LatLng sydney = new LatLng(lati[i], longi[i]);
            mMap.addMarker(new MarkerOptions().position(sydney).title(name[i]));
            mMap.setMinZoomPreference(15.0f);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }

}
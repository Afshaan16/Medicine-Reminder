package com.mhss.gomed;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.mhss.gomed.preferences.Preferences;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        ((TextView) hView.findViewById(R.id.name)).setText(Preferences.getPreferenceValue(getApplicationContext(),Preferences.KEY_NAME));
        ((TextView) hView.findViewById(R.id.mobile)).setText(Preferences.getPreferenceValue(getApplicationContext(),Preferences.KEY_MOBILE));
        ((TextView) hView.findViewById(R.id.email)).setText(Preferences.getPreferenceValue(getApplicationContext(),Preferences.KEY_EMAIL));
        String val = Preferences.getPreferenceValue(getApplicationContext(),Preferences.KEY_IMAGE);
        if(val.equalsIgnoreCase("1")){
            ((ImageView) hView.findViewById(R.id.photo)).setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.boy));
        }else if(val.equalsIgnoreCase("2")){
            ((ImageView) hView.findViewById(R.id.photo)).setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.boytwo));
        }else if(val.equalsIgnoreCase("3")){
            ((ImageView) hView.findViewById(R.id.photo)).setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.boythree));
        }else if(val.equalsIgnoreCase("4")){
            ((ImageView) hView.findViewById(R.id.photo)).setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.girl));
        }else if(val.equalsIgnoreCase("5")){
            ((ImageView) hView.findViewById(R.id.photo)).setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.girlone));
        }else if(val.equalsIgnoreCase("6")){
            ((ImageView) hView.findViewById(R.id.photo)).setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.girlthree));
        }else if(val.equalsIgnoreCase("7")){
            ((ImageView) hView.findViewById(R.id.photo)).setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.girlfour));
        }
            // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_med_reminder, R.id.nav_health,
                R.id.nav_first, R.id.nav_notes, R.id.nav_bmi,R.id.nav_hosp,
                R.id.nav_doc,R.id.nav_medical,R.id.nav_profile,R.id.nav_send,
                R.id.nav_share)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            show_customized_popup();
        }
    }

    public void show_customized_popup() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.popup_decigion);
        ImageView icon = (ImageView) dialog.findViewById(R.id.iv_decigion_icon);

        TextView tv_header = (TextView) dialog
                .findViewById(R.id.tv_decigion_heading);
        tv_header.setText(getResources().getString(R.string.exit));

        TextView tv_title = (TextView) dialog
                .findViewById(R.id.tv_decigion_title);
        tv_title.setText(getResources().getString(R.string.exit_app));

        Button bt_yes = (Button) dialog.findViewById(R.id.bt_yes);
        bt_yes.setText(getResources().getString(R.string.yes));
        bt_yes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        Button bt_no = (Button) dialog.findViewById(R.id.bt_no);
        bt_no.setText(getResources().getString(R.string.no));
        bt_no.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}

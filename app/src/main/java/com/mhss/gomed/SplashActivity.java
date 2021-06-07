package com.mhss.gomed;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.mhss.gomed.preferences.Preferences;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                requestPermission();
            }
        }, 2000);
    }

    public void init() {
        if (Preferences.getPreferenceValue(getApplicationContext(), Preferences.KEY_IS_LOGIN) != null && Preferences.getPreferenceValue(getApplicationContext(), Preferences.KEY_IS_LOGIN).equalsIgnoreCase("true")) {
            Intent intent = new Intent(SplashActivity.this, OTPValidationActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
            startActivity(intent);
        }
        overridePendingTransition(R.anim.slide_in_from_right,
                R.anim.slide_out_to_left);
        finish();
    }

    public void onBackPressed() {

    }

    private static final int PERMISSIONS_REQUEST_LOCATION = 102;

    private void requestPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_LOCATION);

            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // service to get notification subscription category & making external
            // queue
            init();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_LOCATION: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(android.Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++) {
                    perms.put(permissions[i], grantResults[i]);
                }
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
                    init();
                } else {
                    // Permission Denied
                    /*if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                        showRequestDialog(false, "LOCATION");
                    } else {
                        showRequestDialog(true, "LOCATION");
                    }*/
                    init();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //dialog for app permission request
    protected void showRequestDialog(final boolean isContinue, String name) {

        final Dialog requestDialog = new Dialog(SplashActivity.this);
        requestDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestDialog.setCanceledOnTouchOutside(false);
        requestDialog.setContentView(R.layout.request_permission);
        Button exit_app, procceed_app;
        requestDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        requestDialog.show();
        // for Cancel button click
        exit_app = (Button) requestDialog.findViewById(R.id.exit_app);
        // for OK button click
        procceed_app = (Button) requestDialog.findViewById(R.id.procceed_app);
        TextView infotxt = (TextView) requestDialog.findViewById(R.id.infoTXT);
        if (!isContinue) {
            procceed_app.setText("App Settings");
            infotxt.setText("Until you grant the " + name + " permission, you are not able to proceed in the app.If you want to use this mobile app, please go to the  App Settings then Storage and clear its data to accept the permission or Exit from the App.");
        } else {
            procceed_app.setText("Continue");
            infotxt.setText("Until you grant the " + name + " permission, you are not able to proceed in the app. Would you like to accept permission?");
        }
        exit_app.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                requestDialog.dismiss();
                SplashActivity.this.finish();
                System.exit(0);
            }
        });
        procceed_app.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                requestDialog.dismiss();
                if (!isContinue) {
                    startInstalledAppDetailsActivity(SplashActivity.this);
                } else {
                    requestPermission();
                }
            }
        });
    }

    private void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
        finish();
    }

}

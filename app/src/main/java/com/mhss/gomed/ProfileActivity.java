package com.mhss.gomed;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.mhss.gomed.MainActivity;
import com.mhss.gomed.R;
import com.mhss.gomed.preferences.Preferences;

public class ProfileActivity extends AppCompatActivity {

    EditText name, email, mobile, c_mobile, c_email;
    Button update;
    ImageView im1, im2, im3, im4, im5, im6, im7;
    Context mContext;
    int is_selected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mContext = getApplicationContext();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Profile");
        name = (EditText) findViewById(R.id.input_name);
        name.setText(Preferences.getPreferenceValue(mContext, Preferences.KEY_NAME));
        email = (EditText) findViewById(R.id.input_email);
        email.setText(Preferences.getPreferenceValue(mContext, Preferences.KEY_EMAIL));
        mobile = (EditText) findViewById(R.id.input_mobile);
        mobile.setText(Preferences.getPreferenceValue(mContext, Preferences.KEY_MOBILE));
        c_mobile = (EditText) findViewById(R.id.input_c_mobile);
        c_mobile.setText(Preferences.getPreferenceValue(mContext, Preferences.KEY_C_MOBILE));
        c_email = (EditText) findViewById(R.id.input_c_email);
        c_email.setText(Preferences.getPreferenceValue(mContext, Preferences.KEY_C_EMAIL));
        update = (Button) findViewById(R.id.btn_update);

        im1 = (ImageView) findViewById(R.id.img_1);
        im2 = (ImageView) findViewById(R.id.img_2);
        im3 = (ImageView) findViewById(R.id.img_3);
        im4 = (ImageView) findViewById(R.id.img_4);
        im5 = (ImageView) findViewById(R.id.img_5);
        im6 = (ImageView) findViewById(R.id.img_6);
        im7 = (ImageView) findViewById(R.id.img_7);

        try {
            is_selected = Integer.parseInt(Preferences.getPreferenceValue(mContext, Preferences.KEY_IMAGE));
        } catch (Exception e) {
            is_selected = 0;
        }
        if (is_selected == 1) {
            im1.setColorFilter(Color.argb(50, 0, 0, 0));
        } else if (is_selected == 2) {
            im2.setColorFilter(Color.argb(50, 0, 0, 0));
        } else if (is_selected == 3) {
            im3.setColorFilter(Color.argb(50, 0, 0, 0));
        } else if (is_selected == 4) {
            im4.setColorFilter(Color.argb(50, 0, 0, 0));
        } else if (is_selected == 5) {
            im5.setColorFilter(Color.argb(50, 0, 0, 0));
        } else if (is_selected == 6) {
            im6.setColorFilter(Color.argb(50, 0, 0, 0));
        } else if (is_selected == 7) {
            im7.setColorFilter(Color.argb(50, 0, 0, 0));
        }

        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_selected = 1;
                im1.setColorFilter(Color.argb(50, 0, 0, 0));
                im2.setColorFilter(Color.argb(0, 0, 0, 0));
                im3.setColorFilter(Color.argb(0, 0, 0, 0));
                im4.setColorFilter(Color.argb(0, 0, 0, 0));
                im5.setColorFilter(Color.argb(0, 0, 0, 0));
                im6.setColorFilter(Color.argb(0, 0, 0, 0));
                im7.setColorFilter(Color.argb(0, 0, 0, 0));
            }
        });

        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_selected = 2;
                im2.setColorFilter(Color.argb(50, 0, 0, 0));
                im1.setColorFilter(Color.argb(0, 0, 0, 0));
                im3.setColorFilter(Color.argb(0, 0, 0, 0));
                im4.setColorFilter(Color.argb(0, 0, 0, 0));
                im5.setColorFilter(Color.argb(0, 0, 0, 0));
                im6.setColorFilter(Color.argb(0, 0, 0, 0));
                im7.setColorFilter(Color.argb(0, 0, 0, 0));
            }
        });

        im3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_selected = 3;
                im3.setColorFilter(Color.argb(50, 0, 0, 0));
                im2.setColorFilter(Color.argb(0, 0, 0, 0));
                im1.setColorFilter(Color.argb(0, 0, 0, 0));
                im4.setColorFilter(Color.argb(0, 0, 0, 0));
                im5.setColorFilter(Color.argb(0, 0, 0, 0));
                im6.setColorFilter(Color.argb(0, 0, 0, 0));
                im7.setColorFilter(Color.argb(0, 0, 0, 0));
            }
        });

        im4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_selected = 4;
                im4.setColorFilter(Color.argb(50, 0, 0, 0));
                im2.setColorFilter(Color.argb(0, 0, 0, 0));
                im3.setColorFilter(Color.argb(0, 0, 0, 0));
                im1.setColorFilter(Color.argb(0, 0, 0, 0));
                im5.setColorFilter(Color.argb(0, 0, 0, 0));
                im6.setColorFilter(Color.argb(0, 0, 0, 0));
                im7.setColorFilter(Color.argb(0, 0, 0, 0));
            }
        });

        im5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_selected = 5;
                im5.setColorFilter(Color.argb(50, 0, 0, 0));
                im2.setColorFilter(Color.argb(0, 0, 0, 0));
                im3.setColorFilter(Color.argb(0, 0, 0, 0));
                im4.setColorFilter(Color.argb(0, 0, 0, 0));
                im1.setColorFilter(Color.argb(0, 0, 0, 0));
                im6.setColorFilter(Color.argb(0, 0, 0, 0));
                im7.setColorFilter(Color.argb(0, 0, 0, 0));
            }
        });

        im6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_selected = 6;
                im6.setColorFilter(Color.argb(50, 0, 0, 0));
                im2.setColorFilter(Color.argb(0, 0, 0, 0));
                im3.setColorFilter(Color.argb(0, 0, 0, 0));
                im4.setColorFilter(Color.argb(0, 0, 0, 0));
                im5.setColorFilter(Color.argb(0, 0, 0, 0));
                im1.setColorFilter(Color.argb(0, 0, 0, 0));
                im7.setColorFilter(Color.argb(0, 0, 0, 0));
            }
        });

        im7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_selected = 7;
                im7.setColorFilter(Color.argb(50, 0, 0, 0));
                im2.setColorFilter(Color.argb(0, 0, 0, 0));
                im3.setColorFilter(Color.argb(0, 0, 0, 0));
                im4.setColorFilter(Color.argb(0, 0, 0, 0));
                im5.setColorFilter(Color.argb(0, 0, 0, 0));
                im6.setColorFilter(Color.argb(0, 0, 0, 0));
                im1.setColorFilter(Color.argb(0, 0, 0, 0));
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText() != null && name.getText().length() > 0) {
                    if (email.getText() != null && email.getText().length() > 0) {
                        if (mobile.getText() != null && mobile.getText().length() == 10) {
                            if (c_mobile.getText() != null && c_mobile.getText().length() == 10) {
                                if (c_email.getText() != null && c_email.getText().length() > 0) {
                                    if (is_selected > 0) {
                                        Preferences.setPreferenceValue(mContext, Preferences.KEY_NAME, name.getText().toString());
                                        Preferences.setPreferenceValue(mContext, Preferences.KEY_EMAIL, email.getText().toString());
                                        Preferences.setPreferenceValue(mContext, Preferences.KEY_MOBILE, mobile.getText().toString());
                                        Preferences.setPreferenceValue(mContext, Preferences.KEY_C_MOBILE, c_mobile.getText().toString());
                                        Preferences.setPreferenceValue(mContext, Preferences.KEY_C_EMAIL, c_email.getText().toString());
                                        Preferences.setPreferenceValue(mContext, Preferences.KEY_IMAGE, is_selected + "");
                                        Toast.makeText(mContext, "Profile Updated Succesfully", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(mContext, EnterOTPActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(mContext, "Please Select Profile Picture", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(mContext, "Please Enter Caretaker Email ID", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(mContext, "Please Enter Caretaker Mobile Number", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(mContext, "Please Enter Mobile number", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(mContext, "Please Enter Email Id", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mContext, "Please Enter Name", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
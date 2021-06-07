package com.mhss.gomed;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mhss.gomed.preferences.Preferences;

public class OTPValidationActivity extends AppCompatActivity {

    EditText et1, et2, et3, et4;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText2);
        et3 = (EditText) findViewById(R.id.editText3);
        et4 = (EditText) findViewById(R.id.editText4);
        et1.addTextChangedListener(new GenericTextWatcher(et1));
        et2.addTextChangedListener(new GenericTextWatcher(et2));
        et3.addTextChangedListener(new GenericTextWatcher(et3));
        et4.addTextChangedListener(new GenericTextWatcher(et4));
        update = (Button) findViewById(R.id.btn_update);
        toolbar.setTitle("Enter MPIN");
        update.setText("Enter MPIN");
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et1.getText().toString().length() > 0 && et2.getText().toString().length() > 0 && et3.getText().toString().length() > 0 && et4.getText().toString().length() > 0) {
                    String OTP = Preferences.getPreferenceValue(getApplicationContext(),Preferences.KEY_OTP);;
                    String input = et1.getText()+""+et2.getText()+""+et3.getText()+""+et4.getText();
                    if(OTP.equalsIgnoreCase(input)){
                        Toast.makeText(getApplicationContext(), "MPIN Validate Succesfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "MPIN Invalid", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Set OTP", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(OTPValidationActivity.this, EnterOTPActivity.class));
        finish();
    }

    public class GenericTextWatcher implements TextWatcher {
        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch (view.getId()) {

                case R.id.editText1:
                    if (text.length() == 1)
                        et2.requestFocus();
                    break;
                case R.id.editText2:
                    if (text.length() == 1)
                        et3.requestFocus();
                    else if (text.length() == 0)
                        et1.requestFocus();
                    break;
                case R.id.editText3:
                    if (text.length() == 1)
                        et4.requestFocus();
                    else if (text.length() == 0)
                        et2.requestFocus();
                    break;
                case R.id.editText4:
                    if (text.length() == 0)
                        et3.requestFocus();
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
    }
}
package com.mhss.gomed;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mhss.gomed.other.DBAdapter;
import com.mhss.gomed.other.ReminderTable;
import com.mhss.gomed.other.ReminderTimeTable;

import java.util.Calendar;
import java.util.Objects;


public class ADReminderActivity extends AppCompatActivity {

    private EditText _nameText, _doseUnit, _totalDose, _firstDose, _secoundDose, _thirdDose, _startDate, _endDate;
    private Spinner _type, _unit, _freq;
    private CheckBox _remReq;
    private Button _submitButton;

    private Toolbar mToolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_reminder);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initView();
    }

    public void initView() {

        _nameText = (EditText) findViewById(R.id.input_name);
        _doseUnit = (EditText) findViewById(R.id.input_one_time);
        _totalDose = (EditText) findViewById(R.id.input_totatl_dose);
        _firstDose = (EditText) findViewById(R.id.input_time_1);
        _secoundDose = (EditText) findViewById(R.id.input_time_2);
        _thirdDose = (EditText) findViewById(R.id.input_time_3);
        _startDate = (EditText) findViewById(R.id.input_start_date);
        _endDate = (EditText) findViewById(R.id.input_end_date);

        _unit = (Spinner) findViewById(R.id.input_unit);
        _type = (Spinner) findViewById(R.id.input_type);
        _freq = (Spinner) findViewById(R.id.input_freq);
        _freq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 1) {
                    _firstDose.setVisibility(View.VISIBLE);
                    _secoundDose.setVisibility(View.INVISIBLE);
                    _thirdDose.setVisibility(View.INVISIBLE);
                } else if (position == 2) {
                    _firstDose.setVisibility(View.VISIBLE);
                    _secoundDose.setVisibility(View.VISIBLE);
                    _thirdDose.setVisibility(View.INVISIBLE);
                } else if (position == 3) {
                    _firstDose.setVisibility(View.VISIBLE);
                    _secoundDose.setVisibility(View.VISIBLE);
                    _thirdDose.setVisibility(View.VISIBLE);
                } else {
                    _firstDose.setVisibility(View.INVISIBLE);
                    _secoundDose.setVisibility(View.INVISIBLE);
                    _thirdDose.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        _remReq = (CheckBox) findViewById(R.id.remReq);

        _submitButton = (Button) findViewById(R.id.btn_submit);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Medicine");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit) {
            login(true);
        } else if (v.getId() == R.id.input_time_1) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY);
            int mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            String time = ((hourOfDay <= 9) ? "0" + hourOfDay : hourOfDay + "");
                            time += ":";
                            time += (((minute <= 9) ? "0" + minute : minute));
                            _firstDose.setText(time);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        } else if (v.getId() == R.id.input_time_2) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY);
            int mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            String time = ((hourOfDay <= 9) ? "0" + hourOfDay : hourOfDay + "");
                            time += ":";
                            time += (((minute <= 9) ? "0" + minute : minute));
                            _secoundDose.setText(time);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        } else if (v.getId() == R.id.input_time_3) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY);
            int mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            String time = ((hourOfDay <= 9) ? "0" + hourOfDay : hourOfDay + "");
                            time += ":";
                            time += (((minute <= 9) ? "0" + minute : minute));
                            _thirdDose.setText(time);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        } else if (v.getId() == R.id.input_start_date) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            String date = ((dayOfMonth <= 9) ? "0" + dayOfMonth : dayOfMonth + "");
                            date += "-";
                            date += ((((monthOfYear + 1) <= 9) ? "0" + (monthOfYear + 1) : (monthOfYear + 1)));
                            date += "-" + year;
                            _startDate.setText(date);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        } else if (v.getId() == R.id.input_end_date) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            String date = ((dayOfMonth <= 9) ? "0" + dayOfMonth : dayOfMonth + "");
                            date += "-";
                            date += ((((monthOfYear + 1) <= 9) ? "0" + (monthOfYear + 1) : (monthOfYear + 1)));
                            date += "-" + year;
                            _endDate.setText(date);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    public void login(boolean status) {
        if (status) {
            if (!validate()) {
                onLoginFailed();
                return;
            }
        }
        _submitButton.setEnabled(false);
        String message = "";
        DBAdapter dba = new DBAdapter(getApplicationContext());
        dba.open();
        message = "Medicine Added Succesfully...";
        ReminderTable mRem = new ReminderTable();
        mRem.setReminderName(_nameText.getText().toString());
        mRem.setReminderType(_type.getSelectedItem().toString());
        mRem.setOneTimeDose(_doseUnit.getText().toString());
        mRem.setReminderUnit(_unit.getSelectedItem().toString());
        mRem.setTotalDose(_totalDose.getText().toString());
        mRem.setRemainingDose(_totalDose.getText().toString());
        mRem.setDoseCount(_freq.getSelectedItem().toString());
        mRem.setIsReq(_remReq.isChecked() + "");
        mRem.setStartDate(_startDate.getText().toString());
        mRem.setEndDate(_endDate.getText().toString());
        int id = dba.insertReminder(mRem);
        String[] temp = new String[]{_firstDose.getText().toString(), _secoundDose.getText().toString(), _thirdDose.getText().toString()};
        for (int i = 0; i < Integer.parseInt(mRem.getDoseCount()); i++) {
            ReminderTimeTable mTime = new ReminderTimeTable();
            mTime.setReminderId(id);
            mTime.setReminderTime(temp[i]);
            int id1 = dba.insertReminderTime(mTime);
            if(_remReq.isChecked())
                setReminder(mTime , id ,id1, mRem.getStartDate());
        }
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        dba.close();
        onSuccess();
    }

    public void setReminder(ReminderTimeTable mTime , int id ,int count, String date) {


        String sp[] = date.split("-");
        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.DAY_OF_MONTH,Integer.parseInt(sp[0]));
        c1.set(Calendar.MONTH,Integer.parseInt(sp[1])-1);
        c1.set(Calendar.YEAR,Integer.parseInt(sp[2]));
        int dayOfWeek = c1.get(Calendar.DAY_OF_WEEK);

        //Toast.makeText(getApplicationContext(), "Set", Toast.LENGTH_LONG).show();
        /** This intent invokes the activity ReminderActivity, which in turn opens the AlertAlarm window */
        Intent intent = new Intent(getApplicationContext(), AlarmReminderActivity.class);
        intent.putExtra(AlarmReminderActivity.EXTRA_ID, id+"");

        PendingIntent operation = PendingIntent.getActivity(getApplicationContext(), (100+count), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        /** Getting a reference to the System Service ALARM_SERVICE */
        AlarmManager alarmManager = (AlarmManager) Objects.requireNonNull(getApplicationContext()).getSystemService(ALARM_SERVICE);

        /** Creating a calendar object corresponding to the date and time set by the user */
        Calendar calendar = Calendar.getInstance();
        String[] split = mTime.getReminderTime().split(":");
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(split[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(split[1]));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);

        /** Converting the date and time in to milliseconds elapsed since epoch */
        long alarm_time = calendar.getTimeInMillis();

        if (calendar.before(Calendar.getInstance()))
            alarm_time += AlarmManager.INTERVAL_DAY * 7;

        assert alarmManager != null;
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm_time, operation);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarm_time,
                AlarmManager.INTERVAL_DAY * 7, operation);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm_time, operation);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onSuccess() {
        Intent i = new Intent("android.intent.action.medicine");
        getApplicationContext().sendBroadcast(i);
        finish();
    }

    public void onLoginFailed() {
        _submitButton.setEnabled(true);
    }

    public boolean validate() {

        if (_nameText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Enter Medicine Name", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_type.getSelectedItemId() == 0) {
            Toast.makeText(getApplicationContext(), "Please Select Medicine Type", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_doseUnit.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Enter Dose Unit", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_totalDose.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Enter Total Dose", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_freq.getSelectedItemId() == 0) {
            Toast.makeText(getApplicationContext(), "Please Select Dose Frequency", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_firstDose.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Enter 1st Dose Time", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_freq.getSelectedItemId() == 2) {
            if (_secoundDose.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please Enter 2nd Dose Time", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (_freq.getSelectedItemId() == 3) {
            if (_thirdDose.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please Enter 3rd Dose Time", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (_startDate.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Enter Start Date", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_endDate.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Enter End Date", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
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
        tv_header.setText(getResources().getString(R.string.delete));

        TextView tv_title = (TextView) dialog
                .findViewById(R.id.tv_decigion_title);
        tv_title.setText(getResources().getString(R.string.delete_app));

        Button bt_yes = (Button) dialog.findViewById(R.id.bt_yes);
        bt_yes.setText(getResources().getString(R.string.yes));
        bt_yes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
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

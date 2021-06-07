package com.mhss.gomed;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mhss.gomed.other.DBAdapter;
import com.mhss.gomed.other.ReminderDTO;
import com.mhss.gomed.other.ReminderTable;
import com.mhss.gomed.other.ReminderTimeTable;
import com.mhss.gomed.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;

public class AlarmReminderActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "extra_id";

    private MediaPlayer mMediaPlayer;

    private Vibrator mVibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        Bundle extras = getIntent().getExtras();
        String id = extras.getString(EXTRA_ID);
        String name = "";
        DBAdapter dba = new DBAdapter(getApplicationContext());
        dba.open();
        Cursor mCursor = dba.getReminder(id);
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {
                if (mCursor.moveToFirst()) {
                    do {
                        name = mCursor.getString(mCursor.getColumnIndex(ReminderTable.KEY_ReminderName));
                        ((TextView) findViewById(R.id.name)).setText(name);
                        ((TextView) findViewById(R.id.type)).setText("Type : " + mCursor.getString(mCursor.getColumnIndex(ReminderTable.KEY_ReminderType)));
                        ((TextView) findViewById(R.id.unit)).setText("Unit : " + mCursor.getString(mCursor.getColumnIndex(ReminderTable.KEY_ReminderUnit)));
                    }
                    while (mCursor.moveToNext());
                }
            }
        }
        dba.close();
        final String name1 = name;
        ((ImageView) findViewById(R.id.close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ((Button) findViewById(R.id.yes)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBAdapter dba = new DBAdapter(getApplicationContext());
                dba.open();
                dba.updatePills(name1,id,1);
                dba.close();
                finish();
            }
        });

        ((Button) findViewById(R.id.no)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBAdapter dba = new DBAdapter(getApplicationContext());
                dba.open();
                dba.updatePills(name1,id,0);
                dba.close();
                finish();
            }
        });

        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        long[] pattern = {0, 1000, 10000};
        mVibrator.vibrate(pattern, 0);

        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.cuco_sound);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();

    }

    @Override
    public void onStop() {
        super.onStop();
        stopMedialPlayer();
        stopVibrator();
    }

    private void stopMedialPlayer() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }

    private void stopVibrator() {
        if (mVibrator != null) {
            mVibrator.cancel();
        }
    }

}
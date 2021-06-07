package com.mhss.gomed.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mhss.gomed.R;
import com.mhss.gomed.other.DBAdapter;
import com.mhss.gomed.other.HomeAdapter;
import com.mhss.gomed.other.ReminderAdapter;
import com.mhss.gomed.other.ReminderDTO;
import com.mhss.gomed.other.ReminderTable;
import com.mhss.gomed.other.ReminderTimeTable;
import com.mhss.gomed.other.SliderAdapter;
import com.mhss.gomed.other.SliderData;
import com.mhss.gomed.preferences.Preferences;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private HomeAdapter mAdapter;
    private List<ReminderDTO> mReminder;
    private TextView no_record;

    public HomeFragment() {
    }

    public Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_home, container,
                false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        mContext = rootView.getContext();
        no_record = (TextView) rootView.findViewById(R.id.no_record);
        mReminder = new ArrayList<>();
        mAdapter = new HomeAdapter(rootView.getContext(), mReminder);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new ReminderAdapter.RecyclerTouchListener(rootView.getContext(), recyclerView, new ReminderAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(50);
                }

                String[] colors = {"SMS", "Email"};
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Send Medicine Alert");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + Preferences.getPreferenceValue(mContext,Preferences.KEY_C_MOBILE)));
                            intent.putExtra("sms_body", "Please order medicine "+mReminder.get(position).getReminderName()+" , 75% is consumed");
                            startActivity(intent);
                        } else if (which == 1) {
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("message/rfc822");
                            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{Preferences.getPreferenceValue(mContext,Preferences.KEY_C_EMAIL)});
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Medicine Reminder");
                            intent.putExtra(Intent.EXTRA_TEXT, "Please order medicine "+mReminder.get(position).getReminderName()+" , 75% is consumed");
                            startActivity(Intent.createChooser(intent, "Send Email"));
                        }
                    }
                });
                builder.show();
            }
        }));
        loadData();
        setHasOptionsMenu(true);

        // Slider Code
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
        SliderView sliderView = rootView.findViewById(R.id.slider);
        sliderDataArrayList.add(new SliderData(R.drawable.tip1));
        sliderDataArrayList.add(new SliderData(R.drawable.tip2));
        sliderDataArrayList.add(new SliderData(R.drawable.tip3));
        sliderDataArrayList.add(new SliderData(R.drawable.tip4));
        sliderDataArrayList.add(new SliderData(R.drawable.tip5));
        SliderAdapter adapter = new SliderAdapter(mContext, sliderDataArrayList);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(adapter);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        return rootView;
    }


    public void loadData() {
        mReminder.clear();
        DBAdapter dba = new DBAdapter(mContext);
        dba.open();
        Cursor mCursor = dba.getReminder();
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {
                if (mCursor.moveToFirst()) {
                    do {
                        ReminderDTO mData = new ReminderDTO();
                        mData.setReminderId(Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(ReminderTable.KEY_ReminderId))));
                        mData.setReminderName(mCursor.getString(mCursor.getColumnIndex(ReminderTable.KEY_ReminderName)));
                        mData.setReminderType(mCursor.getString(mCursor.getColumnIndex(ReminderTable.KEY_ReminderType)));
                        mData.setReminderUnit(mCursor.getString(mCursor.getColumnIndex(ReminderTable.KEY_ReminderUnit)));
                        mData.setTotalDose(mCursor.getString(mCursor.getColumnIndex(ReminderTable.KEY_TotalDose)));
                        mData.setRemainingDose(mCursor.getString(mCursor.getColumnIndex(ReminderTable.KEY_RemainingDose)));
                        mData.setStartDate(mCursor.getString(mCursor.getColumnIndex(ReminderTable.KEY_ReminderStartDate)));
                        mData.setEndDate(mCursor.getString(mCursor.getColumnIndex(ReminderTable.KEY_ReminderEndDate)));
                        Cursor timeCursor = dba.getReminderTime(mData.getReminderId() + "");
                        List<ReminderTimeTable> temp = new ArrayList<>();
                        if (timeCursor != null) {
                            if (timeCursor.getCount() > 0) {
                                if (timeCursor.moveToFirst()) {
                                    do {
                                        ReminderTimeTable t1 = new ReminderTimeTable();
                                        t1.setReminderTimeId(Integer.parseInt(timeCursor.getString(timeCursor.getColumnIndex(ReminderTimeTable.KEY_ReminderTimeId))));
                                        t1.setReminderId(Integer.parseInt(timeCursor.getString(timeCursor.getColumnIndex(ReminderTimeTable.KEY_ReminderId))));
                                        t1.setReminderTime(timeCursor.getString(timeCursor.getColumnIndex(ReminderTimeTable.KEY_ReminderTime)));
                                        temp.add(t1);
                                    } while (timeCursor.moveToNext());
                                }
                            }
                        }
                        mData.setList(temp);
                        double total = Double.parseDouble(mData.getTotalDose());
                        double rem = Double.parseDouble(mData.getRemainingDose());
                        double per = (rem / total) * 100;
                        mData.setPercent(String.format("%.2f", per));
                        if (per <= 25) {
                            mReminder.add(mData);
                        }
                    }
                    while (mCursor.moveToNext());
                    if (!(mReminder.size() > 0)) {
                        no_record.setVisibility(View.VISIBLE);
                    } else {
                        mAdapter.notifyDataSetChanged();
                        no_record.setVisibility(View.GONE);
                    }
                } else {
                    no_record.setVisibility(View.VISIBLE);
                }
            } else {
                no_record.setVisibility(View.VISIBLE);
            }
        } else {
            no_record.setVisibility(View.VISIBLE);
        }
        dba.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
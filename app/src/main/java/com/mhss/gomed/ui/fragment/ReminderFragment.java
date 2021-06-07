package com.mhss.gomed.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mhss.gomed.ADReminderActivity;
import com.mhss.gomed.AEDNotesActivity;
import com.mhss.gomed.R;
import com.mhss.gomed.other.DBAdapter;
import com.mhss.gomed.other.ReminderAdapter;
import com.mhss.gomed.other.ReminderDTO;
import com.mhss.gomed.other.NotesTable;
import com.mhss.gomed.other.ReminderTable;
import com.mhss.gomed.other.ReminderTimeTable;

import java.util.ArrayList;
import java.util.List;


public class ReminderFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReminderAdapter mAdapter;
    private List<ReminderDTO> mReminder;
    private TextView no_record;

    public ReminderFragment() {
    }

    public Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_reminder, container,
                false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        mContext = rootView.getContext();
        no_record = (TextView) rootView.findViewById(R.id.no_record);
        mReminder = new ArrayList<>();
        mAdapter = new ReminderAdapter(rootView.getContext(), mReminder);

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

                String[] colors = {"Take Pill", "Delete"};
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Choose one");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBAdapter dba = new DBAdapter(mContext);
                        dba.open();
                        if (which == 0) {
                            Toast.makeText(mContext, "Pos "+position, Toast.LENGTH_SHORT).show();
                            Toast.makeText(mContext, "ID "+mReminder.get(position).getReminderId(), Toast.LENGTH_SHORT).show();
                            dba.updatePills(mReminder.get(position).getReminderName() + "",mReminder.get(position).getReminderId() + "",1);
                            Toast.makeText(mContext, "Take Pill Manually...", Toast.LENGTH_LONG).show();
                        } else if (which == 1) {
                            dba.deleteReminder(mReminder.get(position).getReminderId() + "");
                            dba.deleteReminderTime(mReminder.get(position).getReminderId() + "");
                            Toast.makeText(mContext, "Medicine Delete Succesfully...", Toast.LENGTH_LONG).show();
                        }
                        dba.close();
                        loadData();
                    }
                });
                builder.show();

            }
        }));

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(rootView.getContext(), ADReminderActivity.class);
                startActivity(intent);
            }
        });
        loadData();
        setHasOptionsMenu(true);

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
                        mReminder.add(mData);
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

    private BroadcastReceiver ReceivefromService = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mReminder.clear();
            loadData();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            mContext.unregisterReceiver(ReceivefromService);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("Receiver not registered")) {
                Log.i("TAG", "Tried to unregister the reciver when it's not registered");
            } else {
                throw e;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.medicine");
        mContext.registerReceiver(ReceivefromService, filter);
    }
}
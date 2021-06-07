package com.mhss.gomed.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mhss.gomed.AEDNotesActivity;
import com.mhss.gomed.R;
import com.mhss.gomed.other.DBAdapter;
import com.mhss.gomed.other.ReportAdapter;
import com.mhss.gomed.other.ReportDTO;
import com.mhss.gomed.other.NotesTable;
import com.mhss.gomed.other.ReportTable;

import java.util.ArrayList;
import java.util.List;


public class ReportFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReportAdapter mAdapter;
    private List<ReportDTO> mNotes;
    private TextView no_record;

    public ReportFragment() {
    }

    public Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_report, container,
                false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        mContext = rootView.getContext();
        no_record = (TextView) rootView.findViewById(R.id.no_record);
        mNotes = new ArrayList<>();
        mAdapter = new ReportAdapter(rootView.getContext(), mNotes);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(rootView.getContext(), AEDNotesActivity.class);
                startActivity(intent);
            }
        });
        loadData();
        setHasOptionsMenu(true);

        return rootView;
    }


    public void loadData() {
        DBAdapter dba = new DBAdapter(mContext);
        dba.open();
        Cursor mCursor = dba.getReport();
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {
                if (mCursor.moveToFirst()) {
                    do {
                        ReportDTO mData = new ReportDTO();
                        mData.setId(mCursor.getString(mCursor.getColumnIndex(ReportTable.KEY_ReportId)));
                        mData.setName(mCursor.getString(mCursor.getColumnIndex(ReportTable.KEY_Med_Name)));
                        mData.setCreated_date(mCursor.getString(mCursor.getColumnIndex(ReportTable.KEY_DateTime)));
                        mData.setStatus(mCursor.getString(mCursor.getColumnIndex(ReportTable.KEY_Status)));
                        mNotes.add(mData);
                    } while (mCursor.moveToNext());
                    if (!(mNotes.size() > 0)) {
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
            mNotes.clear();
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
        filter.addAction("android.intent.action.notes");
        mContext.registerReceiver(ReceivefromService, filter);
    }
}
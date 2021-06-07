package com.mhss.gomed.other;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mhss.gomed.R;

import java.util.ArrayList;
import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private List<ReminderDTO> courseHeaderList;
    private List<ReminderDTO> contactListFiltered;
    private ContactsAdapterListener listener = null;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, type, unit, total, remaining, startDate, endDate;
        public TextView timeF, timeS, timeT;
        public LinearLayout ll1, ll2, ll3;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            type = (TextView) view.findViewById(R.id.type);
            unit = (TextView) view.findViewById(R.id.unit);
            total = (TextView) view.findViewById(R.id.totalDose);
            remaining = (TextView) view.findViewById(R.id.RemainingDose);
            startDate = (TextView) view.findViewById(R.id.StartDate);
            endDate = (TextView) view.findViewById(R.id.EndDate);
            timeF = (TextView) view.findViewById(R.id.firstD);
            timeS = (TextView) view.findViewById(R.id.secondD);
            timeT = (TextView) view.findViewById(R.id.thirdD);
            ll1 = (LinearLayout) view.findViewById(R.id.lld1);
            ll2 = (LinearLayout) view.findViewById(R.id.lld2);
            ll3 = (LinearLayout) view.findViewById(R.id.lld3);
            if (listener != null) {
                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        //listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                        //Toast.makeText(mContext,"ok"+getAdapterPosition(),Toast.LENGTH_LONG).show();
                        return true;
                    }
                });
            }
        }
    }


    public HomeAdapter(Context mContext, List<ReminderDTO> courseHeaderList) {
        this.mContext = mContext;
        this.courseHeaderList = courseHeaderList;
        contactListFiltered = this.courseHeaderList;
    }

    public HomeAdapter(Context mContext, List<ReminderDTO> courseHeaderList, ContactsAdapterListener listener) {
        this.mContext = mContext;
        this.courseHeaderList = courseHeaderList;
        contactListFiltered = this.courseHeaderList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_home, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ReminderDTO course = courseHeaderList.get(position);
        holder.name.setText(course.getReminderName());
        holder.name.setTag(course.getReminderId());
        holder.type.setText("Type : " + course.getReminderType());
        holder.unit.setText("Unit : " + course.getReminderUnit());
        holder.total.setText("Total Dose : " + course.getTotalDose());
        holder.remaining.setText("Rem. Dose : " + course.getRemainingDose() + " ("+course.getPercent()+"%)");
        holder.startDate.setText("Start Date : " + course.getStartDate());
        holder.endDate.setText("End Date : " + course.getEndDate());
        if(course.getList().size()==1){
            holder.timeF.setText(course.getList().get(0).getReminderTime());
            holder.ll1.setVisibility(View.VISIBLE);
            holder.ll2.setVisibility(View.GONE);
            holder.ll3.setVisibility(View.GONE);
        }else if(course.getList().size()==2){
            holder.timeF.setText(course.getList().get(0).getReminderTime());
            holder.timeS.setText(course.getList().get(1).getReminderTime());
            holder.ll1.setVisibility(View.VISIBLE);
            holder.ll2.setVisibility(View.VISIBLE);
            holder.ll3.setVisibility(View.GONE);
        }else if(course.getList().size()==3){
            holder.timeF.setText(course.getList().get(0).getReminderTime());
            holder.timeS.setText(course.getList().get(1).getReminderTime());
            holder.timeT.setText(course.getList().get(2).getReminderTime());
            holder.ll1.setVisibility(View.VISIBLE);
            holder.ll2.setVisibility(View.VISIBLE);
            holder.ll3.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return courseHeaderList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = courseHeaderList;
                } else {
                    List<ReminderDTO> filteredList = new ArrayList<>();
                    for (ReminderDTO row : courseHeaderList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getReminderName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<ReminderDTO>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private HomeAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final HomeAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public interface ContactsAdapterListener {
        void onContactSelected(ReminderDTO contact);
    }
}

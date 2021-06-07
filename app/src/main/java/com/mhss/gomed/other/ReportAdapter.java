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


public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private List<ReportDTO> courseHeaderList;
    private List<ReportDTO> contactListFiltered;
    private ContactsAdapterListener listener = null;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id , name, status , dt;
        public LinearLayout ll1;

        public MyViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.number);
            name = (TextView) view.findViewById(R.id.name);
            dt = (TextView) view.findViewById(R.id.date);
            status = (TextView) view.findViewById(R.id.status);
            ll1 = (LinearLayout) view.findViewById(R.id.llMain);
            if (listener != null) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // send selected contact in callback
                            listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                    }
                });
            }
        }
    }


    public ReportAdapter(Context mContext, List<ReportDTO> courseHeaderList) {
        this.mContext = mContext;
        this.courseHeaderList = courseHeaderList;
        contactListFiltered = this.courseHeaderList;
    }

    public ReportAdapter(Context mContext, List<ReportDTO> courseHeaderList, ContactsAdapterListener listener) {
        this.mContext = mContext;
        this.courseHeaderList = courseHeaderList;
        contactListFiltered = this.courseHeaderList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_report, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ReportDTO course = courseHeaderList.get(position);
        holder.name.setText(course.getName());
        holder.dt.setText(course.getCreated_date());
        holder.name.setTag(course.getId());
        holder.id.setText(course.getId());
        holder.status.setText(course.getStatus());
        if(position!=0){
            holder.ll1.setVisibility(View.GONE);
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
                    List<ReportDTO> filteredList = new ArrayList<>();
                    for (ReportDTO row : courseHeaderList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
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
                contactListFiltered = (ArrayList<ReportDTO>) filterResults.values;
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
        private ReportAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ReportAdapter.ClickListener clickListener) {
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
        void onContactSelected(ReportDTO contact);
    }
}

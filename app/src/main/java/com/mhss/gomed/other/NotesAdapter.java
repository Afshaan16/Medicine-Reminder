package com.mhss.gomed.other;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mhss.gomed.R;

import java.util.ArrayList;
import java.util.List;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private List<NotesDTO> courseHeaderList;
    private List<NotesDTO> contactListFiltered;
    private ContactsAdapterListener listener = null;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, dt;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            dt = (TextView) view.findViewById(R.id.dt);
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


    public NotesAdapter(Context mContext, List<NotesDTO> courseHeaderList) {
        this.mContext = mContext;
        this.courseHeaderList = courseHeaderList;
        contactListFiltered = this.courseHeaderList;
    }

    public NotesAdapter(Context mContext, List<NotesDTO> courseHeaderList, ContactsAdapterListener listener) {
        this.mContext = mContext;
        this.courseHeaderList = courseHeaderList;
        contactListFiltered = this.courseHeaderList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_notes, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        NotesDTO course = courseHeaderList.get(position);
        holder.name.setText(course.getName());
        holder.dt.setText(course.getCreated_date());
        holder.name.setTag(course.getId());
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
                    List<NotesDTO> filteredList = new ArrayList<>();
                    for (NotesDTO row : courseHeaderList) {

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
                contactListFiltered = (ArrayList<NotesDTO>) filterResults.values;
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
        private NotesAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final NotesAdapter.ClickListener clickListener) {
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
        void onContactSelected(NotesDTO contact);
    }
}

package com.ashstudios.safana.ui.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ashstudios.safana.R;

import java.util.ArrayList;

class TaskCalendarAdapter extends RecyclerView.Adapter<TaskCalendarAdapter.MyViewHolder> {
    private final ArrayList<DayModel> daysOfMonth;
    private final Context context;
    public TaskCalendarAdapter(Context context, ArrayList<DayModel> daysOfMonth) {
        this.context = context;
        this.daysOfMonth = daysOfMonth;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.day_cell_model, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DayModel dayModel = daysOfMonth.get(position);
        holder.dayOfMonth.setText(dayModel.getDay());
    }

   static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView dayOfMonth;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dayOfMonth = itemView.findViewById(R.id.cellDate);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return daysOfMonth.size();
    }
}

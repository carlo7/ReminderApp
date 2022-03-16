package com.example.reminderapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.myviewholder> {
    ArrayList<Data> dataholder = new ArrayList<Data>(); //array list to hold the reminders

    public myAdapter(ArrayList<Data> dataholder) {
        this.dataholder = dataholder;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflates the xml file in recyclerview
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_reminder,
                parent, false);
        return new myviewholder(view);
    }
    //Binds the single reminder objects to recycler view
    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.mTitle.setText(dataholder.get(position).getTitle());
        holder.mDate.setText(dataholder.get(position).getDate());
        holder.mTime.setText(dataholder.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView mTitle, mDate, mTime;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            //holds the reference of the materials to show data in recyclerview
            mTitle = itemView.findViewById(R.id.txtTitle);
            mDate = itemView.findViewById(R.id.txtDate);
            mTime = itemView.findViewById(R.id.txtTime);
        }
    }
}

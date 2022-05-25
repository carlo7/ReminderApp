package com.example.reminderapp;

import android.graphics.Color;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.myViewHolder> {
    MainActivity activity;
    ArrayList<Data> dataHolder; //array list to hold the reminders
    boolean isEnable=false;
    boolean isSelectAll=false;
    ArrayList<Data> selectList= new ArrayList<>();
    MainViewModel mainViewModel;
    TextView itemCount;
    int count=0;



  //create constructors
    public myAdapter(MainActivity activity , ArrayList<Data> dataHolder) {
        this.dataHolder = dataHolder;
        this.activity = activity;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflates the xml file in recyclerview
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_reminder,
                parent, false);

        mainViewModel = new ViewModelProvider.AndroidViewModelFactory(activity.getApplication()).create(MainViewModel.class);

        return new myViewHolder(view);
    }
    //Binds the single reminder objects to recycler view
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {


        holder.mTitle.setText(dataHolder.get(position).getTitle());
        holder.mDate.setText(dataHolder.get(position).getDate());
        holder.mTime.setText(dataHolder.get(position).getTime());


        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(!isEnable)
                {

                    ActionMode.Callback callback= new ActionMode.Callback() {
                        @Override
                        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                            //Initialize Menu inflater  then inflate the CAB menu
                            MenuInflater menuInflater = mode.getMenuInflater();
                            menuInflater.inflate(R.menu.contextual_menu, menu);
                            return true;
                        }

                        @Override
                        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {


                            isEnable= true; //Instantiate isEnable to true

                            clickView(holder); //call clickView// method

                            mainViewModel.getText().observe((LifecycleOwner) activity,
                                    new Observer<Data>() {
                                        @Override
                                        public void onChanged(Data data) {
                                            mode.setTitle(String.format("Select reminders",data));
                                        }
                                    });

                            return true;
                        }

                        @Override
                        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.delete:
                                    for(Data data:selectList)
                                    {
                                        dataHolder.remove(data);
                                    }

                                  mode.finish();
                                    break;

                                case R.id.select_all:
                                    if (selectList.size()==dataHolder.size()){
                                        isSelectAll=false;
                                        selectList.clear();
                                    }
                                    else{
                                        isSelectAll=true;
                                        selectList.clear();
                                        selectList.addAll(dataHolder);
                                    }

                                    notifyDataSetChanged();
;                                   break;
                            }
                            return true;
                        }

                        @Override
                        public void onDestroyActionMode(ActionMode mode) {
                            isEnable = false;
                            selectList.clear();
                            notifyDataSetChanged();

                        }
                    };
                    ((AppCompatActivity)v.getContext()).startActionMode(callback);
                }
                else {
                    holder.checkBox.setVisibility(View.VISIBLE);

                }
                return true;
            }
        });

           holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {


                expandActivity();

            }
});

   if (isSelectAll){
       holder.checkBox.setVisibility(View.VISIBLE);
       holder.checkBox.setChecked(true);

   }
   else{
       holder.checkBox.setVisibility(View.VISIBLE);
       holder.checkBox.setChecked(false);
   }

    }



    private void expandActivity() {
        //TODO ; Add a dialog box view on item click

    }

    private void clickView(myViewHolder holder) {

        //Returns the Adapter position of the item represented by the ViewHolder and stores it in variable data
        Data data= dataHolder.get(holder.getAdapterPosition());

        if(holder.checkBox.getVisibility()==View.GONE){

            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(true);
            selectList.add(data);
            /**count++;
            *updateCount();*/


        }
        else{
            holder.checkBox.setVisibility(View.GONE);
            selectList.remove(data);
           /** count--;
            updateCount();*/

        }

    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        TextView mTitle, mDate, mTime;
        CardView cardView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            //holds the reference of the materials to show data in the cards
            mTitle = itemView.findViewById(R.id.txtTitle);
            mDate = itemView.findViewById(R.id.txtDate);
            mTime = itemView.findViewById(R.id.txtTime);
            checkBox = itemView.findViewById(R.id.check_box);
            cardView= (CardView) itemView;
        }
    }
    /**public void updateCount(){
        itemCount.setText(count +" Item(s) Selected");
    }*/
}

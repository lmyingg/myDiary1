package com.yizi.mydiary.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.yizi.mydiary.AddNewTask;
import com.yizi.mydiary.Model.ToDoModel;
import com.yizi.mydiary.R;
import com.yizi.mydiary.activity.PlanEditActivity;
import com.yizi.mydiary.util.DatabaseHandler;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<com.yizi.mydiary.adapter.ToDoAdapter.ViewHolder> {

    private List<ToDoModel> todolist;
    private PlanEditActivity activity;
    private DatabaseHandler db;
    public ToDoAdapter(DatabaseHandler db, PlanEditActivity activity){
        this.db= this.db;
        this.activity=activity;
    }



    public com.yizi.mydiary.adapter.ToDoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout,parent,false);
        return new com.yizi.mydiary.adapter.ToDoAdapter.ViewHolder(itemView) ;
    }
    public void onBindViewHolder(ViewHolder holder, int position){
        db.openDatabase();
        ToDoModel item= todolist.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    db.updateStatus(item.getId(), 1);
                }
                else {
                    db.updateStatus(item.getId(),0);
                }
            }
        });

    }
    public int getItemCount(){
        return todolist.size();
    }
    private boolean toBoolean(int n){
        return n!=0;
    }
    public void setTasks(List<ToDoModel>todolist){
        this.todolist=todolist;
        notifyDataSetChanged();
    }
    public Context getContext(){
        return activity;
    }
    public void deleteItem(int position){
        ToDoModel item=todolist.get(position);
        db.deleteTask(item.getId());
        todolist.remove(position);
        notifyItemRemoved(position);
    }
    public void editItem(int position){
        ToDoModel item=todolist.get(position);
        Bundle bundle=new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask fragment=new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(),AddNewTask.TAG);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;
        ViewHolder(View view){
            super(view);
            task =view.findViewById(R.id.todoCheckBox);
        }
    }
}

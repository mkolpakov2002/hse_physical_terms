package com.example.physicalterms.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.physicalterms.R;
import com.example.physicalterms.api.FormulaRow;
import com.example.physicalterms.api.TaskRow;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{
    private List<TaskRow> mData;
    private LayoutInflater mInflater;
    private FormulaAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public TaskAdapter(Context context, List<TaskRow> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.task_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TaskRow current = mData.get(position);
        holder.taskItemTextView.setText(current.getText());
        holder.taskItemAnswersView.setText(current.getAnswers().toString());
        holder.taskItemRightAnswerView.setText(current.getRightAnswer());
        holder.taskItemTaskView.setText(current.getTaskType().toString());
        holder.taskItemView.setText(current.getSection());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView taskItemTextView;
        TextView taskItemAnswersView;
        TextView taskItemRightAnswerView;
        TextView taskItemTaskView;
        TextView taskItemView;

        ViewHolder(View itemView) {
            super(itemView);
            taskItemTextView = itemView.findViewById(R.id.taskItemText);
            taskItemAnswersView = itemView.findViewById(R.id.taskItemAnswers);
            taskItemRightAnswerView = itemView.findViewById(R.id.taskItemRightAnswer);
            taskItemTaskView = itemView.findViewById(R.id.taskItemTaskType);
            taskItemView = itemView.findViewById(R.id.taskItemSection);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    TaskRow getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(FormulaAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setData(List<TaskRow> newData){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new TaskDiffUtilCallback(mData, newData), true);
        this.mData = newData;
        diffResult.dispatchUpdatesTo(this);
    }
}

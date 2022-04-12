package com.example.physicalterms.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.physicalterms.R;
import com.example.physicalterms.api.TaskRow;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> implements Filterable {
    private List<TaskRow> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private List<TaskRow> filterableList;
    private TaskAdapter adapter = this;

    // data is passed into the constructor
    public TaskAdapter(Context context, List<TaskRow> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = filterableList = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_task, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TaskRow current = filterableList.get(position);
        holder.taskItemTextView.setText(current.getText());
        holder.taskItemAnswersView.setText(current.getAnswers().toString());
        holder.taskItemRightAnswerView.setText(current.getRightAnswer());
        holder.taskItemTaskView.setText(current.getTaskType().toString());
        holder.taskItemView.setText(current.getSection());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return filterableList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if(charSequence == null || charSequence.length() == 0){
                    filterResults.count = mData.size();
                    filterResults.values = mData;
                    filterableList = mData;

                } else {
                    String searchChr = charSequence.toString().toLowerCase();

                    List<TaskRow> resultData = new ArrayList<>();
                    for(TaskRow userModel: mData){
                        if(userModel.getText().toLowerCase().contains(searchChr.toLowerCase())){
                            resultData.add(userModel);
                        }
                    }
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;

                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new TaskDiffUtilCallback(filterableList, (List<TaskRow>) filterResults.values), true);
                filterableList = (List<TaskRow>) filterResults.values;
                diffResult.dispatchUpdatesTo(adapter);

            }
        };
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) mClickListener.onItemClick(view);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mClickListener != null) mClickListener.onItemClick(view);
                    return true;
                }
            });
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view);
        }
    }

    // convenience method for getting data at click position
    TaskRow getItem(int id) {
        return filterableList.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(TaskAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view);
    }

    public void setData(List<TaskRow> newData){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new TaskDiffUtilCallback(filterableList, newData), true);
        this.mData = filterableList = newData;
        diffResult.dispatchUpdatesTo(this);
    }
}

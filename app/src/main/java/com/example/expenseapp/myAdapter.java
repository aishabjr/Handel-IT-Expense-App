package com.example.expenseapp;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;


/*
 * this adapter is very similar to the adapters used for listview, except a ViewHolder is required
 * see http://developer.android.com/training/improving-layouts/smooth-scrolling.html
 * except instead having to implement a ViewHolder, it is implemented within
 * the adapter.
 *
 * This code has a ViewModel/LiveData and the observer is set in the adapter, so it will update on it's
 * own without the need to notify that the database has been changed.
 */

public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder>{

    private List<Expense> myList;
    private int rowLayout;
    //private  AppCompatActivity activity;  only need it once.
    private final String TAG= "myAdapter";
    private ExpenseListViewModel mViewModel;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        public TextView mCategory;
        public TextView mDate;
        public TextView mAmount;
        public TextView mNote;
        public CardView mCardView;

        private final String TAG= "ViewHolder";
        public ViewHolder(View view) {
            super(view);
            mName = view.findViewById(R.id.name);
            mCategory = view.findViewById(R.id.category);
            mDate = view.findViewById(R.id.date);
            mAmount = view.findViewById(R.id.amount);
            mNote = view.findViewById(R.id.note);
            mCardView = view.findViewById(R.id.cardview);
        }
    }

    //constructor
    public myAdapter(ExpenseListViewModel expenseListViewModel, int rowLayout, AppCompatActivity activity) {
        mViewModel = expenseListViewModel;
        this.rowLayout = rowLayout;
        mViewModel.getExpenses().observe(activity, new Observer<List<Expense>>() {
            @Override
            public void onChanged(@Nullable List<Expense> expenses) {
                Log.d("Adatper", "Data has been added/changed, displaying");
                myList = expenses;
                notifyDataSetChanged();
            }
        });
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Expense entry = myList.get(i);

        viewHolder.mName.setText(entry.getName());
        viewHolder.mCardView.setTag(entry);
        viewHolder.mCategory.setText(String.valueOf(entry.getCategory()));
        viewHolder.mDate.setText(String.valueOf(entry.getDate()));
        viewHolder.mAmount.setText(String.valueOf(entry.getAmount()));
        viewHolder.mNote.setText(String.valueOf(entry.getNote()));
        viewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
                       //use id to get and show dialog then pre-populate it with prev entered data
            @Override
            public void onClick(View view) {
                Expense entry;
                entry = (Expense) view.getTag();
                //set model view
                mViewModel.setExpense(entry);
            }
        });
    }

    //gets the id to be able to delete entries
    public int removeItem(int i){
        Expense expense = myList.get(i);
        int id = expense.getId();
        return id;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myList == null ? 0 : myList.size();
    }
}

package com.example.expenseapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.View;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements
        MultiInputDialogFragment.OnDialogFragmentInteractionListener,
        EditMultiInputDialogFragment.OnEditDialogFragmentInteractionListener{

    FragmentManager fragmentManager;
    RecyclerView mRecyclerView;
    myAdapter mAdapter;
    String TAG = "MainActivity";
    AppDatabase ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();

        //add the multi-input dialog fragment.
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MultiInputDialogFragment newDialog = new MultiInputDialogFragment();
                newDialog.show(fragmentManager, "multiInputDialogExpenseEntry");
            }
        });

        //get the database and a view model.
        ad = AppDatabase.getInstance(this);
        ExpenseListViewModel expenseListViewModel = new ExpenseListViewModel(ad);

        mRecyclerView = findViewById(R.id.listtrans);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new myAdapter(expenseListViewModel, R.layout.expense, this);
        //add the adapter to the recyclerview
        mRecyclerView.setAdapter(mAdapter);

        //set the observer for when the data changes.  It will then update.
        //Note, when the phone rotates, this will be called, because the data is new to the activity.
        expenseListViewModel.getData().observe(this, new Observer<Expense>() {
            @Override
            public void onChanged(final @Nullable Expense data) {
                //logthis("Data changed, updating!");
                if(data.name != null){
                    EditMultiInputDialogFragment newDialog = EditMultiInputDialogFragment.newInstance(data.name, data.category, data.date, data.amount.toString(), data.note, String.valueOf(data.id));
                    newDialog.show(fragmentManager, "EditMultiInputDialogExpenseEntry");
                }
            }
        });

        //Listener for left or right swap to then delete entry
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                if ((direction == ItemTouchHelper.RIGHT) || (direction == ItemTouchHelper.LEFT)) {

                    Thread myThread = new Thread() {
                        public void run() {
                            final int position = viewHolder.getAdapterPosition();
                            int id = mAdapter.removeItem(position);
                            ad.ExpenseDao().deleteById(id);
                        }
                    };
                    myThread.start();
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    //for the MultiInputDialogFragment - used to insert/create new entries
    @Override
    public void onMultiInputInteraction(String[] items) {
        final Expense myExpense = new Expense();

        myExpense.setName(items[0]);
        myExpense.setCategory(items[1]);
        myExpense.setDate(items[2]);
        myExpense.setAmount(Float.valueOf(items[3]));
        myExpense.setNote(items[4]);

        Thread myThread = new Thread() {
            public void run() {
                Log.d(TAG, "Inserting data");
                ad.ExpenseDao().insert(myExpense);
            }
        };
        myThread.start();
    }

    //for the EditMultiInputDialogFragment - used to update entries
    @Override
    public void onEditMultiInputInteraction(String[] items) {
        final Expense myExpense = new Expense();

        myExpense.setName(items[0]);
        myExpense.setCategory(items[1]);
        myExpense.setDate(items[2]);
        myExpense.setAmount(Float.valueOf(items[3]));
        myExpense.setNote(items[4]);
        myExpense.setId(Integer.valueOf(items[5]));

        Thread myThread = new Thread() {
            public void run() {

                Log.d(TAG, "Updating data");
                ad.ExpenseDao().update(myExpense);
            }
        };
        myThread.start();
    }
}

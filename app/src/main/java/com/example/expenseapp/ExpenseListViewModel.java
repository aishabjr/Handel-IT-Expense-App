package com.example.expenseapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * class to hold the data returned from the room database.  it has livedata, so observer is here and
 * pass back, I think.
 */

public class ExpenseListViewModel extends ViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<Expense>> mObservableExpenses;

    private MutableLiveData<Expense> data;

    public ExpenseListViewModel(AppDatabase ad) {

        mObservableExpenses = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableExpenses.setValue(null);

        LiveData<List<Expense>> ExpenseEntries = ad.ExpenseDao().selectAll();

        mObservableExpenses.addSource(ExpenseEntries, new androidx.lifecycle.Observer<List<Expense>>() {
                    @Override
                    public void onChanged(@Nullable List<Expense> expenseEntities) {
                        mObservableExpenses.setValue(expenseEntities);
                    }
                }
        );
    }

    public LiveData<Expense> getData() {
        if (data == null) {
            data = new MutableLiveData<Expense>();
            data.setValue(new Expense());
        }
        return data;
    }

    void setExpense(Expense item) {
        if (data == null) data = new MutableLiveData<Expense>();
        data.setValue(item);
    }

    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    public LiveData<List<Expense>> getExpenses() {
        return mObservableExpenses;
    }
}

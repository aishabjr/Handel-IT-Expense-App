package com.example.expenseapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * This is the Dao for the score "db" It provides the accessor methods to the database.
 * In this example, the return value is LiveData, so an observer can be added else to see when
 * the data changes.
 *
 */
@Dao
public interface ExpenseDao {

    /**
     * Select all entries.
     *
     * @return A {@link LiveData <List<Entry>>>} of all the name and other fields in the table.
     */
    @Query("SELECT * FROM " + Expense.TABLE_NAME)
    LiveData<List<Expense>> selectAll();


    @Query("SELECT * FROM "+Expense.TABLE_NAME +  " ORDER BY " +Expense.COLUMN_NAME+" ASC")
    LiveData<List<Expense>> selectByName();

    @Query("SELECT * FROM "+Expense.TABLE_NAME +  " ORDER BY " +Expense.COLUMN_CATEGORY+" ASC")
    LiveData<List<Expense>> selectByCategory();

    @Query("SELECT * FROM "+Expense.TABLE_NAME +  " ORDER BY " +Expense.COLUMN_DATE+" ASC")
    LiveData<List<Expense>> selectByDate();

    @Query("SELECT * FROM "+Expense.TABLE_NAME +  " ORDER BY " +Expense.COLUMN_AMOUNT+" ASC")
    LiveData<List<Expense>> selectByAmount();

    @Query("SELECT * FROM "+Expense.TABLE_NAME +  " ORDER BY " +Expense.COLUMN_NOTE+" ASC")
    LiveData<List<Expense>> selectByNote();

    /**
     * Select an entry by the ID.
     *
     * @param id The row ID.
     * @return A {@link LiveData <List<Entry>>>} of the selected entry.
     */
    @Query("SELECT * FROM " + Expense.TABLE_NAME + " WHERE " + Expense.COLUMN_ID + " = :id")
    LiveData<List<Expense>> selectById(int id);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Expense> expenses);


    @Query("SELECT COUNT(*) FROM " + Expense.TABLE_NAME)
    int count();

    /**
     * Inserts an entry into the table.
     *
     * @param expenseData A new ExpenseEntry.
     * @return The row ID of the newly inserted data.
     */
    @Insert
    long insert(Expense expenseData);

    /**
     * Inserts multiple expenses into the database
     *
     * @param expenseDatas An array of new expense data.
     * @return The row IDs of the newly inserted expenses.
     */
    @Insert
    long[] insertAll(Expense[] expenseDatas);


    /**
     * Delete an expense by the ID.
     *
     * @param id The row ID.
     * @return A number of expenses deleted. This should always be {@code 1}.
     */
    @Query("DELETE FROM " + Expense.TABLE_NAME + " WHERE " + Expense.COLUMN_ID + " = :id")
    int deleteById(int id);

    /**
     * Delete all expenses.
     */
    @Query("DELETE FROM " + Expense.TABLE_NAME)
    void deleteAllExpenses();


    /**
     * Update the expense. The expense is identified by the row ID.
     *
     * @param expenseData The expense to update.
     * @return A number of expense updated. This should always be {@code 1}.
     */
    @Update
    int update(Expense expenseData);
}

package com.example.expenseapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import android.content.ContentValues;
import android.provider.BaseColumns;

/**
 *
 * Expense Plain Old Java Object  This needs to provide getters and setters for everything plus the
 * constructors for the object.   The table in the database is specified as expense
 */


@Entity(tableName = Expense.TABLE_NAME)
public class Expense {

    /** The name of the Expense table. */
    public static final String TABLE_NAME = "expense";

    /** The name of the ID column. */
    public static final String COLUMN_ID = BaseColumns._ID;

    /** The name of the name column. */
    public static final String COLUMN_NAME = "name";

    /** The name of the category column. */
    public static final String COLUMN_CATEGORY = "category";

    /** The name of the date column. */
    public static final String COLUMN_DATE = "date";

    /** The name of the amount column. */
    public static final String COLUMN_AMOUNT = "amount";

    /** The name of the note column. */
    public static final String COLUMN_NOTE = "note";

    /** The unique ID each score data pair. */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    public int id;

    /** The name of the person. */
    @ColumnInfo(name = COLUMN_NAME)
    public String name;

    /** The expense's category. */
    @ColumnInfo(name = COLUMN_CATEGORY)
    public String category;

    /** The expense's Dtae. */
    @ColumnInfo(name = COLUMN_DATE)
    public String date;

    /** The expense's Amount. */
    @ColumnInfo(name = COLUMN_AMOUNT)
    public Float amount;

    /** The expense's category. */
    @ColumnInfo(name = COLUMN_NOTE)
    public String note;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // blank constructor, the ignore is for database, so it doesn't choose this one.
    @Ignore
    public Expense() {
    }

    // constructor for the 5 data points.
    public Expense( String name, String category, String date, Float amount, String note) {
        this.name = name;
        this.category = category;
        this.date = date;
        this.amount = amount;
        this.note = note;
    }

    //static method to get the data from ContentValues (for content provider data)
    public static Expense fromContentValues(ContentValues values) {
        final Expense expenseData = new Expense();
        if (values.containsKey(COLUMN_ID)) {
            expenseData.id = values.getAsInteger(COLUMN_ID);
        }
        if (values.containsKey(COLUMN_NAME)) {
            expenseData.name = values.getAsString(COLUMN_NAME);
        }
        if (values.containsKey(COLUMN_CATEGORY)) {
            expenseData.category = values.getAsString(COLUMN_CATEGORY);
        }
        if (values.containsKey(COLUMN_DATE)) {
            expenseData.date = values.getAsString(COLUMN_DATE);
        }
        if (values.containsKey(COLUMN_AMOUNT)) {
            expenseData.amount = values.getAsFloat(COLUMN_AMOUNT);
        }
        if (values.containsKey(COLUMN_CATEGORY)) {
            expenseData.note = values.getAsString(COLUMN_NOTE);
        }
        return expenseData;
    }

}

package com.example.expenseapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.DialogFragment;

/**
 * This is a dialogfragment that has five text boxes.
 * <p>
 * It will return a string array via the listener that that needs to be implemented by the activity
 * This is used for editing entries
 */
public class EditMultiInputDialogFragment extends DialogFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";


    private String name;  // name
    private String category;  //category
    private String date;  //date
    private String amount;  //amount
    private String note;  //note
    private String id; //id

    private OnEditDialogFragmentInteractionListener mListener;

    public EditMultiInputDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.  //name
     * @param param2 Parameter 2.  //category
     * @param param3 Parameter 3.  //date
     * @param param4 Parameter 4.  //amount
     * @param param5 Parameter 5.  //note
     * @param param6 Parameter 6.  //id
     * @return A new instance of fragment EditMultiInputDialogFragment.
     */

    public static EditMultiInputDialogFragment newInstance(String param1, String param2, String param3, String param4, String param5, String param6) {
        EditMultiInputDialogFragment fragment = new EditMultiInputDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);  //name
        args.putString(ARG_PARAM2, param2);  //category
        args.putString(ARG_PARAM3, param3);  //date
        args.putString(ARG_PARAM4, param4);  //amount
        args.putString(ARG_PARAM5, param5);  //note
        args.putString(ARG_PARAM6, param6);  //id

        fragment.setArguments(args);
        return fragment;
    }

    //get arguments from the saved instance
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1, null);
            category = getArguments().getString(ARG_PARAM2, null);
            date = getArguments().getString(ARG_PARAM3, null);
            amount = getArguments().getString(ARG_PARAM4, null);
            note = getArguments().getString(ARG_PARAM5, null);
            id = getArguments().getString(ARG_PARAM6, null);
        }
    }

    EditText et_name, et_category, et_date, et_amount, et_note;

    //Set all the corresponding data from the user input to the EditTexts
    //Check if any but the note field is empty and then ask user to enter in missing field
    //Dialog box closes when this happens
    @Override
    public Dialog onCreateDialog(Bundle SavedIntanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View myView = inflater.inflate(R.layout.fragment_multi_input_dialog, null);
        et_name = myView.findViewById(R.id.et_name);
        if (name != null) et_name.setText(name);
        et_category =  myView.findViewById(R.id.et_category);
        if (category != null) et_category.setText(category);
        et_date = myView.findViewById(R.id.et_date);
        if (date != null) et_date.setText(date);
        et_amount = myView.findViewById(R.id.et_amount);
        if (amount != null) et_amount.setText(amount);
        et_note = myView.findViewById(R.id.et_note);
        if (note != null) et_note.setText(note);
        final AlertDialog.Builder builder = new  AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.ThemeOverlay_AppCompat_Dialog));
        builder.setView(myView).setTitle("Expense Entry");
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int idx) {
                String[] returnlist =
                        new String[]{
                                et_name.getText().toString(),
                                et_category.getText().toString(),
                                et_date.getText().toString(),
                                et_amount.getText().toString(),
                                et_note.getText().toString(),
                                String.valueOf(id)
                        };

                if(returnlist[0].equals("")){
                    Toast.makeText(getContext(), "please enter name of expense", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(returnlist[1].equals("")){
                    Toast.makeText(getContext(), "please enter category of expense", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(returnlist[2].equals("")){
                    Toast.makeText(getContext(), "please enter date of expense", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(returnlist[3].equals("")){
                    Toast.makeText(getContext(), "please enter amount of expense", Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    //send the list back to the MainActivity to process.
                    mListener.onEditMultiInputInteraction(returnlist);
                    dismiss();
                }

            }})
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEditDialogFragmentInteractionListener) {
            mListener = (OnEditDialogFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
        **/
    interface OnEditDialogFragmentInteractionListener {
        void onEditMultiInputInteraction(String[] items);
    }
}
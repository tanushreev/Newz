package com.example.tanushree.newz;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AlertDialogFragment extends DialogFragment
{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();

        // Builder is a nested class inside AlertDialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Oops! Sorry!")
                .setMessage("There was an error.")
                .setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();
        return dialog;
    }
}

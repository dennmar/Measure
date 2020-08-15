package com.example.measure.features.habit_tracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.measure.R;
import com.example.measure.models.data.Habit;
import com.example.measure.utils.DBOperationException;

import java.util.HashSet;

/**
 * A fragment that creates a dialog to allow the user to fill out information
 * for a new habit.
 */
public class AddHabitDialogFragment extends DialogFragment {
    /**
     * Create a new habit dialog to be displayed by the fragment.
     *
     * @param savedInstanceState previously saved state of the fragment
     * @return a dialog to fill out information for a new habit
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_habit, null);
        builder.setView(dialogView);

        initSubmitBtn(dialogView);
        initCancelBtn(dialogView);

        return builder.create();
    }

    /**
     * Initialize the submit button to add the habit.
     *
     * @param view view for the user interface
     */
    private void initSubmitBtn(View view) {
        Button submitBtn = view.findViewById(R.id.btn_submit_add_habit);
        TextView nameText = view.findViewById(R.id.edittext_new_habit_name);

        submitBtn.setOnClickListener(v -> {
            String habitName = nameText.getText().toString();
            Habit newHabit = new Habit(habitName, new HashSet<>());

            try {
                ((HabitTrackerFragment) requireParentFragment()).addHabit(newHabit);
            }
            catch (DBOperationException dboe) {
                // TODO: handle exception
            }
            finally {
                dismiss();
            }
        });
    }

    /**
     * Initialize the cancel button to close the dialog.
     *
     * @param view view for the user interface
     */
    private void initCancelBtn(View view) {
        Button cancelBtn = view.findViewById(R.id.btn_cancel_add_habit);
        cancelBtn.setOnClickListener(v -> {
            dismiss();
        });
    }
}

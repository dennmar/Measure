package com.example.measure.features.agenda.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.measure.utils.StringConverter;

import org.joda.time.LocalDate;

/**
 * A fragment that creates a dialog to allow the user to pick the task date.
 */
public class TaskDatePickerDialogFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private TextView dateTextView;

    /**
     * Initialize member variable.
     *
     * @param dateTextView text view that will hold the date
     */
    public TaskDatePickerDialogFragment(TextView dateTextView) {
        this.dateTextView = dateTextView;
    }

    /**
     * Create a task date picker dialog to be displayed by the fragment.
     *
     * @param savedInstanceState previously saved state of the fragment
     * @return a dialog to pick the task date
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LocalDate today = LocalDate.now();
        int day = today.getDayOfMonth();
        int month = today.getMonthOfYear();
        int year = today.getYear();

        // Month is zero-indexed.
        return new DatePickerDialog(getActivity(), this, year, month - 1, day);
    }

    /**
     * Update the text view representing the date whenever the date is set.
     *
     * @param view  datepicker that set the date
     * @param year  year of set date
     * @param month month of set date (0-indexed)
     * @param day   day of set date
     */
    public void onDateSet(DatePicker view, int year, int month, int day) {
        LocalDate selectedDate = new LocalDate(year, month + 1, day);
        String dateStr = StringConverter.localDateToString(selectedDate);
        dateTextView.setText(dateStr);
    }
}

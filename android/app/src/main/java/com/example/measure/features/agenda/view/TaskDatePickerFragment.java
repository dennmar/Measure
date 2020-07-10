package com.example.measure.features.agenda.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.measure.utils.StringConverter;

import org.joda.time.LocalDate;
import org.w3c.dom.Text;

import java.util.Calendar;

/**
 * A fragment that creates a dialog to allow the user to pick task date
 * represented in a text view.
 */
public class TaskDatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private TextView dateTextView;

    /**
     * Initialize member variable.
     *
     * @param dateTextView text view that will hold the date
     */
    public TaskDatePickerFragment(TextView dateTextView) {
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
        final Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    /**
     * Update the text view representing the date whenever the date is set.
     *
     * @param view  datepicker that set the date
     * @param year  year of set date
     * @param month month of set date
     * @param day   day of set date
     */
    public void onDateSet(DatePicker view, int year, int month, int day) {
        LocalDate selectedDate = new LocalDate(year, month, day);
        String dateStr = StringConverter.localDateToString(selectedDate);
        dateTextView.setText(dateStr);
    }
}

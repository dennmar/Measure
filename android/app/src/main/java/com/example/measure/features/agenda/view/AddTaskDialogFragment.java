package com.example.measure.features.agenda.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.measure.R;
import com.example.measure.models.data.Task;
import com.example.measure.utils.StringConverter;

import org.joda.time.LocalDate;

/**
 * A fragment that creates a dialog to allow the user to fill out information
 * for a new task.
 */
public class AddTaskDialogFragment extends DialogFragment {
    /**
     * Create a new task dialog to be displayed by the fragment.
     *
     * @param savedInstanceState previously saved state of the fragment
     * @return a dialog to fill out information for a new task
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_task, null);
        builder.setView(dialogView);

        initDatePicker(dialogView);
        initSubmitBtn(dialogView);
        initCancelBtn(dialogView);

        return builder.create();
    }

    /**
     * Initialize the date picker to set the date of the new task.
     *
     * @param view view for the user interface
     */
    private void initDatePicker(View view) {
        int dateBtnId = R.id.btn_new_task_datepicker;
        int dateTextViewId = R.id.textview_new_task_date;
        ImageButton openDatePicker = view.findViewById(dateBtnId);
        TextView dateTextView = view.findViewById(dateTextViewId);

        openDatePicker.setOnClickListener(v -> {
            DialogFragment taskDatePickerFrag =
                    new TaskDatePickerDialogFragment(dateTextView);
            taskDatePickerFrag.show(requireActivity()
                    .getSupportFragmentManager(), "TaskDatePickerFragment");
        });
    }

    /**
     * Initialize the submit button to add the task.
     *
     * @param view view for the user interface
     */
    private void initSubmitBtn(View view) {
        Button submitBtn = view.findViewById(R.id.btn_submit_add_task);
        TextView nameText = view.findViewById(R.id.edittext_new_task_name);
        TextView dateText = view.findViewById(R.id.textview_new_task_date);

        submitBtn.setOnClickListener(v -> {
            String taskName = nameText.getText().toString();
            String taskDateStr = dateText.getText().toString();
            LocalDate taskDueDate = StringConverter.toLocalDate(taskDateStr);

            Task newTask = new Task();
            newTask.setName(taskName);
            newTask.setLocalDueDate(taskDueDate);

            ((AgendaFragment) requireParentFragment()).addTask(newTask);
            dismiss();
        });
    }

    /**
     * Initialize the cancel button to close the dialog.
     *
     * @param view view for the user interface
     */
    private void initCancelBtn(View view) {
        Button cancelBtn = view.findViewById(R.id.btn_cancel_add_task);
        cancelBtn.setOnClickListener(v -> {
            dismiss();
        });
    }
}

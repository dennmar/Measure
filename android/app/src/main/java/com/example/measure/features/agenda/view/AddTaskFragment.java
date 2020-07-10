package com.example.measure.features.agenda.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.measure.R;
/**
 * A fragment that creates a dialog showing the user a form to add a task.
 */
public class AddTaskFragment extends Fragment {
    /**
     * Restore the fragment from the previously saved state.
     *
     * @param savedInstanceState previously saved state of the fragment
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Instantiate the user interface view.
     *
     * @param inflater           layout inflater to inflate views
     * @param container          parent view to attach to
     * @param savedInstanceState previously saved state of the fragment
     * @return view for the user interface
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_task, container,
                false);
        initDatePicker(rootView);
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
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
                    new TaskDatePickerFragment(dateTextView);
            taskDatePickerFrag.show(requireActivity()
                    .getSupportFragmentManager(), "TaskDatePickerFragment");
        });
    }
}

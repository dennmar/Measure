package com.example.measure.features.habit_tracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.measure.R;
import com.example.measure.di.MeasureApplication;
import com.example.measure.models.data.Habit;

import javax.inject.Inject;

/**
 * A fragment that handles the UI for the habit tracker.
 */
public class HabitTrackerFragment extends Fragment {
    @Inject
    protected HabitTrackerViewModel.Factory htvmFactory;
    private HabitTrackerViewModel habitTrackerViewModel;

    /**
     * Initialize the habit tracker view model.
     *
     * @param savedInstanceState previously saved state of the fragment
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        ((MeasureApplication) getActivity().getApplicationContext())
                .appComponent.inject(this);
        super.onCreate(savedInstanceState);
        habitTrackerViewModel = htvmFactory.create(savedInstanceState);
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
        View rootView = inflater.inflate(R.layout.fragment_habit_tracker,
                container, false);

        initDateDisplay(rootView);
        initHabitDisplay(rootView);
        initAddHabitBtn(rootView);

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * Initialize the display to show the current date and the previous days.
     *
     * @param view view for the user interface
     */
    private void initDateDisplay(View view) {}

    /**
     * Initialize the display to show all the habits and their recent
     * completions.
     *
     * @param view view for the user interface
     */
    private void initHabitDisplay(View view) {}

    /**
     * Initialize the button for adding habits.
     *
     * @param view view for the user interface
     */
    private void initAddHabitBtn(View view) {
        Button addHabitBtn = view.findViewById(R.id.btn_add_habit);
        addHabitBtn.setOnClickListener(v -> {
            AddHabitDialogFragment addHabitDialog = new AddHabitDialogFragment();
            addHabitDialog.show(getChildFragmentManager(),
                    "AddHabitDialogFragment");
        });
    }

    /**
     * Add the habit to the habit tracker.
     *
     * @param habit habit to be added
     */
    public void addHabit(Habit habit) {}
}

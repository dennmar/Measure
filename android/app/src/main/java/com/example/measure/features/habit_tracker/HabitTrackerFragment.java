package com.example.measure.features.habit_tracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.measure.R;

/**
 * A fragment that handles the UI for the habit tracker.
 */
public class HabitTrackerFragment extends Fragment {
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
        View rootView = inflater.inflate(R.layout.fragment_habit_tracker,
                container, false);
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}

package com.example.measure;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.measure.di.MeasureApplication;

/**
 * A fragment that handles the UI for the user's agenda.
 */
public class AgendaFragment extends Fragment {
    private AgendaViewModel agendaViewModel;

    /**
     * Initialize the agenda view model.
     *
     * @param savedInstanceState previously saved state of the fragment
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        agendaViewModel = ((MeasureApplication) getActivity()
                .getApplicationContext()).appComponent.avmFactory()
                .create(savedInstanceState);
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
        View rootView = inflater.inflate(R.layout.fragment_agenda, container,
                false);
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}

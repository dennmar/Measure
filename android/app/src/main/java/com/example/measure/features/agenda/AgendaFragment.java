package com.example.measure.features.agenda;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.measure.R;
import com.example.measure.di.MeasureApplication;
import com.example.measure.models.data.AgendaItem;

import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment that handles the UI for the user's agenda.
 */
public class AgendaFragment extends Fragment {
    private AgendaViewModel agendaViewModel;
    private RecyclerView agendaRecycler;
    private AgendaItemAdapter agendaAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<AgendaItem> agendaItems;

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
        agendaItems = new ArrayList<>();
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
        initAgendaDisplay(rootView);
        initAddTaskBtn(rootView);
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        agendaViewModel.onDestroy();
    }

    /**
     * Initialize the display for the agenda.
     *
     * @param view view for the user interface
     */
    private void initAgendaDisplay(View view) {
        agendaRecycler = view.findViewById(R.id.agenda_recycler);
        layoutManager = new LinearLayoutManager(getActivity());
        agendaRecycler.setLayoutManager(layoutManager);
        agendaAdapter = new AgendaItemAdapter(agendaItems);
        agendaRecycler.setAdapter(agendaAdapter);
    }

    /**
     * Initialize the button for adding tasks.
     *
     * @param view view for the user interface
     */
    private void initAddTaskBtn(View view) {
        Button addTaskBtn = view.findViewById(R.id.add_task_button);
        addTaskBtn.setOnClickListener(v -> {
            agendaItems.add(new AgendaItem("Test agenda item",
                    new Duration(3600000 + 1000 * 17)));
            agendaAdapter.setAgendaItems(agendaItems);
            agendaAdapter.notifyDataSetChanged();
        });
    }
}

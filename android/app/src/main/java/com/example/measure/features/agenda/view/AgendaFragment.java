package com.example.measure.features.agenda.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.measure.R;
import com.example.measure.di.MeasureApplication;
import com.example.measure.features.FragActivity;
import com.example.measure.features.agenda.viewmodel.AgendaViewModel;
import com.example.measure.models.data.Task;
import com.example.measure.utils.StringConverter;

import org.joda.time.Duration;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * A fragment that handles the UI for the user's agenda.
 */
public class AgendaFragment extends Fragment {
    @Inject
    public AgendaViewModel.Factory avmFactory;
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
        ((MeasureApplication) getActivity().getApplicationContext())
                .appComponent.inject(this);
        super.onCreate(savedInstanceState);
        agendaViewModel = avmFactory.create(savedInstanceState);
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

        LocalDate agendaStartDate = LocalDate.now().minusDays(3);
        LocalDate agendaEndDate = LocalDate.now().plusDays(4);
        setAgenda(agendaStartDate, agendaEndDate);
    }

    /**
     * Set the display to show the agenda between the start date and end
     * date.
     *
     * @param agendaStartDate starting date of the agenda (inclusive)
     * @param agendaEndDate   ending date of the agenda (exclusive)
     */
    private void setAgenda(final LocalDate agendaStartDate,
                        final LocalDate agendaEndDate) {
        agendaViewModel.getSortedTasks(agendaStartDate, agendaEndDate)
                .observe(getViewLifecycleOwner(), sortedTasks -> {
                    agendaItems = tasksToAgendaItems(sortedTasks,
                            agendaStartDate, agendaEndDate);
                    agendaAdapter.setAgendaItems(agendaItems);
                    agendaAdapter.notifyDataSetChanged();
                });
    }

    /**
     * Return a list of agenda items for display from the given sorted tasks.
     *
     * @param sortedTasks tasks sorted by date (ascending)
     * @param startDate   start date of the agenda
     * @param endDate     end date of the agenda
     * @return
     */
    private List<AgendaItem> tasksToAgendaItems(List<Task> sortedTasks,
                                                LocalDate startDate,
                                                LocalDate endDate) {
        List<AgendaItem> convAgendaItems = new ArrayList<>();
        LocalDate currDate = new LocalDate(startDate);

        for (Task task : sortedTasks) {
            while (task.getLocalDueDate().compareTo(currDate) < 0) {
                String dateStr = StringConverter.localDateToString(currDate);
                convAgendaItems.add(new AgendaItem(dateStr, new Duration(0)));
                convAgendaItems.add(new AgendaItem("No tasks", new Duration(0)));
                currDate = currDate.plusDays(1);
            }

            convAgendaItems.add(new AgendaItem(task.getName(),
                    task.getTimeWorked()));
        }

        while (currDate.compareTo(endDate) < 0) {
            String dateStr = StringConverter.localDateToString(currDate);
            convAgendaItems.add(new AgendaItem(dateStr, new Duration(0)));
            convAgendaItems.add(new AgendaItem("No tasks", new Duration(0)));
            currDate = currDate.plusDays(1);
        }

        return convAgendaItems;
    }

    /**
     * Initialize the button for adding tasks.
     *
     * @param view view for the user interface
     */
    private void initAddTaskBtn(View view) {
        Button addTaskBtn = view.findViewById(R.id.btn_add_task);
        addTaskBtn.setOnClickListener(v -> {
            AddTaskFragment addTaskFrag = new AddTaskFragment();
            ((FragActivity) requireActivity()).replaceFragment(addTaskFrag);
        });
    }
}

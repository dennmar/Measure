package com.example.measure.features;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.measure.R;
import com.example.measure.features.agenda.view.AgendaFragment;
import com.example.measure.features.habit_tracker.HabitTrackerFragment;
import com.google.android.material.tabs.TabLayout;


/**
 * Main activity for the measure application.
 */
public class MeasureActivity extends FragActivity {
    /**
     * Set the display and add the initial fragment.
     *
     * @param savedInstanceState previously saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);
        initTabLayout();

        setFragContainerId(R.id.measure_act_container);
        addFragment(new AgendaFragment());
    }

    /**
     * Set an on tab selected listener on the tab layout.
     */
    private void initTabLayout() {
        TabLayout tl = findViewById(R.id.tab_layout_measure);
        tl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment nextFrag = null;

                switch (tab.getPosition()) {
                    case 0:
                        nextFrag = new AgendaFragment();
                        break;
                    case 1:
                        nextFrag = new HabitTrackerFragment();
                        break;
                    case 2:
                        break;
                    default:
                        break;
                }

                replaceFragment(nextFrag);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }
}
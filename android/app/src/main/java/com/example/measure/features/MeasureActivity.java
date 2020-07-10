package com.example.measure.features;

import android.os.Bundle;

import com.example.measure.R;
import com.example.measure.features.agenda.view.AgendaFragment;


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
        setFragContainerId(R.id.measure_act_container);
        addFragment(new AgendaFragment());
    }
}
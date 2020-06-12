package com.example.measure;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * Main activity for the measure application.
 */
public class MeasureActivity extends AppCompatActivity {
    /**
     * Set the display and add the initial fragment.
     *
     * @param savedInstanceState previously saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);
        addFragment(R.id.measure_act_container, new AgendaFragment());
    }

    /**
     * Replace the current fragment of a container with the given fragment.
     *
     * @param fragContainerId id of the container to display the fragment
     * @param frag            fragment to switch to
     */
    private void addFragment(int fragContainerId, Fragment frag) {
        getSupportFragmentManager().beginTransaction()
                .add(fragContainerId, frag).commit();
    }

    /**
     * Add a fragment to a container to be displayed.
     *
     * @param fragContainerId id of the container to display the fragment
     * @param frag            fragment to be added to the container
     */
    private void replaceFragment(int fragContainerId, Fragment frag) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(fragContainerId, frag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
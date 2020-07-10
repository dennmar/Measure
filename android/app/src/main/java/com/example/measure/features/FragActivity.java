package com.example.measure.features;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * An activity that manages fragments in a container.
 */
public abstract class FragActivity extends AppCompatActivity {
    private int fragContainerId;

    /**
     * Replace the current fragment of the container with the given fragment.
     *
     * @param frag            fragment to switch to
     */
    public void addFragment(Fragment frag) {
        getSupportFragmentManager().beginTransaction()
                .add(fragContainerId, frag).commit();
    }

    /**
     * Add a fragment to the container to be displayed.
     *
     * @param frag            fragment to be added to the container
     */
    public void replaceFragment(Fragment frag) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(fragContainerId, frag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    protected void setFragContainerId(int id) {
        fragContainerId = id;
    }
}

package com.example.measure.features;

import android.os.Bundle;

import com.example.measure.R;
import com.example.measure.features.register.RegisterFragment;

/**
 * Login/register activity before entering the application.
 */
public class EnterActivity extends FragActivity {
    /**
     * Set the display and add the initial fragment.
     *
     * @param savedInstanceState previously saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        setFragContainerId(R.id.enter_act_container);
        addFragment(new RegisterFragment());
    }
}

package com.example.measure.features.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;

import com.example.measure.R;
import com.example.measure.di.MeasureApplication;

import javax.inject.Inject;

/**
 * A fragment that handles the UI for login.
 */
public class LoginFragment extends Fragment {
    @Inject
    protected LoginViewModel.Factory lvmFactory;
    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ((MeasureApplication) getActivity().getApplicationContext())
                .appComponent.inject(this);
        super.onCreate(savedInstanceState);
        loginViewModel = lvmFactory.create(savedInstanceState);
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
        View rootView = inflater.inflate(R.layout.fragment_login, container,
                false);
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public void addUser(String username, String password) {

    }
}

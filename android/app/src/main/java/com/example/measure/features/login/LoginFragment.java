package com.example.measure.features.login;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;

import com.example.measure.R;
import com.example.measure.di.MeasureApplication;
import com.example.measure.features.FragActivity;
import com.example.measure.features.MeasureActivity;
import com.example.measure.utils.AuthenticationException;
import com.example.measure.utils.DBOperationException;

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
        initLoginBtn(rootView);
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * Set an on-click listener on the button to login.
     *
     * @param view view for the user interface
     */
    private void initLoginBtn(View view) {
        Button loginBtn = view.findViewById(R.id.btn_login_user);
        EditText usernameText = view.findViewById(
                R.id.edittext_login_username);
        EditText passwordText = view.findViewById(
                R.id.edittext_login_password);

        loginBtn.setOnClickListener(v -> {
            String username = usernameText.getText().toString();
            String password = passwordText.getText().toString();

            try {
                loginViewModel.login(username, password);
                FragActivity activity = (FragActivity)requireActivity();
                activity.startActivity(MeasureActivity.class, true);
            }
            catch (AuthenticationException ae) {
                // TODO: handle exception
                Log.d("LoginFragment", ae.toString());
            }
            catch (DBOperationException dboe) {
                Log.d("LoginFragment", dboe.toString());
            }
        });
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public void addUser(String username, String password) {}
}

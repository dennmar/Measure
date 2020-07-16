package com.example.measure.features.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.measure.R;
import com.example.measure.di.MeasureApplication;
import com.example.measure.features.FragActivity;
import com.example.measure.features.login.LoginFragment;
import com.example.measure.models.data.User;

import javax.inject.Inject;

/**
 * A fragment that handles the UI for user registration.
 */
public class RegisterFragment extends Fragment {
    @Inject
    protected RegisterViewModel.Factory rvmFactory;
    private RegisterViewModel registerViewModel;

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
        registerViewModel = rvmFactory.create(savedInstanceState);
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
        View rootView = inflater.inflate(R.layout.fragment_register, container,
                false);
        initRegisterBtn(rootView);
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * Set an on-click listener for the button to register a new user.
     *
     * @param view view for the user interface
     */
    private void initRegisterBtn(View view) {
        Button registerBtn = view.findViewById(R.id.btn_register_user);
        EditText usernameEditText =
                view.findViewById(R.id.edittext_new_user_username);
        EditText emailEditText =
                view.findViewById(R.id.edittext_new_user_email);
        EditText passwordEditText =
                view.findViewById(R.id.edittext_new_user_password);
        EditText password2EditText =
                view.findViewById(R.id.edittext_new_user_password2);

        registerBtn.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            User newUser = new User(username, email, password);
            registerUser(newUser);
            ((FragActivity) requireActivity())
                    .replaceFragment(new LoginFragment());
        });
    }

    /**
     * Register the given user.
     *
     * @param user     user to be registered
     */
    private void registerUser(User user) {
        registerViewModel.addUser(user);
    }
}

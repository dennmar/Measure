package com.example.measure.di.components;

import com.example.measure.di.Scopes;
import com.example.measure.di.modules.AssistedInjectModule;
import com.example.measure.di.modules.prod.LoginRepositoryModule;
import com.example.measure.di.modules.test.MockLoginDaoModule;
import com.example.measure.di.modules.test.MockUserDaoModule;
import com.example.measure.models.login.LoginRepository;
import com.example.measure.models.user.UserDao;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component targeted for login repository tests that makes Dagger create
 * a graph of dependencies and exposes objects from the application graph.
 */
@Component(modules = {
        AssistedInjectModule.class,
        LoginRepositoryModule.class,
        MockLoginDaoModule.class,
        MockUserDaoModule.class
})
@Scopes.ApplicationScope
public interface TestLoginRepositoryComponent {
    LoginRepository loginRepository();
    UserDao mockUserDao();
}

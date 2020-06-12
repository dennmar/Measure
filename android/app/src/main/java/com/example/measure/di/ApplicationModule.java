package com.example.measure.di;

import com.example.measure.LocalLoginRepository;
import com.example.measure.LocalTaskRepository;
import com.example.measure.LoginRepository;
import com.example.measure.TaskRepository;
import com.squareup.inject.assisted.dagger2.AssistedModule;

import dagger.Module;
import dagger.Provides;

/**
 * A module that defines how the application's dependencies are provided.
 */
@AssistedModule
@Module(includes = AssistedInject_ApplicationModule.class)
public class ApplicationModule {
    /**
     * Return the task repository to be provided for all dependents.
     *
     * @return task repository to be used for task data access
     */
    @Provides
    public TaskRepository provideTaskRepository() {
        return new LocalTaskRepository();
    }

    /**
     * Return the login repository to be provided for all dependents.
     *
     * @return login repository to be used for login session data access
     */
    @Provides
    public LoginRepository provideLoginRepository() {
        return new LocalLoginRepository();
    }
}

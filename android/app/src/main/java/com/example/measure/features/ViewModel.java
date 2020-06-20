package com.example.measure.features;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

/**
 * A view model that handles the interaction between a view and the model.
 */
public class ViewModel implements LifecycleOwner {
    protected LifecycleRegistry lifecycleRegistry;

    /**
     * Initialize lifecycle.
     */
    public ViewModel() {
        lifecycleRegistry = new LifecycleRegistry(this);
        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
    }

    /**
     * Return the lifecycle of this instance.
     *
     * @return the lifecycle of this instance
     */
    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }

    /**
     * Enable live data observers created by view model to observe.
     */
    protected void enableObservers() {
        lifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
    }

    /**
     * Remove live data observers created by the view model.
     */
    protected void removeObservers() {
        lifecycleRegistry.setCurrentState(Lifecycle.State.DESTROYED);
    }
}

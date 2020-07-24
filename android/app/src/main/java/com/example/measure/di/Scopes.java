package com.example.measure.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Scope annotations to define when the same instance should be provided by
 * Dagger,
 */
public class Scopes {
    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ApplicationScope {}
}

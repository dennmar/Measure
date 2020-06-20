package com.example.measure.di.modules;

import com.squareup.inject.assisted.dagger2.AssistedModule;

import dagger.Module;

/**
 * A Dagger module to enable assisted injection.
 */
@AssistedModule
@Module(includes = AssistedInject_AssistedInjectModule.class)
public class AssistedInjectModule { }

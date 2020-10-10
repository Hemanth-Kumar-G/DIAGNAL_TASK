package com.task.diagnal.di

import com.task.diagnal.di.scope.ActivityScoped
import com.task.diagnal.ui.main.MainActivity
import com.task.diagnal.ui.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(
    includes = [ViewModelModule::class]
)
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun mainActivity(): MainActivity

}
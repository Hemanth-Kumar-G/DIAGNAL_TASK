package com.task.diagnal.di

import com.task.diagnal.AppController
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ActivityBindingModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class
    ]
)
interface AppComponent : AndroidInjector<AppController> {

    override fun inject(instance: AppController?)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(appController: AppController): Builder

        fun build(): AppComponent
    }
}
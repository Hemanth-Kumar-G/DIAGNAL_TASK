package com.task.diagnal.di


import androidx.lifecycle.ViewModel
import com.task.diagnal.di.scope.ViewModelKey
import com.task.diagnal.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 *<h1></h1>

 *<p>
 * The ViewModelModule is used to provide a mapChatData of view models through dagger that is used by the ViewModelFactoryModule class.
 *</p>

 * @author : Hemanth
 * @since : 8 Oct 2020
 * @version : 1.0
 */

@Module
abstract class ViewModelModule : ViewModelFactoryModule() {

    /*
    * This method basically says
    * inject this object into a Map using the @IntoMap annotation,
    * with the  LoginViewModel.class as key,
    * and a Provider that will build a LoginViewModel
    * object.
    *
    * */

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun provideMainViewModel(mainViewModel: MainViewModel): ViewModel


}
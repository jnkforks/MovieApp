package com.anggitprayogo.movieapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anggitprayogo.core.util.viewmodel.ViewModelFactory
import com.anggitprayogo.core.util.viewmodel.ViewModelKey
import com.anggitprayogo.movieapp.feature.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMovieViewModel(viewModel: MainViewModel): ViewModel
}
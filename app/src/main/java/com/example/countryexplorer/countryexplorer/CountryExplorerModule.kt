package com.example.countryexplorer.countryexplorer

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CountryExplorerModule {

    @Binds
    abstract fun providesNavigator(navigatorImpl: CountryExplorerNavigatorImpl): CountryExplorerNavigator

}
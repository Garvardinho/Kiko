package com.garvardinho.kiko.model.di.modules

import com.garvardinho.kiko.model.room.AppDatabase
import com.garvardinho.kiko.model.room.MovieListDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaoModule {

    @Singleton
    @Provides
    fun movieListDao(): MovieListDao = AppDatabase.getInstance().movieListDao()
}
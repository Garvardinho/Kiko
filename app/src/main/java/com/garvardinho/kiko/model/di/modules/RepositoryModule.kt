package com.garvardinho.kiko.model.di.modules

import com.garvardinho.kiko.model.Repository
import com.garvardinho.kiko.model.RepositoryImpl
import com.garvardinho.kiko.model.realm.IRealmDataSource
import com.garvardinho.kiko.model.retrofit.IRemoteDataSource
import com.garvardinho.kiko.model.room.MovieListDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun repository(
        remoteDataSource: IRemoteDataSource,
        realmDataSource: IRealmDataSource,
        cacheDao: MovieListDao
    ): Repository {
        return RepositoryImpl(
            remoteDataSource = remoteDataSource,
            realmDataSource = realmDataSource,
            cacheDao = cacheDao
        )
    }
}
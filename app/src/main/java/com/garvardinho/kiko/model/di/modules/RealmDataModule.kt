package com.garvardinho.kiko.model.di.modules

import com.garvardinho.kiko.model.realm.IRealmDataSource
import com.garvardinho.kiko.model.realm.RealmDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton

@Module
abstract class RealmDataModule {

    @Singleton
    @Binds
    abstract fun realmDataSource(impl: RealmDataSource) : IRealmDataSource

    companion object {
        @Singleton
        @Provides
        fun realm(): Realm = Realm.getDefaultInstance()
    }
}
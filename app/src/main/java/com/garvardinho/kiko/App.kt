package com.garvardinho.kiko

import android.app.Application
import com.garvardinho.kiko.model.di.components.AppComponent
import com.garvardinho.kiko.model.di.components.DaggerAppComponent
import com.garvardinho.kiko.model.di.modules.AppModule
import com.garvardinho.kiko.model.room.AppDatabase
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmConfiguration

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppDatabase.create(this)
        Single.fromCallable {
            AppDatabase.getInstance().movieListDao().clearCache()
        }.subscribeOn(Schedulers.computation()).subscribe()
        
        Realm.init(this)
        Realm.setDefaultConfiguration(
            RealmConfiguration.Builder()
                .allowWritesOnUiThread(true)
                .allowQueriesOnUiThread(true)
                .build()
        )
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {

        lateinit var instance: App
    }
}
package com.garvardinho.kiko

import android.app.Application
import com.garvardinho.kiko.model.room.AppDatabase
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class App : Application() {

    private val cicerone: Cicerone<Router> by lazy {
        Cicerone.create()
    }
    val navigationHolder get() = cicerone.getNavigatorHolder()
    val router get() = cicerone.router

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppDatabase.create(this)
        Single.fromCallable {
            AppDatabase.getInstance().movieListDao().clearCache()
        }.subscribeOn(Schedulers.computation()).subscribe()
    }

    companion object {

        lateinit var instance: App
    }
}
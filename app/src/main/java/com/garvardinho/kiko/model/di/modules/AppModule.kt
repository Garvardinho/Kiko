package com.garvardinho.kiko.model.di.modules

import com.garvardinho.kiko.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: App) {

    @Singleton
    @Provides
    fun app(): App {
        return app
    }
}
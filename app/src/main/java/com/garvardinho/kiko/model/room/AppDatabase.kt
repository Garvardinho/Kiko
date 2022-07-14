package com.garvardinho.kiko.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        RoomMovie::class
    ],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun movieListDao(): MovieListDao

    companion object {
        private const val DB_NAME = "database.db"
        private var instance: AppDatabase? = null
        fun getInstance() = instance
            ?: throw RuntimeException("Database has not been created. Please call create(context)")

        fun create(context: Context?) {
            if (instance == null) {
                instance = Room.databaseBuilder(context!!, AppDatabase::class.java,
                    DB_NAME)
                    .build()
            }
        }
    }
}
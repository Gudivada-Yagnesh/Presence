package com.yagnesh.presence.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yagnesh.presence.data.model.Student

@Database(
    entities = [Student::class],
    version = 1,
    exportSchema = false
)
abstract class PresenceDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object {

        @Volatile
        private var INSTANCE: PresenceDatabase? = null

        fun getDatabase(context: Context): PresenceDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PresenceDatabase::class.java,
                    "presence_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
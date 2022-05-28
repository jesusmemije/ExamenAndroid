package com.memije.examenandroid.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.memije.examenandroid.room.dao.MovieDao
import com.memije.examenandroid.room.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class ExamenRoomDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: ExamenRoomDatabase? = null

        fun getDatabase(context: Context): ExamenRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExamenRoomDatabase::class.java,
                    "examen_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}
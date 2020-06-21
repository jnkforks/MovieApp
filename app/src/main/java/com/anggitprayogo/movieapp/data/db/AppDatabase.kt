package com.anggitprayogo.movieapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anggitprayogo.movieapp.data.db.dao.MovieDao
import com.anggitprayogo.movieapp.data.db.entity.MovieEntity

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}
package com.anggitprayogo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anggitprayogo.data.local.dao.MovieDao
import com.anggitprayogo.data.local.entity.MovieEntity

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
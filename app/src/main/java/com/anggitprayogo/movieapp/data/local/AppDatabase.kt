package com.anggitprayogo.movieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anggitprayogo.movieapp.data.local.dao.FavouriteDao
import com.anggitprayogo.movieapp.data.local.dao.MovieDao
import com.anggitprayogo.movieapp.data.local.dao.RemoteKeysDao
import com.anggitprayogo.movieapp.data.local.entity.FavouriteEntity
import com.anggitprayogo.movieapp.data.local.entity.MovieEntity
import com.anggitprayogo.movieapp.data.local.entity.RemoteKeys

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
@Database(
    entities = [
        FavouriteEntity::class,
        MovieEntity::class,
        RemoteKeys::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favouriteDao(): FavouriteDao

    abstract fun movieDao(): MovieDao

    abstract fun remoteKeysDao(): RemoteKeysDao
}
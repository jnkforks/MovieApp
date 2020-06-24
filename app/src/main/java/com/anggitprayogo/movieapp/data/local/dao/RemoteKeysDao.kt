package com.anggitprayogo.movieapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anggitprayogo.movieapp.data.local.entity.RemoteKeys

/**
 * Created by Anggit Prayogo on 6/23/20.
 */
@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE movieId =:movieId")
    suspend fun remoteKeysMovieId(movieId: Long): RemoteKeys?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}
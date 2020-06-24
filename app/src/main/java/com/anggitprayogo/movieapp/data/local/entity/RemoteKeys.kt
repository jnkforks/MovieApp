package com.anggitprayogo.movieapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Anggit Prayogo on 6/23/20.
 */
@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val movieId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)
package com.anggitprayogo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) val uid: Long? = null,
    @ColumnInfo(name = "movie_id") val movieId: Int?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "genres") val genres: String?,
    @ColumnInfo(name = "vote") val vote: Double?,
    @ColumnInfo(name = "release_date") val releaseDate: String?,
    @ColumnInfo(name = "overview") val overview: String?,
    @ColumnInfo(name = "banner_url") val bannerUrl: String?,
    @ColumnInfo(name = "poster_url") val posterUrl: String?
)
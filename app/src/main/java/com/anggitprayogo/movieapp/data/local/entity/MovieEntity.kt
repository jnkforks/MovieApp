package com.anggitprayogo.movieapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anggitprayogo.movieapp.BuildConfig

/**
 * Created by Anggit Prayogo on 6/23/20.
 */
@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey @ColumnInfo(name = "id") val movieId: Long?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "genres") val genres: String?,
    @ColumnInfo(name = "vote_count") val voteCount: Int?,
    @ColumnInfo(name = "vote_avarage") val voteAvarage: Double?,
    @ColumnInfo(name = "release_date") val releaseDate: String?,
    @ColumnInfo(name = "overview") val overview: String?,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo(name = "adult") val adult: Boolean?,
    @ColumnInfo(name = "popularity") val popularity: Double?
){
    fun getPoster(): String {
        return BuildConfig.IMAGE_URL + posterPath
    }
}


package com.anggitprayogo.movieapp.data.mapper

import com.anggitprayogo.movieapp.data.local.entity.MovieEntity
import com.anggitprayogo.movieapp.data.remote.entity.Movie

/**
 * Created by Anggit Prayogo on 6/24/20.
 */
object MovieEntityMapper {

    fun mapFromMovie(movie: Movie): MovieEntity{
        with(movie){
            return MovieEntity(
                id?.toLong(),
                title,
                null,
                voteCount,
                voteAverage,
                releaseDate,
                overview,
                backdropPath,
                posterPath,
                adult,
                popularity
            )
        }
    }
}
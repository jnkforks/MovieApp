package com.anggitprayogo.movieapp.data.mapper

import com.anggitprayogo.movieapp.data.local.entity.MovieEntity
import com.anggitprayogo.movieapp.data.remote.entity.Movie

/**
 * Created by Anggit Prayogo on 6/24/20.
 */
object MovieMapper {

    fun mapFromMovieEntity(movieEntity: MovieEntity): Movie {
        with(movieEntity) {
            return Movie(
                adult,
                backdropPath,
                emptyList(),
                movieId?.toInt(),
                overview,
                popularity,
                posterPath,
                releaseDate,
                title,
                null,
                voteAvarage,
                voteCount
            )
        }
    }
}
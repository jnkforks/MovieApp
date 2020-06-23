package com.anggitprayogo.movieapp

import com.anggitprayogo.movieapp.data.local.entity.MovieEntity
import com.anggitprayogo.movieapp.data.remote.entity.*
import org.mockito.Mockito.mock

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
object TestDataSourceMovies {


    val movies = Movies(
        mock(Dates::class.java),
        0,
        listOf<Movie>(
            mock(Movie::class.java),
            mock(Movie::class.java)
        ),
        1,
        2
    )

    val movieDetail = mock(MovieDetail::class.java)

    val movieReviews = MovieReviews(
        1, 1, listOf(
            mock(Review::class.java),
            mock(Review::class.java)
        ), 1, 2
    )

    val moviesEntityList = listOf(
        mock(MovieEntity::class.java),
        mock(MovieEntity::class.java)
    )
}
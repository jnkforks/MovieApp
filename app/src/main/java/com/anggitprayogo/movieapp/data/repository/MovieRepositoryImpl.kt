package com.anggitprayogo.movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.anggitprayogo.movieapp.data.local.dao.MovieDao
import com.anggitprayogo.movieapp.data.local.entity.MovieEntity
import com.anggitprayogo.movieapp.data.remote.entity.Movie
import com.anggitprayogo.movieapp.data.remote.entity.MovieDetail
import com.anggitprayogo.movieapp.data.remote.entity.MovieReviews
import com.anggitprayogo.movieapp.data.remote.entity.Movies
import com.anggitprayogo.movieapp.data.remote.pagingsource.*
import com.anggitprayogo.movieapp.data.remote.routes.NetworkService
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val service: NetworkService,
    private val movieDao: MovieDao
) : MovieRepository {

    override fun getPopularMoviePagingSource(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = PaginConfig.NETWORK_PAGING_SIZE),
            pagingSourceFactory = { PopularMoviePagingSource(service) }
        ).flow
    }

    override fun getUpcomingMoviePagingSource(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = PaginConfig.NETWORK_PAGING_SIZE),
            pagingSourceFactory = { UpcomingMoviePagingSource(service) }
        ).flow
    }

    override fun getNowPlayingMoviePagingSource(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = PaginConfig.NETWORK_PAGING_SIZE),
            pagingSourceFactory = { NowPlayingMoviePagingSource(service) }
        ).flow
    }

    override fun getTopRatedMoviePagingSource(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = PaginConfig.NETWORK_PAGING_SIZE),
            pagingSourceFactory = { TopRatedMoviePagingSource(service) }
        ).flow
    }

    override suspend fun getDetailMovie(movieId: String): Response<MovieDetail> {
        return service.getDetailMovie(movieId)
    }

    override suspend fun getMovieReviewsByMovieId(movieId: String): Response<MovieReviews> {
        return service.getMovieReviewsByMovieId(movieId)
    }

    override suspend fun fetchAllMoviesDao(): List<MovieEntity> {
        return movieDao.fetchAllMovies()
    }

    override suspend fun fetchMovieByMovieId(movieId: Int): List<MovieEntity> {
        return movieDao.fetchMovieByMovieId(movieId)
    }

    override suspend fun insertMovie(movieEntity: MovieEntity) {
        return movieDao.insertMovie(movieEntity)
    }

    override suspend fun deleteMovie(movieEntity: MovieEntity) {
        return movieDao.deleteMovie(movieEntity)
    }
}
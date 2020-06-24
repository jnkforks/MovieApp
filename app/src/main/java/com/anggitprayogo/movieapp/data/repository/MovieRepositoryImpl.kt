package com.anggitprayogo.movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.anggitprayogo.movieapp.data.local.dao.FavouriteDao
import com.anggitprayogo.movieapp.data.local.dao.MovieDao
import com.anggitprayogo.movieapp.data.local.dao.RemoteKeysDao
import com.anggitprayogo.movieapp.data.local.entity.FavouriteEntity
import com.anggitprayogo.movieapp.data.local.entity.MovieEntity
import com.anggitprayogo.movieapp.data.mediator.NowPlayingMovieMediator
import com.anggitprayogo.movieapp.data.mediator.PopularMovieMediator
import com.anggitprayogo.movieapp.data.mediator.TopRatedMovieMediator
import com.anggitprayogo.movieapp.data.mediator.UpcomingMovieMediator
import com.anggitprayogo.movieapp.data.remote.entity.MovieDetail
import com.anggitprayogo.movieapp.data.remote.entity.MovieReviews
import com.anggitprayogo.movieapp.data.remote.pagingsource.PaginConfig
import com.anggitprayogo.movieapp.data.remote.routes.NetworkService
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val service: NetworkService,
    private val favouriteDao: FavouriteDao,
    private val movieDao: MovieDao,
    private val remoteKeysDao: RemoteKeysDao
) : MovieRepository {

    override fun getPopularMoviePagingSource(): Flow<PagingData<MovieEntity>> {
        val paginSourceFactory = { movieDao.fetchAll() }
        return Pager(
            config = PagingConfig(pageSize = PaginConfig.NETWORK_PAGING_SIZE),
            remoteMediator = PopularMovieMediator(service, movieDao, remoteKeysDao),
            pagingSourceFactory = paginSourceFactory
        ).flow
    }

    override fun getUpcomingMoviePagingSource(): Flow<PagingData<MovieEntity>> {
        val paginSourceFactory = { movieDao.fetchAll() }
        return Pager(
            config = PagingConfig(pageSize = PaginConfig.NETWORK_PAGING_SIZE),
            remoteMediator = UpcomingMovieMediator(service, movieDao, remoteKeysDao),
            pagingSourceFactory = paginSourceFactory
        ).flow
    }

    override fun getNowPlayingMoviePagingSource(): Flow<PagingData<MovieEntity>> {
        val paginSourceFactory = { movieDao.fetchAll() }
        return Pager(
            config = PagingConfig(pageSize = PaginConfig.NETWORK_PAGING_SIZE),
            remoteMediator = NowPlayingMovieMediator(service, movieDao, remoteKeysDao),
            pagingSourceFactory = paginSourceFactory
        ).flow
    }

    override fun getTopRatedMoviePagingSource(): Flow<PagingData<MovieEntity>> {
        val paginSourceFactory = { movieDao.fetchAll() }
        return Pager(
            config = PagingConfig(pageSize = PaginConfig.NETWORK_PAGING_SIZE),
            remoteMediator = TopRatedMovieMediator(service, movieDao, remoteKeysDao),
            pagingSourceFactory = paginSourceFactory
        ).flow
    }

    override suspend fun getDetailMovie(movieId: String): Response<MovieDetail> {
        return service.getDetailMovie(movieId)
    }

    override suspend fun getMovieReviewsByMovieId(movieId: String): Response<MovieReviews> {
        return service.getMovieReviewsByMovieId(movieId)
    }

    override suspend fun fetchAllMoviesDao(): List<FavouriteEntity> {
        return favouriteDao.fetchAllMovies()
    }

    override suspend fun fetchMovieByMovieId(movieId: Int): List<FavouriteEntity> {
        return favouriteDao.fetchMovieByMovieId(movieId)
    }

    override suspend fun insertMovie(favouriteEntity: FavouriteEntity) {
        return favouriteDao.insertMovie(favouriteEntity)
    }

    override suspend fun deleteMovie(favouriteEntity: FavouriteEntity) {
        return favouriteDao.deleteMovie(favouriteEntity)
    }
}
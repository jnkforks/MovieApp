package com.anggitprayogo.movieapp.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.anggitprayogo.movieapp.data.local.dao.MovieDao
import com.anggitprayogo.movieapp.data.local.dao.RemoteKeysDao
import com.anggitprayogo.movieapp.data.local.entity.MovieEntity
import com.anggitprayogo.movieapp.data.local.entity.RemoteKeys
import com.anggitprayogo.movieapp.data.mapper.MovieEntityMapper
import com.anggitprayogo.movieapp.data.remote.pagingsource.PaginConfig
import com.anggitprayogo.movieapp.data.remote.routes.NetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException
import javax.inject.Inject

/**
 * Created by Anggit Prayogo on 6/24/20.
 */
@OptIn(ExperimentalPagingApi::class)
class NowPlayingMovieMediator @Inject constructor(
    private val service: NetworkService,
    private val movieDao: MovieDao,
    private val remoteKeysDao: RemoteKeysDao
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, MovieEntity>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: PaginConfig.MOVIE_DB_PAGING_STARTING_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                if (remoteKeys == null) {
                    // The LoadType is PREPEND so some data was loaded before,
                    // so we should have been able to get remote keys
                    // If the remoteKeys are null, then we're an invalid state and we have a bug
                    throw InvalidObjectException("Remote key and the prevKey should not be null")
                }
                // If the previous key is null, then we can't request more data
                val prevKey = remoteKeys.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                remoteKeys.prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys == null || remoteKeys.nextKey == null) {
                    throw InvalidObjectException("Remote key should not be null for $loadType")
                }
                remoteKeys.nextKey
            }
        }

        try {
            val apiResponse = service.getNowPlayingMovie(page.toString())

            val movies = apiResponse.body()?.results
            val endOfPaginationReached = movies.isNullOrEmpty()
            withContext(Dispatchers.IO) {
                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.clearRemoteKeys()
                    movieDao.deleteAllMovies()
                }
                val prevKey =
                    if (page == PaginConfig.MOVIE_DB_PAGING_STARTING_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = movies?.map {
                    RemoteKeys(it.id?.toLong() ?: 0L, prevKey, nextKey)
                }
                keys?.let { remoteKeysDao.insertAll(it) }
                val movieEntityMapper = movies?.map {
                    MovieEntityMapper.mapFromMovie(it)
                }
                movieEntityMapper?.let { movieDao.insertAll(it) }
            }
            return MediatorResult.Success(endOfPaginationReached)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieEntity>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.movieId?.let { movieId ->
                remoteKeysDao.remoteKeysMovieId(movieId.toLong())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieEntity>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                // Get the remote keys of the first items retrieved
                movie.movieId?.toLong()?.let { remoteKeysDao.remoteKeysMovieId(it) }
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieEntity>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                // Get the remote keys of the last item retrieved
                movie.movieId?.toLong()?.let { remoteKeysDao.remoteKeysMovieId(it) }
            }
    }
}
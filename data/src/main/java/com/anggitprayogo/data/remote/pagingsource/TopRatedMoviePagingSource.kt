package com.anggitprayogo.data.remote.pagingsource

import androidx.paging.PagingSource
import com.anggitprayogo.data.remote.entity.Movie
import com.anggitprayogo.data.remote.routes.NetworkService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Anggit Prayogo on 6/23/20.
 */
class TopRatedMoviePagingSource @Inject constructor(
    private val service: NetworkService
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: PaginConfig.MOVIE_DB_PAGING_STARTING_INDEX
        return try {
            val result = service.getTopRated(position.toString())
            val response = result.body()?.results
            LoadResult.Page(
                data = response ?: emptyList(),
                prevKey = if (position == PaginConfig.MOVIE_DB_PAGING_STARTING_INDEX) null else position - 1,
                nextKey = if (response.isNullOrEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
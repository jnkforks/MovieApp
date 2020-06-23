package com.anggitprayogo.movieapp.data.remote.pagingsource

import androidx.paging.PagingSource
import com.anggitprayogo.movieapp.data.remote.entity.Movie
import com.anggitprayogo.movieapp.data.repository.MovieRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Anggit Prayogo on 6/23/20.
 */
const val MOVIE_DB_STARTING_PAGE_INDEX = 1

class MoviePagingSource @Inject constructor(
    private val repository: MovieRepository
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: MOVIE_DB_STARTING_PAGE_INDEX
        return try {
            val result = repository.getPopularMovie()
            val response = result.body()?.results
            LoadResult.Page(
                data = response ?: emptyList(),
                prevKey = if (position == MOVIE_DB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.isNullOrEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
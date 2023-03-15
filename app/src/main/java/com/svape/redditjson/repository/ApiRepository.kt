package com.svape.redditjson.repository

import com.svape.redditjson.api.ApiServices
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ApiRepository @Inject constructor(
    private val apiServices: ApiServices,
) {
    fun getPopularMoviesList() = apiServices.getPopularMoviesList()
}
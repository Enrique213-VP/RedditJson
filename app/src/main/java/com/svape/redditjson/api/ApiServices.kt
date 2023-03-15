package com.svape.redditjson.api

import com.svape.redditjson.response.RedditJson
import com.svape.redditjson.utils.ENDPOINT
import retrofit2.Call
import retrofit2.http.GET

interface ApiServices {

    //    https://www.reddit.com/reddits.json

    @GET(ENDPOINT)
    fun getPopularMoviesList(): Call<RedditJson.Data.Children>




}
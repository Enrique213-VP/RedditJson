package com.svape.redditjson.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.svape.redditjson.R
import com.svape.redditjson.adapter.RedditAdapter
import com.svape.redditjson.databinding.FragmentRedditBinding
import com.svape.redditjson.repository.ApiRepository
import com.svape.redditjson.response.RedditJson
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class RedditFragment : Fragment() {

    private lateinit var binding: FragmentRedditBinding

    @Inject
    lateinit var apiRepository: ApiRepository

    @Inject
    lateinit var moviesAdapter: RedditAdapter

    val TAG = "MoviesFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentRedditBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            prgBarMovies.visibility = View.VISIBLE
            apiRepository.getPopularMoviesList().enqueue(object : Callback<RedditJson.Data.Children> {
                override fun onResponse(
                    call: Call<RedditJson.Data.Children>,
                    response: Response<RedditJson.Data.Children>
                ) {
                    prgBarMovies.visibility = View.VISIBLE
                    when (response.code()) {
                        in 200..299 -> {
                            Log.d("Response Code", " success messages : ${response.code()}")
                            prgBarMovies.visibility = View.GONE
                            response.body()?.let { itBody ->
                                itBody.data.let { itData ->
                                    if (itData.isNotEmpty()) {
                                        moviesAdapter.differ.submitList(itData)
                                        //Recycler
                                        binding.rlMovies.layoutManager = LinearLayoutManager(requireContext())
                                        binding.rlMovies.adapter = moviesAdapter

                                    }
                                }
                            }
                        }
                        in 300..399 -> {
                            Log.d("Response Code", " Redirection messages : ${response.code()}")
                        }
                        in 400..499 -> {
                            Log.d("Response Code", " Client error responses : ${response.code()}")
                        }
                        in 500..599 -> {
                            Log.d("Response Code", " Server error responses : ${response.code()}")
                        }
                    }
                }

                override fun onFailure(call: Call<RedditJson.Data.Children>, t: Throwable) {
                    prgBarMovies.visibility = View.GONE
                    Log.d(TAG, t.message.toString())

                }

            })
        }
    }

}
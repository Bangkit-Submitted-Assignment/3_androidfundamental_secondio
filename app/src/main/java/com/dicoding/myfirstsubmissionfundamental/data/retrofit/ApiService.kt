package com.dicoding.myfirstsubmissionfundamental.data.retrofit

import com.dicoding.myfirstsubmissionfundamental.data.response.DetailUserResponse
import com.dicoding.myfirstsubmissionfundamental.data.response.GithubResponse
import com.dicoding.myfirstsubmissionfundamental.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: token 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")

    @GET("search/users")
    fun getListUser(@Query("q")page: String): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}
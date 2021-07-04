package com.star_zero.unauthorizedsample.api

import com.star_zero.unauthorizedsample.data.model.User
import retrofit2.http.GET

interface SampleService {

    @GET("user")
    suspend fun getUser(): User
}

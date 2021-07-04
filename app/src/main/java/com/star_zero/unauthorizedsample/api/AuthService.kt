package com.star_zero.unauthorizedsample.api

import com.star_zero.unauthorizedsample.data.model.AuthToken
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {
    @POST("login")
    suspend fun login(): AuthToken

    @FormUrlEncoded
    @POST("refresh_token")
    suspend fun refreshToken(@Field("refresh_token") refreshToken: String): AuthToken
}

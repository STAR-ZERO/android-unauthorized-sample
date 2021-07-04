package com.star_zero.unauthorizedsample.data.repository

interface AuthRepository {
    val accessToken: String?

    val refreshToken: String?

    fun isLoggedIn(): Boolean

    suspend fun login()

    suspend fun refreshAuthToken(): String

    fun logout()
}

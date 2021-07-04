package com.star_zero.unauthorizedsample.data.repository

import com.star_zero.unauthorizedsample.api.AuthService
import com.star_zero.unauthorizedsample.data.preference.AppPreference
import com.star_zero.unauthorizedsample.util.AppDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthDataRepository @Inject constructor(
    private val authService: AuthService,
    private val appPreference: AppPreference,
    private val appDispatchers: AppDispatchers,
) : AuthRepository {

    override val accessToken: String?
        get() = appPreference.accessToken

    override val refreshToken: String?
        get() = appPreference.refreshToken

    override fun isLoggedIn(): Boolean {
        return accessToken != null
    }

    override suspend fun login(): Unit = withContext(appDispatchers.io) {
        val accessToken = authService.login()
        appPreference.accessToken = accessToken.accessToken
        appPreference.refreshToken = accessToken.refreshToken
    }

    override suspend fun refreshAuthToken(): String = withContext(appDispatchers.io) {
        val refreshToken = refreshToken!!
        val authToken = authService.refreshToken(refreshToken)
        appPreference.accessToken = authToken.accessToken
        appPreference.refreshToken = authToken.refreshToken
        authToken.accessToken
    }

    override fun logout() {
        appPreference.accessToken = null
        appPreference.refreshToken = null
    }
}

package com.star_zero.unauthorizedsample.api

import com.star_zero.unauthorizedsample.data.repository.AuthRepository
import com.star_zero.unauthorizedsample.uitl.AppDispatchers
import com.star_zero.unauthorizedsample.uitl.UnauthorizedHandler
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccessTokenAuthenticator @Inject constructor(
    private val authRepository: AuthRepository,
    private val unauthorizedHandler: UnauthorizedHandler,
    private val appDispatchers: AppDispatchers
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val accessToken = authRepository.accessToken

        synchronized(this) {
            val newAccessToken = authRepository.accessToken
            if (newAccessToken == null) {
                Timber.d("New access token is already null")
                return null
            }

            val updatedAccessToken = if (newAccessToken != accessToken) {
                Timber.d("Already access token was refreshed.")
                newAccessToken
            } else {
                Timber.d("Execute refresh auth token.")
                refreshToken()
            }

            if (updatedAccessToken == null) {
                expireAuthorization()
                return null
            }

            return response.request.newBuilder()
                .removeHeader("Authorization")
                .header("Authorization", "Bearer $updatedAccessToken")
                .build()
        }
    }

    private fun refreshToken(): String? = runBlocking {
        var retryCount = 0
        while (retryCount < MAX_RETRY_COUNT) {
            try {
                return@runBlocking authRepository.refreshAuthToken()
            } catch (e: Exception) {
                // TODO: If the exception represents an expired refresh token, do not rety.
                Timber.w(e)
                retryCount++
            }
        }
        null
    }

    private fun expireAuthorization() {
        Timber.d("Expire authorization!")
        runBlocking(appDispatchers.main) {
            authRepository.logout()
            unauthorizedHandler.handleUnauthorized()
        }
    }

    companion object {
        private const val MAX_RETRY_COUNT = 3
    }
}

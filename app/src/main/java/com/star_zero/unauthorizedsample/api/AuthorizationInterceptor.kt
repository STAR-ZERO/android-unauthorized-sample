package com.star_zero.unauthorizedsample.api

import com.star_zero.unauthorizedsample.data.preference.AppPreference
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationInterceptor @Inject constructor(
    private val appPreference: AppPreference
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .header("Authorization", "Bearer ${appPreference.accessToken}")
            .build()
        return chain.proceed(newRequest)
    }
}

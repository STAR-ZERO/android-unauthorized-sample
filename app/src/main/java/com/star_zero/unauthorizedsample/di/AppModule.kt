package com.star_zero.unauthorizedsample.di

import android.content.Context
import com.star_zero.unauthorizedsample.App
import com.star_zero.unauthorizedsample.api.AccessTokenAuthenticator
import com.star_zero.unauthorizedsample.api.AuthService
import com.star_zero.unauthorizedsample.api.AuthorizationInterceptor
import com.star_zero.unauthorizedsample.api.SampleService
import com.star_zero.unauthorizedsample.data.repository.AuthDataRepository
import com.star_zero.unauthorizedsample.data.repository.AuthRepository
import com.star_zero.unauthorizedsample.data.repository.SampleDataRepository
import com.star_zero.unauthorizedsample.data.repository.SampleRepository
import com.star_zero.unauthorizedsample.util.AppDispatchers
import com.star_zero.unauthorizedsample.util.DefaultAppDispatchers
import com.star_zero.unauthorizedsample.util.UnauthorizedHandler
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Singleton
    @Binds
    abstract fun bindsAppDispatcher(appDispatchers: DefaultAppDispatchers): AppDispatchers

    @Singleton
    @Binds
    abstract fun bindsAuthRepository(repository: AuthDataRepository): AuthRepository

    @Singleton
    @Binds
    abstract fun bindsSampleRepository(repository: SampleDataRepository): SampleRepository

    companion object {

        // TODO
        private const val BASE_URL = "http://localhost:4567/"

        @Singleton
        @Provides
        fun provideUnauthorizedHandler(@ApplicationContext context: Context): UnauthorizedHandler {
            return context as App
        }

        @Singleton
        @Provides
        fun provideAuthService(): AuthService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()

            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            return retrofit.create(AuthService::class.java)
        }

        @Singleton
        @Provides
        fun provideSampleService(
            accessTokenAuthenticator: AccessTokenAuthenticator,
            authorizationInterceptor: AuthorizationInterceptor,
        ): SampleService {
            val okHttpClient = OkHttpClient.Builder()
                .authenticator(accessTokenAuthenticator)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .addInterceptor(authorizationInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            return retrofit.create(SampleService::class.java)
        }
    }
}

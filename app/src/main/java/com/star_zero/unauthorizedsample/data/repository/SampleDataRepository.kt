package com.star_zero.unauthorizedsample.data.repository

import com.star_zero.unauthorizedsample.api.SampleService
import com.star_zero.unauthorizedsample.data.model.User
import com.star_zero.unauthorizedsample.uitl.AppDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SampleDataRepository @Inject constructor(
    private val sampleService: SampleService,
    private val appDispatchers: AppDispatchers,
) : SampleRepository {

    override suspend fun getUser(): User = withContext(appDispatchers.io) {
        sampleService.getUser()
    }
}

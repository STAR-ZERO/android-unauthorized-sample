package com.star_zero.unauthorizedsample.data.repository

import com.star_zero.unauthorizedsample.data.model.User

interface SampleRepository {
    suspend fun getUser(): User
}

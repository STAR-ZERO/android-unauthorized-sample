package com.star_zero.unauthorizedsample.util

import androidx.annotation.MainThread

interface UnauthorizedHandler {
    @MainThread
    fun handleUnauthorized()
}

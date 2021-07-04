package com.star_zero.unauthorizedsample.uitl

import androidx.annotation.MainThread

interface UnauthorizedHandler {
    @MainThread
    fun handleUnauthorized()
}

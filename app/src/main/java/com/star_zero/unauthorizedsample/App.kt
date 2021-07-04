package com.star_zero.unauthorizedsample

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.annotation.MainThread
import com.star_zero.unauthorizedsample.data.repository.AuthRepository
import com.star_zero.unauthorizedsample.ui.login.LoginActivity
import com.star_zero.unauthorizedsample.util.UnauthorizedHandler
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), UnauthorizedHandler {

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }

    @MainThread
    override fun handleUnauthorized() {
        Toast.makeText(this, "Expired session!", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
    }
}

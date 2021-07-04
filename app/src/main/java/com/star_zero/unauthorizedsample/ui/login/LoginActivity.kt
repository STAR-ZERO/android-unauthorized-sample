package com.star_zero.unauthorizedsample.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.star_zero.unauthorizedsample.R
import com.star_zero.unauthorizedsample.api.AuthService
import com.star_zero.unauthorizedsample.data.preference.AppPreference
import com.star_zero.unauthorizedsample.data.repository.AuthRepository
import com.star_zero.unauthorizedsample.ui.sample.SampleActivity
import com.star_zero.unauthorizedsample.util.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity: AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    lateinit var appPreference: AppPreference

    @Inject
    lateinit var authService: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activiyt_login)

        if (viewModel.isLoggedIn()) {
            moveNext()
            return
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect {
                    handleEvent(it)
                }
            }
        }

        findViewById<Button>(R.id.button).setOnClickListener {
            viewModel.login()
        }
    }

    private fun handleEvent(event: LoginViewModel.Event) {
        when (event) {
            is LoginViewModel.Event.Success -> {
                moveNext()
            }
            is LoginViewModel.Event.Error -> {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }.exhaustive
    }

    private fun moveNext() {
        val intent = Intent(this, SampleActivity::class.java)
        startActivity(intent)
        finish()
    }
}

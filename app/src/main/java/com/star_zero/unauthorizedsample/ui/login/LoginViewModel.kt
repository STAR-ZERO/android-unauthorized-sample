package com.star_zero.unauthorizedsample.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.star_zero.unauthorizedsample.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _event = Channel<Event>()
    val event = _event.receiveAsFlow()

    fun isLoggedIn() = authRepository.isLoggedIn()

    fun login() {
        viewModelScope.launch {
            try {
                authRepository.login()
                _event.send(Event.Success)
            } catch (e: Exception) {
                Timber.w(e)
                _event.send(Event.Error)
            }
        }
    }

    sealed interface Event {
        object Success : Event
        object Error : Event
    }
}

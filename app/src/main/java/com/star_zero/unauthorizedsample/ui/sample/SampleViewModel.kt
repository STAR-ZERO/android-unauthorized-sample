package com.star_zero.unauthorizedsample.ui.sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.star_zero.unauthorizedsample.data.repository.SampleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val sampleRepository: SampleRepository
): ViewModel() {

    fun getUser() {
        viewModelScope.launch {
            try {
                val user = sampleRepository.getUser()
                Timber.d("User: $user")
            } catch (e: Exception) {
                Timber.w(e)
            }
        }
    }
}

package com.star_zero.unauthorizedsample.data.preference

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreference @Inject constructor(
    @ApplicationContext
    private val context: Context
) {

    private val preference = PreferenceManager.getDefaultSharedPreferences(context)

    var accessToken: String?
        get() {
            return preference.getString(KEY_ACCESS_TOKEN, null)
        }
        set(value) {
            preference.edit {
                putString(KEY_ACCESS_TOKEN, value)
            }
        }

    var refreshToken: String?
        get() {
            return preference.getString(KEY_REFRESH_TOKEN, null)
        }
        set(value) {
            preference.edit {
                putString(KEY_REFRESH_TOKEN, value)
            }
        }

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
    }
}

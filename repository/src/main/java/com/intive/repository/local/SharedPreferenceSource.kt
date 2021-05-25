package com.intive.repository.local

import android.content.SharedPreferences
import com.intive.repository.local.SharedPreferencesKeys.LOGIN_KEY

class SharedPreferenceSource(private val prefs: SharedPreferences) {

    fun isUserLogged(): Boolean {
        return prefs.contains(LOGIN_KEY)
    }

    fun loginUser(login: String) {
        prefs.edit().putString(LOGIN_KEY, login).apply()
    }

    fun logoutUser() {
        prefs.edit().clear().apply()
    }

    fun getUserLoginOrNull(): String? {
        return prefs.getString(LOGIN_KEY, null)
    }
}

object SharedPreferencesKeys {
    const val LOGIN_KEY = "login_key"
}
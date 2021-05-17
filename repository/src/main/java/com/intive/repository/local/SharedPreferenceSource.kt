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

}

object SharedPreferencesKeys {
    const val LOGIN_KEY = "login_key"
}
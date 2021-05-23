package com.intive.repository.local

class LocalRepository(
    private val sharedPreferenceSource: SharedPreferenceSource
) {

    fun isUserLogged(): Boolean {
        return sharedPreferenceSource.isUserLogged()
    }

    fun loginUser(login: String) {
        sharedPreferenceSource.loginUser(login)
    }

    fun logoutUser() {
        sharedPreferenceSource.logoutUser()
    }

    fun getUserLoginOrNull(): String? {
        return sharedPreferenceSource.getUserLoginOrNull()
    }

    fun enableCaching() {
        sharedPreferenceSource.enableCaching()
    }

    fun isCachingEnabled(): Boolean {
        return sharedPreferenceSource.isCachingEnabled()
    }
}
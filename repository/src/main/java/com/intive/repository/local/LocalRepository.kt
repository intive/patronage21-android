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
}
package com.intive.shared

import androidx.fragment.app.FragmentActivity

fun FragmentActivity.forceRestart() {
    this.finish()
    startActivity(this.intent)
}
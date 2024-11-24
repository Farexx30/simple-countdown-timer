package com.example.timer.utils

import android.text.Editable
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

fun Editable.toInt(): Int = this.toString().trim().toIntOrNull() ?: 0

fun FragmentTransaction.commitWhenPossible(lifecycle: Lifecycle) {
    lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onStart(owner: LifecycleOwner) {
            super.onStart(owner)

            lifecycle.removeObserver(this)
            commit()
        }
    })
}
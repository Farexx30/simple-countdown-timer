package com.example.timer.utils

import android.text.Editable

fun Editable.toInt(): Int = this.toString().trim().toIntOrNull() ?: 0
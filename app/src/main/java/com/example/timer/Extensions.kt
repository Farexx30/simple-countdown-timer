package com.example.timer

import android.widget.EditText

fun EditText.toInt(): Int = this.text.toString().trim().toIntOrNull() ?: 0
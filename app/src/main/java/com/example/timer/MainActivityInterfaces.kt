package com.example.timer

interface IMainActivityOnTimerFinish {
    fun onFinish()
}

interface IMainActivityChangeEnablement {
    fun changeStartEnablement(enablement: Boolean)
}
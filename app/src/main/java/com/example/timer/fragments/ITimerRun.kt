package com.example.timer.fragments

import com.example.timer.data.TimerData

interface ITimerRun {
    fun updateTimeLeft(currentTime: TimerData)
}
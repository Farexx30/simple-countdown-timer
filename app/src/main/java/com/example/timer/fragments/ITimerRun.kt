package com.example.timer.fragments

import com.example.timer.TimerData

interface ITimerRun {
    fun updateTimeLeft(currentTime: TimerData)
}
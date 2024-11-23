package com.example.timer

import android.os.SystemClock
import com.example.timer.fragments.ITimerRun
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class CountdownTimer(
    private val mainActivity: IMainActivityOnTimerFinish,
    private val timerRunFragment: ITimerRun,
    timerData: TimerData
)  {
    private val scope = CoroutineScope(Dispatchers.Main)
    private var tensOfMinutes = timerData.tensOfMinutes.toInt()
    private var unitsOfMinutes = timerData.unitsOfMinutes.toInt()
    private var tensOfSeconds = timerData.tensOfSeconds.toInt()
    private var unitsOfSeconds = timerData.unitsOfSeconds.toInt() + 1
    private val startTimeInMilliseconds: Long = ((tensOfMinutes * 10 + unitsOfMinutes) * 60
        + tensOfSeconds * 10 + unitsOfSeconds - 1) * 1000L
    private var timeLeftInMilliseconds: Long = startTimeInMilliseconds
    private var currentTime: TimerData = timerData.copy()
    private var currentSecondTimeInMilliseconds = 0L
    private var remainingDelayInMilliseconds = 0L
    private lateinit var countDownJob: Job

    fun startTimer() {
        currentSecondTimeInMilliseconds = SystemClock.elapsedRealtime() - (1000L - remainingDelayInMilliseconds)

        countDownJob = scope.launch {
            while (timeLeftInMilliseconds > 0 || remainingDelayInMilliseconds > 0L) {
                if (remainingDelayInMilliseconds > 0)
                    delay(remainingDelayInMilliseconds)

                remainingDelayInMilliseconds = 0L

                if (timeLeftInMilliseconds > 0)
                {
                    timeLeftInMilliseconds -= 1000

                    if (unitsOfSeconds > 0) {
                        unitsOfSeconds--
                    }
                    else {
                        unitsOfSeconds = 9

                        if (tensOfSeconds > 0) {
                            tensOfSeconds--
                        }
                        else {
                            tensOfSeconds = 5

                            if (unitsOfMinutes > 0) {
                                unitsOfMinutes--
                            }
                            else {
                                unitsOfMinutes = 9

                                tensOfMinutes--
                            }
                        }
                    }
                    currentTime.tensOfMinutes = tensOfMinutes.toString()
                    currentTime.unitsOfMinutes = unitsOfMinutes.toString()
                    currentTime.tensOfSeconds = tensOfSeconds.toString()
                    currentTime.unitsOfSeconds = unitsOfSeconds.toString()
                    updateUIWithTimeLeft(currentTime)

                    currentSecondTimeInMilliseconds = SystemClock.elapsedRealtime()
                }
                delay(1000L)
            }
            onFinish()
        }
    }

    fun pauseTimer() {
        countDownJob.cancel()
        val currentPauseTimeInMilliseconds = SystemClock.elapsedRealtime()
        remainingDelayInMilliseconds = 1000L - (currentPauseTimeInMilliseconds - currentSecondTimeInMilliseconds)
    }

    fun cancelTimer() {
        countDownJob.cancel()
        timeLeftInMilliseconds = startTimeInMilliseconds
        remainingDelayInMilliseconds = 0L
    }

    private fun onFinish() {
        timeLeftInMilliseconds = startTimeInMilliseconds
        remainingDelayInMilliseconds = 0L

        mainActivity.onFinish()
    }

    private fun updateUIWithTimeLeft(currentTime: TimerData) {
        timerRunFragment.updateTimeLeft(currentTime)
    }
}
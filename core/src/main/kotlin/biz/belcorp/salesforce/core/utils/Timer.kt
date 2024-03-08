package biz.belcorp.salesforce.core.utils

import android.os.CountDownTimer

class Timer(
    val seconds: Long
) {

    var onTick: ((secondsRemaining: Long) -> Unit)? = null

    var onFinish: (() -> Unit)? = null

    private val timer = object : CountDownTimer((seconds.times(ONE_THOUSAND)), ONE_THOUSAND) {

        override fun onTick(millisUntilFinished: Long) {
            onTick?.invoke(millisUntilFinished / ONE_THOUSAND)
        }

        override fun onFinish() {
            onFinish?.invoke()
        }

    }

    fun start() {
        timer.start()
    }

    fun cancel() {
        timer.cancel()
    }

    companion object {
        private const val ONE_THOUSAND: Long = 1000
    }

}

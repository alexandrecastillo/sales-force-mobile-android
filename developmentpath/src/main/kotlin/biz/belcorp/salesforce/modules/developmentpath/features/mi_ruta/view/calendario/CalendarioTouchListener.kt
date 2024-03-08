package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.calendario

import android.view.MotionEvent
import android.view.View

class CalendarioTouchListener(val listener: OnMovementListener) : View.OnTouchListener {

    companion object {
        private const val SWIPE_THRESHOLD = 20F
    }

    private var actionDownY = 0F
    private var actionUpY = 0F

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        val action = event.actionMasked

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                actionDownY = event.y
                return false
            }
            MotionEvent.ACTION_MOVE -> {
                // Bloquear el scroll
                return true
            }
            MotionEvent.ACTION_UP -> {
                actionUpY = event.y

                return detectarTipoMovimiento()
            }
            else -> return false
        }
    }

    private fun detectarTipoMovimiento(): Boolean {
        return when {
            movimientoInsuficiente() -> false
            movimientoHaciaAbajo() -> {
                listener.onScrollDown()
                true
            }
            else -> {
                listener.onScrollUp()
                true
            }
        }
    }

    private fun movimientoHaciaAbajo() = actionDownY - actionUpY < 0

    private fun movimientoInsuficiente() = Math.abs(actionDownY - actionUpY) < SWIPE_THRESHOLD

    interface OnMovementListener {
        fun onScrollUp()
        fun onScrollDown()
    }

}

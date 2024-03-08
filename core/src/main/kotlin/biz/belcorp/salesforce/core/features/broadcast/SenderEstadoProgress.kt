package biz.belcorp.salesforce.core.features.broadcast

import android.content.Context
import android.content.Intent
import biz.belcorp.salesforce.core.constants.Constant

class SenderEstadoProgress(private val context: Context) {

    companion object {

        const val ESTADO = "ESTADO"

    }

    private fun cambiarEstadoProgress(mostrar: Boolean) {
        val intent = Intent(Constant.BROADCAST_ESTADO_PROGRESS)
        intent.putExtra(ESTADO, mostrar)
        context.sendBroadcast(intent)
    }

    fun mostrarProgress() {
        cambiarEstadoProgress(true)
    }

    fun ocultarProgress() {
        cambiarEstadoProgress(false)
    }

}

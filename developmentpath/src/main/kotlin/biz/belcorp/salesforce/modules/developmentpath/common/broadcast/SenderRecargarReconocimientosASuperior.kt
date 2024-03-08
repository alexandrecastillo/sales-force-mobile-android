package biz.belcorp.salesforce.modules.developmentpath.common.broadcast

import android.content.Context
import android.content.Intent
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant

class SenderRecargarReconocimientosASuperior(private val context: Context) {

    fun notificarCambio() {
        val intent = Intent(Constant.BROADCAST_CAMBIO_RECONOCIMIENTO_A_SUPERIOR)
        context.sendBroadcast(intent)
    }
}

package biz.belcorp.salesforce.modules.developmentpath.common.broadcast

import android.content.Context
import android.content.Intent
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant

class SenderCambioReconocimiento(private val context: Context) {
    fun notificarCambioEnReconocimiento() {
        val intent = Intent(Constant.BROADCAST_CAMBIO_RECONOCIMIENTO)
        context.sendBroadcast(intent)
    }
}

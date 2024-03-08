package biz.belcorp.salesforce.modules.developmentpath.common.broadcast

import android.content.Context
import android.content.Intent
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant

class SenderRegistroVisitaExitoso(private val context: Context) {

    fun notificarRegistroVisita() {
        val intent = Intent(Constant.BROADCAST_REGISTRO_VISITA)
        context.sendBroadcast(intent)
    }
}

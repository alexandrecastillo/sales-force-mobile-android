package biz.belcorp.salesforce.modules.developmentpath.common.broadcast

import android.content.Context
import android.content.Intent
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant

class SenderCambioAcuerdos(private val context: Context) {

    fun notificar() {
        val intent = Intent(Constant.BROADCAST_CAMBIO_ACUERDOS)
        context.sendBroadcast(intent)
    }
}

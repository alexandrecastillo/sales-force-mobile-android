package biz.belcorp.salesforce.modules.developmentpath.common.broadcast

import android.content.Context
import android.content.Intent
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant

class SenderEventoEditado(private val context: Context) {

    fun notificarEventoEditado() {
        val intent = Intent(Constant.BROADCAST_EVENTO_EDITADO)
        context.sendBroadcast(intent)
    }
}

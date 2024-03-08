package biz.belcorp.salesforce.modules.developmentpath.common.broadcast

import android.content.Context
import android.content.Intent
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant

class SenderRecargarFocos(private val context: Context) {
    fun recargar() {
        val intent = Intent(Constant.BROADCAST_RECARGAR_FOCOS)

        context.sendBroadcast(intent)
    }
}

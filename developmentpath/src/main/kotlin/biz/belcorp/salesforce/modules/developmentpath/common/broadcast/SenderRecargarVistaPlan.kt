package biz.belcorp.salesforce.modules.developmentpath.common.broadcast

import android.content.Context
import android.content.Intent
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant

class SenderRecargarVistaPlan (private val context: Context) {

    fun recargarVistaPlan() {
        val intent = Intent(Constant.BROADCAST_RECARGAR_VISTA_PLAN)
        context.sendBroadcast(intent)
    }
}

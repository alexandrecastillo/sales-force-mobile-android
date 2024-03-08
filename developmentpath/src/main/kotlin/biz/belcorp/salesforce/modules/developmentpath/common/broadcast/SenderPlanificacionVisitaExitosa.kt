package biz.belcorp.salesforce.modules.developmentpath.common.broadcast

import android.content.Context
import android.content.Intent
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant

class SenderPlanificacionVisitaExitosa(private val context: Context) {

    fun notificarExitoPlanificar() {
        val intent = Intent(Constant.BROADCAST_PLANIFICAR_VISITA)
        context.sendBroadcast(intent)
    }
}

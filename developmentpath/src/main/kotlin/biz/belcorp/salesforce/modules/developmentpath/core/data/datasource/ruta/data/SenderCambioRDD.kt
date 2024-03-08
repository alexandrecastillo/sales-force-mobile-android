package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.ruta.data

import android.content.Context
import android.content.Intent
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant

class SenderCambioRDD(private val context: Context) {

    fun notificarCambioEnPlanificacion() {
        val intent = Intent(Constant.BROADCAST_CAMBIO_PLANIFICACION_RDD)
        context.sendBroadcast(intent)
    }
}

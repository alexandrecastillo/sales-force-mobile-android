package biz.belcorp.salesforce.modules.developmentpath.common.broadcast

import android.content.Context
import android.content.Intent
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant

class SenderValorIndicador(private val context: Context) {

    fun enviarNuevosValores(visitadas: Int, planificadas: Int) {
        val intent = Intent(Constant.BROADCAST_NUEVOS_INDICADORES_RDD)
        intent.putExtra(Constant.INDICADOR_TOTAL_VISITADAS, visitadas)
        intent.putExtra(Constant.INDICADOR_TOTAL_PLANIFICADAS, planificadas)
        context.sendBroadcast(intent)
    }
}

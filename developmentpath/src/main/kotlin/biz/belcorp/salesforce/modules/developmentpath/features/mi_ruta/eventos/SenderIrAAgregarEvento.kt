package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos

import android.content.Context
import android.content.Intent
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import java.util.*

class SenderIrAAgregarEvento(private val context: Context) {

    fun ir(fecha: Date) {
        val intent = Intent(Constant.BROADCAST_IR_A_AGREGAR_EVENTO)
        intent.putExtra(Constant.BROADCAST_IR_A_AGREGAR_EVENTO_FECHA_PARAM, fecha)

        context.sendBroadcast(intent)
    }
}

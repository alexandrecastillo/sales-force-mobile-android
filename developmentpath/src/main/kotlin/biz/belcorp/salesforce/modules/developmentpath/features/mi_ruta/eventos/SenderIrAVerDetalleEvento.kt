package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos

import android.content.Context
import android.content.Intent
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant

class SenderIrAVerDetalleEvento(private val context: Context) {

    fun ir(eventoId: Long) {
        val intent = Intent(Constant.BROADCAST_IR_A_VER_DETALLE_EVENTO)
        intent.putExtra(Constant.BROADCAST_IR_A_VER_DETALLE_EVENTO_ID_PARAM, eventoId)

        context.sendBroadcast(intent)
    }
}

package biz.belcorp.salesforce.modules.developmentpath.common.broadcast

import android.content.Context
import android.content.Intent
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.BROADCAST_ITEM_POSITION_EXTRA

class SenderRecargarHabilidadesEquipo(private val context: Context) {
    fun recargar(itemPosition: Int) {
        val intent = Intent(Constant.BROADCAST_RECARGAR_HABILIDADES_EQUIPO)
        intent.putExtra(BROADCAST_ITEM_POSITION_EXTRA, itemPosition)
        context.sendBroadcast(intent)
    }
}

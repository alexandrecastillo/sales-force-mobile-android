package biz.belcorp.salesforce.modules.developmentpath.common.broadcast

import android.content.Context
import android.content.Intent
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant

class SenderRecargarHabilidadesPropias(private val context: Context) {
    fun recargar() {
        val intent = Intent(Constant.BROADCAST_RECARGAR_HABILIDADES_PROPIAS)
        context.sendBroadcast(intent)
    }
}

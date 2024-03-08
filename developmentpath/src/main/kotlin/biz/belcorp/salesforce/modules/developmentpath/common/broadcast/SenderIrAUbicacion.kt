package biz.belcorp.salesforce.modules.developmentpath.common.broadcast

import android.content.Context
import android.content.Intent
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.MenuPersonaModel

class SenderIrAUbicacion(private val context: Context) {

    companion object {
        const val ARG_MODEL = "MODEL"
    }

    fun irAUbicacion(modelo: MenuPersonaModel) {
        val intent = Intent(Constant.BROADCAST_IR_A_UBICAR_PERSONA)
        intent.putExtra(ARG_MODEL, modelo)
        context.sendBroadcast(intent)
    }
}

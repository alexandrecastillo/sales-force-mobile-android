package biz.belcorp.salesforce.modules.developmentpath.common.broadcast

import android.content.Context
import android.content.Intent
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant

class SenderVisibilidadMenu(private val context: Context) {

    fun ocultarMenuPrincipal() {
        val intent = Intent(Constant.BROADCAST_VISIBILIDAD_MENU)
        intent.putExtra(Constant.MENU_ACCION, Constant.MENU_OCULTAR)
        context.sendBroadcast(intent)
    }

    fun mostrarMenuPrincipal() {
        val intent = Intent(Constant.BROADCAST_VISIBILIDAD_MENU)
        intent.putExtra(Constant.MENU_ACCION, Constant.MENU_MOSTRAR)
        context.sendBroadcast(intent)
    }
}

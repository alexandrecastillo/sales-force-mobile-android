package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.broadcast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.utils.agregarUAComoParametros

class SenderClickUaRdd(private val context: Context) {

    fun clickearUa(llaveUA: LlaveUA) {
        val intent = Intent(Constant.BROADCAST_CLICK_SECCION_RDD)
        intent.putExtras(Bundle().agregarUAComoParametros(llaveUA))
        context.sendBroadcast(intent)
    }
}

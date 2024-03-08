package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.sincronizacion

import biz.belcorp.salesforce.modules.developmentpath.common.broadcast.SenderRecargarVistaPlan

class BroadcastEventosHelper (private val senderRecargarVistaPlan: SenderRecargarVistaPlan): BroadcastEventosHandler {
    override fun emitirBroadcastCambioEvento() {
        senderRecargarVistaPlan.recargarVistaPlan()
    }
}

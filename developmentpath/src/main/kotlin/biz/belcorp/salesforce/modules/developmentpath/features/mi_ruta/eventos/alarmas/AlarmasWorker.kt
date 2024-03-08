package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.alarmas

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.Alarma

interface AlarmasWorker {
    fun programar(alarma: Alarma.AlarmaEvento): String
    fun eliminar(id: String)
    fun eliminarTodas()
}

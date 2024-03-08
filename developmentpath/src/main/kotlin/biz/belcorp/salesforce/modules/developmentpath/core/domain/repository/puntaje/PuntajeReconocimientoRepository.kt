package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.puntaje

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.PuntajeReconocimiento

interface PuntajeReconocimientoRepository {
    fun obtenerPuntaje(llaveUA: LlaveUA, campania: String?, rol: Rol): PuntajeReconocimiento
}

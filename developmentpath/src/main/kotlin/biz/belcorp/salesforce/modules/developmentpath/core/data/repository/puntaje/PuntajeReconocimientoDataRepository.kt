package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.puntaje

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.puntaje.data.PuntajeReconocimientoLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.puntaje.PuntajeReconocimientoMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.PuntajeReconocimiento
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.puntaje.PuntajeReconocimientoRepository

class PuntajeReconocimientoDataRepository(
    private val puntajesReconoce: PuntajeReconocimientoLocalDataStore,
    private val mapper: PuntajeReconocimientoMapper
) : PuntajeReconocimientoRepository {

    override fun obtenerPuntaje(
        llaveUA: LlaveUA,
        campania: String?,
        rol: Rol
    ): PuntajeReconocimiento {
        return mapper.puntajeParser(puntajesReconoce.obtenerPuntaje(llaveUA, campania, rol))
    }
}


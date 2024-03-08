package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.tips

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.tips.TipsVisitaDbDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.GrupoTipsVisita
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tips.TipsVisitaRepository

class TipsVisitaDataRepository(private val dbStore: TipsVisitaDbDataStore) : TipsVisitaRepository {

    override fun obtenerTipsDePersona(personaId: Long, rol: Rol): GrupoTipsVisita? {
        return dbStore.obtenerTipsDePersona(personaId, rol)
    }

    override fun obtenerIdCabeceraTip(personaId: Long, rol: Rol): Long? {
        return dbStore.obtenerIdCabeceraTip(personaId, rol)
    }
}

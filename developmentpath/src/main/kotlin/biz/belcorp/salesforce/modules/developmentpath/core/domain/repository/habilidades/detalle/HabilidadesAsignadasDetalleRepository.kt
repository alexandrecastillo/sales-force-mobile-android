package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.detalle

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.DetalleHabilidadesAcompaniamiento
import io.reactivex.Single

interface HabilidadesAsignadasDetalleRepository {
    fun obtener(personaId: Long, rol: Rol, campania: String): Single<List<DetalleHabilidadesAcompaniamiento>>
}

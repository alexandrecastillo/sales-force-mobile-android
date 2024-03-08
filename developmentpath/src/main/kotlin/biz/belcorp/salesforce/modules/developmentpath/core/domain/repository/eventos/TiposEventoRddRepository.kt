package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.eventos

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.TipoEventoRdd

interface TiposEventoRddRepository {
    fun recuperar(rol: Rol): List<TipoEventoRdd>
    fun recuperar(): List<TipoEventoRdd>
}

package biz.belcorp.salesforce.modules.consultants.core.domain.repository.filtros

import biz.belcorp.salesforce.core.domain.entities.search.TipoEstado
import io.reactivex.Observable

interface TipoEstadoRepository {
    val all: Observable<List<TipoEstado>>
}

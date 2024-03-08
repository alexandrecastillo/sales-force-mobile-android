package biz.belcorp.salesforce.modules.consultants.core.domain.repository.filtros

import biz.belcorp.salesforce.core.domain.entities.search.TipoSegmento
import io.reactivex.Observable

interface TipoSegmentoRepository {
    val all: Observable<List<TipoSegmento>>
}

package biz.belcorp.salesforce.modules.consultants.core.domain.repository.filtros

import biz.belcorp.salesforce.modules.consultants.core.domain.entities.TipoSaldo
import io.reactivex.Observable

interface TipoSaldoRepository {
    val all: Observable<List<TipoSaldo>>
}

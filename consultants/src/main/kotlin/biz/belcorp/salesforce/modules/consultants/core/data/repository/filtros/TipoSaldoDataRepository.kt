package biz.belcorp.salesforce.modules.consultants.core.data.repository.filtros

import biz.belcorp.salesforce.modules.consultants.core.domain.entities.TipoSaldo
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.filtros.TipoSaldoRepository
import io.reactivex.Observable

class TipoSaldoDataRepository(
    private val dataStore: TipoSaldoDBDataStore,
    private val dataMapper: TipoSaldoEntityDataMapper
) : TipoSaldoRepository {

    override val all: Observable<List<TipoSaldo>>
        get() {
            return dataStore
                .all
                .map { dataMapper.parse(it) }
        }

}

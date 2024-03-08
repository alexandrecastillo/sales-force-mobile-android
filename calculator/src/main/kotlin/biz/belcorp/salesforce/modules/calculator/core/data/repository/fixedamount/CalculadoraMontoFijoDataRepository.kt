package biz.belcorp.salesforce.modules.calculator.core.data.repository.fixedamount

import biz.belcorp.salesforce.modules.calculator.core.data.repository.calculatingresult.mapper.CalculatingResultEntityDataMapper
import biz.belcorp.salesforce.modules.calculator.core.data.repository.fixedamount.data.CalculadoraMontoFijoDBDataStore
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.fixedamount.CalculadoraMontoFijoRepository
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.CalculadoraMontoFijo
import io.reactivex.Single

class CalculadoraMontoFijoDataRepository
constructor(
    private val dbStore: CalculadoraMontoFijoDBDataStore,
    private val mapper: CalculatingResultEntityDataMapper

) : CalculadoraMontoFijoRepository {
    override fun all(): Single<List<CalculadoraMontoFijo>> =
        dbStore.all().map { mapper.parseCalculadoraMontiFijo(it) }
}

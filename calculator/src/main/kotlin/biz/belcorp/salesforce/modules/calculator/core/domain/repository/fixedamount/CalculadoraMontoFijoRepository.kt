package biz.belcorp.salesforce.modules.calculator.core.domain.repository.fixedamount

import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.CalculadoraMontoFijo
import io.reactivex.Single

interface CalculadoraMontoFijoRepository {
    fun all(): Single<List<CalculadoraMontoFijo>>
}

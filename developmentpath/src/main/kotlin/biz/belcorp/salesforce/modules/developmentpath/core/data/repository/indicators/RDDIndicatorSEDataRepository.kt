package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.indicators

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.indicators.RddIndicatorSEDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.indicators.IndicadorEntityDataMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.indicador.IndicadorRddArgsSE
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.indicadores.SeRDDIndicatorRepository
import io.reactivex.Single

class RDDIndicatorSEDataRepository (private val mapper: IndicadorEntityDataMapper,
                                    private val seRDDIndicatorDataStore: RddIndicatorSEDBDataStore
            )
    : SeRDDIndicatorRepository
{
    override fun getSeRddIndicatorData(uaSegmentId: String): Single<IndicadorRddArgsSE> {
        return seRDDIndicatorDataStore
                .getRDDIndicatorDataForSe(uaSegmentId)
                .map { mapper.parseRddIndicatorDataForSe(it) }
    }
}

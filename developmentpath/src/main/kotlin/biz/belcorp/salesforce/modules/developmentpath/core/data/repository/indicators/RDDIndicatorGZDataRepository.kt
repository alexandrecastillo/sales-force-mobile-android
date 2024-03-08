package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.indicators

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.indicators.RddIndicatorGZDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.indicators.IndicadorEntityDataMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.indicador.IndicadorRddArgsGZ
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.indicadores.GzRDDIndicatorRepository
import io.reactivex.Single

class RDDIndicatorGZDataRepository (private val mapper: IndicadorEntityDataMapper,
                                    private val gzRDDIndicatorDataStore: RddIndicatorGZDBDataStore
            )
    : GzRDDIndicatorRepository
{

    override fun getGzRddIndicatorData(): Single<IndicadorRddArgsGZ> {
        return gzRDDIndicatorDataStore
                .getGzRDDIndicatorData()
                .map { mapper.parseRddIndicatorDataForGz(it) }
    }

    override fun getGzRddIndicatorDataBySegment(uaSegmentId: String): Single<IndicadorRddArgsGZ> {
        return gzRDDIndicatorDataStore
                .getGzRddIndicatorDataBySegment(uaSegmentId)
                .map { mapper.parseRddIndicatorDataForGz(it) }
    }
}

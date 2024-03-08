package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.indicators

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.indicators.RddIndicatorGRDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.indicators.IndicadorEntityDataMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.indicador.IndicadorRddArgsGR
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.indicadores.GrRDDIndicatorRepository
import io.reactivex.Single

class RDDIndicatorGRDataRepository (private val mapper: IndicadorEntityDataMapper,
                                    private val grRDDIndicatorDataStore: RddIndicatorGRDBDataStore
            )
    : GrRDDIndicatorRepository {


    override fun getItsRDDIndicatorDataForGr(): Single<IndicadorRddArgsGR> {
        return grRDDIndicatorDataStore
                .getRDDIndicatorDataForGr()
                .map { mapper.parseRddIndicatorDataForGr(it) }
    }

    override fun getGrRDDIndicatorDataForGr(uaSegmentId: String): Single<IndicadorRddArgsGR> {
        return grRDDIndicatorDataStore
                .getRDDIndicatorDataForDv(uaSegmentId)
                .map { mapper.parseRddIndicatorDataForGr(it) }
    }

    override fun getGrRDDIndicatorDataForGrAsDv(uaSegmentId: String): Single<IndicadorRddArgsGR> {
        return grRDDIndicatorDataStore
                .getRDDIndicatorDataForGrAsDv(uaSegmentId)
                .map { mapper.parseRddIndicatorDataForGr(it) }
    }
}

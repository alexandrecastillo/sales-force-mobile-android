package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.indicators

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.indicators.RddIndicatorDVCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.indicators.RddIndicatorDVDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.indicators.IndicadorEntityDataMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.indicador.IndicadorRddArgsDV
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.VisitasPropias
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.indicadores.DvRDDIndicatorRepository
import io.reactivex.Single

class RDDIndicatorDVDataRepository (private val mapper: IndicadorEntityDataMapper,
                                    private val dvRDDIndicatorDataStore: RddIndicatorDVDBDataStore,
                                    private val dvRddIndicatorDVCloudDataStore: RddIndicatorDVCloudDataStore
)
    : DvRDDIndicatorRepository {

    override fun obtenerIndicadorVisitasPropias():
            Single<VisitasPropias> {
        return dvRDDIndicatorDataStore.obtenerRDDIndicadorVisitasPropias()
    }


    override fun obtenerIndicadorVisitasEquipo(campania: String,
                                               ua: String): Single<IndicadorRddArgsDV> {
        return dvRddIndicatorDVCloudDataStore.obtenerIndicadores(campania, ua)
                .flatMap {
                    dvRDDIndicatorDataStore
                            .guardarRddIndicadorVisitasEquipo(it)
                            .andThen(dvRDDIndicatorDataStore.obtenerRDDIndicadorVisitasEquipo())
                }.onErrorResumeNext {
                    dvRDDIndicatorDataStore.obtenerRDDIndicadorVisitasEquipo()
                }.map {
                    mapper.parseRddIndicatorDataForDv(it)
                }
    }
}

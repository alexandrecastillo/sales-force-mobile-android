package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.indicators.di

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.indicators.RddIndicatorDVCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.indicators.RddIndicatorDVDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.indicators.IndicadorEntityDataMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.indicators.RDDIndicatorDVDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.indicators.RDDIndicatorGRDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.indicators.RDDIndicatorGZDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.indicators.RDDIndicatorSEDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.indicadores.DvRDDIndicatorRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.indicadores.GrRDDIndicatorRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.indicadores.GzRDDIndicatorRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.indicadores.SeRDDIndicatorRepository
import org.koin.dsl.module

internal val indicatorsModule = module {
    factory { IndicadorEntityDataMapper() }
    factory { RddIndicatorDVDBDataStore() }
    factory { RddIndicatorDVCloudDataStore(get(), get()) }
    factory<DvRDDIndicatorRepository> { RDDIndicatorDVDataRepository(get(),get(),get()) }
    factory<GrRDDIndicatorRepository> { RDDIndicatorGRDataRepository(get(),get()) }
    factory<GzRDDIndicatorRepository> { RDDIndicatorGZDataRepository(get(),get()) }
    factory<SeRDDIndicatorRepository> { RDDIndicatorSEDataRepository(get(),get()) }
}

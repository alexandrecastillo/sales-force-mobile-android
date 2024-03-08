package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.observaciones.di

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.observaciones.ObservacionVisitaDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.observaciones.ObservacionVisitaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.observaciones.ObservacionVisitaDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.observaciones.ObservacionVisitaRepository
import org.koin.dsl.module

internal val observacionesModule = module {
    factory { ObservacionVisitaMapper() }
    factory { ObservacionVisitaDBDataStore(mapper = get()) }
    factory<ObservacionVisitaRepository> { ObservacionVisitaDataRepository(dbStore = get()) }
}

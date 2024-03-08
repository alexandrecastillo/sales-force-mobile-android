package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.visitas.di

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.visitas.VisitaDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.visitas.VisitasPorFechaDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.visitas.VisitasPorFechaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.visitas.VisitasPorFechasMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.visitas.VisitasPorFechaDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.visitas.VisitasPorFechaRepository
import org.koin.dsl.module

internal val visitasModule = module {
    factory { VisitasPorFechaMapper() }
    factory { VisitaDBDataStore(sender = get()) }
    factory { VisitasPorFechasMapper() }
    factory { VisitasPorFechaDBDataStore(visitasPorFechasMapper = get()) }
    factory<VisitasPorFechaRepository> { VisitasPorFechaDataRepository(dbStore = get()) }
}

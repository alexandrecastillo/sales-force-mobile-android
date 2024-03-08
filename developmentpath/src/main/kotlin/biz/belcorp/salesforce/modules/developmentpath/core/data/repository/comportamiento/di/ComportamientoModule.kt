package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.comportamiento.di

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.comportamientos.ComportamientoDetallePorcentajeDbStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.comportamientos.ComportamientosDbStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.comportamientos.ComportamientoDetallePorcentajeMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.comportamientos.ComportamientoMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.comportamiento.ComportamientoDetallePorcentajeDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.comportamiento.ComportamientosDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.comportamientos.ComportamientoDetallePorcentajeRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.comportamientos.ComportamientosRepository
import org.koin.dsl.module

internal val comportamientoModule = module {
    factory { ComportamientoMapper() }
    factory { ComportamientosDbStore() }
    factory<ComportamientosRepository> { ComportamientosDataRepository(get(), get()) }

    factory { ComportamientoDetallePorcentajeDbStore() }
    factory { ComportamientoDetallePorcentajeMapper() }
    factory<ComportamientoDetallePorcentajeRepository> {
        ComportamientoDetallePorcentajeDataRepository(dbStore = get(), mapper = get())
    }
}

package biz.belcorp.salesforce.modules.consultants.core.data.repository.filtros.di

import biz.belcorp.salesforce.modules.consultants.core.data.repository.filtros.*
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.filtros.*
import org.koin.dsl.module

val filtrosModule = module {

    factory { TipoEstadoEntityDataMapper() }
    factory { TipoPedidoEntityDataMapper() }
    factory { TipoSaldoEntityDataMapper() }
    factory { TipoSegmentoEntityDataMapper() }

    factory { TipoEstadoDBDataStore() }
    factory { TipoPedidoDBDataStore() }
    factory { TipoSaldoDBDataStore() }
    factory { TipoSegmentoDBDataStore() }

    factory<TipoEstadoRepository> { TipoEstadoDataRepository(get(), get()) }
    factory<TipoPedidoRepository> { TipoPedidoDataRepository(get(), get()) }
    factory<TipoSaldoRepository> { TipoSaldoDataRepository(get(), get()) }
    factory<TipoSegmentoRepository> { TipoSegmentoDataRepository(get(), get()) }

}

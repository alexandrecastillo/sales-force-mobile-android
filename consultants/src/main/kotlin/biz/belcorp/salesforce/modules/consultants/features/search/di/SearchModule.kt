package biz.belcorp.salesforce.modules.consultants.features.search.di

import biz.belcorp.salesforce.modules.consultants.features.search.BusquedaConsultoraPresenter
import biz.belcorp.salesforce.modules.consultants.features.search.BusquedaConsultoraView
import biz.belcorp.salesforce.modules.consultants.features.search.mappers.*
import biz.belcorp.salesforce.modules.consultants.features.searchunified.ConsultantViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val searchModule = module {

    factory { SeccionModelDataMapper() }
    factory { TipoEstadoModelDataMapper() }
    factory { TipoPedidoModelDataMapper() }
    factory { TipoSaldoModelDataMapper() }
    factory { TipoSegmentoModelDataMapper() }
    factory { FiltrosBusquedaMapper(get(), get(), get(), get()) }

    factory { (view: BusquedaConsultoraView) ->
        BusquedaConsultoraPresenter(view, get(), get(), get(), get(), get(), get(), get())
    }

    viewModel { ConsultantViewModel(get(), get(), get()) }
}

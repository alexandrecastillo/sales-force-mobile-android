package biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.di

import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.ConsolidadoPresenter
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.mappers.CampaignModelDataMapper
import org.koin.dsl.module

val consolidadoListadoModule = module {

    factory { CampaignModelDataMapper() }

    factory {
        ConsolidadoPresenter(
            get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()
        )
    }


}

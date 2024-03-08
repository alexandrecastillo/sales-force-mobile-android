package biz.belcorp.salesforce.modules.consultants.features.list.di

import biz.belcorp.salesforce.modules.consultants.features.list.ConsultantsListPresenter
import biz.belcorp.salesforce.modules.consultants.features.list.adapters.BeautyConsultantAdapter
import biz.belcorp.salesforce.modules.consultants.features.list.mappers.ConsultoraModelDataMapper
import biz.belcorp.salesforce.modules.consultants.features.list.utils.GridBuilder
import org.koin.dsl.module


val listModule = module {

    factory { BeautyConsultantAdapter(get()) }

    factory { ConsultoraModelDataMapper() }

    factory { GridBuilder(get()) }

    factory { params ->
        ConsultantsListPresenter(params[0], get(), get(), get(), get(), get())
    }

}

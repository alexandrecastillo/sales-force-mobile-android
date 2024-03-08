package biz.belcorp.salesforce.core.domain.usecases.session.di

import biz.belcorp.salesforce.core.domain.usecases.saleforcestatus.GetSaleForceStatusUseCase
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import org.koin.dsl.module

internal val sessionModule = module {

    factory { ObtenerSesionUseCase(get()) }
    factory { GetSaleForceStatusUseCase(get(), get(), get()) }

}

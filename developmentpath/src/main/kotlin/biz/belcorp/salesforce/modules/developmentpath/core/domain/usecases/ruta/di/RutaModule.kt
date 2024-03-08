package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta.DefinidorDeRutaPropiaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta.GetConsultantsUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta.IrAMiRutaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta.RddUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta.RecuperarResultadoVisitasUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta.RecuperarRutaUseCase
import org.koin.dsl.module

internal val rddModule = module {
    factory {
        RddUseCase(
            get(), get(), get(), get(), get(), get(), get(), get(),
            get(), get(), get(), get(), get(), get(), get()
        )
    }
    factory { DefinidorDeRutaPropiaUseCase(get(), get(), get(), get()) }
    factory { RecuperarRutaUseCase(get(), get(), get()) }
    factory { IrAMiRutaUseCase(get(), get(), get(), get(), get(), get(), get(), get()) }
    factory { RecuperarResultadoVisitasUseCase(get(), get(), get(), get(), get(), get()) }
    factory { GetConsultantsUseCase(get(), get(), get(), get()) }
}

package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cobranza.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cobranza.CobranzaYGananciaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cobranza.ObtenerDatosCobranzaCampaniaAnteriorUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cobranza.ObtenerDatosCobranzaUseCase
import org.koin.dsl.module

internal val cobranzaModule = module {
    factory { ObtenerDatosCobranzaCampaniaAnteriorUseCase(get(), get(), get(), get()) }
    factory { CobranzaYGananciaUseCase(get(), get(), get(), get()) }
    factory { ObtenerDatosCobranzaUseCase(get(), get(), get()) }
}

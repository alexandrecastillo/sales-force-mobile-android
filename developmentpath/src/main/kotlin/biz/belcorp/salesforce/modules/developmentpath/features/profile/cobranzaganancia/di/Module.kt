package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaganancia.di

import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.cobranza.DatosCobranzaContract
import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.cobranza.DatosCobranzaMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.cobranza.DatosCobranzaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaganancia.CobranzaGananciaMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaganancia.CobranzaGananciaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaganancia.DatosCobranzaGananciaContract
import org.koin.dsl.module

val datosCobranzaGanaciaModule = module {
    factory { CobranzaGananciaMapper() }
    factory { DatosCobranzaMapper() }

    factory { params ->
        CobranzaGananciaPresenter(view = params[0], useCase = get(), mapper = get())
    }
    factory<DatosCobranzaContract.Presenter> { params ->
        DatosCobranzaPresenter(view = params[0], useCase = get(), mapper = get())
    }
}

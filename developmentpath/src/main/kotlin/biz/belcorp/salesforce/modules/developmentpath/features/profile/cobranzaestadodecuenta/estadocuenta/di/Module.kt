package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.estadocuenta.di

import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.estadocuenta.DatosEstadoCuentaContract
import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.estadocuenta.DatosEstadoCuentaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.estadocuenta.EstadoCuentaModelMapper
import org.koin.dsl.module

val datosEstadoCuentaModule = module {

    factory { EstadoCuentaModelMapper() }

    factory<DatosEstadoCuentaContract.Presenter> { params ->
        DatosEstadoCuentaPresenter(view = params[0], useCase = get(), mapper = get())
    }
}

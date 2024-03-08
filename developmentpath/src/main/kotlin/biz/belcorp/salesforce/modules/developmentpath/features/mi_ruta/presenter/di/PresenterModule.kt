package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.di

import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.MapaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.RddPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.mapper.*
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.mapper.card.*
import org.koin.dsl.module

internal val presenterModule = module {
    factory { DatosBasicosMapper() }
    factory { DatosRolMapper() }
    factory { DatosVisitaMapper() }
    factory { MenuPersonaMapper(get()) }
    factory { MiRutaCardMapper(get(), get(), get(), get(), get()) }
    factory { DatosTipsDesarrolloMapper() }
    factory { BuscadorMapper(get()) }
    factory { CabeceraMapper() }
    factory { CalendarioMapper() }
    factory { EventoMapper() }
    factory { ObservacionesVisitaMapper() }
    factory { PersonaEnMapaMapper() }
    factory { RddMapper(get(), get(), get(), get(), get(), get()) }
    factory { args -> MapaPresenter(args[0], get()) }
    factory { RddPresenter(get(), get(), get(), get(), get(), get(), get()) }
}

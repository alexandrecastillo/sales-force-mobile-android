package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.di

import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.AvanceModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.AvanceZonasMapper
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.DesarrolloHabilidadMapper
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.model.DesarrolloComportamientoMapper
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.presenter.DesarrolloUaComportamientosPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.presenter.DesarrolloUaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.DesarrolloUaView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.comportamientos.DesarrolloUaComportamientosView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.habilidades.DesarrolloUaHabilidadesView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avancecampania.presenter.AvanceCampaniaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avanceregiones.mapper.AvanceRegionesMapper
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avanceregiones.presenter.AvanceRegionesPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avanceregiones.view.AvanceRegionesView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avancevisitasequipos.AvanceVisitasEquiposPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.misvisitas.MisVisitasPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.misvisitas.MisVisitasView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.presenter.AvanceRegionPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.presenter.ReconocimientoRegionPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gr.view.AvanceRegionView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gz.presenter.AvanceZonaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gz.view.AvanceView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.gz.view.DashboardRddGz
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.visitas.model.AvanceMapper
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.visitas.presenter.AvanceVisitasPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.visitas.view.AvanceVisitasView
import org.koin.dsl.module

internal val avanceModule = module {
    factory { (view: AvanceView) ->
        AvanceZonaPresenter(view, get(), get(), get())
    }
    factory { DashboardRddGz() }
    factory { AvanceMapper() }
    factory { (view: AvanceVisitasView) ->
        AvanceVisitasPresenter(view, get(), get(),get(),get(),get())
    }
    factory { AvanceModelMapper() }
    factory { (view: AvanceRegionView) ->
        AvanceRegionPresenter(view, get(), get(),get())
    }
    factory { DesarrolloComportamientoMapper(get()) }

    factory { (view: DesarrolloUaComportamientosView) ->
        DesarrolloUaComportamientosPresenter(view, get(), get(), get())
    }
    factory { params ->
        AvanceCampaniaPresenter(params[0], get(), get(), get())
    }

    factory { AvanceRegionesMapper() }
    factory { (view: AvanceRegionesView) ->
        AvanceRegionesPresenter(view, get(), get())
    }

    factory { params ->
        AvanceVisitasEquiposPresenter(params[0], get())
    }

    factory { (view: DesarrolloUaHabilidadesView) ->
        ReconocimientoRegionPresenter(view, get(), get(), get())
    }

    factory { (view: DesarrolloUaView) ->
        DesarrolloUaPresenter(view, get())
    }
    factory { DesarrolloHabilidadMapper() }
    factory { AvanceZonasMapper() }
    factory { (view: MisVisitasView) -> MisVisitasPresenter(view, get()) }
}

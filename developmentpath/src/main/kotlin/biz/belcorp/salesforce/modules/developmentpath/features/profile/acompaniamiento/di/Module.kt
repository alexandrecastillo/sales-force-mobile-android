package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.features.profile.ProfileViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.conversacion.focotrabajo.FocosTrabajoPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.conversacion.focotrabajo.GraficoGrMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.foco.FocoModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.foco.FocoPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.graficos.gerenteregion.GraficosGrPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.graficos.gerenteregion.topbottom.GraficoTopBottomPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.graficos.gerenteregion.topbottom.TopBottomMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.asignar.presenter.AsignarHabilidadPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.presenter.DetalleHabilidadesPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.contenedor.model.GraficoResumenMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.contenedor.presenter.InformacionHistoricaRegionPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.model.HistoricoMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.graficobarras.presenter.GraficoResumenRegionPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.productivasu3c.model.ZonasProductivasMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.productivasu3c.presenter.ProductivasU3CPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.planificacion.PlanificacionMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.planificacion.PlanificacionPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.reconocimiento.MostrarReconocimientoPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.di.tipsDesarrolloModules
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsvideo.di.tipsVideoModule
import biz.belcorp.salesforce.modules.developmentpath.features.profile.dream.DreamViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.dream_add.AddDreamViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

internal val acompaniamientoModule by lazy {
    listByElementsOf<Module>(
        tipsDesarrolloModules,
        planificacionModule,
        mostrarReconocimientoModule,
        habilidadesModule,
        tipsVideoModule,
        focosModule,
        graficosModule,
        profileModule,
        dreamModule,
    )
}

private val planificacionModule = module {
    factory { PlanificacionMapper() }
    factory { params ->
        PlanificacionPresenter(params[0], get(), get(), get())
    }
}

private val mostrarReconocimientoModule = module {
    factory { params ->
        MostrarReconocimientoPresenter(
            view = params[0],
            useCase = get()
        )
    }
}

private val profileModule = module {
    viewModel {
        ProfileViewModel(
            getNameUseCase = get(),
            syncUseCase = get(),
            obtenerSesionUseCase = get()
        )
    }
}

private val habilidadesModule = module {
    factory { params ->
        DetalleHabilidadesPresenter(params[0], get())
    }
    factory { params ->
        AsignarHabilidadPresenter(params[0], get())
    }
}

private val focosModule = module {
    factory { FocoModelMapper() }
    factory { FocoPresenter(get(), get(), get()) }
    factory { params -> FocosTrabajoPresenter(params[0], get(), get()) }
}

private val graficosModule = module {
    factory { GraficoGrMapper() }
    factory { TopBottomMapper() }
    factory { GraficoResumenMapper() }
    factory { HistoricoMapper() }
    factory { ZonasProductivasMapper() }
    factory { params -> GraficosGrPresenter(params[0], get()) }
    factory { params -> GraficoTopBottomPresenter(params[0], get(), get()) }
    factory { params -> GraficoResumenRegionPresenter(params[0], get(), get(), get()) }
    factory { params -> ProductivasU3CPresenter(params[0], get(), get()) }
    factory { InformacionHistoricaRegionPresenter(get(), get()) }
}

private val dreamModule = module {
    viewModel {
        DreamViewModel(get(), get(), get(), get(), get(), get())
    }
    viewModel { AddDreamViewModel(get(), get(), get(), get(), get()) }
}

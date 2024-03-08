package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress.ProgresoCaminoBrillanteMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.caminobrillante.progress.ProgresoCaminoBrillantePresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.concursos.ConcursoModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.concursos.ConcursosFragmentContract
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.concursos.ConcursosFragmentPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.herramientasdigitales.HerramientaDigitalContract
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.herramientasdigitales.HerramientaDigitalPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.herramientasdigitales.HerramientaDigitalViewMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.TipsContract
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.TipsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.mapper.TipViewMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.mapper.VideoViewMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.header.DetalleTipsDesarrolloViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.documents.DocumentosPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.novedades.ListaDocumentoMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.novedades.ListaNovedadesMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.novedades.novedades.NovedadesPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.programanuevas.TipsProgramaNuevasMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.programanuevas.TipsProgramaNuevasPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.tips.VentaGananciaTipsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.tips.mapper.OfertaViewMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.tips.mapper.TipOfertaViewMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.ventaganancia.VentaGananciaContract
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.ventaganancia.VentaGananciaModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.ventaganancia.VentaGananciaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.section.TipsDesarrolloMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.section.TipsDesarrolloViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

internal val tipsDesarrolloModules by lazy {
    listByElementsOf<Module>(
        sectionModule,
        detailModules
    )
}

private val sectionModule = module {
    factory { TipsDesarrolloMapper() }
    viewModel { TipsDesarrolloViewModel(get(), get()) }
}

private val detailModules by lazy {
    listByElementsOf<Module>(
        headerModule,
        concursosModule,
        novedadesModule,
        digitalModule,
        programaNuevasModule,
        ventaGananciaModule,
        caminoBrillanteModule
    )
}

private val headerModule = module {
    viewModel { DetalleTipsDesarrolloViewModel(get(), get()) }
}

private val concursosModule = module {
    factory { ConcursoModelMapper() }
    factory { params ->
        ConcursosFragmentPresenter(
            view = params[0],
            useCase = get(),
            mapper = get()
        ) as ConcursosFragmentContract.Presenter
    }
}

private val novedadesModule = module {
    factory { ListaNovedadesMapper() }
    factory { ListaDocumentoMapper() }

    factory { params ->
        NovedadesPresenter(
            view = params[0],
            useCase = get(),
            mapper = get()
        )
    }
    factory { params ->
        DocumentosPresenter(
            view = params[0],
            useCase = get(),
            mapper = get()
        )
    }
}

private val digitalModule = module {
    factory { HerramientaDigitalViewMapper() }
    factory { TipViewMapper() }
    factory { VideoViewMapper() }

    factory { params ->
        HerramientaDigitalPresenter(
            view = params[0],
            useCase = get(),
            mapper = get()
        ) as HerramientaDigitalContract.Presenter
    }
    factory { params ->
        TipsPresenter(
            view = params[0],
            ventaGananciaTipsUseCase = get(),
            digitalUseCase = get(),
            caminoBrillanteUseCase = get(),
            tipMapper = get(),
            videoMapper = get()
        ) as TipsContract.Presenter
    }
}

private val programaNuevasModule = module {
    factory { TipsProgramaNuevasMapper() }
    factory { params ->
        TipsProgramaNuevasPresenter(
            view = params[0],
            useCase = get(),
            tipsMapper = get()
        )
    }
}

private val ventaGananciaModule = module {
    factory { VentaGananciaModelMapper() }
    factory { OfertaViewMapper() }
    factory { TipOfertaViewMapper(get()) }

    factory { params ->
        VentaGananciaPresenter(
            view = params[0],
            useCase = get(),
            mapper = get()
        ) as VentaGananciaContract.Presenter
    }
    factory { params ->
        VentaGananciaTipsPresenter(
            view = params[0],
            useCase = get(),
            mapper = get()
        )
    }
}

private val caminoBrillanteModule = module {
    factory { ProgresoCaminoBrillanteMapper() }
    factory { params ->
        ProgresoCaminoBrillantePresenter(
            view = params[0],
            useCase = get(),
            mapper = get()
        )
    }
}

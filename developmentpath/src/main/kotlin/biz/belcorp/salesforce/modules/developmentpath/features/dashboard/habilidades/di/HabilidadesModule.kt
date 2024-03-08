package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.habilidades.di

import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.habilidades.propios.model.MiHabilidadViewMapper
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.habilidades.propios.presenter.MisHabilidadesPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.habilidades.propios.view.MisHabilidadesView
import org.koin.dsl.module

internal val habilidadesModule = module {
    factory { (view: MisHabilidadesView) ->
        MisHabilidadesPresenter(view, get(), get(), get())
    }
    factory { MiHabilidadViewMapper() }
}

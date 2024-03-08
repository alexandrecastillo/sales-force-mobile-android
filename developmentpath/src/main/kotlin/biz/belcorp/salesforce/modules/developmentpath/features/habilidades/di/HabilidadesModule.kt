package biz.belcorp.salesforce.modules.developmentpath.features.habilidades.di

import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.avance_u6c.presenter.AvanceHabilidadPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.avance_u6c.view.AvanceHabilidadMapper
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.reconocer.presenter.ReconocerHabilidadesPresenter
import org.koin.dsl.module

internal val habilidadesModule = module {
    factory { params -> ReconocerHabilidadesPresenter(params[0], get(), get()) }
    factory { params -> AvanceHabilidadPresenter(params[0], get(), get(), get(), get()) }
    factory { AvanceHabilidadMapper() }
}

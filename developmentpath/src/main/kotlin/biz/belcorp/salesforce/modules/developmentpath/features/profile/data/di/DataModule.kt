package biz.belcorp.salesforce.modules.developmentpath.features.profile.data.di

import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.gz.CampaniaACampaniaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzagananciaanterior.CollectionInfoViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.contacto.DatosContactoViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.desempenio.last6campaigns.DesempenioGrGzModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.desempenio.last6campaigns.DesempenioGrGzPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gz.goals.presenter.MetaPersonalPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.gz.goals.view.MetaPersonalMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.persona.DatosPersonaMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.data.persona.DatosPersonaViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val profileData = module {

    viewModel { DatosContactoViewModel(get()) }

    factory { params -> DesempenioGrGzPresenter(params[0], get(), get()) }
    factory { params -> MetaPersonalPresenter(params[0], get(), get()) }

    factory { DatosPersonaMapper() }
    viewModel { DatosPersonaViewModel(get(), get()) }

    viewModel { CollectionInfoViewModel(get(), get()) }
    factory { CampaniaACampaniaPresenter(get(), get()) }

    factory { MetaPersonalMapper() }
    factory { DesempenioGrGzModelMapper() }

}

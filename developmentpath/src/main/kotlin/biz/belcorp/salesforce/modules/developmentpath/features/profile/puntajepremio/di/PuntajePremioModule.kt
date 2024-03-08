package biz.belcorp.salesforce.modules.developmentpath.features.profile.puntajepremio.di

import biz.belcorp.salesforce.modules.developmentpath.features.profile.puntajepremio.PuntajePremioPresenter
import org.koin.dsl.module

internal val puntajePremioModule = module {
    factory { PuntajePremioPresenter(get()) }
}

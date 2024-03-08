package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsvideo.di

import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsvideo.TipsMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsvideo.TipsPresenter
import org.koin.dsl.module

val tipsVideoModule = module {
    factory { TipsMapper() }
    factory {
        TipsPresenter(useCase = get(), mapper = get())
    }
}

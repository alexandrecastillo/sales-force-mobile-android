package biz.belcorp.salesforce.modules.developmentpath.features.focos.di

import biz.belcorp.salesforce.modules.developmentpath.features.focos.presenter.AsignarFocosPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.focos.presenter.ConstructorTitulo
import org.koin.dsl.module

internal val focosModule = module {
    factory { AsignarFocosPresenter(get()) }
    factory { ConstructorTitulo() }
}

package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.di

import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.FocoModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.FocoRegionModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.FocosContenedorPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.FocosContenedorView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gr.FocosHabilidadesPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gz.presenter.FocoPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gz.view.FocosView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.propios.model.MiFocoViewMapper
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.propios.presenter.MisFocosPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.propios.view.MisFocosView
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.se.presenter.FocosSEPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.se.view.FocosSEView
import org.koin.dsl.module

internal val focosModule = module {
    factory { params ->
        FocosHabilidadesPresenter(params[0], get(), get())
    }
    factory { FocoRegionModelMapper() }
    factory { FocoModelMapper() }
    factory { (view: FocosView) ->
        FocoPresenter(view, get(), get())
    }
    factory { (view: MisFocosView) ->
        MisFocosPresenter(view, get(), get(), get())
    }
    factory { MiFocoViewMapper() }
    factory { (view: FocosSEView) ->
        FocosSEPresenter(view, get())
    }
    factory { (view: FocosContenedorView) ->
        FocosContenedorPresenter(view, get())
    }
}

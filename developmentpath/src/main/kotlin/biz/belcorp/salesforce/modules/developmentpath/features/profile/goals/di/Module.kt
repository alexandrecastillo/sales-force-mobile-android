package biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.consultora.crear.CrearMetaConsultoraPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.consultora.listar.ListarMetasConsultoraMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.consultora.listar.ListarMetasConsultoraPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.mapper.MetaSociaModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.view.crear.MetaSociaPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.view.listar.ListarMetaSociaPresenter
import org.koin.core.module.Module
import org.koin.dsl.module

internal val goalsModules by lazy {
    listByElementsOf<Module>(
        goalsConsultoraModule,
        goalsSociaEmpresariaModule
    )
}

private val goalsConsultoraModule = module {
    factory { ListarMetasConsultoraMapper() }

    factory { params ->
        CrearMetaConsultoraPresenter(
            view = params[0],
            useCase = get(),
            obtenerListadoCampanaFinUseCase = get(),
            administrarMetaConsultoraUseCase = get()
        )
    }
    factory { params ->
        ListarMetasConsultoraPresenter(
            view = params[0],
            useCase = get(),
            metaMapper = get()
        )
    }
}

private val goalsSociaEmpresariaModule = module {
    factory { MetaSociaModelMapper() }

    factory { params ->
        MetaSociaPresenter(
            view = params[0],
            useCase = get(),
            mapper = get()
        )
    }
    factory { params ->
        ListarMetaSociaPresenter(
            view = params[0],
            useCase = get(),
            mapper = get()
        )
    }
}

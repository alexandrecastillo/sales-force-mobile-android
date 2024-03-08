package biz.belcorp.salesforce.modules.postulants.features.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ErrorMessageFactory
import biz.belcorp.salesforce.modules.postulants.features.formulario.di.uneteFormularioModule
import biz.belcorp.salesforce.modules.postulants.features.evaluation.di.postulantEvaluationModule
import biz.belcorp.salesforce.modules.postulants.features.indicador.di.uneteIndicadorModule
import biz.belcorp.salesforce.modules.postulants.features.mapper.ParametroUneteModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.mapper.PostulantesModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.mapper.PrePostulantesModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.mapper.UneteConfiguracionModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.messaging.di.messagingModule
import biz.belcorp.salesforce.modules.postulants.features.sync.di.syncPostulanteModule
import org.koin.core.module.Module
import org.koin.dsl.module

internal val featureModules by lazy {
    listByElementsOf<Module>(
        uneteFormularioModule,
        uneteIndicadorModule,
        uneteBaseModules,
        syncPostulanteModule,
        messagingModule,
        postulantEvaluationModule
    )
}

internal val uneteBaseModules = module {
    listByElementsOf<Module>(
        factory { PrePostulantesModelDataMapper() },
        factory { ParametroUneteModelDataMapper() },
        factory { UneteConfiguracionModelDataMapper() },
        factory { PostulantesModelDataMapper() },
        factory {
            ErrorMessageFactory(
                get()
            )
        }
    )
}

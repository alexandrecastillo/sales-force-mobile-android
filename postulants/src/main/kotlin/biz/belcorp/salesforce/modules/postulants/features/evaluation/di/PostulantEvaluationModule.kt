package biz.belcorp.salesforce.modules.postulants.features.evaluation.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.postulants.features.evaluation.PostulantEValuationPresenter
import biz.belcorp.salesforce.modules.postulants.features.evaluation.container.PostulantEvaluationContainerPresenter
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

internal val postulantEvaluationModule by lazy {
    listByElementsOf<Module>(module)
}


internal val module = module {
    factory {
        PostulantEValuationPresenter(androidContext(), get(), get(), get(), get(), get())
    }

    factory {
        PostulantEvaluationContainerPresenter(get())
    }
}


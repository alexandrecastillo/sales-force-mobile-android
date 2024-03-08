package biz.belcorp.salesforce.modules.postulants.features.formulario.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.di.formularioModule
import org.koin.core.module.Module


internal val uneteFormularioModule by lazy {
    listByElementsOf<Module>(
        formularioModule
    )
}

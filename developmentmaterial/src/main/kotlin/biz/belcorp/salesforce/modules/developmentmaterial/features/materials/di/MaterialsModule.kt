package biz.belcorp.salesforce.modules.developmentmaterial.features.materials.di

import biz.belcorp.salesforce.modules.developmentmaterial.features.materials.MaterialesDesarrolloPresenter
import biz.belcorp.salesforce.modules.developmentmaterial.features.materials.MaterialesDesarrolloView
import biz.belcorp.salesforce.modules.developmentmaterial.features.materials.mappers.DocumentMapper
import org.koin.dsl.module


val materialsModule = module {

    factory { DocumentMapper() }

    factory { (view: MaterialesDesarrolloView) ->
        MaterialesDesarrolloPresenter(view, get(), get(), get())
    }

}

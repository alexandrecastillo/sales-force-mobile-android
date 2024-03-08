package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.DigitalSaleViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.mapper.DigitalSaleCoMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.mapper.DigitalSaleModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.mapper.DigitalSaleSeMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.mapper.DigitalSaleTextResolver
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.headercontainer.DetallePrepararseEsClavePresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.marcasycategorias.BrandModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.marcasycategorias.BrandsAndCategoriesViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.marcasycategorias.CategoryModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.multimarkcategorie.MultiMarkCategoryViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.productosmasvendidos.TopSoldProductsModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.productosmasvendidos.TopSoldProductsTextResolver
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.productosmasvendidos.TopSoldProductsViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.capitalizacion.mapper.CapitalizationSeBarEntryMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.capitalizacion.mapper.CapitalizationSeMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.capitalizacion.view.CapitalizationSeViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ganancia.mapper.ProfitSeBarEntryMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ganancia.mapper.ProfitSeMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ganancia.view.ProfitSeViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.pedidosu6c.OrdersBarEntriesModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.pedidosu6c.ProfileSeOrdersU6CViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.penetracionmarca.marcas.BrandsViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.retencionactivas.mappers.RetencionBarEntryMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.retencionactivas.mappers.RetencionMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.retencionactivas.view.ActivesRetentionViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ventaneta.mappers.GraphicNetSaleSeMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ventaneta.models.NetSaleBarEntryMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ventaneta.view.GraphicNetSaleSeViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.performance.mapper.PerformanceModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.performance.view.PerformanceSeViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.resultado.mappers.ResultByLastCampaignMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.resultado.mappers.ResultLastCampaignGoalRealMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.resultado.mappers.ResultLastCampaignMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.resultado.mappers.ResultLastCampaignTextResolver
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.resultado.viewmodel.ResultsLastCampaignViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.mappers.CatalogSaleConsultantMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.mappers.GainConsultantMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.view.BrightPathCabeceraMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.view.SaleConsultantTextResolver
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.viewmodel.BrightPathViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.viewmodel.CatalogSaleConsultantViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.viewmodel.GainConsultantViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.section.PrepararseEsClaveContract
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.section.PrepararseEsClaveMapper
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.section.PrepararseEsClavePresenter
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

internal val prepararseEsClaveModules by lazy {
    listByElementsOf<Module>(
        prepararseEsClaveModule,
        ventaGananciaModule,
        marcasYCategoriasModule,
        productosMasVendidosModule,
        digitalModule,
        performanceModule,
        graphicsModule,
        campaignResultsModule,
        brightPathModule
    )
}

private val prepararseEsClaveModule = module {
    factory { PrepararseEsClaveMapper() }
    factory<PrepararseEsClaveContract.Presenter> { params ->
        PrepararseEsClavePresenter(
            view = params[0],
            useCase = get(),
            mapper = get()
        )
    }
    factory { params ->
        DetallePrepararseEsClavePresenter(
            view = params[0],
            useCase = get(),
            mapper = get()
        )
    }
}

private val ventaGananciaModule = module {
    factory { SaleConsultantTextResolver(get()) }
    factory { CatalogSaleConsultantMapper(get()) }
    factory { GainConsultantMapper(get()) }
    viewModel { CatalogSaleConsultantViewModel(get(), get()) }
    viewModel { GainConsultantViewModel(get(), get()) }
}

private val marcasYCategoriasModule = module {
    factory { BrandModelMapper() }
    factory { CategoryModelMapper() }
    viewModel { BrandsAndCategoriesViewModel(get(), get(), get()) }
}

private val productosMasVendidosModule = module {
    factory { TopSoldProductsModelMapper(get()) }
    factory { TopSoldProductsTextResolver(get()) }
    viewModel { TopSoldProductsViewModel(get(), get()) }
    viewModel { MultiMarkCategoryViewModel(get(), get()) }
}

private val digitalModule = module {
    factory { DigitalSaleTextResolver(get()) }
    factory { DigitalSaleSeMapper(get()) }
    factory { DigitalSaleCoMapper(get()) }
    factory { DigitalSaleModelMapper(get(), get()) }
    viewModel { DigitalSaleViewModel(get(), get(), get()) }
}

private val performanceModule = module {
    factory { PerformanceModelMapper(get()) }
    viewModel { PerformanceSeViewModel(get(), get()) }
}

private val graphicsModule = module {

    factory { CapitalizationSeMapper() }
    factory { CapitalizationSeBarEntryMapper() }
    viewModel { CapitalizationSeViewModel(get(), get(), get()) }

    factory { ProfitSeMapper() }
    factory { ProfitSeBarEntryMapper() }
    factory { ProfitSeViewModel(get(), get(), get()) }

    factory { OrdersBarEntriesModelMapper() }
    viewModel { ProfileSeOrdersU6CViewModel(get(), get()) }
    factory { RetencionMapper() }
    factory { RetencionBarEntryMapper() }
    viewModel { ActivesRetentionViewModel(get(), get(), get()) }
    factory { GraphicNetSaleSeMapper() }
    factory { NetSaleBarEntryMapper() }
    viewModel { GraphicNetSaleSeViewModel(get(), get(), get()) }
    viewModel { BrandsViewModel(get(), get()) }
}

private val campaignResultsModule = module {
    factory { ResultLastCampaignTextResolver(get()) }
    factory { ResultLastCampaignGoalRealMapper() }
    factory { ResultByLastCampaignMapper(get()) }
    factory { ResultLastCampaignMapper(get(), get(), get()) }
    viewModel { ResultsLastCampaignViewModel(get(), get()) }
}

private val brightPathModule = module {
    factory { BrightPathCabeceraMapper() }
    viewModel { BrightPathViewModel(get(), get(), get(), get()) }
}

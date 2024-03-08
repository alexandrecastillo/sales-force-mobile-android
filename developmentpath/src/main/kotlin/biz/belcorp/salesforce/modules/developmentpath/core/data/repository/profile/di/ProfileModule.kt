package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.graficos.data.ProfileSeOrdersU6CDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.horariovisita.cloud.HorarioVisitaCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.horariovisita.data.HorarioVisitaConsultoraLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.horariovisita.data.HorarioVisitaLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.ingresosextra.cloud.IngresosExtraCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.ingresosextra.data.IngresosExtraLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.DigitalSaleDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.digital.cloud.DigitalSaleCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.salesconsultant.TopSalesCoDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.salesconsultant.TopSalesSeDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.ventas.cloud.SalesConsultantCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.ventas.data.CatalogSaleConsultantDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.ventas.data.GainConsultantDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.ventas.data.SaleBrightPathConsultantDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.ventas.data.SaleConsultantDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.horariovisita.HorarioVisitaConsultoraEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.horariovisita.HorarioVisitaEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.ingresosextra.OtraMarcaEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave.detail.masvendido.marcascategorias.BrandsAndCategoriesMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave.detail.masvendido.marcascategorias.BrandsMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave.detail.masvendido.marcascategorias.MultiMarkCategoriesMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave.detail.masvendido.productosmasvendidos.TopProductsMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave.detail.ventas.CatalogSaleConsultantMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave.detail.ventas.GainConsultantMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave.detail.ventas.SaleConsultantMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.pedidosu6c.ProfileSeOrdersU6CSyncDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.pedidosu6c.cloud.ProfileSeOrdersU6CCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.pedidosu6c.mapper.ProfileSeOrdersU6CSyncMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.horariovisita.HorarioVisitaDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.info.ProfileInfoDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.info.data.BusinessPartnerInfoDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.info.data.ConsultantInfoDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.info.data.DirectoryInfoDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.info.data.PostulantsInfoDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.info.mappers.ContactInfoMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.info.mappers.ProfileInfoMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.ingresosextra.IngresosExtraDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.performance.PerformanceSeDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.performance.mapper.PerformanceMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.DigitalSaleSyncDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.PrepararseEsClaveDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.digital.DigitalSaleDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.digital.mapper.DigitalSaleMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.digital.mapper.DigitalSaleQueryMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.masvendido.TopSalesCoSyncDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.masvendido.TopSalesSeSyncDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.masvendido.mappers.TopSalesCoMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.masvendido.mappers.TopSalesSeMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.masvendido.marcasycategorias.TopSalesCoDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.masvendido.marcasycategorias.TopSalesSeDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.ventas.BrightPathConsultantDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.ventas.CatalogSaleConsultantDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.ventas.GainConsultantDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.ventas.SaleConsultantSyncDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.cloud.TopSalesCoCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.cloud.TopSalesSeCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.preparingiskey.TopSalesCoSyncRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.preparingiskey.TopSalesSeSyncRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.ProfileInfoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.preparingiskey.co.TopSalesCoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.preparingiskey.se.TopSalesSeRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.seordersu6c.ProfileSeOrdersU6CSyncRepository
import org.koin.core.module.Module
import org.koin.dsl.module

internal val profileModules by lazy {
    listByElementsOf<Module>(
        prepararseEsClaveModule,
        ventaGananciaModule,
        marcasYCategoriasModule,
        productosMasVendidosModule,
        digitalModule,
        ingresosExtraModule,
        infoModule,
        performanceModule,
        horarioVisitaModule,
        brightPathSales
    )
}

private val prepararseEsClaveModule = module {
    factory<PrepararseEsClaveRepository> { PrepararseEsClaveDataRepository() }
    factory<ProfileSeOrdersU6CSyncRepository> {
        ProfileSeOrdersU6CSyncDataRepository(get(), get(), get())
    }
    factory { ProfileSeOrdersU6CSyncMapper() }
    factory { ProfileSeOrdersU6CCloudStore(get(), get()) }
    factory { ProfileSeOrdersU6CDataStore() }
}

private val ventaGananciaModule = module {
    factory { SaleConsultantMapper() }
    factory { CatalogSaleConsultantMapper() }
    factory { GainConsultantMapper() }
    factory { SaleConsultantDataStore() }
    factory { CatalogSaleConsultantDataStore() }
    factory { GainConsultantDataStore() }
    factory { SalesConsultantCloudStore(get(), get()) }
    factory<CatalogSaleConsultantRepository> { CatalogSaleConsultantDataRepository(get(), get()) }
    factory<GainConsultantRepository> { GainConsultantDataRepository(get(), get()) }
    factory<SaleConsultantSyncRepository> { SaleConsultantSyncDataRepository(get(), get(), get()) }
}

private val marcasYCategoriasModule = module {
    factory<TopSalesCoRepository> { TopSalesCoDataRepository(get(), get(), get(), get()) }
    factory<TopSalesSeRepository> { TopSalesSeDataRepository(get(), get(), get()) }
    factory<TopSalesCoSyncRepository> {
        TopSalesCoSyncDataRepository(
            get(),
            get(),
            get()
        )
    }
    factory<TopSalesSeSyncRepository> {
        TopSalesSeSyncDataRepository(
            get(),
            get(),
            get()
        )
    }
    factory { BrandsAndCategoriesMapper() }
    factory { BrandsMapper() }
    factory { TopSalesSeMapper() }
    factory { TopSalesCoDataStore() }
    factory { TopSalesSeDataStore() }
    factory { TopSalesCoCloudStore(get(), get()) }
    factory { TopSalesSeCloudStore(get(), get()) }
    factory { TopSalesCoMapper() }
    factory { MultiMarkCategoriesMapper() }
}

private val productosMasVendidosModule = module {
    factory { TopProductsMapper() }
}

private val digitalModule = module {
    factory { DigitalSaleQueryMapper() }
    factory { DigitalSaleMapper() }
    factory { DigitalSaleCloudStore(get(), get()) }
    factory { DigitalSaleDataStore() }
    factory<DigitalSaleRepository> { DigitalSaleDataRepository(get(), get()) }
    factory<DigitalSaleSyncRepository> { DigitalSaleSyncDataRepository(get(), get(), get(), get()) }
}

private val ingresosExtraModule = module {
    factory { OtraMarcaEntityMapper(get()) }
    factory { IngresosExtraCloudDataStore(get(), get()) }
    factory { IngresosExtraLocalDataStore() }
    factory<IngresosExtraRepository> { IngresosExtraDataRepository(get(), get(), get()) }
}

private val horarioVisitaModule = module {
    factory { HorarioVisitaEntityMapper() }
    factory { HorarioVisitaConsultoraEntityMapper() }
    factory { HorarioVisitaCloudDataStore(get(), get()) }
    factory { HorarioVisitaLocalDataStore() }
    factory { HorarioVisitaConsultoraLocalDataStore() }
    factory<HorarioVisitaRepository> {
        HorarioVisitaDataRepository(
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}

private val infoModule = module {
    factory { ContactInfoMapper() }
    factory { ProfileInfoMapper() }
    factory { PostulantsInfoDataStore() }
    factory { ConsultantInfoDataStore() }
    factory { BusinessPartnerInfoDataStore() }
    factory { DirectoryInfoDataStore() }
    factory<ProfileInfoRepository> {
        ProfileInfoDataRepository(
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}

private val performanceModule = module {
    factory { PerformanceMapper() }
    factory<PerformanceSeRepository> { PerformanceSeDataRepository(get(), get()) }
}

private val brightPathSales = module {
    factory { SaleBrightPathConsultantDataStore() }
    factory<BrightPathSaleRepository> { BrightPathConsultantDataRepository(get()) }
}

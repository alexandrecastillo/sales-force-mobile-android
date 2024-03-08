package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.tipsdesarrollo.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tips.TipEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.caminobrillante.CaminoBrillanteTipEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.digital.HerramientaDigitalEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ventaganancia.VentaGananciaEntity
import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.tipsdesarrollo.TipsDesarrolloDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.tipsdesarrollo.TipsNegocioDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.tipsdesarrollo.caminobrillante.ProgresoCaminoBrillanteDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.tipsdesarrollo.novedades.NovedadesDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.tipsdesarrollo.tipsoferta.TipsOfertaCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.tipsdesarrollo.tipsoferta.TipsOfertaDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.TipsDesarrolloMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.TipsNegocioEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.caminobrillante.ProgresoCaminoBrillanteMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.digital.HerramientaDigitalEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.novedades.ListaIncentivosMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.novedades.ListaNovedadesMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.tips.TipEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.tips.VideoEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.tips.caminobrillante.CaminoBrillanteTipsEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.tips.tipsoferta.OfertaEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.tips.tipsoferta.TipsOfertaEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.ventaganancia.VentaGananciaEntityMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.tipsdesarrollo.TipsDesarrolloDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.tipsdesarrollo.caminobrillante.CaminoBrillanteTipsDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.tipsdesarrollo.caminobrillante.ProgresoCaminoBrillanteDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.tipsdesarrollo.digital.HerramientaDigitalDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.tipsdesarrollo.novedades.DocumentoDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.tipsdesarrollo.novedades.NovedadesDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.tipsdesarrollo.tipsoferta.TipsOfertaDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.tipsdesarrollo.ventaganancia.VentaGananciaDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.tipsdesarrollo.ventaganancia.VentaGananciaTipsDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo.*
import com.google.gson.reflect.TypeToken
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val tipsDesarrolloModule by lazy {
    listByElementsOf<Module>(
        tipsDesarrolloGeneralModule,
        tipsDesarrolloDetalleModule,
        tipsOfertaModule,
        novedadesModule
    )
}

private val tipsDesarrolloGeneralModule = module {
    factory { TipsDesarrolloMapper(gson = get()) }
    factory { TipsDesarrolloDataStore() }
    factory<TipsDesarrolloRepository> {
        TipsDesarrolloDataRepository(get(), get(), get(), get())
    }
}

private const val NAMED_TYPE_VG1 = "TYPE_VG1"
private const val NAMED_TYPE_VG2 = "TYPE_VG2"
private const val NAMED_TYPE_VG3 = "TYPE_VG3"
private const val NAMED_TYPE_DG1 = "TYPE_DG1"
private const val NAMED_TYPE_CB2 = "TYPE_CB2"

private const val NAMED_MAPPER_VG1 = "MAPPER_VG1"
private const val NAMED_MAPPER_VG2 = "MAPPER_VG2"
private const val NAMED_MAPPER_VG3 = "MAPPER_VG3"
private const val NAMED_MAPPER_DG1 = "MAPPER_DG1"
private const val NAMED_MAPPER_CB2 = "MAPPER_CB2"

private val tipsDesarrolloDetalleModule = module {

    factory(named(NAMED_TYPE_VG1)) { object : TypeToken<VentaGananciaEntity>() {}.type }
    factory(named(NAMED_TYPE_VG2)) { object : TypeToken<List<TipEntity>>() {}.type }
    factory(named(NAMED_TYPE_DG1)) { object : TypeToken<List<HerramientaDigitalEntity>>() {}.type }
    factory(named(NAMED_TYPE_CB2)) { object : TypeToken<CaminoBrillanteTipEntity>() {}.type }

    factory(named(NAMED_MAPPER_VG1)) {
        TipsNegocioEntityMapper<VentaGananciaEntity>(
            type = get(
                named(
                    NAMED_TYPE_VG1
                )
            ), gson = get()
        )
    }
    factory(named(NAMED_MAPPER_VG2)) {
        TipsNegocioEntityMapper<List<TipEntity>>(
            type = get(
                named(
                    NAMED_TYPE_VG2
                )
            ), gson = get()
        )
    }
    factory(named(NAMED_MAPPER_DG1)) {
        TipsNegocioEntityMapper<List<HerramientaDigitalEntity>>(
            type = get(
                named(NAMED_TYPE_DG1)
            ), gson = get()
        )
    }
    factory(named(NAMED_MAPPER_CB2)) {
        TipsNegocioEntityMapper<CaminoBrillanteTipEntity>(
            type = get(
                named(NAMED_TYPE_CB2)
            ), gson = get()
        )
    }

    factory { ProgresoCaminoBrillanteMapper(gson = get()) }
    factory { VentaGananciaEntityMapper() }
    factory { TipEntityMapper() }
    factory { VideoEntityMapper() }
    factory { HerramientaDigitalEntityMapper(tipMapper = get(), videoMapper = get()) }
    factory { CaminoBrillanteTipsEntityMapper(tipMapper = get(), videoMapper = get()) }

    factory { TipsNegocioDBDataStore() }
    factory { ProgresoCaminoBrillanteDataStore() }

    factory<VentaGananciaRepository> {
        VentaGananciaDataRepository(
            get(),
            get(named(NAMED_MAPPER_VG1)),
            get()
        )
    }
    factory<VentaGananciaTipsRepository> {
        VentaGananciaTipsDataRepository(
            get(),
            get(named(NAMED_MAPPER_VG2)),
            get()
        )
    }
    factory<HerramientaDigitalRepository> {
        HerramientaDigitalDataRepository(
            get(),
            get(named(NAMED_MAPPER_DG1)),
            get()
        )
    }
    factory<CaminoBrillanteTipsRepository> {
        CaminoBrillanteTipsDataRepository(
            get(),
            get(named(NAMED_MAPPER_CB2)),
            get()
        )
    }

    factory<ProgresoCaminoBrillanteRepository> {
        ProgresoCaminoBrillanteDataRepository(
            dataStore = get(),
            mapper = get()
        )
    }
}

private val tipsOfertaModule = module {
    factory { OfertaEntityMapper() }
    factory { TipsOfertaEntityMapper(gson = get(), innerMapper = get()) }
    factory { TipsOfertaCloudDataStore(api = get(), apiCallHelper = get()) }
    factory { TipsOfertaDBDataStore() }
    factory<TipsOfertaRepository> {
        TipsOfertaDataRepository(
            cloudDataStore = get(),
            localDataStore = get(),
            mapper = get()
        )
    }
}

private val novedadesModule = module {
    factory { ListaNovedadesMapper() }
    factory { ListaIncentivosMapper() }
    factory { NovedadesDBDataStore() }
    factory<NovedadesRepository> { NovedadesDataRepository(mapper = get(), dataStore = get()) }
    factory<DocumentoRepository> { DocumentoDataRepository(mapper = get(), dataStore = get()) }
}

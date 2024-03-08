package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.graficos.pedidosu6c.ProfileSeOrdersU6CUseCase
import org.koin.dsl.module

internal val graphicsModule = module {
    factory { GraphicCapitalizationUseCase(get(), get(), get(), get()) }
    factory { GraphicProfitSeUseCase(get(), get(), get(), get()) }
    factory { GetBrandsUseCase(get(), get(), get(),get()) }
    factory { ActivesRetentionUseCase(get(), get(), get(), get()) }
    factory { GraphicNetSaleSeUseCase(get(), get(), get(), get()) }
    factory { DefinirGraficoInferior() }
    factory { ObtenerGraficosGrUseCase(get(), get(), get(), get()) }
    factory { ObtenerGraficoTopBottomUseCase(get(), get(), get(), get()) }
    factory { ObtenerTitulosGraficosGrUseCase(get(), get(), get(), get()) }
    factory { ProfileSeOrdersU6CUseCase(get(), get(), get(), get()) }
}

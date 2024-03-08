package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.TipsDesarrolloUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.caminobrillante.ProgresoCaminoBrillanteUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.digital.HerramientaDigitalUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.novedades.DocumentoUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.novedades.NovedadesUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.tips.CaminoBrillanteTipsUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.tips.TipsOfertaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.tips.VentaGananciaTipsUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo.ventaganancia.VentaGananciaUseCase
import org.koin.dsl.module

internal val tipsDesarrolloModule = module {
    factory { TipsDesarrolloUseCase(get(), get()) }
    factory { VentaGananciaUseCase(get(), get(), get(), get()) }
    factory { VentaGananciaTipsUseCase(get(), get(), get(), get()) }
    factory { TipsOfertaUseCase(get(), get(), get(), get(), get()) }
    factory { HerramientaDigitalUseCase(get(), get(), get(), get()) }
    factory { NovedadesUseCase(get(), get(), get(), get()) }
    factory { DocumentoUseCase(get(), get(), get(), get()) }
    factory { ProgresoCaminoBrillanteUseCase(get(), get(), get(), get()) }
    factory { CaminoBrillanteTipsUseCase(get(), get(), get(), get()) }
}

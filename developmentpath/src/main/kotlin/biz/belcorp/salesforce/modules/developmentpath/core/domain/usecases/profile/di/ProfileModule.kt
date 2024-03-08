package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.CabeceraPerfilUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.GetPerformanceSeUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.RecuperarPersonaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.SyncProfileUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.horariovisita.HorarioVisitaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.info.GetProfileInfoUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.ingresosextra.IngresosExtraUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.PrepararseEsClaveUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.digital.GetDigitalSaleUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.masvendido.marcasycategorias.GetBrandsAndCategoriesUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.masvendido.productosmasvendidos.GetTopSoldProductsUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.ventas.GetCatalogSaleConsultantUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.ventas.GetGainConsultantUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.prepararseesclave.detail.ventas.SaleBrightPathConsultantUseCase
import org.koin.dsl.module

internal val profileModule = module {
    factory { SyncProfileUseCase(get(), get(), get(), get(), get(), get()) }
    factory { RecuperarPersonaUseCase(get(), get(), get()) }

    factory { CabeceraPerfilUseCase(get(), get(), get(), get()) }

    factory { PrepararseEsClaveUseCase(get(), get(), get(), get(), get()) }

    factory { GetCatalogSaleConsultantUseCase(get(), get(), get()) }
    factory { GetGainConsultantUseCase(get(), get(), get()) }

    factory { SaleBrightPathConsultantUseCase(get(), get(), get()) }

    factory { GetBrandsAndCategoriesUseCase(get(), get(), get(), get()) }
    factory { GetTopSoldProductsUseCase(get(), get(), get(), get()) }

    factory { GetDigitalSaleUseCase(get(), get(), get(), get(), get()) }

    factory { IngresosExtraUseCase(get(), get(), get(), get()) }

    factory { HorarioVisitaUseCase(get(), get(), get()) }

    factory { GetProfileInfoUseCase(get(), get(), get()) }

    factory { GetPerformanceSeUseCase(get()) }
}

package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.sync.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.sync.DashboardSyncUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.sync.ObtenerFechaSyncUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.sync.ProfileSyncUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.sync.SyncUseCase
import org.koin.dsl.module

internal val sincronizacionModule = module {
    factory { ObtenerFechaSyncUseCase(get()) }
    factory {
        ProfileSyncUseCase(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    factory {
        SyncUseCase(
            get(), get(), get(), get(), get(), get(), get(), get(), get(),
            get(), get(), get(), get(), get(), get(), get(), get(), get(), get()
        )
    }
    factory { DashboardSyncUseCase(get(), get(), get(), getOrNull()) }
}

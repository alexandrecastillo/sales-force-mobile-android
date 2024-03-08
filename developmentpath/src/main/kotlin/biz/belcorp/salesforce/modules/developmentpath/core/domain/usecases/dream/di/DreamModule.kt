package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dream.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dream.DeleteDreamUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dream.GetDreamUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dream.bp.CreateDreamBusinessPartnerUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dream.bp.DeleteDreamBpUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dream.bp.EditDreamBpUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dream.bp.GetBusinessPartnerDreamUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dream.bp.SyncBusinessPartnerDreamsUseCase
import org.koin.dsl.module

internal val dreamModule = module {
    factory { GetDreamUseCase(get(), get(), get()) }
    factory { DeleteDreamUseCase(get()) }
    factory { SyncBusinessPartnerDreamsUseCase(get(), get()) }
    factory { GetBusinessPartnerDreamUseCase(get(), get(), get()) }
    factory { CreateDreamBusinessPartnerUseCase(get()) }
    factory { DeleteDreamBpUseCase(get()) }
    factory { EditDreamBpUseCase(get()) }
}

package biz.belcorp.salesforce.modules.consultants.core.data.repository.unified.di

import biz.belcorp.salesforce.modules.consultants.core.data.repository.unified.ConsultantsDataRepository
import biz.belcorp.salesforce.modules.consultants.core.data.repository.unified.data.ConsultantsDataStore
import biz.belcorp.salesforce.modules.consultants.core.data.repository.unified.helper.QueryBuilderHelper
import biz.belcorp.salesforce.modules.consultants.core.data.repository.unified.helper.builders.*
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.unified.ConsultantsRepository
import org.koin.dsl.module

val unifiedModule = module {

    factory { NewCycleQueryBuilder() }
    factory { OrdersQueryBuilder() }
    factory { RetentionQueryBuilder() }
    factory { OrdersStatusQueryBuilder() }
    factory { PegsQueryBuilder() }
    factory { BillingQueryBuilder() }
    factory { StateQueryBuilder() }
    factory { SpecialQueryBuilder() }
    factory { DigitalQueryBuilder() }
    factory { TypeQueryBuilder() }
    factory { MultibrandQueryBuilder() }
    factory { MulticategoryQueryBuilder() }
    factory { OrderTypeQueryBuilder() }
    factory { QueryBuilderHelper() }
    factory { ConsultantsDataStore(get()) }
    factory<ConsultantsRepository> { ConsultantsDataRepository(get(), get(), get()) }

}

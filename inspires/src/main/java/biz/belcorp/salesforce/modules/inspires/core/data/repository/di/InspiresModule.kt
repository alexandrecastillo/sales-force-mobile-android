package biz.belcorp.salesforce.modules.inspires.core.data.repository.di

import biz.belcorp.salesforce.modules.inspires.core.data.repository.advances.AdvancesDataRepository
import biz.belcorp.salesforce.modules.inspires.core.data.repository.advances.data.AdvancesDBDataStore
import biz.belcorp.salesforce.modules.inspires.core.data.repository.advances.mapper.AdvancesEntityDataMapper
import biz.belcorp.salesforce.modules.inspires.core.data.repository.conditionslegenddetail.ConditionsLegendDetailDataRepository
import biz.belcorp.salesforce.modules.inspires.core.data.repository.conditionslegenddetail.data.ConditionsLegendDetailDBDataStore
import biz.belcorp.salesforce.modules.inspires.core.data.repository.conditionslegenddetail.mapper.ConditionsLegendDetailEntityDataMapper
import biz.belcorp.salesforce.modules.inspires.core.data.repository.indicator.IndicatorDataRepository
import biz.belcorp.salesforce.modules.inspires.core.data.repository.indicator.data.IndicatorDBDataStore
import biz.belcorp.salesforce.modules.inspires.core.data.repository.indicator.mapper.IndicatorEntityDataMapper
import biz.belcorp.salesforce.modules.inspires.core.data.repository.legendconditions.LegendConditionsDataRepository
import biz.belcorp.salesforce.modules.inspires.core.data.repository.legendconditions.data.LegendConditionsDBDataStore
import biz.belcorp.salesforce.modules.inspires.core.data.repository.legendconditions.mapper.LegendConditionsEntityDataMapper
import biz.belcorp.salesforce.modules.inspires.core.data.repository.progressperiod.ProgressPeriodDataRepository
import biz.belcorp.salesforce.modules.inspires.core.data.repository.progressperiod.data.ProgressPeriodDBDataStore
import biz.belcorp.salesforce.modules.inspires.core.data.repository.progressperiod.mapper.ProgressPeriodEntityDataMapper
import biz.belcorp.salesforce.modules.inspires.core.data.repository.ranking.RankingDataRepository
import biz.belcorp.salesforce.modules.inspires.core.data.repository.ranking.data.RankingDBDataStore
import biz.belcorp.salesforce.modules.inspires.core.data.repository.ranking.mapper.RankingEntityDataMapper
import biz.belcorp.salesforce.modules.inspires.core.data.repository.terms.TermsDataRepository
import biz.belcorp.salesforce.modules.inspires.core.data.repository.terms.data.TermsDBDataStore
import biz.belcorp.salesforce.modules.inspires.core.data.repository.terms.mapper.TermsEntityDataMapper
import biz.belcorp.salesforce.modules.inspires.core.domain.repository.advances.AdvancesRepository
import biz.belcorp.salesforce.modules.inspires.core.domain.repository.conditionslegenddetail.ConditionsLegendDetailRepository
import biz.belcorp.salesforce.modules.inspires.core.domain.repository.indicator.IndicatorRepository
import biz.belcorp.salesforce.modules.inspires.core.domain.repository.legendconditions.LegendConditionsRepository
import biz.belcorp.salesforce.modules.inspires.core.domain.repository.progressperiod.ProgressPeriodRepository
import biz.belcorp.salesforce.modules.inspires.core.domain.repository.ranking.RankingRepository
import biz.belcorp.salesforce.modules.inspires.core.domain.repository.terms.TermsRepository
import org.koin.dsl.module

val inspiresModule = module {

    factory { AdvancesEntityDataMapper() }
    factory { ConditionsLegendDetailEntityDataMapper() }
    factory { IndicatorEntityDataMapper() }
    factory { LegendConditionsEntityDataMapper() }
    factory { ProgressPeriodEntityDataMapper() }
    factory { RankingEntityDataMapper() }
    factory { TermsEntityDataMapper() }

    factory { AdvancesDBDataStore() }
    factory { ConditionsLegendDetailDBDataStore() }
    factory { IndicatorDBDataStore() }
    factory { LegendConditionsDBDataStore() }
    factory { ProgressPeriodDBDataStore() }
    factory { RankingDBDataStore() }
    factory { TermsDBDataStore() }

    factory<AdvancesRepository> { AdvancesDataRepository(get(), get()) }
    factory<ConditionsLegendDetailRepository> { ConditionsLegendDetailDataRepository(get(), get()) }
    factory<IndicatorRepository> { IndicatorDataRepository(get(), get()) }
    factory<LegendConditionsRepository> { LegendConditionsDataRepository(get(), get()) }
    factory<ProgressPeriodRepository> { ProgressPeriodDataRepository(get(), get()) }
    factory<RankingRepository> { RankingDataRepository(get(), get()) }
    factory<TermsRepository> { TermsDataRepository(get(), get()) }
}

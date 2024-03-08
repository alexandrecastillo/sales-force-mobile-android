package biz.belcorp.salesforce.modules.inspires.features.travel.di

import biz.belcorp.salesforce.modules.inspires.features.travel.InspireTravelPresenter
import biz.belcorp.salesforce.modules.inspires.features.travel.cards.limited.InspireTravelCardLimitedPresenter
import biz.belcorp.salesforce.modules.inspires.features.travel.cards.lost.InspireTravelCardLostPresenter
import biz.belcorp.salesforce.modules.inspires.features.travel.cards.ranking.InspireTravelCardRankingPresenter
import biz.belcorp.salesforce.modules.inspires.features.travel.tabs.advance.InspireAdvancePresenter
import biz.belcorp.salesforce.modules.inspires.features.travel.tabs.conditions.InspireConditionsPresenter
import biz.belcorp.salesforce.modules.inspires.features.travel.tabs.ranking.InspireRankingPresenter
import org.koin.dsl.module

internal val travelModule = module {

    factory { InspireTravelPresenter(get(), get(), get()) }
    factory { InspireAdvancePresenter(get(), get(), get(), get(), get()) }
    factory { InspireConditionsPresenter(get(), get(), get(), get()) }
    factory { InspireRankingPresenter(get(), get(), get()) }

    factory { InspireTravelCardLimitedPresenter(get(), get()) }
    factory { InspireTravelCardLostPresenter(get(), get()) }
    factory { InspireTravelCardRankingPresenter(get(), get(), get()) }

}

package biz.belcorp.salesforce.modules.consultants.features.di

import biz.belcorp.salesforce.modules.consultants.features.chatbot.di.chatBotModule
import biz.belcorp.salesforce.modules.consultants.features.filters.di.filtersModule
import biz.belcorp.salesforce.modules.consultants.features.list.di.listModule
import biz.belcorp.salesforce.modules.consultants.features.maps.di.mapModule
import biz.belcorp.salesforce.modules.consultants.features.messaging.di.messagingModule
import biz.belcorp.salesforce.modules.consultants.features.mypartners.di.myPartnerViewModel
import biz.belcorp.salesforce.modules.consultants.features.quantity.di.quantityModule
import biz.belcorp.salesforce.modules.consultants.features.search.di.searchModule
import biz.belcorp.salesforce.modules.consultants.features.sync.di.syncModule

val featureModules by lazy {
    listOf(
        searchModule,
        syncModule,
        listModule,
        filtersModule,
        quantityModule,
        chatBotModule,
        mapModule,
        messagingModule,
        myPartnerViewModel
    )
}

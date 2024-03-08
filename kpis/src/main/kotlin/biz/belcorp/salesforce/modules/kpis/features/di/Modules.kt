package biz.belcorp.salesforce.modules.kpis.features.di

import biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton.di.detailButtonModule
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.di.retentionCapitalizationModule
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.di.gainModule
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.di.newCycleModule
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.di.salesOrdersModule
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.di.kpiDetailModule
import biz.belcorp.salesforce.modules.kpis.features.kpis.di.kpiModule
import biz.belcorp.salesforce.modules.kpis.features.messaging.di.messagingModule
import biz.belcorp.salesforce.modules.kpis.features.sync.di.syncModule

val featureModules by lazy {
    listOf(
        kpiModule,
        kpiDetailModule,
        salesOrdersModule,
        gainModule,
        retentionCapitalizationModule,
        newCycleModule,
        detailButtonModule,
        messagingModule,
        syncModule
    )
}

package biz.belcorp.salesforce.modules.kpis.core.domain.entities

import biz.belcorp.salesforce.core.domain.entities.campania.CampaignRules
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.configuration.Configuration
import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.CapitalizationIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.CollectionIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle.NewCycleIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersIndicator

class KpiContainer(
    val saleOrders: KpiData<SaleOrdersIndicator>,
    val collections: KpiData<CollectionIndicator>,
    val newCycles: KpiData<NewCycleIndicator>,
    val capitalizations: KpiData<CapitalizationIndicator>,
    val configuration: Configuration,
    val isBright: Boolean,
    val role: Rol,
    val person: Person?,
    val campaign: Campania,
    val isThirdPerson: Boolean,
    val sessionRole: Rol
) {
    val isSale get() = !CampaignRules.isBilling(campaign)
}

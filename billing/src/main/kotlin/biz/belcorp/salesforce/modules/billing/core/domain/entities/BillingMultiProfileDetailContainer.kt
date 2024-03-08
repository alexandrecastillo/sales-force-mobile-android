package biz.belcorp.salesforce.modules.billing.core.domain.entities

class BillingMultiProfileDetailContainer(
    val retainedPegs: PegsBilling,
    val newCyclePendings: List<NewCycleBilling>,
    val isThirdPerson: Boolean
)

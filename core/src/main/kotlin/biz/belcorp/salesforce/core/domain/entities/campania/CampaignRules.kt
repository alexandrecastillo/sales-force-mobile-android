package biz.belcorp.salesforce.core.domain.entities.campania

object CampaignRules {

    fun isBilling(campaign: Campania, useFirstDayFlag: Boolean = true): Boolean {
        val allowBilling = !useFirstDayFlag || !campaign.esPrimerDiaFacturacion
        return campaign.periodo == Campania.Periodo.FACTURACION && allowBilling
    }
}

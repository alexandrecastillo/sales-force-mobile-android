package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos

import biz.belcorp.salesforce.core.domain.entities.campania.Campania

interface AcuerdosReconocimientosView {
    fun setLatestThreeCampaignAgreements(campaigns: List<Campania>)
}

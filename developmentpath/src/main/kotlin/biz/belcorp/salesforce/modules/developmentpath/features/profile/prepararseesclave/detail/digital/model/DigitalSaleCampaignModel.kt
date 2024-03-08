package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.model

data class DigitalSaleCampaignModel(
    val campaign: String?,
    val campaignCode: String,
    val saleCampaignChild: List<DigitalSaleCampaignChildModel>
)

package biz.belcorp.salesforce.modules.brightpath.features.container.consultants.header

interface ConsultantsHeaderDetailView {

    fun showCampaignInfo(campaignInfo: String)
    fun showTitle(title: String)
    fun showConsultantsInf(consultantsAmountInf: String)
    fun showDescriptionInf(descriptionInf: String)

}

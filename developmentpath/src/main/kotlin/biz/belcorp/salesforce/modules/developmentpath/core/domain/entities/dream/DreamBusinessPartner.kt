package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream

import biz.belcorp.salesforce.core.constants.Constant

class DreamBusinessPartner(
    dreamId: String? = Constant.EMPTY_STRING,
    amountToComplete: Int? = Constant.NUMBER_ONE,
    numberCampaignsToComplete: Int? = Constant.NUMBER_ZERO,
    dream: String? = Constant.EMPTY_STRING,
    region: String? = Constant.EMPTY_STRING,
    zone: String? = Constant.EMPTY_STRING,
    section: String? = Constant.EMPTY_STRING,
    comment: String? = Constant.EMPTY_STRING,
    campaignCreated: String? = Constant.EMPTY_STRING,
    status: String? = Constant.EMPTY_STRING,
    campaignEnd: String? = Constant.EMPTY_STRING,
    dateCreated: String? = Constant.EMPTY_STRING,
    dateEdited: String? = Constant.EMPTY_STRING,
    dateCompleted: String? = Constant.EMPTY_STRING,
    totalGain: Float? = Constant.ZERO_FLOAT,
    campaignList: List<DreamCampaign> = listOf<DreamCampaign>(),
    currencySymbol: String? = Constant.EMPTY_STRING,
    var bpCode: String? = Constant.EMPTY_STRING,
    var bpName: String? = Constant.EMPTY_STRING
) : Dream(
    dreamId = dreamId,
    amountToComplete = amountToComplete,
    numberCampaignsToComplete = numberCampaignsToComplete,
    dream = dream,
    region = region,
    zone = zone,
    section = section,
    comment = comment,
    campaignCreated = campaignCreated,
    status = status,
    campaignEnd = campaignEnd,
    dateCreated = dateCreated,
    dateEdited = dateEdited,
    dateCompleted = dateCompleted,
    totalGain = totalGain,
    campaignList = campaignList,
    currencySymbol = currencySymbol,
)

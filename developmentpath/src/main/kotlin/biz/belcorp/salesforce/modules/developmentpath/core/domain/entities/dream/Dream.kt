package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream

import biz.belcorp.salesforce.core.constants.Constant

open class Dream(
    var dreamId: String? = Constant.EMPTY_STRING,
    var consultantCode: String? = Constant.EMPTY_STRING,
    val amountToComplete: Int? = Constant.NUMBER_ONE,
    val numberCampaignsToComplete: Int? = Constant.NUMBER_ZERO,
    val dream: String? = Constant.EMPTY_STRING,
    val region: String? = Constant.EMPTY_STRING,
    val zone: String? = Constant.EMPTY_STRING,
    val section: String? = Constant.EMPTY_STRING,
    val comment: String? = Constant.EMPTY_STRING,
    val campaignCreated: String? = Constant.EMPTY_STRING,
    val status: String? = Constant.EMPTY_STRING,
    var consultantName: String? = Constant.EMPTY_STRING,
    val campaignEnd: String? = Constant.EMPTY_STRING,
    val dateCreated: String? = Constant.EMPTY_STRING,
    val dateEdited: String? = Constant.EMPTY_STRING,
    val dateCompleted: String? = Constant.EMPTY_STRING,
    val totalGain: Float? = Constant.ZERO_FLOAT,
    var campaignList: List<DreamCampaign> = listOf<DreamCampaign>(),
    var currencySymbol: String? = Constant.EMPTY_STRING,
)

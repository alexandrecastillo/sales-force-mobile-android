package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.dream

import biz.belcorp.salesforce.core.constants.Constant

data class EditCreateDream(
    var dreamDescription: String = Constant.EMPTY_STRING,
    var dreamComments: String = Constant.EMPTY_STRING,
    var dreamAmount: String = Constant.EMPTY_STRING,
    var dreamsCampaignsAchieve: String = Constant.EMPTY_STRING,
    var actualCampaign: String = Constant.EMPTY_STRING,
    var consultantCode: String = Constant.EMPTY_STRING,
    var dreamId: String = Constant.EMPTY_STRING,
    var bpCode: String = Constant.EMPTY_STRING,
)

package biz.belcorp.salesforce.modules.brightpath.features.brightpath

import biz.belcorp.salesforce.core.constants.Constant


data class BusinessPartnerChangeLevelModel(
    val campaign: String = Constant.EMPTY_STRING,
    val profile: String? = Constant.EMPTY_STRING,
    val region: String? = Constant.EMPTY_STRING,
    val zone: String? = Constant.EMPTY_STRING,
    val section: String? = Constant.EMPTY_STRING,
    val consultantCode: String? = Constant.EMPTY_STRING,
    val campaignRequirement: Int? = Constant.NUMBER_ZERO,
    val sectionSales: Double = Constant.EMPTY_DOUBLE,
    val sectionOrders: Int? = Constant.NUMBER_ZERO,
    val gainAmountLowValue: Double = Constant.EMPTY_DOUBLE,
    val gainAmountLowValuePlus: Double = Constant.EMPTY_DOUBLE,
    val gainAmountHighValue: Double = Constant.EMPTY_DOUBLE,
    val gainAmountHighValuePlus: Double = Constant.EMPTY_DOUBLE,
    val level: BusinessPartnerLevelModel,
    val nextLevel: BusinessPartnerChangeNextLevelModel,
    val requirement: List<BusinessPartnerRequirementModel>
)

data class BusinessPartnerLevelModel(
    val code: String? = Constant.EMPTY_STRING,
    val name: String? = Constant.EMPTY_STRING,
    val consecutiveCampaigns: Int? = Constant.NUMBER_ZERO,
    val campaignsNotAccomplished: Int? = Constant.NUMBER_ZERO,
    val accomplished: Boolean? = false
)

data class BusinessPartnerChangeNextLevelModel(
    val name: String? = Constant.EMPTY_STRING,
    val accomplished: Boolean = false,
    val campaigns_accomplished: Int = Constant.NUMBER_ZERO,
    val sales: BusinessPartnerSalesModel,
    val orders: BusinessPartnerOrdersModel
)

data class BusinessPartnerRequirementModel(
    val level: String = Constant.EMPTY_STRING,
    val minimumSales: Int = Constant.NUMBER_ZERO,
    val minimumOrders: Int = Constant.NUMBER_ZERO
)


data class BusinessPartnerSalesModel(
    val requirement: Int = Constant.NUMBER_ZERO,
    val real: Float = Constant.ZERO_FLOAT,
    val accomplished: Boolean = false
)

data class BusinessPartnerOrdersModel(
    val requirement: Int = Constant.NUMBER_ZERO,
    val real: Int = Constant.NUMBER_ZERO,
    val accomplished: Boolean = false
)


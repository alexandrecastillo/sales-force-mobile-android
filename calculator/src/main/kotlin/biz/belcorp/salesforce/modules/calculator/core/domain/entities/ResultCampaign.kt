package biz.belcorp.salesforce.modules.calculator.core.domain.entities

class ResultCampaign(
    val campaign: String,
    val region: String,
    val zone: String,
    val section: String,
    val catalogSale: Double?,
    val catalogSaleGoal: Double?,
    val orders: Int?,
    val ordersGoal: Int?,
    val capitalization: Int?,
    val capitalizationGoal: Int?,
    val entries: Int?,
    val entriesGoal: Int?,
    val reentries: Int?,
    val expenses: Int?,
    val activity: Double?,
    val possibleExpenses: Int?,
    val consultants6d6: Int?,
    val recovery: Double?,
    val consultantsWithDebt: Int?,
    val activesRetention: Double?
)

class ResultsOptional(val result: ResultCampaign?)

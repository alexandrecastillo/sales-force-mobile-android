package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model

class CollectionConsolidatedModel(
    val collectionList: List<CollectionGridIndicatorModel>,
    val uaTitle: String,
    val recoveryTitle: String,
    val invoicedTitle: String,
    val collectedTitle: String
)

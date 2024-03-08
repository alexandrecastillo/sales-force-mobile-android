package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.consolidated.grid

sealed class HeaderType(val title: String)

class UaHeader(title: String) : HeaderType(title)

sealed class CollectionHeaderType(title: String) : HeaderType(title) {
    class Recovery(title: String) : CollectionHeaderType(title)
    class Invoiced(title: String) : CollectionHeaderType(title)
    class Collected(title: String) : CollectionHeaderType(title)
}

package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.sales.consolidated.core


sealed class HeaderType(val title: String)

class UaHeader(title: String) : HeaderType(title)

sealed class PEGsHeaderType(title: String) : HeaderType(title) {
    class PEGs(title: String) : PEGsHeaderType(title)
    class RetentionPEGs(title: String) : PEGsHeaderType(title)
}

sealed class CapitalizationHeaderType(title: String) : HeaderType(title) {
    class Entries(title: String) : CapitalizationHeaderType(title)
    class Reentries(title: String) : CapitalizationHeaderType(title)
    class Expenses(title: String) : CapitalizationHeaderType(title)
    class Capitalization(title: String) : CapitalizationHeaderType(title)
    class CapitalizationGoal(title: String) : CapitalizationHeaderType(title)
}

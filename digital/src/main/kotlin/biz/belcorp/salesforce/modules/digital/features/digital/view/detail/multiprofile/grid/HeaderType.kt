package biz.belcorp.salesforce.modules.digital.features.digital.view.detail.multiprofile.grid

sealed class HeaderType(val title: String)

class UaHeader(title: String) : HeaderType(title)

sealed class DigitalHeaderType(title: String) : HeaderType(title) {
    class Value(title: String) : DigitalHeaderType(title)
    class ValuePercentage(title: String) : DigitalHeaderType(title)
}

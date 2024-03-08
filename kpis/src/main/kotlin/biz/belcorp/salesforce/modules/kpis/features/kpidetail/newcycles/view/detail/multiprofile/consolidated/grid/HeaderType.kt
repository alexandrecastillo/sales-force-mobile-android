package biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.detail.multiprofile.consolidated.grid

sealed class HeaderType(val title: String)

class UaHeader(title: String) : HeaderType(title)

sealed class LowValueHeaderType(title: String) : HeaderType(title) {
    class Segment6d6(title: String) : LowValueHeaderType(title)
    class Segment5d5(title: String) : LowValueHeaderType(title)
    class Segment4d4(title: String) : LowValueHeaderType(title)
    class Segment3d3(title: String) : LowValueHeaderType(title)
    class Segment2d2(title: String) : LowValueHeaderType(title)
}

sealed class HighValueHeaderType(title: String) : HeaderType(title) {
    class Segment6d6(title: String) : HighValueHeaderType(title)
    class Segment5d5(title: String) : HighValueHeaderType(title)
    class Segment4d4(title: String) : HighValueHeaderType(title)
}


package biz.belcorp.salesforce.modules.virtualmethodology.features.methodology.model

class GroupsSegmentationModel(
    var codigo: Int? = 0,
    var nombre: String? = null,
    var listSegm: List<SegmentationModel>? = emptyList()
) {
    var close = false
}

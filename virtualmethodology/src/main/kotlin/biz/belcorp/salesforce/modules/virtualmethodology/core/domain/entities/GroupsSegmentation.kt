package biz.belcorp.salesforce.modules.virtualmethodology.core.domain.entities

class GroupsSegmentation(
    var codigo: Int?,
    var nombre: String?,
    var listSeg: List<Segmentation> = emptyList()
)

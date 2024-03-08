package biz.belcorp.salesforce.modules.virtualmethodology.features.methodology.mappers

import biz.belcorp.salesforce.modules.virtualmethodology.core.domain.entities.GroupsSegmentation
import biz.belcorp.salesforce.modules.virtualmethodology.features.methodology.model.GroupsSegmentationModel

class GroupSegMapper {

    fun map(list: List<GroupsSegmentation>): List<GroupsSegmentationModel> {
        return list.asSequence().map { map(it) }.toList()
    }

    private fun map(group: GroupsSegmentation): GroupsSegmentationModel {
        return GroupsSegmentationModel(
            codigo = group.codigo ?: 0,
            nombre = group.nombre ?: "",
            listSegm = SegmentationMapper().map(group.listSeg)
        )
    }

}

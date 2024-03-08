package biz.belcorp.salesforce.modules.virtualmethodology.core.data.repository.mappers

import biz.belcorp.salesforce.core.entities.sql.metodologia.GrupoSegEntity
import biz.belcorp.salesforce.core.entities.sql.metodologia.SegmentacionEntity
import biz.belcorp.salesforce.modules.virtualmethodology.core.domain.entities.GroupSeg
import biz.belcorp.salesforce.modules.virtualmethodology.core.domain.entities.GroupsSegmentation
import biz.belcorp.salesforce.modules.virtualmethodology.core.domain.entities.Segmentation

class GroupSegDataMapper {

    fun mapGroupsSeg(
        listGroup: List<GrupoSegEntity>,
        listSeg: List<SegmentacionEntity>
    ): List<GroupsSegmentation> {
        val groupsSeg = arrayListOf<GroupsSegmentation>()
        val lGroups = listGroup.asSequence().mapNotNull { map(it) }.toList()
        val lSegms = listSeg.asSequence().mapNotNull { map(it) }.toList()

        lGroups.forEach { group ->
            val segmentations =
                lSegms.filter { segmentation -> segmentation.codigoGrupoMensaje == group.codigo }
            groupsSeg.add(
                GroupsSegmentation(
                    codigo = group.codigo,
                    nombre = group.nombre,
                    listSeg = segmentations
                )
            )
        }

        return groupsSeg
    }

    private fun map(entity: GrupoSegEntity): GroupSeg? {
        return GroupSeg(
            codigo = entity.codigo,
            nombre = entity.nombre
        )
    }

    private fun map(entity: SegmentacionEntity): Segmentation? {
        return Segmentation(
            codigo = entity.codigo,
            imagenMensaje = entity.imagenMensaje,
            textoMensaje = entity.textoMensaje,
            codigoGrupoMensaje = entity.codigoGrupoMensaje,
            nombre = entity.nombre,
            campanaInicio = entity.campanaInicio,
            campanaFin = entity.campanaFin
        )
    }
}

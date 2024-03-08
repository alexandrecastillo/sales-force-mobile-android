package biz.belcorp.salesforce.modules.virtualmethodology.features.methodology.mappers

import biz.belcorp.salesforce.modules.virtualmethodology.core.domain.entities.Segmentation
import biz.belcorp.salesforce.modules.virtualmethodology.features.methodology.model.SegmentationModel

class SegmentationMapper() {

    fun map(list: List<Segmentation>): List<SegmentationModel> {
        return list.asSequence().map { map(it) }.toList()
    }

    private fun map(segmentation: Segmentation): SegmentationModel {
        return return SegmentationModel(
            codigo = segmentation.codigo ?: 0,
            imagenMensaje = segmentation.imagenMensaje ?: "",
            textoMensaje = segmentation.textoMensaje ?: "",
            codigoGrupoMensaje = segmentation.codigoGrupoMensaje ?: 0,
            nombre = segmentation.nombre ?: "",
            campanaInicio = segmentation.campanaInicio ?: "",
            campanaFin = segmentation.campanaFin ?: ""
        )
    }
}

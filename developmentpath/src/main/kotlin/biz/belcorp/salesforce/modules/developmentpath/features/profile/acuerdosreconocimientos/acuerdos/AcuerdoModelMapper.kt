package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos

import biz.belcorp.salesforce.core.utils.parseToDateItem
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.Acuerdo

class AcuerdoModelMapper {
    fun map(entidad: Acuerdo, editable: Boolean): AcuerdoModel {
        return AcuerdoModel(
            id = entidad.id,
            descripcion = entidad.contenido,
            fecha = entidad.fechaCreacion.parseToDateItem(),
            mostrarEditarAcuerdo = editable,
            mostrarEliminarAcuerdo = editable,
            mostrarGuardarAcuerdo = false,
            mostrarCancelar = false,
            mostrarTextoAcuerdo = true,
            mostrarFechaAcuerdo = true,
            mostrarEdicionTextoAcuerdo = false,
            mostrarLineaInferior = true
        )
    }
}

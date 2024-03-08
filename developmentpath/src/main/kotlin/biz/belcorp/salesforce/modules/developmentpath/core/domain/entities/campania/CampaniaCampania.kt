package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.campania

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.AcuerdosCampania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.Habilidad

class CampaniaCampania(
    val tituloCampania: String,
    val campania: String,
    val acuerdos: List<AcuerdosCampania>,
    val cantidadHabilidades: String,
    val habilidades: List<Habilidad>,
    val porcentaje: Double,
    val proveedorDeVisibilidad: ProveedorDeVisibilidad
) {

    data class ProveedorDeVisibilidad(
        var mostrarEditarAcuerdo: Boolean = false,
        var mostrarCardHabilidades: Boolean = false,
        var mostrarReconocerHabilidad: Boolean = false,
        var mostrarSinHabilidades: Boolean = false,
        var mostrarConHabilidades: Boolean = false
    )
}

package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.model

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades.ObtenerHabilidadesDetalleUseCase

class HabilidadAsignadaDetalleModel(val tipoIcono: Int,
                                    val descripcion: String,
                                    val detalles: List<Detalle>,
                                    val tipoDetalle: ObtenerHabilidadesDetalleUseCase.TipoDetalle,
                                    val comportamientos: List<Comportamiento>) {

    data class Detalle(val descripcion: String)
    data class Comportamiento(val descripcion: String)
}

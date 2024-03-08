package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.presenter

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades.ObtenerHabilidadesDetalleUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.view.DetalleHabilidadesView

class DetalleHabilidadesPresenter(
    private val view: DetalleHabilidadesView,
    private val useCase: ObtenerHabilidadesDetalleUseCase
) {
    fun obtener(personaId: Long, rol: Rol) {
        useCase.obtener(
            ObtenerHabilidadesDetalleUseCase.Request(personaId, rol),
            DetalleHabilidadesSubscriber(view)
        )
    }
}

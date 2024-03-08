package biz.belcorp.salesforce.modules.developmentpath.features.profile.data.desempenio.last6campaigns

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.desempenio.ObtenerDesempenioGrGzUseCase


class DesempenioGrGzPresenter(
    private val view: DesempenioGrGzView,
    private val useCase: ObtenerDesempenioGrGzUseCase,
    private val mapper: DesempenioGrGzModelMapper
) {

    fun obtener(personaId: Long, rol: Rol) {
        useCase.obtener(
            ObtenerDesempenioGrGzUseCase.Request(
                personaId = personaId,
                rol = rol,
                subscriber = DesempenioGzSubscriber(view, mapper)
            )
        )
    }
}

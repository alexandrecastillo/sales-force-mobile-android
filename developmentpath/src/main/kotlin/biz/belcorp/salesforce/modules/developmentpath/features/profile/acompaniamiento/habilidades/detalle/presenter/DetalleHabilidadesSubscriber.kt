package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.presenter

import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.DetalleHabilidadesAcompaniamiento
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades.ObtenerHabilidadesDetalleUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.model.HabilidadAsignadaDetalleModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.detalle.view.DetalleHabilidadesView
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class DetalleHabilidadesSubscriber(val view: DetalleHabilidadesView?) :
    BaseSingleObserver<ObtenerHabilidadesDetalleUseCase.Response>() {

    override fun onSuccess(t: ObtenerHabilidadesDetalleUseCase.Response) {
        doAsync {

            val habilidadAsignadaDetalles = t.habilidadesLiderazgo.map { parse(it, t.tipoDetalle) }

            uiThread {
                view?.cargar(habilidadAsignadaDetalles)
                view?.mostrarSinHabilidades(t.mostrarSinHabilidades)
                view?.habilitarAsignacion(t.habilitarAsignacion)
                view?.mostrarDetalleHabilidades(t.mostrarDetalleHabilidades)
            }
        }
    }

    private fun parse(
        detalleHabilidadesAcompaniamiento: DetalleHabilidadesAcompaniamiento,
        tipoDetalle: ObtenerHabilidadesDetalleUseCase.TipoDetalle
    ): HabilidadAsignadaDetalleModel {
        return HabilidadAsignadaDetalleModel(
            tipoIcono = detalleHabilidadesAcompaniamiento.habilidad.tipoIcono,
            descripcion = detalleHabilidadesAcompaniamiento.habilidad.descripcion
                ?: throw Exception("No se pudo obtenerPorPersonaId descripcion de habilidad"),
            detalles = detalleHabilidadesAcompaniamiento
                .detalleLista.map { HabilidadAsignadaDetalleModel.Detalle(it) },
            comportamientos = detalleHabilidadesAcompaniamiento
                .comportamientoLista.map { HabilidadAsignadaDetalleModel.Comportamiento(it) },
            tipoDetalle = tipoDetalle
        )
    }
}

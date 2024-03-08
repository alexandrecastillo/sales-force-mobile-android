package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.comportamientos.progreso.detalle

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.isNaNToZero
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.comportamientos.ProgresoReconocimientos
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.reconocimiento.ProgresoReconocimientoUseCase
import kotlin.math.roundToInt

class ProgresoReconocimientoPresenter(
    private val view: ProgresoReconocimientoView,
    private val useCase: ProgresoReconocimientoUseCase
) {

    fun obtenerComportamientosSeisUltimasCampanas(personaId: Long, rol: Rol) {
        val request = ProgresoReconocimientoUseCase.Request(
            personaId = personaId,
            rol = rol,
            subscriber = ObtenerProgresoSubscriber()
        )
        useCase.ejecutar(request)
    }

    private inner class ObtenerProgresoSubscriber :
        BaseSingleObserver<ProgresoReconocimientoUseCase.Response>() {
        override fun onSuccess(t: ProgresoReconocimientoUseCase.Response) {
            val nombresCampanias = parsearNombresDeCampania(t)
            val progresos = t.progresosPorComportamiento.map {
                val reconocimientosInvertidos = it.reconocimientos.reversed()
                convertir(it, reconocimientosInvertidos)
            }
            view.pintarCampanias(nombresCampanias)
            view.showComportamientosUltimasSeisCampanias(progresos)
        }

        private fun convertir(
            progreso: ProgresoReconocimientos.ProgresoPorComportamiento,
            reconocimientosInvertidos: List<ProgresoReconocimientos.ReconocimientoDeComportamientoEnCampania>
        ): ProgresoReconocimientoFragment.ProgresoModel {
            return ProgresoReconocimientoFragment.ProgresoModel(
                iconProgreso = progreso.comportamiento.tipoIcono,
                mPorsentage = progreso.avance.porcentaje.isNaNToZero().roundToInt(),
                mDescripcion = progreso.comportamiento.titulo,
                mCapania1 = reconocimientosInvertidos.getOrNull(0)?.reconocido ?: false,
                mCapania2 = reconocimientosInvertidos.getOrNull(1)?.reconocido ?: false,
                mCapania3 = reconocimientosInvertidos.getOrNull(2)?.reconocido ?: false,
                mCapania4 = reconocimientosInvertidos.getOrNull(3)?.reconocido ?: false,
                mCapania5 = reconocimientosInvertidos.getOrNull(4)?.reconocido ?: false,
                mCapania6 = reconocimientosInvertidos.getOrNull(5)?.reconocido ?: false
            )
        }
    }

    private fun parsearNombresDeCampania(response: ProgresoReconocimientoUseCase.Response) =
        response.campanias.reversed().map { eliminarGuionEnNombreCampania(it.nombreCorto) }

    private fun eliminarGuionEnNombreCampania(nombreCorto: String) =
        nombreCorto.replace("-", "")
}

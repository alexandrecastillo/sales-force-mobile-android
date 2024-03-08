package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.comportamientos

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento.TodosLosReconocimientosEnCampania

class ProgresoReconocimientos(
    private val todosLosReconocimientosEnCampanias: List<TodosLosReconocimientosEnCampania>,
    private val comportamientos: List<Comportamiento>
) {

    val totalReconocimientos = todosLosReconocimientosEnCampanias.size
    val reconocimientos = todosLosReconocimientosEnCampanias.flatMap { it.reconocimiento }
    val avancePorComportamiento by lazy { calcularAvancePorComportamiento() }
    val progresoPorComportamiento by lazy { calcularProgresoPorComportamiento() }

    private fun calcularAvancePorComportamiento(): List<Avance> {
        return comportamientos.map { calcularAvancePorUnicoComportamiento(it) }
    }

    private fun calcularAvancePorUnicoComportamiento(comportamiento: Comportamiento): Avance {
        val cantidadReconocida = contarReconocimientosEnTodasLasCampanias(comportamiento.id)
        val porcentaje = totalReconocimientos.takeIf { it != Constant.NUMBER_ZERO }?.let {
            cantidadReconocida * Constant.NUMBER_ONE_HUNDRED.toDouble() / it
        }
        return Avance(comportamiento, porcentaje ?: Constant.ZERO_DECIMAL)
    }

    private fun calcularProgresoPorComportamiento(): List<ProgresoPorComportamiento> {
        return comportamientos.map { calcularProgresoPorUnicoComportamiento(it) }
    }

    private fun calcularProgresoPorUnicoComportamiento(comportamiento: Comportamiento):
        ProgresoPorComportamiento {
        val reconocimientosComportamiento = todosLosReconocimientosEnCampanias.map {
            ReconocimientoDeComportamientoEnCampania(
                campania = it.campania,
                reconocido = it.comportamientoFueReconocido(comportamiento.id)
            )
        }
        return ProgresoPorComportamiento(
            comportamiento = comportamiento,
            avance = calcularAvancePorUnicoComportamiento(comportamiento),
            reconocimientos = reconocimientosComportamiento
        )
    }

    private fun contarReconocimientosEnTodasLasCampanias(comportamientoId: Long): Int {
        return reconocimientos
            .filter { it.reconocido }
            .count { it.comportamiento.id == comportamientoId }
    }

    data class Avance(
        val comportamiento: Comportamiento,
        val porcentaje: Double
    )

    data class ProgresoPorComportamiento(
        val comportamiento: Comportamiento,
        val avance: Avance,
        val reconocimientos: List<ReconocimientoDeComportamientoEnCampania>
    )

    data class ReconocimientoDeComportamientoEnCampania(
        val campania: Campania,
        val reconocido: Boolean
    )
}

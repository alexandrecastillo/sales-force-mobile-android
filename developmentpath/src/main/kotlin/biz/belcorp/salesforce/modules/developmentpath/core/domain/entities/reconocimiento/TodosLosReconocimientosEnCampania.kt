package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.reconocimiento

import biz.belcorp.salesforce.core.domain.entities.campania.Campania

class TodosLosReconocimientosEnCampania(
    val campania: Campania,
    val reconocimiento: List<ReconocimientoComportamiento>,
    val realizado: Boolean = true
) {

    val reconocidos = reconocimiento.count { it.reconocido }

    val total = reconocimiento.size

    val razon = reconocidos * 1.0 / total

    val porcentaje = razon * 100

    fun comportamientoFueReconocido(comportamientoId: Long): Boolean {
        return reconocimiento
            .filter { it.comportamiento.id == comportamientoId }
            .any { it.reconocido }
    }

    companion object {
        fun construirNoRealizado(codigoCampania: String): TodosLosReconocimientosEnCampania {
            return construirNoRealizado(Campania.construirDummy(codigoCampania))
        }

        fun construirNoRealizado(campania: Campania): TodosLosReconocimientosEnCampania {
            return TodosLosReconocimientosEnCampania(campania, emptyList(), false)
        }
    }
}

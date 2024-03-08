package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.historico

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.Acuerdo

class AcuerdosHistoricos(var acuerdos: List<Acuerdo> = emptyList()) {

    val acuerdosNoCumplidos get() = acuerdos.filter { !it.cumplido }
    val acuerdosCumplidos get() = acuerdos.filter { it.cumplido }
    val acuerdosNoCumplidosAgrupados get() = agruparAcuerdosPorCampania(acuerdosNoCumplidos)
    val acuerdosCumplidosAgrupados get() = agruparAcuerdosPorCampania(acuerdosCumplidos)

    private fun agruparAcuerdosPorCampania(acuerdos: List<Acuerdo>): List<AcuerdosPorCampania> {
        val agrupador = AgrupadorAcuerdosPorCampania(acuerdos)
        return agrupador.agrupar()
    }

    fun invertirCumplimiento(acuerdoId: Long): Acuerdo {
        val acuerdo = buscarAcuerdo(acuerdoId)
        acuerdo.cumplido = !acuerdo.cumplido
        return acuerdo
    }

    fun traerAcuerdo(acuerdoId: Long): Acuerdo {
        val acuerdo = buscarAcuerdo(acuerdoId)
        return acuerdo
    }

    private fun buscarAcuerdo(acuerdoId: Long): Acuerdo {
        return requireNotNull(acuerdos.find { it.id == acuerdoId }) {
            "Acuerdo no encontrado"
        }
    }
}

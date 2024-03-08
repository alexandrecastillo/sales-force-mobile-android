package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.consolidado

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.Acuerdo

class CumplimientoCampania(
    val campania: Campania,
    private val cumplimientoPrecalculado: CumplimientoPrecalculado,
    private val acuerdos: List<Acuerdo>
) {

    val estado get() = calcularEstado()

    private fun calcularEstado(): Estado {
        return if (acuerdos.isNotEmpty()) {
            calcularEstadoPromedioDeAcuerdos()
        } else {
            cumplimientoPrecalculado.estado
        }
    }

    private fun calcularEstadoPromedioDeAcuerdos(): Estado {
        acuerdos.forEach {
            if (!it.cumplido) return Estado.NO_CUMPLIDO
        }

        return Estado.CUMPLIDO
    }

    enum class Estado {
        CUMPLIDO,
        NO_CUMPLIDO,
        NINGUNO
    }
}

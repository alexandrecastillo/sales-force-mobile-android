package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta

import biz.belcorp.salesforce.core.utils.diferenciaDeMesesCon
import biz.belcorp.salesforce.core.utils.es
import biz.belcorp.salesforce.core.utils.perteneceAMes
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.Dia
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.DiaDeMes
import java.util.*

class GeneradorDeMeses(
    private val hoy: Calendar,
    private val diasDeCampania: List<Dia>
) {

    var mesSeleccionado: Int = -1

    fun generar(): List<Mes> {
        if (diasDeCampania.isEmpty()) {
            return emptyList()
        }

        val meses = mutableListOf<Mes>()
        val mesFinal = diasDeCampania.last().fecha
        val mesInicial = diasDeCampania.first().fecha
        val cantidadMeses = mesFinal.diferenciaDeMesesCon(mesInicial) + 1
        val mesAGenerar = mesInicial.clone() as Calendar

        while (meses.size < cantidadMeses) {
            val mes = GeneradorDeMes(mesAGenerar.clone() as Calendar, hoy, diasDeCampania).generar()

            verificarMesSeleccionado(mesAGenerar, meses.size)
            meses.add(mes)
            mesAGenerar.add(Calendar.MONTH, 1)
        }

        return meses
    }

    private fun verificarMesSeleccionado(mes: Calendar, indice: Int) {
        if (hoy.perteneceAMes(mes)) {
            mesSeleccionado = indice
        }
    }
}

class GeneradorDeMes(
    private val mes: Calendar,
    private val hoy: Calendar,
    private val diasDeCampania: List<Dia>
) {

    private val DIAS_EN_SEMANA = 7

    fun generar(): Mes {
        val diasDeMes = mutableListOf<DiaDeMes>()
        val mesCalendario = establecerInicioDeMes()
        val semanasEnMes = obtenerSemanasEnMes()
        val diasPorMes = DIAS_EN_SEMANA * semanasEnMes

        while (diasDeMes.size < diasPorMes) {
            val dia = obtenerDiaExistenteOCrearNuevo(mesCalendario)
            val diaDeMes = dia.obtenerDiaDeMes(mes)
            verificarSiEsHoy(diaDeMes.dia)

            diasDeMes.add(diaDeMes)
            mesCalendario.add(Calendar.DAY_OF_MONTH, 1)
        }

        return Mes(mes, diasDeMes)
    }

    private fun establecerInicioDeMes(): Calendar {
        val calendar = mes.clone() as Calendar
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val offset = calendar.get(Calendar.DAY_OF_WEEK) - 1
        calendar.add(Calendar.DAY_OF_MONTH, -offset)

        return calendar
    }

    private fun obtenerSemanasEnMes(): Int {
        return mes.getActualMaximum(Calendar.WEEK_OF_MONTH)
    }

    private fun obtenerDiaExistenteOCrearNuevo(calendario: Calendar): Dia {
        return diasDeCampania
            .find { it.fecha.es(calendario) }
            ?: Dia(
                calendario.clone() as Calendar,
                emptyList(),
                false,
                false,
                false,
                false,
                false,
                emptyList()
            )
    }

    private fun verificarSiEsHoy(dia: Dia) {
        if (dia.fecha.es(hoy)) {
            dia.esHoy = true
        }
    }
}

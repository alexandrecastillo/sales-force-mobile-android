package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.buscador


import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.Dia

class BuscadorRdd(
    val dias: List<Dia>,
    private val personasNoPlanificadas: List<PersonaRdd>
) {

    fun buscar(filtro: String = Constant.EMPTY_STRING): Resultado {
        return Resultado(
            buscarEnDias(dias, filtro),
            personasNoPlanificadas.filter { it.match(filtro) })
    }

    private fun buscarEnDias(dias: List<Dia>, filtro: String): List<Dia> {
        val resultado = mutableListOf<Dia>()
        dias.forEach { dia ->
            val visitasProgramadasFiltradas = dia.visitasProgramadas.filter { it.match(filtro) }
            if (visitasProgramadasFiltradas.isNotEmpty()) {
                resultado.add(dia.copy(visitasProgramadas = visitasProgramadasFiltradas))
            }
        }
        return resultado
    }

    class Resultado(
        val dias: List<Dia>,
        val personasNoPlanificadas: List<PersonaRdd>
    )

    interface Buscable {
        fun match(filtro: String): Boolean {
            return filtro.isBlank()
        }

        fun sugerir(): String?
    }
}

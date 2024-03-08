package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.historico

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.Acuerdo

class AgrupadorAcuerdosPorCampania(val acuerdos: List<Acuerdo>) {

    fun agrupar(): List<AcuerdosPorCampania> {
        val resultado = mutableListOf<AcuerdosPorCampania>()
        val campanias = recuperarCampaniasDeAcuerdos(acuerdos)
        campanias.forEach { campania ->
            val acuerdos = acuerdos.filter { acuerdo -> acuerdo.codigoCampania == campania.codigo }
            resultado.add(AcuerdosPorCampania(campania, acuerdos))
        }
        return resultado
    }

    private fun recuperarCampaniasDeAcuerdos(acuerdos: List<Acuerdo>): List<Campania> {
        val campanias = mutableListOf<Campania>()
        acuerdos.forEach { acuerdo ->
            val campania = campanias.find { campania -> campania.codigo == acuerdo.codigoCampania }
            if (campania == null) campanias.add(crearCampania(acuerdo.codigoCampania))
        }
        return campanias
    }

    private fun crearCampania(codigoCampania: String): Campania {
        return Campania.construirDummy(codigoCampania)
    }
}

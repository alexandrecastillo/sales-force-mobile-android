package biz.belcorp.salesforce.modules.postulants.core.domain.enums

import biz.belcorp.salesforce.core.domain.entities.pais.Pais

enum class UneteConfig(var pais: String, val pasos: Int) {

    CO(Pais.COLOMBIA.codigoIso, 4),
    CR(Pais.COSTARICA.codigoIso, 4),
    GT(Pais.GUATEMALA.codigoIso, 4),
    PA(Pais.PANAMA.codigoIso, 4),
    SV(Pais.SALVADOR.codigoIso, 4),
    DO(Pais.DOMINICANA.codigoIso, 5),
    PR(Pais.PUERTORICO.codigoIso, 5),
    BO(Pais.BOLIVIA.codigoIso, 5),
    MX(Pais.MEXICO.codigoIso, 5),
    PE(Pais.PERU.codigoIso, 5),
    EC(Pais.ECUADOR.codigoIso, 5),
    CL(Pais.CHILE.codigoIso, 5);

    companion object {
        fun find(pais: String): UneteConfig? {
            values().forEach {
                if (it.pais == pais)
                    return it
            }
            return null
        }
    }
}

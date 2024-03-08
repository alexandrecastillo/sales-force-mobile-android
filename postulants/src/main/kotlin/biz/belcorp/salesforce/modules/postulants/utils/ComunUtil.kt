package biz.belcorp.salesforce.modules.postulants.utils

import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.enums.Marca


object ComunUtil {

    fun getBrandFocus(iso: String): Int {
        return when (iso) {
            Pais.BOLIVIA.codigoIso, Pais.CHILE.codigoIso, Pais.COLOMBIA.codigoIso,
            Pais.DOMINICANA.codigoIso, Pais.ECUADOR.codigoIso, Pais.GUATEMALA.codigoIso,
            Pais.PERU.codigoIso, Pais.SALVADOR.codigoIso -> Marca.ESIKA.codigo
            Pais.COSTARICA.codigoIso, Pais.MEXICO.codigoIso, Pais.PANAMA.codigoIso,
            Pais.PUERTORICO.codigoIso -> Marca.LBEL.codigo
            else -> Marca.LBEL.codigo
        }
    }
}

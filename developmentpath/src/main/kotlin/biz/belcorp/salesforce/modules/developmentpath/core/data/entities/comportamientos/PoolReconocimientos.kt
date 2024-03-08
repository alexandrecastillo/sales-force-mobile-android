package biz.belcorp.salesforce.modules.developmentpath.core.data.entities.comportamientos

import biz.belcorp.salesforce.core.entities.sql.comportamientos.ComportamientoDetalleEntity

class PoolReconocimientos(private val reconocimientos: List<ComportamientoDetalleEntity>) {
    fun buscar(codigoCampania: String): ComportamientoDetalleEntity? {
        return reconocimientos.find { it.campania == codigoCampania }
    }
}

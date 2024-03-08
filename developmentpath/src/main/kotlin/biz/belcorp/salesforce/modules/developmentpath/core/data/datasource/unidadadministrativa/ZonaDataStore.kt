package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.unidadadministrativa

import biz.belcorp.salesforce.core.entities.sql.ua.ZonaGzJoinned

interface ZonaDataStore {
    fun recuperarAvances(codigoRegion: String): List<ZonaGzJoinned>
}

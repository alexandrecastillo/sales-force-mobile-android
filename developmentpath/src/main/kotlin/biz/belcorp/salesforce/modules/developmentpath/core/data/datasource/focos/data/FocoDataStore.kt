package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.CabeceraFoco
import io.reactivex.Observable

interface FocoDataStore {

    fun obtener(segmentoId: Int): Observable<List<CabeceraFoco>>
    fun hasData(segmentoId: Int): Observable<Boolean>
}

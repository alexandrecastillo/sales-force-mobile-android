package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.focos

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.CabeceraFoco
import io.reactivex.Observable

interface FocoRepository {
    fun obtener(segmentoId: Int): Observable<List<CabeceraFoco>>
    fun hasData(segmentoId: Int): Observable<Boolean>
}

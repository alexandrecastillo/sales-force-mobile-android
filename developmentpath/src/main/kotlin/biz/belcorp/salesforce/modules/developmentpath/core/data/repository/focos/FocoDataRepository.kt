package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.focos

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.focos.data.FocoDBStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.CabeceraFoco
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.focos.FocoRepository
import io.reactivex.Observable

class FocoDataRepository(private val focoDBStore: FocoDBStore) : FocoRepository {

    override fun hasData(segmentoId: Int): Observable<Boolean> {
        return focoDBStore.hasData(segmentoId)
    }

    override fun obtener(segmentoId: Int): Observable<List<CabeceraFoco>> {
        return focoDBStore.obtener(segmentoId)
    }
}

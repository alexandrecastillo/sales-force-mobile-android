package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.tipsdesarrollo.caminobrillante

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.tipsdesarrollo.caminobrillante.ProgresoCaminoBrillanteDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.caminobrillante.ProgresoCaminoBrillanteMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.caminobrillante.NivelActualCaminoBrillante
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.caminobrillante.NivelCaminoBrillante
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo.ProgresoCaminoBrillanteRepository
import io.reactivex.Single

class ProgresoCaminoBrillanteDataRepository(
    private val dataStore: ProgresoCaminoBrillanteDataStore,
    private val mapper: ProgresoCaminoBrillanteMapper
) : ProgresoCaminoBrillanteRepository {

    override fun obtenerNivelActual(llaveUA: LlaveUA): Single<NivelActualCaminoBrillante> {
        return doOnSingle {
            val nivelActual = dataStore.obtenerProgreso(llaveUA)
            mapper.parse(nivelActual)
        }
    }

    override fun obtenerNiveles(): Single<List<NivelCaminoBrillante>> {
        return doOnSingle {
            val niveles = dataStore.obtenerNiveles()
            niveles.map { mapper.parse(it) }
        }
    }
}

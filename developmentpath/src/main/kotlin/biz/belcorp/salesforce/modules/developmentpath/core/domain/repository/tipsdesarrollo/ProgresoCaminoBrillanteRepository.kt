package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.caminobrillante.NivelActualCaminoBrillante
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.caminobrillante.NivelCaminoBrillante
import io.reactivex.Single

interface ProgresoCaminoBrillanteRepository {
    fun obtenerNivelActual(llaveUA: LlaveUA): Single<NivelActualCaminoBrillante>
    fun obtenerNiveles(): Single<List<NivelCaminoBrillante>>
}

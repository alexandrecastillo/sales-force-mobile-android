package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.indicadores

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.indicador.IndicadorRddArgsDV
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.VisitasPropias
import io.reactivex.Single

interface DvRDDIndicatorRepository {

    fun obtenerIndicadorVisitasPropias(): Single<VisitasPropias>
    fun obtenerIndicadorVisitasEquipo(campania : String, ua : String): Single<IndicadorRddArgsDV>
}

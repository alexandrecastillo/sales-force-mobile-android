package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.graficos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.GraficoGr
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TipoGrafico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TopBottomHistorico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.historicos.HistoricoRegion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.historicos.ZonasProductividad
import io.reactivex.Completable
import io.reactivex.Single

interface GraficosGrRepository {
    fun obtenerTitulosGraficos(llaveUA: LlaveUA): Single<List<GraficoGr>>
    fun obtenerGraficos(llaveUA: LlaveUA): Completable
    fun obtenerGrafico(llaveUA: LlaveUA, tipoGrafico: TipoGrafico): Single<HistoricoRegion>
    fun obtenerBloqueProductiva(llaveUA: LlaveUA): Single<ZonasProductividad>
    fun obtenerBloqueTopBottomPorTipoGrafico(llaveUA: LlaveUA, tipoGrafico: TipoGrafico): Single<TopBottomHistorico>
}

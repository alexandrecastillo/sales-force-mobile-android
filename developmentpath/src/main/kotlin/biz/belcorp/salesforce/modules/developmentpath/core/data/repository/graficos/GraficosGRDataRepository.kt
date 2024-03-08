package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.graficos.cloud.GraficosGRCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.graficos.data.GraficosGRLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.graficos.GraficoGrMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.graficos.GraficoGrData
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.GraficoGr
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TipoGrafico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.TopBottomHistorico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.historicos.HistoricoRegion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.historicos.ZonasProductividad
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.graficos.GraficosGrRepository
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Single

class GraficosGRDataRepository(private val graficosGRLocalDataStore: GraficosGRLocalDataStore,
                               private val graficosGRCloudDataStore: GraficosGRCloudDataStore,
                               private val graficoGrMapper: GraficoGrMapper
) : GraficosGrRepository {

    val gson = Gson()

    override fun obtenerGraficos(llaveUA: LlaveUA): Completable {
        return graficosGRCloudDataStore.descargarGraficos(llaveUA)
            .flatMapCompletable {
                graficosGRLocalDataStore.borrarDatosPrevios()
                    .andThen(guardarGraficos(llaveUA, it.graficosGr))
                    .andThen(actualizarGraficos(llaveUA, it.graficosGr))
            }
    }

    private fun guardarGraficos(llaveUA: LlaveUA, graficosGr: List<GraficoGrData>?): Completable {
        return if (graficosGr.isNullOrEmpty()) {
            Completable.complete()
        } else {
            graficosGRLocalDataStore.guardarGraficosPorUA(llaveUA, gson.toJson(graficosGr))
        }
    }

    private fun actualizarGraficos(llaveUA: LlaveUA, graficosGr: List<GraficoGrData>?): Completable {
        return if (graficosGr.isNullOrEmpty()) {
            Completable.complete()
        } else {
            Completable.create { emitter ->
                graficosGr.forEach {
                    graficosGRLocalDataStore.actualizarTituloGrafico(llaveUA, it.tipoGrafico
                        ?: 0, it.valor ?: "")
                }
                emitter.onComplete()
            }
        }
    }

    override fun obtenerTitulosGraficos(llaveUA: LlaveUA): Single<List<GraficoGr>> {
        return graficosGRLocalDataStore
            .obtenerTitulosGraficos(llaveUA).map { listaTituloGrafico ->
                listaTituloGrafico.map { graficoGrMapper.parseTituloGrafico(it) }
            }
    }

    override fun obtenerBloqueProductiva(llaveUA: LlaveUA): Single<ZonasProductividad> {
        return graficosGRLocalDataStore.obtenerGraficosGrPorUA(llaveUA)
            .map {
                graficoGrMapper.parseProductivas(it, TipoGrafico.PRODUCTIVAS)
            }
    }

    override fun obtenerBloqueTopBottomPorTipoGrafico(llaveUA: LlaveUA, tipoGrafico: TipoGrafico): Single<TopBottomHistorico> {
        return graficosGRLocalDataStore.obtenerGraficosGrPorUA(llaveUA)
            .map { graficoGrMapper.parsetoTopBottom(it, tipoGrafico.codigo) }
    }

    override fun obtenerGrafico(llaveUA: LlaveUA, tipoGrafico: TipoGrafico): Single<HistoricoRegion> {
        return graficosGRLocalDataStore.obtenerGraficosGrPorUA(llaveUA)
            .map { graficoGrMapper.parseGraficoEspecifico(it, tipoGrafico.codigo) }
    }
}

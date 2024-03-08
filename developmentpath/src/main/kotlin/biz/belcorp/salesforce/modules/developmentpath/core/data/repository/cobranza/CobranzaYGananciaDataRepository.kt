package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.cobranza

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.datos.CobranzaYGananciaEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.cobranza.cloud.CobranzaYGananciaCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.cobranza.data.CobranzaYGananciaLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.cobranza.CobranzaYGananciaDataMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.cobranza.CobranzaYGanancia
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.cobranza.CobranzaYGananciaRepository
import io.reactivex.Flowable
import io.reactivex.Single

class CobranzaYGananciaDataRepository(
    private val cobranzaYGananciaLocalDataStore: CobranzaYGananciaLocalDataStore,
    private val cloudDataStore: CobranzaYGananciaCloudDataStore,
    private val dataMapper: CobranzaYGananciaDataMapper
) : CobranzaYGananciaRepository {

    override fun obtenerCobranzaGanancia(llaveUa: LlaveUA): Flowable<CobranzaYGanancia> {
        return obtener(llaveUa).concatWith(obtenerCloud(llaveUa))
    }

    private fun obtener(llaveUa: LlaveUA): Single<CobranzaYGanancia> {
        return cobranzaYGananciaLocalDataStore.obtenerDbCobranza(llaveUa)
            .map {
                dataMapper.parseLocal(it)
            }
    }

    private fun obtenerCloud(llaveUa: LlaveUA): Single<CobranzaYGanancia> {
        return cloudDataStore.obtener(llaveUa)
            .filter {
                it.resultado != null
            }
            .flatMapSingle {
                actualizarDb(it.resultado!!.first())
            }
            .onErrorResumeNext {
                obtener(llaveUa)
            }
    }

    private fun actualizarDb(cobranzaYGanancia: CobranzaYGananciaEntity): Single<CobranzaYGanancia> {
        return cobranzaYGananciaLocalDataStore.actualizarDb(cobranzaYGanancia)
            .map { dataMapper.parseService(it) }
    }
}

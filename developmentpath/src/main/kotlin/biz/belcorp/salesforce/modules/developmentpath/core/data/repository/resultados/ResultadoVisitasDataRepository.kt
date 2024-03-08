package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.resultados

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.resultadovisita.ResultadoVisitasDbStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.SyncRestApi
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.resultados.ResultadoVisitasRepository
import io.reactivex.Single

class ResultadoVisitasDataRepository(
    private val resultadoVisitasDbStore: ResultadoVisitasDbStore,
    private val syncRestApi: SyncRestApi
) : ResultadoVisitasRepository {

    override fun recuperarCantidadConsultorasFacturadas(): Long {
        return resultadoVisitasDbStore.recuperarCantidadConsultorasFacturadas()
    }

    override fun recuperarCantidadConsultorasNoFacturadas(): Long {
        return resultadoVisitasDbStore.recuperarCantidadConsultorasNoFacturadas()
    }

    override fun recuperarConsultorasFacturadas(): List<ConsultoraRdd> {
        return resultadoVisitasDbStore.recuperarConsultorasFacturadas()
    }

    override fun recuperarConsultorasNoFacturadas(): List<ConsultoraRdd> {
        return resultadoVisitasDbStore.recuperarConsultorasNoFacturadas()
    }

    override fun recuperarMontosDeConsultoras(
        campania: String,
        ua: LlaveUA
    ): Single<Map<Long, Double>> {
        return syncRestApi
            .consultorasConPedidoIngresado(
                campania,
                ua.codigoRegion,
                ua.codigoZona,
                ua.codigoSeccion
            )
            .map { response -> response.montos.associateBy({ it.id }, { it.monto }) }
    }
}

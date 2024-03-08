package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.plan

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.plan.cloud.PlanDetalleCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.plan.data.PlanDetalleLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.online.PlanDetalleRepository
import io.reactivex.Completable

class PlanDetalleDataRepository(
    private val database: PlanDetalleLocalDataStore,
    private val cloud: PlanDetalleCloudDataStore
) : PlanDetalleRepository {

    override fun sincronizar(planId: Long): Completable {
        return cloud.obtener(planId).flatMapCompletable { planDetalle ->
            database.crearOActualizarDetallePlanRuta(planDetalle.response?.detallesPlanRutaRDD)
                .andThen(database.crearOActualizarVisitaPorFecha(planDetalle.response?.visitasPorFecha))
                .andThen(database.crearOActualizarFechasNoHabiles(planDetalle.response?.fechaNoHabilFacturacion))
                .andThen(database.crearOActualizarEventos(planDetalle.response?.eventosRdd))
                .andThen(database.crearOActualizarEventosXUA(planDetalle.response?.eventosXUaRdd))
        }
    }
}

package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.plan.data

import biz.belcorp.salesforce.core.entities.sql.eventos.EventoRddEntity
import biz.belcorp.salesforce.core.entities.sql.eventos.EventoRddXUaEntity
import biz.belcorp.salesforce.core.entities.sql.path.VisitaXFechaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.plan.DetallePlanRutaRDDEntity
import biz.belcorp.salesforce.core.entities.sql.plan.FechaNoHabilFacturacionEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.eventos.data.EventosDataLocalStore
import com.raizlabs.android.dbflow.kotlinextensions.processInTransaction
import io.reactivex.Completable

class PlanDetalleLocalDataStore(private val eventosxUaDatabase: EventosDataLocalStore) {

    fun crearOActualizarDetallePlanRuta(detallesPlanRuta: List<DetallePlanRutaRDDEntity>?): Completable {
        detallesPlanRuta?.processInTransaction { detallePlanRutaRDDEntity, _ ->
            detallePlanRutaRDDEntity.save()
        }
        return Completable.complete()
    }

    fun crearOActualizarVisitaPorFecha(visitasPorFecha: List<VisitaXFechaRDDEntity>?): Completable {
        visitasPorFecha?.processInTransaction { visitaPorFecha, _ ->
            visitaPorFecha.save()
        }
        return Completable.complete()
    }

    fun crearOActualizarFechasNoHabiles(fechasNoHabiles: List<FechaNoHabilFacturacionEntity>?): Completable {
        fechasNoHabiles?.processInTransaction { fechaNoHabil, _ -> fechaNoHabil.save() }

        return Completable.complete()
    }

    fun crearOActualizarEventos(eventos: List<EventoRddEntity>?): Completable {
        eventos?.processInTransaction { evento, _ -> evento.save() }

        return Completable.complete()
    }

    fun crearOActualizarEventosXUA(eventosXUA: List<EventoRddXUaEntity>?): Completable {
        if (eventosXUA == null) return Completable.complete()

        return eventosxUaDatabase.guardarEventosXUa(eventosXUA)
    }

}

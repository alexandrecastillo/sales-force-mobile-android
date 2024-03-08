package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.eventos.data

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.eventos.*
import biz.belcorp.salesforce.core.utils.aGuionSiEsNullOVacio
import biz.belcorp.salesforce.core.utils.doOnCompletable
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.core.utils.save
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.map
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toStringDate
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Select
import com.raizlabs.android.dbflow.sql.language.Update
import io.reactivex.Completable
import io.reactivex.Single

class EventosDataLocalStore(private val eventoXUaLocalStore: EventosXUaDataLocalStore) {

    fun crearEvento(evento: EventoRddEntity): Single<Long> {
        return doOnSingle {
            evento.marcarseComoNuevo()
            evento.insert()
        }
    }

    fun crearEventoXUa(eventoXUa: EventoRddXUaEntity): Completable {
        return doOnCompletable {
            eventoXUa.marcarseComoNuevo()
            eventoXUa.marcarseComoCreador()
            eventoXUa.insert()
        }
    }

    fun editarEvento(evento: EventoRddEntity): Completable {
        return doOnCompletable {
            evento.actualizar()
        }
    }

    fun eliminarEvento(eventoId: Long): Completable {
        return doOnCompletable {
            eliminar(eventoId)
        }
    }

    fun recuperar(eventoId: Long): Single<EventoTipoEventoJoined> {
        return doOnSingle { recuperarSincrono(eventoId) }
    }

    fun recuperarSincrono(eventoId: Long): EventoTipoEventoJoined {
        val query = (seleccionarColumnasEventoXUa()
            from EventoRddEntity::class
            innerJoin EventoRddXUaEntity::class
            on (EventoRddXUaEntity_Table.IdEventoLocal.withTable()
            eq EventoRddEntity_Table.Id.withTable())
            innerJoin TipoEventoRddEntity::class
            on (EventoRddEntity_Table.IdTipo.withTable()
            eq TipoEventoRddEntity_Table.Id.withTable())
            where (EventoRddXUaEntity_Table.Id.withTable()
            eq eventoId))

        return requireNotNull(query.queryCustomSingle(EventoTipoEventoJoined::class.java))
    }

    private fun seleccionarColumnasEventoXUa(): Select {
        return Select(
            EventoRddXUaEntity_Table.Id.withTable(),
            EventoRddEntity_Table.FechaInicio.withTable(),
            EventoRddEntity_Table.FechaFin.withTable(),
            EventoRddEntity_Table.TodoElDia.withTable(),
            EventoRddEntity_Table.IdTipo.withTable(),
            EventoRddEntity_Table.DescripcionPersonalizada.withTable(),
            EventoRddEntity_Table.CompartirObligatorioInicial.withTable(),
            EventoRddEntity_Table.Alertar.withTable(),
            EventoRddEntity_Table.TiempoAlerta.withTable(),
            EventoRddEntity_Table.UnidadTiempoAlerta.withTable(),
            EventoRddEntity_Table.Ubicacion.withTable(),
            TipoEventoRddEntity_Table.Descripcion.withTable(),
            TipoEventoRddEntity_Table.CompartirObligatorio.withTable(),
            TipoEventoRddEntity_Table.AceptaDescripcionPersonalizada.withTable(),
            TipoEventoRddEntity_Table.Rol.withTable(),
            EventoRddXUaEntity_Table.Region.withTable(),
            EventoRddXUaEntity_Table.Zona.withTable(),
            EventoRddXUaEntity_Table.Seccion.withTable(),
            EventoRddXUaEntity_Table.Activo.withTable(),
            EventoRddXUaEntity_Table.IndicaCumplimiento.withTable(),
            EventoRddXUaEntity_Table.FechaCumplimiento.withTable()
        )
    }

    fun esUaCreadora(llaveUA: LlaveUA, eventoId: Long): Single<Boolean> {
        return Single.create {
            val query = (select from EventoRddXUaEntity::class
                where (EventoRddXUaEntity_Table.IdEventoLocal eq eventoId)
                and (EventoRddXUaEntity_Table.Region eq llaveUA.codigoRegion)
                and (EventoRddXUaEntity_Table.Zona eq llaveUA.codigoZona)
                and (EventoRddXUaEntity_Table.Seccion eq llaveUA.codigoSeccion))

            val eventoXUa = requireNotNull(query.querySingle())

            it.onSuccess(eventoXUa.esCreador)
        }
    }

    private fun EventoRddEntity.actualizar() {
        val query = Update(EventoRddEntity::class.java)
            .set(
                EventoRddEntity_Table.FechaInicio eq fechaInicio,
                EventoRddEntity_Table.FechaFin eq fechaFin,
                EventoRddEntity_Table.TodoElDia eq todoElDia,
                EventoRddEntity_Table.IdTipo eq idTipo,
                EventoRddEntity_Table.DescripcionPersonalizada eq descripcionPersonalizada,
                EventoRddEntity_Table.CompartirObligatorioInicial eq compartirObligatorioInicial,
                EventoRddEntity_Table.Alertar eq alertar,
                EventoRddEntity_Table.TiempoAlerta eq tiempoAlerta,
                EventoRddEntity_Table.UnidadTiempoAlerta eq unidadTiempoAlerta,
                EventoRddEntity_Table.Ubicacion eq ubicacion,
                EventoRddEntity_Table.Activo eq activo,
                EventoRddEntity_Table.Enviado eq false,
                EventoRddEntity_Table.FechaModificacion eq java.util.Calendar.getInstance().toStringDate()
            )
            .where(EventoRddEntity_Table.Id eq id)

        query.execute()
    }

    private fun eliminar(eventoId: Long) {
        val query = Update(EventoRddEntity::class.java)
            .set(
                EventoRddEntity_Table.Activo eq false,
                EventoRddXUaEntity_Table.Enviado eq false
            )
            .where(EventoRddEntity_Table.Id eq eventoId)

        query.execute()
    }

    fun recuperarNuevosNoEnviados(): Single<List<EventoRddEntity>> {
        return Single.create {
            val query = (
                select
                    from EventoRddEntity::class
                    where (EventoRddEntity_Table.Enviado eq false
                    and (EventoRddEntity_Table.DesdeServidor eq false)
                    and (EventoRddXUaEntity_Table.Activo eq true)))

            it.onSuccess(query.queryList())
        }
    }

    fun recuperarEditadosNoEnviados(): Single<List<EventoRddEntity>> {
        return doOnSingle {
            val query = (
                select
                    from EventoRddEntity::class
                    where (EventoRddEntity_Table.Enviado eq false
                    and (EventoRddEntity_Table.DesdeServidor eq true)))

            query.queryList()
        }
    }

    fun marcarComoEnviados(eventos: List<EventoRddEntity>): Completable {
        return doOnCompletable {
            eventos.forEach { it.marcarseComoEnviado() }
            eventos.save()
        }
    }

    fun eliminarEventosXUaPorEventoId(eventoId: Long): Completable {
        return doOnCompletable {
            val eventosXUa = (select
                from EventoRddXUaEntity::class
                where (EventoRddXUaEntity_Table.IdEventoLocal eq eventoId)).queryList()

            eventosXUa.forEach { it.eliminar() }
        }
    }

    fun guardarEventosXUa(eventosXUa: List<EventoRddXUaEntity>): Completable {
        return doOnCompletable {
            eventosXUa.forEach { it.guardar() }
        }
    }

    private fun EventoRddXUaEntity.guardar() {
        if (existePorEventoYUa())
            actualizar()
        else
            insertar()
    }

    private fun EventoRddXUaEntity.existePorEventoYUa(): Boolean {
        val countQuery =
            (Select(com.raizlabs.android.dbflow.sql.language.Method.count()) from EventoRddXUaEntity::class
                where (EventoRddXUaEntity_Table.IdEventoLocal eq idEventoLocal)
                and (EventoRddXUaEntity_Table.Region eq region)
                and (EventoRddXUaEntity_Table.Zona eq zona)
                and (EventoRddXUaEntity_Table.Seccion eq seccion))

        return (countQuery.longValue() > 0L)
    }

    private fun EventoRddXUaEntity.insertar() {
        marcarseComoDesdeServidor()
        save()
    }

    private fun EventoRddXUaEntity.eliminar() {
        marcarseComoEliminado()
        save()
    }

    private fun EventoRddXUaEntity.actualizar() {
        com.raizlabs.android.dbflow.sql.language.Update(EventoRddXUaEntity::class.java)
            .set(
                EventoRddXUaEntity_Table.FechaModificacion eq fechaModificacion,
                EventoRddXUaEntity_Table.IdEventoServer eq idEventoServer,
                EventoRddXUaEntity_Table.IdServidor eq idServidor,
                EventoRddXUaEntity_Table.Activo eq activo,
                EventoRddXUaEntity_Table.Asistira eq asistira,
                EventoRddXUaEntity_Table.UsuarioModificacion eq usuarioModificacion,
                EventoRddXUaEntity_Table.IndicaCumplimiento eq indicaCumplimiento,
                EventoRddXUaEntity_Table.FechaCumplimiento eq fechaCumplimiento,
                EventoRddXUaEntity_Table.DesdeServidor eq true,
                EventoRddXUaEntity_Table.EsCreador eq true,
                EventoRddXUaEntity_Table.Enviado eq true
            )
            .where(
                EventoRddXUaEntity_Table.IdEventoLocal eq idEventoLocal
                    and (EventoRddXUaEntity_Table.Region eq region)
                    and (EventoRddXUaEntity_Table.Zona eq zona)
                    and (EventoRddXUaEntity_Table.Seccion eq seccion)
            ).execute()
    }


    fun recuperarPorUa(llaveUA: LlaveUA): List<EventoTipoEventoJoined> {
        val where = (seleccionarColumnasEventoXUa()
            from EventoRddEntity::class
            innerJoin EventoRddXUaEntity::class
            on (EventoRddEntity_Table.Id.withTable()
            eq EventoRddXUaEntity_Table.IdEventoLocal.withTable())
            innerJoin TipoEventoRddEntity::class
            on (EventoRddEntity_Table.IdTipo.withTable()
            eq TipoEventoRddEntity_Table.Id.withTable())
            where (EventoRddXUaEntity_Table.Region.withTable()
            eq llaveUA.codigoRegion.aGuionSiEsNullOVacio())
            and (EventoRddXUaEntity_Table.Zona.withTable()
            eq llaveUA.codigoZona.aGuionSiEsNullOVacio())
            and (EventoRddXUaEntity_Table.Seccion.withTable()
            eq llaveUA.codigoSeccion.aGuionSiEsNullOVacio())
            and (EventoRddEntity_Table.Activo.withTable() eq true)
            and (EventoRddXUaEntity_Table.Activo.withTable() eq true)
            and (EventoRddXUaEntity_Table.Asistira.withTable() eq true))

        return where.queryCustomList(EventoTipoEventoJoined::class.java)
    }

    fun recuperarEventoId(eventoXUaId: Long): Single<Long> {
        return doOnSingle {
            val query = (select from EventoRddXUaEntity::class
                where (EventoRddXUaEntity_Table.Id eq eventoXUaId))
            requireNotNull(query.querySingle()?.idEventoLocal)
        }
    }

    fun guardarYRecuperar(
        eventos: List<EventoRddEntity>,
        eventosXUa: List<EventoRddXUaEntity>
    ):
        Single<Triple<List<EventoTipoEventoJoined>, List<EventoTipoEventoJoined>, List<EventoTipoEventoJoined>>> {

        return doOnSingle {
            guardarCambioEnEventos(eventos)
            val trioIds = eventoXUaLocalStore.guardarEventosXUaCambiados(eventosXUa)
            val trioEventosJoinned = trioIds.map { recuperarSincrono(it) }
            trioEventosJoinned
        }
    }

    private fun guardarCambioEnEventos(eventos: List<EventoRddEntity>) {
        eventos.forEach {
            if (it.existePorIdDeServer())
                it.actualizarPorIdServer()
            else
                it.save()
        }
    }

    private fun EventoRddEntity.existePorIdDeServer(): Boolean {
        val query = (select
            from EventoRddEntity::class
            where (EventoRddEntity_Table.IdServidor eq idServidor))

        return (query.queryList().size > Constant.CERO)
    }

    private fun EventoRddEntity.actualizarPorIdServer() {
        com.raizlabs.android.dbflow.sql.language.Update(EventoRddEntity::class.java)
            .set(
                EventoRddEntity_Table.FechaInicio eq fechaInicio,
                EventoRddEntity_Table.FechaFin eq fechaFin,
                EventoRddEntity_Table.TodoElDia eq todoElDia,
                EventoRddEntity_Table.IdTipo eq idTipo,
                EventoRddEntity_Table.DescripcionPersonalizada eq descripcionPersonalizada,
                EventoRddEntity_Table.CompartirObligatorioInicial eq compartirObligatorioInicial,
                EventoRddEntity_Table.FechaModificacion eq fechaModificacion,
                EventoRddEntity_Table.Alertar eq alertar,
                EventoRddEntity_Table.TiempoAlerta eq tiempoAlerta,
                EventoRddEntity_Table.UnidadTiempoAlerta eq unidadTiempoAlerta,
                EventoRddEntity_Table.Ubicacion eq ubicacion,
                EventoRddEntity_Table.Activo eq activo,
                EventoRddEntity_Table.Enviado eq true,
                EventoRddEntity_Table.DesdeServidor eq true
            )
            .where(
                EventoRddEntity_Table.IdServidor eq idServidor
            ).execute()
    }
}

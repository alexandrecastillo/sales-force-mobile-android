package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.eventos.data

import biz.belcorp.salesforce.core.entities.sql.eventos.EventoRddXUaEntity
import biz.belcorp.salesforce.core.entities.sql.eventos.EventoRddXUaEntity_Table
import biz.belcorp.salesforce.core.utils.addIfNotNull
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.ConfirmarEventoAtraccionUseCase
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Update
import io.reactivex.Completable
import io.reactivex.Single

class EventosXUaDataLocalStore(private val administrador: AdministradorEventosXUaCambiados) {

    fun rechazarEvento(eventoXUaId: Long, usuario: String): Completable {
        return Completable.create {
            val evento = recuperarSincrono(eventoXUaId)
            evento?.marcarseComoRechazado()
            evento?.asignarUsuario(usuario)
            evento?.save()
            it.onComplete()
        }
    }

    fun registrarEvento(t: ConfirmarEventoAtraccionUseCase.RegistrarEventoParams): Completable {
        return Completable.create {
            val evento = recuperarSincrono(t.eventoXUaId)
            evento?.marcarComoRegistrado(t.fechaRegistro)
            evento?.asignarUsuario(t.usuarioModificacion)
            evento?.save()
            it.onComplete()
        }
    }

    fun recuperarNoEnviados(): Single<List<EventoRddXUaEntity>> {
        return Single.create {
            val query = (select
                from (EventoRddXUaEntity::class)
                where (EventoRddXUaEntity_Table.Enviado eq false)
                and (EventoRddXUaEntity_Table.DesdeServidor eq true))

            it.onSuccess(query.queryList())
        }
    }

    fun marcarAEnviados(): Completable {
        return Completable.create {
            Update(EventoRddXUaEntity::class.java)
                .set(EventoRddXUaEntity_Table.Enviado eq true)
                .where(EventoRddXUaEntity_Table.Enviado eq false).execute()

            it.onComplete()
        }
    }

    fun determinarEsOrganizador(eventoXUaId: Long): Single<Boolean> {
        return Single.create {
            val evento = requireNotNull(recuperarSincrono(eventoXUaId))
            it.onSuccess(evento.esCreador)
        }
    }

    fun recuperar(eventoXUaId: Long): Single<EventoRddXUaEntity> {
        return Single.create {
            val evento = requireNotNull(recuperarSincrono(eventoXUaId))
            it.onSuccess(evento)
        }
    }

    fun recuperarEventoId(eventoXUaId: Long): Single<Long> {
        return Single.create {
            val evento = requireNotNull(recuperarSincrono(eventoXUaId))
            it.onSuccess(evento.idEventoLocal)
        }
    }

    private fun recuperarSincrono(eventoXUaId: Long): EventoRddXUaEntity? {
        val query = (select from EventoRddXUaEntity::class
            where (EventoRddXUaEntity_Table.Id eq eventoXUaId))

        return query.querySingle()
    }

    fun guardarEventosXUaCambiados(eventosXUa: List<EventoRddXUaEntity>):
        Triple<List<Long>, List<Long>, List<Long>> {

        val nuevos = mutableListOf<Long>()
        val editados = mutableListOf<Long>()
        val eliminados = mutableListOf<Long>()

        val eventosPropios = eventosXUa
            .map { administrador.tipificar(it) }
            .filter { it.esPropio }
            .filter { !it.esCreador }

        eventosPropios.forEach {
            when (it.tipoCambio) {
                EventoRddXUaEntity.TipoCambio.ELIMINADO -> eliminados.addIfNotNull(it.id)
                EventoRddXUaEntity.TipoCambio.EDITADO -> editados.addIfNotNull(it.id)
                EventoRddXUaEntity.TipoCambio.NUEVO -> nuevos.addIfNotNull(it.id)
            }
        }

        return Triple(nuevos, editados, eliminados)
    }
}

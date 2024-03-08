package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.eventos.data

import biz.belcorp.salesforce.core.entities.sql.eventos.EventoRddEntity_Table
import biz.belcorp.salesforce.core.entities.sql.eventos.EventoRddXUaEntity
import biz.belcorp.salesforce.core.entities.sql.eventos.EventoRddXUaEntity_Table
import com.raizlabs.android.dbflow.kotlinextensions.eq
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.where

class AdministradorEventosXUaCambiados {

    fun tipificar(evento: EventoRddXUaEntity): EventoRddXUaEntity {
        val existencia = evento.existePorIdDeServer()
        if (existencia is Datos.Existe)
            evento.tipificarComoNoNuevo(existencia.asistira)
        else
            evento.insertar()

        return evento
    }

    private fun EventoRddXUaEntity.tipificarComoNoNuevo(asistira: Boolean) {
        id = actualizarPorIdServer()
        if (activo && this.asistira)
            tipificarComoEditado()
        else
            tipificarComoEliminado(asistira)
    }

    private fun EventoRddXUaEntity.insertar() {
        if (activo) {
            id = insert()
            tipificarComoNuevo()
        }
    }

    private fun EventoRddXUaEntity.existePorIdDeServer(): Datos {
        val evento = (com.raizlabs.android.dbflow.kotlinextensions.select
            from EventoRddXUaEntity::class
            where (EventoRddXUaEntity_Table.IdServidor eq idServidor)).querySingle()

        return if (evento == null)
            Datos.NoExiste()
        else
            Datos.Existe(evento.asistira)
    }

    private fun EventoRddXUaEntity.actualizarPorIdServer(): Long? {
        com.raizlabs.android.dbflow.sql.language.Update(EventoRddXUaEntity::class.java)
            .set(
                EventoRddXUaEntity_Table.IdEventoServer eq idEventoServer,
                EventoRddXUaEntity_Table.Region eq region,
                EventoRddXUaEntity_Table.Zona eq zona,
                EventoRddXUaEntity_Table.Seccion eq seccion,
                EventoRddXUaEntity_Table.Activo eq activo,
                EventoRddXUaEntity_Table.Asistira eq asistira,
                EventoRddXUaEntity_Table.FechaModificacion eq fechaModificacion,
                EventoRddXUaEntity_Table.EsCreador eq esCreador,
                EventoRddXUaEntity_Table.Enviado eq true,
                EventoRddXUaEntity_Table.IndicaCumplimiento eq indicaCumplimiento,
                EventoRddXUaEntity_Table.DesdeServidor eq true)
            .where(
                EventoRddEntity_Table.IdServidor eq idServidor).execute()

        val querySelect = (com.raizlabs.android.dbflow.kotlinextensions.select from EventoRddXUaEntity::class
            where (EventoRddEntity_Table.IdServidor eq idServidor))

        return querySelect.querySingle()?.id
    }

    sealed class Datos {

        class NoExiste : Datos()

        class Existe(val asistira: Boolean) : Datos()
    }
}

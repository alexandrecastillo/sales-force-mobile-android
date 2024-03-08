package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.eventos

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.eventos.EventoRddEntity
import biz.belcorp.salesforce.core.entities.sql.eventos.EventoRddXUaEntity
import biz.belcorp.salesforce.core.entities.sql.eventos.EventoTipoEventoJoined
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.toCalendar
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toDate
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toDateT
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toStringDate
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.CrearEventoUseCase

class EventoRddMapper {

    fun parse(modelo: EventoRddModeloCreacion): EventoRddEntity {

        val entidad = EventoRddEntity()
        entidad.fechaInicio = modelo.fechaInicio.toStringDate()
        entidad.fechaFin = modelo.fechaFin?.toStringDate()
        entidad.todoElDia = modelo.esTodoElDia
        entidad.idTipo = modelo.idTipoEvento
        entidad.descripcionPersonalizada = modelo.descripcionPersonalizada
        entidad.compartirObligatorioInicial = modelo.compartirObligatorio
        entidad.usuarioCreacion = modelo.usuarioCreacion
        entidad.alertar = modelo.alertar
        entidad.tiempoAlerta = modelo.alerta?.valor
        entidad.unidadTiempoAlerta = modelo.alerta?.unidad?.simbolo
        entidad.ubicacion = modelo.ubicacion
        entidad.region = modelo.llaveUa?.codigoRegion ?: Constant.HYPHEN
        entidad.zona = modelo.llaveUa?.codigoZona ?: Constant.HYPHEN
        entidad.seccion = modelo.llaveUa?.codigoSeccion ?: Constant.HYPHEN
        entidad.campania = modelo.campania

        if (modelo is EventoRddModeloEdicion) {
            entidad.id = modelo.id
        }

        return entidad
    }

    fun parse(eventoXUa: CrearEventoUseCase.GenerarEventoXUaParam): EventoRddXUaEntity {
        val entidad = EventoRddXUaEntity()
        entidad.region = eventoXUa.llaveUA.codigoRegion ?: Constant.HYPHEN
        entidad.zona = eventoXUa.llaveUA.codigoZona ?: Constant.HYPHEN
        entidad.seccion = eventoXUa.llaveUA.codigoSeccion ?: Constant.HYPHEN
        entidad.idEventoLocal = eventoXUa.idEvento

        return entidad
    }

    fun parse(modelo: EventoTipoEventoJoined): EventoRdd? {

        val tipoEvento = TipoEventoRdd(
            id = modelo.idTipo,
            descripcion = modelo.descripcion ?: Constant.EMPTY_STRING,
            compartirObligatorio = modelo.compartirObligatorio,
            rol = Rol.Builder.construir(modelo.rol),
            aceptaDescripcionPersonalizada = modelo.aceptaDescripcionPersonalizada
        )

        val alerta: Alerta? = crearAlerta(modelo)

        val eventoXUa = EventoRddXUa(
            id = modelo.id,
            uA = LlaveUA(
                codigoRegion = modelo.region,
                codigoZona = modelo.zona,
                codigoSeccion = modelo.seccion,
                consultoraId = Constant.MENOS_UNO.toLong()
            )
        )

        val evento = EventoRdd(
            id = modelo.id,
            fechaInicio = modelo.fechaInicio.toDateT()?.toCalendar() ?: return null,
            fechaFin = modelo.fechaFin.toDateT()?.toCalendar(),
            esTodoElDia = modelo.todoElDia,
            tipo = tipoEvento,
            descripcionPersonalizada = modelo.descripcionPersonalizada,
            compartirObligatorio = modelo.compartirObligatorioInicial,
            alertar = modelo.alertar,
            alerta = alerta,
            ubicacion = modelo.ubicacion ?: Constant.EMPTY_STRING,
            eventoRddXUa = eventoXUa,
            registrar = modelo.indicaCumplimiento,
            fechaRegistro = modelo.fechaCumplimiento.toDate(),
            activo = modelo.activo
        )

        eventoXUa.setEvento(evento)

        return evento
    }

    private fun crearAlerta(modelo: EventoTipoEventoJoined): Alerta? {
        return if (modelo.alertar) {
            Alerta(
                valor = modelo.tiempoAlerta ?: return null,
                unidad = construirUnidad(
                    modelo.tiempoAlerta!!,
                    modelo.unidadTiempoAlerta
                )
            )
        } else {
            null
        }
    }

    private fun construirUnidad(valor: Int, unidad: String?): Alerta.UnidadTiempoAlerta {
        return if (valor <= 0) Alerta.UnidadTiempoAlerta.NINGUNO
        else Alerta.UnidadTiempoAlerta.Builder.construir(unidad)
    }

    fun parseToCambios(triple: Triple<List<EventoRdd>, List<EventoRdd>, List<EventoRdd>>): EventosCambiadosRespuesta.Cambiados {
        return EventosCambiadosRespuesta.Cambiados(
            nuevos = triple.first,
            editados = triple.second,
            eliminados = triple.third
        )
    }
}

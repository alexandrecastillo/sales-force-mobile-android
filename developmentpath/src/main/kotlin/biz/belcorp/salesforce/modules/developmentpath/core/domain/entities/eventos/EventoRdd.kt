package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos

import java.util.*

class EventoRdd(
    val id: Long,
    val fechaInicio: Calendar,
    val fechaFin: Calendar?,
    esTodoElDia: Boolean,
    val tipo: TipoEventoRdd,
    val descripcionPersonalizada: String?,
    val compartirObligatorio: Boolean,
    val alertar: Boolean,
    val alerta: Alerta?,
    val ubicacion: String,
    val eventoRddXUa: EventoRddXUa,
    val registrar: Boolean,
    val fechaRegistro: Date?,
    val activo: Boolean
) : Evento(fechaInicio, esTodoElDia, registrar) {

    val descripcion: String?
        get() {
            return if (tipo.aceptaDescripcionPersonalizada) {
                descripcionPersonalizada
            } else {
                tipo.descripcion
            }
        }

    val intervalo: Intervalo?
        get() {
            return Intervalo(
                inicio = fechaInicio.clone() as Calendar,
                fin = (fechaFin?.clone() as? Calendar) ?: return null
            )
        }

    val fechaAlarma: Calendar? = calcularFechaAlarma()

    private fun calcularFechaAlarma(): Calendar? {
        val resultado = fechaInicio.clone() as Calendar
        if (!alertar || alerta == null) return null
        if (alerta.valor == 0) return resultado

        when (alerta.unidad) {
            Alerta.UnidadTiempoAlerta.MINUTOS ->
                resultado.add(Calendar.MINUTE, -alerta.valor)
            Alerta.UnidadTiempoAlerta.HORAS ->
                resultado.add(Calendar.HOUR, -alerta.valor)
            Alerta.UnidadTiempoAlerta.DIAS ->
                resultado.add(Calendar.DAY_OF_YEAR, -alerta.valor)
            Alerta.UnidadTiempoAlerta.SEMANAS ->
                resultado.add(Calendar.WEEK_OF_YEAR, -alerta.valor)
            else -> resultado.add(Calendar.SECOND, 0)
        }
        return resultado
    }
}


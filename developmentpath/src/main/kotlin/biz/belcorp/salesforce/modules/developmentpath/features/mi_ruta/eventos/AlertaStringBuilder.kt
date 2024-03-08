package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos

import android.content.Context
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.model.AgregarEventoViewModel

class AlertaStringBuilder(private val context: Context) {

    fun construir(cantidad: Int, unidad: AgregarEventoViewModel.Unidad): String {
        return when(unidad) {
            AgregarEventoViewModel.Unidad.SIN_ALERTA ->
                context.getString(R.string.rdd_agregareventos_tiempo_ninguno)
            AgregarEventoViewModel.Unidad.A_LA_HORA ->
                    context.getString(R.string.rdd_agregareventos_tiempo_a_la_hora)
            else -> "$cantidad ${obtenerUnidad(cantidad, unidad)} ${obtenerTextoAntes()}"
        }
    }

    private fun obtenerUnidad(cantidad: Int, unidad: AgregarEventoViewModel.Unidad): String {
        return when(unidad) {
            AgregarEventoViewModel.Unidad.MINUTOS -> obtenerMinutos(cantidad)
            AgregarEventoViewModel.Unidad.HORAS -> obtenerHoras(cantidad)
            AgregarEventoViewModel.Unidad.DIAS -> obtenerDias(cantidad)
            AgregarEventoViewModel.Unidad.SEMANAS -> obtenerSemanas(cantidad)
            else -> throw NotImplementedError()
        }
    }

    private fun obtenerMinutos(cantidad: Int): String {
        return context.resources.getQuantityString(
                R.plurals.rdd_agregareventos_tiempo_minutos,
                cantidad,
                cantidad)
    }

    private fun obtenerHoras(cantidad: Int): String {
        return context.resources.getQuantityString(
                R.plurals.rdd_agregareventos_tiempo_horas,
                cantidad,
                cantidad)
    }

    private fun obtenerDias(cantidad: Int): String {
        return context.resources.getQuantityString(
                R.plurals.rdd_agregareventos_tiempo_dias,
                cantidad,
                cantidad)
    }

    private fun obtenerSemanas(cantidad: Int): String {
        return context.resources.getQuantityString(
                R.plurals.rdd_agregareventos_tiempo_semanas,
                cantidad,
                cantidad)
    }

    private fun obtenerTextoAntes(): String {
        return context.getString(R.string.rdd_agregareventos_tiempo_antes)
    }
}

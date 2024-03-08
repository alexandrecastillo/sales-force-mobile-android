package biz.belcorp.salesforce.modules.developmentpath.features.habilidades.view

import android.graphics.Color
import biz.belcorp.salesforce.modules.developmentpath.R

class ProveedorEstiloHabilidad {

    private val verde = "#38be62"
    private val gris = "#252628"

    fun obtenerColorSeleccionado(seleccionado: Boolean): Int {
        return if (seleccionado) Color.parseColor(verde) else Color.parseColor(gris)
    }

    fun obtenerIcono(posicion: Int): Int {
        return when (posicion) {
            1 -> R.drawable.ic_orienta_resultados
            2 -> R.drawable.ic_maneja_tiempo
            3 -> R.drawable.ic_analiza_y_prioriza
            4 -> R.drawable.ic_hace_cosas
            5 -> R.drawable.ic_negociacion
            6 -> R.drawable.ic_toma_decisiones
            7 -> R.drawable.ic_desarrolla_otros
            8 -> R.drawable.ic_mantiene_foco
            9 -> R.drawable.ic_es_creativa
            10 -> R.drawable.ic_maneja_estres
            11 -> R.drawable.ic_busca_aprender
            12 -> R.drawable.ic_influencia
            13 -> R.drawable.ic_responde_al_cambio
            else -> R.drawable.ic_default
        }
    }

    fun obtenerFondo(seleccionado: Boolean): Int {
        return if (seleccionado) R.drawable.border_layout_green else R.drawable.border_layout_white
    }

    fun obtenerIconoDetalle(posicion: Int): Int {
        return when (posicion) {
            1 -> R.drawable.ic_orienta_resultados_detalle
            2 -> R.drawable.ic_maneja_tiempo_detalle
            3 -> R.drawable.ic_analiza_prioriza_detalle
            4 -> R.drawable.ic_hace_cosas_detalle
            5 -> R.drawable.ic_negociacion_detalle
            6 -> R.drawable.ic_toma_decisiones_detalle
            7 -> R.drawable.ic_desarrolla_otros_detalle
            8 -> R.drawable.ic_mantiene_foco_detalle
            9 -> R.drawable.ic_es_creativa_detalle
            10 -> R.drawable.ic_maneja_estres_detalle
            11 -> R.drawable.ic_busca_aprender_detalle
            12 -> R.drawable.ic_influencia_detalle
            13 -> R.drawable.ic_responde_al_cambio_detalle
            else -> R.drawable.ic_default_detalle
        }
    }
}

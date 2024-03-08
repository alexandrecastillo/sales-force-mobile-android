package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.mapper

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.Rdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.DiaDeMes
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.CalendarioViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.DiaCalendarioViewModel
import java.util.*

class CalendarioMapper {

    fun map(rdd: Rdd): CalendarioViewModel {
        val dias = rdd.mesSeleccionado.dias.map { map(it) }

        return CalendarioViewModel(dias)
    }

    private fun map(diaDeMes: DiaDeMes): DiaCalendarioViewModel {
        return DiaCalendarioViewModel(
                dia = diaDeMes.dia.fecha.get(Calendar.DAY_OF_MONTH).toString(),
                seleccionado = diaDeMes.dia.seleccionado,
                tipoTexto = obtenerTipoTexto(diaDeMes),
                tipoPunto = obtenerTipoPunto(diaDeMes),
                tipoFondo = obtenerTipoFondo(diaDeMes),
                tipoSeleccion = obtenerTipoCirculo(diaDeMes))
    }

    private fun obtenerTipoTexto(diaDeMes: DiaDeMes): DiaCalendarioViewModel.TipoTexto {
        return when {
            diaDeMes.dia.seleccionado -> DiaCalendarioViewModel.TipoTexto.SELECCIONADO
            diaDeMes.perteneceAMes -> DiaCalendarioViewModel.TipoTexto.HABILITADO
            else -> DiaCalendarioViewModel.TipoTexto.DESHABILITADO
        }
    }

    private fun obtenerTipoPunto(diaDeMes: DiaDeMes): DiaCalendarioViewModel.TipoPunto {
        return if (diaDeMes.dia.existenConsultorasNuevas && diaDeMes.dia.seleccionado) {
            DiaCalendarioViewModel.TipoPunto.BLANCO
        } else if (diaDeMes.dia.existenConsultorasNuevas && !diaDeMes.dia.seleccionado) {
            DiaCalendarioViewModel.TipoPunto.COLOR
        } else {
            DiaCalendarioViewModel.TipoPunto.NINGUNO
        }
    }

    private fun obtenerTipoFondo(diaDeMes: DiaDeMes): DiaCalendarioViewModel.TipoFondo {
        return when {
            !diaDeMes.dia.perteneceACampania -> DiaCalendarioViewModel.TipoFondo.NINGUNO
            diaDeMes.dia.esInicioCampania -> DiaCalendarioViewModel.TipoFondo.CIRCULO_RECTANGULO_DERECHA
            diaDeMes.dia.esFinCampania -> DiaCalendarioViewModel.TipoFondo.CIRCULO_RECTANGULO_IZQUIERDA
            diaDeMes.dia.inicioSemana -> DiaCalendarioViewModel.TipoFondo.RECTANGULO_REDONDEADO_IZQUIERDA
            diaDeMes.dia.finSemana -> DiaCalendarioViewModel.TipoFondo.RECTANGULO_REDONDEADO_DERECHA
            !diaDeMes.dia.estaEnCampania -> DiaCalendarioViewModel.TipoFondo.NINGUNO
            else -> DiaCalendarioViewModel.TipoFondo.RECTANGULO
        }
    }

    private fun obtenerTipoCirculo(diaDeMes: DiaDeMes): DiaCalendarioViewModel.TipoSeleccion {
        return when {
            diaDeMes.dia.seleccionado -> DiaCalendarioViewModel.TipoSeleccion.CIRCULO
            diaDeMes.dia.esHoy -> DiaCalendarioViewModel.TipoSeleccion.CIRCUNFERENCIA
            else -> DiaCalendarioViewModel.TipoSeleccion.NINGUNO
        }
    }
}

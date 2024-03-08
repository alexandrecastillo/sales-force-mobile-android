package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.presenter

import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.Alerta
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Seleccionador
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.model.AgregarEventoModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.model.AgregarEventoViewModel


class TiemposAlertaHandler(private val viewHandler: AgregarEventoViewHandler,
                           private val model: AgregarEventoViewModel,
                           private val modelMapper: AgregarEventoModelMapper) {

    private var seleccionadorAlertas = Seleccionador<Alerta?>()

    fun seleccionarTiempoAlerta(posicion: Int) {
        doAsync {
            seleccionadorAlertas.seleccionarSolo(posicion)

            val alertaSeleccionada = seleccionadorAlertas.primerSeleccionado ?: return@doAsync
            model.alertaSeleccionada = modelMapper.convertir(alertaSeleccionada)
            model.tiemposAlerta = seleccionadorAlertas
                    .seleccionables
                    .map { modelMapper.convertir(it) }

            uiThread {
                viewHandler.pintarTiemposAlerta()
                viewHandler.pintarTiempoAlertaSeleccionado()
            }
        }
    }

    fun manejarRespuesta(respuesta: Seleccionador<Alerta?>) {
        doAsync {
            seleccionadorAlertas = respuesta
            val alertaSeleccionada = seleccionadorAlertas.primerSeleccionado ?: return@doAsync
            model.alertaSeleccionada = modelMapper.convertir(alertaSeleccionada)
            model.tiemposAlerta = seleccionadorAlertas
                    .seleccionables
                    .map { modelMapper.convertir(it) }

            uiThread {
                viewHandler.pintarTiemposAlerta()
                viewHandler.pintarTiempoAlertaSeleccionado()
            }
        }
    }
}

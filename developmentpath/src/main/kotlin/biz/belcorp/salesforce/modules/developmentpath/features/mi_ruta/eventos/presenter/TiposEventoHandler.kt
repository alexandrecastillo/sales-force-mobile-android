package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.presenter

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.TipoEventoRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.Seleccionador
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.model.AgregarEventoModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.model.AgregarEventoViewModel
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class TiposEventoHandler(private val viewHandler: AgregarEventoViewHandler,
                         private val model: AgregarEventoViewModel,
                         private val modelMapper: AgregarEventoModelMapper) {

    private var seleccionadorTiposEvento = Seleccionador<TipoEventoRdd>()

    fun seleccionarTipoEvento(posicion: Int) {
        doAsync {
            seleccionadorTiposEvento.seleccionarSolo(posicion)

            val tipoSeleccionado = seleccionadorTiposEvento
                    .primerSeleccionado
                    ?.elemento
                    ?: return@doAsync

            actualizarModeloSegunSeleccion(tipoSeleccionado)

            uiThread { refrescarVistaSegunSeleccion() }
        }
    }

    fun manejarRespuesta(respuesta: Seleccionador<TipoEventoRdd>) {
        doAsync {
            seleccionadorTiposEvento = respuesta

            model.tiposEvento = seleccionadorTiposEvento.seleccionables
                    .map { modelMapper.convertir(it.elemento) }

            val tipoSeleccionado = seleccionadorTiposEvento
                    .primerSeleccionado
                    ?.elemento
                    ?: return@doAsync

            actualizarModeloSegunSeleccion(tipoSeleccionado)

            uiThread {
                viewHandler.pintarTiposEvento()
                refrescarVistaSegunSeleccion()
            }
        }
    }

    private fun actualizarModeloSegunSeleccion(tipoSeleccionado: TipoEventoRdd) {
        model.tipoEventoSeleccionado = modelMapper.convertir(tipoSeleccionado)
        model.compartir = calcularCompartir(tipoSeleccionado)
        model.mostrarDescripcionPersonalizada = tipoSeleccionado.aceptaDescripcionPersonalizada
        model.mostrarCompartir = tipoSeleccionado.permiteCompartir &&
                !tipoSeleccionado.compartirObligatorio
        model.mostrarAvisoCompartir = tipoSeleccionado.permiteCompartir &&
                tipoSeleccionado.compartirObligatorio
        model.botonGuardarHabilitado = !model.mostrarDescripcionPersonalizada ||
                model.descripcionPersonalizada.isNotBlank()
    }

    private fun calcularCompartir(tipoSeleccionado: TipoEventoRdd): Boolean {
        if (!tipoSeleccionado.permiteCompartir) return false
        if (tipoSeleccionado.compartirObligatorio) return true

        return model.compartirOriginal
    }

    private fun refrescarVistaSegunSeleccion() {
        viewHandler.pintarTipoEventoSeleccionado()
        viewHandler.mostrarUOcultarDescripcionPersonalizada()
        viewHandler.mostrarUOcultarAvisoCompartir()
        viewHandler.pintarLayoutCompartir()
        viewHandler.pintarCompartir()
        viewHandler.habilitarODeshabilitarBotonGuardar()
    }
}

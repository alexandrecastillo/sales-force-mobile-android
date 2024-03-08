package biz.belcorp.salesforce.modules.developmentpath.features.focos

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.FocoSeleccionado
import biz.belcorp.salesforce.modules.developmentpath.features.focos.model.FocoModel
import biz.belcorp.salesforce.modules.developmentpath.features.focos.model.PersonaModel

interface AsignarView {
    fun mostrarTituloFocosPropios()
    fun mostrarTituloFocosHijos()
    fun ocultarTituloFocos()
    fun mostrarTituloPersonas()
    fun ocultarTituloPersonas()
    fun cargarFocos(focos: List<FocoModel>)
    fun focoSeleccionado(foco: FocoSeleccionado)
    fun codigoSeleccionado(foco: FocoSeleccionado)
    fun cargarPersonas(personas: List<PersonaModel>)
    fun habilitarBotonGuardado()
    fun deshabilitarBotonGuardado()
    fun mostrarMensaje(mensaje: String)
    fun notificarActualizacionEnFocosDeHijas()
    fun notificarActualizacionEnMisFocos()
    fun cerrar()
}

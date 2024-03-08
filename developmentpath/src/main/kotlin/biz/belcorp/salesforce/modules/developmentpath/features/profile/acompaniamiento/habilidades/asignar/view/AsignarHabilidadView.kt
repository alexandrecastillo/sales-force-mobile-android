package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.asignar.view

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.FocoSeleccionado
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.asignar.model.HabilidadModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.model.GerenteZonaModel

interface AsignarHabilidadView {

    fun deshabilitarBotonGuardado()
    fun habilitarBotonGuardado()
    fun cargarHabilidades(listaHabilidades: List<HabilidadModel>)
    fun cargarFococSeleccionado(foco: FocoSeleccionado)
    fun cargarCantidadesDeHabilidades(titulo: Int, numeroSeleccionados: Int, maximoNumeroHabilidades: Int)
    fun cargarGerenteZona(gerenteZona: GerenteZonaModel)
    fun recargarTabFocos()
    fun cerrar()
}

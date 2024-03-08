package biz.belcorp.salesforce.modules.developmentpath.features.habilidades.reconocer.view

import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.reconocer.model.HabilidadModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.habilidades.model.GerenteZonaModel

interface ReconocerHabilidadesView {

    fun cargarHabilidades(listaHabilidades: List<HabilidadModel>)
    fun cargarCantidadesDeHabilidades(numeroSeleccionados: Int, maximoNumeroHabilidades: Int)
    fun cargarGerenteZona(gerenteZona: GerenteZonaModel)
    fun cerrar()
    fun recargar()
    fun mostrarBotonReconocer(mostrarBotonReconocer: Boolean)
    fun desactivarSeleccionar(desactivarSeleccionar: Boolean)
}

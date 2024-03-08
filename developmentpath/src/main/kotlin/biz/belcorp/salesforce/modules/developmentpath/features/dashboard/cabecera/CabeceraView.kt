package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.cabecera

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.features.flujo.Navigator

interface CabeceraView {
    var navigator: Navigator?
    fun cargarNombreCampania(periodoCampania: Campania.Periodo, nombre: String)
    fun cargarRolZonaSeccion(gzRolZona: String, rol: Rol?, sesion: Sesion)
    fun cargarIniciales(iniciales: String)
    fun configurarBotonVerMiRuta(planId: Long, rol: Rol)
    fun habilitarBotonVerMiRuta()
    fun deshabilitarBotonVerMiRuta()
    fun pintarMiRutaEnBoton()
    fun pintarVerRutaEnBoton()
    fun mostrarBotonRetroceder()
    fun ocultarBotonRetroceder()
}

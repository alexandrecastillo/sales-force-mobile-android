package biz.belcorp.salesforce.modules.developmentpath.features.habilidades.avance_u6c.presenter

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.AvanceHabilidad
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.habilidades.DetalleHabilidadesAcompaniamiento
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades.GetAvanceHabilidadesUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.habilidades.ObtenerHabilidadesDetalleUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile.RecuperarPersonaUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.avance_u6c.view.AvanceHabilidadMapper
import biz.belcorp.salesforce.modules.developmentpath.features.habilidades.avance_u6c.view.AvanceHabilidadView

class AvanceHabilidadPresenter(
    private val view: AvanceHabilidadView,
    private val getAvanceHabilidadesUseCase: GetAvanceHabilidadesUseCase,
    private val recuperarPersonaUseCase: RecuperarPersonaUseCase,
    private val obtenerHabilidadesDetalleUseCase: ObtenerHabilidadesDetalleUseCase,
    private val mapper: AvanceHabilidadMapper
) {


    private var personaId = -1L
    private var rol = Rol.NINGUNO

    private var campanias: List<String> = emptyList()

    private var habilidadesAvance: List<AvanceHabilidad> = emptyList()

    private var habilidadesAsignadas: List<DetalleHabilidadesAcompaniamiento> = emptyList()

    fun establecerPersonaIdYRol(personaId: Long, rol: Rol) {
        this.personaId = personaId
        this.rol = rol
        inicializar()
    }

    private fun inicializar() {
        recuperarPersona()
    }

    private fun recuperarPersona() {
        val request = RecuperarPersonaUseCase.Request(
            personaId = personaId,
            rol = rol,
            subscriber = RecuperarPersonaSubscriber()
        )
        recuperarPersonaUseCase.recuperar(request)
    }

    private fun recuperarAvanceHabilidades(persona: PersonaRdd) {
        val ua = persona.llaveUA
        getAvanceHabilidadesUseCase.getAvanceHabilidades(
            ua.codigoZona,
            ua.codigoRegion!!, rol, AvanceHabilidadesSubscriber()
        )
    }

    private fun recuperarHabilidadesAsignadas() {
        val request = ObtenerHabilidadesDetalleUseCase.Request(personaId, rol)
        obtenerHabilidadesDetalleUseCase.obtener(request, ObtenerHabilidadesAsignadasSubscriber())
    }

    private fun pintarResultado() {
        doAsync {
            val modelos = mapper.parse(habilidadesAvance, habilidadesAsignadas)
            uiThread {
                view.pintarCampanias(campanias)
                view.pintarHabilidades(modelos)
            }
        }
    }

    private inner class ObtenerHabilidadesAsignadasSubscriber :
        BaseSingleObserver<ObtenerHabilidadesDetalleUseCase.Response>() {

        override fun onSuccess(t: ObtenerHabilidadesDetalleUseCase.Response) {
            habilidadesAsignadas = t.habilidadesLiderazgo
            pintarResultado()
        }
    }

    private inner class RecuperarPersonaSubscriber : BaseSingleObserver<PersonaRdd>() {

        override fun onSuccess(t: PersonaRdd) {
            recuperarAvanceHabilidades(t)
        }
    }

    private inner class AvanceHabilidadesSubscriber :
        BaseSingleObserver<Pair<List<String>, List<AvanceHabilidad>>>() {

        override fun onSuccess(t: Pair<List<String>, List<AvanceHabilidad>>) {
            campanias = t.first
            habilidadesAvance = t.second
            recuperarHabilidadesAsignadas()
        }

    }
}

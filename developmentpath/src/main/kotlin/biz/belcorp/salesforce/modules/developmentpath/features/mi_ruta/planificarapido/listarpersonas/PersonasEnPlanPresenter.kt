package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificarapido.listarpersonas

import biz.belcorp.salesforce.core.domain.exceptions.UnsupportedRoleException
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.WordUtils
import biz.belcorp.salesforce.core.utils.parseToDateItem
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteRegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.plan.PersonasEnPlanUseCase
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread

class PersonasEnPlanPresenter(private val personasEnPlanUseCase: PersonasEnPlanUseCase) {

    var view: PersonasEnPlanView? = null

    fun recuperarPersonas(planId: Long) {
        val request = PersonasEnPlanUseCase.Request(planId, PersonasSubscriber())
        personasEnPlanUseCase.recuperar(request)
    }

    private fun PersonasEnPlanUseCase.PersonaResponse.transformarAModelo(): PersonaEnPlanModel {
        return PersonaEnPlanModel(personaId = persona.id,
                                  visitaId = visitaId,
                                  iniciales = persona.iniciales,
                                  nombres = WordUtils.capitalizeFully(persona.nombreApellido),
                                  descripcion = persona.obtenerDescripcion(),
                                  fechaPlanificacion = fechaPlanificacion?.parseToDateItem() ?: "",
                                  planificada = planificada)
    }

    private fun PersonaRdd.obtenerDescripcion(): String {
        return when (this) {
            is GerenteRegionRdd -> concatenarUaYProductividad(region.codigo, estadoProductividad)
            else -> throw UnsupportedRoleException(rol)
        }
    }

    private fun concatenarUaYProductividad(codigoUa: String, productividad: String?): String {
        return StringBuilder()
                .append("R")
                .append(codigoUa)
                .also { agregarProductividad(productividad, it) }
                .toString()
    }

    private fun agregarProductividad(productividad: String?, it: StringBuilder) {
        if (productividad != null) {
            it.append(" - ")
            it.append(productividad)
        }
    }

    inner class PersonasSubscriber : BaseSingleObserver<List<PersonasEnPlanUseCase.PersonaResponse>>() {
        override fun onSuccess(t: List<PersonasEnPlanUseCase.PersonaResponse>) {
            doAsync {
                val modelos = t.map { it.transformarAModelo() }
                uiThread { view?.pintarPersonas(modelos) }
            }
        }
    }
}

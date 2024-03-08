package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.planificacion

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import java.util.*

sealed class PlanificacionModel(
    val nombreCortoCampania: String,
    val visitas: List<VisitaModel>
) {

    class NoPlanificada(
        val visitaId: Long,
        nombreCortoCampania: String,
        visitas: List<VisitaModel>
    ) : PlanificacionModel(
        nombreCortoCampania,
        visitas
    )

    class Planificada(
        val visitaId: Long,
        val fecha: Date,
        val fechaString: String,
        val horaString: String,
        val registroValido: Boolean,
        nombreCortoCampania: String,
        visitas: List<VisitaModel>
    ) : PlanificacionModel(
        nombreCortoCampania,
        visitas
    )

    class Registrada(
        nombreCortoCampania: String,
        visitas: List<VisitaModel>
    ) : PlanificacionModel(
        nombreCortoCampania,
        visitas
    )

    class PuedeAdicionarVisita(
        val personaId: Long,
        val rol: Rol,
        nombreCortoCampania: String,
        visitas: List<VisitaModel>
    ) : PlanificacionModel(
        nombreCortoCampania,
        visitas
    )
}

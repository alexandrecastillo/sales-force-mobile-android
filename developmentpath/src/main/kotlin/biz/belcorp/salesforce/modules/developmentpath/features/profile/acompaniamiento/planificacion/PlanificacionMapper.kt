package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.planificacion

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.utils.WordUtils
import biz.belcorp.salesforce.core.utils.parseToDescription
import biz.belcorp.salesforce.core.utils.parseToDescriptionPlusHours
import biz.belcorp.salesforce.core.utils.parseToHoursDescription
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.Rdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.Visita
import biz.belcorp.salesforce.modules.developmentpath.utils.conPrimeraLetraMayus

class PlanificacionMapper {

    fun map(
        estadoPersona: Pair<PersonaRdd, Rdd.EstadoRdd>,
        campania: Campania
    ): PlanificacionModel {

        val persona = estadoPersona.first
        val estado = estadoPersona.second
        val nombreCortoCampania = (campania.nombreCorto).replace(Constant.HYPHEN, Constant.EMPTY_STRING)
        val modelosVisita = persona.agenda.visitasRegistradas.map { map(it) }

        return when (estado) {
            Rdd.EstadoRdd.NoPlanificada ->
                PlanificacionModel
                    .NoPlanificada(
                        visitaId = checkNotNull(persona.primeraVisitaNoRegistrada?.id),
                        nombreCortoCampania = nombreCortoCampania,
                        visitas = modelosVisita
                    )

            Rdd.EstadoRdd.Planificada ->
                PlanificacionModel
                    .Planificada(
                        visitaId = checkNotNull(persona.primeraVisitaNoRegistrada?.id),
                        fecha = checkNotNull(persona.primeraVisitaNoRegistrada?.horaAMostrar),
                        fechaString = checkNotNull(
                            persona
                                .primeraVisitaNoRegistrada
                                ?.horaAMostrar
                                ?.parseToDescription()
                                ?.conPrimeraLetraMayus()
                        ),
                        horaString = checkNotNull(
                            persona
                                .primeraVisitaNoRegistrada
                                ?.horaAMostrar
                                ?.parseToHoursDescription()
                        ),
                        nombreCortoCampania = nombreCortoCampania,
                        visitas = modelosVisita,
                        registroValido = persona.puedeRegistrarVisitaHoy
                    )

            Rdd.EstadoRdd.Registrada ->
                PlanificacionModel
                    .Registrada(
                        nombreCortoCampania = nombreCortoCampania,
                        visitas = modelosVisita
                    )

            Rdd.EstadoRdd.PuedeAdicionarVisita ->
                PlanificacionModel
                    .PuedeAdicionarVisita(
                        personaId = checkNotNull(persona.id),
                        rol = checkNotNull(persona.rol),
                        nombreCortoCampania = nombreCortoCampania,
                        visitas = modelosVisita
                    )
        }
    }

    private fun map(visita: Visita): VisitaModel {
        val fecha = visita.fechaRegistro?.time
        val fechaString = fecha?.parseToDescriptionPlusHours().orEmpty()
        val fechaStringMayuscula = WordUtils.capitalize(fechaString)
        return VisitaModel(visita.id, "- $fechaStringMayuscula")
    }
}

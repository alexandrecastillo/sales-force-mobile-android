package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.perfil

import biz.belcorp.salesforce.core.entities.sql.perfil.PostulanteDetallePlanJoinned
import biz.belcorp.salesforce.core.utils.toDescriptionDay
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.visitas.VisitaRddDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toDateT
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.DirectorioTelefonico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PosibleConsultoraRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Ubicacion

class PosibleConsultoraMapper(private val visitaRddDBDataStore: VisitaRddDBDataStore) {

    fun parse(model: PostulanteDetallePlanJoinned): PosibleConsultoraRdd {
        val ubicacion =
            Ubicacion(
                direccion = model.direccion,
                latitud = model.latitud,
                longitud = model.longitud
            )

        val directorio =
            DirectorioTelefonico(
                celular = DirectorioTelefonico.Telefono.construirFavorito(
                    model.celular
                ),
                casa = DirectorioTelefonico.Telefono.construir(
                    model.telefono
                ),
                otro = null
            )

        val agenda = visitaRddDBDataStore.obtenerVisitasDePosibleCandidata(model.numeroDocumento!!)

        val persona =
            PosibleConsultoraRdd(
                id = model.solicitudPostulanteId,
                solicitudId = -1,
                primerNombre = model.primerNombre,
                segundoNombre = model.segundoNombre,
                primerApellido = model.apellidoPaterno,
                segundoApellido = model.apellidoMaterno,
                email = model.correoElectronico,
                ubicacion = ubicacion,
                tipoDocumento = Persona.TipoDocumento.NINGUNO,
                documento = model.numeroDocumento
                    ?: error("Error al obtenerPorZonaId documento de posible consultora"),
                cumpleanios = model.fechaNacimiento.toDateT()?.toDescriptionDay(),
                estado = model.estadoPostulante,
                fuenteIngreso = obtenerFuenteDeIngreso(model),
                paso = model.paso,
                directorio = directorio,
                data = model.data
            )

        persona.establecerAgenda(agenda)
        agenda.establecerPersona(persona)

        return persona
    }

    private fun obtenerFuenteDeIngreso(model: PostulanteDetallePlanJoinned): PosibleConsultoraRdd.FuenteIngreso {
        return when {
            model.fuenteIngreso.equals("MovilSE", true) ->
                PosibleConsultoraRdd.FuenteIngreso.MOVIL_SE
            model.fuenteIngreso.equals("UB", true) ->
                PosibleConsultoraRdd.FuenteIngreso.UB
            else -> PosibleConsultoraRdd.FuenteIngreso.NINGUNO
        }
    }

    fun parse(models: List<PostulanteDetallePlanJoinned>): List<PosibleConsultoraRdd> {
        return models.map { parse(it) }
    }
}

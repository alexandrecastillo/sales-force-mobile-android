package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.personas

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.entities.sql.personas.CabeceraGZJoinned
import biz.belcorp.salesforce.core.entities.sql.visitas.GzDetalleRutaJoinned
import biz.belcorp.salesforce.core.utils.toDateWithT
import biz.belcorp.salesforce.core.utils.toFullDate
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.visitas.VisitaRddDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.visitas.VisitaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.DirectorioTelefonico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteZonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Ubicacion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.AgendaVisitas
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.RegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.ZonaRdd
import java.util.*

class GerenteZonaMapper(
    private val visitaMapper: VisitaMapper,
    private val visitaDataStore: VisitaRddDBDataStore
) {

    private fun parseAgrupadas(entidades: List<GzDetalleRutaJoinned>): GerenteZonaRdd {

        val entidad = entidades[0]

        val directorio =
            DirectorioTelefonico(
                celular = DirectorioTelefonico.Telefono.construirFavorito(
                    entidad.telefMovil
                ),
                casa = DirectorioTelefonico.Telefono.construir(
                    entidad.telefCasa
                ),
                otro = null
            )

        val gerente = GerenteZonaRdd(
            id = checkNotNull(entidad.consultoraId),
            estadoProductividad = entidad.estado,
            usuario = checkNotNull(entidad.usuario),
            primerNombre = entidad.primerNombre,
            segundoNombre = null,
            primerApellido = entidad.primerApellido,
            segundoApellido = entidad.segundoApellido,
            email = entidad.mailBelcorp,
            ubicacion = Ubicacion.construirDummy(),
            tipoDocumento = Persona.TipoDocumento.NINGUNO,
            documento = entidad.nroDoc ?: "",
            cumpleanios = null,
            fechaNacimiento = null,
            directorio = directorio,
            esNueva = entidad.esNueva,
            nroCampaniasComoNueva = entidad.nroCampaniasComoNueva
        )

        val agenda = AgendaVisitas(visitaMapper.parseFromGz(entidades))

        gerente.establecerAgenda(agenda)
        agenda.establecerPersona(gerente)

        return gerente
    }


    fun parse(entidades: List<GzDetalleRutaJoinned>): List<GerenteZonaRdd> {

        val entidadesAgrupadas = entidades.groupBy { it.usuario }

        return entidadesAgrupadas.map { (_, detalles) -> parseAgrupadas(detalles) }
    }

    fun parsear(entity: CabeceraGZJoinned): GerenteZonaRdd {
        val gerente = GerenteZonaRdd(
            id = entity.consultoraId ?: throw Exception("Id de consultora no válido"),
            usuario = checkNotNull(entity.usuario),
            primerApellido = entity.primerApellido,
            segundoApellido = entity.segundoApellido,
            primerNombre = entity.primerNombre,
            segundoNombre = null,
            email = entity.mailBelcorp,
            tipoDocumento = Persona.TipoDocumento.NINGUNO,
            estadoProductividad = entity.estado ?: "",
            documento = entity.nroDoc ?: "",
            fechaNacimiento = entity.fechaNacimiento?.toDateWithT(),
            directorio = DirectorioTelefonico(
                celular = DirectorioTelefonico.Telefono(
                    numero = entity.telefMovil ?: "", esFavorito = true
                ),
                casa = null,
                otro = null
            ),
            ubicacion = Ubicacion.construirDummy(),
            cumpleanios = null,
            esNueva = entity.esNueva,
            nroCampaniasComoNueva = entity.nroCampaniasComoNueva
        )

        val zona = ZonaRdd(
            codigo = entity.codigoZona ?: throw Exception("Codigo de zona no válido"),
            campania = entity.extraerCampania(),
            secciones = emptyList(),
            gerenteZona = null,
            focos = mutableListOf(),
            habilidades = mutableListOf(),
            planId = -1L
        )

        val agendaVisitas = visitaDataStore.obtenerVisitasDeGZ(gerente.usuario)

        zona.region = crearRegion(entity)

        gerente.zona = zona

        gerente.establecerAgenda(agendaVisitas)

        agendaVisitas.establecerPersona(gerente)

        return gerente
    }

    private fun crearRegion(entity: CabeceraGZJoinned): RegionRdd {
        return RegionRdd(
            codigo = entity.codigoRegion ?: "",
            campania = Campania.construirDummy(),
            gerenteRegion = null,
            zonas = emptyList(),
            focos = mutableListOf(),
            habilidades = mutableListOf(),
            planId = -1L,
            planValido = false
        )
    }

    private fun CabeceraGZJoinned.extraerCampania(): Campania {
        return Campania(
            codigo = campaniaCodigo,
            nombreCorto = campaniaNombreCorto,
            inicio = campaniaInicio.toFullDate() ?: Date(),
            fin = campaniaFin.toFullDate() ?: Date(),
            inicioFacturacion = campaniaInicioFacturacion.toFullDate() ?: Date(),
            orden = campaniaOrden,
            esPrimerDiaFacturacion = esPrimerDiaFacturacion,
            periodo = Campania.construirPeriodo(periodo)
        )
    }
}

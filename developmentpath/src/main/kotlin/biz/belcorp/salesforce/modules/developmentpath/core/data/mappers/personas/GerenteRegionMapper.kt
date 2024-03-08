package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.personas

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.DirectorioTelefonico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sesion.persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Ubicacion
import biz.belcorp.salesforce.core.entities.sql.directorio.GerenteRegionJoinned
import biz.belcorp.salesforce.core.entities.sql.personas.CabeceraGRJoinned
import biz.belcorp.salesforce.core.utils.formatShort
import biz.belcorp.salesforce.core.utils.toDate
import biz.belcorp.salesforce.core.utils.toFullDate
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toDate
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteRegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.RegionRdd
import java.util.*

class GerenteRegionMapper {

    fun parsear(entity: CabeceraGRJoinned): GerenteRegionRdd {
        entity.let {
            val gr = parseGrEntity(it)
            gr.region = crearRegion(entity)

            return gr
        }
    }

    fun parsear(entity: GerenteRegionJoinned): GerenteRegionRdd {
        entity.let {
            val gr = parseGrEntity(it)
            gr.region = crearRegion(entity)

            return gr
        }
    }

    private fun parseGrEntity(entity: GerenteRegionJoinned) =
        GerenteRegionRdd(
            id = entity.consultoraId ?: throw Exception("Id de consultora no válido"),
            primerApellido = entity.primerApellido,
            segundoApellido = entity.segundoApellido,
            primerNombre = entity.primerNombre,
            segundoNombre = null,
            email = entity.mailBelcorp,
            tipoDocumento = Persona.TipoDocumento.NINGUNO,
            estadoProductividad = entity.estado ?: "",
            usuario = requireNotNull(entity.usuario),
            documento = entity.nroDoc ?: "",
            fechaNacimiento = entity.fechaNacimiento?.toDate(formatShort),
            directorio = DirectorioTelefonico(
                celular = DirectorioTelefonico.Telefono(
                    numero = entity.telefMovil ?: "",
                    esFavorito = true
                ),
                casa = null,
                otro = null
            ),
            ubicacion = Ubicacion.construirDummy(),
            cumpleanios = null
        )


    private fun parseGrEntity(entity: CabeceraGRJoinned) =
        GerenteRegionRdd(
            id = entity.consultoraId ?: throw Exception("Id de consultora no válido"),
            primerApellido = entity.primerApellido,
            segundoApellido = entity.segundoApellido,
            primerNombre = entity.primerNombre,
            segundoNombre = null,
            email = entity.mailBelcorp,
            tipoDocumento = Persona.TipoDocumento.NINGUNO,
            estadoProductividad = entity.estado ?: "",
            usuario = requireNotNull(entity.usuario),
            documento = entity.nroDoc ?: "",
            fechaNacimiento = entity.fechaNacimiento.toDate(),
            directorio = DirectorioTelefonico(
                celular = DirectorioTelefonico.Telefono(
                    numero = entity.telefMovil ?: "",
                    esFavorito = true
                ),
                casa = null,
                otro = null
            ),
            ubicacion = Ubicacion.construirDummy(),
            cumpleanios = null
        )

    private fun crearRegion(entity: CabeceraGRJoinned): RegionRdd {
        return RegionRdd(
            codigo = entity.codigoRegion ?: "",
            campania = entity.extraerCampania(),
            gerenteRegion = null,
            zonas = emptyList(),
            focos = mutableListOf(),
            habilidades = mutableListOf(),
            planId = -1L,
            planValido = false
        )
    }

    private fun crearRegion(entity: GerenteRegionJoinned): RegionRdd {
        return RegionRdd(
            codigo = entity.codigoRegion ?: "",
            campania = entity.extraerCampania(),
            gerenteRegion = null,
            zonas = emptyList(),
            focos = mutableListOf(),
            planId = Constant.MENOS_UNO.toLong(),
            planValido = false,
            habilidades = mutableListOf()
        )
    }


    private fun CabeceraGRJoinned.extraerCampania(): Campania {
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

    private fun GerenteRegionJoinned.extraerCampania(): Campania {
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

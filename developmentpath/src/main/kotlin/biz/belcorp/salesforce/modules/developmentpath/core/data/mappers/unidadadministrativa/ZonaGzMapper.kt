package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.unidadadministrativa

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.entities.sql.ua.ZonaGzJoinned
import biz.belcorp.salesforce.core.utils.toFullDate
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.DirectorioTelefonico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteZonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Ubicacion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.RegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.ZonaRdd
import java.util.*

class ZonaGzMapper {

    fun parse(modelo: ZonaGzJoinned): ZonaRdd {

        val gerente = parsearGerente(modelo)

        val zona = ZonaRdd(
            codigo = modelo.codigoZona ?: Constant.EMPTY_STRING,
            campania = parsearCampania(modelo),
            secciones = emptyList(),
            gerenteZona = gerente,
            focos = mutableListOf(),
            planId = modelo.planId,
            habilidades = mutableListOf(),
            visitasPlanificadas = modelo.totalPlanificadas,
            visitasRegistradas = modelo.totalVisitadas
        )

        val region = RegionRdd(
            codigo = modelo.codigoRegion ?: Constant.EMPTY_STRING,
            campania = Campania.construirDummy(),
            zonas = emptyList(),
            gerenteRegion = null,
            focos = mutableListOf(),
            habilidades = mutableListOf(),
            planId = Constant.MENOS_UNO.toLong(),
            visitasPlanificadas = Constant.CERO,
            visitasRealizadas = Constant.CERO,
            planValido = false
        )

        zona.gerenteZona = gerente
        zona.region = region
        gerente?.zona = zona

        return zona
    }

    private fun parsearCampania(modelo: ZonaGzJoinned): Campania {
        return Campania(
            codigo = modelo.codigoCampania,
            nombreCorto = modelo.nombreCorto,
            inicio = modelo.fechaInicio.toFullDate() ?: Date(),
            inicioFacturacion = modelo.fechaFin.toFullDate() ?: Date(),
            fin = modelo.fechaInicio.toFullDate() ?: Date(),
            esPrimerDiaFacturacion = false,
            periodo = Campania.Periodo.VENTA
        )
    }

    fun parsearGerente(modelo: ZonaGzJoinned): GerenteZonaRdd? {

        if (modeloTieneCamposQueIndicanDescobertura(modelo)) return null // Zona descoberturada

        return GerenteZonaRdd(
            id = requireNotNull(modelo.consultoraId),
            usuario = modelo.usuario ?: Constant.EMPTY_STRING,
            primerNombre = modelo.nombreGerente,
            primerApellido = modelo.apellidoGerente,
            ubicacion = Ubicacion.construirDummy(),
            tipoDocumento = Persona.TipoDocumento.NINGUNO,
            documento = Constant.EMPTY_STRING,
            directorio = DirectorioTelefonico.construirDummy(),
            fechaNacimiento = null,
            esNueva = false,
            estadoProductividad = modelo.estado,
            segundoNombre = null,
            segundoApellido = null,
            email = null,
            cumpleanios = null,
            nroCampaniasComoNueva = Constant.CERO
        )
    }

    private fun modeloTieneCamposQueIndicanDescobertura(modelo: ZonaGzJoinned): Boolean {
        return modelo.consultoraId == null
    }

    fun parse(avances: List<ZonaGzJoinned>) = avances.map { parse(it) }
}

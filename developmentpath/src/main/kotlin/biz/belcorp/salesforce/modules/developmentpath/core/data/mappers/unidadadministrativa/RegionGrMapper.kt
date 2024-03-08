package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.unidadadministrativa

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.entities.sql.ua.RegionGrJoinned
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.DirectorioTelefonico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Ubicacion
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toDateT
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteRegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.RegionRdd
import java.util.*

class RegionGrMapper{

    fun parse(entities: List<RegionGrJoinned>): List<RegionRdd> {
        return entities.map { parse(it) }
    }

    private fun parse(modelo: RegionGrJoinned): RegionRdd {

        val gerente = parseGerente(modelo)

        val region = RegionRdd(
            codigo = requireNotNull(modelo.codigo),
            campania = parsearCampania(modelo),
            gerenteRegion = gerente,
            zonas = emptyList(),
            focos = mutableListOf(),
            habilidades = mutableListOf(),
            visitasRealizadas = modelo.totalVisitadas,
            visitasPlanificadas = modelo.totalPlanificadas,
            planId = modelo.planId ?: Constant.MENOS_UNO.toLong(),
            planValido = modelo.planId != null)

        gerente?.region = region

        return region
    }

    fun parseGerente(modelo: RegionGrJoinned): GerenteRegionRdd? {
        return if (poseeCamposNecesariosParaGerenteRegion(modelo)) {

            val directorioTelefonico =
                DirectorioTelefonico(
                    celular = DirectorioTelefonico.Telefono.construirFavorito(
                        modelo.celular
                    ),
                    casa = DirectorioTelefonico.Telefono.construir(
                        modelo.telefonoCasa
                    ),
                    otro = null
                )

            GerenteRegionRdd(
                id = modelo.consultoraId ?: Constant.MENOS_UNO.toLong(),
                usuario = modelo.usuario ?: Constant.EMPTY_STRING,
                primerNombre = modelo.nombreGerente,
                primerApellido = modelo.apellidoGerente,
                segundoNombre = modelo.apellidoGerente,
                segundoApellido = null,
                email = modelo.email,
                ubicacion = Ubicacion.construirDummy(),
                tipoDocumento = Persona.TipoDocumento.NINGUNO,
                documento = modelo.documento ?: Constant.EMPTY_STRING,
                cumpleanios = modelo.cumpleanios,
                fechaNacimiento = modelo.cumpleanios.toDateT(),
                directorio = directorioTelefonico,
                estadoProductividad = modelo.estado)
        } else {
            null
        }
    }

    private fun parsearCampania(modelo: RegionGrJoinned): Campania {
        return Campania(
            codigo = modelo.codigoCampania,
            nombreCorto = modelo.nombreCorto,
            inicio = Date(),
            fin = Date(),
            inicioFacturacion = Date(),
            orden = Constant.UNO,
            esPrimerDiaFacturacion = modelo.esPrimerDiaFacturacion,
            periodo = Campania.construirPeriodo(modelo.periodo)
        )
    }

    private fun poseeCamposNecesariosParaGerenteRegion(modelo: RegionGrJoinned): Boolean {
        return modelo.consultoraId != null && modelo.usuario != null
    }
}

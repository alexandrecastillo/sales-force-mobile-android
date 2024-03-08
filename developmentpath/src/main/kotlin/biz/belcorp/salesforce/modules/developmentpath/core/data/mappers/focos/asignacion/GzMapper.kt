package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.asignacion

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.entities.sql.focos.asignacion.GzCampaniaJoined
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.DirectorioTelefonico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Ubicacion
import biz.belcorp.salesforce.core.utils.toFullDate
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteZonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.RegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.ZonaRdd

class GzMapper {

    fun parsearGZs(modelos: List<GzCampaniaJoined>) = modelos.map { parsearGZ(it) }

    private fun parsearGZ(modelo: GzCampaniaJoined): GerenteZonaRdd {

        val gz =
            GerenteZonaRdd(
                id = checkNotNull(modelo.consultoraId),
                usuario = checkNotNull(modelo.usuario),
                primerNombre = modelo.primerNombre,
                segundoNombre = null,
                primerApellido = modelo.primerApellido,
                segundoApellido = null,
                directorio = DirectorioTelefonico.construirDummy(),
                ubicacion = Ubicacion.construirDummy(),
                email = null,
                documento = Constant.EMPTY_STRING,
                tipoDocumento = Persona.TipoDocumento.NINGUNO,
                cumpleanios = null,
                esNueva = modelo.esNueva,
                fechaNacimiento = null,
                estadoProductividad = null,
                nroCampaniasComoNueva = modelo.nroCampaniasComoNueva
            )

        val campania = Campania(
            codigo = modelo.codigoCampania,
            nombreCorto = modelo.nombreCorto,
            inicio = requireNotNull(modelo.fechaInicio.toFullDate()),
            fin = requireNotNull(modelo.fechaFin.toFullDate()),
            inicioFacturacion = requireNotNull(modelo.fechaInicioFacturacion.toFullDate()),
            periodo = Campania.construirPeriodo(modelo.periodo),
            esPrimerDiaFacturacion = modelo.esPrimerDiaFacturacion
        )

        val zona = ZonaRdd(
            codigo = checkNotNull(modelo.codZona),
            campania = campania,
            secciones = emptyList(),
            gerenteZona = gz,
            focos = mutableListOf(),
            habilidades = mutableListOf(),
            planId = Constant.MENOS_UNO.toLong()
        )

        val region = RegionRdd(
            codigo = checkNotNull(modelo.codRegion),
            campania = Campania.construirDummy(),
            gerenteRegion = null,
            habilidades = mutableListOf(),
            focos = mutableListOf(),
            zonas = listOf(zona),
            planId = Constant.MENOS_UNO.toLong(),
            planValido = false
        )

        zona.region = region
        gz.zona = zona

        return gz
    }
}

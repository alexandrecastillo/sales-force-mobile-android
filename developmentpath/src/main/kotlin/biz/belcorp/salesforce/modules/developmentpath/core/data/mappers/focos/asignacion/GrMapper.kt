package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.asignacion

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.entities.sql.focos.asignacion.GrCampaniaJoined
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.DirectorioTelefonico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Ubicacion
import biz.belcorp.salesforce.core.utils.toFullDate
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteRegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.RegionRdd

class GrMapper {

    fun parsearGRs(modelos: List<GrCampaniaJoined>) = modelos.map { parsearGR(it) }

    private fun parsearGR(modelo: GrCampaniaJoined): GerenteRegionRdd {

        val gr =
            GerenteRegionRdd(
                id = checkNotNull(modelo.consultoraId),
                usuario = checkNotNull(modelo.usuario),
                primerNombre = modelo.primerNombre,
                segundoNombre = null,
                primerApellido = modelo.primerApellido,
                segundoApellido = null,
                directorio = DirectorioTelefonico.construirDummy(),
                ubicacion = Ubicacion.construirDummy(),
                email = null,
                documento = "",
                tipoDocumento = Persona.TipoDocumento.NINGUNO,
                cumpleanios = null,
                fechaNacimiento = null,
                estadoProductividad = null
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

        val region = RegionRdd(
            codigo = checkNotNull(modelo.codRegion),
            campania = campania,
            zonas = emptyList(),
            gerenteRegion = gr,
            focos = mutableListOf(),
            habilidades = mutableListOf(),
            planId = -1L,
            planValido = false
        )

        gr.region = region

        return gr
    }
}

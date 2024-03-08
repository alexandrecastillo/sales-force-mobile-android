package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.dashboard

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.DirectorioTelefonico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sesion.persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Ubicacion
import biz.belcorp.salesforce.core.entities.sql.focos.FocoRddEntity
import biz.belcorp.salesforce.core.entities.sql.focos.dashboard.RegionFocosJoinned
import biz.belcorp.salesforce.core.entities.sql.habilidades.HabilidadEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.FocosMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.habilidades.HabilidadesMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteRegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.RegionRdd
import java.util.*

class RegionMapper(
    private val focosMapper: FocosMapper,
    private val habilidadesMapper: HabilidadesMapper
) {

    fun map(
        modelosRegion: RegionFocosJoinned,
        modelosFoco: List<FocoRddEntity>,
        modelosHabilidad: List<HabilidadEntity>
    ): RegionRdd {

        return RegionRdd(
            codigo = requireNotNull(modelosRegion.codigo),
            campania = parsearCampania(modelosRegion),
            gerenteRegion = obtenerGr(modelosRegion),
            zonas = emptyList(),
            focos = focosMapper.map(modelosRegion.focos, modelosFoco),
            habilidades = habilidadesMapper.map(modelosRegion.habilidades, modelosHabilidad),
            planId = Constant.MENOS_UNO.toLong(),
            planValido = false
        )
    }

    private fun obtenerGr(modelo: RegionFocosJoinned): GerenteRegionRdd? {
        if (modelo.consultoraId == null || modelo.usuario == null) return null // Descoberturada

        return GerenteRegionRdd(
            id = requireNotNull(modelo.consultoraId),
            usuario = requireNotNull(modelo.usuario),
            primerNombre = modelo.primerNombre,
            segundoNombre = null,
            primerApellido = modelo.primerApellido,
            segundoApellido = null,
            cumpleanios = null,
            estadoProductividad = null,
            documento = Constant.EMPTY_STRING,
            tipoDocumento = Persona.TipoDocumento.NINGUNO,
            email = null,
            ubicacion = Ubicacion.construirDummy(),
            fechaNacimiento = null,
            directorio = DirectorioTelefonico.construirDummy()
        )
    }

    private fun parsearCampania(modelo: RegionFocosJoinned): Campania {
        return Campania(
            codigo = modelo.codigoCampania,
            nombreCorto = modelo.nombreCorto,
            inicio = Date(),
            fin = Date(),
            inicioFacturacion = Date(),
            orden = Constant.CERO,
            esPrimerDiaFacturacion = modelo.esPrimerDiaFacturacion,
            periodo = Campania.construirPeriodo(modelo.periodo)
        )
    }
}

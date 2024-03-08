package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.dashboard

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.DirectorioTelefonico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sesion.persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Ubicacion
import biz.belcorp.salesforce.core.entities.sql.focos.FocoRddEntity
import biz.belcorp.salesforce.core.entities.sql.focos.dashboard.ZonaFocosJoined
import biz.belcorp.salesforce.core.entities.sql.habilidades.HabilidadEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.FocosMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.habilidades.HabilidadesMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.GerenteZonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.ZonaRdd
import java.util.*

class ZonaMapper(
    private val focosMapper: FocosMapper,
    private val habilidadesMapper: HabilidadesMapper
) {

    fun map(
        modelosZona: ZonaFocosJoined,
        modelosFoco: List<FocoRddEntity>,
        modelosHabilidad: List<HabilidadEntity>
    ): ZonaRdd {

        return ZonaRdd(
            codigo = requireNotNull(modelosZona.codigo),
            campania = parsearCampania(modelosZona),
            gerenteZona = obtenerGz(modelosZona),
            secciones = emptyList(),
            focos = focosMapper.map(modelosZona.focos, modelosFoco),
            habilidades = habilidadesMapper.map(modelosZona.habilidades, modelosHabilidad),
            planId = Constant.MENOS_UNO.toLong()
        )
    }

    private fun obtenerGz(modelo: ZonaFocosJoined): GerenteZonaRdd? {
        if (modelo.consultoraId == null) return null // Descoberturada

        return GerenteZonaRdd(
            id = requireNotNull(modelo.consultoraId),
            usuario = modelo.usuario ?: Constant.EMPTY_STRING,
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
            directorio = DirectorioTelefonico.construirDummy(),
            esNueva = false,
            nroCampaniasComoNueva = Constant.CERO
        )
    }

    private fun parsearCampania(modelo: ZonaFocosJoined): Campania {
        return Campania(
            codigo = modelo.codigoCampania,
            nombreCorto = modelo.nombreCorto,
            inicio = Date(),
            fin = Date(),
            inicioFacturacion = Date(),
            orden = Constant.CERO,
            periodo = Campania.construirPeriodo(modelo.periodo),
            esPrimerDiaFacturacion = modelo.esPrimerDiaFacturacion
        )
    }
}

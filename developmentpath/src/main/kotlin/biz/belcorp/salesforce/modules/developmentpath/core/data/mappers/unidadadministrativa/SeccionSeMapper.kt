package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.unidadadministrativa

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.entities.sql.ua.SeccionSociaJoinned
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.DirectorioTelefonico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Ubicacion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.SociaEmpresariaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.RegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.SeccionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.ZonaRdd

class SeccionSeMapper {

    fun parse(modelos: List<SeccionSociaJoinned>): List<SeccionRdd> {
        return modelos.map { parse(it) }
    }

    fun parse(modelo: SeccionSociaJoinned): SeccionRdd {

        val socia = parsearSocia(modelo)

        val seccion = SeccionRdd(
            codigo = modelo.codigoSeccion,
            campania = Campania.construirDummy(),
            sociaEmpresaria = socia,
            consultoras = emptyList(),
            nivel = modelo.nivel,
            planId = modelo.planId,
            visitasProgramadasInicialmente = modelo.planificadas,
            visitasRegistradas = modelo.visitadas,
            visitedDays = modelo.visitedDays)



        val zona = ZonaRdd(
            codigo = modelo.codigoZona,
            campania = Campania.construirDummy(),
            gerenteZona = null,
            secciones = emptyList(),
            focos = mutableListOf(),
            habilidades = mutableListOf(),
            planId = -1L,
            visitasPlanificadas = 0,
            visitasRegistradas = 0)

        val region = RegionRdd(
            codigo = modelo.codigoRegion,
            campania = Campania.construirDummy(),
            zonas = emptyList(),
            gerenteRegion = null,
            focos = mutableListOf(),
            habilidades = mutableListOf(),
            planId = -1L,
            visitasPlanificadas = 0,
            visitasRealizadas = 0,
            planValido = false)

        seccion.zona = zona
        zona.region = region
        socia?.seccion = seccion

        return seccion
    }

    fun parsearSocia(modelo: SeccionSociaJoinned): SociaEmpresariaRdd? {
        if (consultoraIdInvalido(modelo.consultoraId)) return null // descoberturada

        return SociaEmpresariaRdd(
            id = modelo.consultoraId!!,
            primerNombre = modelo.primerNombre,
            segundoNombre = null,
            primerApellido = modelo.primerApellido,
            segundoApellido = null,
            campaniaIngreso = null,
            origenPedido = null,
            ultimaFacturacion = null,
            saldoPendiente = null,
            montoPedido = null,
            ventaRetail = null,
            recaudoNoComisionable = null,
            recaudoTotal = null,
            ganancia = null,
            recaudoComisionable = null,
            ventaFacturada = null,
            ventaGanancia = null,
            consultoraConsecutiva = null,
            gananciaVentaRetail = null,
            email = null,
            ubicacion = Ubicacion(
                null,
                null,
                null
            ),
            tipoDocumento = Persona.TipoDocumento.NINGUNO,
            documento = "",
            cumpleanios = null,
            directorio = DirectorioTelefonico.construirDummy(),
            codigo = "",
            clasificacionLider = null,
            subClasificacionLider = null,
            exitosa = modelo.exitosa > 0,
            productividad = modelo.estado,
            focos = mutableListOf())
    }

    private fun consultoraIdInvalido(consultoraId: Long?) =
        consultoraId == null || consultoraId < 1
}

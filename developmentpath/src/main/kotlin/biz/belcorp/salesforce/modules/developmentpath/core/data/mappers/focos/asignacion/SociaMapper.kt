package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.asignacion

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.entities.sql.focos.asignacion.SeCampaniaJoined
import biz.belcorp.salesforce.core.utils.toFullDate
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.DirectorioTelefonico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.SociaEmpresariaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Ubicacion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.RegionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.SeccionRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.ZonaRdd

class SociaMapper {

    fun parsearSocias(modelos: List<SeCampaniaJoined>): List<SociaEmpresariaRdd> =
        modelos.map { parsearSocia(it) }

    private fun parsearSocia(modelo: SeCampaniaJoined): SociaEmpresariaRdd {

        val socia =
            SociaEmpresariaRdd(
                codigo = Constant.EMPTY_STRING,
                clasificacionLider = Constant.EMPTY_STRING,
                subClasificacionLider = Constant.EMPTY_STRING,
                exitosa = false,
                productividad = Constant.EMPTY_STRING,
                focos = mutableListOf(),
                campaniaIngreso = Constant.EMPTY_STRING,
                origenPedido = Constant.EMPTY_STRING,
                ultimaFacturacion = Constant.EMPTY_STRING,
                saldoPendiente = Constant.EMPTY_STRING,
                ventaRetail = Constant.EMPTY_STRING,
                recaudoNoComisionable = Constant.EMPTY_STRING,
                recaudoTotal = Constant.EMPTY_STRING,
                ganancia = Constant.EMPTY_STRING,
                recaudoComisionable = Constant.EMPTY_STRING,
                ventaFacturada = Constant.EMPTY_STRING,
                ventaGanancia = Constant.EMPTY_STRING,
                consultoraConsecutiva = Constant.EMPTY_STRING,
                gananciaVentaRetail = Constant.EMPTY_STRING,
                montoPedido = Constant.EMPTY_STRING,
                id = modelo.consultoraId,
                primerNombre = modelo.primerNombre,
                segundoNombre = null,
                primerApellido = modelo.primerApellido,
                segundoApellido = null,
                email = Constant.EMPTY_STRING,
                ubicacion = Ubicacion.construirDummy(),
                tipoDocumento = Persona.TipoDocumento.NINGUNO,
                documento = Constant.EMPTY_STRING,
                cumpleanios = Constant.EMPTY_STRING,
                directorio = DirectorioTelefonico.construirDummy()
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

        val seccion = SeccionRdd(
            codigo = requireNotNull(modelo.seccion) {
                throw Exception("Error al obtener código de sección")
            },
            campania = campania,
            sociaEmpresaria = socia,
            consultoras = emptyList(),
            nivel = null,
            planId = Constant.MENOS_UNO.toLong(),
            visitasProgramadasInicialmente = Constant.MENOS_UNO,
            visitasRegistradas = Constant.MENOS_UNO
        )

        val zona = ZonaRdd(
            codigo = checkNotNull(modelo.zona),
            campania = Campania.construirDummy(),
            secciones = emptyList(),
            gerenteZona = null,
            focos = mutableListOf(),
            habilidades = mutableListOf(),
            planId = Constant.MENOS_UNO.toLong()
        )

        val region = RegionRdd(
            codigo = checkNotNull(modelo.region),
            campania = Campania.construirDummy(),
            gerenteRegion = null,
            habilidades = mutableListOf(),
            focos = mutableListOf(),
            zonas = listOf(zona),
            planId = Constant.MENOS_UNO.toLong(),
            planValido = false
        )

        seccion.zona = zona
        zona.region = region
        socia.seccion = seccion

        return socia
    }
}

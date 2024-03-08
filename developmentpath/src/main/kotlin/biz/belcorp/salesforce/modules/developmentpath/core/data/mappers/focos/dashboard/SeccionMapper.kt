package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.dashboard

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.entities.sql.focos.FocoRddEntity
import biz.belcorp.salesforce.core.entities.sql.focos.dashboard.SeccionFocosJoined
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.focos.FocosMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.DirectorioTelefonico
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.SociaEmpresariaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Ubicacion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.zonificacion.SeccionRdd
import java.util.*

class SeccionMapper(private val focosMapper: FocosMapper) {

    fun map(it: SeccionFocosJoined, modelosFoco: List<FocoRddEntity>): SeccionRdd {
        return SeccionRdd(
            codigo = checkNotNull(it.codigoSeccion) { "Código de sección inválido" },
            campania = parsearCampania(it),
            sociaEmpresaria = crearSociaEmpresaria(it, modelosFoco),
            consultoras = emptyList(),
            nivel = Constant.EMPTY_STRING,
            planId = -1,
            visitasProgramadasInicialmente = Constant.MENOS_UNO,
            visitasRegistradas = Constant.MENOS_UNO
        )
    }

    private fun crearSociaEmpresaria(
        modeloSociaFocos: SeccionFocosJoined,
        modelosFoco: List<FocoRddEntity>
    ): SociaEmpresariaRdd? {

        if (modeloSociaFocos.consultoraId == null || modeloSociaFocos.consultoraId == 0L) return null

        return SociaEmpresariaRdd(
            codigo = "",
            clasificacionLider = null,
            subClasificacionLider = null,
            exitosa = false,
            productividad = null,
            campaniaIngreso = null,
            origenPedido = null,
            ultimaFacturacion = null,
            saldoPendiente = null,
            ventaRetail = null,
            recaudoNoComisionable = null,
            recaudoTotal = null,
            ganancia = null,
            recaudoComisionable = null,
            ventaFacturada = null,
            ventaGanancia = null,
            consultoraConsecutiva = null,
            gananciaVentaRetail = null,
            montoPedido = null,
            focos = focosMapper.map(modeloSociaFocos.focos, modelosFoco),
            id = modeloSociaFocos.consultoraId!!,
            primerNombre = modeloSociaFocos.primerNombre,
            segundoNombre = null,
            primerApellido = modeloSociaFocos.primerApellido,
            segundoApellido = null,
            email = null,
            ubicacion = Ubicacion.construirDummy(),
            tipoDocumento = Persona.TipoDocumento.NINGUNO,
            documento = Constant.EMPTY_STRING,
            cumpleanios = null,
            directorio = DirectorioTelefonico.construirDummy()
        )
    }

    private fun parsearCampania(modelo: SeccionFocosJoined): Campania {
        return Campania(
            codigo = modelo.codigoCampania,
            nombreCorto = modelo.nombreCorto,
            inicio = Date(),
            fin = Date(),
            inicioFacturacion = Date(),
            orden = 0,
            periodo = Campania.construirPeriodo(modelo.periodo),
            esPrimerDiaFacturacion = modelo.esPrimerDiaFacturacion
        )
    }
}

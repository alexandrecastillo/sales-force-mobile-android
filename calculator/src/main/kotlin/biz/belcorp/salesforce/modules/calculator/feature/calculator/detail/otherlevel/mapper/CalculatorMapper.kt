package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.otherlevel.mapper

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.socialbonus.SocialBonus
import biz.belcorp.salesforce.core.utils.formatWithNoDecimalThousands
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.*
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.CalculadoraMontoFijo
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.CalculadoraMontoFijoModel
import biz.belcorp.salesforce.modules.calculator.model.*

class CalculatorMapper {

    fun transformCalculadoraNivelSuperior(list: List<UpperLevel>?): List<UpperLevelModel> {
        val result = arrayListOf<UpperLevelModel>()
        list?.forEach { domain ->
            result.add(
                UpperLevelModel(
                    nivelID = domain.nivelID,
                    descripcion = domain.descripcion
                )
            )
        }

        return result
    }

    fun map(entity: LevelParameter?, currency: String): LevelParameterModel? {

        return entity?.let {
            val bonusChangeLevel = if (it.bonoCambioNivel != null && it.bonoCambioNivel > 0) {
                "$currency ${it.bonoCambioNivel.toDouble().formatWithNoDecimalThousands()}"
            } else {
                Constant.EMPTY_STRING
            }
            val minimunSale = if (it.ventaMinimaProximoNivel != null) {
                "$currency ${it.ventaMinimaProximoNivel.toDouble().formatWithNoDecimalThousands()}"
            } else {
                Constant.EMPTY_STRING
            }
            val averageOrderProfit = if (it.gananciaPromedioPedido != null) {
                "$currency ${it.gananciaPromedioPedido.toDouble().formatWithNoDecimalThousands()}"
            } else {
                Constant.EMPTY_STRING
            }
            LevelParameterModel(
                it.nivelID,
                averageOrderProfit,
                it.pedidoMinimoNivel,
                minimunSale,
                it.metaIngresos,
                it.metaCapitalizacion,
                it.porcentajeRetencionActivas,
                it.metaPedidos,
                it.metaVenta,
                it.indicadorMedicionMeta,
                it.indicadorMedicionVariable,
                it.indicadorMedicionRetencion,
                bonusChangeLevel,
                it.porcentajeComisionEXAV,
                it.porcentajeComisionEXBV,
                it.porcentajeComisionNEAV,
                it.porcentajeComisionNEBV
            )
        }
    }

    fun transformVariableSocia(entity: PartnerVariable): PartnerVariableModel {
        return entity.let {
            PartnerVariableModel(
                it.nivelID,
                it.indicadorNuevaLider,
                it.metaPedido,
                it.metaVenta,
                it.numeroActivasIniciales,
                it.porcentajeMetaRecuperacion ?: Constant.EMPTY_DOUBLE,
                it.promedioVentaPedidosAV,
                it.promedioVentaPedidosBV,
                it.nivelCambioCampania,
                it.metaIngresos,
                it.metaCapitalizacion,
                it.indicadorMedicionVariable,
                it.indicadorMedicionMeta,
                it.porcentajeComisionEXAV,
                it.porcentajeComisionEXBV,
                it.porcentajeComisionNEAV,
                it.porcentajeComisionNEBV,
                it.bonoCambioNivel
            )
        }
    }

    fun transformVariableSocia(params: CalculatorMapperParams): PartnerVariableModel {
        return params.model.let {
            PartnerVariableModel(
                nivelID = it.nivelID,
                indicadorNuevaLider = it.indicadorNuevaLider,
                metaPedido = it.metaPedido,
                metaVenta = it.metaVenta,
                numeroActivasIniciales = it.numeroActivasIniciales,
                porcentajeMetaRecuperacion = it.porcentajeMetaRecuperacion ?: Constant.EMPTY_DOUBLE,
                promedioVentaPedidosAV = it.promedioVentaPedidosAV,
                promedioVentaPedidosBV = it.promedioVentaPedidosBV,
                nivelCambioCampania = it.nivelCambioCampania,
                metaIngresos = it.metaIngresos,
                metaCapitalizacion = it.metaCapitalizacion,
                indicadorMedicionVariable = it.indicadorMedicionVariable,
                indicadorMedicionMeta = it.indicadorMedicionMeta,
                porcentajeComisionEXAV = it.porcentajeComisionEXAV,
                porcentajeComisionEXBV = it.porcentajeComisionEXBV,
                porcentajeComisionNEAV = it.porcentajeComisionNEAV,
                porcentajeComisionNEBV = it.porcentajeComisionNEBV,
                bonoCambioNivel = it.bonoCambioNivel,
                campaign = params.campaign,
                currencySymbol = params.currencySymbol
            )
        }
    }


    fun transformResultado(model: CalculatingResultModel?): CalculatorResult? {
        if (model != null) {
            return CalculatorResult(
                valorResultado = model.valorResultado,
                bono = model.bono,
                codRegion = model.codRegion,
                codResultado = model.codResultado,
                codSeccion = model.codSeccion,
                codZona = model.codZona,
                exitoso = model.exitoso,
                campania = model.campania,
                detalleResultadoCalculadora = transformDetalleResultadoReverse(model.detalleResultadoCalculadora)
            )
        }
        return null
    }

    fun transformDetalleResultadoReverse(list: List<CalculatingResultDetailModel>): List<CalculatingResultDetail> {
        val result = arrayListOf<CalculatingResultDetail>()
        list.forEach { model ->
            result.add(
                CalculatingResultDetail(
                    cantidad = model.cantidad,
                    etiqueta = model.etiqueta,
                    codDetalle = model.codDetalle,
                    descripcion = model.descripcion,
                    valor = model.valor,
                    orden = model.orden
                )
            )
        }
        return result
    }

    fun transformBonoSocia(model: SocialBonus?): SocialBonusModel? {
        return model?.let {
            SocialBonusModel(
                codigoTipoBono = it.codigoTipoBono,
                descripcionTipoBono = it.descripcionTipoBono,
                codigoTipoMedicion = it.codigoTipoMedicion,
                indicadorActivo = it.indicadorActivo,
                indicadorTipoBono = it.indicadorTipoBono,
                montoUnitarioBono = it.montoUnitarioBono,
                ingresaCantidad = it.ingresaCantidad,
                codConsulta = it.codConsulta
            )
        }
    }

    fun transformBonoSocia(list: List<SocialBonus>): List<SocialBonusModel> {
        val result = arrayListOf<SocialBonusModel>()
        list.forEach { model ->
            result.add(
                SocialBonusModel(
                    codigoTipoBono = model.codigoTipoBono,
                    descripcionTipoBono = model.descripcionTipoBono,
                    codigoTipoMedicion = model.codigoTipoMedicion,
                    indicadorActivo = model.indicadorActivo,
                    indicadorTipoBono = model.indicadorTipoBono,
                    montoUnitarioBono = model.montoUnitarioBono,
                    ingresaCantidad = model.ingresaCantidad,
                    codConsulta = model.codConsulta
                )
            )
        }
        return result
    }

    fun transformMontoFijo(list: List<CalculadoraMontoFijo>): List<CalculadoraMontoFijoModel> {
        val result = arrayListOf<CalculadoraMontoFijoModel>()
        list.forEach {
            result.add(transformMontoFijo(it))
        }
        return result
    }

    private fun transformMontoFijo(domain: CalculadoraMontoFijo): CalculadoraMontoFijoModel {
        return CalculadoraMontoFijoModel(
            codigoRango = domain.codigoRango,
            tipoRango = domain.tipoRango,
            valorMinimoRango = domain.valorMinimoRango,
            valorMaximoRango = domain.valorMaximoRango,
            bonoExitosa = domain.bonoExitosa,
            numPedidosTotales = domain.numPedidosTotales,
            numPedidosMetaCobranzas = domain.numPedidosMetaCobranzas,
            promedioVentaRango = domain.promedioVentaRango,
            textoBonoExitoso = domain.textoBonoExitoso,
            textoInput = domain.textoInput,
            mensaje = domain.mensaje,
            campaniaProceso = domain.campaniaProceso,
            bonoNoExitosa = domain.bonoNoExitosa,
            textoBonoNoExitoso = domain.textoBonoNoExitoso
        )
    }

    fun transformDetallePago(list: List<PaymentDetail>): List<PaymentDetailModel> {
        val result = arrayListOf<PaymentDetailModel>()
        list.forEach { model ->
            result.add(
                PaymentDetailModel(
                    constancia = model.constancia,
                    cantidad = model.cantidad,
                    mensaje = model.mensaje
                )
            )
        }
        return result
    }


    fun transformDetalleConcurso(list: List<ContestDetail>): List<ContestDetailModel> {
        val result = arrayListOf<ContestDetailModel>()
        list.forEach { entity ->
            result.add(
                ContestDetailModel(
                    variable1 = entity.variable1,
                    nivelBT1 = entity.nivelBT1,
                    variable2 = entity.variable2,
                    nivelBT2 = entity.nivelBT2,
                    bono = entity.bono,
                    descripcionNivel = entity.descripcionNivel,
                    nivelSE = entity.nivelSE
                )
            )
        }
        return result
    }

    class CalculatorMapperParams(
        val currencySymbol: String,
        val campaign: String,
        val model: PartnerVariable
    )
}

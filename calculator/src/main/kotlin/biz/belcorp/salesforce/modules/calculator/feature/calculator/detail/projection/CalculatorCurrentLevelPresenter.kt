package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.entities.socialbonus.SocialBonus
import biz.belcorp.salesforce.core.domain.usecases.campania.ObtenerCampaniasUseCase
import biz.belcorp.salesforce.core.domain.usecases.configuration.ConfigurationUseCase
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.domain.usecases.socialbonus.SocialBonusUseCase
import biz.belcorp.salesforce.core.features.base.Presenter
import biz.belcorp.salesforce.core.features.handlers.CoroutineExceptionHandler
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.ContestDetail
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.PaymentDetail
import biz.belcorp.salesforce.modules.calculator.core.domain.usecase.*
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.CalculadoraDetalleConcursoVariableSociaModel
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.CalculadoraMontoFijo
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.CalculadoraMontoFijoModel
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.otherlevel.mapper.CalculatorMapper
import biz.belcorp.salesforce.modules.calculator.model.*
import biz.belcorp.salesforce.modules.calculator.util.Constant.EMPTY_DOUBLE
import biz.belcorp.salesforce.modules.calculator.util.Constant.LABEL_CALCULATOR_CONCURSO
import biz.belcorp.salesforce.modules.calculator.util.Constant.LABEL_CALCULATOR_NIVEL
import biz.belcorp.salesforce.modules.calculator.util.Constant.LABEL_CALCULATOR_PAV
import biz.belcorp.salesforce.modules.calculator.util.Constant.LABEL_CALCULATOR_PBV
import biz.belcorp.salesforce.modules.calculator.util.Constant.LABEL_CALCULATOR_PEDIDOS
import biz.belcorp.salesforce.modules.calculator.util.Constant.LABEL_CALCULATOR_TAG_COMISION
import biz.belcorp.salesforce.modules.calculator.util.Constant.LABEL_CALCULATOR_TAG_PAV
import biz.belcorp.salesforce.modules.calculator.util.Constant.LABEL_CALCULATOR_TAG_PBV
import biz.belcorp.salesforce.modules.calculator.util.Constant.LABEL_CALCULATOR_TAG_PEDIDOS
import kotlinx.coroutines.launch

class CalculatorCurrentLevelPresenter
constructor(
    private val getCalculadoraBonoSociaUseCase: SocialBonusUseCase,
    private val getCalculadoraDetalleConcursoSEUseCase: ContestDetailUseCase,
    private val getCalculadoraVariableSociaUseCase: PartnerVariableUseCase,
    private val getCalculadoraDetallePagoUseCase: PaymentDetailUseCase,
    private val getCalculadoraResultadoUseCase: CalculatorResultUseCase,
    private val getCampaignUseCase: ObtenerCampaniasUseCase,
    private val configurationRepository: ConfigurationUseCase,
    private val mapper: CalculatorMapper,
    private val getCalculadoraMontoFijoUseCase: GetCalculadoraMontoFijoUseCase,
    private val sessionUseCase: ObtenerSesionUseCase
) : Presenter, ViewModel() {

    private lateinit var view: CalculatorCurrentLevelView
    private var detalleConcursoList = mutableListOf<CalculadoraDetalleConcursoVariableSociaModel>()
    private val calculatorResultModel: CalculatingResultModel by lazy { CalculatingResultModel() }
    private var preDetailList = mutableListOf<CalculatingResultDetailModel>()
    private var extraDetailList = mutableListOf<CalculatingResultDetailModel>()
    private var montoFijoList: List<CalculadoraMontoFijo> = emptyList()
    private var flagCalculadoraNueva = false
    private val session get() = sessionUseCase.obtener()

    fun create(view: CalculatorCurrentLevelView) {
        this.view = view
    }

    override fun destroy() {
        super.destroy()
        getCalculadoraBonoSociaUseCase.dispose()
        getCalculadoraDetalleConcursoSEUseCase.dispose()
        getCalculadoraDetallePagoUseCase.dispose()
        getCampaignUseCase.dispose()
        getCalculadoraMontoFijoUseCase.dispose()
    }

    fun onPrepare() {
        viewModelScope.launch(handler) {
            io {
                val isNewCalculator =
                    getCalculadoraResultadoUseCase.getFlagNewCalculator(session.countryIso)
                val campaign = getCampaignUseCase.obtenerCampaniaActual()
                calculatorResultModel.campania = campaign.codigo
                view.showCampaign(campaign.nombreCorto.deleteHyphen())
                if (isNewCalculator) {
                    flagCalculadoraNueva = isNewCalculator
                    getCalculadoraMontoFijoUseCase.all(MontoFijoSubscriber())
                } else {
                    ui {
                        view.showStaticInputsGoal()
                    }
                }
                getCountryConfig(campaign.nombreCorto)
            }
        }
    }

    private suspend fun getCountryConfig(campaign: String?) {
        val currency = configurationRepository.getConfiguration().currencySymbol
        getVariablesSE(currency, campaign)
        getBonusSE()
        getDetalleConcursoSE()
    }

    fun getBonusSE() {
        getCalculadoraBonoSociaUseCase.all(BonoSociaListObserver())
    }

    fun getDetalleConcursoSE() {
        getCalculadoraDetalleConcursoSEUseCase.all(DetalleConcursoSociaListObserver())
    }

    fun runCalculationProfitDynamic(
        symbol: String?,
        listMontoFijo: List<CalculadoraMontoFijoModel>,
        valueIngresos: Int,
        valueCapit: Int
    ) {
        viewModelScope.launch(handler) {
            io {
                val partnerVariable = getCalculadoraVariableSociaUseCase.getPartnerVariable()
                mapper.transformVariableSocia(partnerVariable).let { variableSocia ->
                    val status = getProfitStatusDynamic(
                        symbol = symbol,
                        variableSocia = variableSocia,
                        listMontoFijo = listMontoFijo,
                        valueIngresos = valueIngresos,
                        valueCapit = valueCapit
                    )
                    ui {
                        view.showStatus(status)
                    }
                }
            }
        }
    }

    fun runCalculationProfitStatic(
        valuePAV: Int,
        valuePBV: Int,
        valueIngresos: Int,
        valueCapit: Int
    ) {
        viewModelScope.launch(handler) {
            io {
                val partnerVariable = getCalculadoraVariableSociaUseCase.getPartnerVariable()
                mapper.transformVariableSocia(partnerVariable).let { variableSocia ->
                    val status = getProfitStatusStatic(
                        variableSocia = variableSocia,
                        valuePAV = valuePAV,
                        valuePBV = valuePBV,
                        valueIngresos = valueIngresos,
                        valueCapit = valueCapit
                    )
                    ui {
                        view.showStatus(status)
                    }

                }
            }
        }
    }

    private suspend fun getVariablesSE(symbol: String?, campaign: String?) {
        val partnerVariable = getCalculadoraVariableSociaUseCase.getPartnerVariable()
        mapper.transformVariableSocia(partnerVariable).let { variableSocia ->
            ui {
                view.showCurrencySymbol(symbol)
                showValuesSE(variableSocia, symbol, campaign)
            }
        }
    }

    fun findBonus(codigoTipoBono: String) {
        getCalculadoraBonoSociaUseCase.one(BonoSociaObserver(), codigoTipoBono)
    }

    fun findCompetition(tipoVariable: String) {
        viewModelScope.launch(handler) {
            io {
                val detalleConcursoVariable =
                    detalleConcursoList.first { it.variable.equals(tipoVariable) }
                ui {
                    view.onDetalleConcurso(detalleConcursoVariable)
                }
            }
        }
    }

    fun runCalculationProfitExtra(
        bonusList: MutableList<SocialBonusModel>,
        contestsList: MutableList<CalculadoraDetalleConcursoVariableSociaModel>,
        bonoCambioNivel: Double
    ) {
        if (calculatorResultModel.calculated) {
            viewModelScope.launch(handler) {
                io {
                    calculateProfitExtra(bonusList, contestsList, bonoCambioNivel)
                    ui {
                        if (contestsList.isEmpty()) {
                            view.showBonusResult(calculatorResultModel)
                        }
                    }
                }
            }
        } else {
            view.showCalculationError()
        }
    }

    private fun calculateProfitExtra(
        bonusList: MutableList<SocialBonusModel>,
        contestsList: MutableList<CalculadoraDetalleConcursoVariableSociaModel>,
        bonoCambioNivel: Double
    ) {
        var size = preDetailList.size
        extraDetailList.clear()
        extraDetailList.addAll(preDetailList)
        calculatorResultModel.bono = bonoCambioNivel

        bonusList.forEach { bono ->
            val detalle = CalculatingResultDetailModel()
            val quantity = if (bono.ingresaCantidad) {
                (bono.cantidadIngresada).plus(bono.cantidadIngresadaAlt)
            } else {
                Constant.NUMBER_ONE
            }
            detalle.etiqueta = quantity.toString()
            detalle.cantidad = quantity
            detalle.descripcion = bono.descripcionTipoBono
            detalle.orden = size++
            detalle.valor = bono.montoUnitarioBono ?: EMPTY_DOUBLE
            extraDetailList.add(detalle)

        }

        if (contestsList.isNotEmpty()) {
            val v1 = contestsList.first().variable
            val n1 = contestsList.first().nivelSeleccionado
            val v2 = contestsList.getOrNull(Constant.NUMBER_ONE)?.variable
            val n2 = contestsList.getOrNull(Constant.NUMBER_ONE)?.nivelSeleccionado
            val model = ContestDetailModel(
                variable1 = v1,
                nivelBT1 = n1,
                variable2 = v2,
                nivelBT2 = n2,
                bono = null,
                descripcionNivel = null,
                nivelSE = null
            )
            verifyDetailContest(model)
        } else {
            addExtraCalculationValues()
        }
    }

    private fun verifyDetailContest(model: ContestDetailModel) {
        getCalculadoraDetalleConcursoSEUseCase.all(DetalleConcursoSociaObserver(model))
    }

    private fun addExtraCalculationValues() {
        var resultValue = EMPTY_DOUBLE
        val filteredList: MutableList<CalculatingResultDetailModel> = arrayListOf()
        extraDetailList.forEach { item ->
            val cantidad = item.cantidad
            val valor = item.valor
            if (cantidad == null) {
                resultValue += item.valor ?: EMPTY_DOUBLE
                filteredList.add(item)
            } else if (cantidad > Constant.NUMBER_ZERO && valor != null && valor > EMPTY_DOUBLE) {
                resultValue += cantidad.times(valor)
                filteredList.add(item)
            }
        }
        calculatorResultModel.detalleResultadoCalculadora = filteredList
        calculatorResultModel.valorResultado = resultValue
    }

    private fun showValuesSE(
        variableSocia: PartnerVariableModel,
        symbol: String?,
        campaign: String?
    ) {
        variableSocia.numeroActivasIniciales?.let { view.showActiveConsultants(it) }

        with(variableSocia) {
            porcentajeMetaRecuperacion = obtainRecoveryGoalPercentage(porcentajeMetaRecuperacion)
            view.showRecoveryGoalPercentage(porcentajeMetaRecuperacion)
        }

        view.showLevelChangeCard(
            isChangeLeve(variableSocia),
            campaign,
            variableSocia.bonoCambioNivel
        )
        when (variableSocia.indicadorMedicionMeta) {
            IM_META_VENTA -> obtainImMetaVenta(variableSocia, symbol)
            IM_META_PEDIDO -> obtainImMetaPedido(variableSocia)
            null -> {
                view.hideGoal()
            }
        }
        if (variableSocia.indicadorNuevaLider == Constant.NUMBER_ONE.toString() || sessionUseCase.obtener().isPreBronce) {
            view.setViewHideIconInformation()
        }
    }

    private fun obtainImMetaPedido(variableSocia: PartnerVariableModel) {
        when {
            variableSocia.metaPedido == null -> {
                view.showWarningMessageOrdersOrSalesGoal(isOrder = true)
                view.hideGoal()
            }
            variableSocia.metaPedido!! > Constant.NUMBER_ZERO -> view.showOrderGoal(variableSocia.metaPedido.toString())
            else -> view.hideGoal()
        }
    }

    private fun obtainImMetaVenta(variableSocia: PartnerVariableModel, symbol: String?) {
        with(variableSocia) {
            when {
                metaVenta == null -> {
                    view.showWarningMessageOrdersOrSalesGoal(isOrder = false)
                    view.hideGoal()
                }
                metaVenta!! > Constant.NUMBER_ZERO -> {
                    view.showSalesGoal("$symbol ${metaVenta?.doubleOrIntFormatWithCommas()}")
                }
                else -> view.hideGoal()
            }
        }
    }

    private fun isChangeLeve(variableSocia: PartnerVariableModel): Boolean {
        return (variableSocia.nivelID != variableSocia.nivelCambioCampania?.toInt()
            && variableSocia.bonoCambioNivel != null
            && variableSocia.bonoCambioNivel!! > ZERO_FLOAT)
    }

    private fun getProfitStatusStatic(
        variableSocia: PartnerVariableModel,
        valuePAV: Int,
        valuePBV: Int,
        valueIngresos: Int,
        valueCapit: Int
    ): Boolean {
        preDetailList.clear()

        var comparison = isComparacionIndicadorMedicionMeta(variableSocia, valuePAV, valuePBV)

        with(variableSocia) {
            porcentajeMetaRecuperacion = obtainRecoveryGoalPercentage(porcentajeMetaRecuperacion)

            if (indicadorMedicionRetencion == IM_RETENCION_EXIGE) {
                indicadorMedicionVariable = IMV_INGRESOS_O_CAPITALIZACION
            }
        }

        val imv = isInMetaVenta(variableSocia, valueIngresos, valueCapit)

        comparison = comparison && imv

        if (INDICATOR_NEW_LEADER_OFF == variableSocia.indicadorNuevaLider &&
            variableSocia.indicadorMedicionMeta != null
        ) {
            loadPedDetailListStatic(comparison, valuePAV, valuePBV, variableSocia)
        }

        calculatorResultModel.exitoso = comparison
        calculatorResultModel.calculated = true
        return comparison
    }

    private fun obtainRecoveryGoalPercentage(porcentajeMetaRecuperacion: Double): Double {
        return when (session.countryIso) {
            Pais.COLOMBIA.codigoIso -> porcentajeMetaRecuperacion.compareWithNumber(EMPTY_DOUBLE, COBRANZA)
            else -> COBRANZA
        }
    }

    private fun loadPedDetailListStatic(
        comparison: Boolean, valuePAV: Int, valuePBV: Int,
        variableSocia: PartnerVariableModel
    ) {

        var detalle: CalculatingResultDetailModel
        var profitPAV: Double
        var profitPBV: Double
        var comisionPAV: Double
        var comisionPBV: Double

        with(variableSocia) {
            val averagePAV: Double = promedioVentaPedidosAV ?: EMPTY_DOUBLE
            val averagePBV: Double = promedioVentaPedidosBV ?: EMPTY_DOUBLE

            comisionPAV = porcentajeComisionNEAV ?: EMPTY_DOUBLE
            comisionPBV = porcentajeComisionNEBV ?: EMPTY_DOUBLE

            if (comparison) {
                comisionPAV = porcentajeComisionEXAV ?: EMPTY_DOUBLE
                comisionPBV = porcentajeComisionEXBV ?: EMPTY_DOUBLE
            }

            val porcentajeMetaRecuperacion = porcentajeMetaRecuperacion.div(PERCENT)

            profitPAV = averagePAV.times(comisionPAV.div(PERCENT)).times(porcentajeMetaRecuperacion)
            profitPBV = averagePBV.times(comisionPBV.div(PERCENT)).times(porcentajeMetaRecuperacion)
        }

        detalle = CalculatingResultDetailModel().apply {
            etiqueta = valuePAV.toString()
            cantidad = valuePAV
            descripcion =
                LABEL_CALCULATOR_PAV.replace(LABEL_CALCULATOR_TAG_PAV, comisionPAV.toString())
            valor = profitPAV
            orden = Constant.NUMBER_ONE
            calculado = true
        }

        preDetailList.add(detalle)

        detalle = CalculatingResultDetailModel().apply {
            etiqueta = valuePBV.toString()
            cantidad = valuePBV
            descripcion =
                LABEL_CALCULATOR_PBV.replace(LABEL_CALCULATOR_TAG_PBV, comisionPBV.toString())
            valor = profitPBV
            orden = Constant.NUMBER_TWO
            calculado = true
        }

        preDetailList.add(detalle)
    }

    private fun isComparacionIndicadorMedicionMeta(
        variableSocia: PartnerVariableModel,
        valuePAV: Int,
        valuePBV: Int
    ): Boolean {
        return when (variableSocia.indicadorMedicionMeta) {
            IM_META_VENTA -> isComparisonInMetaVentaStatic(variableSocia, valuePAV, valuePBV)
            IM_META_PEDIDO -> isComparisonInMetaPedidoStatic(variableSocia, valuePAV, valuePBV)
            else -> true
        }
    }

    private fun isComparisonInMetaPedidoStatic(
        variableSocia: PartnerVariableModel,
        valuePAV: Int,
        valuePBV: Int
    ): Boolean {
        return when {
            variableSocia.metaPedido == null -> true
            variableSocia.metaPedido!! > Constant.NUMBER_ZERO -> valuePAV.plus(valuePBV) >= variableSocia.metaPedido!!
            else -> true
        }
    }

    private fun getProfitStatusDynamic(
        symbol: String?,
        variableSocia: PartnerVariableModel,
        listMontoFijo: List<CalculadoraMontoFijoModel>,
        valueIngresos: Int,
        valueCapit: Int
    ): Boolean {
        var comparison: Boolean
        preDetailList.clear()

        with(variableSocia) {
            porcentajeMetaRecuperacion = obtainRecoveryGoalPercentage(porcentajeMetaRecuperacion)

            comparison = when (indicadorMedicionMeta) {
                IM_META_VENTA -> isComparisonInMetaVentaDynamic(variableSocia, listMontoFijo)
                IM_META_PEDIDO -> isComparisonInMetaPedidoDynamic(variableSocia, listMontoFijo)
                else -> true
            }

            if (indicadorMedicionRetencion == IM_RETENCION_EXIGE) {
                indicadorMedicionVariable = IMV_INGRESOS_O_CAPITALIZACION
            }

            val imv = isInMetaVenta(variableSocia, valueIngresos, valueCapit)

            comparison = comparison && imv
        }

        if (INDICATOR_NEW_LEADER_OFF == variableSocia.indicadorNuevaLider &&
            variableSocia.indicadorMedicionMeta != null
        ) {
            loadPreDetailList(comparison, symbol, listMontoFijo)
        }

        calculatorResultModel.exitoso = comparison
        calculatorResultModel.calculated = true
        return comparison
    }

    private fun loadPreDetailList(
        comparison: Boolean, symbol: String?,
        listMontoFijo: List<CalculadoraMontoFijoModel>
    ) {

        var ordenDetail = Constant.NUMBER_ZERO

        var detalle: CalculatingResultDetailModel

        listMontoFijo.forEach {
            val orderDescription =
                LABEL_CALCULATOR_PEDIDOS.replace(LABEL_CALCULATOR_TAG_PEDIDOS, it.textoInput)

            val bono = if (comparison) {
                it.bonoExitosa.doubleOrInt()
            } else {
                it.bonoNoExitosa.doubleOrInt()
            }
            val orderComision =
                orderDescription.replace(LABEL_CALCULATOR_TAG_COMISION, "$symbol $bono")
            val orderValue = if (comparison) it.bonoExitosa else it.bonoNoExitosa

            ordenDetail++

            detalle = CalculatingResultDetailModel().apply {
                etiqueta = it.inputTextUser
                cantidad = it.inputTextUser.toInt()
                descripcion = orderComision
                valor = orderValue.toDouble()
                orden = ordenDetail
                calculado = true
            }
            preDetailList.add(detalle)
        }
    }

    private fun isComparisonInMetaPedidoDynamic(
        variableSocia: PartnerVariableModel,
        listMontoFijo: List<CalculadoraMontoFijoModel>
    ): Boolean {
        return when {
            variableSocia.metaPedido == null -> true
            variableSocia.metaPedido!! > Constant.NUMBER_ZERO -> getValuesPedido(listMontoFijo) >= variableSocia.metaPedido!!
            else -> true
        }
    }

    private fun getValuesPedido(listMontoFijo: List<CalculadoraMontoFijoModel>): Int {
        var valuesOrder = 0
        listMontoFijo.forEach {
            valuesOrder += it.inputTextUser.toIntOrNull().zeroIfNull()
        }
        return valuesOrder
    }

    private fun isInMetaVenta(
        variableSocia: PartnerVariableModel,
        valueIngresos: Int,
        valueCapit: Int
    ): Boolean {

        return when (variableSocia.indicadorMedicionVariable) {
            IMV_INGRESOS ->
                valueIngresos >= variableSocia.metaIngresos!!
            IMV_CAPITALIZACION ->
                valueCapit >= variableSocia.metaCapitalizacion!!
            IMV_INGRESOS_O_CAPITALIZACION, IMV_ACTIVAS ->
                valueIngresos >= variableSocia.metaIngresos!! ||
                    valueCapit >= variableSocia.metaCapitalizacion!!
            IMV_INGRESOS_Y_CAPITALIZACION ->
                valueIngresos >= variableSocia.metaIngresos!! &&
                    valueCapit >= variableSocia.metaCapitalizacion!!
            IMV_NINGUNO ->
                true
            else ->
                true
        }

    }

    private fun isComparisonInMetaVentaDynamic(
        variableSocia: PartnerVariableModel,
        listMontoFijo: List<CalculadoraMontoFijoModel>
    ): Boolean {

        with(variableSocia) {
            return if (metaVenta == null) {
                true
            } else if (metaVenta!! > Constant.NUMBER_ZERO &&
                promedioVentaPedidosAV != null &&
                promedioVentaPedidosBV != null
            ) {
                var total = 0f
                listMontoFijo.forEach {
                    total =
                        total.plus(it.inputTextUser.toFloat().times(it.promedioVentaRango.toFloat()))
                }
                total >= metaVenta!!
            } else {
                false
            }
        }
    }

    private fun isComparisonInMetaVentaStatic(
        variableSocia: PartnerVariableModel,
        valuePAV: Int,
        valuePBV: Int
    ): Boolean {
        with(variableSocia) {
            return when {
                metaVenta == null -> true
                (metaVenta!! > Constant.NUMBER_ZERO &&
                    promedioVentaPedidosAV != null &&
                    promedioVentaPedidosBV != null) -> {
                    val totalPAV: Double = promedioVentaPedidosAV?.times(valuePAV)
                        ?: EMPTY_DOUBLE
                    val totalPBV: Double = promedioVentaPedidosBV?.times(valuePBV)
                        ?: EMPTY_DOUBLE
                    totalPAV.plus(totalPBV) >= metaVenta!!
                }
                else -> false
            }
        }
    }

    inner class MontoFijoSubscriber : BaseSingleObserver<List<CalculadoraMontoFijo>>() {
        override fun onError(e: Throwable) = e.printStackTrace()
        override fun onSuccess(t: List<CalculadoraMontoFijo>) {
            montoFijoList = t
            view.showDynamicInputsGoal(mapper.transformMontoFijo(t))
        }
    }

    inner class BonoSociaListObserver : BaseSingleObserver<List<SocialBonus>>() {
        override fun onError(e: Throwable) = e.printStackTrace()
        override fun onSuccess(t: List<SocialBonus>) {
            view.showBonusSE(mapper.transformBonoSocia(t))
        }
    }

    inner class BonoSociaObserver : BaseSingleObserver<SocialBonus>() {
        override fun onError(e: Throwable) = e.printStackTrace()
        override fun onSuccess(t: SocialBonus) {
            mapper.transformBonoSocia(t)?.let {
                getCalculadoraDetallePagoUseCase.all(CalculadoraDetallePagoObserver(it))
            }
        }
    }

    inner class CalculadoraDetallePagoObserver(private val bonoSocia: SocialBonusModel) :
        BaseSingleObserver<List<PaymentDetail>>() {
        override fun onError(e: Throwable) = e.printStackTrace()
        override fun onSuccess(t: List<PaymentDetail>) {
            bonoSocia.codConsulta?.let {
                mapper.transformDetallePago(t)
                    .filter { bonoSocia.codConsulta == it.constancia }
                    .let { detalle ->
                        bonoSocia.apply {
                            cantidad = detalle.first().cantidad
                            mensaje = detalle.first().mensaje
                            cantidadAlt = detalle.getOrNull(Constant.NUMBER_ONE)?.cantidad
                            mensajeAlt = detalle.getOrNull(Constant.NUMBER_ONE)?.mensaje
                        }
                    }
            }
            view.onBono(bonoSocia)
        }
    }

    inner class DetalleConcursoSociaObserver(private val model: ContestDetailModel) :
        BaseSingleObserver<List<ContestDetail>>() {
        override fun onError(e: Throwable) = e.printStackTrace()
        override fun onSuccess(t: List<ContestDetail>) {
            val contest = mapper.transformDetalleConcurso(t).firstOrNull {
                (it.variable1 == model.variable1 &&
                    it.nivelBT1 == model.nivelBT1 &&
                    it.variable2 == model.variable2 &&
                    it.nivelBT2 == model.nivelBT2) ||
                    (it.variable1 == model.variable2 &&
                        it.nivelBT1 == model.nivelBT2 &&
                        it.variable2 == model.variable1 &&
                        it.nivelBT2 == model.nivelBT1)
            }
            if (contest != null) {
                val detalle = CalculatingResultDetailModel()
                detalle.etiqueta = getEtiqueta(contest)
                detalle.cantidad = Constant.NUMBER_ONE
                detalle.descripcion = LABEL_CALCULATOR_CONCURSO
                detalle.orden = extraDetailList.size + Constant.NUMBER_ONE
                detalle.valor = contest.bono ?: EMPTY_DOUBLE
                extraDetailList.add(detalle)
                addExtraCalculationValues()
            }
            if (contest == null) {
                view.showContestDependencyAlert(Constant.COLON)
            } else {
                view.showBonusResult(calculatorResultModel)
            }
        }

        private fun getEtiqueta(contest: ContestDetailModel): String = when {
            contest.nivelBT1 != null && contest.nivelBT2 != null ->
                "$LABEL_CALCULATOR_NIVEL ${contest.nivelBT1}, ${contest.nivelBT2}"
            contest.nivelBT1 != null && contest.nivelBT2 == null ->
                "$LABEL_CALCULATOR_NIVEL ${contest.nivelBT1}"
            contest.nivelBT1 == null && contest.nivelBT2 != null ->
                "$LABEL_CALCULATOR_NIVEL ${contest.nivelBT2}"
            else -> ""
        }
    }

    inner class DetalleConcursoSociaListObserver :
        BaseSingleObserver<List<ContestDetail>>() {
        override fun onError(e: Throwable) = e.printStackTrace()
        override fun onSuccess(t: List<ContestDetail>) {
            mapper.transformDetalleConcurso(t).let { list ->
                if (!list.isNullOrEmpty()) {
                    if (!list.firstOrNull()?.variable1.isNullOrEmpty()) {
                        val listNivel = mutableListOf<Int>()
                        (list.distinctBy { it.nivelBT1 }).forEach {
                            it.nivelBT1?.let { it1 -> listNivel.add(it1) }
                        }
                        detalleConcursoList.add(
                            CalculadoraDetalleConcursoVariableSociaModel(
                                list.firstOrNull()?.variable1,
                                listNivel
                            )
                        )
                    }
                    if (!list.firstOrNull()?.variable2.isNullOrEmpty()) {
                        val listNivel = mutableListOf<Int>()
                        (list.distinctBy { it.nivelBT2 }).forEach {
                            it.nivelBT2?.let { it1 -> listNivel.add(it1) }
                        }
                        detalleConcursoList.add(
                            CalculadoraDetalleConcursoVariableSociaModel(
                                list.firstOrNull()?.variable2,
                                listNivel
                            )
                        )
                    }
                }
                view.showDetailCompetitionSE(detalleConcursoList)
            }
        }
    }

    companion object {
        const val IMV_NINGUNO = "0"
        const val IMV_INGRESOS = "1"
        const val IMV_CAPITALIZACION = "2"
        const val IMV_INGRESOS_O_CAPITALIZACION = "3" // || 5
        const val IMV_INGRESOS_Y_CAPITALIZACION = "4"
        const val IMV_ACTIVAS = "5"
        const val INDICATOR_NEW_LEADER_OFF = "0"
        const val IM_META_PEDIDO = "P"
        const val IM_META_VENTA = "V"
        const val IM_RETENCION_EXIGE = "1" //PERU
        const val COBRANZA = 85.0
        const val PERCENT = 100f
        const val ZERO_FLOAT = 0.0f
    }

    private fun isFlagCalculadoraNueva(): Boolean {
        return flagCalculadoraNueva
    }

    fun loadVersionCalculator() {
        if (isFlagCalculadoraNueva()) {
            view.setDynamicViews()
            view.setDynamicListeners()
        } else {
            view.setStaticViews()
            view.setStaticListeners()
        }
    }

    fun actionCalculateProfitExtra() {
        if (isFlagCalculadoraNueva()) {
            view.actionCalculateProfitExtraDynamic()
        } else {
            view.actionCalculateProfitExtraStatic()
        }
    }

    fun actionDialogProfitTable(symbol: String?) {
        view.openDialogInformation(
            mapper.transformMontoFijo(montoFijoList),
            sessionUseCase.obtener(),
            symbol
        )
    }

    fun isListValid(listMontoFijo: List<CalculadoraMontoFijoModel>): Boolean {
        listMontoFijo.forEach {
            if (it.inputTextUser.isEmpty()) {
                return false
            }
        }
        return true
    }

    fun getCurrentCountryIso() = session.countryIso

    private val handler = CoroutineExceptionHandler { _, exception ->
        val message = exception.message.orEmpty()
        Log.e(CalculatorCurrentLevelPresenter::javaClass.name, message)
    }
}

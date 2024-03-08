package biz.belcorp.salesforce.modules.calculator.feature.calculator.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.features.broadcast.SenderEstadoProgress
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.calculator.R
import biz.belcorp.salesforce.modules.calculator.feature.Navigator
import biz.belcorp.salesforce.modules.calculator.feature.calculator.analitycs.AnalyticsCalculatorViewModel
import biz.belcorp.salesforce.modules.calculator.feature.calculator.base.CALCULATOR_SCOPE
import biz.belcorp.salesforce.modules.calculator.feature.calculator.base.NavigationStateObserver
import biz.belcorp.salesforce.modules.calculator.feature.calculator.base.NavigatorResultViewState
import biz.belcorp.salesforce.modules.calculator.feature.calculator.base.SHARED_NAV
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.CalculatorResultViewState
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.otherlevel.UpperLevelViewModel
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.otherlevel.UpperLevelViewState
import biz.belcorp.salesforce.modules.calculator.feature.calculator.header.CalculatorCabeceraFragment
import biz.belcorp.salesforce.modules.calculator.feature.calculator.result.CalculatorResultViewModel.Companion.IM_META_PEDIDO
import biz.belcorp.salesforce.modules.calculator.feature.calculator.result.CalculatorResultViewModel.Companion.IM_META_VENTA
import biz.belcorp.salesforce.modules.calculator.model.CalculatingResultModel
import biz.belcorp.salesforce.modules.calculator.util.Constant.IS_CAMPANIA_NAVIDAD
import biz.belcorp.salesforce.modules.calculator.util.Constant.IS_DIAMOND
import kotlinx.android.synthetic.main.fragment_calculator_result.*
import org.koin.android.viewmodel.ext.android.viewModel

class CalculatorResultFragment : BaseFragment(), Navigator {

    private val analyticsCalculatorViewModel by viewModel<AnalyticsCalculatorViewModel>()
    private val senderEstadoProgress: SenderEstadoProgress by injectFragment()
    private val upperLevelViewModel by viewModel<UpperLevelViewModel>()
    private val calculatorResultViewModel by viewModel<CalculatorResultViewModel>()
    private val stateObserver by injectScoped<NavigationStateObserver>(
        CALCULATOR_SCOPE,
        SHARED_NAV
    )
    private val navigator: Navigator? = null
    private var isDiamond: Boolean = false
    private var isChristmas: Boolean = false
    private var isSaved: Boolean = false
    private var resultModel = CalculatingResultModel()
    private var partnerLevel: String? = null

    override fun getLayout(): Int = R.layout.fragment_calculator_result

    private val observerResultResponse = Observer<CalculatorResultViewState> { state ->
        when (state) {
            is CalculatorResultViewState.Success -> {
                when (state.partnerVariable.indicadorMedicionMeta) {
                    IM_META_VENTA -> {
                        if (state.partnerVariable.metaVenta == null) {
                            showWarningMessageSalesGoal()
                        }
                    }
                    IM_META_PEDIDO -> {
                        if (state.partnerVariable.metaPedido == null) {
                            showWarningMessageOrdersGoal()
                        }
                    }
                }
                partnerLevel = state.partnerVariable.nivelCambioCampania
                showCampaign(state.partnerVariable.campaign)
                showResultValues(state.partnerVariable.currencySymbol)
                upperLevelViewModel.getUpperLevel()
            }
            is CalculatorResultViewState.Inserted -> resultSaved()
            is CalculatorResultViewState.Deleted -> starNewCalculation()
            is CalculatorResultViewState.Failed ->
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
        }
        hideProgress()
    }

    private val observerDataResponse = Observer<UpperLevelViewState> { state ->
        when (state) {
            is UpperLevelViewState.Success -> {
                val level = state.data?.firstOrNull { it.nivelID == partnerLevel?.toInt() }
                level?.let { showLevelToChange(it.descripcion) }
            }
            is UpperLevelViewState.Failed ->
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun setupViewModels() {
        upperLevelViewModel.viewState.observe(viewLifecycleOwner, observerDataResponse)
        calculatorResultViewModel.viewState.observe(viewLifecycleOwner, observerResultResponse)
        upperLevelViewModel.getUpperLevel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.getArgs()
        this.setHeader()
        setupViewModels()
        showProgress()
        calculatorResultViewModel.getPartnerVariable()
        analyticsCalculatorViewModel.sendCalculatorScreen()
    }

    private fun getArgs() {
        arguments?.let { bundle ->
            isChristmas = bundle.getBoolean(IS_CAMPANIA_NAVIDAD, false)
            isDiamond = bundle.getBoolean(IS_DIAMOND, false)
            resultModel = requireNotNull(bundle.getParcelable(KEY_CALCULATOR_RESULT))
            isSaved = bundle.getBoolean(KEY_CALCULATOR_SAVED, false)
        }
    }

    private fun showResultValues(symbol: String?) {
        arguments?.let {
            setTextAndColorToSuccessOrNotSuccessText(resultModel.exitoso)
            rvwCalculatorResult?.apply {
                layoutManager = GridLayoutManager(context, Constant.NUMBER_ONE)
                itemAnimator = DefaultItemAnimator()
                isNestedScrollingEnabled = false
                adapter = CalculatorResultAdapter(resultModel.detalleResultadoCalculadora, symbol)
            }
            if (resultModel.bono != null && resultModel.bono!! > 0) {
                lblBonoCambioNivel?.visible()
                lblBonoCambioNivel?.setRectangleText(
                    getString(
                        R.string.calculator_result_order_gain_plus,
                        symbol,
                        resultModel.bono?.toInt()?.formatWithThousands()
                    )
                )
                txvAlso?.visible()
            } else {
                lblBonoCambioNivel?.gone()
                txvAlso?.gone()
            }
            lblGananciaPedido?.text = getString(
                R.string.calculator_result_order_gain,
                symbol,
                resultModel.valorResultado?.doubleOrIntFormatWithCommas()
            )
            btnCalculatorResultSave?.text =
                getString(if (isSaved) R.string.calculator_result_new_calculation else R.string.calculator_result_save_calculation)
            btnCalculatorResultSave?.setOnClickListener {
                if (isSaved) {
                    calculatorResultViewModel.newCalculation()
                    analyticsCalculatorViewModel.sendCalculatorEvent(TagAnalytics.EVENTO_CALCULADORA_NUEVO_CALCULO)
                } else {
                    showProgress()
                    calculatorResultViewModel.saveResult(resultModel)
                    analyticsCalculatorViewModel.sendCalculatorEvent(TagAnalytics.EVENTO_CALCULADORA_GUARDAR_GANANCIA)
                }
            }
        }
    }

    private fun showCampaign(campaign: String?) {
        txvHeaderCampaignLabel?.text = getString(R.string.calculator_result_could_you_win, campaign)
    }

    private fun showLevelToChange(level: String?) {
        lblBonoCambioNivel?.setText(getString(R.string.calculator_result_change_level_to, level))
    }

    private fun showWarningMessageSalesGoal() {
        cvwCalculatorWarningMessage?.visible()
        txvCalculatorWarningMessage?.text = getString(R.string.calculator_message_sales_goal)
    }

    private fun showWarningMessageOrdersGoal() {
        cvwCalculatorWarningMessage?.visible()
        txvCalculatorWarningMessage?.text = getString(R.string.calculator_message_orders_goal)
    }

    private fun setTextAndColorToSuccessOrNotSuccessText(exitoso: Boolean) {
        if (exitoso) {
            setTextAndColorToSuccessText()
        } else {
            setTextAndColorToNotSuccessText()
        }
    }

    private fun setTextAndColorToNotSuccessText() {
        txvSuccessfulDot?.textColor = ContextCompat.getColor(context!!, R.color.estado_negativo)
        txvSuccessfulState?.text = getString(R.string.calculator_result_be_unsucceful)
        txvSuccessfulState?.textColor = ContextCompat.getColor(context!!, R.color.estado_negativo)
    }

    private fun setTextAndColorToSuccessText() {
        txvSuccessfulDot?.textColor = ContextCompat.getColor(context!!, R.color.estado_positivo)
        txvSuccessfulState?.text = getString(R.string.calculator_result_be_succeful)
        txvSuccessfulState?.textColor = ContextCompat.getColor(context!!, R.color.estado_positivo)
    }

    private fun resultSaved() {
        btnCalculatorResultSave?.text = getString(R.string.calculator_result_new_calculation)
        btnCalculatorResultSave?.setOnClickListener {
            calculatorResultViewModel.newCalculation()
            analyticsCalculatorViewModel.sendCalculatorEvent(TagAnalytics.EVENTO_CALCULADORA_NUEVO_CALCULO)
        }
        stateObserver.postValue(NavigatorResultViewState.CLEAR(resultModel))
    }

    private fun starNewCalculation() {
        stateObserver.postValue(NavigatorResultViewState.RESET)
    }

    override fun retroceder() {
        activity?.onBackPressed()
    }

    private fun showProgress() {
        senderEstadoProgress.mostrarProgress()
    }

    private fun hideProgress() {
        senderEstadoProgress.ocultarProgress()
    }

    private fun setHeader() {
        val header = CalculatorCabeceraFragment.newInstance()
        header.navigator = navigator
        childFragmentManager
            .beginTransaction()
            .replace(R.id.flCalculatorResultHeader, header)
            .commit()
    }

    companion object {

        const val KEY_CALCULATOR_RESULT = "resultModel"
        const val KEY_CALCULATOR_SAVED = "saved"

        @JvmStatic
        fun newInstance(
            result: CalculatingResultModel,
            saved: Boolean,
            isChristmas: Boolean,
            isDiamond: Boolean
        ): CalculatorResultFragment {
            return CalculatorResultFragment()
                .withArguments(
                    KEY_CALCULATOR_RESULT to result,
                    KEY_CALCULATOR_SAVED to saved,
                    IS_CAMPANIA_NAVIDAD to isChristmas,
                    IS_DIAMOND to isDiamond
                )
        }
    }
}

package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.campaignprojection

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.SECTION_PARTNER
import biz.belcorp.salesforce.core.constants.Constant.ZERO_FLOAT
import biz.belcorp.salesforce.core.constants.Level
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.entities.SuccessfuHistoricEntity_.value
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.calculator.R
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.CampaignProjectionModel
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.CampaignProjectionRetentionModel
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.OrderModel
import biz.belcorp.salesforce.modules.calculator.util.*
import kotlinx.android.synthetic.main.fragment_campaign_projection.*
import org.koin.android.viewmodel.ext.android.viewModel

const val CLASSIFICATION_ESTABLECIDA = "ESTABLECIDA"
const val CLASSIFICATION_REINGRESO = "REINGRESO"

class CampaignProjectionFragment : BaseFragment() {

    private val campaignProjectionViewModel by viewModel<CampaignProjectionViewModel>()

    //Capitalization
    private var pegsInMySection = 0
    private var initialActives = 0
    private var entries = 0
    private var reEntries = 0
    private var pegsRetention = 0
    private var projectedCapitalization = 0
    private var finalProjectedActives = 0
    private var finalsLastYearActives = 1
    private var capitalizationCanBeSaved = true

    //Orders
    private var lowValueInput = 0
    private var lowValueGain = 0f
    private var lowValueGainProjected = 0f
    private var lowValuePlusInput = 0
    private var lowValuePlusGain = 0f
    private var lowValuePlusGainProjected = 0f
    private var highValueInput = 0
    private var highValueGain = 0f
    private var highValueGainProjected = 0f
    private var highValuePlusInput = 0
    private var highValuePlusGain = 0f
    private var highValuePlusGainProjected = 0f
    private var totalOrdersProjected = 0
    private var totalOrdersGoal = 0
    private var totalGain = 0
    private var lowValueItem: OrderModel? = null
    private var lowValuePlusItem: OrderModel? = null
    private var highValueItem: OrderModel? = null
    private var highValuePlusItem: OrderModel? = null
    private var currentSymbol: String? = null

    //Retention
    private var reEntriesLatest5C = 0
    private var retentionExpected1 = 0
    private var retentionExpected2 = 0
    private var retentionExpected3 = 0
    private var retentionExpected4 = 0
    private var retentionExpected5 = 0
    private var retentionExpected6 = 0
    private var retentionExpected7 = 0
    private var retentionExpected8 = 0
    private lateinit var retentionList: List<CampaignProjectionRetentionModel>

    //Status SE
    private var isNewClassification = false
    private var classification: String? = null
    private var level: String? = null
    private var entryGoal: Int? = null
    private var capitalizationGoal: Int? = null
    private var isDisclaimerSuccess = false


    private lateinit var campaignProjectionModel: CampaignProjectionModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!arguments?.getString(SECTION_PARTNER).isNullOrBlank()) {
            setupViewModelForPartner()
        } else {
            setupViewModel()
            setUpSaveButton()
        }

    }

    private fun setupViewModelForPartner() {
        campaignProjectionViewModel.viewSyncPartnerState.observe(viewLifecycleOwner, Observer {
            setupViewModel()
            setUpSaveButton()
        })
        campaignProjectionViewModel.fetchCampaignProjectionInfoPartner(arguments?.getString(SECTION_PARTNER)!!)
    }

    private fun setupViewModel() {
        campaignProjectionViewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        campaignProjectionViewModel.fetchCampaignProjectionInfo(arguments?.getString(SECTION_PARTNER))
        campaignProjectionViewModel.viewStatusSEState.observe(
            viewLifecycleOwner,
            viewStatusSEStateObserver
        )
        campaignProjectionViewModel.viewResultsLastCampaignState.observe(
            viewLifecycleOwner,
            viewResultsLastCampaignStateObserver
        )
    }

    private fun setUpCapitalizationInputs() {
        et_projected_entries_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Nothing to implement
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                calculateCapitalizationInputs()
                calculateOrdersInputs()
            }

            override fun afterTextChanged(p0: Editable?) {
                //Nothing to implement
            }

        })

        et_projected_reentries_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Nothing to implement
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                calculateCapitalizationInputs()
                calculateOrdersInputs()
            }

            override fun afterTextChanged(p0: Editable?) {
                //Nothing to implement
            }

        })

        et_retained_pegs_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Nothing to implement
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!p0.toString().isNullOrEmpty()
                    && !p0.toString().isNullOrBlank() &&
                    p0.toString().toInt() <= pegsInMySection
                ) {
                    calculateCapitalizationInputs()
                    calculateOrdersInputs()
                    capitalizationCanBeSaved = true
                } else {
                    capitalizationCanBeSaved = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                //Nothing to implement
            }

        })
    }

    private fun calculateCapitalizationInputs() {
        pegsRetention = if (et_retained_pegs_input.text.isNullOrBlank())
            NUMBER_ZERO else
            et_retained_pegs_input.text.toString().toInt()

        val projectedExpenses = pegsInMySection.minus(pegsRetention)

        et_projected_expenses_input.setText(projectedExpenses.toString())

        entries = if (et_projected_entries_input.text.isNullOrBlank()) NUMBER_ZERO else
            et_projected_entries_input.text.toString().toInt()

        reEntries = if (et_projected_reentries_input.text.isNullOrBlank()) NUMBER_ZERO else
            et_projected_reentries_input.text.toString().toInt()

        projectedCapitalization = entries
            .plus(reEntries).minus(projectedExpenses)

        et_projected_capitalization_input.setText(projectedCapitalization.toString())

        finalProjectedActives = initialActives.plus(projectedCapitalization)

        et_final_actives_input.setText(finalProjectedActives.toString())

        val retentionPercentage =
            finalProjectedActives.toFloat().div(
                (if (finalsLastYearActives > 0)
                    finalsLastYearActives else NUMBER_ONE)
            )
        tv_active_retention_percentage.text =
            "${retentionPercentage.toHundredPercentage().formatWithDecimalThousands()} %"

        if (Pais.MEXICO.codigoIso == UserProperties.session?.countryIso && (level == Level.PRE_BRONZE)) {

            tv_capitalization_value.text = projectedCapitalization.toString()

            val capiPoint = getString(
                R.string.cycle_news_profit_proyected_6d6_function,
                currentSymbol,
                campaignProjectionModel.capiGainByPointProjectionCurrent.toString()
            )
            tv_earning_capi_point_value.text = capiPoint

            if(campaignProjectionModel.isNotNull() && campaignProjectionModel.capiGainByPointProjectionCurrent.isNotNull()
                && projectedCapitalization.isNotNull()) {
                val data = campaignProjectionModel.capiGainByPointProjectionCurrent!!.times(
                    projectedCapitalization
                )
                val earning = getString(
                    R.string.cycle_news_profit_proyected_6d6_function,
                    currentSymbol,
                    data.toString()
                )

                tv_earning_capi_value.text = earning

            }



        }

    }

    private fun setUpOrdersInputs() {
        et_orders_low_value_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Nothing to implement
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                calculateOrdersInputs()
            }

            override fun afterTextChanged(p0: Editable?) {
                //Nothing to implement
            }

        })

        et_orders_low_value_plus_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Nothing to implement
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                calculateOrdersInputs()
            }

            override fun afterTextChanged(p0: Editable?) {
                //Nothing to implement
            }

        })

        et_orders_high_value_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Nothing to implement
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                calculateOrdersInputs()
            }

            override fun afterTextChanged(p0: Editable?) {
                //Nothing to implement
            }

        })

        et_orders_high_value_plus_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Nothing to implement
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                calculateOrdersInputs()
            }

            override fun afterTextChanged(p0: Editable?) {
                //Nothing to implement
            }

        })
    }

    private fun calculateOrdersInputs() {
        lowValueInput = if (et_orders_low_value_input.text.isNullOrBlank())
            NUMBER_ZERO else
            et_orders_low_value_input.text.toString().toInt()

        if (lowValuePlusItem.isNotNull()) {
            lowValuePlusInput = if (et_orders_low_value_plus_input.text.isNullOrBlank())
                NUMBER_ZERO else
                et_orders_low_value_plus_input.text.toString().toInt()
        }

        highValueInput = if (et_orders_high_value_input.text.isNullOrBlank())
            NUMBER_ZERO else
            et_orders_high_value_input.text.toString().toInt()

        highValuePlusInput = if (et_orders_high_value_plus_input.text.isNullOrBlank())
            NUMBER_ZERO else
            et_orders_high_value_plus_input.text.toString().toInt()


        lowValueGainProjected = lowValueInput * lowValueGain
        tv_low_value_gain_per_order_projected_value.text =
            getString(
                R.string.total_gain_number,
                currentSymbol,
                lowValueGainProjected.formatWithDecimalThousands()
            )

        if (lowValuePlusItem.isNotNull()) {
            lowValuePlusGainProjected = lowValuePlusInput * lowValuePlusGain
            tv_low_value_plus_gain_per_order_projected_value.text =
                getString(
                    R.string.total_gain_number,
                    currentSymbol,
                    lowValuePlusGainProjected.formatWithDecimalThousands()
                )
        }
        highValueGainProjected = highValueInput * highValueGain
        tv_high_value_gain_per_order_projected_value.text =
            getString(
                R.string.total_gain_number,
                currentSymbol,
                highValueGainProjected.formatWithDecimalThousands()
            )

        highValuePlusGainProjected = highValuePlusInput * highValuePlusGain
        tv_high_value_plus_gain_per_order_projected_value.text =
            getString(
                R.string.total_gain_number,
                currentSymbol,
                highValuePlusGainProjected.formatWithDecimalThousands()
            )


        totalOrdersProjected = lowValueInput.plus(highValueInput).plus(highValuePlusInput)
            .plus(if (lowValuePlusItem.isNotNull()) lowValuePlusInput else NUMBER_ZERO)
        if (totalOrdersProjected >= totalOrdersGoal) {
            tv_total_orders_goal_label.isSelected = false
            tv_total_orders_goal_label.text = getString(R.string.goal_achieved)
        } else {
            tv_total_orders_goal_label.isSelected = true
            tv_total_orders_goal_label.text = getString(R.string.goal_not_achieved)
        }
        tv_total_orders_number.text = totalOrdersProjected.toString()

        totalGain = (lowValueInput * lowValueGain)
            .plus((highValueInput * highValueGain))
            .plus((highValuePlusInput * highValuePlusGain))
            .plus(
                if (lowValuePlusItem.isNotNull())
                    (lowValuePlusInput * lowValuePlusGain) else ZERO_FLOAT
            ).toInt()
        tv_total_gain_number.text = getString(
            R.string.total_gain_number,
            currentSymbol,
            totalGain.formatWithThousands()
        )

        val activityProjected =
            totalOrdersProjected.toFloat().div(
                (if (finalProjectedActives > 0)
                    finalProjectedActives else NUMBER_ONE)
            )
        tv_projected_activity_percentage_number.text =
            "${activityProjected.toHundredPercentage().formatWithDecimalThousands()} %"

        tv_projected_pegs_cx_number.text =
            finalProjectedActives.minus(totalOrdersProjected).toString()

        showSuccessMessage()

    }


    private fun updateOrdersValues() {

        if (lowValueItem.isNotNull()) {
            lowValueGain =
                if (!isDisclaimerSuccess && lowValueItem?.gainPerOrderNotSuccess.isNotNull())
                    lowValueItem!!.gainPerOrderNotSuccess!! else lowValueItem!!.gainPerOrder!!
            lowValueGainProjected = lowValueInput * lowValueGain
            tv_low_value_gain_per_order_value.text = lowValueGain.toString()
            tv_low_value_gain_per_order_projected_value.text =
                getString(
                    R.string.total_gain_number,
                    currentSymbol,
                    lowValueGainProjected.formatWithDecimalThousands()
                )
        }
        if (lowValuePlusItem.isNotNull()) {
            lowValuePlusGain =
                if (!isDisclaimerSuccess && lowValuePlusItem!!.gainPerOrderNotSuccess.isNotNull())
                    lowValuePlusItem!!.gainPerOrderNotSuccess!! else lowValuePlusItem!!.gainPerOrder!!
            lowValuePlusGainProjected = lowValuePlusInput * lowValuePlusGain
            tv_low_value_plus_gain_per_order_value.text = lowValuePlusGain.toString()
            tv_low_value_plus_gain_per_order_projected_value.text =
                getString(
                    R.string.total_gain_number,
                    currentSymbol,
                    lowValuePlusGainProjected.formatWithDecimalThousands()
                )
        }
        if (highValueItem.isNotNull()) {
            highValueGain =
                if (!isDisclaimerSuccess && highValueItem!!.gainPerOrderNotSuccess.isNotNull())
                    highValueItem!!.gainPerOrderNotSuccess!! else highValueItem!!.gainPerOrder!!
            highValueGainProjected = highValueInput * highValueGain
            tv_high_value_gain_per_order_value.text = highValueGain.toString()
            tv_high_value_gain_per_order_projected_value.text =
                getString(
                    R.string.total_gain_number,
                    currentSymbol,
                    highValueGainProjected.formatWithDecimalThousands()
                )
        }

        if (highValuePlusItem.isNotNull()) {
            highValuePlusGain =
                if (!isDisclaimerSuccess && highValuePlusItem!!.gainPerOrderNotSuccess.isNotNull())
                    highValuePlusItem!!.gainPerOrderNotSuccess!! else highValuePlusItem!!.gainPerOrder!!
            highValuePlusGainProjected = highValuePlusInput * highValuePlusGain
            tv_high_value_plus_gain_per_order_value.text = highValuePlusGain.toString()
            tv_high_value_plus_gain_per_order_projected_value.text =
                getString(
                    R.string.total_gain_number,
                    currentSymbol,
                    highValuePlusGainProjected.formatWithDecimalThousands()
                )
        }

        totalGain = (lowValueInput * lowValueGain)
            .plus((highValueInput * highValueGain))
            .plus((highValuePlusInput * highValuePlusGain))
            .plus(
                if (lowValuePlusItem.isNotNull())
                    (lowValuePlusInput * lowValuePlusGain) else ZERO_FLOAT
            ).toInt()
        tv_total_gain_number.text = getString(
            R.string.total_gain_number,
            currentSymbol,
            totalGain.formatWithThousands()
        )
    }

    private val viewStateObserver = Observer<CampaignProjectionViewState> {
        when (it) {
            is CampaignProjectionViewState.Success -> {
                if (it.campaignProjectionModel.pegsInMySection.isNotNull()) {
                    showInfo(it.campaignProjectionModel)
                }
            }

            is CampaignProjectionViewState.Failed -> showError()
        }
    }

    private val viewStatusSEStateObserver = Observer<StatusSEViewState> {
        when (it) {
            is StatusSEViewState.Success -> {
                isNewClassification = Constant.NEW == it.SaleForceStatus.classification
                classification = it.SaleForceStatus.classification
                level = it.SaleForceStatus.level
                showSuccessMessage()
                if (campaignProjectionModel.isNotNull()) {
                    showCapitalizationInfo(campaignProjectionModel)
                    fillCicloDeNuevas(campaignProjectionModel.retentionList, reEntriesLatest5C)
                    showRetentionPercentage6d6Message()
                }
            }

            is StatusSEViewState.Failed -> hideProjectionSuccessMessage()
        }
    }

    private val viewResultsLastCampaignStateObserver = Observer<ResultsLastCampaignViewState> {
        when (it) {
            is ResultsLastCampaignViewState.Success -> {
                entryGoal = it.model.result?.entriesGoal
                capitalizationGoal = it.model.result?.capitalizationGoal
                showSuccessMessage()
            }

            is ResultsLastCampaignViewState.Failure -> hideProjectionSuccessMessage()
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun showInfo(model: CampaignProjectionModel) {
        campaignProjectionModel = model
        showCapitalizationInfo(model)
        showOrdersInfo(model)
        reEntriesLatest5C = model.reEntriesLatest5C!!
        retentionList = model.retentionList.toCollection(mutableListOf())
        fillCicloDeNuevas(model.retentionList, reEntriesLatest5C)
        campaignProjectionViewModel.loadSaleForceStatus(arguments?.getString(SECTION_PARTNER))
        campaignProjectionViewModel.getCampaignResults(arguments?.getString(SECTION_PARTNER))
        showRetentionPercentage6d6Message()
    }

    @SuppressLint("SetTextI18n")
    private fun showRetentionPercentage6d6Message() {

        cl_6d6_profit_proyected_extended.visibility = View.VISIBLE

        //High MultiBrand
        if (has6d6HighMultiBrandValue()) {

            tv_profit_6d6_proyected_title_extended_high_multi_mark.visible()
            tv_profit_6d6_proyected_function_extended_high_multi_mark.visible()
            tv_profit_6d6_proyected_value_percentage_extended_high_multi_mark.visible()

            val firstMemberInFunctionHighMultiMark =
                if (tv_6d6_alto_multi_mark_retener.text.isNullOrBlank()) NUMBER_ZERO else tv_6d6_alto_multi_mark_retener.text.toString()
                    .toInt()
            val secondMemberInFunctionHighMultiMark = getString(
                R.string.cycle_news_profit_proyected_6d6_function,
                currentSymbol,
                campaignProjectionModel.retentionGain6d6HighMultiBrand!!.formatWithNoDecimalThousands()
            )
            val proyectionProfitFunctionHighMultiMark = "$firstMemberInFunctionHighMultiMark * $secondMemberInFunctionHighMultiMark"

            val retention6d6GainHighMultiMark =
                (campaignProjectionModel.retentionGain6d6HighMultiBrand!! * firstMemberInFunctionHighMultiMark).formatWithNoDecimalThousands()
            val percentageCalculationHighMultiMark = getString(
                R.string.cycle_news_profit_proyected_6d6_function,
                currentSymbol,
                retention6d6GainHighMultiMark
            )

            tv_profit_6d6_proyected_function_extended_high_multi_mark.text = proyectionProfitFunctionHighMultiMark
            tv_profit_6d6_proyected_value_percentage_extended_high_multi_mark.text = percentageCalculationHighMultiMark
        } else {
            tv_profit_6d6_proyected_title_extended_high_multi_mark.gone()
            tv_profit_6d6_proyected_function_extended_high_multi_mark.gone()
            tv_profit_6d6_proyected_value_percentage_extended_high_multi_mark.gone()
        }


        //High
        if (has6d6HighValue()) {

            tv_profit_6d6_proyected_title_extended_high.visible()
            tv_profit_6d6_proyected_function_extended_high.visible()
            tv_profit_6d6_proyected_value_percentage_extended_high.visible()

            val firstMemberInFunctionHigh =
                if (tv_6d6_alto_retener.text.isNullOrBlank()) NUMBER_ZERO else tv_6d6_alto_retener.text.toString()
                    .toInt()
            val secondMemberInFunctionHigh = getString(
                R.string.cycle_news_profit_proyected_6d6_function,
                currentSymbol,
                campaignProjectionModel.retentionGain6d6High!!.formatWithNoDecimalThousands()
            )
            val proyectionProfitFunctionHigh =
                "$firstMemberInFunctionHigh * $secondMemberInFunctionHigh"

            val retention6d6GainHigh =
                (campaignProjectionModel.retentionGain6d6High!! * firstMemberInFunctionHigh).formatWithNoDecimalThousands()
            val percentageCalculationHigh = getString(
                R.string.cycle_news_profit_proyected_6d6_function,
                currentSymbol,
                retention6d6GainHigh
            )

            tv_profit_6d6_proyected_function_extended_high.text = proyectionProfitFunctionHigh
            tv_profit_6d6_proyected_value_percentage_extended_high.text =
                percentageCalculationHigh
        } else {

            tv_profit_6d6_proyected_title_extended_high.gone()
            tv_profit_6d6_proyected_function_extended_high.gone()
            tv_profit_6d6_proyected_value_percentage_extended_high.gone()

        }


        //Low
        if (has6d6LowValue()) {

            tv_profit_6d6_proyected_title_extended_low.visible()
            tv_profit_6d6_proyected_function_extended_low.visible()
            tv_profit_6d6_proyected_value_percentage_extended_low.visible()

            val firstMemberInFunctionLow =
                if (tv_6d6_bajo_retener.text.isNullOrBlank()) NUMBER_ZERO else tv_6d6_bajo_retener.text.toString()
                    .toInt()
            val secondMemberInFunctionLow = getString(
                R.string.cycle_news_profit_proyected_6d6_function,
                currentSymbol,
                campaignProjectionModel.retentionGain6d6Low!!.formatWithNoDecimalThousands()
            )
            val proyectionProfitFunctionLow =
                "$firstMemberInFunctionLow * $secondMemberInFunctionLow"

            val retentionGain6d6LowFunction =
                (campaignProjectionModel.retentionGain6d6Low!! * firstMemberInFunctionLow).formatWithNoDecimalThousands()
            val percentageCalculationLow = getString(
                R.string.cycle_news_profit_proyected_6d6_function,
                currentSymbol,
                retentionGain6d6LowFunction
            )

            tv_profit_6d6_proyected_function_extended_low.text = proyectionProfitFunctionLow
            tv_profit_6d6_proyected_value_percentage_extended_low.text =
                percentageCalculationLow
        } else {
            tv_profit_6d6_proyected_title_extended_low.gone()
            tv_profit_6d6_proyected_function_extended_low.gone()
            tv_profit_6d6_proyected_value_percentage_extended_low.gone()
        }

        //6d6
        if (PeBoEstablishedReEntry() || Pais.PANAMA.codigoIso == UserProperties.session?.countryIso) {

            tv_profit_6d6_proyected_title_extended.visible()
            tv_profit_6d6_proyected_function_extended.visible()
            tv_profit_6d6_proyected_value_percentage_extended.visible()

            val firstMemberInFunction =
                if (tv_6d6_retener.text.isNullOrBlank()) NUMBER_ZERO else tv_6d6_retener.text.toString()
                    .toInt()
            val secondMemberInFunction = getString(
                R.string.cycle_news_profit_proyected_6d6_function,
                currentSymbol,
                campaignProjectionModel.retentionGain6d6Low!!.formatWithNoDecimalThousands()
            )
            val proyectionProfitFunction =
                "$firstMemberInFunction * $secondMemberInFunction"

            val retentionGain6d6Function =
                (campaignProjectionModel.retentionGain6d6Low!! * firstMemberInFunction).formatWithNoDecimalThousands()
            val percentageCalculationLow = getString(
                R.string.cycle_news_profit_proyected_6d6_function,
                currentSymbol,
                retentionGain6d6Function
            )

            tv_profit_6d6_proyected_function_extended.text = proyectionProfitFunction
            tv_profit_6d6_proyected_value_percentage_extended.text = percentageCalculationLow
        } else {
            tv_profit_6d6_proyected_title_extended.gone()
            tv_profit_6d6_proyected_function_extended.gone()
            tv_profit_6d6_proyected_value_percentage_extended.gone()
        }


        //6d6 projected
        val firstMemberInFunction =
            if (tv_6d6_retener.text.isNullOrBlank()) NUMBER_ZERO else tv_6d6_retener.text.toString()
                .toInt()

        val retentionGain6d6Function =
            (campaignProjectionModel.retentionGain6d6Low!! * firstMemberInFunction).formatWithNoDecimalThousands()
        val percentageCalculation = getString(
            R.string.cycle_news_profit_proyected_6d6_function,
            currentSymbol,
            retentionGain6d6Function
        )

        val retention6d6 =
            if (tv_6d6_retener.text.isNullOrBlank()) NUMBER_ZERO else tv_6d6_retener.text.toString()
                .toInt()

        val retentionLow =
            if (tv_6d6_bajo_retener.text.isNullOrBlank()) NUMBER_ZERO else tv_6d6_bajo_retener.text.toString()
                .toInt()
        val retentionHigh =
            if (tv_6d6_alto_retener.text.isNullOrBlank()) NUMBER_ZERO else tv_6d6_alto_retener.text.toString()
                .toInt()
        val retentionHighMultiBrand =
            if (tv_6d6_alto_multi_mark_retener.text.isNullOrBlank()) NUMBER_ZERO else tv_6d6_alto_multi_mark_retener.text.toString()
                .toInt()

        val firstMemberInFunctionPercentage = retention6d6 + retentionLow + retentionHigh + retentionHighMultiBrand

        if (campaignProjectionModel.reEntriesLatest5C == 0) {
            tv_6d6_percentage_proyected_function_extended.gone()
            tv_6d6_percentage_proyected_percentage_extended.text = "$NUMBER_ZERO%"
        } else {

            val secondMemberInFunctionPercentage =
                if (campaignProjectionModel.reEntriesLatest5C == 0) NUMBER_ONE else campaignProjectionModel.reEntriesLatest5C

            val proyectionPercentageFunction =
                "( $firstMemberInFunctionPercentage / $secondMemberInFunctionPercentage ) * 100"

            tv_profit_6d6_proyected_value_percentage_extended.text = percentageCalculation

            tv_6d6_percentage_proyected_function_extended.visible()
            tv_6d6_percentage_proyected_function_extended.text = proyectionPercentageFunction

            tv_6d6_percentage_proyected_percentage_extended.text =
                ((firstMemberInFunctionPercentage.toFloat()
                    .div(secondMemberInFunctionPercentage!!.toFloat())) * 100).toInt()
                    .toString() + "%"
        }

        if (!tv_6d6_bajo_retener.text.isNullOrBlank() && tv_6d6_bajo_retener.text.toString()
                .toInt() < tv_6d6_bajo_posibles_consultoras_vivas.text.toString().toInt() ||
            !tv_6d6_alto_retener.text.isNullOrBlank() && tv_6d6_alto_retener.text.toString()
                .toInt() < tv_6d6_alto_posibles_consultoras_vivas.text.toString().toInt() &&
            ((Pais.MEXICO.codigoIso == UserProperties.session?.countryIso && (level != Level.PRE_BRONZE)) ||
                !PeBoEstablishedReEntry() ||
                !MxEstablishedBronzeSilverGoldPlatinumDiamond() ||
                !CoDoClEcCrEstablishedReEntry())
        ) {
            tv_6d6_extended_percentage_proyected_percentage_disclaimer.visibility = View.VISIBLE
        } else {
            tv_6d6_extended_percentage_proyected_percentage_disclaimer.visibility = View.GONE
        }


    }

    private fun showSuccessMessage() {
        if (Pais.MEXICO.codigoIso == UserProperties.session?.countryIso && (level == Level.PRE_BRONZE)) {

            tv_projected_message_mx.visible()

        } else {

            tv_projected_message.visibility = View.VISIBLE
            v_separator_seven.visibility = View.VISIBLE
            val valueInput =
                lowValueInput + lowValuePlusInput + highValueInput + highValuePlusInput
            if (entryGoal.isNotNull() && level.isNotNull()) {
                when (UserProperties.session?.countryIso) {
                    Pais.COLOMBIA.codigoIso -> showSuccessMessageToColombia(valueInput)
                    Pais.PERU.codigoIso -> showSuccessMessageToPeru(valueInput)
                    Pais.MEXICO.codigoIso -> showSuccessMessageToMexico(valueInput)
                    Pais.ECUADOR.codigoIso -> showSuccessMessageToEcuador(valueInput)
                    Pais.CHILE.codigoIso -> showSuccessMessageToChile(valueInput)
                    Pais.BOLIVIA.codigoIso -> showSuccessMessageToBolivia(valueInput)
                    else -> showSuccessMessageAllCountries(valueInput)
                }
                updateOrdersValues()
            } else {
                tv_projected_message.visibility = View.GONE
                v_separator_seven.visibility = View.GONE
            }

        }

    }

    private fun showSuccessMessageAllCountries(valueInput: Int) {

        when (level) {
            Level.PRE_BRONZE,
            Level.DIAMOND,
            Level.PLATINUM -> {
                if (valueInput >= totalOrdersGoal && projectedCapitalization >= capitalizationGoal!!) {
                    tv_projected_message.isSelected = false
                    tv_projected_message.text = getString(R.string.projection_success_message)
                    isDisclaimerSuccess = true
                } else {
                    tv_projected_message.isSelected = true
                    tv_projected_message.text =
                        getString(R.string.projection_not_achieved_message)
                    isDisclaimerSuccess = false
                }
            }

            Level.BRONZE,
            Level.SILVER,
            Level.GOLDEN -> {
                if (isNewClassification) {
                    if (projectedCapitalization >= capitalizationGoal!!) {
                        tv_projected_message.isSelected = false
                        tv_projected_message.text =
                            getString(R.string.projection_success_message)
                        isDisclaimerSuccess = true
                    } else {
                        tv_projected_message.isSelected = true
                        tv_projected_message.text =
                            getString(R.string.projection_not_achieved_message)
                        isDisclaimerSuccess = false
                    }

                } else {
                    if (valueInput >= totalOrdersGoal && (entries >= entryGoal!! || projectedCapitalization >= capitalizationGoal!!)) {
                        tv_projected_message.isSelected = false
                        tv_projected_message.text =
                            getString(R.string.projection_success_message)
                        isDisclaimerSuccess = true
                    } else {
                        tv_projected_message.isSelected = true
                        tv_projected_message.text =
                            getString(R.string.projection_not_achieved_message)
                        isDisclaimerSuccess = false
                    }
                }
            }

        }
    }

    private fun showSuccessMessageToBolivia(valueInput: Int) {

        when (level) {
            Level.PRE_BRONZE,
            Level.PLATINUM,
            Level.DIAMOND -> {
                if (valueInput >= totalOrdersGoal && projectedCapitalization >= capitalizationGoal!!) {
                    tv_projected_message.isSelected = false
                    tv_projected_message.text = getString(R.string.projection_success_message)
                    isDisclaimerSuccess = true
                } else {
                    tv_projected_message.isSelected = true
                    tv_projected_message.text =
                        getString(R.string.projection_not_achieved_message)
                    isDisclaimerSuccess = false
                }
            }

            Level.BRONZE,
            Level.SILVER,
            Level.GOLDEN -> {
                if (isNewClassification) {
                    if (entries >= entryGoal!! && projectedCapitalization >= capitalizationGoal!!) {
                        tv_projected_message.isSelected = false
                        tv_projected_message.text =
                            getString(R.string.projection_success_message)
                        isDisclaimerSuccess = true
                    } else {
                        tv_projected_message.isSelected = true
                        tv_projected_message.text =
                            getString(R.string.projection_not_achieved_message)
                        isDisclaimerSuccess = false
                    }

                } else {
                    if (valueInput >= totalOrdersGoal && (entries >= entryGoal!! || projectedCapitalization >= capitalizationGoal!!)) {
                        tv_projected_message.isSelected = false
                        tv_projected_message.text =
                            getString(R.string.projection_success_message)
                        isDisclaimerSuccess = true
                    } else {
                        tv_projected_message.isSelected = true
                        tv_projected_message.text =
                            getString(R.string.projection_not_achieved_message)
                        isDisclaimerSuccess = false
                    }
                }
            }

        }
    }

    private fun showSuccessMessageToChile(valueInput: Int) {

        when (level) {
            Level.BRONZE,
            Level.SILVER,
            Level.GOLDEN,
            Level.PLATINUM,
            Level.DIAMOND -> {
                if (isNewClassification && classification.equals("Nueva 1")) {

                    if (valueInput >= totalOrdersGoal && entries >= entryGoal!! && projectedCapitalization >= capitalizationGoal!!) {
                        tv_projected_message.isSelected = false
                        tv_projected_message.text =
                            getString(R.string.projection_success_message)
                        isDisclaimerSuccess = true
                    } else {
                        tv_projected_message.isSelected = true
                        tv_projected_message.text =
                            getString(R.string.projection_not_achieved_message)
                        isDisclaimerSuccess = false
                    }
                } else {

                    if (valueInput >= totalOrdersGoal && (entries >= entryGoal!! || projectedCapitalization >= capitalizationGoal!!)) {
                        tv_projected_message.isSelected = false
                        tv_projected_message.text =
                            getString(R.string.projection_success_message)
                        isDisclaimerSuccess = true
                    } else {
                        tv_projected_message.isSelected = true
                        tv_projected_message.text =
                            getString(R.string.projection_not_achieved_message)
                        isDisclaimerSuccess = false
                    }
                }
            }
        }
    }

    private fun showSuccessMessageToEcuador(valueInput: Int) {
        when (level) {
            Level.PRE_BRONZE -> {
                if (!isNewClassification) {

                    if (valueInput >= totalOrdersGoal && projectedCapitalization >= capitalizationGoal!!) {
                        tv_projected_message.isSelected = false
                        tv_projected_message.text =
                            getString(R.string.projection_success_message)
                        isDisclaimerSuccess = true
                    } else {
                        tv_projected_message.isSelected = true
                        tv_projected_message.text =
                            getString(R.string.projection_not_achieved_message)
                        isDisclaimerSuccess = false
                    }

                }
            }


            Level.BRONZE,
            Level.GOLDEN,
            Level.SILVER -> {
                if (isNewClassification) {
                    if (entries >= entryGoal!! && projectedCapitalization >= capitalizationGoal!!) {
                        tv_projected_message.isSelected = false
                        tv_projected_message.text =
                            getString(R.string.projection_success_message)
                        isDisclaimerSuccess = true
                    } else {
                        tv_projected_message.isSelected = true
                        tv_projected_message.text =
                            getString(R.string.projection_not_achieved_message)
                        isDisclaimerSuccess = false
                    }
                } else {
                    if (valueInput >= totalOrdersGoal && (entries >= entryGoal!! && projectedCapitalization >= capitalizationGoal!!)) {
                        tv_projected_message.isSelected = false
                        tv_projected_message.text =
                            getString(R.string.projection_success_message)
                        isDisclaimerSuccess = true
                    } else {
                        tv_projected_message.isSelected = true
                        tv_projected_message.text =
                            getString(R.string.projection_not_achieved_message)
                        isDisclaimerSuccess = false
                    }
                }

            }

            Level.DIAMOND,
            Level.PLATINUM -> {

                if (valueInput >= totalOrdersGoal && (entries >= entryGoal!! && projectedCapitalization >= capitalizationGoal!!)) {
                    tv_projected_message.isSelected = false
                    tv_projected_message.text = getString(R.string.projection_success_message)
                    isDisclaimerSuccess = true
                } else {
                    tv_projected_message.isSelected = true
                    tv_projected_message.text =
                        getString(R.string.projection_not_achieved_message)
                    isDisclaimerSuccess = false
                }
            }

        }
    }

    private fun showSuccessMessageToMexico(valueInput: Int) {
        when (level) {
            Level.PRE_BRONZE -> {
                if (!isNewClassification) {
                    if (valueInput >= totalOrdersGoal && entries >= entryGoal!! && projectedCapitalization >= capitalizationGoal!!) {
                        tv_projected_message_mx.isSelected = false
                        tv_projected_message_mx.text =
                            getString(R.string.projection_success_message)
                        isDisclaimerSuccess = true
                    } else {
                        tv_projected_message_mx.isSelected = true
                        tv_projected_message_mx.text =
                            getString(R.string.projection_not_achieved_message)
                        isDisclaimerSuccess = false
                    }

                }
            }

            Level.BRONZE -> {
                if (isNewClassification) {
                    if (entries >= entryGoal!! && projectedCapitalization >= capitalizationGoal!!) {
                        tv_projected_message.isSelected = false
                        tv_projected_message.text =
                            getString(R.string.projection_success_message)
                        isDisclaimerSuccess = true
                    } else {
                        tv_projected_message.isSelected = true
                        tv_projected_message.text =
                            getString(R.string.projection_not_achieved_message)
                        isDisclaimerSuccess = false
                    }
                } else {

                    if (valueInput >= totalOrdersGoal && entries >= entryGoal!!) {
                        tv_projected_message.isSelected = false
                        tv_projected_message.text =
                            getString(R.string.projection_success_message)
                        isDisclaimerSuccess = true
                    } else {
                        tv_projected_message.isSelected = true
                        tv_projected_message.text =
                            getString(R.string.projection_not_achieved_message)
                        isDisclaimerSuccess = false
                    }

                }
            }

            Level.GOLDEN,
            Level.SILVER,
            Level.DIAMOND,
            Level.PLATINUM -> {
                if (isNewClassification) {
                    if (entries >= entryGoal!! && projectedCapitalization >= capitalizationGoal!!) {
                        tv_projected_message.isSelected = false
                        tv_projected_message.text =
                            getString(R.string.projection_success_message)
                        isDisclaimerSuccess = true
                    } else {
                        tv_projected_message.isSelected = true
                        tv_projected_message.text =
                            getString(R.string.projection_not_achieved_message)
                        isDisclaimerSuccess = false
                    }
                } else {

                    if (valueInput >= totalOrdersGoal && (entries >= entryGoal!! || projectedCapitalization >= capitalizationGoal!!)) {
                        tv_projected_message.isSelected = false
                        tv_projected_message.text =
                            getString(R.string.projection_success_message)
                        isDisclaimerSuccess = true
                    } else {
                        tv_projected_message.isSelected = true
                        tv_projected_message.text =
                            getString(R.string.projection_not_achieved_message)
                        isDisclaimerSuccess = false
                    }
                }


            }
        }
    }

    private fun showSuccessMessageToPeru(valueInput: Int) {
        when (level) {
            Level.BRONZE,
            Level.GOLDEN,
            Level.SILVER -> {
                if (isNewClassification) {
                    if (entries >= entryGoal!! && projectedCapitalization >= capitalizationGoal!!) {
                        tv_projected_message.isSelected = false
                        tv_projected_message.text =
                            getString(R.string.projection_success_message)
                        isDisclaimerSuccess = true
                    } else {
                        tv_projected_message.isSelected = true
                        tv_projected_message.text =
                            getString(R.string.projection_not_achieved_message)
                        isDisclaimerSuccess = false
                    }
                } else {
                    if (valueInput >= totalOrdersGoal && (entries >= entryGoal!! || projectedCapitalization >= capitalizationGoal!!)) {
                        tv_projected_message.isSelected = false
                        tv_projected_message.text =
                            getString(R.string.projection_success_message)
                        isDisclaimerSuccess = true
                    } else {
                        tv_projected_message.isSelected = true
                        tv_projected_message.text =
                            getString(R.string.projection_not_achieved_message)
                        isDisclaimerSuccess = false
                    }

                }
            }

            Level.DIAMOND,
            Level.PLATINUM -> {


                if (valueInput >= totalOrdersGoal && projectedCapitalization >= capitalizationGoal!!) {
                    tv_projected_message.isSelected = false
                    tv_projected_message.text = getString(R.string.projection_success_message)
                    isDisclaimerSuccess = true
                } else {
                    tv_projected_message.isSelected = true
                    tv_projected_message.text =
                        getString(R.string.projection_not_achieved_message)
                    isDisclaimerSuccess = false
                }


            }
        }

    }

    private fun showSuccessMessageToColombia(valueInput: Int) {
        when (level) {
            Level.BRONZE,
            Level.GOLDEN,
            Level.SILVER -> {
                if (isNewClassification) {
                    if (projectedCapitalization >= capitalizationGoal!!) {
                        tv_projected_message.isSelected = false
                        tv_projected_message.text =
                            getString(R.string.projection_success_message)
                        isDisclaimerSuccess = true
                    } else {
                        tv_projected_message.isSelected = true
                        tv_projected_message.text =
                            getString(R.string.projection_not_achieved_message)
                        isDisclaimerSuccess = false
                    }
                } else {

                    if (valueInput >= totalOrdersGoal && (entries >= entryGoal!! || projectedCapitalization >= capitalizationGoal!!)) {
                        tv_projected_message.isSelected = false
                        tv_projected_message.text =
                            getString(R.string.projection_success_message)
                        isDisclaimerSuccess = true
                    } else {
                        tv_projected_message.isSelected = true
                        tv_projected_message.text =
                            getString(R.string.projection_not_achieved_message)
                        isDisclaimerSuccess = false
                    }

                }
            }

            Level.DIAMOND,
            Level.PLATINUM -> {
                if (valueInput >= totalOrdersGoal && (entries >= entryGoal!! || projectedCapitalization >= capitalizationGoal!!)) {
                    tv_projected_message.isSelected = false
                    tv_projected_message.text = getString(R.string.projection_success_message)
                    isDisclaimerSuccess = true
                } else {
                    tv_projected_message.isSelected = true
                    tv_projected_message.text =
                        getString(R.string.projection_not_achieved_message)
                    isDisclaimerSuccess = false
                }


            }
        }
    }

    private fun hideProjectionSuccessMessage() {
        tv_projected_message.visibility = View.GONE
        v_separator_seven.visibility = View.GONE
    }

    private fun showCapitalizationInfo(model: CampaignProjectionModel) {
        entries = model.entriesProjected!!
        reEntries = model.reEntriesProjected!!
        pegsRetention = model.pegsRetentionExpected!!
        pegsInMySection = model.pegsInMySection!!
        initialActives = model.initialActives!!
        projectedCapitalization = model.projectedCapitalization!!

        finalProjectedActives = model.finalProjectedActives!!

        finalsLastYearActives = model.finalsLastYearActives!!

        et_projected_entries_input.setText(
            if (entries > 0) entries.toString() else EMPTY_STRING
        )
        et_projected_reentries_input.setText(
            if (reEntries > 0) reEntries.toString() else EMPTY_STRING
        )
        et_retained_pegs_input.setText(
            if (pegsRetention > 0) pegsRetention.toString() else EMPTY_STRING
        )

        et_section_pegs_input.setText(pegsInMySection.toString())
        et_initial_actives_input.setText(initialActives.toString())

        val projectedExpenses = pegsInMySection.minus(pegsRetention)
        capitalizationCanBeSaved = true
        et_projected_expenses_input.setText(projectedExpenses.toString())

        val projectedCapitalization = entries
            .plus(reEntries).minus(projectedExpenses)
        et_projected_capitalization_input.setText(projectedCapitalization.toString())


        et_final_actives_input.setText(
            finalProjectedActives.toString()
        )

        val retentionPercentage =
            finalProjectedActives.toFloat().div(
                (if (finalsLastYearActives > 0)
                    finalsLastYearActives else NUMBER_ONE)
            )
        tv_active_retention_percentage.text =
            "${retentionPercentage.toHundredPercentage().formatWithDecimalThousands()} %"

        if (Pais.MEXICO.codigoIso == UserProperties.session?.countryIso && (level == Level.PRE_BRONZE)) {

            tv_capitalization.visible()
            tv_capitalization_value.visible()
            tv_earning_capi_point.visible()
            tv_earning_capi_point_value.visible()
            tv_earning_capi.visible()
            tv_earning_capi_value.visible()

            tv_capitalization_value.text = projectedCapitalization.toString()
            val capiPoint = getString(
                R.string.cycle_news_profit_proyected_6d6_function,
                currentSymbol,
                campaignProjectionModel.capiGainByPointProjectionCurrent.toString()
            )
            tv_earning_capi_point_value.text = capiPoint

            if(campaignProjectionModel.isNotNull() && campaignProjectionModel.capiGainByPointProjectionCurrent.isNotNull()
                && projectedCapitalization.isNotNull()) {
                val data = campaignProjectionModel.capiGainByPointProjectionCurrent!!.times(
                    projectedCapitalization
                )
                val earning = getString(
                    R.string.cycle_news_profit_proyected_6d6_function,
                    currentSymbol,
                    data.toString()
                )

                tv_earning_capi_value.text = earning

            }


        }
        setUpCapitalizationInputs()
    }

    private fun showOrdersInfo(model: CampaignProjectionModel) {
        totalOrdersGoal = model.currentTotalOrders!!
        currentSymbol = model.currencySymbol

        tv_current_orders.setText(totalOrdersGoal.toString())

        for (order in model.orderList) {
            when (order.title) {
                Constant.LOW_VALUE_ORDER_TITLE -> lowValueItem = order
                Constant.LOW_VALUE_PLUS_ORDER_TITLE -> lowValuePlusItem = order
                Constant.HIGH_VALUE_ORDER_TITLE -> highValueItem = order
                Constant.HIGH_VALUE_PLUS_ORDER_TITLE -> highValuePlusItem = order
                else -> {
                    //nothing to do
                }
            }
        }

        if (lowValueItem.isNotNull()) {
            lowValueInput = lowValueItem?.unitsExpected!!
            lowValueGain = lowValueItem!!.gainPerOrder!!
            et_orders_low_value_input.setText(
                if (lowValueInput > 0)
                    lowValueInput.toString() else EMPTY_STRING
            )
            tv_low_value_gain_per_order_value.text = lowValueGain.toString()
            tv_low_value_gain_per_order_projected_value.text =
                getString(
                    R.string.total_gain_number,
                    currentSymbol,
                    lowValueItem!!.gainPerOrderExpected!!.toString()
                )
        }

        if (lowValuePlusItem.isNotNull() &&
            (Pais.ECUADOR.codigoIso == UserProperties.session?.countryIso
                || Pais.PERU.codigoIso == UserProperties.session?.countryIso)
        ) {
            ll_low_value_plus_container.visibility = View.VISIBLE
            v_separator_two.visibility = View.VISIBLE
            lowValuePlusInput = lowValuePlusItem?.unitsExpected!!
            lowValuePlusGain = lowValuePlusItem?.gainPerOrder!!
            et_orders_low_value_plus_input.setText(
                if (lowValuePlusInput > 0)
                    lowValuePlusInput.toString() else EMPTY_STRING
            )
            tv_low_value_plus_gain_per_order_value.text = lowValuePlusGain.toString()
            tv_low_value_plus_gain_per_order_projected_value.text =
                getString(
                    R.string.total_gain_number,
                    currentSymbol,
                    lowValuePlusItem!!.gainPerOrderExpected!!.toString()
                )
        } else {
            ll_low_value_plus_container.visibility = View.GONE
            v_separator_two.visibility = View.GONE
        }

        if (highValueItem.isNotNull()) {
            highValueInput = highValueItem?.unitsExpected!!
            highValueGain = highValueItem!!.gainPerOrder!!
            et_orders_high_value_input.setText(
                if (highValueInput > 0)
                    highValueInput.toString() else EMPTY_STRING
            )
            tv_high_value_gain_per_order_value.text = highValueGain.toString()
            tv_high_value_gain_per_order_projected_value.text =
                getString(
                    R.string.total_gain_number,
                    currentSymbol,
                    highValueItem!!.gainPerOrderExpected!!.toString()
                )
        }

        if (highValuePlusItem.isNotNull()) {
            highValuePlusInput = highValuePlusItem?.unitsExpected!!
            highValuePlusGain = highValuePlusItem!!.gainPerOrder!!
            et_orders_high_value_plus_input.setText(
                if (highValuePlusInput > 0)
                    highValuePlusInput.toString() else EMPTY_STRING
            )
            tv_high_value_plus_gain_per_order_value.text = highValuePlusGain.toString()
            tv_high_value_plus_gain_per_order_projected_value.text =
                getString(
                    R.string.total_gain_number,
                    currentSymbol,
                    highValuePlusItem!!.gainPerOrderExpected!!.toString()
                )
        }

        totalOrdersProjected = model.ordersExpectedTotal!!

        if (totalOrdersProjected != null) {
            if (totalOrdersProjected >= totalOrdersGoal) {
                tv_total_orders_goal_label.isSelected = false
                tv_total_orders_goal_label.text = getString(R.string.goal_achieved)
            } else {
                tv_total_orders_goal_label.isSelected = true
                tv_total_orders_goal_label.text = getString(R.string.goal_not_achieved)
            }
        }
        tv_total_orders_number.text = totalOrdersProjected.toString()

        currentSymbol = model.currencySymbol
        tv_total_gain_number.text = getString(
            R.string.total_gain_number,
            currentSymbol,
            model.ordersTotalGain!!.formatWithDecimalThousands()
        )

        val activityProjected =
            totalOrdersProjected.toFloat().div(
                (if (finalProjectedActives > 0)
                    finalProjectedActives else NUMBER_ONE)
            )
        tv_projected_activity_percentage_number.text =
            "${activityProjected.toHundredPercentage().formatWithDecimalThousands()} %"

        tv_projected_pegs_cx_number.text =
            finalProjectedActives.minus(totalOrdersProjected).toString()
        setUpOrdersInputs()
    }

    @SuppressLint("SetTextI18n")
    private fun fillCicloDeNuevas(
        retentionList: List<CampaignProjectionRetentionModel>,
        entriesLatest5C: Int?
    ) {

        val currentCampaignCodeSplitted = Integer.parseInt(
            UserProperties.session?.campaign?.nombreCorto?.subSequence
                (2, UserProperties.session?.campaign?.nombreCorto?.length!!).toString()
        )

        val currentCampaignMinusFive =
            decreaseFiveCampaigns(
                currentCampaignCodeSplitted,
                UserProperties.session?.countryIso!!.equals(Pais.PUERTORICO)
            )

        tv_retencion_ciclo_de_nuevas_ingresos_amount.text =
            "En campaña $currentCampaignMinusFive tuviste $entriesLatest5C ingresos"

        for (retention in retentionList) {
            when (retention.title) {
                "2d2" -> {
                    tv_2d2.text = retention.title
                    tv_2d2_posibles_consultoras_vivas.text =
                        retention.activeConsultant.toString()
                    retentionExpected1 = retention.retentionExpected!!
                    tv_2d2_retener.setText(retention.retentionExpected.toString())

                    if (retention.activeConsultant!! > 0) {
                        tv_percentage_retention_2d2.invisible()
                        tv_percentage_retention_2d2.text =
                            (((Integer.parseInt(tv_2d2_retener.text.toString())) * 100)
                                / retention.activeConsultant!!).toString() + "%"
                    }

                    tv_2d2_retener.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            p0: CharSequence?,
                            p1: Int,
                            p2: Int,
                            p3: Int
                        ) {
                            //Nothing to implement
                        }

                        override fun onTextChanged(
                            p0: CharSequence?,
                            p1: Int,
                            p2: Int,
                            p3: Int
                        ) {
                            if (!p0.toString().isNullOrEmpty()
                                && !p0.toString().isNullOrBlank()
                            ) {
                                if (isInRangeCicloDeNuevas(
                                        retention.activeConsultant!!,
                                        Integer.parseInt(p0.toString())
                                    )
                                ) {
                                    tv_percentage_retention_2d2.invisible()
                                    retentionExpected1 = (((Integer.parseInt(p0.toString()))))
                                    if (retention.activeConsultant!! > 0) {
                                        val retentionPercentageExpected1 =
                                            (((Integer.parseInt(p0.toString())) * 100) /
                                                retention.activeConsultant!!)
                                        tv_percentage_retention_2d2.text =
                                            "$retentionPercentageExpected1%"
                                    }
                                } else {
                                    if (Integer.parseInt(p0.toString()) != 0) {
                                        tv_percentage_retention_2d2.visible()
                                        tv_percentage_retention_2d2.text = "Valor erróneo"
                                    } else {
                                        retentionExpected1 = 0
                                    }
                                }
                            }
                        }

                        override fun afterTextChanged(p0: Editable?) {
                            //Nothing to implement
                        }
                    })
                }

                "3d3" -> {
                    tv_3d3.text = retention.title
                    tv_3d3_posibles_consultoras_vivas.text =
                        retention.activeConsultant.toString()
                    retentionExpected2 = retention.retentionExpected!!
                    tv_3d3_retener.setText(retention.retentionExpected.toString())
                    if (retention.activeConsultant!! > 0) {
                        tv_percentage_retention_3d3.invisible()
                        tv_percentage_retention_3d3.text =
                            (((Integer.parseInt(tv_3d3_retener.text.toString())) * 100) /
                                retention.activeConsultant!!).toString() + "%"
                    }
                    tv_3d3_retener.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            p0: CharSequence?,
                            p1: Int,
                            p2: Int,
                            p3: Int
                        ) {
                            //Nothing to implement
                        }

                        override fun onTextChanged(
                            p0: CharSequence?,
                            p1: Int,
                            p2: Int,
                            p3: Int
                        ) {
                            if (!p0.toString().isNullOrEmpty()
                                && !p0.toString().isNullOrBlank()
                            ) {

                                if (isInRangeCicloDeNuevas(
                                        retention.activeConsultant!!,
                                        Integer.parseInt(p0.toString())
                                    )
                                ) {
                                    tv_percentage_retention_3d3.invisible()
                                    retentionExpected2 = (Integer.parseInt(p0.toString()))
                                    if (retention.activeConsultant!! > 0) {
                                        val retentionPercentageExpected2 =
                                            (((Integer.parseInt(p0.toString())) * 100)
                                                / retention.activeConsultant!!)
                                        tv_percentage_retention_3d3.text =
                                            "$retentionPercentageExpected2%"
                                    }
                                } else {
                                    if (Integer.parseInt(p0.toString()) != 0) {
                                        tv_percentage_retention_3d3.visible()
                                        tv_percentage_retention_3d3.text = "Valor erróneo"
                                    } else {
                                        retentionExpected2 = 0
                                    }
                                }

                            }

                        }

                        override fun afterTextChanged(p0: Editable?) {
                            //Nothing to implement
                        }
                    })
                }

                "4d4" -> {
                    tv_4d4.text = retention.title
                    tv_4d4_posibles_consultoras_vivas.text =
                        retention.activeConsultant.toString()
                    retentionExpected3 = retention.retentionExpected!!
                    tv_4d4_retener.setText(retention.retentionExpected.toString())
                    if (retention.activeConsultant!! > 0) {
                        tv_percentage_retention_4d4.invisible()
                        tv_percentage_retention_4d4.text =
                            (((Integer.parseInt(tv_4d4_retener.text.toString())) * 100) /
                                retention.activeConsultant!!).toString() + "%"
                    }
                    tv_4d4_retener.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            p0: CharSequence?,
                            p1: Int,
                            p2: Int,
                            p3: Int
                        ) {
                            //Nothing to implement
                        }

                        override fun onTextChanged(
                            p0: CharSequence?,
                            p1: Int,
                            p2: Int,
                            p3: Int
                        ) {
                            if (!p0.toString().isNullOrEmpty()
                                && !p0.toString().isNullOrBlank()
                            ) {

                                if (isInRangeCicloDeNuevas(
                                        retention.activeConsultant!!,
                                        Integer.parseInt(p0.toString())
                                    )
                                ) {
                                    tv_percentage_retention_4d4.invisible()
                                    retentionExpected3 = (Integer.parseInt(p0.toString()))
                                    if (retention.activeConsultant!! > 0) {
                                        val retentionExpectedPercentage3 =
                                            (((Integer.parseInt(p0.toString())) * 100)
                                                / retention.activeConsultant!!)
                                        tv_percentage_retention_4d4.text =
                                            retentionExpectedPercentage3.toString() + "%"
                                    }
                                } else {
                                    if (Integer.parseInt(p0.toString()) != 0) {
                                        tv_percentage_retention_4d4.visible()
                                        tv_percentage_retention_4d4.text = "Valor erróneo"
                                    } else {
                                        retentionExpected3 = 0
                                    }
                                }
                            }

                        }

                        override fun afterTextChanged(p0: Editable?) {
                            //Nothing to implement
                        }
                    })
                }

                "5d5" -> {
                    tv_5d5.text = retention.title
                    tv_5d5_posibles_consultoras_vivas.text =
                        retention.activeConsultant.toString()
                    retentionExpected4 = retention.retentionExpected!!
                    tv_5d5_retener.setText(retention.retentionExpected.toString())
                    if (retention.activeConsultant!! > 0) {
                        tv_percentage_retention_5d5.invisible()
                        tv_percentage_retention_5d5.text =
                            (((Integer.parseInt(tv_5d5_retener.text.toString())) * 100) /
                                retention.activeConsultant!!).toString() + "%"
                    }
                    tv_5d5_retener.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            p0: CharSequence?,
                            p1: Int,
                            p2: Int,
                            p3: Int
                        ) {
                            //Nothing to implement
                        }

                        override fun onTextChanged(
                            p0: CharSequence?,
                            p1: Int,
                            p2: Int,
                            p3: Int
                        ) {
                            if (!p0.toString().isNullOrEmpty()
                                && !p0.toString().isNullOrBlank()
                            ) {

                                if (isInRangeCicloDeNuevas(
                                        retention.activeConsultant!!,
                                        Integer.parseInt(p0.toString())
                                    )
                                ) {
                                    tv_percentage_retention_5d5.invisible()
                                    retentionExpected4 = (Integer.parseInt(p0.toString()))
                                    if (retention.activeConsultant!! > 0) {
                                        val retentionPercentageExpected4 =
                                            (((Integer.parseInt(p0.toString())) * 100)
                                                / retention.activeConsultant!!)
                                        tv_percentage_retention_5d5.text =
                                            "$retentionPercentageExpected4%"
                                    }
                                } else {
                                    if (Integer.parseInt(p0.toString()) != 0) {
                                        tv_percentage_retention_5d5.visible()
                                        tv_percentage_retention_5d5.text = "Valor erróneo"
                                    } else {
                                        retentionExpected4 = 0
                                    }
                                }
                            }

                        }

                        override fun afterTextChanged(p0: Editable?) {
                            //Nothing to implement
                        }
                    })
                }

                "6d6" -> {
                    if (has6d6()) {
                        cl_ciclo_6d6_container.visibility = View.VISIBLE
                        tv_6d6.text = retention.title
                        tv_6d6_posibles_consultoras_vivas.text =
                            retention.activeConsultant.toString()
                        retentionExpected5 = retention.retentionExpected!!
                        tv_6d6_retener.setText(retention.retentionExpected.toString())
                        if (retention.activeConsultant!! > 0) {
                            tv_6d6_retener.setText(retention.retentionExpected.toString())
                            if (retention.activeConsultant!! > 0) {
                                tv_percentage_retention_6d6.invisible()
                                tv_percentage_retention_6d6.text =
                                    (((Integer.parseInt(tv_6d6_retener.text.toString())) * 100) /
                                        retention.activeConsultant!!).toString() + "%"
                            }
                        }
                        tv_6d6_retener.addTextChangedListener(object : TextWatcher {
                            override fun beforeTextChanged(
                                p0: CharSequence?,
                                p1: Int,
                                p2: Int,
                                p3: Int
                            ) {
                                //Nothing to implement
                            }

                            override fun onTextChanged(
                                p0: CharSequence?,
                                p1: Int,
                                p2: Int,
                                p3: Int
                            ) {
                                if (!p0.toString().isNullOrEmpty()
                                    && !p0.toString().isNullOrBlank()
                                ) {

                                    if (isInRangeCicloDeNuevas(
                                            retention.activeConsultant!!,
                                            Integer.parseInt(p0.toString())
                                        )
                                    ) {
                                        tv_percentage_retention_6d6.invisible()
                                        retentionExpected5 = (Integer.parseInt(p0.toString()))
                                        val retentionPercentageExpected5 =
                                            (((Integer.parseInt(p0.toString())) * 100)
                                                / retention.activeConsultant!!)
                                        tv_percentage_retention_6d6.text =
                                            "$retentionPercentageExpected5%"

                                        showRetentionPercentage6d6Message()

                                    } else {
                                        if (Integer.parseInt(p0.toString()) != 0) {
                                            tv_percentage_retention_6d6.visible()
                                            tv_percentage_retention_6d6.text = "Valor erróneo"
                                        } else {
                                            retentionExpected5 = 0
                                        }
                                    }
                                }

                            }

                            override fun afterTextChanged(p0: Editable?) {
                                //Nothing to implement
                            }
                        })
                    } else {
                        cl_ciclo_6d6_container.visibility = View.GONE
                    }
                }

                "6d6 Bajo Valor" -> {
                    if (has6d6LowValue()) {
                        cl_ciclo_6d6_bajo_container.visibility = View.VISIBLE
                        v_6d6_bajo_divider.visibility = View.VISIBLE
                        tv_6d6_bajo.text = retention.title
                        tv_6d6_bajo_posibles_consultoras_vivas.text =
                            retention.activeConsultant.toString()
                        retentionExpected6 = retention.retentionExpected!!
                        tv_6d6_bajo_retener.setText(retention.retentionExpected.toString())
                        if (retention.activeConsultant!! > 0) {
                            tv_percentage_retention_bajo_6d6.invisible()
                            tv_percentage_retention_bajo_6d6.text =
                                (((Integer.parseInt(tv_6d6_bajo_retener.text.toString())) * 100)
                                    / retention.activeConsultant!!).toString() + "%"
                        }
                        tv_6d6_bajo_retener.addTextChangedListener(object : TextWatcher {
                            override fun beforeTextChanged(
                                p0: CharSequence?,
                                p1: Int,
                                p2: Int,
                                p3: Int
                            ) {
                                //Nothing to implement
                            }

                            override fun onTextChanged(
                                p0: CharSequence?,
                                p1: Int,
                                p2: Int,
                                p3: Int
                            ) {
                                if (!p0.toString().isNullOrEmpty()
                                    && !p0.toString().isNullOrBlank()
                                ) {

                                    if (isInRangeCicloDeNuevas(
                                            retention.activeConsultant!!,
                                            Integer.parseInt(p0.toString())
                                        )
                                    ) {
                                        tv_percentage_retention_bajo_6d6.invisible()
                                        retentionExpected6 = (Integer.parseInt(p0.toString()))
                                        if (retention.activeConsultant!! > 0) {
                                            val retentionPercentageExpected6 =
                                                (((Integer.parseInt(p0.toString())) * 100) /
                                                    retention.activeConsultant!!)
                                            tv_percentage_retention_bajo_6d6.text =
                                                "$retentionPercentageExpected6%"

                                            showRetentionPercentage6d6Message()
                                        }
                                    } else {
                                        if (Integer.parseInt(p0.toString()) != 0) {
                                            tv_percentage_retention_bajo_6d6.visible()
                                            tv_percentage_retention_bajo_6d6.text = "Valor erróneo"
                                        } else {
                                            retentionExpected6 = 0
                                        }
                                    }

                                }
                            }

                            override fun afterTextChanged(p0: Editable?) {
                                //Nothing to implement
                            }
                        })
                    } else {
                        cl_ciclo_6d6_bajo_container.visibility = View.GONE
                        v_6d6_bajo_divider.visibility = View.GONE
                    }
                }

                "6d6 Alto Valor" -> {
                    if (has6d6HighValue()) {
                        cl_ciclo_6d6_alto_container.visibility = View.VISIBLE
                        tv_6d6_alto.text = retention.title
                        tv_6d6_alto_posibles_consultoras_vivas.text =
                            retention.activeConsultant.toString()
                        retentionExpected7 = retention.retentionExpected!!
                        tv_6d6_alto_retener.setText(retention.retentionExpected.toString())
                        if (retention.activeConsultant!! > 0) {
                            tv_percentage_retention_6d6_alto.invisible()
                            tv_percentage_retention_6d6_alto.text =
                                (((Integer.parseInt(tv_6d6_alto_retener.text.toString())) * 100) /
                                    retention.activeConsultant!!).toString() + "%"
                        }
                        tv_6d6_alto_retener.addTextChangedListener(object : TextWatcher {
                            override fun beforeTextChanged(
                                p0: CharSequence?,
                                p1: Int,
                                p2: Int,
                                p3: Int
                            ) {
                                //Nothing to implement
                            }

                            override fun onTextChanged(
                                p0: CharSequence?,
                                p1: Int,
                                p2: Int,
                                p3: Int
                            ) {
                                if (!p0.toString().isNullOrEmpty()
                                    && !p0.toString().isNullOrBlank()
                                ) {

                                    if (isInRangeCicloDeNuevas(
                                            retention.activeConsultant!!,
                                            Integer.parseInt(p0.toString())
                                        )
                                    ) {
                                        tv_percentage_retention_6d6_alto.invisible()
                                        retentionExpected7 = (Integer.parseInt(p0.toString()))
                                        if (retention.activeConsultant!! > 0) {
                                            val retentionPercentageExpected7 =
                                                (((Integer.parseInt(p0.toString())) * 100) /
                                                    retention.activeConsultant!!)
                                            tv_percentage_retention_6d6_alto.text =
                                                "$retentionPercentageExpected7%"

                                            showRetentionPercentage6d6Message()

                                        }
                                    } else {
                                        if (Integer.parseInt(p0.toString()) != 0) {
                                            tv_percentage_retention_6d6_alto.visible()
                                            tv_percentage_retention_6d6_alto.text = "Valor erróneo"
                                        } else {
                                            retentionExpected7 = 0
                                        }
                                    }

                                }
                            }

                            override fun afterTextChanged(p0: Editable?) {
                                //Nothing to implement
                            }
                        })
                    } else {
                        cl_ciclo_6d6_alto_container.visibility = View.GONE
                    }
                }

                "6d6 Alto Valor Multimarca" -> {
                    if (has6d6HighMultiBrandValue()) {
                        cl_ciclo_6d6_alto_multi_mark_container.visibility = View.VISIBLE
                        v_6d6_alto_divider_multi_mark.visibility = View.VISIBLE
                        tv_6d6_alto_multi_mark.text = getString(R.string.sixdsix_multi_brand)
                        tv_6d6_alto_posibles_multi_mark_consultoras_vivas.text =
                            retention.activeConsultant.toString()
                        retentionExpected8 = retention.retentionExpected!!
                        tv_6d6_alto_multi_mark_retener.setText(retention.retentionExpected.toString())
                        if (retention.activeConsultant!! > 0) {
                            tv_percentage_retention_6d6_alto_multi_mark.text =
                                (((Integer.parseInt(tv_6d6_alto_multi_mark_retener.text.toString())) * 100) /
                                    retention.activeConsultant!!).toString() + "%"
                        }
                        tv_6d6_alto_multi_mark_retener.addTextChangedListener(object : TextWatcher {
                            override fun beforeTextChanged(
                                p0: CharSequence?,
                                p1: Int,
                                p2: Int,
                                p3: Int
                            ) {
                                //Nothing to implement
                            }

                            override fun onTextChanged(
                                p0: CharSequence?,
                                p1: Int,
                                p2: Int,
                                p3: Int
                            ) {
                                if (!p0.toString().isNullOrEmpty()
                                    && !p0.toString().isNullOrBlank()
                                ) {

                                    if (isInRangeCicloDeNuevas(
                                            retention.activeConsultant!!,
                                            Integer.parseInt(p0.toString())
                                        )
                                    ) {
                                        tv_percentage_retention_6d6_alto_multi_mark.invisible()
                                        retentionExpected8 = (Integer.parseInt(p0.toString()))
                                        if (retention.activeConsultant!! > 0) {
                                            val retentionPercentageExpected7 =
                                                (((Integer.parseInt(p0.toString())) * 100) /
                                                    retention.activeConsultant!!)
                                            tv_percentage_retention_6d6_alto_multi_mark.text =
                                                "$retentionPercentageExpected7%"

                                            showRetentionPercentage6d6Message()

                                        }
                                    } else {
                                        if (Integer.parseInt(p0.toString()) != 0) {
                                            tv_percentage_retention_6d6_alto_multi_mark.visible()
                                            tv_percentage_retention_6d6_alto_multi_mark.text = "Valor erróneo"
                                        } else {
                                            retentionExpected8 = 0
                                        }
                                    }

                                }
                            }

                            override fun afterTextChanged(p0: Editable?) {
                                //Nothing to implement
                            }
                        })
                    } else {
                        cl_ciclo_6d6_alto_multi_mark_container.visibility = View.GONE
                        v_6d6_alto_divider_multi_mark.visibility = View.GONE
                    }
                }
            }
        }

    }


    private fun has6d6(): Boolean =
        (Pais.MEXICO.codigoIso == UserProperties.session?.countryIso && (level == Level.PRE_BRONZE)) || PeBoEstablishedReEntry() ||
            Pais.PANAMA.codigoIso == UserProperties.session?.countryIso

    private fun has6d6LowValue(): Boolean = CoDoClEcCrEstablishedReEntry() || MxEstablishedBronzeSilverGoldPlatinumDiamond() ||
        Pais.GUATEMALA.codigoIso == UserProperties.session?.countryIso ||
        Pais.SALVADOR.codigoIso == UserProperties.session?.countryIso

    private fun has6d6HighValue(): Boolean = CoDoClEcCrEstablishedReEntry() || MxEstablishedBronzeSilverGoldPlatinumDiamond() ||
        Pais.GUATEMALA.codigoIso == UserProperties.session?.countryIso ||
        Pais.SALVADOR.codigoIso == UserProperties.session?.countryIso

    private fun has6d6HighMultiBrandValue(): Boolean = CoDoClEcCrEstablishedReEntry() || PeBoEstablishedReEntry()

    private fun CoDoClEcCrEstablishedReEntry(): Boolean =
        (Pais.COLOMBIA.codigoIso == UserProperties.session?.countryIso ||
            Pais.DOMINICANA.codigoIso == UserProperties.session?.countryIso ||
            Pais.CHILE.codigoIso == UserProperties.session?.countryIso ||
            Pais.COSTARICA.codigoIso == UserProperties.session?.countryIso ||
            Pais.ECUADOR.codigoIso == UserProperties.session?.countryIso)

    private fun PeBoEstablishedReEntry(): Boolean =
        (Pais.PERU.codigoIso == UserProperties.session?.countryIso ||
            Pais.BOLIVIA.codigoIso == UserProperties.session?.countryIso )

    private fun MxEstablishedBronzeSilverGoldPlatinumDiamond(): Boolean =
        (Pais.MEXICO.codigoIso == UserProperties.session?.countryIso) && (classification == CLASSIFICATION_ESTABLECIDA) &&
            (level == Level.BRONZE ||
                level == Level.SILVER ||
                level == Level.GOLDEN ||
                level == Level.PLATINUM ||
                level == Level.DIAMOND)

    private fun isInRangeCicloDeNuevas(maximunValue: Int, input: Int): Boolean {
        return (input in 1..maximunValue)
    }

    private fun setUpSaveButton() {
        if (biz.belcorp.salesforce.core.constants.Constant.PHASE_SALE ==
            UserProperties.session?.campaign?.getPhase()
        ) {
            btn_save_projection.visibility = View.VISIBLE
            btn_save_projection.setOnClickListener {
                if (capitalizationCanBeSaved) {
                    campaignProjectionViewModel.saveCampaignProjectionInfo(
                        CampaignProjectionModel(
                            region = campaignProjectionModel.region,
                            zone = campaignProjectionModel.zone,
                            section = campaignProjectionModel.section,
                            pegsInMySection = pegsInMySection,
                            pegsRetentionExpected = pegsRetention,
                            pegsRetentionReal = campaignProjectionModel.pegsRetentionReal,
                            initialActives = initialActives,
                            finalProjectedActives = finalProjectedActives,
                            projectedCapitalization = projectedCapitalization,
                            entriesProjected = entries,
                            entriesReal = campaignProjectionModel.entriesReal,
                            reEntriesProjected = reEntries,
                            reEntriesReal = campaignProjectionModel.reEntriesReal,
                            finalsLastYearActives = finalsLastYearActives,
                            ordersExpectedTotal = totalOrdersProjected,
                            ordersTotalGain = totalGain.toFloat(),
                            currentTotalOrders = totalOrdersGoal,
                            orderList = createOrderList(),
                            retentionList = createRetentionList(),
                            retentionGain6d6HighMultiBrand = campaignProjectionModel.retentionGain6d6HighMultiBrand ?: ZERO_FLOAT,
                            retentionGain6d6High = campaignProjectionModel.retentionGain6d6High,
                            retentionGain6d6Low = campaignProjectionModel.retentionGain6d6Low,
                            reEntriesLatest5C = campaignProjectionModel.reEntriesLatest5C,
                            capiGainByPointProjectionCurrent = campaignProjectionModel.capiGainByPointProjectionCurrent,
                            capiGainByPointProjectionReal = campaignProjectionModel.capiGainByPointProjectionReal,
                            capitalizationCurrent = projectedCapitalization,
                            capitalizationReal = campaignProjectionModel.capitalizationReal,
                            gainCurrent = projectedCapitalization
                                * campaignProjectionModel.capiGainByPointProjectionCurrent!!,
                            gainReal = campaignProjectionModel.capiGainByPointProjectionReal,
                        ),
                        arguments?.getString(SECTION_PARTNER)
                    )
                }
            }
        } else {
            btn_save_projection.visibility = View.GONE
        }
    }

    private fun createOrderList(): List<OrderModel> {
        val list: List<OrderModel>
        if (lowValuePlusItem.isNotNull()) {
            list = listOf(
                lowValueItem.apply {
                    this?.unitsExpected = lowValueInput
                    this?.gainPerOrder = lowValueItem!!.gainPerOrder
                    this?.gainPerOrderNotSuccess = lowValueItem!!.gainPerOrderNotSuccess
                    this?.gainPerOrderExpected = lowValueGainProjected
                },
                lowValuePlusItem.apply {
                    this?.unitsExpected = lowValuePlusInput
                    this?.gainPerOrder = lowValuePlusItem!!.gainPerOrder
                    this?.gainPerOrderNotSuccess = lowValuePlusItem!!.gainPerOrderNotSuccess
                    this?.gainPerOrderExpected = lowValuePlusGainProjected
                },
                highValueItem.apply {
                    this?.unitsExpected = highValueInput
                    this?.gainPerOrder = highValueItem!!.gainPerOrder
                    this?.gainPerOrderNotSuccess = highValueItem!!.gainPerOrderNotSuccess
                    this?.gainPerOrderExpected = highValueGainProjected
                },
                highValuePlusItem.apply {
                    this?.unitsExpected = highValuePlusInput
                    this?.gainPerOrder = highValuePlusItem!!.gainPerOrder
                    this?.gainPerOrderNotSuccess = highValuePlusItem!!.gainPerOrderNotSuccess
                    this?.gainPerOrderExpected = highValuePlusGainProjected
                }) as List<OrderModel>
        } else {
            list = listOf(
                lowValueItem.apply {
                    this?.unitsExpected = lowValueInput
                    this?.gainPerOrder = lowValueItem!!.gainPerOrder
                    this?.gainPerOrderNotSuccess = lowValueItem!!.gainPerOrderNotSuccess
                    this?.gainPerOrderExpected = lowValueGainProjected
                },
                highValueItem.apply {
                    this?.unitsExpected = highValueInput
                    this?.gainPerOrder = highValueItem!!.gainPerOrder
                    this?.gainPerOrderNotSuccess = highValueItem!!.gainPerOrderNotSuccess
                    this?.gainPerOrderExpected = highValueGainProjected
                },
                highValuePlusItem.apply {
                    this?.unitsExpected = highValuePlusInput
                    this?.gainPerOrder = highValuePlusItem!!.gainPerOrder
                    this?.gainPerOrderNotSuccess = highValuePlusItem!!.gainPerOrderNotSuccess
                    this?.gainPerOrderExpected = highValuePlusGainProjected
                },
            ) as List<OrderModel>
        }
        return list
    }

    private fun createRetentionList(): List<CampaignProjectionRetentionModel> {
        for (retention in retentionList) {
            when (retention.title) {
                "2d2" -> {
                    retention.retentionExpected = retentionExpected1
                }

                "3d3" -> {
                    retention.retentionExpected = retentionExpected2

                }

                "4d4" -> {
                    retention.retentionExpected = retentionExpected3

                }

                "5d5" -> {
                    retention.retentionExpected = retentionExpected4

                }

                "6d6" -> {
                    retention.retentionExpected = retentionExpected5
                }

                "6d6 Bajo Valor" -> {
                    retention.retentionExpected = retentionExpected6
                }

                "6d6 Alto Valor" -> {
                    retention.retentionExpected = retentionExpected7

                }

                "6d6 Alto Valor Multimarca" -> {
                    retention.retentionExpected = retentionExpected8
                }
            }
        }
        return retentionList
    }

    override fun getLayout(): Int = R.layout.fragment_campaign_projection

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    private fun showError() {
        setUpCapitalizationInputs()
        setUpOrdersInputs()
        toast(R.string.campaign_projection_fetch_error)
    }

    companion object {
        @JvmStatic
        fun newInstance(partnerSection: String?): CampaignProjectionFragment {
            val fragment = CampaignProjectionFragment()
            val bundle = bundleOf(SECTION_PARTNER to partnerSection)
            fragment.arguments = bundle
            return fragment
        }
    }
}

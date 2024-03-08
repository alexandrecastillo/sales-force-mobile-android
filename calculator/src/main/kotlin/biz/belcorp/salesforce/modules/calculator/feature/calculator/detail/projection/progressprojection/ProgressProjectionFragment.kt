package biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.progressprojection

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Level
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.calculator.R
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.campaignprojection.CLASSIFICATION_ESTABLECIDA
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.campaignprojection.CLASSIFICATION_REINGRESO
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.CampaignProjectionModel
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.CampaignProjectionRetentionModel
import biz.belcorp.salesforce.modules.calculator.feature.calculator.detail.projection.model.OrderModel
import kotlinx.android.synthetic.main.fragment_campaign_projection.*
import kotlinx.android.synthetic.main.fragment_progress_projection.cl_ciclo_6d6_alto_container
import kotlinx.android.synthetic.main.fragment_progress_projection.cl_ciclo_6d6_alto_multi_brand_container
import kotlinx.android.synthetic.main.fragment_progress_projection.cl_ciclo_6d6_bajo_container
import kotlinx.android.synthetic.main.fragment_progress_projection.cl_ciclo_6d6_container
import kotlinx.android.synthetic.main.fragment_progress_projection.divider_6d6
import kotlinx.android.synthetic.main.fragment_progress_projection.divider_6d6_low
import kotlinx.android.synthetic.main.fragment_progress_projection.et_current_orders
import kotlinx.android.synthetic.main.fragment_progress_projection.et_final_actives_input
import kotlinx.android.synthetic.main.fragment_progress_projection.et_initial_actives_input
import kotlinx.android.synthetic.main.fragment_progress_projection.et_projected_capitalization_input
import kotlinx.android.synthetic.main.fragment_progress_projection.et_projected_expenses_input
import kotlinx.android.synthetic.main.fragment_progress_projection.et_section_pegs_input
import kotlinx.android.synthetic.main.fragment_progress_projection.high_low_divider
import kotlinx.android.synthetic.main.fragment_progress_projection.high_low_multi_brand_divider
import kotlinx.android.synthetic.main.fragment_progress_projection.ll_low_value_plus_container
import kotlinx.android.synthetic.main.fragment_progress_projection.ll_low_value_plus_input_container
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_2d2
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_2d2_posibles_consultoras_vivas
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_2d2_projected
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_2d2_real
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_3d3
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_3d3_posibles_consultoras_vivas
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_3d3_projected
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_3d3_real
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_4d4
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_4d4_posibles_consultoras_vivas
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_4d4_projected
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_4d4_real
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_5d5
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_5d5_posibles_consultoras_vivas
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_5d5_projected
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_5d5_real
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_6d6
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_6d6_alto
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_6d6_alto_multi_brand
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_6d6_alto_posibles_consultoras_vivas
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_6d6_alto_posibles_consultoras_vivas_multi_brand
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_6d6_alto_projected
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_6d6_alto_projected_multi_brand
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_6d6_alto_real
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_6d6_alto_real_multi_brand
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_6d6_bajo
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_6d6_bajo_posibles_consultoras_vivas
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_6d6_bajo_projected
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_6d6_bajo_real
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_6d6_posibles_consultoras_vivas
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_6d6_projected
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_6d6_real
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_active_retention_percentage
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_entries_projected
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_entries_real
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_high_value_gain_per_order_projected_value
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_high_value_gain_per_order_value
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_high_value_plus_gain_per_order_projected_value
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_high_value_plus_gain_per_order_value
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_high_value_plus_projected
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_high_value_plus_real
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_high_value_projected
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_high_value_real
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_low_value_gain_per_order_projected_value
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_low_value_gain_per_order_value
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_low_value_plus_gain_per_order_projected_value
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_low_value_plus_gain_per_order_value
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_low_value_plus_projected
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_low_value_plus_real
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_low_value_projected
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_low_value_real
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_percentage_retention_2d2
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_percentage_retention_3d3
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_percentage_retention_4d4
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_percentage_retention_5d5
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_percentage_retention_6d6
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_percentage_retention_6d6_alto
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_percentage_retention_6d6_alto_multi_brand
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_percentage_retention_6d6_bajo
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_projected_activity_percentage_number
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_projected_message
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_projected_pegs_cx_number
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_reentries_projected
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_reentries_real
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_retained_pegs_projected
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_retained_pegs_real
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_retencion_ciclo_de_nuevas_ingresos_amount
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_total_gain_number
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_total_orders_goal_achieved_label
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_total_orders_goal_not_achieved_label
import kotlinx.android.synthetic.main.fragment_progress_projection.tv_total_orders_number
import kotlinx.android.synthetic.main.fragment_progress_projection.v_separator_seven
import kotlinx.android.synthetic.main.fragment_progress_projection.v_separator_two
import org.koin.android.viewmodel.ext.android.viewModel


class ProgressProjectionFragment : BaseFragment() {

    private val progressProjectionViewModel by viewModel<ProgressProjectionViewModel>()

    //Capitalization
    private var entries = 0
    private var projectedCapitalization = 0
    private var finalProjectedActives = 0

    //Orders
    private var lowValueRealInput = 0
    private var lowValuePlusRealInput = 0
    private var highValueRealInput = 0
    private var highValuePlusRealInput = 0
    private var totalOrdersGoal = 0
    private var realOrderTotalGoal = 0
    private var lowValueItem: OrderModel? = null
    private var lowValuePlusItem: OrderModel? = null
    private var highValueItem: OrderModel? = null
    private var highValuePlusItem: OrderModel? = null

    //Status SE
    private var isNewClassification = false
    private var classification: String? = null
    private var level: String? = null
    private var entryGoal: Int? = null
    private var capitalizationGoal: Int? = null
    private var isDisclaimerSuccess = false


    private lateinit var campaignProjectionModel: CampaignProjectionModel


    private var currentSymbol: String? = null
    private var reEntriesLatest5C: Int? = null

    override fun onResume() {
        super.onResume()
        if (!arguments?.getString(Constant.SECTION_PARTNER).isNullOrBlank()) {
            setupViewModelForPartner()
        } else {
            setupViewModels()
        }

    }

    private fun setupViewModelForPartner() {
        progressProjectionViewModel.viewSyncPartnerState.observe(viewLifecycleOwner, Observer {
            setupViewModels()
        })
        progressProjectionViewModel.fetchPartnerCampaignProjectionInfo(arguments?.getString(Constant.SECTION_PARTNER)!!)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    private fun setupViewModels() {
        progressProjectionViewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        progressProjectionViewModel.fetchCampaignProjectionInfo(arguments?.getString(Constant.SECTION_PARTNER))
        progressProjectionViewModel.viewStatusSEState.observe(
            viewLifecycleOwner,
            viewStatusSEStateObserver
        )
        progressProjectionViewModel.viewResultsLastCampaignState.observe(
            viewLifecycleOwner,
            viewResultsLastCampaignStateObserver
        )
    }

    private val viewStateObserver = Observer<ProgressProjectionViewState> {
        when (it) {
            is ProgressProjectionViewState.Success -> {
                if (it.campaignProjectionModel.pegsInMySection.isNotNull()) {
                    showProgressCampaignInfo(it.campaignProjectionModel)
                }
            }

            is ProgressProjectionViewState.Failed -> showError()
        }
    }

    private val viewStatusSEStateObserver = Observer<ProgressStatusSEViewState> {
        when (it) {
            is ProgressStatusSEViewState.Success -> {
                isNewClassification =
                    biz.belcorp.salesforce.modules.calculator.util.Constant.NEW == it.SaleForceStatus.productivity
                classification = it.SaleForceStatus.classification
                level = it.SaleForceStatus.level
                showSuccessMessage()
                if (campaignProjectionModel.isNotNull()) {
                    fillCicloDeNuevas(campaignProjectionModel.retentionList, reEntriesLatest5C)
                    showRetentionPercentage6d6Message()
                }
            }

            is ProgressStatusSEViewState.Failed -> hideProjectionSuccessMessage()
        }
    }

    private val viewResultsLastCampaignStateObserver =
        Observer<ProgressResultsLastCampaignViewState> {
            when (it) {
                is ProgressResultsLastCampaignViewState.Success -> {
                    entryGoal = it.model.result?.entriesGoal
                    capitalizationGoal = it.model.result?.capitalizationGoal
                    showSuccessMessage()
                }

                is ProgressResultsLastCampaignViewState.Failure -> hideProjectionSuccessMessage()
            }
        }

    private fun showSuccessMessage() {
        tv_projected_message.visibility = View.VISIBLE
        v_separator_seven.visibility = View.VISIBLE
        val valueInput =
            lowValueRealInput + lowValuePlusRealInput + highValueRealInput + highValuePlusRealInput
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

    private fun showSuccessMessageAllCountries(valueInput: Int) {

        when (level) {
            Level.PRE_BRONZE,
            Level.DIAMOND,
            Level.PLATINUM -> {
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


    private fun showSuccessMessageToMexico(valueInput: Int) {
        when (level) {
            Level.PRE_BRONZE -> {
                if (!isNewClassification) {
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

    private fun hideProjectionSuccessMessage() {
        tv_projected_message.visibility = View.GONE
    }

    private fun showProgressCampaignInfo(model: CampaignProjectionModel) {
        campaignProjectionModel = model
        reEntriesLatest5C = model.reEntriesLatest5C!!
        showCapitalizationInfo(model)
        showOrdersInfo(model)
        fillCicloDeNuevas(model.retentionList, model.reEntriesLatest5C!!)

        progressProjectionViewModel.loadSaleForceStatus()
        progressProjectionViewModel.getCampaignResults(arguments?.getString(Constant.SECTION_PARTNER))
        showRetentionPercentage6d6Message()
    }

    private fun showCapitalizationInfo(model: CampaignProjectionModel) {
        entries = model.entriesProjected!!
        val entriesReal = model.entriesReal!!
        val reEntriesProjected = model.reEntriesProjected!!
        val reEntriesReal = model.reEntriesReal!!
        val pegsRetention = model.pegsRetentionExpected!!
        val pegsReal = model.pegsRetentionReal!!
        val finalsLastYearActives = model.finalsLastYearActives!!

        tv_entries_projected.text = entries.toString()
        tv_entries_real.text = entriesReal.toString()
        tv_entries_real.isSelected = entries > entriesReal

        tv_reentries_projected.text = reEntriesProjected.toString()
        tv_reentries_real.text = reEntriesReal.toString()
        tv_reentries_real.isSelected = reEntriesProjected > reEntriesReal

        et_section_pegs_input.setText(model.pegsInMySection.toString())

        tv_retained_pegs_projected.text = pegsRetention.toString()
        tv_retained_pegs_real.text = pegsReal.toString()
        tv_retained_pegs_real.isSelected = pegsRetention > pegsReal

        et_initial_actives_input.setText(model.initialActives.toString())

        val projectedExpenses = model.pegsInMySection!!.minus(pegsReal)
        et_projected_expenses_input.setText(projectedExpenses.toString())


        projectedCapitalization = entriesReal.plus(reEntriesReal).minus(projectedExpenses)

        et_projected_capitalization_input.setText(projectedCapitalization.toString())

        finalProjectedActives = projectedCapitalization.plus(model.initialActives!!)

        et_final_actives_input.setText(
            finalProjectedActives.toString()
        )

        val retentionPercentage =
            finalProjectedActives.toFloat().div(
                (if (finalsLastYearActives > 0)
                    finalsLastYearActives else Constant.NUMBER_ONE)
            )
        tv_active_retention_percentage.text =
            "${retentionPercentage.toHundredPercentage().formatWithDecimalThousands()} %"
    }

    private fun showOrdersInfo(model: CampaignProjectionModel) {
        totalOrdersGoal = model.currentTotalOrders!!
        currentSymbol = model.currencySymbol

        var lowValueRealProjection = 0f
        var lowValuePlusRealProjection = 0f
        var highValueRealProjection = 0f
        var highValuePlusRealProjection = 0f


        for (order in model.orderList) {
            when (order.title) {
                biz.belcorp.salesforce.modules.calculator.util.Constant.LOW_VALUE_ORDER_TITLE ->
                    lowValueItem =
                        order

                biz.belcorp.salesforce.modules.calculator.util.Constant.LOW_VALUE_PLUS_ORDER_TITLE
                -> lowValuePlusItem =
                    order

                biz.belcorp.salesforce.modules.calculator.util.Constant.HIGH_VALUE_ORDER_TITLE
                -> highValueItem =
                    order

                biz.belcorp.salesforce.modules.calculator.util.Constant.HIGH_VALUE_PLUS_ORDER_TITLE
                -> highValuePlusItem =
                    order

                else -> {
                    //nothing to do
                }
            }
        }

        if (lowValueItem.isNotNull()) {
            val lowValueInput = lowValueItem?.unitsExpected!!
            lowValueRealInput = lowValueItem!!.unitsReal!!

            tv_low_value_projected.text = lowValueInput.toString()
            tv_low_value_real.text = lowValueRealInput.toString()
            tv_low_value_real.isSelected = lowValueInput > lowValueRealInput

            tv_low_value_gain_per_order_value.text = lowValueItem!!.gainPerOrder.toString()

            lowValueRealProjection = lowValueRealInput * lowValueItem!!.gainPerOrder!!

            tv_low_value_gain_per_order_projected_value.text =
                getString(
                    R.string.total_gain_number,
                    currentSymbol,
                    lowValueRealProjection.toString()
                )
        }

        if (lowValuePlusItem.isNotNull() &&
            (Pais.ECUADOR.codigoIso == UserProperties.session?.countryIso
                || Pais.PERU.codigoIso == UserProperties.session?.countryIso)
        ) {
            ll_low_value_plus_input_container.visibility = View.VISIBLE
            v_separator_two.visibility = View.VISIBLE
            val lowValuePlusInput = lowValuePlusItem?.unitsExpected!!
            lowValuePlusRealInput = lowValuePlusItem!!.unitsReal!!

            tv_low_value_plus_projected.text = lowValuePlusInput.toString()
            tv_low_value_plus_real.text = lowValuePlusRealInput.toString()
            tv_low_value_plus_real.isSelected = lowValuePlusInput > lowValuePlusRealInput

            lowValuePlusRealProjection = lowValuePlusRealInput * lowValuePlusItem!!.gainPerOrder!!

            tv_low_value_plus_gain_per_order_value.text = lowValuePlusItem!!.gainPerOrder.toString()
            tv_low_value_plus_gain_per_order_projected_value.text =
                getString(
                    R.string.total_gain_number,
                    currentSymbol,
                    lowValuePlusRealProjection.toString()
                )
        } else {
            ll_low_value_plus_container.visibility = View.GONE
            v_separator_two.visibility = View.GONE
        }

        if (highValueItem.isNotNull()) {
            val highValueInput = highValueItem?.unitsExpected!!
            highValueRealInput = highValueItem!!.unitsReal!!

            tv_high_value_projected.text = highValueInput.toString()
            tv_high_value_real.text = highValueRealInput.toString()
            tv_high_value_real.isSelected = highValueInput > highValueRealInput

            highValueRealProjection = highValueRealInput * highValueItem!!.gainPerOrder!!

            tv_high_value_gain_per_order_value.text = highValueItem!!.gainPerOrder.toString()
            tv_high_value_gain_per_order_projected_value.text =
                getString(
                    R.string.total_gain_number,
                    currentSymbol,
                    highValueRealProjection.toString()
                )
        }

        if (highValuePlusItem.isNotNull()) {
            val highValuePlusInput = highValuePlusItem?.unitsExpected!!
            highValuePlusRealInput = highValuePlusItem!!.unitsReal!!

            tv_high_value_plus_projected.text = highValuePlusInput.toString()
            tv_high_value_plus_real.text = highValuePlusRealInput.toString()
            tv_high_value_plus_real.isSelected = highValuePlusInput > highValuePlusRealInput

            highValuePlusRealProjection =
                highValuePlusRealInput * highValuePlusItem!!.gainPerOrder!!

            tv_high_value_plus_gain_per_order_value.text =
                highValuePlusItem!!.gainPerOrder.toString()
            tv_high_value_plus_gain_per_order_projected_value.text =
                getString(
                    R.string.total_gain_number,
                    currentSymbol,
                    highValuePlusRealProjection.toString()
                )
        }

        realOrderTotalGoal = lowValueItem?.unitsReal
            ?.plus(highValueItem?.unitsReal!!)
            ?.plus(highValuePlusItem?.unitsReal!!)
            ?.plus(
                (if (lowValuePlusItem.isNotNull())
                    lowValuePlusItem?.unitsReal else Constant.NUMBER_ZERO)!!
            )!!


        et_current_orders.setText(totalOrdersGoal.toString())

        if (totalOrdersGoal <= realOrderTotalGoal) {
            tv_total_orders_goal_achieved_label.visibility = View.VISIBLE
            tv_total_orders_goal_not_achieved_label.visibility = View.GONE
        } else {
            tv_total_orders_goal_achieved_label.visibility = View.GONE
            tv_total_orders_goal_not_achieved_label.visibility = View.VISIBLE

        }

        tv_total_orders_number.text = realOrderTotalGoal.toString()

        val totalRealGain = highValuePlusRealProjection.plus(highValueRealProjection)
            .plus(lowValuePlusRealProjection).plus(lowValueRealProjection)

        tv_total_gain_number.text = getString(
            R.string.total_gain_number,
            currentSymbol,
            totalRealGain.toString()
        )

        val activityProjected =
            realOrderTotalGoal.toFloat().div(
                (if (finalProjectedActives > 0)
                    finalProjectedActives else Constant.NUMBER_ONE)
            )
        tv_projected_activity_percentage_number.text =
            "${activityProjected.toHundredPercentage().formatWithDecimalThousands()} %"

        tv_projected_pegs_cx_number.text =
            finalProjectedActives.minus(realOrderTotalGoal).toString()

    }


    private fun updateOrdersValues() {
        var lowValueInput = 0
        var lowValuePlusInput = 0
        var highValueInput = 0
        var highValuePlusInput = 0
        var lowValueGain = 0f
        var lowValuePlusGain = 0f
        var highValueGain = 0f
        var highValuePlusGain = 0f

        if (lowValueItem.isNotNull()) {
            lowValueInput = lowValueItem!!.unitsReal!!
            lowValueGain =
                if (!isDisclaimerSuccess && lowValueItem?.gainPerOrderNotSuccess.isNotNull())
                    lowValueItem!!.gainPerOrderNotSuccess!! else lowValueItem!!.gainPerOrder!!
            val lowValueGainProjected = lowValueInput * lowValueGain
            tv_low_value_gain_per_order_value.text = lowValueGain.toString()
            tv_low_value_gain_per_order_projected_value.text =
                getString(
                    R.string.total_gain_number,
                    campaignProjectionModel.currencySymbol,
                    lowValueGainProjected.formatWithDecimalThousands()
                )
        }
        if (lowValuePlusItem.isNotNull()) {
            lowValuePlusInput = lowValuePlusItem!!.unitsReal!!
            lowValuePlusGain =
                if (!isDisclaimerSuccess && lowValuePlusItem!!.gainPerOrderNotSuccess.isNotNull())
                    lowValuePlusItem!!.gainPerOrderNotSuccess!! else lowValuePlusItem!!.gainPerOrder!!
            val lowValuePlusGainProjected = lowValuePlusInput * lowValuePlusGain
            tv_low_value_plus_gain_per_order_value.text = lowValuePlusGain.toString()
            tv_low_value_plus_gain_per_order_projected_value.text =
                getString(
                    R.string.total_gain_number,
                    campaignProjectionModel.currencySymbol,
                    lowValuePlusGainProjected.formatWithDecimalThousands()
                )
        }
        if (highValueItem.isNotNull()) {
            highValueInput = highValueItem!!.unitsReal!!
            highValueGain =
                if (!isDisclaimerSuccess && highValueItem!!.gainPerOrderNotSuccess.isNotNull())
                    highValueItem!!.gainPerOrderNotSuccess!! else highValueItem!!.gainPerOrder!!
            val highValueGainProjected = highValueInput * highValueGain
            tv_high_value_gain_per_order_value.text = highValueGain.toString()
            tv_high_value_gain_per_order_projected_value.text =
                getString(
                    R.string.total_gain_number,
                    campaignProjectionModel.currencySymbol,
                    highValueGainProjected.formatWithDecimalThousands()
                )
        }
        if (highValuePlusItem.isNotNull()) {
            highValuePlusInput = highValuePlusItem!!.unitsReal!!
            highValuePlusGain =
                if (!isDisclaimerSuccess && highValuePlusItem!!.gainPerOrderNotSuccess.isNotNull())
                    highValuePlusItem!!.gainPerOrderNotSuccess!! else highValuePlusItem!!.gainPerOrder!!
            val highValuePlusGainProjected = highValuePlusInput * highValuePlusGain
            tv_high_value_plus_gain_per_order_value.text = highValuePlusGain.toString()
            tv_high_value_plus_gain_per_order_projected_value.text =
                getString(
                    R.string.total_gain_number,
                    campaignProjectionModel.currencySymbol,
                    highValuePlusGainProjected.formatWithDecimalThousands()
                )
        }
        val totalGain = (lowValueInput * lowValueGain)
            .plus((highValueInput * highValueGain))
            .plus((highValuePlusInput * highValuePlusGain))
            .plus(
                if (lowValuePlusItem.isNotNull())
                    (lowValuePlusInput * lowValuePlusGain) else Constant.ZERO_FLOAT
            )
        tv_total_gain_number.text = getString(
            R.string.total_gain_number,
            campaignProjectionModel.currencySymbol,
            totalGain.toString()
        )
    }

    @SuppressLint("SetTextI18n")
    private fun fillCicloDeNuevas(
        retentionList: List<CampaignProjectionRetentionModel>,
        entriesLatest5C: Int?
    ) {

        val currentCampaignShortName = UserProperties.session?.campaign?.nombreCorto
        tv_retencion_ciclo_de_nuevas_ingresos_amount.text =
            "En campaña $currentCampaignShortName tuviste $entriesLatest5C ingresos"

        for (retention in retentionList) {
            when (retention.title) {
                "2d2" -> {
                    val retentionExpected2d2 = retention.retentionExpected!!
                    val retentionReal2d2 = retention.retentionReal!!
                    val activeConsultant2d2 = retention.activeConsultant!!
                    tv_2d2.text = retention.title
                    tv_2d2_posibles_consultoras_vivas.text = activeConsultant2d2.toString()
                    tv_2d2_projected.text = retentionExpected2d2.toString()
                    tv_2d2_real.text = retentionReal2d2.toString()
                    tv_2d2_real.isSelected = retentionExpected2d2 > retentionReal2d2

                    if (activeConsultant2d2 > 0) {
                        tv_percentage_retention_2d2.invisible()
                        tv_percentage_retention_2d2.text =
                            (((Integer.parseInt(retentionExpected2d2.toString())) * 100)
                                / activeConsultant2d2).toString() + "%"
                    }
                }

                "3d3" -> {
                    val retentionExpected3d3 = retention.retentionExpected!!
                    val retentionReal3d3 = retention.retentionReal!!
                    val activeConsultant3d3 = retention.activeConsultant!!
                    tv_3d3.text = retention.title
                    tv_3d3_posibles_consultoras_vivas.text = activeConsultant3d3.toString()
                    tv_3d3_projected.text = retentionExpected3d3.toString()
                    tv_3d3_real.text = retentionReal3d3.toString()
                    tv_3d3_real.isSelected = retentionExpected3d3 > retentionReal3d3

                    if (activeConsultant3d3 > 0) {
                        tv_percentage_retention_3d3.invisible()
                        tv_percentage_retention_3d3.text =
                            (((Integer.parseInt(retentionExpected3d3.toString())) * 100)
                                / activeConsultant3d3).toString() + "%"
                    }
                }

                "4d4" -> {
                    val retentionExpected4d4 = retention.retentionExpected!!
                    val retentionReal4d4 = retention.retentionReal!!
                    val activeConsultant4d4 = retention.activeConsultant!!
                    tv_4d4.text = retention.title
                    tv_4d4_posibles_consultoras_vivas.text = activeConsultant4d4.toString()
                    tv_4d4_projected.text = retentionExpected4d4.toString()

                    tv_4d4_real.text = retentionReal4d4.toString()
                    tv_4d4_real.isSelected = retentionExpected4d4 > retentionReal4d4

                    if (activeConsultant4d4 > 0) {
                        tv_percentage_retention_4d4.invisible()
                        tv_percentage_retention_4d4.text =
                            (((Integer.parseInt(retentionExpected4d4.toString())) * 100)
                                / activeConsultant4d4).toString() + "%"
                    }
                }

                "5d5" -> {
                    val retentionExpected5d5 = retention.retentionExpected!!
                    val retentionReal5d5 = retention.retentionReal!!
                    val activeConsultant5d5 = retention.activeConsultant!!
                    tv_5d5.text = retention.title
                    tv_5d5_posibles_consultoras_vivas.text = activeConsultant5d5.toString()
                    tv_5d5_projected.text = retentionExpected5d5.toString()
                    tv_5d5_real.text = retentionReal5d5.toString()
                    tv_5d5_real.isSelected = retentionExpected5d5 > retentionReal5d5

                    if (activeConsultant5d5 > 0) {
                        tv_percentage_retention_5d5.invisible()
                        tv_percentage_retention_5d5.text =
                            (((Integer.parseInt(retentionExpected5d5.toString())) * 100)
                                / activeConsultant5d5).toString() + "%"
                    }
                }

                "6d6" -> {
                    if (has6d6()) {
                        cl_ciclo_6d6_container.visibility = View.VISIBLE
                        val retentionExpected6d6 = retention.retentionExpected!!
                        val retentionReal6d6 = retention.retentionReal!!
                        val activeConsultant6d6 = retention.activeConsultant!!
                        tv_6d6.text = retention.title
                        tv_6d6_posibles_consultoras_vivas.text = activeConsultant6d6.toString()
                        tv_6d6_projected.text = retentionExpected6d6.toString()

                        tv_6d6_real.text = retentionReal6d6.toString()
                        tv_6d6_real.isSelected = retentionExpected6d6 > retentionReal6d6

                        if (activeConsultant6d6 > 0) {
                            tv_percentage_retention_6d6.invisible()
                            tv_percentage_retention_6d6.text =
                                (((Integer.parseInt(retentionExpected6d6.toString())) * 100)
                                    / activeConsultant6d6).toString() + "%"

                            showRetentionPercentage6d6Message()
                        }
                    } else {
                        divider_6d6.gone()
                        cl_ciclo_6d6_container.visibility = View.GONE
                    }
                }

                "6d6 Bajo Valor" -> {
                    if (has6d6LowValue()) {
                        divider_6d6_low.visibility = View.VISIBLE
                        high_low_divider.visibility = View.VISIBLE
                        cl_ciclo_6d6_bajo_container.visibility = View.VISIBLE
                        val retentionExpected6d6Bajo = retention.retentionExpected!!
                        val retentionReal6d6Bajo = retention.retentionReal!!
                        val activeConsultant6d6Bajo = retention.activeConsultant!!
                        tv_6d6_bajo.text = retention.title
                        tv_6d6_bajo_posibles_consultoras_vivas.text =
                            activeConsultant6d6Bajo.toString()
                        tv_6d6_bajo_projected.text = retentionExpected6d6Bajo.toString()
                        tv_6d6_bajo_real.text = retentionReal6d6Bajo.toString()
                        tv_6d6_bajo_real.isSelected =
                            retentionExpected6d6Bajo > retentionReal6d6Bajo

                        if (activeConsultant6d6Bajo > 0) {
                            tv_percentage_retention_6d6_bajo.invisible()
                            tv_percentage_retention_6d6_bajo.text =
                                (((Integer.parseInt(retentionExpected6d6Bajo.toString())) * 100)
                                    / activeConsultant6d6Bajo).toString() + "%"

                            showRetentionPercentage6d6Message()
                        }
                    } else {
                        divider_6d6_low.visibility = View.GONE
                        high_low_divider.visibility = View.GONE
                        cl_ciclo_6d6_bajo_container.visibility = View.GONE
                    }
                }

                "6d6 Alto Valor" -> {
                    if (has6d6HighValue()) {
                        high_low_divider.visibility = View.VISIBLE
                        cl_ciclo_6d6_alto_container.visibility = View.VISIBLE
                        val retentionExpected6d6Alto = retention.retentionExpected!!
                        val retentionReal6d6Alto = retention.retentionReal!!
                        val activeConsultant6d6Alto = retention.activeConsultant!!
                        tv_6d6_alto.text = retention.title
                        tv_6d6_alto_posibles_consultoras_vivas.text =
                            activeConsultant6d6Alto.toString()
                        tv_6d6_alto_projected.text = retentionExpected6d6Alto.toString()
                        tv_6d6_alto_real.text = retentionReal6d6Alto.toString()
                        tv_6d6_alto_real.isSelected =
                            retentionExpected6d6Alto > retentionReal6d6Alto

                        if (activeConsultant6d6Alto > 0) {
                            tv_percentage_retention_6d6_alto.invisible()
                            tv_percentage_retention_6d6_alto.text =
                                (((Integer.parseInt(retentionExpected6d6Alto.toString())) * 100)
                                    / activeConsultant6d6Alto).toString() + "%"
                            showRetentionPercentage6d6Message()
                        }
                    } else {
                        high_low_divider.visibility = View.GONE
                        cl_ciclo_6d6_alto_container.visibility = View.GONE
                    }
                }

                "6d6 Alto Valor Multimarca" -> {
                    if (has6d6HighMultiBrandValue()) {
                        high_low_multi_brand_divider.visibility = View.VISIBLE
                        cl_ciclo_6d6_alto_multi_brand_container.visibility = View.VISIBLE
                        val retentionExpected6d6AltoMultiBran = retention.retentionExpected!!
                        val retentionReal6d6AltoMultiBran = retention.retentionReal!!
                        val activeConsultant6d6AltoMultiBran = retention.activeConsultant!!
                        tv_6d6_alto_multi_brand.text = getString(R.string.sixdsix_multi_brand)
                        tv_6d6_alto_posibles_consultoras_vivas_multi_brand.text = activeConsultant6d6AltoMultiBran.toString()
                        tv_6d6_alto_projected_multi_brand.text = retentionExpected6d6AltoMultiBran.toString()
                        tv_6d6_alto_real_multi_brand.text = retentionReal6d6AltoMultiBran.toString()
                        tv_6d6_alto_real_multi_brand.isSelected = retentionExpected6d6AltoMultiBran > retentionReal6d6AltoMultiBran

                        if (activeConsultant6d6AltoMultiBran > 0) {
                            tv_percentage_retention_6d6_alto_multi_brand.invisible()
                            tv_percentage_retention_6d6_alto_multi_brand.text =
                                (((Integer.parseInt(retentionExpected6d6AltoMultiBran.toString())) * 100)
                                    / activeConsultant6d6AltoMultiBran).toString() + "%"
                            showRetentionPercentage6d6Message()
                        }
                    } else {
                        high_low_multi_brand_divider.visibility = View.GONE
                        cl_ciclo_6d6_alto_multi_brand_container.visibility = View.GONE
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

    @SuppressLint("SetTextI18n")
    private fun showRetentionPercentage6d6Message() {

        cl_6d6_profit_proyected_extended.visibility = View.VISIBLE

        //High MultiBrand
        if (has6d6HighMultiBrandValue()) {

            tv_profit_6d6_proyected_title_extended_high_multi_mark.visible()
            tv_profit_6d6_proyected_function_extended_high_multi_mark.visible()
            tv_profit_6d6_proyected_value_percentage_extended_high_multi_mark.visible()

            val firstMemberInFunctionHigh =
                if (tv_6d6_alto_real_multi_brand.text.isNullOrBlank()) Constant.NUMBER_ZERO else tv_6d6_alto_real_multi_brand.text.toString()
                    .toInt()

            val secondMemberInFunctionHigh = getString(
                R.string.cycle_news_profit_proyected_6d6_function,
                currentSymbol,
                campaignProjectionModel.retentionGain6d6HighMultiBrand!!.formatWithNoDecimalThousands()
            )
            val proyectionProfitFunctionHigh =
                "$firstMemberInFunctionHigh * $secondMemberInFunctionHigh"
            val retention6d6GainHigh =
                (campaignProjectionModel.retentionGain6d6HighMultiBrand!! * firstMemberInFunctionHigh).formatWithNoDecimalThousands()
            val percentageCalculationHigh = getString(
                R.string.cycle_news_profit_proyected_6d6_function,
                currentSymbol,
                retention6d6GainHigh
            )

            tv_profit_6d6_proyected_function_extended_high_multi_mark.text = proyectionProfitFunctionHigh
            tv_profit_6d6_proyected_value_percentage_extended_high_multi_mark.text = percentageCalculationHigh

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
                if (tv_6d6_alto_real.text.isNullOrBlank()) Constant.NUMBER_ZERO else tv_6d6_alto_real.text.toString()
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
                if (tv_6d6_bajo_real.text.isNullOrBlank()) Constant.NUMBER_ZERO else tv_6d6_bajo_real.text.toString()
                    .toInt()
            val secondMemberInFunctionLow = getString(
                R.string.cycle_news_profit_proyected_6d6_function,
                currentSymbol,
                campaignProjectionModel.retentionGain6d6Low!!.formatWithNoDecimalThousands()
            )
            val proyectionProfitFunctionLow =
                "$firstMemberInFunctionLow * $secondMemberInFunctionLow"
            val retention6d6GainLow =
                (campaignProjectionModel.retentionGain6d6Low!! * firstMemberInFunctionLow).formatWithNoDecimalThousands()
            val percentageCalculationLow = getString(
                R.string.cycle_news_profit_proyected_6d6_function,
                currentSymbol,
                retention6d6GainLow
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
                if (tv_6d6_real.text.isNullOrBlank()) Constant.NUMBER_ZERO else tv_6d6_real.text.toString()
                    .toInt()
            val secondMemberInFunctionLow = getString(
                R.string.cycle_news_profit_proyected_6d6_function,
                currentSymbol,
                campaignProjectionModel.retentionGain6d6Low!!.formatWithNoDecimalThousands()
            )
            val proyectionProfitFunction =
                "$firstMemberInFunction * $secondMemberInFunctionLow"
            val retention6d6Gain =
                (campaignProjectionModel.retentionGain6d6Low!!.toFloat() * firstMemberInFunction).formatWithNoDecimalThousands()
            val percentageCalculationLow = getString(
                R.string.cycle_news_profit_proyected_6d6_function,
                currentSymbol,
                retention6d6Gain
            )

            tv_profit_6d6_proyected_function_extended.text = proyectionProfitFunction
            tv_profit_6d6_proyected_value_percentage_extended.text =
                percentageCalculationLow
        } else {
            tv_profit_6d6_proyected_title_extended.gone()
            tv_profit_6d6_proyected_function_extended.gone()
            tv_profit_6d6_proyected_value_percentage_extended.gone()
        }


        val firstMemberInFunction =
            if (tv_6d6_real.text.isNullOrBlank()) Constant.NUMBER_ZERO else tv_6d6_real.text.toString()
                .toInt()
        val retention6d6GainLowFunction =
            (campaignProjectionModel.retentionGain6d6Low!! * firstMemberInFunction).formatWithNoDecimalThousands()
        val percentageCalculation = getString(
            R.string.cycle_news_profit_proyected_6d6_function,
            currentSymbol,
            retention6d6GainLowFunction
        )

        val retention6d6 =
            if (tv_6d6_real.text.isNullOrBlank()) Constant.NUMBER_ZERO else tv_6d6_real.text.toString()
                .toInt()

        val retentionLow =
            if (tv_6d6_bajo_real.text.isNullOrBlank()) Constant.NUMBER_ZERO else tv_6d6_bajo_real.text.toString()
                .toInt()
        val retentionHigh =
            if (tv_6d6_alto_real.text.isNullOrBlank()) Constant.NUMBER_ZERO else tv_6d6_alto_real.text.toString()
                .toInt()
        val retentionHighMultiBrand =
            if (tv_6d6_alto_real_multi_brand.text.isNullOrBlank()) Constant.NUMBER_ZERO else tv_6d6_alto_real_multi_brand.text.toString()
                .toInt()

        val firstMemberInFunctionPercentage = retention6d6 + retentionLow + retentionHigh + retentionHighMultiBrand

        if (campaignProjectionModel.reEntriesLatest5C == 0) {
            tv_6d6_percentage_proyected_percentage_extended.text = "${Constant.NUMBER_ZERO}%"
        } else {

            val secondMemberInFunctionPercentage =
                campaignProjectionModel.reEntriesLatest5C

            val proyectionPercentageFunction =
                "( $firstMemberInFunctionPercentage / $secondMemberInFunctionPercentage ) * 100"

            tv_profit_6d6_proyected_value_percentage_extended.text = percentageCalculation

            tv_6d6_percentage_proyected_function_extended.text = proyectionPercentageFunction

            tv_6d6_percentage_proyected_percentage_extended.text =
                ((firstMemberInFunctionPercentage.toFloat() / secondMemberInFunctionPercentage!!.toFloat())
                    * 100).toInt().toString() + "%"
        }


    }


    private fun showError() {
        toast(R.string.campaign_projection_fetch_error)
    }

    override fun getLayout(): Int = R.layout.fragment_progress_projection

    companion object {
        @JvmStatic
        fun newInstance(partnerSection: String?): ProgressProjectionFragment {
            val fragment = ProgressProjectionFragment()
            val bundle = bundleOf(Constant.SECTION_PARTNER to partnerSection)
            fragment.arguments = bundle
            return fragment
        }
    }
}

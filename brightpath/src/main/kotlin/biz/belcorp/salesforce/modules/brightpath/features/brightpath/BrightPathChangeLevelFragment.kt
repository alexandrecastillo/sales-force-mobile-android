package biz.belcorp.salesforce.modules.brightpath.features.brightpath

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.ColorInt
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import biz.belcorp.mobile.components.core.extensions.getColorAttr
import biz.belcorp.mobile.components.core.extensions.getDrawable
import biz.belcorp.mobile.components.core.extensions.tinted
import biz.belcorp.salesforce.analytics.core.domain.entities.UserProperties
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.INSPIRA_BRONCE
import biz.belcorp.salesforce.core.constants.Constant.INSPIRA_DIAMANTE
import biz.belcorp.salesforce.core.constants.Constant.INSPIRA_ORO
import biz.belcorp.salesforce.core.constants.Constant.INSPIRA_PLATA
import biz.belcorp.salesforce.core.constants.Constant.INSPIRA_PLATINIUM
import biz.belcorp.salesforce.core.constants.Constant.INSPIRA_PLATINUM
import biz.belcorp.salesforce.core.constants.Constant.INSPIRA_PRE_BRONCE
import biz.belcorp.salesforce.core.constants.Constant.PHASE_SALE
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.brightpath.R
import biz.belcorp.salesforce.modules.brightpath.features.brightpath.BrightPathChangeLevelViewModel.*
import biz.belcorp.salesforce.modules.consultants.core.data.network.dto.MyPartnersDto
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.se.model.GainDetailSeModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_bright_path_change.*

private const val EMPTY_STATE = 0
private const val DATA_STATE = 1

@Suppress("IMPLICIT_CAST_TO_ANY")
class BrightPathChangeLevelFragment : BaseDialogFragment() {


    override fun getLayout(): Int = R.layout.fragment_bright_path_change

    private val viewModel by inject<BrightPathChangeLevelViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewFlipper.displayedChild = EMPTY_STATE
        setupToolbar()
        setupViewModel()

        pathProgress.isEnabled = false
        progressLowValue.isEnabled = false
        progressHighValue.isEnabled = false
        progressHighPlusValue.isEnabled = false

    }

    private fun setupViewModel() {

        viewModel.viewState.observe(viewLifecycleOwner, userViewStateObserver)
        viewModel.viewStateLevel.observe(viewLifecycleOwner, userViewStateLevelObserver)

        if (arguments?.getString(Constant.PARTNER_DATA).isNotNull()) {
            
            val data = Gson().fromJson(arguments?.getString(Constant.PARTNER_DATA), MyPartnersDto.NewBusinessPartner::class.java)

            viewModel.setDataPartner(data)

            val name = "${data.personalInfo.firstName} ${data.personalInfo.surname}"
            txtConsultantName.text = name

            val uaKey = viewModel.uaKeyFromPartner()

            viewModel.getLevelInformation(uaKey)

            viewModel.getGainSePartner(uaKey)

        } else {
            viewModel.getLevelInformation()
            viewModel.getUserInformation()
        }


    }

    private val userViewStateObserver = Observer<UserInfoViewState> { userSate ->
        userSate?.let {
            when (it) {
                is UserInfoViewState.SuccessSession -> loadUserInformation(it.user)
                is UserInfoViewState.SuccessKpis -> loadKpis(it.kpi)
                else -> {}
            }
        }
    }

    private val userViewStateLevelObserver = Observer<UserInfoLevelViewState> { userSate ->
        userSate?.let {
            when (it) {
                is UserInfoLevelViewState.SuccessLevel -> loadData(it.data)
                else -> {}
            }
        }
    }

    private fun loadKpis(kpi: GainDetailSeModel) {

        val filter = kpi.chargedOrderList.filter { it.label != getString(R.string.low_value_plus) }

        filter.forEach {
            if (it.isNotNull()) {
                if (!lowValue.isVisible()) {
                    lowValue.visible()
                    lowValue.text = it.value
                    lowValueTitle.visible()
                    lowValueTitle.text = it.label
                    progressLowValue.visible()
                    progressLowValue.max = it.maxProgress
                    progressLowValue.progress = it.currentProgress
                } else if (!highValue.isVisible()) {
                    highValue.visible()
                    highValue.text = it.value
                    highValueTitle.visible()
                    highValueTitle.text = it.label
                    progressHighValue.visible()
                    progressHighValue.max = it.maxProgress
                    progressHighValue.progress = it.currentProgress
                } else if (!highValuePlus.isVisible()) {
                    highValuePlus.visible()
                    highValuePlus.text = it.value
                    highValuePlusTitle.visible()
                    highValuePlusTitle.text = it.label
                    progressHighPlusValue.visible()
                    progressHighPlusValue.max = it.maxProgress
                    progressHighPlusValue.progress = it.currentProgress
                }
            }
        }

    }

    private fun loadData(data: List<BusinessPartnerChangeLevelModel>) {
        Log.e("TAG", "loadData: ")

        if (data.isNotEmpty()) {
            viewFlipper.displayedChild = DATA_STATE

            val campaign = data.last()
            setConsultantDetail(campaign)

            setCurrentNextLevel(campaign)

            lastCampaignsList(data)
        }

    }

    private fun setCurrentNextLevel(model: BusinessPartnerChangeLevelModel) {
        val currentLevel =
            model.requirement.last { it.level.toUpperCase() == model.level.name }
        val position = model.requirement.indexOf(currentLevel) + 1
        var nextLevelData = currentLevel
        if (position <= model.requirement.size - 1) {
            nextLevelData = model.requirement[position]
        }

        imgCurrentLevel.setImageDrawable(
            ContextCompat.getDrawable(
                requireActivity(),
                getLevelIcon(currentLevel.level)
            )
        )
        levelStay.text = getString(R.string.to_stay_level, currentLevel.level)
        if (currentLevel.minimumSales > 0) {
            levelStaySell.text =
                getString(
                    R.string.level_sale,
                    currentLevel.minimumSales.toFloat().formatWithDecimalThousands()
                )
        } else {
            levelStaySell.visibility = View.GONE
        }
        levelStayOrders.text =
            getString(R.string.level_orders, currentLevel.minimumOrders.toString())

        imgNextLevel.setImageDrawable(
            ContextCompat.getDrawable(
                requireActivity(),
                getLevelIcon(nextLevelData.level)
            )
        )
        if (INSPIRA_DIAMANTE != currentLevel.level) {
            nextLevelContainer.visibility = View.VISIBLE
            nextLevel.visibility = View.VISIBLE
            imgNextLevel.visibility = View.VISIBLE
            dividerNextLevel.visibility = View.VISIBLE
            nextLevel.text = getString(R.string.to_up_level, nextLevelData.level)
            if (nextLevelData.minimumSales > 0) {
                nextLevelSale.text = getString(
                    R.string.level_sale,
                    nextLevelData.minimumSales.toFloat().formatWithDecimalThousands()
                )
            } else {
                nextLevelSale.visibility = View.GONE
            }
            nextLevelOrder.text =
                getString(R.string.level_orders, nextLevelData.minimumOrders.toString())

            accomplishCampaign.text = getString(
                R.string.campaigns_to_upgrade,
                model.nextLevel.campaigns_accomplished.toString()
            )

            constraintLayout34.visibility = View.VISIBLE
            imgLevelStart.setImageDrawable(
                ContextCompat.getDrawable(
                    requireActivity(),
                    getLevelIcon(currentLevel.level)
                )
            )
            imgLevelEnd.setImageDrawable(
                ContextCompat.getDrawable(
                    requireActivity(),
                    getLevelIcon(nextLevelData.level)
                )
            )
            starLevel.text = currentLevel.level
            endLevel.text = nextLevelData.level
            pathProgress.progress = model.nextLevel.campaigns_accomplished

        } else {
            nextLevelContainer.visibility = View.GONE
            nextLevel.visibility = View.GONE
            imgNextLevel.visibility = View.GONE
            dividerNextLevel.visibility = View.GONE
            constraintLayout34.visibility = View.GONE
        }
        txtConsultantCampaign.text =
            getString(R.string.campaign_acomplish, model.level.consecutiveCampaigns.toString())

        val currentCampaign = campaignNumber(model.campaign)
        yourSales.text = getString(R.string.your_current_sales, currentCampaign)
        yourOrders.text = getString(R.string.your_current_orders, currentCampaign)
        currentSalesAmount.text = model.sectionSales.toFloat().formatWithDecimalThousands()
        currentOrders.text = model.sectionOrders.toString()

        var imgUpgrade = ContextCompat.getDrawable(requireActivity(), R.drawable.order_active)
        var textUpgrade = getString(R.string.upgrade_level_accomplish)

        if (model.nextLevel.accomplished && model.level.name != INSPIRA_DIAMANTE) {
            imgAccomplishLevel.visibility = View.VISIBLE
            textUpgrade = getString(R.string.upgrade_level_accomplish)

        } else if ((!model.nextLevel.accomplished && model.level.accomplished == true)
            || (model.level.accomplished == true && model.level.name == INSPIRA_DIAMANTE)
        ) {
            imgAccomplishLevel.visibility = View.GONE
            textUpgrade = getString(R.string.upgrade_accomplish)

        } else {
            imgUpgrade = ContextCompat.getDrawable(requireActivity(), R.drawable.order_no_active)
            imgAccomplishLevel.visibility = View.VISIBLE
            textUpgrade = getString(R.string.upgrade_no_accomplish)
        }

        imgAccomplishLevel.setImageDrawable(imgUpgrade)
        accomplishLevel.text = textUpgrade



        projectEarning.text = getString(R.string.projection_earnings, nextLevelData.level)

        val value =
            model.gainAmountLowValue + model.gainAmountHighValue + model.gainAmountHighValuePlus
        projectEarningFooter.text =
            getString(
                R.string.project_earning_as,
                nextLevelData.level,
                value.toFloat().formatWithDecimalThousands()
            )

        lowValueAmount.text = model.gainAmountLowValue.formatWithThousands()
        highValueAmount.text = model.gainAmountHighValue.formatWithThousands()
        highValuePlusAmount.text = model.gainAmountHighValuePlus.formatWithThousands()

        if (model.level.name == INSPIRA_DIAMANTE) {
            linarUpLevel.gone()
            constrainEarning.gone()
        }


    }

    private fun campaignNumber(campaign: String): String {
        return "C${campaign.substring(campaign.length - 2)}"
    }

    private fun lastCampaignsList(businessPartnerChangeLevel: List<BusinessPartnerChangeLevelModel>) {

        val businessPartnerChangeLevelList = if (PHASE_SALE ==
            UserProperties.session?.campaign?.getPhase()
        ) {
            if (businessPartnerChangeLevel.size >= 4) {
                businessPartnerChangeLevel.subList(0, businessPartnerChangeLevel.size - 1)
                    .takeLast(3).reversed()
            } else {
                businessPartnerChangeLevel.take(2).reversed()
            }

        } else {
            businessPartnerChangeLevel.take(3).reversed()
        }


        businessPartnerChangeLevelList.forEach {
            val currentCampaign = campaignNumber(it.campaign)
            if (!c3Result.isVisible()) {
                c3Result.visible()
                c3Sell.visible()
                c3Order.visible()
                c3Status.visible()
                c3Result.text = currentCampaign

                c3Sell.text = it.sectionSales.toFloat().formatWithDecimalThousands()
                c3Order.text = it.sectionOrders.toString()
                c3Status.text = if (it.nextLevel.accomplished && it.level.name != INSPIRA_DIAMANTE) {
                    c3Status.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.order_active_small,
                        0,
                        0,
                        0
                    )
                    getString(R.string.upgrade_level_accomplish)
                } else if ((!it.nextLevel.accomplished && it.level.accomplished == true) ||
                    (it.level.accomplished == true && it.level.name == INSPIRA_DIAMANTE)
                ) {
                    c3Status.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        0,
                        0
                    )
                    getString(R.string.upgrade_accomplish)
                } else {
                    c3Status.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.order_no_active_small,
                        0,
                        0,
                        0
                    )
                    getString(R.string.upgrade_no_accomplish)
                }

            } else if (!c2Result.isVisible()) {
                c2Result.visible()
                c2Sell.visible()
                c2Order.visible()
                c2Status.visible()
                c2Result.text = currentCampaign

                c2Sell.text = it.sectionSales.toFloat().formatWithDecimalThousands()
                c2Order.text = it.sectionOrders.toString()
                c2Status.text = if (it.nextLevel.accomplished && it.level.name != INSPIRA_DIAMANTE) {
                    c2Status.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.order_active_small,
                        0,
                        0,
                        0
                    )
                    getString(R.string.upgrade_level_accomplish)
                } else if ((!it.nextLevel.accomplished && it.level.accomplished == true) ||
                    (it.level.accomplished == true && it.level.name == INSPIRA_DIAMANTE)
                ) {
                    c2Status.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        0,
                        0
                    )
                    getString(R.string.upgrade_accomplish)
                } else {
                    c2Status.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.order_no_active_small,
                        0,
                        0,
                        0
                    )
                    getString(R.string.upgrade_no_accomplish)
                }

            } else if (!c1Result.isVisible()) {
                c1Result.visible()
                c1Sell.visible()
                c1Order.visible()
                c1Status.visible()
                c1Result.text = currentCampaign

                c1Sell.text = it.sectionSales.toFloat().formatWithDecimalThousands()
                c1Order.text = it.sectionOrders.toString()
                c1Status.text = if (it.nextLevel.accomplished && it.level.name != INSPIRA_DIAMANTE) {
                    c1Status.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.order_active_small,
                        0,
                        0,
                        0
                    )
                    getString(R.string.upgrade_level_accomplish)
                } else if ((!it.nextLevel.accomplished && it.level.accomplished == true) ||
                    (it.level.accomplished == true && it.level.name == INSPIRA_DIAMANTE)
                ) {
                    c1Status.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        0,
                        0
                    )
                    getString(R.string.upgrade_accomplish)
                } else {
                    c1Status.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.order_no_active_small,
                        0,
                        0,
                        0
                    )
                    getString(R.string.upgrade_no_accomplish)
                }

            }
        }

    }

    private fun loadUserInformation(model: Sesion) {
        Log.e("TAG", "loadUserInformation: ")

        viewModel.getGainSe(model.llaveUA)

        setDataConsultant(model)

    }

    private fun setDataConsultant(model: Sesion) {
        txtConsultantName.text = "${model.person.firstName} ${model.person.firstSurname}"
    }

    private fun getLevelIcon(segment: String): Int {
        return when (segment.toUpperCase()) {
            INSPIRA_PRE_BRONCE -> {
                R.drawable.level_bronze
            }

            INSPIRA_BRONCE -> {
                R.drawable.level_bronze
            }

            INSPIRA_PLATA -> {
                R.drawable.level_silver
            }

            INSPIRA_ORO -> {
                R.drawable.level_gold
            }

            INSPIRA_PLATINIUM, INSPIRA_PLATINUM -> {
                R.drawable.level_platinum
            }

            INSPIRA_DIAMANTE -> {
                R.drawable.level_diamond
            }

            else -> {
                R.drawable.level_bronze
            }
        }
    }

    private fun setConsultantDetail(segment: BusinessPartnerChangeLevelModel) {

        txtConsultantLevel.text = getString(R.string.actual_level, segment.level.name)

        when (segment.level.name?.toUpperCase()) {
            INSPIRA_PRE_BRONCE -> {
                setColorsLevel(
                    R.drawable.background_level_bronze,
                    R.drawable.level_bronze,
                    R.drawable.path_pre_bronze
                )
            }

            INSPIRA_BRONCE -> {
                setColorsLevel(
                    R.drawable.background_level_bronze,
                    R.drawable.level_bronze,
                    R.drawable.path_bronze
                )
            }

            INSPIRA_PLATA -> {
                setColorsLevel(
                    R.drawable.background_level_silver,
                    R.drawable.level_silver,
                    R.drawable.path_silver
                )
            }

            INSPIRA_ORO -> {
                setColorsLevel(
                    R.drawable.background_level_gold,
                    R.drawable.level_gold,
                    R.drawable.path_gold
                )
            }

            INSPIRA_PLATINUM, INSPIRA_PLATINIUM -> {
                setColorsLevel(
                    R.drawable.background_level_platinum,
                    R.drawable.level_platinum,
                    R.drawable.path_platinum
                )
            }

            INSPIRA_DIAMANTE -> {
                setColorsLevel(
                    R.drawable.background_level_diamond,
                    R.drawable.level_diamond,
                    R.drawable.path_diamond
                )
            }
        }
    }

    private fun setColorsLevel(background: Int, icon: Int, path: Int) {
        containerLevel.background = ContextCompat.getDrawable(requireActivity(), background)
        imgLevel.setImageDrawable(ContextCompat.getDrawable(requireActivity(), icon))
        imgLevelHorizontal.setImageDrawable(ContextCompat.getDrawable(requireActivity(), path))
    }

    private fun setupToolbar() {
        actionBar(changeBrightPathToolbar as? MaterialToolbar) {
            title = "Cambio nivel"
            val titleColor = getColorAttr(android.R.attr.textColorPrimary)
            customizeReturnableMode(titleColor)
        }
    }

    private fun Toolbar.customizeReturnableMode(@ColorInt titleColor: Int) {
        val icon = getDrawable(biz.belcorp.salesforce.core.R.drawable.ic_backspace)
        val iconTinted = icon?.tinted(titleColor)
        navigationIcon = iconTinted?.mutate()
        setNavigationOnClickListener {

            if (arguments?.getString(Constant.PARTNER_DATA).isNotNull()) {
                findNavController().navigate(biz.belcorp.salesforce.base.R.id.actionGoToMyPartnersFragment)
            } else {
                findNavController().navigateUp()
            }

        }
    }

}

package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.view.View.GONE
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.base.BaseToolbarDialogFragment
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.sales.ConsultantSaleEntity
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.utils.formatearConComas
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.toUpperCase
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.NUMBER_FIVE
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.NUMBER_FOUR
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.NUMBER_ONE
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.NUMBER_SIX
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.NUMBER_THREE
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.NUMBER_TWO
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.adapters.LevelRequestAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.model.LevelRequestModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.viewmodel.BrightPathViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.viewmodel.BrightPathViewModel.BrightPathViewState
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_bright_path.amountCurrentLevel
import kotlinx.android.synthetic.main.fragment_bright_path.amountNextLevel
import kotlinx.android.synthetic.main.fragment_bright_path.campaignProgress
import kotlinx.android.synthetic.main.fragment_bright_path.containerLevel
import kotlinx.android.synthetic.main.fragment_bright_path.currentLevel
import kotlinx.android.synthetic.main.fragment_bright_path.endPath
import kotlinx.android.synthetic.main.fragment_bright_path.endPathStay
import kotlinx.android.synthetic.main.fragment_bright_path.endPathStayAmount
import kotlinx.android.synthetic.main.fragment_bright_path.endPathStayCampaign
import kotlinx.android.synthetic.main.fragment_bright_path.headerToolbar
import kotlinx.android.synthetic.main.fragment_bright_path.imgPath
import kotlinx.android.synthetic.main.fragment_bright_path.imgPathEnd
import kotlinx.android.synthetic.main.fragment_bright_path.imgPathEndStay
import kotlinx.android.synthetic.main.fragment_bright_path.imgPathHorizontal
import kotlinx.android.synthetic.main.fragment_bright_path.imgPathStart
import kotlinx.android.synthetic.main.fragment_bright_path.imgPathStartStay
import kotlinx.android.synthetic.main.fragment_bright_path.levelRequest
import kotlinx.android.synthetic.main.fragment_bright_path.nextLevel
import kotlinx.android.synthetic.main.fragment_bright_path.pathProgress
import kotlinx.android.synthetic.main.fragment_bright_path.sb_bright_path_progress
import kotlinx.android.synthetic.main.fragment_bright_path.starPath
import kotlinx.android.synthetic.main.fragment_bright_path.startPathStay
import kotlinx.android.synthetic.main.fragment_bright_path.startPathStayAmount
import kotlinx.android.synthetic.main.fragment_bright_path.startPathStayCampaign
import kotlinx.android.synthetic.main.fragment_bright_path.tv_amount_orders
import kotlinx.android.synthetic.main.fragment_bright_path.tv_first_element_campaign
import kotlinx.android.synthetic.main.fragment_bright_path.tv_five_element_campaign
import kotlinx.android.synthetic.main.fragment_bright_path.tv_four_element_campaign
import kotlinx.android.synthetic.main.fragment_bright_path.tv_last_element_campaign
import kotlinx.android.synthetic.main.fragment_bright_path.tv_progress_title_section
import kotlinx.android.synthetic.main.fragment_bright_path.tv_second_element_campaign
import kotlinx.android.synthetic.main.fragment_bright_path.tv_third_element_campaign
import kotlinx.android.synthetic.main.fragment_bright_path.txtConsultantCampaign
import kotlinx.android.synthetic.main.fragment_bright_path.txtConsultantLevel
import kotlinx.android.synthetic.main.fragment_bright_path.txtConsultantName
import kotlinx.android.synthetic.main.fragment_bright_path.viewFlipperBrightPath


const val CONSULTORA_BRILLANTE = "BRILLANTE"
const val CONSULTORA_TOPACIO = "TOPACIO"
const val CONSULTORA_PERLA = "PERLA"
const val CONSULTORA_AMBAR = "ÁMBAR"
const val CONSULTORA_AAMBAR = "AMBAR"
const val CONSULTORA_CORAL = "CORAL"
const val CONSULTORA_CONSULTORA = "CONSULTORA"

const val SHOW_CONTENT = 0
const val SHOW_EMPTY = 1


class BrightPathFragment : BaseToolbarDialogFragment() {

    private val viewModel by injectFragment<BrightPathViewModel>()

    private val personIdentifier by lazyPersonIdentifier()

    private lateinit var saleConsultantTextResolver: SaleConsultantTextResolver

    private val adapterLevelRequest: LevelRequestAdapter by lazy { LevelRequestAdapter() }

    override fun getLayout(): Int = R.layout.fragment_bright_path

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    override fun getToolbar(): MaterialToolbar? = headerToolbar as? MaterialToolbar

    override fun getTitle(): String? = getString(R.string.sale)

    override fun getMode(): Mode = Mode.RETURNABLE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpLevelProgressbar()
        setUpPeriodProgressbar()
        setupViewModel()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpPeriodProgressbar() {
        sb_bright_path_progress.setOnTouchListener { _, _ -> true }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpLevelProgressbar() {
        pathProgress.setOnTouchListener { _, _ -> true }
    }

    private fun setupViewModel() {
        saleConsultantTextResolver = SaleConsultantTextResolver(context!!)
        viewModel.viewState.observe(viewLifecycleOwner, infoViewState)
        viewModel.viewStatePersonalInfo.observe(viewLifecycleOwner, infoViewState)

        viewModel.fetchPersonInfo(personIdentifier)
        viewModel.fetchSalesInfo(personIdentifier)
    }

    private val infoViewState = Observer<BrightPathViewState> { infoState ->
        infoState?.let {
            when (it) {
                is BrightPathViewState.ShowPersonInfo -> showPersonInfo(it.person)
                is BrightPathViewState.ShoSaleInfo -> showSaleInfo(it.sale)
            }
        }
    }

    private fun showPersonInfo(model: BrightPathCabeceraModel) {
        txtConsultantName.text = model.nombres
    }

    private fun showSaleInfo(model: List<ConsultantSaleEntity>) {
        Log.e("TAG", "showPersonInfo: ")
        if (model.isNotEmpty()) {

            viewFlipperBrightPath.displayedChild = SHOW_CONTENT

            val modelEntity: ConsultantSaleEntity = model.last()
            txtConsultantLevel.text =
                getString(R.string.consultant_level, modelEntity.brightPathLevel.last().name)
            setConsultantDetail(modelEntity.brightPathLevel.last().name)
            txtConsultantCampaign.text =
                getString(R.string.progress_current, modelEntity.brightPathPeriod_)

            starPath.text =
                getString(R.string.level_consultant, modelEntity.brightPathLevel.last().name)
            endPath.text =
                getString(R.string.level_consultant, modelEntity.brightPathNextLevel[0].name)


            campaignProgress.text = SpannableStringBuilder()
                .append(getString(R.string.campaign_progress_1))
                .append(" ")
                .bold {
                    append(
                        getString(
                            R.string.campaign_progress_2,
                            modelEntity.brightPathLevel.last().accumulativeOrders
                        )
                    )
                }
                .append(" ")
                .append(getString(R.string.campaign_progress_3))
                .append(" ")
                .bold {
                    append(
                        getString(
                            R.string.campaign_progress_4,
                            modelEntity.brightPathLevel.last().campaignProgress
                        )
                    )
                }
                .append(" ")
                .append(getString(R.string.campaign_progress_5))

            currentLevel.text = getString(
                R.string.currentLevelAccumulated,
                modelEntity.brightPathLevel.last().name,
                getString(R.string.current_campaign_format, modelEntity.campaign.takeLast(2))
            )


            amountCurrentLevel.text =
                getString(
                    R.string.currency_simbol,
                    modelEntity.brightPathLevel.last().accumulativeSales.formatearConComas()
                )

            nextLevel.text =
                getString(R.string.nextLevelAccumulated, modelEntity.brightPathNextLevel[0].name)

            var amount =
                modelEntity.brightPathNextLevel[0].brightPathNextLevelSales[0].salesRequirement - modelEntity.brightPathLevel[0].accumulativeSales

            if (amount < 0) amount = 0.0

            amountNextLevel.text =
                getString(
                    R.string.currency_simbol,
                    amount.formatearConComas()
                )


            setProgress(model)

            setStayLevel(model)
            tv_progress_title_section.text =
                getString(R.string.current_period, modelEntity.brightPathPeriod_)
            setCampaignProgress(modelEntity.brightPathPeriod_, modelEntity.campaign)
            if (modelEntity.brightPathLevel.isNotEmpty()) {
                tv_amount_orders.text = getString(
                    R.string.amount_orders,
                    modelEntity.brightPathLevel.last().accumulativeOrders.toString()
                )
            }
            recyclerLevelRequest(model)
        } else {

            viewFlipperBrightPath.displayedChild = SHOW_EMPTY

        }
    }

    private fun setCampaignProgress(brightPathPeriod: String, currentCampaign: String) {
        when (brightPathPeriod) {
            "C03-C08" -> {
                val position = setCampaignPosition(1, currentCampaign)
                tv_first_element_campaign.text = "C3"
                tv_second_element_campaign.text = "C4"
                tv_third_element_campaign.text = "C5"
                tv_four_element_campaign.text = "C6"
                tv_five_element_campaign.text = "C7"
                tv_last_element_campaign.text = "C8"
                switchCampaignIndicatorColor(position)
                sb_bright_path_progress.progress = getProgressViewPosition(position)
            }

            "C09-C14" -> {
                val position = setCampaignPosition(2, currentCampaign)
                tv_first_element_campaign.text = "C9"
                tv_second_element_campaign.text = "C10"
                tv_third_element_campaign.text = "C11"
                tv_four_element_campaign.text = "C12"
                tv_five_element_campaign.text = "C13"
                tv_last_element_campaign.text = "C14"
                switchCampaignIndicatorColor(position)
                sb_bright_path_progress.progress = getProgressViewPosition(position)
            }

            "C15-C02" -> {
                val position = setCampaignPosition(3, currentCampaign)
                tv_first_element_campaign.text = "C15"
                tv_second_element_campaign.text = "C16"
                tv_third_element_campaign.text = "C17"
                tv_four_element_campaign.text = "C18"
                tv_five_element_campaign.text = "C01"
                tv_last_element_campaign.text = "C02"
                switchCampaignIndicatorColor(position)
                sb_bright_path_progress.progress = getProgressViewPosition(position)
            }
        }

    }


    private fun setCampaignPosition(period: Int, currentCampaign: String): Int {
        val lastDigitsCampaign = currentCampaign.takeLast(2)
        var pos = 0
        if (period == NUMBER_ONE) {
            for (index in 3..8) {
                pos++
                if (lastDigitsCampaign.toInt() == index) {
                    return pos
                }
            }
        }
        if (period == NUMBER_TWO) {
            for (index in 9..14) {
                pos++
                if (lastDigitsCampaign.toInt() == index) {
                    return pos
                }
            }
        }
        if (period == NUMBER_THREE) {
            when (lastDigitsCampaign.toInt()) {
                NUMBER_ONE -> {
                    pos = 5
                    return pos
                }

                NUMBER_TWO -> {
                    pos = 6
                    return pos
                }

                else -> {
                    for (index in 15..18) {
                        pos++
                        if (lastDigitsCampaign.toInt() == index) {
                            return pos
                        }
                    }
                }
            }
        }
        return pos
    }

    private fun getProgressViewPosition(position: Int): Int {
        when (position) {
            NUMBER_ONE -> {
                return 0
            }

            NUMBER_TWO -> {
                return 18
            }

            NUMBER_THREE -> {
                return 40
            }

            NUMBER_FOUR -> {
                return 62
            }

            NUMBER_FIVE -> {
                return 82
            }

            NUMBER_SIX -> {
                return 100
            }
        }
        return 0
    }

    private fun switchCampaignIndicatorColor(position: Int) {
        when (position) {
            NUMBER_ONE -> {
                tv_first_element_campaign.isSelected = true
            }

            NUMBER_TWO -> {
                tv_first_element_campaign.isSelected = true
                tv_second_element_campaign.isSelected = true
            }

            NUMBER_THREE -> {
                tv_first_element_campaign.isSelected = true
                tv_second_element_campaign.isSelected = true
                tv_third_element_campaign.isSelected = true
            }

            NUMBER_FOUR -> {
                tv_first_element_campaign.isSelected = true
                tv_second_element_campaign.isSelected = true
                tv_third_element_campaign.isSelected = true
                tv_four_element_campaign.isSelected = true
            }

            NUMBER_FIVE -> {
                tv_first_element_campaign.isSelected = true
                tv_second_element_campaign.isSelected = true
                tv_third_element_campaign.isSelected = true
                tv_four_element_campaign.isSelected = true
                tv_five_element_campaign.isSelected = true
            }

            NUMBER_SIX -> {
                tv_first_element_campaign.isSelected = true
                tv_second_element_campaign.isSelected = true
                tv_third_element_campaign.isSelected = true
                tv_four_element_campaign.isSelected = true
                tv_five_element_campaign.isSelected = true
                tv_last_element_campaign.isSelected = true
            }
        }
    }

    private fun setStayLevel(model: List<ConsultantSaleEntity>) {
        val campaign = model.last().brightPathLevel.last().campaignProgress
        startPathStay.text =
            getString(R.string.to_stay_sell, model.last().brightPathLevel.last().name.toUpperCase())

        if (model.last().brightPathLevel.last().averageCurrentLevel!! > 0) {

            startPathStayAmount.text =
                getString(
                    R.string.currency_simbol,
                    model.last().brightPathLevel.last().averageCurrentLevel!!.formatearConComas()
                )
            startPathStayCampaign.text = SpannableStringBuilder()
                .append(getString(R.string.for_each_campaign))
                .append(" ")
                .bold { append(getString(R.string.campaign_progress_2, campaign.toString())) }
                .append(" ")
                .append(getString(R.string.campaign_progress_5))
        } else {
            startPathStayAmount.visibility = GONE
            startPathStayCampaign.text = SpannableStringBuilder()
                .append(getString(R.string.pending_campaign))
                .append(" ")
                .bold { append(getString(R.string.campaign_progress_2, campaign.toString())) }
                .append(" ")
                .append(getString(R.string.campaign_progress_5))
        }

        endPathStay.text =
            getString(
                R.string.to_upgrade_sale,
                model.last().brightPathNextLevel[0].name.toUpperCase()
            )
        endPathStayAmount.text =
            getString(
                R.string.currency_simbol,
                model.last().brightPathNextLevel[0].brightPathNextLevelSales[0].average.formatearConComas()
            )
        endPathStayCampaign.text = SpannableStringBuilder()
            .append(getString(R.string.for_each_campaign))
            .append(" ")
            .bold { append(getString(R.string.campaign_progress_2, campaign.toString())) }
            .append(" ")
            .append(getString(R.string.campaign_progress_5))
    }

    private fun setConsultantDetail(segment: String) {
        when (segment.toUpperCase()) {
            CONSULTORA_BRILLANTE -> {
                setColorsLevel(
                    R.drawable.background_consultant_bright,
                    R.drawable.level_consultant_bright,
                    R.drawable.path_consultant_bright
                )
            }

            CONSULTORA_TOPACIO -> {
                setColorsLevel(
                    R.drawable.background_consultant_topacio,
                    R.drawable.level_consultant_topacio,
                    R.drawable.path_consultant_topacio
                )
            }

            CONSULTORA_PERLA -> {
                setColorsLevel(
                    R.drawable.background_consultant_perla,
                    R.drawable.level_consultant_perla,
                    R.drawable.path_consultant_perla
                )
            }

            CONSULTORA_AMBAR -> {
                setColorsLevel(
                    R.drawable.background_consultant_ambar,
                    R.drawable.level_consultant_ambar,
                    R.drawable.path_consultant_ambar
                )
            }

            CONSULTORA_AAMBAR -> {
                setColorsLevel(
                    R.drawable.background_consultant_ambar,
                    R.drawable.level_consultant_ambar,
                    R.drawable.path_consultant_ambar
                )
            }

            CONSULTORA_CORAL -> {
                setColorsLevel(
                    R.drawable.background_consultant_coral,
                    R.drawable.level_consultant_coral,
                    R.drawable.path_consultant_coral
                )
            }

            CONSULTORA_CONSULTORA -> {
                setColorsLevel(
                    R.drawable.background_consultant,
                    R.drawable.level_consultant,
                    R.drawable.path_consultant
                )
            }
        }
    }

    private fun setProgress(model: List<ConsultantSaleEntity>) {

        var icon: Int = R.drawable.level_consultant
        var iconEnd: Int = R.drawable.level_consultant
        var progress: Int = R.drawable.seekbar_consultant_progress_style

        when (model.last().brightPathLevel.last().name.toUpperCase()) {
            CONSULTORA_BRILLANTE -> {
                icon = R.drawable.level_consultant_bright
                progress = R.drawable.seekbar_consultant_bright_progress_style
            }

            CONSULTORA_TOPACIO -> {
                icon = R.drawable.level_consultant_topacio
                progress = R.drawable.seekbar_consultant_topacio_progress_style
            }

            CONSULTORA_PERLA -> {
                icon = R.drawable.level_consultant_perla
                progress = R.drawable.seekbar_consultant_perla_progress_style
            }

            CONSULTORA_AMBAR -> {
                icon = R.drawable.level_consultant_ambar
                progress = R.drawable.seekbar_consultant_ambar_progress_style
            }

            CONSULTORA_AAMBAR -> {
                icon = R.drawable.level_consultant_ambar
                progress = R.drawable.seekbar_consultant_ambar_progress_style
            }

            CONSULTORA_CORAL -> {
                icon = R.drawable.level_consultant_coral
                progress = R.drawable.seekbar_consultant_coral_progress_style
            }

            CONSULTORA_CONSULTORA -> {
                icon = R.drawable.level_consultant
                progress = R.drawable.seekbar_consultant_progress_style
            }
        }

        when (model.last().brightPathNextLevel[0].name.toUpperCase()) {
            CONSULTORA_BRILLANTE -> {
                iconEnd = R.drawable.level_consultant_bright
            }

            CONSULTORA_TOPACIO -> {
                iconEnd = R.drawable.level_consultant_topacio
            }

            CONSULTORA_PERLA -> {
                iconEnd = R.drawable.level_consultant_perla
            }

            CONSULTORA_AMBAR -> {
                iconEnd = R.drawable.level_consultant_ambar
            }

            CONSULTORA_AAMBAR -> {
                iconEnd = R.drawable.level_consultant_ambar
            }

            CONSULTORA_CORAL -> {
                iconEnd = R.drawable.level_consultant_coral
            }

            CONSULTORA_CONSULTORA -> {
                iconEnd = R.drawable.level_consultant
            }
        }

        imgPathStart.setImageDrawable(ContextCompat.getDrawable(requireActivity(), icon))
        imgPathEnd.setImageDrawable(ContextCompat.getDrawable(requireActivity(), iconEnd))

        pathProgress.progressDrawable = ContextCompat.getDrawable(requireActivity(), progress)
        pathProgress.max =
            model.last().brightPathNextLevel[0].brightPathNextLevelSales[0].salesRequirement.toInt()
        pathProgress.progress = model.last().brightPathLevel.last().accumulativeSales.toInt()

        imgPathStartStay.setImageDrawable(ContextCompat.getDrawable(requireActivity(), icon))
        imgPathEndStay.setImageDrawable(ContextCompat.getDrawable(requireActivity(), iconEnd))

    }


    private fun setColorsLevel(background: Int, icon: Int, path: Int) {
        containerLevel.background = ContextCompat.getDrawable(requireActivity(), background)
        imgPath.setImageDrawable(ContextCompat.getDrawable(requireActivity(), icon))
        imgPathHorizontal.setImageDrawable(ContextCompat.getDrawable(requireActivity(), path))
    }

    private fun recyclerLevelRequest(model: List<ConsultantSaleEntity>) {
        with(levelRequest) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = adapterLevelRequest
        }

        val data = listOf(


            LevelRequestModel(
                path = getString(
                    R.string.level_consultant,
                    model.last().brightPathNextLevel[0].name
                ),
                saleAccumulative =
                getString(
                    R.string.currency_simbol,
                    model.last().brightPathNextLevel[0].brightPathNextLevelSales[0].salesRequirement.formatearConComas()
                ),
                currentLevelOrderRequirement = getString(
                    R.string.level_orders_placed,
                    model.last().brightPathNextLevel[0].brightPathNextLevelOrder[0].requirement
                ),
            )
        ).toMutableList()

        if (model.last().brightPathLevel.last().name.toUpperCase() != CONSULTORA_CONSULTORA) {
            data.add(
                0, LevelRequestModel(
                    path = getString(R.string.level_consultant, model.last().brightPathLevel.last().name),
                    saleAccumulative =
                    getString(
                        R.string.currency_simbol,
                        model.last().brightPathLevel.last().currentLevelSalesRequirement.formatearConComas()
                    ),
                    currentLevelOrderRequirement = getString(
                        R.string.level_orders_placed,
                        model.last().brightPathLevel.last().currentLevelOrderRequirement
                    ),
                )
            )
        }

        adapterLevelRequest.submitList(data)

    }
}

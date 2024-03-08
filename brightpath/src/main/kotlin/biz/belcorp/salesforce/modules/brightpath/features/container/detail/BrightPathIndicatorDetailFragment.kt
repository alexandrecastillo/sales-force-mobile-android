package biz.belcorp.salesforce.modules.brightpath.features.container.detail


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.tinted
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.features.consultants.OnConsultantsListener
import biz.belcorp.salesforce.core.include.Include
import biz.belcorp.salesforce.core.include.IncludeManager
import biz.belcorp.salesforce.core.utils.actionBar
import biz.belcorp.salesforce.core.utils.commitFragment
import biz.belcorp.salesforce.core.utils.inject
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.brightpath.R
import biz.belcorp.salesforce.modules.brightpath.features.container.detail.filter.FilterConstancyFragment
import biz.belcorp.salesforce.modules.brightpath.features.container.detail.filter.OnSelectedLevelFilter
import biz.belcorp.salesforce.modules.brightpath.features.header.BrightPathHeaderKpiFragment
import biz.belcorp.salesforce.modules.brightpath.features.ua.OnUASegmentSelectedListener
import biz.belcorp.salesforce.modules.brightpath.features.ua.UASegmentModel
import biz.belcorp.salesforce.modules.brightpath.features.ua.UASegmentsFragment
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_level_indicator_detail.*
import kotlinx.android.synthetic.main.fragment_level_indicator_detail.toolbar
import kotlinx.android.synthetic.main.view_top_header_indicator_info.*


class BrightPathIndicatorDetailFragment :
    BaseDialogFragment(),
    BrightPathIndicatorDetailView,
    OnSelectedLevelFilter,
    OnUASegmentSelectedListener {

    private val includeManager by inject<IncludeManager>()

    private val presenter: BrightPathIndicatorDetailPresenter by inject()

    private lateinit var constancyFilterFragment: FilterConstancyFragment
    private lateinit var headerFragment: BrightPathHeaderKpiFragment
    private lateinit var consultantFragment: Fragment
    private lateinit var uaSegmentsFrag: UASegmentsFragment
    private lateinit var brightPathHeaderKpiFragment: BrightPathHeaderKpiFragment

    override fun getLayout() = R.layout.fragment_level_indicator_detail

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val prevUASelected = it.getString(ARG_PREV_UA_ID_SELECTED, EMPTY_STRING)
            presenter.setUaIdSegmentSelected(prevUASelected)
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.registerReceiver(
            consultantsCounterReceiver,
            IntentFilter(Constant.ACTION_CONSULTANTS_COUNT)
        )
    }

    override fun onPause() {
        super.onPause()
        activity?.unregisterReceiver(consultantsCounterReceiver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.create(this)
        initFrags()
        initEvents()
        presenter.initViews()

        arguments?.getInt(ARG_BEAUTY_CONSULTANT_TYPE_LIST)?.let {
            if (it == 4) {
                presenter.checkEndPeriodTitle()
                brightPathHeaderKpiFragment.arguments =
                    this@BrightPathIndicatorDetailFragment.arguments
            }
        }
    }

    override fun showHeader() {
        commitFragment(
            R.id.frameHeaderView,
            brightPathHeaderKpiFragment
        )
    }

    override fun showCampaign(campaignText: String) {
        tvCampaignInfo?.text = campaignText
    }

    private fun initEvents() {
        actionBar(toolbar as MaterialToolbar) {
            setNavigationIcon(biz.belcorp.salesforce.base.R.drawable.ic_backspace)
            navigationIcon?.tinted(getColor(biz.belcorp.salesforce.base.R.color.white))
            setNavigationOnClickListener {
                closeDialog()
            }
        }
    }

    override fun showConsultancyFilter() {
        commitFragment(R.id.frameConstancyFilterView, constancyFilterFragment)
    }

    override fun showBeautyConsultantListView() {
        commitFragment(R.id.frameConsultantsListView, consultantFragment)
    }

    override fun showUaSegment() {
        uaSegmentsFrag = UASegmentsFragment.newInstance(
            presenter.getUaSegmentSelected(),
            false
        )

        uaSegmentsFrag.setViewListener(this)

        commitFragment(R.id.frameUASegmentView, uaSegmentsFrag)

    }


    override fun onSelectedLevelFilter(level: String) {
        presenter.setLevelSelectedOnFilter(level)

        if (presenter.getTypeSelection() == 4) {
            val endLevel = if (level.trim().isEmpty()) {
                "%"
            } else level

            (consultantFragment as? OnConsultantsListener)?.getEndPeriodConsultants(
                nivel = endLevel
            )
            return
        }

        (consultantFragment as? OnConsultantsListener)?.getGrownConsultants(
            presenter.getLevelSelectedOnFilter(),
            presenter.getUaSegmentSelected()
        )
    }

    override fun showEndPeriodTitle(title: String) {
        tvIndicatorName.text = title
    }

    override fun getBeautyConsultantList(
        uaSegmentSelected: String,
        constancySelected: String,
        typeSelection: Int
    ) {
        (consultantFragment as? OnConsultantsListener)?.getGrownConsultants(
            constancySelected,
            uaSegmentSelected, typeSelection = typeSelection
        )
    }

    private fun initFrags() {
        val beautyView = arguments?.let {
            it.getInt(ARG_BEAUTY_CONSULTANT_TYPE_LIST, GROWN_BEAUTY_CONSULTANT_VIEW)
        } ?: run {
            GROWN_BEAUTY_CONSULTANT_VIEW
        }
        presenter.setTypeSelection(beautyView)
        brightPathHeaderKpiFragment = BrightPathHeaderKpiFragment.newInstance()
        constancyFilterFragment = FilterConstancyFragment.newInstance()

        constancyFilterFragment.arguments = arguments

        headerFragment = BrightPathHeaderKpiFragment.newInstance()
        consultantFragment = includeManager.getInclude(Include.LEGACY_CONSULTANT_LIST)
            .withArguments(
                ARG_PREV_UA_ID_SELECTED to presenter.getUaSegmentSelected(),
                ARG_BEAUTY_CONSULTANT_TYPE_LIST to beautyView
            )
        constancyFilterFragment.setListener(this)
    }


    override fun onSegmentSelected(segmentSelected: UASegmentModel?) {
        segmentSelected?.let {
            presenter.setUaIdSegmentSelected(it.segmentID)
            presenter.getBeautyConsultants()
        }
    }

    companion object {
        const val GROWN_BEAUTY_CONSULTANT_VIEW = 2
        const val ARG_PREV_UA_ID_SELECTED = "PREV_UA_SEGMENT_ID_SELECTED"

        const val ARG_BEAUTY_CONSULTANT_TYPE_LIST = "ARG_BEAUTY_CONSULTANT_TYPE_LIST"
    }

    private val consultantsCounterReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            val action = Constant.ACTION_CONSULTANTS_COUNT

            p1?.let { intent ->
                if (intent.action.equals(action)) {
                    val amount =
                        intent.getIntExtra(Constant.BUNDLE_CONSULTANTS_COUNT, Constant.NUMBER_ZERO)

                    if (amount > Constant.NUMBER_ZERO) {
                        tvConsultansCount.visibility = View.VISIBLE
                        tvConsultansCount.text = if (amount == Constant.NUMBER_ONE) {
                            "$amount consultora encontrada"
                        } else {
                            "$amount consultoras encontradas"
                        }
                    }
                }
            }
        }
    }
}

package biz.belcorp.salesforce.modules.brightpath.features.container


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.tinted
import biz.belcorp.salesforce.base.utils.navigateTo
import biz.belcorp.salesforce.components.utils.decoration.ItemOffsetDecoration
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.Constant.NUMERO_DOS
import biz.belcorp.salesforce.core.utils.actionBar
import biz.belcorp.salesforce.core.utils.setIndicatorColor
import biz.belcorp.salesforce.core.utils.setOnSafeRefreshListener
import biz.belcorp.salesforce.modules.brightpath.R
import biz.belcorp.salesforce.modules.brightpath.core.domain.constants.Constants
import biz.belcorp.salesforce.modules.brightpath.features.container.consultants.ConsultantsIndicatorDetailFragment.Companion.ARG_CONSULTANT_LIST_HEADER_MODEL
import biz.belcorp.salesforce.modules.brightpath.features.container.detail.BrightPathIndicatorDetailFragment
import biz.belcorp.salesforce.modules.brightpath.features.drilldown.GridBrightPathDetailKpiPresenter
import biz.belcorp.salesforce.modules.brightpath.features.drilldown.GridDrillDownAdapter
import biz.belcorp.salesforce.modules.brightpath.features.drilldown.GridView
import biz.belcorp.salesforce.modules.brightpath.features.drilldown.model.ConsultantHeaderDetailedKpiModel
import biz.belcorp.salesforce.modules.brightpath.features.header.BrightPathHeaderKpiFragment
import biz.belcorp.salesforce.modules.brightpath.features.ua.OnUASegmentSelectedListener
import biz.belcorp.salesforce.modules.brightpath.features.ua.UASegmentModel
import biz.belcorp.salesforce.modules.brightpath.features.ua.UASegmentsFragment
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_indicator_detail.*
import kotlinx.android.synthetic.main.fragment_indicator_detail.toolbar
import kotlinx.android.synthetic.main.view_top_header_indicator_info.*
import org.koin.android.ext.android.inject
import biz.belcorp.salesforce.base.R as BaseR


class IndicatorDetailFragment : BaseDialogFragment(),
    IndicatorDetailView,
    GridView,
    OnUASegmentSelectedListener {

    override fun getLayout(): Int = R.layout.fragment_indicator_detail

    private lateinit var uaSegmentsFragment: UASegmentsFragment

    private val headerDetailKpiPresenter: IndicatorDetailPresenter by inject()
    private val detailContentKpiPresenter: GridBrightPathDetailKpiPresenter by inject()

    private var gridDrillDownAdapter = GridDrillDownAdapter()

    companion object {
        const val ARG_PREV_UA_ID_SELECTED = "PREV_UA_SEGMENT_ID_SELECTED"
        const val UA_SEGMENT_ID_SELECTED = "uaSegmentIdSelected"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val prevUASelected = it.getString(UA_SEGMENT_ID_SELECTED).orEmpty()
            headerDetailKpiPresenter.setUaSegmentSelected(prevUASelected)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        headerDetailKpiPresenter.create(this)
        detailContentKpiPresenter.create(this)
        headerDetailKpiPresenter.initViewData()
        headerDetailKpiPresenter.handleIndicatorViews()

        detailContentKpiPresenter.showUaSegmentsView()
        detailContentKpiPresenter.setListener()
        detailContentKpiPresenter.handleViews()
        setupSwipeToRefresh()
        initEvents()
    }

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    override fun onDestroy() {
        super.onDestroy()
        headerDetailKpiPresenter.destroy()
        detailContentKpiPresenter.destroy()
    }

    override fun onResume() {
        super.onResume()
        activity?.registerReceiver(
            startLoadingReceiver,
            IntentFilter(Constants.START_LOADING_ACTION)
        )
    }

    override fun onPause() {
        super.onPause()
        activity?.unregisterReceiver(startLoadingReceiver)
    }

    override fun drawHeaderDetailKpiView() {
        drawBrightPathDetailKpiView()
    }

    override fun showCampaign(campaignText: String) {
        tvCampaignInfo?.text = campaignText
    }

    override fun addConsultantsDrillDown() {
        drawBrightPathDetailKpiView()
    }

    override fun drawUAListView() {
        uaSegmentsFragment = UASegmentsFragment.newInstance(
            headerDetailKpiPresenter.getUaSegmentSelected(),
            false
        )
        commitFragment(uaSegmentsFragment, frameUASegment.id)
    }

    override fun setupGridView(items: List<ConsultantHeaderDetailedKpiModel>) {
        gridDrillDownAdapter.let {
            it.addItems(items)
            it.setUaSegmentId(headerDetailKpiPresenter.getUaSegmentSelected())
            it.onClickListener = { model ->
                navToDetailedConsultantList(
                    bundleOf(
                        ARG_CONSULTANT_LIST_HEADER_MODEL to model,
                        ARG_PREV_UA_ID_SELECTED to headerDetailKpiPresenter.getUaSegmentSelected()
                    )
                )
            }

            it.onClickLevelListener = { id ->
                val extras = Bundle().apply {
                    putString(
                        ARG_PREV_UA_ID_SELECTED,
                        headerDetailKpiPresenter.getUaSegmentSelected()
                    )
                }

                if (id == Constants.ID_END_PERIOD) {
                    extras.putInt(
                        BrightPathIndicatorDetailFragment.ARG_BEAUTY_CONSULTANT_TYPE_LIST,
                        4
                    )
                }

                navToBrightPathIndicator(extras)
            }

            if (gridViewSection?.adapter == null) {
                context?.let { context ->
                    gridViewSection?.apply {
                        setHasFixedSize(true)
                        layoutManager = GridLayoutManager(context, NUMERO_DOS)
                        addItemDecoration(
                            ItemOffsetDecoration(
                                context,
                                R.dimen.activity_indicator_margin
                            )
                        )
                        adapter = it
                    }
                }
            }
        }
    }

    override fun setListenerForGz() {
        uaSegmentsFragment.setViewListener(this)
    }

    override fun onSegmentSelected(segmentSelected: UASegmentModel?) {
        segmentSelected?.apply {
            headerDetailKpiPresenter.setUaSegmentSelected(segmentID)
            detailContentKpiPresenter.getDrillDownData()
        }
    }

    private fun setupSwipeToRefresh() {
        swipeToRefresh?.setIndicatorColor(R.color.indicator_bright_path)
    }

    override fun showLoading() {
        swipeToRefresh?.isRefreshing = true
    }

    override fun hideLoading() {
        swipeToRefresh?.isRefreshing = false
        activity?.sendBroadcast(Intent(Constants.START_LOADING_ACTION))
    }

    override fun showErrorMessaje() {
        Toast.makeText(
            activity,
            resources.getString(R.string.error_message_no_data),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun drawBrightPathDetailKpiView() {
        commitFragment(
            BrightPathHeaderKpiFragment.newInstance(),
            headerDetailView.id
        )
    }

    private fun commitFragment(frag: Fragment, layoutId: Int) {
        childFragmentManager.beginTransaction()
            .replace(layoutId, frag, frag.javaClass.name)
            .commit()
    }

    private fun navToDetailedConsultantList(bundle: Bundle) {
        navigateTo(
            BaseR.id.action_fragBrightPathIndicatorDetail_to_fragConsultantListDetailedKpi,
            bundle
        )
    }

    private fun navToBrightPathIndicator(bundle: Bundle) {
        navigateTo(
            BaseR.id.action_fragBrightPathIndicatorDetail_to_fragBrightPathKpiDetail,
            bundle
        )
    }

    private fun initEvents() {
        actionBar(toolbar as MaterialToolbar) {
            setNavigationIcon(BaseR.drawable.ic_backspace)
            navigationIcon?.tinted(getColor(BaseR.color.white))
            setNavigationOnClickListener {
                closeDialog()
            }
        }

        swipeToRefresh.setOnSafeRefreshListener {
            headerDetailKpiPresenter.syncConsultants()
        }
    }

    private val startLoadingReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            drawBrightPathDetailKpiView()
        }
    }
}

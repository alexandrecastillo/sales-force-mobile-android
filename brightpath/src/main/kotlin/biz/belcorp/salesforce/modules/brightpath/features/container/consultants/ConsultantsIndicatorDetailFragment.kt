package biz.belcorp.salesforce.modules.brightpath.features.container.consultants

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.tinted
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.features.consultants.OnConsultantsListener
import biz.belcorp.salesforce.core.include.Include
import biz.belcorp.salesforce.core.include.IncludeManager
import biz.belcorp.salesforce.core.utils.actionBar
import biz.belcorp.salesforce.core.utils.inject
import biz.belcorp.salesforce.core.utils.orZero
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.brightpath.R
import biz.belcorp.salesforce.modules.brightpath.features.container.consultants.header.ConsultantsHeaderDetailView
import biz.belcorp.salesforce.modules.brightpath.features.drilldown.model.ConsultantHeaderDetailedKpiModel
import biz.belcorp.salesforce.modules.brightpath.features.ua.OnUASegmentSelectedListener
import biz.belcorp.salesforce.modules.brightpath.features.ua.UASegmentModel
import biz.belcorp.salesforce.modules.brightpath.features.ua.UASegmentsFragment
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_detail_list_consultants.*
import kotlinx.android.synthetic.main.view_top_header_indicator_info.*
import kotlinx.android.synthetic.main.view_top_header_indicator_info.toolbar
import biz.belcorp.salesforce.base.R as BaseR


class ConsultantsIndicatorDetailFragment :
    BaseDialogFragment(),
    ConsultantsDetailView,
    ConsultantsHeaderDetailView,
    OnUASegmentSelectedListener {

    private val includeManager by inject<IncludeManager>()

    private val presenter: ConsultantsDetailPresenter by inject()

    private lateinit var consultantListFrag: Fragment
    private lateinit var uaSegmentsFrag: UASegmentsFragment

    companion object {
        const val ARG_CONSULTANT_LIST_HEADER_MODEL = "ARG_CONSULTANT_LIST_HEADER_MODEL"
        const val ARG_PREV_UA_ID_SELECTED = "PREV_UA_SEGMENT_ID_SELECTED"
        const val ARG_CONSULTANT_ID_LIST_SELECTED = "ARG_CONSULTANT_ID_LIST_SELECTED"
    }


    override fun getLayout() = R.layout.fragment_detail_list_consultants

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            presenter.prevUaSelected = it.getString(ARG_PREV_UA_ID_SELECTED).orEmpty()
            it.getParcelable<ConsultantHeaderDetailedKpiModel>(ARG_CONSULTANT_LIST_HEADER_MODEL)
                ?.apply {
                    consultantQuantity = consultantQuantity.orZero()
                    presenter.model = this
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.create(this, this)
        presenter.paintHeaderDescription()
        initEvents()
    }

    override fun onSegmentSelected(segmentSelected: UASegmentModel?) {
        segmentSelected?.segmentID?.let {
            presenter.prevUaSelected = it
            (consultantListFrag as OnConsultantsListener).getConsultants(it)
        }
    }

    override fun showUaSegmentListPrevUaSelected() {
        childFragmentManager.beginTransaction()
            .add(
                R.id.uaSegmentsView,
                uaSegmentsFrag,
                UASegmentsFragment().javaClass.name
            )
            .commit()
    }

    override fun showCampaignInfo(campaignInfo: String) {
        tvCampaignInfo?.text = campaignInfo
    }

    override fun showTitle(title: String) {
        tvIndicatorName?.text = title
    }

    override fun showConsultantsInf(consultantsAmountInf: String) {
        tvGzConsultantAmount?.text = consultantsAmountInf
    }

    override fun showDescriptionInf(descriptionInf: String) {
        tvGzConsultantDetail?.text = descriptionInf
    }

    private fun showConsultantList() {
        childFragmentManager.beginTransaction()
            .add(
                R.id.frameConsultantsList,
                consultantListFrag,
                consultantListFrag.javaClass.name
            )
            .commit()
    }

    override fun defineSeFragment() {
        consultantsListViewInstance()
        showConsultantList()
    }

    override fun defineGzFragment() {
        uaSegmentsViewInstance()
        consultantsListViewInstance()
        showConsultantList()
    }

    private fun consultantsListViewInstance() {
        val consultantIdList = presenter.model?.id ?: 0

        consultantListFrag = includeManager
            .getInclude(Include.LEGACY_CONSULTANT_LIST)
            .withArguments(
                ARG_CONSULTANT_ID_LIST_SELECTED to consultantIdList,
                ARG_PREV_UA_ID_SELECTED to presenter.prevUaSelected
            )
    }

    private fun uaSegmentsViewInstance() {
        uaSegmentsFrag = UASegmentsFragment.newInstance(
            presenter.prevUaSelected,
            false
        )

        uaSegmentsFrag.setViewListener(this)
    }

    private fun initEvents() {
        actionBar(toolbar as MaterialToolbar) {
            setNavigationIcon(BaseR.drawable.ic_backspace)
            navigationIcon?.tinted(getColor(BaseR.color.white))
            setNavigationOnClickListener {
                closeDialog()
            }
        }
    }

}

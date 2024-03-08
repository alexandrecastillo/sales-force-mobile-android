package biz.belcorp.salesforce.modules.consultants.features.searchunified

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import biz.belcorp.mobile.components.core.extensions.getColor
import biz.belcorp.mobile.components.core.extensions.tinted
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.mobile.components.design.selector.bar.model.SelectorModel
import biz.belcorp.mobile.components.design.selector.bar.view.SelectorBar
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.HYPHEN
import biz.belcorp.salesforce.core.constants.HASH_UA_KEY
import biz.belcorp.salesforce.core.constants.SourceType
import biz.belcorp.salesforce.core.domain.entities.consultants.ConsultantFilter
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.events.LiveDataBus
import biz.belcorp.salesforce.core.features.uainfo.UaInfoModel
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.consultants.R
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.view.SearchConsultantFragment
import biz.belcorp.salesforce.modules.consultants.features.filters.utils.TabsCreator
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_search_consultant_dialog.*
import org.koin.android.viewmodel.ext.android.viewModel

class SearchConsultantDialogFragment : BaseDialogFragment(), SelectorBar.SelectorBarListener {

    private val titleToolbar by lazy {
        arguments?.getString(HASH_TITLE, EMPTY_STRING) ?: EMPTY_STRING
    }
    private val sourceType by lazy {
        arguments?.getInt(HASH_SOURCE_TYPE, SourceType.NONE) ?: SourceType.NONE
    }
    private var uaKey = LlaveUA()

    private val viewModel by viewModel<ConsultantViewModel>()

    override fun getLayout() = R.layout.fragment_search_consultant_dialog

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupArguments()
        setupViewModel()
        setupFragment()
    }

    private fun setupUI() {
        setupToolbar()
        setupTabs()
    }

    private fun setupArguments() {
        updateUaKey(arguments?.getSerializable(HASH_UA_KEY) as LlaveUA)
    }

    private fun setupToolbar() {
        actionBar(toolbar as MaterialToolbar) {
            title = titleToolbar
            setNavigationIcon(biz.belcorp.mobile.components.design.R.drawable.ic_backspace)
            navigationIcon?.tinted(getColor(biz.belcorp.mobile.components.design.R.color.white))
            setNavigationOnClickListener { closeDialog() }
        }
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        viewModel.getSelectorUas()
    }

    private fun setupTabs() {
        TabsCreator.with(tabs).create(sourceType)
        tabs?.onTabSelected { publish(uaKey, tabs.getSelectedTab()?.tag as String) }
    }

    private fun updateSelector(items: List<SelectorModel>) {
        if (items.isEmpty()) return
        selector?.apply {
            visible(items.isNotEmpty())
            val model = items.first().apply { isSelected = true }.model as UaInfoModel
            dataSet = items
            addOnItemSelectorBarListener(this@SearchConsultantDialogFragment)
            updateSelectorDescription(model)
            updateUaKey(model.uaKey)
        }
    }

    private fun updateSelectorDescription(model: UaInfoModel) {
        groupDescription?.visible()
        tvTitleSelector?.text = getString(R.string.text_consultant_title)
        tvDescriptionSelector?.text = if (model.isCovered) {
            spannable {
                bold(span("${getString(R.string.consultant_section_hint)} ${model.label} ")) +
                        span(HYPHEN) + space() + span(model.userKpiInformation)
            }
        } else color(span(model.userKpiInformation), getCompatColor(model.userInformationColor))
    }

    private fun setupFragment() {
        val fragment = SearchConsultantFragment.newInstance()
        childFragmentManager.beginTransaction()
            .replaceOnce(
                R.id.frameSearchConsultant,
                fragment.javaClass.simpleName,
                childFragmentManager
            ) { fragment }.commit()
    }

    override fun onSelected(model: Any?) {
        model?.let {
            if (model is UaInfoModel) {
                updateSelectorDescription(model)
                updateUaKey(model.uaKey)
                publish(uaKey, tabs.getSelectedTab()?.tag as String)
            }
        }
    }

    private fun updateUaKey(uaKey: LlaveUA) {
        this.uaKey = uaKey
    }

    private fun publish(uaKey: LlaveUA, filter: String) {
        LiveDataBus.publish(
            EventSubject.CONSULTANT_FILTERS,
            ConsultantFilter(sourceType, uaKey, listOf(filter))
        )
    }

    private val viewStateObserver = Observer<SearchConsultantViewState> {
        when (it) {
            is SearchConsultantViewState.Success -> {
                updateSelector(it.uas)
                publish(uaKey, tabs.getSelectedTab()?.tag as String)
            }
            is SearchConsultantViewState.Failure -> Unit
        }
    }

    companion object {
        const val HASH_TITLE = "TITLE"
        const val HASH_SOURCE_TYPE = "SOURCE_TYPE"

        fun newInstance(uaKey: LlaveUA, title: String, sourceType: Int) =
            SearchConsultantDialogFragment().withArguments(
                HASH_UA_KEY to uaKey,
                HASH_TITLE to title,
                HASH_SOURCE_TYPE to sourceType
            )
    }
}

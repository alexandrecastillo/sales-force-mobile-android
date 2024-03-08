package biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.view

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.mobile.components.core.extensions.gone
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.mobile.components.design.searchbar.SearchBar
import biz.belcorp.mobile.components.dialogs.filter.FilterDialog
import biz.belcorp.mobile.components.dialogs.filter.model.Filter
import biz.belcorp.mobile.components.dialogs.filter.model.group.GroupModel
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.SourceType
import biz.belcorp.salesforce.core.domain.entities.consultants.ConsultantFilter
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.events.EventSubject.CONSULTANTS_SYNC
import biz.belcorp.salesforce.core.events.EventSubject.CONSULTANT_FILTERS
import biz.belcorp.salesforce.core.events.sync.SyncState
import biz.belcorp.salesforce.core.events.utils.consumableObserver
import biz.belcorp.salesforce.core.events.utils.observeEventSubject
import biz.belcorp.salesforce.core.features.utils.PERSON_IDENTIFIER_KEY
import biz.belcorp.salesforce.core.include.Include
import biz.belcorp.salesforce.core.include.IncludeManager
import biz.belcorp.salesforce.core.utils.showOnce
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.consultants.R
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.adapter.SearchConsultantAdapter
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.ConsultantContainerModel
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.model.ConsultantModel
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.view.filter.FilterDialogViewModel
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.view.filter.FilterDialogViewState
import kotlinx.android.synthetic.main.fragment_search_consultant.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class SearchConsultantFragment : BaseFragment(), SearchBar.OnTextChanged,
    SearchBar.OnClearSelected {

    private val includeManager by inject<IncludeManager>()
    private val viewModel by viewModel<SearchConsultantViewModel>()
    private val filterDialogViewModel by viewModel<FilterDialogViewModel>()

    private val consultantAdapter by lazy { SearchConsultantAdapter() }
    private var uaKey = LlaveUA()
    private var filter: ConsultantFilter? = null
    private var sourceType = SourceType.NONE
    private var flagMto: Int= Constant.ONE_NEGATIVE

    override fun getLayout() = R.layout.fragment_search_consultant

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isAdded) return
        setup()
        createObserver()
        setupLiveObservers()
    }

    private fun createObserver() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateSearchConsultant)
        filterDialogViewModel.viewState.observe(viewLifecycleOwner, viewStateFilterDialog)
    }

    private fun setupLiveObservers() {
        observeEventSubject(CONSULTANTS_SYNC, observer = syncStateObserver)
        observeEventSubject(CONSULTANT_FILTERS, observer = filterObserver)
    }

    private val filterObserver = consumableObserver<ConsultantFilter> {
        this.filter = it
        this.sourceType = it.type
        this.uaKey = it.uaKey
        applyFilters()
    }

    private val syncStateObserver = consumableObserver<SyncState.Updated> {
        applyFilters()
    }

    private val viewStateSearchConsultant = Observer<SearchConsultantViewState> {
        when (it) {
            is SearchConsultantViewState.Success -> showSuccessViews(it.model)
            is SearchConsultantViewState.Empty -> showEmptyDataViews(it.model)
            is SearchConsultantViewState.SuccessSearch -> showSuccessSearchView(it.consultants)
            is SearchConsultantViewState.EmptySearch -> showEmptySearchView()
            is SearchConsultantViewState.Failure -> showFailureViews()
            is SearchConsultantViewState.HideLoading -> hideLoading()
        }
    }

    private val viewStateFilterDialog = Observer<FilterDialogViewState> {
        when (it) {
            is FilterDialogViewState.Success -> openFilters(it.title, it.filters)
            is FilterDialogViewState.Request -> viewModel.getConsultants(it.consultantFilter)
        }
    }

    private fun applyFilters() {
        filter?.also {
            viewModel.getConsultants(it)
            filterDialogViewModel.updateDefaultFilter(it.filters)
        }
    }

    private fun showSuccessViews(model: ConsultantContainerModel) {
        flagMto = model.flagMto

        searchBar?.text = Constant.EMPTY_STRING
        tvEmptyData?.gone()
        ivEmptyData?.gone()
        searchBar?.visible(model.showSearchBar)
        btnFilters?.visible(model.showFilter)
        clFilters?.visible(model.showFilter || model.showSummary)
        tvTotalSearch?.visible(model.showSummary)
        rvConsultants?.visible(model.hasConsultants)
        updateConsultants(model.consultants)
        setTotalSearchLabel(model.consultants.size)
    }

    private fun setTotalSearchLabel(totalConsultants: Int) {
        tvTotalSearch?.apply {
            text = totalConsultants.let {
                resources.getQuantityString(R.plurals.text_total_consultants, it, it)
            }
        }
    }

    private fun showEmptyDataViews(model: ConsultantContainerModel) {
        searchBar?.visible(model.showFilter)
        clFilters?.visible(model.showFilter)
        rvConsultants?.gone()
        tvTotalSearch?.gone()
        tvEmptyData?.visible()
        ivEmptyData?.visible()
        updateConsultants(emptyList())
    }

    private fun showEmptySearchView() {
        searchBar?.visible()
        clFilters?.gone()
        rvConsultants?.gone()
        tvEmptyData?.visible()
        ivEmptyData?.visible()
    }

    private fun updateConsultants(consultants: List<ConsultantModel>) {
        consultantAdapter.setFlagMto(flagMto)
        consultantAdapter.submitList(consultants)
    }

    private fun showSuccessSearchView(consultants: List<ConsultantModel>) {
        updateConsultants(consultants)
        clFilters?.visible()
        rvConsultants?.visible()
        ivEmptyData?.gone()
        tvEmptyData?.gone()
        setTotalSearchLabel(consultants.size)
    }

    private fun showFailureViews() {
        clFilters?.gone()
        tvEmptyData?.visible()
        ivEmptyData?.visible()
        searchBar?.gone()
        tvTotalSearch?.gone()
        rvConsultants?.gone()
        loading?.gone()
    }

    private fun hideLoading() {
        loading?.gone()
    }

    private fun setup() {
        setupRecyclerView()
        setupConsultantList()
        setupSearchBar()
    }

    private fun setupRecyclerView() {
        consultantAdapter.setOnClickListener { personIdentifier ->
            if (!isAdded) return@setOnClickListener
            val profileFragment = getProfileFragment(personIdentifier)
            profileFragment?.showOnce(childFragmentManager)
        }
    }

    private fun setupConsultantList() {
        rvConsultants?.apply {
            itemAnimator = null
            layoutManager = LinearLayoutManager(requireContext())
            adapter = consultantAdapter
        }
    }

    private fun setupSearchBar() {
        searchBar?.apply {
            onClearSelected(this@SearchConsultantFragment)
            onTextChanged(this@SearchConsultantFragment)
        }
    }

    private fun openFilters(title: String, filters: List<GroupModel>) {
        btnFilters?.setOnClickListener {
            FilterDialog.Builder(requireContext())
                .setTitle(title)
                .withCountSelected(true)
                .withData(filters)
                .withCallback(object : FilterDialog.FilterListener {
                    override fun onApply(dialog: DialogInterface?, filtered: List<GroupModel>) {
                        filterDialogViewModel.updateFilters(sourceType, uaKey, filtered)
                        dialog?.dismiss()
                    }

                    override fun onItemChanged(item: Filter?, isChecked: Boolean) = Unit
                    override fun onClean() = Unit
                    override fun onClose(dialog: DialogInterface?) {
                        dialog?.dismiss()
                    }
                })
                .show()
        }
    }

    private fun getProfileFragment(personIdentifier: PersonIdentifier): BaseDialogFragment? {
        return includeManager.getInclude(Include.PROFILE)
            .withArguments(
                PERSON_IDENTIFIER_KEY to personIdentifier
            ) as? BaseDialogFragment
    }

    override fun onClearSelected() = Unit

    override fun onTextChanged(text: String) {
        viewModel.searchConsultants(text)
    }

    companion object {
        fun newInstance(): Fragment = SearchConsultantFragment()
    }
}

package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.shared.postulants

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import biz.belcorp.salesforce.components.utils.LineDividerDecoration
import biz.belcorp.salesforce.components.utils.decoration.ItemOffsetDecoration
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.base.FragmentParameters
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.model.PostulantKpiModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.uadetail.UaDetailViewState
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.adapter.grid.KpiGridAdapter
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.KpiFragmentParameters
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.viewmodel.UaDetailViewModel
import kotlinx.android.synthetic.main.fragment_postulant_kpi.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import biz.belcorp.salesforce.components.R as ComponentR

class PostulantsKpiFragment : BaseFragment() {

    private val params by lazy {
        arguments?.getSerializable(FragmentParameters.key) as KpiFragmentParameters
    }

    private val viewModel by viewModel<PostulantKpiViewModel>()
    private val postulantKpiGridAdapter by lazy { KpiGridAdapter() }

    override fun getLayout() = R.layout.fragment_postulant_kpi

    private val uaViewModel by sharedViewModel<UaDetailViewModel>()

    private val observerDataResponse =
        Observer<PostulantKpiViewState> { state ->
            when (state) {
                is PostulantKpiViewState.Loading -> doOnLoading()
                is PostulantKpiViewState.Success -> doOnSuccess(state.data)
                is PostulantKpiViewState.Failed -> doOnFailed()
            }
        }

    private val viewUaStateObserver = Observer<UaDetailViewState> { state ->
        when (state) {
            is UaDetailViewState.UpdateItem -> syncData(state.uaKey)
            is UaDetailViewState.Failure -> toast(state.message)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPostulantRecycler()
        setupViewModel()
        setupObservers()
    }

    private fun setupObservers() {
        if (!params.isParent) {
            uaViewModel.uaViewState.observe(viewLifecycleOwner, viewUaStateObserver)
        }
        syncData(params.uaKey)
    }

    private fun syncData(ua: LlaveUA) {
        viewModel.getData(ua)
    }

    private fun setupViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, observerDataResponse)
    }

    private fun doOnLoading() = Unit

    private fun doOnSuccess(model: PostulantKpiModel) {
        model.apply {
            rvPostulantsKpiValues?.visible()
            tvManageYourCapLabel?.visible()
            tvManageYourCapLabel?.text = capitalizationLabel

            postulantKpiGridAdapter.update(kpiValues)
        }
    }

    private fun doOnFailed() = Unit

    private fun setupPostulantRecycler() {
        rvPostulantsKpiValues?.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(
                requireContext(),
                Constant.NUMBER_TWO,
                GridLayoutManager.VERTICAL,
                false
            )
            addItemDecoration(
                ItemOffsetDecoration(
                    resources.getDimensionPixelOffset(ComponentR.dimen.content_inset_small)
                )
            )
            addItemDecoration(
                LineDividerDecoration(
                    context,
                    biz.belcorp.salesforce.base.R.drawable.divider_newcycle,
                    biz.belcorp.salesforce.components.R.dimen.content_inset_small
                )
            )
            adapter = postulantKpiGridAdapter
        }
    }

    companion object {
        val TAG = PostulantsKpiFragment::javaClass.javaClass.simpleName

        fun newInstance() = PostulantsKpiFragment()
    }

}

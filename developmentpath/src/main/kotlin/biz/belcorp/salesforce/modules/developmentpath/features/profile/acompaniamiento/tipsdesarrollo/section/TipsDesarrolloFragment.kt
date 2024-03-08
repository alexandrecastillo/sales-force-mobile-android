package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.section

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.mobile.components.core.extensions.gone
import biz.belcorp.salesforce.components.utils.decoration.BoxOffsetDecoration
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.sharedViewModel
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.PerfilFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.ProfileViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.ProfileViewState
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.header.DetalleTipsDesarrolloFragment
import kotlinx.android.synthetic.main.fragment_tips_desarrollo.*
import org.koin.android.viewmodel.ext.android.viewModel
import biz.belcorp.salesforce.base.R as BaseR

class TipsDesarrolloFragment : BaseFragment() {

    private val personIdentifier by lazyPersonIdentifier()
    private val viewModel by viewModel<TipsDesarrolloViewModel>()
    private val profileViewModel by sharedViewModel<ProfileViewModel, PerfilFragment>()

    private val adapter by lazy { TipsDesarrolloAdapter(::onClickTip) }

    override fun getLayout(): Int = R.layout.fragment_tips_desarrollo

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarRecycler()
        setupViewModel()
    }

    private fun setupViewModel() {
        profileViewModel?.viewState?.observe(viewLifecycleOwner, profileViewStateObserver)
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        getTips()
    }

    private val profileViewStateObserver = Observer<ProfileViewState> {
        when (it) {
            is ProfileViewState.SuccessSync -> getTips()
        }
    }

    private val viewStateObserver = Observer<TipsDesarrolloViewState> {
        when (it) {
            is TipsDesarrolloViewState.ShowTips -> showTips(it.tips)
            is TipsDesarrolloViewState.ShowEmptyView -> showEmptyView()
        }
    }

    private fun getTips() {
        viewModel.obtener(personIdentifier)
    }

    private fun configurarRecycler() {
        rvTipsDesarrollo?.setHasFixedSize(true)
        rvTipsDesarrollo?.layoutManager = LinearLayoutManager(context)
        rvTipsDesarrollo?.addItemDecoration(
            BoxOffsetDecoration(
                requireContext(),
                BaseR.dimen.ds_margin_medium,
                BaseR.dimen.zero
            )
        )
        rvTipsDesarrollo?.adapter = adapter
    }

    private fun onClickTip(model: TipDesarrolloModel) {
        val detalleTipsDesarrolloFragment = DetalleTipsDesarrolloFragment
            .newInstance(personIdentifier)
            .apply { elemetoSeleccionado = model }
        detalleTipsDesarrolloFragment.show(childFragmentManager, detalleTipsDesarrolloFragment.tag)
    }

    private fun showTips(tips: List<TipDesarrolloModel>) {
        tvEmpty?.gone()
        rvTipsDesarrollo?.visible()
        adapter.actualizar(tips)
    }

    private fun showEmptyView() {
        tvEmpty?.visible()
        rvTipsDesarrollo?.gone()
    }

    companion object {

        fun newInstance(personIdentifier: PersonIdentifier): TipsDesarrolloFragment {
            return TipsDesarrolloFragment()
                .withPersonIdentifier(personIdentifier)
        }
    }
}

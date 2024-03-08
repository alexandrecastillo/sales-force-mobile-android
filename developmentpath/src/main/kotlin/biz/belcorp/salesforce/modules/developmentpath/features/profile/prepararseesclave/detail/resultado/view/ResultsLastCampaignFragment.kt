package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.resultado.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.dimen
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.resultado.model.ResultModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.resultado.viewmodel.ResultsLastCampaignViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.resultado.viewmodel.ResultsLastCampaignViewState
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.EtiquetaInfoAdapter
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.EtiquetaInfoDecoration
import biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta.GridManagerBuilder
import kotlinx.android.synthetic.main.fragment_resultado_cx.*
import org.koin.android.viewmodel.ext.android.viewModel
import biz.belcorp.salesforce.base.R as BaseR

class ResultsLastCampaignFragment : BaseFragment() {

    private val viewModel by viewModel<ResultsLastCampaignViewModel>()
    private val personIdentifier by lazyPersonIdentifier()

    override fun getLayout(): Int = R.layout.fragment_resultado_cx

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentResultados?.gone()
        configureRecyclerViewGoalReal()
        configureRecyclerViewResults()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.getResults(personIdentifier)
        viewModel.viewState.observe(viewLifecycleOwner, resultsViewState)
    }

    private fun configureRecyclerViewGoalReal() {
        recyclerMetaReal?.apply {
            isNestedScrollingEnabled = false
            addItemDecoration(EtiquetaInfoDecoration(dimen(BaseR.dimen.content_inset_normal)))
            adapter = EtiquetaInfoAdapter()
        }
    }

    private fun configureRecyclerViewResults() {
        recyclerResultadosCx?.apply {
            isNestedScrollingEnabled = false
            addItemDecoration(EtiquetaInfoDecoration(dimen(BaseR.dimen.content_inset_normal)))
            adapter = EtiquetaInfoAdapter()
        }
    }

    private fun updateUI(model: ResultModel): Unit = with(model) {
        txtMetaVsReal.text = getString(R.string.resultado_meta_real, campaign)
        txtResultadoCx.text = getString(R.string.resultado_cx, campaign)

        recyclerMetaReal?.layoutManager = GridManagerBuilder.buildForContainer(context, goalReal)

        recyclerResultadosCx?.layoutManager = GridManagerBuilder.buildForContainer(context, results)

        (recyclerMetaReal?.adapter as EtiquetaInfoAdapter).actualizar(goalReal.grupos)
        (recyclerResultadosCx?.adapter as EtiquetaInfoAdapter).actualizar(results.grupos)

        contentResultados?.visible()
    }

    private val resultsViewState = Observer<ResultsLastCampaignViewState> {
        when (it) {
            is ResultsLastCampaignViewState.Success -> updateUI(it.model)
            is ResultsLastCampaignViewState.Failure -> Unit
        }
    }

    companion object {
        fun newInstance(personIdentifier: PersonIdentifier): ResultsLastCampaignFragment {
            return ResultsLastCampaignFragment()
                .withPersonIdentifier(personIdentifier)
        }
    }
}

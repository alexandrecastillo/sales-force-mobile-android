package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.mobile.components.core.extensions.gone
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.adapter.DigitalParentAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.model.DigitalSaleModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.model.DigitalSaleViewState
import kotlinx.android.synthetic.main.fragment_digital.clContenedorLabelActivas
import kotlinx.android.synthetic.main.fragment_digital.recycler
import kotlinx.android.synthetic.main.fragment_digital.tvEmpty
import kotlinx.android.synthetic.main.fragment_digital.txtSinDataDigital
import kotlinx.android.synthetic.main.fragment_digital.txtTituloActivas
import org.koin.android.viewmodel.ext.android.viewModel

class DigitalFragment : BaseFragment() {

    private val viewModel by viewModel<DigitalSaleViewModel>()
    private val analyticsPresenter by injectFragment<FirebaseAnalyticsPresenter>()
    private val personIdentifier by lazyPersonIdentifier()

    private val adapter by lazy { DigitalParentAdapter() }

    override fun getLayout(): Int = R.layout.fragment_digital

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
    }

    fun mostrarData(data: List<DigitalSaleModel>) {
        if (!isAttached()) return
        if (!establecerVisibilidadDigital(data)) return
        tvEmpty?.gone()
        recycler?.visible()
        (recycler?.adapter as? DigitalParentAdapter)?.actualizar(data)
    }

    private fun pintarTituloActivas(titulo: String) {
        if (!isAttached()) return
        txtTituloActivas?.text = titulo
        if (titulo.isNotEmpty()) clContenedorLabelActivas?.visibility = View.VISIBLE
    }

    private fun showEmptyView() {
        tvEmpty?.visible()
        recycler?.gone()
    }

    private fun inicializar() {
        configurarRecyclerView()
        initViewModel()
        inicializarPresenters()
    }

    private val viewStateObserver = Observer { state: DigitalSaleViewState ->
        when (state) {
            is DigitalSaleViewState.Success -> {
                with(state.container) {
                    mostrarData(list)
                    pintarTituloActivas(activesTitle)
                }
            }

            else -> showEmptyView()
        }
    }

    private fun initViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
        viewModel.getDigitalSaleByRole(personIdentifier)
    }

    private fun configurarRecyclerView() {
        val context = context ?: return
        recycler?.apply {
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = this@DigitalFragment.adapter
        }
    }

    private fun inicializarPresenters() {
        analyticsPresenter.enviarPantallaDigital(
            TagAnalytics.PREPARARSE_ES_CLAVE_DIGITAL,
            personIdentifier.role,
            personIdentifier.id
        )
    }

    private fun establecerVisibilidadDigital(data: List<DigitalSaleModel>): Boolean {
        if (data.isNullOrEmpty()) {
            recycler?.visibility = View.GONE
            txtSinDataDigital?.visibility = View.VISIBLE
            return false
        }
        recycler?.visibility = View.VISIBLE
        txtSinDataDigital?.visibility = View.GONE
        return true
    }

    companion object {
        val TAG = DigitalFragment::class.java.simpleName

        fun newInstance(personIdentifier: PersonIdentifier): DigitalFragment {
            return DigitalFragment().withPersonIdentifier(personIdentifier)
        }
    }
}

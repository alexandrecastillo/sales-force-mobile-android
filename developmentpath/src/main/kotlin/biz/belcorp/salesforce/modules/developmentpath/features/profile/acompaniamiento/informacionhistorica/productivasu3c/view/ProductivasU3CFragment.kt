package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.productivasu3c.view

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.NonScrollableLayoutManager
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.productivasu3c.model.ZonaProductivaModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.informacionhistorica.productivasu3c.presenter.ProductivasU3CPresenter
import kotlinx.android.synthetic.main.fragment_productivas_u3c_region.*

class ProductivasU3CFragment : BaseFragment(), ProductivasU3CView {

    private val personaId: Long by lazy {
        arguments?.getLong(ARG_PERSONA_ID, -1L) ?: -1L
    }

    private val presenter by injectFragment<ProductivasU3CPresenter>()

    override fun getLayout() = R.layout.fragment_productivas_u3c_region

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarRecyclerView()
        recuperarInformacion()
    }

    private fun configurarRecyclerView() {
        recyclerZonas?.layoutManager = NonScrollableLayoutManager()
            .withContext(context)
            .linearVertical()
        recyclerZonas?.adapter = ProductivasU3CAdapter()
    }

    private fun recuperarInformacion() {
        presenter.recuperarInformacion(personaId)
    }

    override fun pintarZonas(zonas: List<ZonaProductivaModel>) {
        (recyclerZonas?.adapter as? ProductivasU3CAdapter)?.actualizar(zonas)
    }

    companion object {
        private const val ARG_PERSONA_ID = "PERSONA_ID"

        fun newInstance(personaId: Long) = ProductivasU3CFragment()
            .withArguments(ARG_PERSONA_ID to personaId)
    }
}

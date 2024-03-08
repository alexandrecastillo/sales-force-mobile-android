package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.section

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.headercontainer.DetallePrepararseEsClaveFragment
import kotlinx.android.synthetic.main.fragment_prepararse_es_clave.*

class PrepararseEsClaveFragment : BaseFragment(),
    PrepararseEsClaveContract.View, PrepararseEsClaveAdapter.SelectableCallback {

    private val personIdentifier by lazyPersonIdentifier()
    val presenter by injectFragment<PrepararseEsClaveContract.Presenter>()

    private val adapter by lazy {
        PrepararseEsClaveAdapter(
            R.layout.item_prepararse_es_clave,
            PrepararseEsClaveViewHolder.Type.GENERAL
        ).apply { listener = this@PrepararseEsClaveFragment }
    }

    override fun getLayout(): Int = R.layout.fragment_prepararse_es_clave

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
    }

    override fun mostrartDescripcion(descripcion: String) {
        header?.hitDescription =
            String.format(getString(R.string.summary_descripcion_por_rol), descripcion)
    }

    override fun onSelectePrepararseEsClaveItem(
        item: PrepararseEsClaveModel,
        type: PrepararseEsClaveViewHolder.Type,
        posicion: Int
    ) {
        if (!isAttached()) return
        when (type) {
            PrepararseEsClaveViewHolder.Type.GENERAL -> {
                val detallePrepararseEsClaveFragment =
                    DetallePrepararseEsClaveFragment.newInstance(personIdentifier)
                        .apply { elemetoSeleccionado = item }
                detallePrepararseEsClaveFragment.show(
                    childFragmentManager,
                    detallePrepararseEsClaveFragment.tag
                )
            }
            PrepararseEsClaveViewHolder.Type.DETALLE -> Unit
        }
    }

    override fun mostrarData(data: List<PrepararseEsClaveModel>) {
        (recycler?.adapter as? PrepararseEsClaveAdapter)?.actualizar(data)
    }

    private fun inicializar() {
        configurarRecyclerView()
        presenter.obtener(personIdentifier.id, personIdentifier.role)
        presenter.pintarDescripcionPorRol(personIdentifier.role)
    }

    private fun configurarRecyclerView() {
        recycler?.setHasFixedSize(true)
        recycler?.layoutManager = GridLayoutManager(context, 2)
        recycler?.adapter = adapter
    }

    companion object {
        fun newInstance(personIdentifier: PersonIdentifier): PrepararseEsClaveFragment {
            return PrepararseEsClaveFragment().withPersonIdentifier(personIdentifier)
        }
    }
}

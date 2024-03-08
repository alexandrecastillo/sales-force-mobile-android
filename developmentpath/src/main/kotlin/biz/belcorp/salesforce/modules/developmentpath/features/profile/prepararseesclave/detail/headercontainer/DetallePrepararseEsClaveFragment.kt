package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.headercontainer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.base.BaseToolbarDialogFragment
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.utils.lazyPersonIdentifier
import biz.belcorp.salesforce.core.features.utils.withPersonIdentifier
import biz.belcorp.salesforce.core.utils.dimen
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.replaceOnce
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclave.PrepararseEsClave
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.AcuerdosUltimasCampaniasFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.DigitalFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.container.MasVendidoContenedorFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.container.NegocioContenedorFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.resultado.view.ResultsLastCampaignFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.view.SalesFragment
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.section.PrepararseEsClaveAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.section.PrepararseEsClaveModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.section.PrepararseEsClaveViewHolder
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_prepararse_es_clave_detail.*
import biz.belcorp.salesforce.base.R as BaseR

class DetallePrepararseEsClaveFragment : BaseToolbarDialogFragment(), DetallePrepararseEsClaveView,
    PrepararseEsClaveAdapter.SelectableCallback {

    private val personIdentifier by lazyPersonIdentifier()
    private val presenter by injectFragment<DetallePrepararseEsClavePresenter>()
    private val analyticsPresenter by injectFragment<FirebaseAnalyticsPresenter>()
    private val adapter by lazy {
        PrepararseEsClaveAdapter(
            R.layout.item_prepararse_es_clave_detail,
            PrepararseEsClaveViewHolder.Type.DETALLE
        ).apply { listener = this@DetallePrepararseEsClaveFragment }
    }

    var elemetoSeleccionado: PrepararseEsClaveModel? = null

    override fun getLayout() = R.layout.fragment_prepararse_es_clave_detail

    override fun getToolbar(): MaterialToolbar? = toolbar as? MaterialToolbar

    override fun getTitle(): String? = resources.getString(R.string.title_prepararse_es_clave)

    override fun getMode(): Mode = Mode.RETURNABLE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
    }

    override fun mostrarElementos(elementos: List<PrepararseEsClaveModel>) {
        adapter.actualizar(elementos)
    }

    override fun moverAOpcionSeleccionada() {
        elemetoSeleccionado?.let {
            rvOpcionesPreparaseEsClave?.scrollToPosition(it.posicion)
        }
    }

    override fun onSelectePrepararseEsClaveItem(
        item: PrepararseEsClaveModel,
        type: PrepararseEsClaveViewHolder.Type,
        posicion: Int
    ) {
        elemetoSeleccionado = item
        presenter.establecerOpcionSeleccionada()
        mostrarFragmento(elemetoSeleccionado?.tipo, elemetoSeleccionado?.titulo)
        presenter.moverAOpcionSeleccionada()
    }

    override fun establecerOpcionSeleccionada() {
        elemetoSeleccionado?.let {
            adapter.notificarElementosCambiados(it.posicion)
        }
    }

    private fun inicializar() {
        configurarRecycler()
        obtenerElementos(personIdentifier.role)
        mostrarFragmento(elemetoSeleccionado?.tipo, elemetoSeleccionado?.titulo)
    }

    private fun mostrarFragmento(tipo: PrepararseEsClave.Tipo?, titulo: String?) {
        when (tipo) {
            PrepararseEsClave.Tipo.VENTA -> incrustarFragmento(
                SalesFragment.newInstance(personIdentifier)
            )
            PrepararseEsClave.Tipo.RESULTADOCX -> incrustarFragmento(
                ResultsLastCampaignFragment.newInstance(personIdentifier)
            )
            PrepararseEsClave.Tipo.NEGOCIO -> incrustarFragmento(
                NegocioContenedorFragment.newInstance(personIdentifier)
            )
            PrepararseEsClave.Tipo.DIGITAL -> incrustarFragmento(
                DigitalFragment.newInstance(
                    personIdentifier
                )
            )
            PrepararseEsClave.Tipo.LOMASVENDIDO -> incrustarFragmento(
                MasVendidoContenedorFragment.newInstance(
                    personIdentifier
                )
            )
            PrepararseEsClave.Tipo.ACUERDOSU3C -> incrustarFragmento(
                AcuerdosUltimasCampaniasFragment.newInstance(
                    personIdentifier.id,
                    personIdentifier.role
                )
            )
            else -> Logger.loge("PrepararseEsClave.Tipo", "error")
        }

        if (personIdentifier.role == Rol.CONSULTORA) {
            analyticsPresenter.enviarEventoTagConsultora(
                TagAnalytics.EVENTO_VER_SECCION_CONSULTORA,
                titulo
            )
        }
    }

    private fun incrustarFragmento(fragment: Fragment) {
        childFragmentManager.apply {
            beginTransaction()
                .replaceOnce(
                    R.id.flContenedor,
                    fragment::class.java.simpleName,
                    this
                ) { fragment }.commit()
        }
    }

    private fun configurarRecycler() {
        rvOpcionesPreparaseEsClave?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
            addItemDecoration(ItemSeccionDecoration(dimen(BaseR.dimen.content_inset_normal)))
            adapter = this@DetallePrepararseEsClaveFragment.adapter
        }
    }

    private fun obtenerElementos(rol: Rol) = presenter.obtener(personIdentifier.id, rol)

    companion object {

        fun newInstance(personIdentifier: PersonIdentifier): DetallePrepararseEsClaveFragment {
            return DetallePrepararseEsClaveFragment().withPersonIdentifier(personIdentifier)
        }
    }
}

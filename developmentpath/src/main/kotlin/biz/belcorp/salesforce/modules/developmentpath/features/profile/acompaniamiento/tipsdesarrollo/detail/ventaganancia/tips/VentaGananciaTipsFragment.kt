package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.tips

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.shareText
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Opciones
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.AnalyticsConstants.LABEL_TIPS_DESARROLLO_VENTA_Y_GANANCIA_COMPARTIR
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.TipsAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.TipsContract
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.model.TipModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.model.VideoModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.tips.model.OfertaModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.tips.model.TipOfertaModel
import kotlinx.android.synthetic.main.fragment_venta_ganancia_tips.*
import java.util.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.tipsoferta.Params as OffersParams

class VentaGananciaTipsFragment : BaseFragment(),
    TipsContract.View, VentaGananciaTipsView, Observer {

    private val mTipsPresenter by injectFragment<TipsContract.Presenter>()
    private val mTipsOfertaPresenter by injectFragment<VentaGananciaTipsPresenter>()

    private val mTipsAdapter by lazy {
        TipsAdapter().apply {
            callback = object : TipsAdapter.Callback {
                override fun onSharedClickItem(model: OfertaModel) {
                    context?.shareText(model.productoUrl ?: return)
                    faPresenter.enviarEventoTagTipsDesarrollo(
                        TagAnalytics.EVENTO_TIPS_DESARROLLO_VENTA_Y_GANANCIA_COMPARTIR,
                        LABEL_TIPS_DESARROLLO_VENTA_Y_GANANCIA_COMPARTIR
                    )
                }
            }
        }
    }

    private val faPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    private var personaId: Long = -1L
    private lateinit var rol: Rol

    var opciones = emptyList<String>()

    private var finalizoTraerTips = false
    private var finalizoTraerTipsOferta = false

    private val mNetworkReceiver = NetworkReceiver()

    override fun getLayout(): Int = R.layout.fragment_venta_ganancia_tips

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
        inicializarNetworkObservable()
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        activity?.registerReceiver(mNetworkReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        activity?.unregisterReceiver(mNetworkReceiver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        destruirNetworkObservable()
    }

    override fun update(o: Observable, connected: Any) {
        if (connected is Boolean) {
            inicializarTipsOfertasPresenter(!connected)
        }
    }

    override fun mostrarTips(data: List<TipModel>) {
        if (!isAttached()) return
        finalizoTraerTips = true
        showEmptyListView(false)
        mTipsAdapter.actualizarData(data)
    }

    override fun mostrarTipsVacio() {
        if (!isAttached()) return
        finalizoTraerTips = true
        showEmptyListView(true)
    }

    override fun mostrarVideo(video: VideoModel) = Unit

    override fun mostrarVideoVacio() = Unit

    override fun mostrarProgreso(show: Boolean) {
        if (!isAttached()) return
        progress?.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun mostrarTipsGanaMas(data: List<TipOfertaModel>) {
        if (!isAttached()) return
        finalizoTraerTipsOferta = true
        showEmptyListView(false)
        mTipsAdapter.actualizarOffers(data)
    }

    override fun mostrarTipsGanaMasVacio() {
        if (!isAttached()) return
        finalizoTraerTipsOferta = true
        showEmptyListView(mTipsAdapter.getTipsCount() == 0)
    }

    private fun recuperarArgs() {
        arguments?.let {
            personaId = it.getLong(ARG_PERSONA_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
        }
    }

    private fun inicializar() {
        finalizoTraerTips = false
        finalizoTraerTipsOferta = false
        configurarTipsRecyclerView()
        showEmptyListView()
        inicializarTipsPresenter()
    }

    private fun configurarTipsRecyclerView() {
        rvTips?.apply {
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mTipsAdapter
        }
    }

    private fun inicializarTipsPresenter() {
        mTipsPresenter.obtener(Params(personaId, rol, opciones.filter { it == Opciones.VG2 }))
    }

    private fun inicializarTipsOfertasPresenter(offline: Boolean) {
        mTipsAdapter.limpiarOffers()
        mTipsOfertaPresenter.obtener(
            OffersParams(
                personaId,
                rol,
                offline
            )
        )
    }

    private fun inicializarNetworkObservable() {
        NetworkReceiver.ObservableObject.instance.addObserver(this)
    }

    private fun destruirNetworkObservable() {
        NetworkReceiver.ObservableObject.instance.deleteObserver(this)
    }

    private fun showEmptyListView(shown: Boolean = false) {
        if (view == null) return
        txtSinTips?.visibility =
            if (validarSiFinalizoTraerData() && shown) View.VISIBLE else View.GONE
    }

    private fun validarSiFinalizoTraerData(): Boolean {
        return finalizoTraerTips && finalizoTraerTipsOferta
    }

    companion object {

        private const val ARG_PERSONA_ID = "PERSONA_ID"
        private const val ARG_ROL = "ROL"

        fun newInstance(personaId: Long, rol: Rol) = VentaGananciaTipsFragment()
            .withArguments(
                ARG_PERSONA_ID to personaId,
                ARG_ROL to rol
            )
    }
}

package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.programanuevas

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.shareText
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.AnalyticsConstants.LABEL_COMPARTIR_VIDEO
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.TipsAdapter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.TipsContract
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.model.TipModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.digital.tips.model.VideoModel
import biz.belcorp.salesforce.modules.developmentpath.features.video.VideoFragment
import kotlinx.android.synthetic.main.fragment_tips_recomendaciones.*

class TipsProgramaNuevasFragment : VideoFragment(), TipsContract.View {

    private var personaId: Long = -1L
    private lateinit var rol: Rol

    private val presenter by injectFragment<TipsProgramaNuevasPresenter>()
    private val tipsAdapter by lazy { TipsAdapter() }

    private val faPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    private lateinit var mVideoModel: VideoModel

    override fun getLayout(): Int = R.layout.fragment_tips_recomendaciones

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializar()
    }

    override fun getVideoLayout() = R.id.youtube_fragment

    private fun recuperarArgs() {
        arguments?.let {
            personaId = it.getLong(ARG_PERSONA_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
        }
    }

    private fun inicializar() {
        configurarTipsRecyclerView()
        inicializarPresenter()
        inicializarEventos()
    }

    private fun inicializarPresenter() {
        presenter.obtener(personaId, rol)
    }

    override fun mostrarTips(data: List<TipModel>) {
        if (!isFragmentAttached()) return
        txtSinTips?.visibility = View.GONE
        tipsAdapter.actualizarData(data)
    }

    override fun mostrarTipsVacio() {
        if (!isFragmentAttached()) return
        txtSinTips?.visibility = View.VISIBLE
    }

    private fun configurarTipsRecyclerView() {
        rvTips?.apply {
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = tipsAdapter
        }
    }

    private fun inicializarEventos() {
        btnCompartir?.setOnClickListener {
            context?.shareText(mVideoModel.videoUrl ?: return@setOnClickListener)
            faPresenter.enviarEventoTagTipsDesarrollo(
                TagAnalytics.EVENTO_TIPS_DESARROLLO_PROGRAMA_NUEVAS_COMPARTIR_VIDEO,
                LABEL_COMPARTIR_VIDEO
            )
        }
    }

    private fun isFragmentAttached(): Boolean {
        if (activity == null && !isAdded)
            return false
        return true
    }

    override fun mostrarVideo(video: VideoModel) {
        if (!isFragmentAttached()) return
        clContenedorVideo?.visibility = View.VISIBLE
        mVideoModel = video
        initVideoYoutube(mVideoModel.videoId ?: return)
    }

    override fun mostrarVideoVacio() {
        if (!isFragmentAttached()) return
        clContenedorVideo?.visibility = View.GONE
    }

    companion object {

        private const val ARG_PERSONA_ID = "PERSONA_ID"
        private const val ARG_ROL = "ROL"

        fun newInstance(personaId: Long, rol: Rol) =
            TipsProgramaNuevasFragment()
                .withArguments(
                    ARG_PERSONA_ID to personaId,
                    ARG_ROL to rol
                )
    }
}

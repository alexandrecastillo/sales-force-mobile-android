package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsvideo

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsvideo.youtubevideo.TipsVideoFragment
import kotlinx.android.synthetic.main.fragment_tips.*

class TipsFragment : BaseFragment(), TipsView {

    private var personaId: Long = -1
    private lateinit var rol: Rol
    private val presenter by injectFragment<TipsPresenter>()

    override fun getLayout(): Int = R.layout.fragment_tips

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            personaId = it.getLong(ARG_PERSONA_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.personaId = personaId
        presenter.rol = rol
        presenter.view = this
        configurarAdapter()
        presenter.obtener(personaId, rol)
    }

    private fun configurarAdapter() {
        rv_tips?.layoutManager = LinearLayoutManager(context ?: return)
        rv_tips?.adapter = TipsVisitaAdapter()
    }

    override fun cargarModelo(viewModel: TipsViewModel) {
        cardResultTips?.visible()
        tv_titulo?.text = viewModel.titulo
        (rv_tips?.adapter as? TipsVisitaAdapter)?.actualizar(viewModel.tips)
        if (!viewModel.urlVideo.isNullOrEmpty()) {
            playVideo(viewModel.urlVideo, viewModel.tituloVideo)
        } else {
            playVideo(Constant.EMPTY_STRING, Constant.EMPTY_STRING)
        }
    }

    override fun mostrarMensaje(mensaje: String) {
        toast(mensaje)
    }

    private fun playVideo(ulrVide: String?, descriptionVideo: String?) {
        if (!isResumed) return
        val tipsVideoFragment = TipsVideoFragment.newInstance(ulrVide, descriptionVideo, rol)
        childFragmentManager
            .beginTransaction()
            .replace(R.id.fl_youtube_video, tipsVideoFragment, "tipsVideo")
            .commit()
    }

    companion object {
        private val ARG_PERSONA_ID = "id"
        private val ARG_ROL = "rol"

        fun newInstance(personaId: Long, rol: Rol) = TipsFragment()
            .withArguments(
                ARG_PERSONA_ID to personaId,
                ARG_ROL to rol
            )
    }
}

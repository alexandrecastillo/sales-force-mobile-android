package biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.features.entities.ParametroUneteModel
import kotlinx.android.synthetic.main.fragment_rechazar_postulante.*

class RechazarPostulanteFragment : BaseDialogFragment(), RechazoPostulanteView {

    private val rechazoPostulantePresenter: RechazoPostulantePresenter by injectFragment()
    var mListener: OnRechazoInteractionListener? = null
    var esPostulante: Boolean = true


    override fun getLayout(): Int {
        return R.layout.fragment_rechazar_postulante
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pais = it.getString(PAIS).toString()
            solicitudPostulanteID = it.getInt(SOLICITUDPOSTULANTEID)
            estadoPostulante = it.getInt(ESTADOPOSTULANTE)
            estadoRol = it.getInt(ESTADOROL)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rechazoPostulantePresenter.setView(this)
        rechazoPostulantePresenter.getMotivoRechazos()
        btn_rechazar?.setOnClickListener {
            if (esPostulante) rechazoPostulante() else rechazoPrePostulante()
        }
        iv_close.setOnClickListener {
            dialog?.dismiss()
        }
    }

    override fun context(): Context {
        return context!!
    }

    private fun rechazoPostulante() {
        val tipoMotivoRechazo =
            (uspn_motivo?.selectedItem() as ParametroUneteModel).valor.toString()
        val comentario = uet_comentarios?.getText()
        rechazoPostulantePresenter.updateEstadoRechazada(
            pais,
            solicitudPostulanteID,
            estadoPostulante, estadoRol, tipoMotivoRechazo, comentario!!
        )
    }

    private fun rechazoPrePostulante() {
        val tipoMotivoRechazo =
            (uspn_motivo?.selectedItem() as ParametroUneteModel).valor.toString()
        val comentario = uet_comentarios?.getText()
        rechazoPostulantePresenter.updateEstadoPreRechazada(
            pais,
            solicitudPostulanteID,
            tipoMotivoRechazo,
            comentario!!
        )
    }

    override fun onStart() {
        super.onStart()
        ajustarTamanioAPantalla()
    }

    private fun ajustarTamanioAPantalla() {
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)
    }


    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onDestroy() {
        rechazoPostulantePresenter.destroy()
        super.onDestroy()
    }

    override fun showMotivoRechazo(model: List<ParametroUneteModel>) {
        uspn_motivo?.load(model)
    }

    override fun showPostulanteRechaza() {
        dialog?.dismiss()
        mListener?.postulanteRechazada()
    }

    override fun showLoading() {
        inc_loading?.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        inc_loading?.visibility = View.GONE
    }

    companion object {

        private const val PAIS = "PAIS"
        private var pais: String = ""
        private const val SOLICITUDPOSTULANTEID = "SOLICITUDPOSTULANTEID"
        private var solicitudPostulanteID: Int = 0
        private const val ESTADOPOSTULANTE = "ESTADOPOSTULANTE"
        private var estadoPostulante: Int = 0
        private const val ESTADOROL = "ESTADOROL"
        private var estadoRol: Int = 0

        fun newInstance(
            pais: String,
            solicitudPostulanteID: Int,
            estadoPostulante: Int,
            estadoRol: Int
        ): RechazarPostulanteFragment {
            val fragment = RechazarPostulanteFragment()
            val args = Bundle()
            args.putString(PAIS, pais)
            args.putInt(SOLICITUDPOSTULANTEID, solicitudPostulanteID)
            args.putInt(ESTADOPOSTULANTE, estadoPostulante)
            args.putInt(ESTADOROL, estadoRol)
            fragment.arguments = args
            return fragment
        }
    }
}

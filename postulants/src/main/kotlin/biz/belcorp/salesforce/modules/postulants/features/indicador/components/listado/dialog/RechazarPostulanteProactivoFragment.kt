package biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.dialog

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.features.entities.ParametroUneteModel
import kotlinx.android.synthetic.main.fragment_rechazar_postulante_proactivo.*

class RechazarPostulanteProactivoFragment : BaseDialogFragment(), RechazoPostulanteView {

    private val rechazoPostulantePresenter: RechazoPostulantePresenter by injectFragment()
    var mListener: OnRechazoInteractionListener? = null
    var esPostulante: Boolean = true

    override fun getLayout(): Int {
        return R.layout.fragment_rechazar_postulante_proactivo
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rechazoPostulantePresenter.setView(this)
        rechazoPostulantePresenter.getMotivoRechazoProactivos()
        btn_rechazar?.setOnClickListener {

            (uspn_motivo?.selectedItem() as ParametroUneteModel).nombre?.let { m ->
                mListener?.postulanteProactivaRechazada(m)
                dismiss()
            }
        }
        iv_close.setOnClickListener {
            dialog?.dismiss()
        }
    }

    override fun context(): Context {
        return context!!
    }

    override fun onStart() {
        super.onStart()
        ajustarTamanioAPantalla()
    }

    private fun ajustarTamanioAPantalla() {

        val displayMetrics = DisplayMetrics()

        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)

        val width = displayMetrics.widthPixels

        dialog?.window?.setLayout(width - 110, ViewGroup.LayoutParams.WRAP_CONTENT)
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
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    fun setListener(mListener: OnRechazoInteractionListener) {
        this.mListener = mListener
    }

    companion object {
        fun newInstance(): RechazarPostulanteProactivoFragment {
            return RechazarPostulanteProactivoFragment()
        }
    }
}

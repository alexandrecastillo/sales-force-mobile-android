package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.modules.postulants.features.entities.PrePostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.prepostulantes.FormularioPreView
import biz.belcorp.salesforce.modules.postulants.utils.Constant

abstract class BaseUnetePreFragment : BaseFragment() {

    object Estado {
        const val Nuevo = 1
        const val Edicion = 2
        const val Visualizacion = 3
    }

    var formularioPreView: FormularioPreView? = null
    var mPreModel: PrePostulanteModel? = null
    var isValid: Boolean = false
    var mEstado = Estado.Nuevo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        formularioPreView = activity as FormularioPreView
        initView()
        initModel()
        initEstado()
    }

    abstract fun initEstado()

    private fun isStepEditable(): Boolean {
        return when (paso()) {
            Constant.NUMERO_UNO -> formularioPreView?.uneteConfiguracion()?.edicionpaso1 ?: false
            Constant.NUMERO_DOS -> formularioPreView?.uneteConfiguracion()?.edicionpaso2 ?: false
            else -> false
        }
    }

    fun getEstado(): Int {
        mEstado = if (formularioPreView?.prePostulante()?.solicitudPrePostulanteID != 0) {
            if (paso() > formularioPreView?.paso()!!) {
                Estado.Nuevo
            } else {
                if (isStepEditable()) {
                    Estado.Edicion
                } else {
                    Estado.Visualizacion
                }
            }
        } else {
            Estado.Nuevo
        }
        return mEstado
    }

    val validarMail = {
        formularioPreView?.uneteConfiguracion()!!.validarMail
    }

    val validarCelular = {
        formularioPreView?.uneteConfiguracion()!!.validarCelular
    }

    abstract fun initView()

    abstract fun initModel()

    abstract fun paso(): Int

    public fun toast(string: Int) {
        android.widget.Toast.makeText(
            getActivity(), getString(string),
            android.widget.Toast.LENGTH_SHORT
        ).show();
    }

    public fun toastString(string: String) {
        android.widget.Toast.makeText(
            getActivity(), string,
            android.widget.Toast.LENGTH_SHORT
        ).show();
    }

}

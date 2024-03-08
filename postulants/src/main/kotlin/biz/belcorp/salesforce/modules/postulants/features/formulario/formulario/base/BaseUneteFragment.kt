package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.toast
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.FormularioView
import biz.belcorp.salesforce.modules.postulants.utils.Constant

abstract class BaseUneteFragment : BaseFragment() {

    object Estado {
        const val Nuevo = 1
        const val Edicion = 2
        const val Visualizacion = 3
    }

    var formularioView: FormularioView? = null
    var mModel: PostulanteModel? = null
    var isValid: Boolean = false
    var mEstado = Estado.Nuevo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        formularioView = activity as FormularioView
        initView()
        initModel()
        initEstado()
    }

    abstract fun initEstado()

    private fun isStepEditable(): Boolean {
        return when (paso()) {
            Constant.NUMERO_UNO -> formularioView?.uneteConfiguracion()?.edicionpaso1 ?: false
            Constant.NUMERO_DOS -> formularioView?.uneteConfiguracion()?.edicionpaso2 ?: false
            Constant.NUMERO_TRES -> formularioView?.uneteConfiguracion()?.edicionpaso3 ?: false
            Constant.NUMERO_CUATRO -> formularioView?.uneteConfiguracion()?.edicionpaso4 ?: false
            Constant.NUMERO_CINCO -> formularioView?.uneteConfiguracion()?.edicionpaso5 ?: false
            else -> false
        }
    }

    fun getEstado(): Int {
        mEstado = if (formularioView?.postulante()?.solicitudPostulanteID != 0) {
            if (paso() > formularioView?.paso()!!) {
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
        formularioView?.uneteConfiguracion()?.validarMail ?: false
    }

    val validarCelular = {
        formularioView?.uneteConfiguracion()?.validarCelular ?: false
    }

    fun toast(string: Int) {
        context?.toast(string)
    }

    fun toastString(string: String) {
        context?.toast(string)
    }

    abstract fun initView()

    abstract fun initModel()

    abstract fun paso(): Int

}

package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso4

import android.content.Context
import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteConfig
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteEstadoTelefonico
import biz.belcorp.salesforce.modules.postulants.features.entities.*
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.IUnetePaso4
import biz.belcorp.salesforce.modules.postulants.features.formulario.componentes.UnetePaso4Factory
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base.BaseUneteFragment
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.KeyboardUtil
import biz.belcorp.salesforce.modules.postulants.utils.customDialog
import biz.belcorp.salesforce.modules.postulants.utils.solicitudRechazadaDialog
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.dialog_material.*
import kotlinx.android.synthetic.main.fragment_unete_paso_4.*

class UnetePaso4Fragment : BaseUneteFragment(), Step, UnetePaso4View {

    private val presenter: UnetePaso4Presenter by injectFragment()

    private var content: IUnetePaso4? = null
    private var esZonaAltoRiesgo: Boolean = false
    private var pasoBloqueado: Int = Constant.SIN_ZONA

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validateDisable()
        btnContinuar?.setOnClickListener {

            KeyboardUtil.dismissKeyboard(context, view)

            if (elFormularioEsValido()) {
                content?.createModel()?.let {
                    mModel = it
                    presenter.updatePostulante(it)
                }
                formularioView?.getPasoBloqueado()?.let { paso -> pasoBloqueado = paso }
            }

        }
    }

    private fun validateDisable() {
        val validate = formularioView?.disableSteps() ?: false
        if (validate) content?.disable()
    }

    private fun elFormularioEsValido() = content?.validate() == true

    override fun paso(): Int {
        return formularioView?.pais()?.let { pais ->
            UneteConfig.find(pais)?.pasos?.let { pasos ->
                pasos - 1
            } ?: Constant.NUMERO_CERO
        } ?: Constant.NUMERO_CERO
    }

    override fun errorAlObtenerNombreConsultoraRecomendante() {
        content?.errorAlObtenerNombreConsultoraRecomendante()
    }

    override fun getLayout() = R.layout.fragment_unete_paso_4

    override fun initView() {

        presenter.setView(this)

        val vw = UnetePaso4Factory.getView(activity as Context, formularioView?.pais(), this)
        content = vw as IUnetePaso4

        lyContainer?.removeAllViews()
        lyContainer?.addView(vw)
    }

    override fun showError(message: String) {
        context?.solicitudRechazadaDialog(message) {}?.show()
    }

    override fun showSolicitudRechazada(message: String) {
        context?.solicitudRechazadaDialog(message) {
            activity?.finish()
        }?.show()
    }

    override fun onSelected() = Unit

    override fun verifyStep(): VerificationError? {
        return if (content?.validate() != true) {
            VerificationError(resources.getString(R.string.tx_complete_los_datos))
        } else {
            if (mEstado == Estado.Nuevo) {
                toast(R.string.unete_step_no_guardado)
                VerificationError(resources.getString(R.string.tx_complete_los_datos))
            } else null
        }
    }

    override fun onError(error: VerificationError) = Unit

    override fun showLoading() {
        formularioView?.showLoading()
    }

    override fun hideLoading() {
        formularioView?.hideLoading()
    }

    override fun getConsultoraRecomienda(codigo: String) {
        presenter.findConsultora(codigo)
    }

    override fun showConsultoraRecomienda(consultora: String) {
        content?.showConsultoraRecomienda(consultora)
    }

    override fun showGeneros(generos: List<SexoModel>) {
        content?.showGeneros(generos)
    }

    override fun showTipoMeta(tiposMetas: List<TipoMetaModel>) {
        content?.showTipoMeta(tiposMetas)
    }

    override fun showNivelEducativo(nivelEducativo: List<TablaLogicaModel>) {
        content?.showNivelEducativo(nivelEducativo)
    }

    override fun showEstadoCivil(estadoCivil: List<TablaLogicaModel>) {
        content?.showEstadoCivil(estadoCivil)
    }

    override fun showTipoVinculoAval(tipoVinculoAval: List<TablaLogicaModel>) {
        content?.showTipoVinculoAval(tipoVinculoAval)
    }

    override fun showTipoVinculoFamiliar(tipoVinculoFamiliar: List<TablaLogicaModel>) {
        content?.showTipoVinculoFamiliar(tipoVinculoFamiliar)
    }

    override fun showTipoVinculoNoFamiliar(tipoVinculoNoFamiliar: List<TablaLogicaModel>) {
        content?.showTipoVinculoNoFamiliar(tipoVinculoNoFamiliar)
    }

    override fun initModel() {
        presenter.listGeneros()
        presenter.listTipoMeta()
        presenter.listNivelEducativo()
        presenter.listEstadoCivil()
        presenter.listTipoVinculoFamiliar()
        presenter.listTipoVinculoNoFamiliar()
        presenter.listTipoVinculoAval()
        presenter.listOtrasMarcas()
        presenter.listOrigenIngreso()
        presenter.listTipoPersona()
        presenter.validarZonaRiesgo(getModel().codigoZona?.trim() + Constant.EMPTY_STRING + getModel().codigoSeccion?.trim())
    }

    override fun getModel(): PostulanteModel {
        return formularioView?.postulante() ?: PostulanteModel()
    }

    override fun updated(postulanteModel: PostulanteModel) {
        mModel = postulanteModel
        if (paso() == pasoBloqueado &&
            mModel?.estadoTelefonico != UneteEstadoTelefonico.VALIDADO.value
        ) {
            formularioView?.setPostulante(postulanteModel)
            mostrarMensajeBloqueoPaso()
        } else {
            formularioView?.postulante(postulanteModel)
            formularioView?.continuar(Constant.NUMERO_CINCO)
        }
    }

    override fun showOtrasMarcas(tipoVinculo: List<TablaLogicaModel>) {
        content?.showOtrasMarcas(tipoVinculo)
    }

    override fun showTipoPersona(tiposPersona: List<TablaLogicaModel>) {
        content?.showTipoPersona(tiposPersona)
    }

    override fun showOrigenIngreso(origenesIngreso: List<TablaLogicaModel>) {
        content?.showOrigenIngreso(origenesIngreso)
    }

    override fun setZonaRiesgo(esZonaRiesgo: Boolean) {
        esZonaAltoRiesgo = esZonaRiesgo
    }

    override fun esZonaRiesgo() = esZonaAltoRiesgo

    override fun getCodigoRol() = presenter.getCodigoRol()

    override fun expandirConsultoraRecomendante(codigoConsultoraRecomendante: String?) =
        presenter.expandirConsultoraRecomendante(codigoConsultoraRecomendante)

    override fun obtenerCodigoODocumentoConsultoraRecomendante(codigoConsultoraRecomendante: String?) =
        presenter.obtenerCodigoODocumentoConsultoraRecomendante(codigoConsultoraRecomendante)

    override fun initEstado() {
        when (getEstado()) {
            Estado.Edicion -> {
                content?.disable(true)
                btnContinuar?.isEnabled = true
            }
            Estado.Visualizacion -> {
                content?.disable()
                btnContinuar?.isEnabled = false
            }
        }
    }

    private fun mostrarMensajeBloqueoPaso() {
        context?.customDialog(R.layout.dialog_material) {
            tvTitle.gone()
            tvContent.text = getString(R.string.alert_paso_bloqueado)
            ivIcon.gone()

            btnOk.text = getString(R.string.actualizacion_button_ok)
            btnCancel.visible()
            btnOk.setOnClickListener {
                dismiss()
            }
        }?.show()
    }

}

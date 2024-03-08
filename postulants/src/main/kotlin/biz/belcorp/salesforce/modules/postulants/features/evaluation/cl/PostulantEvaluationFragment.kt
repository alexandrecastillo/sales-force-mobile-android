package biz.belcorp.salesforce.modules.postulants.features.evaluation.cl

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CL
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.BuroResponse
import biz.belcorp.salesforce.modules.postulants.features.adapter.validacionmensaje.ValidacionMensajeModel
import biz.belcorp.salesforce.modules.postulants.features.adapter.validacionmensaje.ValidacionesMensajesAdapter
import biz.belcorp.salesforce.modules.postulants.features.entities.ParametroUneteModel
import biz.belcorp.salesforce.modules.postulants.features.evaluation.PostulantEValuationPresenter
import biz.belcorp.salesforce.modules.postulants.features.evaluation.PostulantEvaluationView
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.V
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.customDialog
import biz.belcorp.salesforce.modules.postulants.utils.onTextChanged
import kotlinx.android.synthetic.main.dialog_material.*
import kotlinx.android.synthetic.main.dialog_material.btnOk
import kotlinx.android.synthetic.main.dialog_material.tvContent
import kotlinx.android.synthetic.main.fragment_postulant_evaluation_cl.*

class PostulantEvaluationFragment : BaseFragment(), PostulantEvaluationView {

    private val presenter: PostulantEValuationPresenter by injectFragment()

    private val messagesAdapter: ValidacionesMensajesAdapter by lazy {
        ValidacionesMensajesAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.setView(this)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_postulant_evaluation_cl
    }

    override fun initViews(countryIso: String, rol: String) {
        initViews()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun onHeaderClick(modo: Int) {

    }

    override fun showTipoDocumento(model: List<ParametroUneteModel>) {

    }

    override fun showErrorConnectionMessage() {
        context?.customDialog(R.layout.dialog_material) {
            ivIcon.visibility = View.GONE
            tvTitle.setText(R.string.documento_duplicado)
            tvContent.text = context.getString(R.string.error_conexion_reintentar)
            btnOk.setText(R.string.accept)
            btnOk.setOnClickListener {
                dismiss()
            }
        }?.show()
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), "OTRO TIPO DE ERROR: $message", Toast.LENGTH_LONG).show()
    }

    override fun onSuccess(response: BuroResponse, messages: List<ValidacionMensajeModel>) {
        showResponse(messages)
    }

    override fun onShowName(name: String) {
        txtName.text = name
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    private fun validateForm(): Boolean {
        if (presenter.sesion.rol == Rol.SOCIA_EMPRESARIA) return txtDocumento.validate()

        return txtSeccion.validate() && txtDocumento.validate()
    }

    private fun documentValidation() {

    }

    private fun showResponse(messages: List<ValidacionMensajeModel>) {
        messagesAdapter.submitList(messages)
    }

    private fun clear() {
        txtName.text = Constant.EMPTY_STRING
        messagesAdapter.submitList(emptyList())
    }

    private fun initViews() {
        rvwMessages?.adapter = messagesAdapter

        txtDocumento.setRequired(true)
        txtSeccion.setRequired(true)

        txtSeccion?.addV(
            V(
                requireContext(), Validation.EMPTY,
                R.string.postulant_evaluation_empty_section
            )
        )

        txtSeccion?.inputType(InputType.TYPE_CLASS_TEXT)

        txtDocumento?.addV(
            V(requireContext(), Validation.EMPTY, R.string.unete_paso1_valid_rut),
            V(
                requireContext(),
                Validation.MIN_LENGTH,
                CL.RUT_MIN_LENGHT,
                R.string.unete_paso1_valid_rut
            )
        )

        btnConsultar.setOnClickListener {
            if (!validateForm()) return@setOnClickListener

            presenter.evaluarPostulante(
                documento = txtDocumento.getText(),
                seccion = txtSeccion?.getText().toString().trim()
            )
        }

        txtDocumento.onTextChanged {
            clear()
        }

        if (presenter.sesion.rol == Rol.SOCIA_EMPRESARIA)
            txtSeccion.visibility = View.GONE
        else
            txtSeccion.visibility = View.VISIBLE

        clear()
        documentValidation()
    }
}

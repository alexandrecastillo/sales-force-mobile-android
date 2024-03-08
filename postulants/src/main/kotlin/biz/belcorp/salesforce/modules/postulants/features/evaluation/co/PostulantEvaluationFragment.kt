package biz.belcorp.salesforce.modules.postulants.features.evaluation.co

import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.CO
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.BuroResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteTipoDocumento
import biz.belcorp.salesforce.modules.postulants.features.adapter.validacionmensaje.ValidacionMensajeModel
import biz.belcorp.salesforce.modules.postulants.features.adapter.validacionmensaje.ValidacionesMensajesAdapter
import biz.belcorp.salesforce.modules.postulants.features.entities.ParametroUneteModel
import biz.belcorp.salesforce.modules.postulants.features.evaluation.PostulantEValuationPresenter
import biz.belcorp.salesforce.modules.postulants.features.evaluation.PostulantEvaluationView
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Filters
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.V
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms
import biz.belcorp.salesforce.modules.postulants.utils.customDialog
import biz.belcorp.salesforce.modules.postulants.utils.onTextChanged
import kotlinx.android.synthetic.main.dialog_material.*
import kotlinx.android.synthetic.main.dialog_material.btnOk
import kotlinx.android.synthetic.main.dialog_material.tvContent
import kotlinx.android.synthetic.main.fragment_postulant_evaluation_co.*

class PostulantEvaluationFragment : BaseFragment(), PostulantEvaluationView {

    private val presenter: PostulantEValuationPresenter by injectFragment()

    private val messagesAdapter: ValidacionesMensajesAdapter by lazy {
        ValidacionesMensajesAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.setView(this)
        presenter.listTipoDocumento()
        initViews()
    }

    override fun getLayout(): Int {
        return R.layout.fragment_postulant_evaluation_co
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
        spnTipoDocumento.load(model)
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
        return txtDocumento.validate() && txtApellidoPaterno.validate()
    }

    private fun documentValidation() {

        when (spnTipoDocumento.selected<ParametroUneteModel>()?.valor) {
            UneteTipoDocumento.CO_CC.codigo -> {
                txtDocumento?.setRestriction(Filters.filterNumber(CO.CEDULA_MAX_LENGHT))
                txtDocumento?.inputType(InputType.TYPE_CLASS_NUMBER)
                txtDocumento?.v(
                    V(
                        requireContext(),
                        Validation.EMPTY,
                        R.string.unete_paso1_valid_documento_empty
                    ),
                    fieldCedulaValid(UneteTipoDocumento.CO_CC)
                )
                txtDocumento?.v(
                    V(
                        requireContext(),
                        Validation.STARTIN2,
                        R.string.unete_paso1_valid_documento_format
                    )
                )
            }
            UneteTipoDocumento.CO_CE.codigo -> {
                txtDocumento?.inputType(InputType.TYPE_CLASS_NUMBER)
                txtDocumento?.setRestriction(Filters.filterMaxLength(7))
                txtDocumento?.v(
                    V(
                        requireContext(),
                        Validation.EMPTY,
                        R.string.unete_paso1_valid_documento_empty
                    ),
                    V(
                        requireContext(),
                        Validation.MIN_LENGTH,
                        4,
                        R.string.unete_paso1_valid_documento_min_length
                    )
                )
            }
            UneteTipoDocumento.CO_CONTRASENA.codigo -> {
                txtDocumento?.inputType(InputType.TYPE_CLASS_NUMBER)
                txtDocumento?.setRestriction(
                    arrayOf(
                        Filters.filterMaxLength(CO.CEDULA_MAX_LENGHT),
                        InputFilter.AllCaps()
                    )
                )
                txtDocumento?.v(
                    V(
                        requireContext(),
                        Validation.EMPTY,
                        R.string.unete_paso1_valid_documento_empty
                    ),
                    fieldCedulaValid(UneteTipoDocumento.CO_CONTRASENA)
                )

            }
        }
    }

    private fun fieldCedulaValid(tipoDocumento: UneteTipoDocumento): V {
        val a = object : Validation.Companion.ValidCommand {
            override fun execute(): Boolean {
                return UneteAlgorithms.validarCedulaCO(txtDocumento?.getText()?.trim().orEmpty(), tipoDocumento)
            }
        }
        return V(
            requireContext(),
            Validation.CALLABLE,
            a,
            R.string.unete_paso1_valid_documento_format
        )
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

        txtApellidoPaterno.setRequired(true)
        txtDocumento.setRequired(true)

        txtApellidoPaterno?.addV(
            V(
                requireContext(), Validation.EMPTY,
                R.string.unete_paso1_valid_apellido_paterno_empty
            )
        )

        btnConsultar.setOnClickListener {
            if (!validateForm()) return@setOnClickListener

            presenter.evaluarPostulante(
                tipoDocumento = spnTipoDocumento?.selected<ParametroUneteModel>()?.valor
                    ?: Constant.EMPTY_STRING,
                documento = txtDocumento.getText(),
                apellido = txtApellidoPaterno.getText()
            )
        }

        spnTipoDocumento.onItemSelected {
            txtDocumento.setText(Constant.EMPTY_STRING)

            documentValidation()
            clear()
        }

        txtDocumento.onTextChanged {
            clear()
        }

        txtApellidoPaterno.onTextChanged {
            clear()
        }

        clear()
        documentValidation()
    }
}

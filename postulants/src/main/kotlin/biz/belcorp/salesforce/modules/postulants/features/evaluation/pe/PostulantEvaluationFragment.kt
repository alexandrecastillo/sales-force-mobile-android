package biz.belcorp.salesforce.modules.postulants.features.evaluation.pe

import android.os.Bundle
import android.text.InputType
import android.text.InputType.TYPE_CLASS_NUMBER
import android.text.InputType.TYPE_CLASS_TEXT
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.postulants.R
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
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.EMPTY
import biz.belcorp.salesforce.modules.postulants.features.widget.validUnete.Validation.Companion.MIN_LENGTH
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.RUC
import biz.belcorp.salesforce.modules.postulants.utils.customDialog
import biz.belcorp.salesforce.modules.postulants.utils.onTextChanged
import kotlinx.android.synthetic.main.dialog_material.*
import kotlinx.android.synthetic.main.fragment_postulant_evaluation_pe.*

class PostulantEvaluationFragment : BaseFragment(), PostulantEvaluationView {

    private val presenter: PostulantEValuationPresenter by injectFragment()

    private val messagesAdapter: ValidacionesMensajesAdapter by lazy {
        ValidacionesMensajesAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.setView(this)
        presenter.listTipoDocumento()
    }

    override fun getLayout(): Int {
        return R.layout.fragment_postulant_evaluation_pe
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
        if (presenter.sesion.rol == Rol.SOCIA_EMPRESARIA) return txtDocumento.validate()

        return txtSeccion.validate() && txtDocumento.validate()
    }

    private fun documentValidation() {

        when (spnTipoDocumento.selected<ParametroUneteModel>()?.idParametroUnete) {
            UneteTipoDocumento.DNI.tipo -> {
                txtDocumento?.setRestriction(Filters.filterNumber(8))
                txtDocumento?.inputType(TYPE_CLASS_NUMBER)
                txtDocumento?.v(
                    V(requireContext(), EMPTY, R.string.unete_valid_obligatorio),
                    V(
                        requireContext(),
                        MIN_LENGTH,
                        8,
                        R.string.unete_paso1_valid_documento_min_length
                    )
                )

            }
            UneteTipoDocumento.CE.tipo -> {
                txtDocumento?.inputType(TYPE_CLASS_NUMBER)
                txtDocumento?.setRestriction(Filters.filterNumber(10))
                txtDocumento?.v(
                    V(requireContext(), EMPTY, R.string.unete_paso1_valid_documento_empty),
                    V(
                        requireContext(),
                        MIN_LENGTH,
                        7,
                        R.string.unete_paso1_valid_documento_min_length
                    )
                )
            }
            UneteTipoDocumento.PASAPORTE.tipo -> {
                txtDocumento?.inputType(TYPE_CLASS_TEXT)
                txtDocumento?.setRestriction(Filters.filterAlphanumeric(10))
                txtDocumento?.v(
                    V(requireContext(), EMPTY, R.string.unete_paso1_valid_documento_empty),
                    V(
                        requireContext(),
                        MIN_LENGTH,
                        7,
                        R.string.unete_paso1_valid_documento_min_length
                    )
                )

            }
            UneteTipoDocumento.RUC.tipo -> {
                txtDocumento?.inputType(TYPE_CLASS_NUMBER)
                txtDocumento?.setRestriction(Filters.filterNumber(11))
                txtDocumento?.v(
                    V(requireContext(), EMPTY, R.string.unete_paso1_valid_documento_empty),
                    V(
                        requireContext(),
                        MIN_LENGTH,
                        11,
                        R.string.unete_paso1_valid_documento_min_length
                    ),
                    fieldDocumentoValid()
                )

            }
            UneteTipoDocumento.OTROS.tipo -> {
                txtDocumento?.inputType(TYPE_CLASS_TEXT)
                txtDocumento?.setRestriction(Filters.filterMaxLength(11))
                txtDocumento?.resetV()

            }
        }
    }

    private fun showResponse(messages: List<ValidacionMensajeModel>) {
        messagesAdapter.submitList(messages)
    }

    private fun fieldDocumentoValid(): V {
        val a = object : Validation.Companion.ValidCommand {
            override fun execute(): Boolean {
                return RUC.validate(txtDocumento?.getText().orEmpty())
            }
        }
        return V(requireContext(), Validation.CALLABLE, a, R.string.unete_valid_formato_invalido)
    }

    private fun clear() {
        txtName.text = Constant.EMPTY_STRING
        messagesAdapter.submitList(emptyList())
    }

    private fun initViews() {
        rvwMessages?.adapter = messagesAdapter
        rvwMessages?.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

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
            V(requireContext(), Validation.EMPTY, R.string.unete_paso1_valid_documento_format)
        )

        btnConsultar.setOnClickListener {
            if (!validateForm()) return@setOnClickListener

            presenter.evaluarPostulante(
                tipoDocumento = spnTipoDocumento?.selected<ParametroUneteModel>()?.valor
                    ?: Constant.EMPTY_STRING,
                documento = txtDocumento.getText(),
                seccion = txtSeccion?.getText().toString().trim()
            )
        }

        txtDocumento.onTextChanged {
            clear()
        }

        spnTipoDocumento.onItemSelected {
            txtDocumento.setText(Constant.EMPTY_STRING)

            documentValidation()
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

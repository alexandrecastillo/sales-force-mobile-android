package biz.belcorp.salesforce.modules.creditinquiry.features

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import biz.belcorp.mobile.components.core.extensions.gone
import biz.belcorp.mobile.components.core.extensions.visible
import biz.belcorp.salesforce.analytics.core.domain.entities.Label
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.creditinquiry.R
import biz.belcorp.salesforce.modules.creditinquiry.features.model.*
import biz.belcorp.salesforce.modules.creditinquiry.features.presenters.ConsultaCrediticiaPresenter
import biz.belcorp.salesforce.modules.creditinquiry.features.util.Constant
import biz.belcorp.salesforce.modules.creditinquiry.features.util.Constant.CHAR_BLANK_SPACE
import biz.belcorp.salesforce.modules.creditinquiry.features.util.KeyboardUtil
import biz.belcorp.salesforce.modules.creditinquiry.features.util.StringUtils
import biz.belcorp.salesforce.modules.creditinquiry.utils.AnalyticUtils
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.fragment_credit_consulting_detail.*
import java.util.*


class CreditConsultingDetailFragment : BaseFragment(), ConsultaCrediticiaView,
    ConsultaCrediticiaExtView {

    override fun getLayout() = R.layout.fragment_credit_consulting_detail

    private val presenter: ConsultaCrediticiaPresenter by injectFragment()

    private var consultaCrediticiaModel: ConsultaCrediticiaInternaModel? = null

    private var isActiveConsultant: Boolean = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            consultaCrediticiaModel = it.getParcelable(INTERNAL_CREDIT_INQUIRY_ARGS)
        }

        presenter.setView(this, this)

        isActiveConsultant = alreadyIsActiveConsultant(consultaCrediticiaModel)
        consultaCrediticiaModel?.let {
            loadConsultantInformation(it)
            loadInternalCreditConsulting(it)
            showExternalCreditConsulting(it)
        }

        btn_consult?.setOnClickListener {
            presenter.setView(this, this)
            presenter.checkTitle()

            presenter.searchConsultaCrediticiaDeudaExterna(
                consultaCrediticiaModel?.documentoIdentidad.orEmpty()
            )
            ll_check_external_debt?.gone()
            tv_consulting_external_debt?.visible()
        }

        btn_consult_no_belcorp?.setOnClickListener {
            if (camposValidos()) {
                validarRegionZona()
            }
        }

        iv_back?.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun camposValidos(): Boolean {
        val lastName = et_first_lastname.text.toString()
            .trim { it <= CHAR_BLANK_SPACE }
            .apply { et_first_lastname?.setText(this) }
        KeyboardUtil.putEditTextCaretAtTheEnd(et_first_lastname)

        val region = et_region.text.toString()
            .trim { it <= CHAR_BLANK_SPACE }
            .apply { et_region?.setText(this) }
        KeyboardUtil.putEditTextCaretAtTheEnd(et_region)

        val zone = et_zona.text.toString()
            .trim { it <= CHAR_BLANK_SPACE }
            .apply { et_zona?.setText(this) }
        KeyboardUtil.putEditTextCaretAtTheEnd(et_zona)

        context?.apply {
            getSharedPreferences(Constant.USER_PREFERENCES, Context.MODE_PRIVATE).apply {
                getString(Constant.COD_ROL, Constant.ROL).let {
                    when {
                        it.equals(Constant.GZ, ignoreCase = true)
                            && lastName.isEmpty() -> {
                            tv_error_message?.text =
                                getString(R.string.you_must_complete_first_name)
                        }
                        it.equals(Constant.GR, ignoreCase = true)
                            && (lastName.isEmpty() || zone.isEmpty()) -> {
                            tv_error_message?.text =
                                getString(R.string.you_must_complete_fields)
                        }
                        it.equals(Constant.DV, ignoreCase = true)
                            && (lastName.isEmpty() || zone.isEmpty() || region.isEmpty()) -> {
                            tv_error_message?.text =
                                getString(R.string.you_must_complete_fields2)
                        }
                        else -> return true
                    }
                }
            }
        }
        return false
    }

    private fun validarRegionZona() {
        val codRol: String = presenter.session.codigoRol
        val region: String = presenter.session.region ?: Constant.REGION
        val zona: String = presenter.session.zona ?: Constant.ZONA

        when {
            codRol.equals(Constant.GZ, ignoreCase = true) -> presenter.validarRegionZona(
                region,
                zona
            )
            codRol.equals(Constant.GR, ignoreCase = true) -> presenter.validarRegionZona(
                region,
                et_zona.text.toString()
            )
            codRol.equals(
                Constant.DV,
                ignoreCase = true
            ) -> presenter.validarRegionZona(et_region.text.toString(), et_zona.text.toString())
        }
    }

    private fun alreadyIsActiveConsultant(consultaCrediticiaModel: ConsultaCrediticiaInternaModel?): Boolean {
        return !(!consultaCrediticiaModel?.codigoConsultora.isNullOrEmpty()
            && !consultaCrediticiaModel?.estadoCliente.isNullOrEmpty()
            && Constant.WITHDRAWAL != consultaCrediticiaModel?.estadoCliente)
    }

    private fun loadConsultantInformation(consultaCrediticiaModel: ConsultaCrediticiaInternaModel) {
        if (!consultaCrediticiaModel.primerApellido.isNullOrEmpty()
            && !consultaCrediticiaModel.primerNombre.isNullOrEmpty()
        ) {
            consultaCrediticiaModel.apply {
                setFullName(this)

                tv_identity_document?.text = documentoIdentidad
                tv_code?.text = codigoConsultora
                tv_status?.text = estadoCliente
                tv_debt?.text = deudaBelcorp
                tv_campaign_income?.text = campanaIngreso
                tv_last_billing?.text = ultimaCampanaFacturada
            }
            AnalyticUtils.search(Label.CREDIT_INQUIRY_SEARCH_OK)

        } else {
            tv_title_name?.gone()
            tv_name?.gone()
            tl_belcorp_data?.gone()
            tv_there_is_not_belcorp_data?.visible()
            AnalyticUtils.search(Label.CREDIT_INQUIRY_SEARCH_EMPTY)
        }
    }

    private fun showExternalCreditConsulting(consultaCrediticiaModel: ConsultaCrediticiaInternaModel) {
        val prefs = context?.getSharedPreferences(Constant.USER_PREFERENCES, Context.MODE_PRIVATE)
        val codRol = prefs?.getString(Constant.COD_ROL, Constant.ROL)

        if ((consultaCrediticiaModel.tieneBloqueo != true)
            && consultaCrediticiaModel.valoracionInterna == null
            && isAvailableExternalCreditConsultingCO()
        ) {
            cv_consulnt_external_debt?.gone()
            cv_consulnt_external_no_belcorp?.visible()
            ll_first_lastname?.visible()
            Locale.getDefault().let {

            }
            if (codRol?.toUpperCase(Locale.getDefault())
                == Constant.GR.toUpperCase(Locale.getDefault())
            ) {
                ll_zona?.visible()
            } else if (codRol?.toUpperCase(Locale.getDefault())
                == Constant.DV.toUpperCase(Locale.getDefault())
            ) {
                ll_zona?.visible()
                ll_region?.visible()
            }
            return
        }

        if ((consultaCrediticiaModel.tieneBloqueo != true)
            && consultaCrediticiaModel.valoracionInterna != null
            && isAvailableExternalCreditConsultingCO()
            && Constant.NOT_APT != consultaCrediticiaModel.valoracionInterna
        ) {

            when {
                codRol.equals(Constant.GR, ignoreCase = true) -> {
                    ll_zona?.visible()
                }
                codRol.equals(Constant.DV, ignoreCase = true) -> {
                    ll_zona?.visible()
                    ll_region?.visible()
                }
            }

            cv_consulnt_external_debt?.gone()
            cv_consulnt_external_no_belcorp?.visible()
            et_first_lastname.setText(consultaCrediticiaModel.primerApellido.orEmpty())
            return
        }

        showExternalCreditConsultingOption(consultaCrediticiaModel)

    }

    private fun showExternalCreditConsultingOption(consultaCrediticiaModel: ConsultaCrediticiaInternaModel) {
        if ((consultaCrediticiaModel.valoracionInterna == null || Constant.APT == consultaCrediticiaModel.valoracionInterna)
            && isAvailableExternalCreditConsulting() && (consultaCrediticiaModel.tieneBloqueo != true)
        ) {
            when (presenter.session.countryIso) {
                Constant.COD_COLOMBIA -> cv_consulnt_external_no_belcorp?.visible()
                else -> cv_consulnt_external_debt?.visible()
            }
        } else {
            when (presenter.session.countryIso) {
                Constant.COD_COLOMBIA -> cv_consulnt_external_no_belcorp?.gone()
                else -> cv_consulnt_external_debt?.gone()
            }
        }
    }

    private fun loadInternalCreditConsulting(consultaCrediticiaModel: ConsultaCrediticiaInternaModel) {
        when (consultaCrediticiaModel.tieneBloqueo) {
            true -> {
                cv_motive_lock?.visible()
                showMotiveLock(consultaCrediticiaModel.bloqueos)
                showConsultantResult(false)
            }
            false -> {
                cv_motive_lock?.gone()
                if (!isAvailableExternalCreditConsulting()) {
                    showConsultantResult(true)
                }
            }
        }
    }

    private fun showConsultantResult(consultantIsApt: Boolean) {
        ll_consultant_result?.visible()

        if (consultantIsApt) {
            context?.apply {
                tv_credit_condulting_result?.setTextColor(
                    ContextCompat.getColor(this, R.color.cian)
                )
            }

            iv_credit_condulting_result?.setImageResource(R.drawable.ic_credit_consulting_apt)
            tv_credit_condulting_result?.text = getString(R.string.consultant_is_apt)
        } else {
            iv_credit_condulting_result?.setImageResource(R.drawable.ic_credit_consulting_not_apt)
            tv_credit_condulting_result?.text = getString(R.string.consultant_is_not_apt)
            context?.apply {
                tv_credit_condulting_result?.setTextColor(
                    ContextCompat.getColor(this, R.color.red_error)
                )
            }
        }
    }

    private fun mostrarBuroCrediticioExterno(intStatus: Int, consultantIsApt: Boolean) {
        ll_consultant_result?.visible()

        if (consultantIsApt) {
            context?.apply {
                tv_credit_condulting_result?.setTextColor(
                    ContextCompat.getColor(this, R.color.cian)
                )
            }

            iv_credit_condulting_result?.setImageResource(R.drawable.ic_credit_consulting_apt)
            tv_credit_condulting_result?.text = getString(intStatus)
        } else {
            iv_credit_condulting_result?.setImageResource(R.drawable.ic_credit_consulting_not_apt)
            tv_credit_condulting_result?.text = getString(intStatus)
            context?.apply {
                tv_credit_condulting_result?.setTextColor(
                    ContextCompat.getColor(this, R.color.red_error)
                )
            }
        }
    }

    private fun setFullName(consultaCrediticiaModel: ConsultaCrediticiaModel) {
        tv_title_name?.visible()
        tv_name?.visible()

        val primerNombre = StringUtils.capitalizePhrase(consultaCrediticiaModel.primerNombre)
        val segundoNombre = StringUtils.capitalize(consultaCrediticiaModel.segundoNombre)
        val primerApellido = StringUtils.capitalize(consultaCrediticiaModel.primerApellido)
        val segundoApellido = StringUtils.capitalize(consultaCrediticiaModel.segundoApellido)
        val nombreCompleto = "$primerNombre $segundoNombre $primerApellido $segundoApellido"

        tv_name?.text = nombreCompleto
    }

    private fun setFullName(consultaCrediticiaModel: ConsultaCrediticiaInternaModel) {
        tv_title_name?.visible()
        tv_name?.visible()

        val primerNombre = StringUtils.capitalizePhrase(consultaCrediticiaModel.primerNombre)
        val segundoNombre = StringUtils.capitalize(consultaCrediticiaModel.segundoNombre)
        val primerApellido = StringUtils.capitalize(consultaCrediticiaModel.primerApellido)
        val segundoApellido = StringUtils.capitalize(consultaCrediticiaModel.segundoApellido)
        val nombreCompleto = "$primerNombre $segundoNombre $primerApellido $segundoApellido"

        tv_name?.text = nombreCompleto
    }

    private fun isAvailableExternalCreditConsultingCO(): Boolean {
        return when (presenter.session.countryIso) {
            Constant.COD_COLOMBIA -> true
            else -> false
        }
    }

    private fun isAvailableExternalCreditConsulting(): Boolean {
        return when (presenter.session.countryIso) {
            Constant.COD_COLOMBIA, Constant.COD_PERU, Constant.COD_ECUADOR -> true
            else -> false
        }
    }

    private fun showMotiveLock(motivoBloqueoDeudaInternaModelList: List<BloqueoModel>?) {
        if (motivoBloqueoDeudaInternaModelList?.isNotEmpty() == true) {
            cv_motive_lock?.visible()

            title_bloqueo?.text = String.format(
                getString(R.string.title_bloqueo),
                motivoBloqueoDeudaInternaModelList.size.toString()
            )

            motivoBloqueoDeudaInternaModelList.forEach {
                it.apply {
                    val inflater = LayoutInflater.from(context)
                    val view =
                        inflater.inflate(R.layout.item_motive_lock_layout, ll_motive_lock, false)

                    view.findViewById<MaterialTextView>(R.id.tv_tipo_lock)?.apply {
                        text = tipoBloqueo
                    }

                    view.findViewById<MaterialTextView>(R.id.tv_motive_lock)?.apply {
                        text = String.format(getString(R.string.motivo_bloqueo), motivoBloqueo)
                    }
                    view.findViewById<MaterialTextView>(R.id.tv_obs_lock)?.apply {
                        text = String.format(getString(R.string.obs_bloqueo), observacion)
                    }

                    ll_motive_lock.addView(view)
                }
            }
            showConsultantResult(false)
        }
    }

    private fun mostrarVistaRespuesta() {
        tv_consulting_external_debt?.gone()
        tv_external_debt?.visible()
        tv_consulting_external_no_belcorp?.gone()
        tv_external_no_belcorp?.visible()
    }

    private fun getConsultaCrediticiaExterna() {
        this.presenter.setView(this, this)
        enviarConsultaCrediticiaExterna()
        tv_error_message?.text = Constant.EMPTY_STRING
        ll_check_external_no_belcorp?.gone()
        tv_consulting_external_no_belcorp?.visible()
    }

    private fun enviarConsultaCrediticiaExterna() {
        val prefs = context?.getSharedPreferences(Constant.USER_PREFERENCES, Context.MODE_PRIVATE)
        val codRol = prefs?.getString(Constant.COD_ROL, Constant.ROL)

        when {
            codRol.equals(Constant.GZ, ignoreCase = true) -> {
                if (consultaCrediticiaModel?.valoracionInterna == null) {
                    presenter.searchBuroCredCO(
                        documentoIdentidad = consultaCrediticiaModel?.documentoIdentidad.orEmpty(),
                        primerApellido = et_first_lastname.text.toString(),
                        region = null,
                        zona = null
                    )
                } else {
                    presenter.searchBuroCredCO(
                        documentoIdentidad = consultaCrediticiaModel?.documentoIdentidad.orEmpty(),
                        primerApellido = consultaCrediticiaModel?.primerApellido,
                        region = null,
                        zona = null
                    )
                }
            }
            codRol.equals(Constant.GR, ignoreCase = true) -> {
                if (consultaCrediticiaModel?.valoracionInterna == null) {
                    presenter.searchBuroCredCO(
                        documentoIdentidad = consultaCrediticiaModel?.documentoIdentidad.orEmpty(),
                        zona = et_zona.text.toString(),
                        primerApellido = et_first_lastname.text.toString(),
                        region = null
                    )
                } else {
                    presenter.searchBuroCredCO(
                        documentoIdentidad = consultaCrediticiaModel?.documentoIdentidad.orEmpty(),
                        zona = et_zona.text.toString(),
                        primerApellido = consultaCrediticiaModel?.primerApellido,
                        region = null
                    )
                }
            }
            codRol.equals(Constant.DV, ignoreCase = true) -> {
                if (consultaCrediticiaModel?.valoracionInterna == null) {
                    presenter.searchBuroCredCO(
                        documentoIdentidad = consultaCrediticiaModel?.documentoIdentidad.orEmpty(),
                        region = et_region.text.toString(),
                        zona = et_zona.text.toString(),
                        primerApellido = et_first_lastname.text.toString()
                    )
                } else {
                    presenter.searchBuroCredCO(
                        documentoIdentidad = consultaCrediticiaModel?.documentoIdentidad.orEmpty(),
                        region = et_region.text.toString(),
                        zona = et_zona.text.toString(),
                        primerApellido = consultaCrediticiaModel?.primerApellido
                    )
                }
            }
        }
    }

    override fun mostrarAptPE() {
        mostrarVistaRespuesta()
        showConsultantResult(true)
    }

    override fun mostrarConsultoraNoApt() {
        mostrarVistaRespuesta()
        showConsultantResult(false)
    }

    override fun mostrarNombreCompleto(nombreCompleto: String) {
        if (nombreCompleto.isNotEmpty()) {
            tv_title_name?.visible()
            tv_name?.visible()
            tv_name?.text = nombreCompleto
        } else {
            if (View.VISIBLE != tv_name?.visibility) {
                tv_title_name?.gone()
                tv_name?.gone()
            }
        }
    }

    override fun mostrarMotivos(motivos: List<MotivoModel>) {
        if (!isAttached()) return
        if (motivos.isNotEmpty()) {
            tv_external_debt?.gone()
            tv_external_no_belcorp?.gone()
            ll_external_debts_content?.visible()

            motivos.forEach {
                val layoutInflater: LayoutInflater = LayoutInflater.from(context)
                val view: View = layoutInflater.inflate(
                    R.layout.item_external_debt_layout,
                    ll_external_debts,
                    false
                )

                view.findViewById<MaterialTextView>(R.id.tv_amount)?.apply {
                    text = it.descripcion
                }

                view.findViewById<MaterialTextView>(R.id.tv_currency)?.apply {
                    text = EMPTY_STRING
                }


                ll_external_debts?.addView(view)
            }
        } else {
            tv_external_no_belcorp?.text = getString(R.string.there_are_external_debts)
            tv_external_debt?.text = getString(R.string.there_are_external_debts)
        }
    }


    override fun mostrarExplicaciones(explicaciones: List<ExplicacionModel>) {
        if (!isAttached()) return
        ll_external_debts_content?.visible()
        explicaciones.forEach {
            val layoutInflater: LayoutInflater = LayoutInflater.from(context)
            val view: View =
                layoutInflater.inflate(R.layout.item_external_debt_layout, ll_external_debts, false)

            view.findViewById<MaterialTextView>(R.id.tv_currency)?.apply {
                text = ""
            }

            view.findViewById<MaterialTextView>(R.id.tv_amount)?.apply {
                text = it.valor
            }

            ll_external_debts.addView(view)
        }
        if (explicaciones.isEmpty()) {
            cv_consulnt_external_debt?.gone()
        }
    }

    private fun mostrarEstado(estado: String) {
        if (estado.isNotEmpty()) {
            ll_consultant_result?.visible()
            tv_external_debt?.text = getString(R.string.could_not_do_the_request_retry_late)
            tv_external_no_belcorp?.text = getString(R.string.could_not_do_the_request_retry_late)
            context?.apply {
                tv_credit_condulting_result?.setTextColor(
                    ContextCompat.getColor(this, R.color.cian)
                )
            }

            iv_credit_condulting_result?.setImageResource(R.drawable.ic_credit_consulting_apt)
            tv_credit_condulting_result?.text = estado
        }
    }

    override fun showConsultaCrediticia(consultaCrediticiaModel: ConsultaCrediticiaModel?) {
        tv_consulting_external_debt?.gone()
        tv_external_debt?.visible()
        tv_consulting_external_no_belcorp?.gone()
        tv_external_no_belcorp?.visible()

        consultaCrediticiaModel?.apply {
            if (valoracionExterna.equals(Constant.APT, ignoreCase = true)) {
                if (deudasExternasList?.isNotEmpty() == true) {
                    tv_external_debt?.text = getString(R.string.there_are_external_debts)
                    tv_external_no_belcorp?.text = getString(R.string.there_are_external_debts)

                    showConsultantResult(true)
                } else {
                    tv_external_debt?.text = getString(R.string.there_are_not_external_debts)
                    tv_external_no_belcorp?.text = getString(R.string.there_are_not_external_debts)

                    showConsultantResult(true)
                }

            } else if (valoracionExterna.equals(Constant.NOT_APT, ignoreCase = true)) {
                tv_external_debt?.text = getString(R.string.there_are_external_debts)
                tv_external_no_belcorp?.text = getString(R.string.there_are_external_debts)

                showConsultantResult(false)
            } else if (valoracionExterna.equals(Constant.EMPTY_APT, ignoreCase = true)) {
                tv_external_debt?.text = getString(R.string.empty_result)
                tv_external_no_belcorp?.text = getString(R.string.empty_result)

                showConsultantResult(false)
            }
        }

        if (!consultaCrediticiaModel?.primerApellido.isNullOrEmpty()
            && !consultaCrediticiaModel?.primerNombre.isNullOrEmpty()
            && !consultaCrediticiaModel?.segundoApellido.isNullOrEmpty()
            && !consultaCrediticiaModel?.segundoNombre.isNullOrEmpty()
        ) {
            setFullName(consultaCrediticiaModel!!)
        } else {
            if (tv_name?.visibility != View.VISIBLE) {
                tv_title_name?.gone()
                tv_name?.gone()
            }
        }
    }

    override fun sendResponseValidarRegionZona(response: Int?) {
        when (response) {
            -1 -> tv_error_message?.text = getString(R.string.error_validate_region_zone)
            0 -> getConsultaCrediticiaExterna()
            1 -> tv_error_message?.text = getString(R.string.invalid_region)
            2 -> tv_error_message?.text = getString(R.string.invalid_zone)
            3 -> tv_error_message?.text = getString(R.string.error_validate_region_zone)
        }
    }

    override fun showMessageError(message: String) {
        tv_consulting_external_debt?.gone()
        tv_external_debt?.visible()
        tv_external_debt?.text = message
    }

    override fun mostrarAptCO(intStatus: Int, nombre: String, estado: String) {
        mostrarVistaRespuesta()
        mostrarBuroCrediticioExterno(intStatus, true)
    }

    override fun mostrarNoAptCO(intStatus: Int, nombre: String, estado: String, nomEstado: String) {
        mostrarVistaRespuesta()
        mostrarBuroCrediticioExterno(intStatus, false)

        tv_external_debt?.text = nomEstado
        tv_external_no_belcorp?.text = nomEstado
    }

    override fun mostrarEstadoCO(estado: String) {
        mostrarVistaRespuesta()
        mostrarEstado(estado)
    }

    override fun mostrarBuroEC(dni: String, score: String, fullname: String) {
        mostrarNombreCompleto(fullname)
        mostrarVistaRespuesta()
        showConsultantResult(true)
    }

    override fun mostrarBuroRejectionEC(dni: String, score: String, fullname: String) {
        mostrarNombreCompleto(fullname)
        mostrarVistaRespuesta()
        showConsultantResult(false)
    }

    override fun mostrarResultadoBuroEC(orEmpty: String) {
        cv_consulnt_external_debt?.gone()
    }

    override fun mostrarBuroSV(fullname: String, intStatus: Int) = Unit

    override fun mostrarBuroResultadoNegativoSV(fullname: String, intStatus: Int) {
        when (intStatus) {
            R.string.buro_sin_revisar_sv -> Unit
            R.string.buro_sin_resultado_sv -> Unit
            R.string.buro_error_intente_nuevamente_sv -> Unit
        }
    }

    override fun mostrarBuroSinResultadosSV(fullname: String, intStatus: Int) = Unit

    override fun mostrarBuroResultadoCL(resultado: String) {
        tv_external_debt?.text = resultado
        tv_external_no_belcorp?.text = resultado
    }

    override fun setNewTitle(title: String) {
        tv_credit_consulting_title.text = title
    }

    override fun getFragment(): BaseFragment? {
        return this
    }
}

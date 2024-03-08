package biz.belcorp.salesforce.modules.creditinquiry.features.presenters

import biz.belcorp.salesforce.core.domain.exceptions.UnauthorizedException
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.creditinquiry.R
import biz.belcorp.salesforce.modules.creditinquiry.core.domain.entity.ConsultaCrediticia
import biz.belcorp.salesforce.modules.creditinquiry.core.domain.entity.ConsultaCrediticia2
import biz.belcorp.salesforce.modules.creditinquiry.core.domain.exception.DefaultErrorBundle
import biz.belcorp.salesforce.modules.creditinquiry.core.domain.exception.ErrorBundle
import biz.belcorp.salesforce.modules.creditinquiry.core.domain.usecases.ConsultaCrediticiaUseCase
import biz.belcorp.salesforce.modules.creditinquiry.features.ConsultaCrediticiaExtView
import biz.belcorp.salesforce.modules.creditinquiry.features.ConsultaCrediticiaView
import biz.belcorp.salesforce.modules.creditinquiry.features.FilterCreditInquiryFragment
import biz.belcorp.salesforce.modules.creditinquiry.features.mapper.ConsultaCrediticiaModelDataMapper
import biz.belcorp.salesforce.modules.creditinquiry.features.model.ConsultaCrediticia2Model
import biz.belcorp.salesforce.modules.creditinquiry.features.model.ConsultaCrediticiaInterna
import biz.belcorp.salesforce.modules.creditinquiry.features.util.Constant
import biz.belcorp.salesforce.modules.creditinquiry.features.util.Constant.APROVE
import biz.belcorp.salesforce.modules.creditinquiry.features.util.Constant.APROVED
import biz.belcorp.salesforce.modules.creditinquiry.features.util.Constant.APROVE_AAA
import biz.belcorp.salesforce.modules.creditinquiry.features.util.Constant.BLANK_SPACE
import biz.belcorp.salesforce.modules.creditinquiry.features.util.Constant.CHECK
import biz.belcorp.salesforce.modules.creditinquiry.features.util.Constant.EQUIFAX
import biz.belcorp.salesforce.modules.creditinquiry.features.util.Constant.REJECTED
import biz.belcorp.salesforce.modules.creditinquiry.features.util.Constant.REJECTION
import biz.belcorp.salesforce.modules.creditinquiry.features.util.Constant.RESULT_A
import biz.belcorp.salesforce.modules.creditinquiry.features.util.Constant.RESULT_N
import biz.belcorp.salesforce.modules.creditinquiry.features.util.Constant.SENTINEL
import biz.belcorp.salesforce.modules.creditinquiry.utils.customMessage

class ConsultaCrediticiaPresenter constructor(
    private val useCase: ConsultaCrediticiaUseCase,
    private val sessionUseCase: ObtenerSesionUseCase,
    private val consultaCrediticiaModelDataMapper: ConsultaCrediticiaModelDataMapper
) {

    val session get() = sessionUseCase.obtener()

    private var consultaCrediticiaView: ConsultaCrediticiaView? = null
    private var consultaCrediticiaExtView: ConsultaCrediticiaExtView? = null

    fun setView(consultaCrediticiaView: ConsultaCrediticiaView) {
        this.consultaCrediticiaView = consultaCrediticiaView
    }

    fun setView(
        consultaCrediticiaView: ConsultaCrediticiaView,
        consultaCrediticiaExtView: ConsultaCrediticiaExtView
    ) {
        this.consultaCrediticiaView = consultaCrediticiaView
        this.consultaCrediticiaExtView = consultaCrediticiaExtView
    }

    fun checkTitle() {
        val res = consultaCrediticiaView?.getFragment()?.resources

        consultaCrediticiaView?.setNewTitle(
            if (Constant.COUNTRIES_NEW_TITLE.contains(session.countryIso.toUpperCase())) {
                res?.getString(R.string.credit_consulting_toolbar_new) ?: DEFAULT_TITLE
            } else {
                res?.getString(R.string.credit_consulting_toolbat) ?: DEFAULT_TITLE
            }
        )
    }

    fun destroy() {
        this.consultaCrediticiaView = null
        this.consultaCrediticiaExtView = null
    }

    fun searchConsultaCrediticiaDeudaInterna(documentoIdentidad: String) {
        this.useCase.consultaCrediticiaDeudaInterna(
            session.countryIso,
            documentoIdentidad,
            ConsultaCrediticiaInternaSubscriber()
        )
    }

    fun validarRegionZona(region: String, zona: String) {
        this.useCase.validarRegionZona(region, zona, ValidarRegionZonaSubscriber())
    }

    fun searchConsultaCrediticiaDeudaExterna(documentoIdentidad: String) {
        this.useCase.consultaCrediticiaDeudaExterna(
            documentoIdentidad,
            ConsultaCrediticiaSubscriber2()
        )
    }

    fun searchBuroCredCO(
        documentoIdentidad: String,
        region: String?,
        zona: String?,
        primerApellido: String?
    ) {
        this.useCase.consultaBelcorpBuroCrediticioCO(
            documentoIdentidad,
            region,
            zona,
            primerApellido,
            ConsultaCrediticiaSubscriber()
        )
    }

    private fun showConsultaCrediticia(consultaCrediticia: ConsultaCrediticia) {
        val model =
            this.consultaCrediticiaModelDataMapper.parseConsultaCrediticia(consultaCrediticia)
        this.consultaCrediticiaView?.showConsultaCrediticia(model)
    }

    private fun showConsultaCrediticia(consultaCrediticia: ConsultaCrediticiaInterna) {
        val model =
            this.consultaCrediticiaModelDataMapper.parseConsultaCrediticia(consultaCrediticia)
        this.consultaCrediticiaView?.showConsultaCrediticia(model)
    }

    private fun showConsultaCrediticia(consultaCrediticia: ConsultaCrediticia2) {
        val model = this.consultaCrediticiaModelDataMapper
            .parseConsultaCrediticia(consultaCrediticia)
        var fullName = ""

        if (!model.primerNombre.isNullOrEmpty() and !model.primerApellido.isNullOrEmpty()) {
            fullName = getFullname(model = model)
        }
        this.consultaCrediticiaExtView?.mostrarNombreCompleto(fullName)

        when (session.countryIso) {
            Constant.COD_PERU -> manejarCredicitiaPE(model)
            Constant.COD_COLOMBIA -> manejarCrediticiaCO(model)
            Constant.COD_ECUADOR -> manejarCrediticiaEC(model)
            Constant.COD_SALVADOR -> manejarCrediticiaSV(model)
            Constant.COD_CHILE -> manejarCrediticiaSV(model)
        }
    }

    private fun getFullname(model: ConsultaCrediticia2Model) =
        model.primerNombre.orEmpty() +
            " ${model.segundoNombre.orEmpty()}" +
            " ${model.primerApellido.orEmpty()}" +
            " ${model.segundoApellido.orEmpty()}"

    private fun manejarCrediticiaSV(model: ConsultaCrediticia2Model) {
        if (model.resultado.isNullOrEmpty()) {
            this.consultaCrediticiaExtView?.mostrarBuroSinResultadosSV(
                model.nombreCompleto.orEmpty(),
                R.string.buro_error_intente_nuevamente_sv
            )
        } else {
            when (model.preCalificacion?.precalificalificacion) {
                REJECTED -> Unit
                APROVED -> Unit
                CHECK -> Unit
            }
        }
    }

    private fun manejarCrediticiaEC(model: ConsultaCrediticia2Model) {
        when (model.estado) {
            REJECTION -> {
                this.consultaCrediticiaExtView?.mostrarBuroRejectionEC(
                    model.docIdentidad.orEmpty(),
                    model.score.orEmpty(),
                    model.nombreCompleto.orEmpty()
                )
            }
            APROVE, APROVE_AAA -> {
                this.consultaCrediticiaExtView?.mostrarBuroEC(
                    model.docIdentidad.orEmpty(),
                    model.score.orEmpty(),
                    model.nombreCompleto.orEmpty()
                )
            }
            BLANK_SPACE -> {
                this.consultaCrediticiaExtView?.mostrarResultadoBuroEC(model.resultado.orEmpty())
            }
        }
    }

    private fun manejarCrediticiaCO(model: ConsultaCrediticia2Model) {
        when {
            model.resultado.equals(RESULT_A, ignoreCase = true) -> {
                this.consultaCrediticiaExtView?.mostrarAptCO(
                    R.string.consultant_is_apt_co,
                    model.nombreCompleto.orEmpty(),
                    model.estado.orEmpty()
                )
            }
            model.resultado.equals(RESULT_N, ignoreCase = true) -> {
                this.consultaCrediticiaExtView?.mostrarNoAptCO(
                    R.string.consultant_is_not_apt_co,
                    model.nombreCompleto.orEmpty(),
                    model.estado.orEmpty(),
                    model.nomEstado.orEmpty()
                )
            }
            else -> {
                this.consultaCrediticiaExtView?.mostrarEstadoCO(model.estado.orEmpty())
            }
        }
    }

    private fun manejarCredicitiaPE(model: ConsultaCrediticia2Model) {
        if (model.enumEstadoCrediticio == 2 || model.enumEstadoCrediticio == 1) {
            this.consultaCrediticiaExtView?.mostrarAptPE()
            this.consultaCrediticiaExtView?.mostrarExplicaciones(model.explicaciones.orEmpty())
        } else if (model.enumEstadoCrediticio == 3) {
            this.consultaCrediticiaExtView?.mostrarConsultoraNoApt()

            when {
                model.buro.equals(SENTINEL, ignoreCase = true) -> {
                    this.consultaCrediticiaExtView?.mostrarMotivos(model.motivos.orEmpty())
                }
                model.buro.equals(EQUIFAX, ignoreCase = true) -> {
                    this.consultaCrediticiaExtView?.mostrarExplicaciones(model.explicaciones.orEmpty())
                }
            }
        }
    }

    private fun sendResponseValidarRegionZona(response: Int?) {
        this.consultaCrediticiaView?.sendResponseValidarRegionZona(response)
    }

    fun showMessageError(errorBundle: ErrorBundle) {
        try {
            val errorMessage = errorBundle.exception.customMessage()
            this.consultaCrediticiaView?.showMessageError(errorMessage)
        } catch (e1: UnauthorizedException) {
            e1.printStackTrace()
        }

    }

    private inner class ConsultaCrediticiaSubscriber2 : BaseSingleObserver<ConsultaCrediticia2>() {

        override fun onError(e: Throwable) {
            super.onError(e)
            this@ConsultaCrediticiaPresenter.showMessageError(DefaultErrorBundle(e as Exception))
        }

        override fun onSuccess(t: ConsultaCrediticia2) {
            this@ConsultaCrediticiaPresenter.showConsultaCrediticia(t)
        }
    }


    private inner class ConsultaCrediticiaInternaSubscriber :
        BaseSingleObserver<ConsultaCrediticiaInterna>() {

        override fun onError(e: Throwable) {
            super.onError(e)
            this@ConsultaCrediticiaPresenter.showMessageError(DefaultErrorBundle(e as Exception))
        }

        override fun onSuccess(t: ConsultaCrediticiaInterna) {
            this@ConsultaCrediticiaPresenter.showConsultaCrediticia(t)
        }
    }

    private inner class ConsultaCrediticiaSubscriber : BaseSingleObserver<ConsultaCrediticia2>() {

        override fun onError(e: Throwable) {
            super.onError(e)
            this@ConsultaCrediticiaPresenter.showMessageError(DefaultErrorBundle(e as Exception))
        }

        override fun onSuccess(t: ConsultaCrediticia2) {
            this@ConsultaCrediticiaPresenter.showConsultaCrediticia(t)
        }
    }


    private inner class ValidarRegionZonaSubscriber : BaseSingleObserver<Int>() {

        override fun onError(e: Throwable) {
            super.onError(e)
            this@ConsultaCrediticiaPresenter.showMessageError(DefaultErrorBundle(e as Exception))
        }

        override fun onSuccess(t: Int) {
            this@ConsultaCrediticiaPresenter.sendResponseValidarRegionZona(t)
        }
    }

    companion object {
        private const val DEFAULT_TITLE = "CONSULTA DE DEUDA"
    }
}

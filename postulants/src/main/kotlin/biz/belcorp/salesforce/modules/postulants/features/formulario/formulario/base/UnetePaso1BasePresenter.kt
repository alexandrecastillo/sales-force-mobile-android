package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base

import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.exceptions.NetworkConnectionException
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.BuroResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.ParametroUnete
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.TablaLogica
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.*
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.DefaultErrorBundle
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ErrorBundle
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ErrorMessageFactory
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.ParametroUneteUseCase
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.PostulantesUseCase
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.TablaLogicaUseCase
import biz.belcorp.salesforce.modules.postulants.features.entities.SexoModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.mapper.TablaLogicaModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso1.UnetePaso1View
import biz.belcorp.salesforce.modules.postulants.features.mapper.ParametroUneteModelDataMapper
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import java.text.SimpleDateFormat
import java.util.*

abstract class UnetePaso1BasePresenter(
    private val useCase: PostulantesUseCase,
    private val mapParametroUnete: ParametroUneteModelDataMapper,
    private val errorMessageFactory: ErrorMessageFactory,
    private val mapTablaLogica: TablaLogicaModelDataMapper,
    private val useCaseSession: ObtenerSesionUseCase,
    private val tablaLogicaUseCase: TablaLogicaUseCase,
    private val parametroUneteUseCase: ParametroUneteUseCase,
    private val textResolver: PostulantTextResolver
) {

    var mView: UnetePaso1View? = null
    private var postulanteDocumento: String? = null

    val sesion get() = requireNotNull(useCaseSession.obtener())

    fun setView(vw: UnetePaso1View) {
        this.mView = vw
    }

    fun showErrorMessage(errorBundle: ErrorBundle) {
        errorMessageFactory.create(errorBundle.exception) {
            onDefaultError { mView?.showError(it) }
            onSolicitudRechazadaError { mView?.showSolicitudRechazada(it) }
        }
    }

    fun hideViewLoading() {
        mView?.hideLoading()
    }

    fun showLoading() {
        mView?.showLoading()
    }

    fun showLoading(msg: String) {
        mView?.showLoading(msg)
    }

    fun destroyFather() {
        useCase.dispose()
        mView = null
    }

    fun showSexo() {
        val lista = arrayListOf<SexoModel>()

        var p = SexoModel()

        p.codigo = Constant.LETTER_F
        p.descripcion = Constant.FEMENINO
        lista.add(p)

        p = SexoModel()
        p.codigo = Constant.LETTER_M
        p.descripcion = Constant.MASCULINO
        lista.add(p)

        mView?.showGeneros(lista)
    }

    fun doOnFirstName(firstName: String) {
        if (firstName.isNotEmpty()) {
            mView?.mostrarPrimerNombre(firstName)
        }
    }

    fun doOnSecondName(secondName: String) {
        if (secondName.isNotEmpty()) {
            mView?.mostrarSegundoNombre(secondName)
        }
    }

    fun doOnFirstLasname(firstLastName: String) {
        if (firstLastName.isNotEmpty()) {
            mView?.mostrarPrimerApellido(firstLastName)
        }
    }

    fun doOnSecondLastname(secondLastname: String) {
        if (secondLastname.isNotEmpty()) {
            mView?.mostrarSegundoApellido(secondLastname)
        }
    }

    fun doOnBirthDay(fechaNacimiento: String?) {
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        val strFecha = if (!fechaNacimiento.isNullOrEmpty()) {
            try {
                val date: Date = inputFormat.parse(fechaNacimiento)
                outputFormat.format(date)
            } catch (ex: Exception) {
                Constant.EMPTY_STRING
            }
        } else {
            Constant.EMPTY_STRING
        }

        mView?.mostrarfechaNacimiento(strFecha)
    }

    inner class DocumentExistSubscriber : BaseSingleObserver<Boolean>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
        }

        override fun onSuccess(t: Boolean) {
            if (t) {
                mView?.documentoNoDuplicado()
            } else {
                mView?.showDuplicateDocumentNumber(postulanteDocumento)
            }
        }
    }

    inner class TipoDocumentoSubscriber : BaseSingleObserver<List<ParametroUnete>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<ParametroUnete>) {
            val y = mapParametroUnete.map(t)
            mView?.showTipoDocumento(y)
        }
    }

    inner class RegimenContableSubscriber : BaseSingleObserver<List<ParametroUnete>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<ParametroUnete>) {
            val y = mapParametroUnete.map(t)
            mView?.showRegimenContable(y.sortedBy { it.idParametroUnete })
        }
    }

    inner class NacionalidadSubscriber : BaseSingleObserver<List<TablaLogica>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<TablaLogica>) {
            val y = mapTablaLogica.map(t)
            mView?.showNacionalidad(y)
        }
    }

    inner class ValidarBloqueosSubscriber : BaseSingleObserver<BuroResponse>() {

        override fun onError(exception: Throwable) {
            hideViewLoading()
            Logger.loge(javaClass.simpleName, exception.message)

            if (exception is NetworkConnectionException && sesion.pais?.codigoIso ?: "" == Pais.SALVADOR.codigoIso) {
                mView?.showErrorConnectionMessageSV()
            } else {
                val b = BuroResponse()
                b.bloqueosInternos = false
                b.enumEstadoCrediticioID = Constant.NUMERO_UNO
                b.estadoCrediticio = true
                b.esConsultora = false
                b.esPostulante = false
                b.bloqueosExternos = false
                b.mensajeError = Constant.NULL_STRING
                b.estadoActividad = Constant.NUMERO_UNO
                b.nombreCompleto = Constant.EMPTY_STRING
                b.offline = true

                if (sesion.countryIso == Pais.BOLIVIA.codigoIso) {
                    b.enumEstadoCrediticioID = Constant.NUMERO_DOS
                }

                if (sesion.countryIso == Pais.PERU.codigoIso) {
                    b.enumEstadoCrediticioID = UneteEstadoCrediticio.SIN_CONSULTAR.value
                }

                validate(b, UneteResponse.SUCCESS)
            }
        }

        override fun onSuccess(t: BuroResponse) {

            hideViewLoading()

            if (sesion.countryIso == Pais.PERU.codigoIso) {
                t.enumEstadoCrediticioID = UneteEstadoCrediticio.SIN_CONSULTAR.value
            }

            doOnFirstName(t.primerNombre)
            doOnSecondName(t.segundoNombre)
            doOnFirstLasname(t.primerApellido)
            doOnSecondLastname(t.segundoApellido)
            doOnBirthDay(t.fechaNacimiento)

            if (sesion.countryIso != Pais.COSTARICA.codigoIso) {
                t.bloqueosExternos = getBloqueoExterno(
                    t.enumEstadoCrediticioID
                        ?: Constant.NUMERO_CERO
                )
            }
            if (esCondicionadaEnCR(t.enumEstadoCrediticioID ?: Constant.NUMERO_CERO)) {
                mView?.showModal(textResolver.getPostulantTitle(), t.mensajeError)
            }

            t.mensajeError =
                if (t.mensajeError == Constant.EMPTY_STRING) Constant.NULL_STRING else t.mensajeError

            validate(t, UneteResponse.SUCCESS)
        }
    }

    inner class ValidarMailSubscriber : BaseSingleObserver<Boolean>() {

        override fun onError(exception: Throwable) {
            hideViewLoading()
            mView?.showValidMail()
        }

        override fun onSuccess(t: Boolean) {
            hideViewLoading()
            if (t) {
                mView?.showDuplicateMail()
            } else {
                mView?.showValidMail()
            }
        }
    }

    fun validate(bloqueos: BuroResponse, error: UneteResponse) {
        val response = textResolver.getErrorMessage(error)

        when (error) {
            UneteResponse.ERROR_GENERIC, UneteResponse.ERROR_SERVICE -> mView?.showModal(
                response.first, response.second
            )
            else -> validarBloqueos(bloqueos)
        }
    }

    fun getBloqueoExterno(valueBloqueoExterno: Int): Boolean {
        return !(UneteEstadoCrediticio.PUEDE_SER_CONSULTORA.value == valueBloqueoExterno ||
            UneteEstadoCrediticio.SIN_CONSULTAR.value == valueBloqueoExterno ||
            UneteEstadoCrediticio.CONDICIONADA_FIADOR.value == valueBloqueoExterno ||
            UneteEstadoCrediticio.CONDICIONADA_CARTA_DESCARGO.value == valueBloqueoExterno ||
            UneteEstadoCrediticio.CARTA_DESCARGO_Y_FIADOR.value == valueBloqueoExterno ||
            UneteEstadoCrediticio.CONDICIONADA.value == valueBloqueoExterno ||
            UneteEstadoCrediticio.SUJETO_NO_ENCONTRADO.value == valueBloqueoExterno)
    }

    fun esCondicionadaEnCR(valueBloqueoExterno: Int): Boolean {
        return esCR() && valueBloqueoExterno in listOf(
            UneteEstadoCrediticio.CONDICIONADA_FIADOR.value,
            UneteEstadoCrediticio.CONDICIONADA_CARTA_DESCARGO.value,
            UneteEstadoCrediticio.CARTA_DESCARGO_Y_FIADOR.value,
            UneteEstadoCrediticio.CONDICIONADA.value
        )
    }

    fun validarBloqueos(bloqueos: BuroResponse) {
        val response = validateBloqueos(bloqueos)
        val message = textResolver.getBuroMessage(sesion.countryIso, response, bloqueos)

        when (response) {
            UneteResponse.ES_POSTULANTE_RECHAZADA, UneteResponse.ES_CONSULTORA,
            UneteResponse.ES_POSTULANTE, UneteResponse.BLOQUEOS_INTERNOS, UneteResponse.ES_POTENCIAL
            -> mView?.showModal(message.first, message.second)
            UneteResponse.BLOQUEOS_EXTERNOS -> mView?.validarZonaPagoDeContado(bloqueos)
            else -> {
                devolverDetallesBloqueos(bloqueos)
                mView?.showBody(true)

                if (bloqueos.respuestaServicio == UneteRespuestaServicio.ERR_INDIVIDUAL_NOT_FOUND.value) {
                    mView?.showBloqueos(bloqueos)
                }
            }
        }
    }

    abstract fun devolverDetallesBloqueos(bloqueos: BuroResponse)

    fun validateBloqueos(bloqueos: BuroResponse): UneteResponse {
        return if (esCondicionadaEnCR(bloqueos.enumEstadoCrediticioID ?: Constant.NUMERO_CERO)) {
            UneteResponse.SUCCESS
        } else if (bloqueos.esConsultora == true) {
            UneteResponse.ES_CONSULTORA
        } else if (bloqueos.esPostulante == true) {
            UneteResponse.ES_POSTULANTE
        } else if (bloqueos.esPotencial == true) {
            UneteResponse.ES_POTENCIAL
        } else if (bloqueos.bloqueosInternos == true) {
            UneteResponse.BLOQUEOS_INTERNOS
        } else if (bloqueos.bloqueosExternos == true) {
            UneteResponse.BLOQUEOS_EXTERNOS
        } else if (bloqueos.enumEstadoCrediticioID ?: Constant.NUMERO_CERO == Constant.NUMERO_TRES) {
            UneteResponse.ES_POSTULANTE_RECHAZADA
        } else {
            UneteResponse.SUCCESS
        }
    }

    fun getMessageBloqueosExternos(): String {
        return textResolver.getMessageBloqueExterno(sesion.countryIso)
    }

    fun listNacionalidad() {
        tablaLogicaUseCase.list(NacionalidadSubscriber(), UneteTablaLogica.NACIONALIDAD.value)
    }

    fun listTipoDocumento() {
        parametroUneteUseCase.list(TipoDocumentoSubscriber(), UneteTipoParametro.TIPODOCUMENTO.tipo)
    }

    fun listRegimenContable() {
        parametroUneteUseCase.list(
            RegimenContableSubscriber(),
            UneteTipoParametro.REGIMENCONTABLE.tipo
        )
    }

    fun listSexo() {
        showSexo()
    }

    fun validarMail(pais: String, mail: String, numeroDocumento: String) {
        showLoading()
        useCase.validarMail(ValidarMailSubscriber(), pais, mail, numeroDocumento)
    }

    fun esCR(): Boolean {
        return sesion.countryIso == Pais.COSTARICA.codigoIso
    }

    fun validarDocumento(numeroDocumento: String?) {
        postulanteDocumento = numeroDocumento
        useCase.existeNumeroDocumento(DocumentExistSubscriber(), checkNotNull(numeroDocumento))
    }
}

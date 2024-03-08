package biz.belcorp.salesforce.modules.postulants.features.evaluation

import android.content.Context
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.exceptions.NetworkConnectionException
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.modules.postulants.R
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.BuroResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.ParametroUnete
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteTipoParametro
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.ParametroUneteUseCase
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.ValidarBloqueosUseCase
import biz.belcorp.salesforce.modules.postulants.features.adapter.validacionmensaje.ValidacionMensajeModel
import biz.belcorp.salesforce.modules.postulants.features.base.Presenter
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base.PostulantTextResolver
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base.validationMessageTextResolver.ValidationMessageCL as CL
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base.validationMessageTextResolver.ValidationMessageDefault as Default
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base.validationMessageTextResolver.ValidationMessagePE as PE
import biz.belcorp.salesforce.modules.postulants.features.mapper.ParametroUneteModelDataMapper
import biz.belcorp.salesforce.modules.postulants.utils.Constant

class PostulantEValuationPresenter(
    private val context: Context,
    private val parametroUneteUseCase: ParametroUneteUseCase,
    private val mapParametroUnete: ParametroUneteModelDataMapper,
    private val useCaseSession: ObtenerSesionUseCase,
    private val bloqueosUseCase: ValidarBloqueosUseCase,
    private val textResolver: PostulantTextResolver
) : Presenter {

    private var mView: PostulantEvaluationView? = null

    val sesion get() = requireNotNull(useCaseSession.obtener())

    fun setView(mView: PostulantEvaluationView) {
        this.mView = mView

        this.mView?.initViews(sesion.countryIso, sesion.rol.codigoRol)
    }

    fun listTipoDocumento() {
        mView?.showLoading()
        parametroUneteUseCase.list(TipoDocumentoSubscriber(), UneteTipoParametro.TIPODOCUMENTO.tipo)
    }

    inner class TipoDocumentoSubscriber : BaseSingleObserver<List<ParametroUnete>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<ParametroUnete>) {
            val y = mapParametroUnete.map(t)
            mView?.showTipoDocumento(y)
            mView?.hideLoading()
        }
    }

    fun onDestroy() {
        parametroUneteUseCase.dispose()
    }

    fun evaluarPostulante(
        tipoDocumento: String = Constant.EMPTY_STRING,
        documento: String,
        apellido: String = Constant.EMPTY_STRING,
        seccion: String = Constant.EMPTY_STRING
    ) {
        mView?.showLoading()
        bloqueosUseCase.evaluarPostulante(
            useCaseSuscriber = ValidarBloqueosSubscriber(),
            documento = documento,
            tipoDocumento = tipoDocumento,
            apellido = apellido,
            secc = seccion
        )
    }

    inner class ValidarBloqueosSubscriber : BaseSingleObserver<BuroResponse>() {

        override fun onError(exception: Throwable) {
            mView?.hideLoading()
            Logger.loge(javaClass.simpleName, exception.message)

            if (exception is NetworkConnectionException && sesion.pais?.codigoIso ?: "" == Pais.SALVADOR.codigoIso) {
                mView?.showErrorConnectionMessage()
            } else {
                mView?.showError(exception.message ?: "")
            }
        }

        override fun onSuccess(t: BuroResponse) {
            val messages: List<ValidacionMensajeModel> =
                if (t.bloqueosInternos == true && (arrayListOf<String>(Pais.COLOMBIA.codigoIso).contains(
                        sesion.pais?.codigoIso
                    ))
                ) {
                    var newMessage =
                        "${t.valoracionBelcorp ?: Constant.EMPTY_STRING} - ${t.deudaBelcorp ?: Constant.EMPTY_STRING}"

                    newMessage += if ((t.bloqueos ?: listOf()).isNotEmpty()) {
                        "\n${(t.bloqueos?.get(0)?.motivoBloqueo) ?: Constant.EMPTY_STRING}\n${
                            t.bloqueos?.get(
                                0
                            )?.observacion ?: Constant.EMPTY_STRING
                        }"
                    } else {
                        Constant.EMPTY_STRING
                    }

                    listOf(
                        ValidacionMensajeModel(
                            title = ValidacionMensajeModel.Text(
                                value = context.getString(R.string.unete_paso1_valid_postulante_title_datos_belcorp),
                                color = context.getColor(R.color.black),
                                isVisible = true
                            ),
                            subtitle = ValidacionMensajeModel.Text(
                                value = textResolver.getSubtitleMessageDatosBelcorp(false),
                                isVisible = true,
                                color = context.getColor(R.color.estado_negativo)
                            ),
                            description = ValidacionMensajeModel.Text(
                                value = newMessage,
                                isVisible = true,
                            )
                        )
                    )
                } else {
                    when (t.enumEstadoCrediticioID) {

                        5 -> {
                            listOf(
                                ValidacionMensajeModel(
                                    title = ValidacionMensajeModel.Text(
                                        value = context.getString(R.string.unete_paso1_valid_postulante_title),
                                        color = context.getColor(R.color.black),
                                        isVisible = true
                                    ),
                                    subtitle = ValidacionMensajeModel.Text(
                                        isVisible = false,
                                    ),
                                    description = ValidacionMensajeModel.Text(
                                        value = "Ocurrió un error inesperado, vuelve a intentarlo.",
                                        isVisible = true,
                                    )
                                )
                            )
                        }

                        else -> {
                            getBuroMessage(
                                countryIso = sesion.pais?.codigoIso ?: Pais.COLOMBIA.codigoIso,
                                responses = validateBloqueos(t),
                                bloqueos = t
                            )
                        }
                    }
                }

            mView?.onSuccess(t, messages)

            mView?.onShowName(if (t.nombreCompleto.isNullOrEmpty()) "${t.primerNombre} ${t.segundoNombre} ${t.primerApellido} ${t.segundoApellido}" else "${t.nombreCompleto}")

            mView?.hideLoading()
        }
    }

    fun validateBloqueos(bloqueos: BuroResponse): List<UneteResponse> {
        val result = mutableListOf<UneteResponse>()

        if (bloqueos.esConsultora == true) {
            result.add(UneteResponse.ES_CONSULTORA)
        }

        if (bloqueos.esPostulante == true) {
            result.add(UneteResponse.ES_POSTULANTE)
        }

        if (bloqueos.bloqueosInternos == true) {
            result.add(UneteResponse.BLOQUEOS_INTERNOS)
        }

        if (bloqueos.bloqueosExternos == true ||
            bloqueos.enumEstadoCrediticioID ?: Constant.NUMERO_CERO == Constant.NUMERO_TRES
        ) {
            result.add(UneteResponse.BLOQUEOS_EXTERNOS)
        }

        return result
    }

    fun getBuroMessage(
        countryIso: String, responses: List<UneteResponse>, bloqueos: BuroResponse
    ): List<ValidacionMensajeModel> {
        return when (Pais.find(countryIso)) {
            Pais.PERU -> PE().getMessages(context, responses, bloqueos)
            Pais.CHILE -> CL().getMessages(context, responses, bloqueos)
            else -> Default().getMessages(context, countryIso, responses, bloqueos)
        }
    }

}

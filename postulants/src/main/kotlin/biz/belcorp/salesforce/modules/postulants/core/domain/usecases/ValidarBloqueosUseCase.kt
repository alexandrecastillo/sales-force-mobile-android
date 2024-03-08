package biz.belcorp.salesforce.modules.postulants.core.domain.usecases

import biz.belcorp.salesforce.core.constants.CountryISO
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.BuroResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.Postulante
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.PaisUnete
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteEstadoCrediticio
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.PostulanteBloqueadaException
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.ConsultaCrediticiaRepository
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.PostulantesRepository
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import io.reactivex.Single

class ValidarBloqueosUseCase(
    private val postulanteRepository: PostulantesRepository,
    private val crediticiaRepository: ConsultaCrediticiaRepository,
    private val sessionUsecase: ObtenerSesionUseCase,
    threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun validarBloqueosPaso1(
        useCaseSubscriber: BaseSingleObserver<BuroResponse>, documento: String,
        tipoDocumento: String, postulante: Postulante
    ) {

        val sesion = sessionUsecase.obtener()

        val username = sesion.username
        val pais = sesion.countryIso
        val region = sesion.region.orEmpty()
        val zona = sesion.zona.orEmpty()
        val seccion = sesion.seccion.orEmpty()

        when (pais) {
            CountryISO.SALVADOR -> {
                val single = validarPostulanteApta(pais, tipoDocumento, documento, Origen.UNETE)
                    .flatMap { validarCrediticiaInterna(it, pais, documento, Origen.UNETE) }
                    .flatMap { agregarFirma(postulante, it) }
                    .flatMap {
                        validarCrediticiaExterna(
                            it,
                            username,
                            pais,
                            documento,
                            region,
                            zona,
                            tipoDocumento,
                            seccion,
                            Origen.UNETE
                        )
                    }
                    .onErrorResumeNext { ex ->
                        if (ex is PostulanteBloqueadaException) {
                            Single.create {
                                it.onSuccess(ex.buroResponse)
                            }
                        } else {
                            Single.error(ex)
                        }
                    }

                execute(single, useCaseSubscriber)
            }
            CountryISO.PERU -> {
                val single = validarPostulanteApta(pais, tipoDocumento, documento, Origen.UNETE)
                    .flatMap { validarCrediticiaInterna(it, pais, documento, Origen.UNETE) }
                    .onErrorResumeNext { ex ->
                        if (ex is PostulanteBloqueadaException) {
                            Single.create {
                                it.onSuccess(ex.buroResponse)
                            }
                        } else {
                            Single.error(ex)
                        }
                    }

                execute(single, useCaseSubscriber)
            }
            else -> {
                val single = validarPostulanteApta(pais, tipoDocumento, documento, Origen.UNETE)
                    .flatMap { validarCrediticiaInterna(it, pais, documento, Origen.UNETE) }
                    .flatMap {
                        validarCrediticiaExterna(
                            it,
                            username,
                            pais,
                            documento,
                            region,
                            zona,
                            tipoDocumento,
                            seccion,
                            Origen.UNETE
                        )
                    }
                    .onErrorResumeNext { ex ->
                        if (ex is PostulanteBloqueadaException) {
                            Single.create {
                                it.onSuccess(ex.buroResponse)
                            }
                        } else {
                            Single.error(ex)
                        }
                    }

                execute(single, useCaseSubscriber)
            }
        }
    }

    private fun validarPostulanteApta(
        pais: String,
        tipoDocumento: String,
        documento: String,
        origen: Origen
    ): Single<BuroResponse> {
        return postulanteRepository.postulanteApta(pais, tipoDocumento, documento)
            .map {

                val buroResponse = BuroResponse()
                when (it.tipoError) {
                    Constant.NUMERO_TRES -> {
                        buroResponse.esConsultora = true
                        buroResponse.esPostulante = false
                        buroResponse.esPotencial = false
                    }
                    Constant.NUMERO_CUATRO -> {
                        buroResponse.esConsultora = false
                        buroResponse.esPostulante = true
                        buroResponse.esPotencial = false
                    }

                    Constant.NUMERO_SIETE -> {
                        buroResponse.esConsultora = false
                        buroResponse.esPostulante = false
                        buroResponse.esPotencial = true
                    }
                }
                buroResponse.nombreCompleto = it.nombreCompleto
                buroResponse.mensajeError = it.mensajeError.orEmpty()
                buroResponse.estadoActividad = it.idEstadoActividad
                buroResponse.tipoSolicitud = it.tipoSolicitud

                if (!it.esApta && (origen == Origen.UNETE || !PaisUnete.paisesAllErrors.contains(pais))) {
                    throw PostulanteBloqueadaException(buroResponse)
                }

                buroResponse
            }
    }

    private fun validarCrediticiaInterna(
        buroResponse: BuroResponse,
        pais: String,
        documento: String,
        origen: Origen
    ): Single<BuroResponse> {
        return crediticiaRepository.validacionCrediticiaInterna(pais, documento)
            .map {

                buroResponse.bloqueosInternos = it.bloqueosInternos
                buroResponse.valoracionBelcorp = it.valoracionBelcorp
                buroResponse.motivoBloqueo = it.motivoBloqueo
                buroResponse.deudaBelcorp = it.deudaBelcorp
                buroResponse.bloqueos = it.bloqueos

                if (it.bloqueosInternos == true && (origen == Origen.UNETE || !PaisUnete.paisesAllErrors.contains(pais))) {
                    throw PostulanteBloqueadaException(buroResponse)
                }

                buroResponse
            }
    }

    private fun agregarFirma(
        postulante: Postulante,
        buroResponse: BuroResponse
    ): Single<BuroResponse> {
        return postulanteRepository.agregarPostulanteFirma(postulante).map {
            buroResponse
        }
    }

    private fun validarCrediticiaExterna(
        buroResponse: BuroResponse,
        username: String,
        pais: String,
        documento: String,
        region: String,
        zona: String,
        tipoDocumento: String,
        codSeccion: String = Constant.EMPTY_STRING,
        origen: Origen
    ): Single<BuroResponse> {
        return when (pais) {
            Pais.PERU.codigoIso,
            Pais.COSTARICA.codigoIso,
            Pais.ECUADOR.codigoIso,
            Pais.DOMINICANA.codigoIso,
            Pais.GUATEMALA.codigoIso,
            Pais.PANAMA.codigoIso,
            Pais.SALVADOR.codigoIso -> {
                crediticiaRepository
                    .validacionCrediticiaExterna(
                        username,
                        pais,
                        documento,
                        region,
                        zona,
                        tipoDocumento,
                        codSeccion
                    )
                    .map {

                        if (it.segundoNombre.isEmpty() && it.primerNombre.contains(" ")) {
                            buroResponse.primerNombre = it.primerNombre.substringBefore(" ")
                            buroResponse.segundoNombre = it.primerNombre.substringAfter(" ")
                        } else {
                            buroResponse.primerNombre = it.primerNombre
                            buroResponse.segundoNombre = it.segundoNombre
                        }

                        buroResponse.primerApellido = it.primerApellido
                        buroResponse.segundoApellido = it.segundoApellido
                        buroResponse.enumEstadoCrediticioID = it.enumEstadoCrediticio
                        buroResponse.bloqueosExternos = it.bloqueosExternos
                        buroResponse.mensajeError = it.mensaje
                        buroResponse.requiereAprobacionSAC = it.requiereAprobacionSAC
                        buroResponse.respuestaServicio = it.respuestaServicio
                        buroResponse.fechaNacimiento = it.fechaNacimiento

                        if (it.bloqueosExternos == true && (origen == Origen.UNETE || !PaisUnete.paisesAllErrors.contains(pais))) {
                            throw PostulanteBloqueadaException(buroResponse)
                        }

                        buroResponse
                    }
            }
            Pais.BOLIVIA.codigoIso -> {
                buroResponse.enumEstadoCrediticioID =
                    UneteEstadoCrediticio.PUEDE_SER_CONSULTORA.value
                Single.just(buroResponse)
            }
            else -> {
                Single.just(buroResponse)
            }
        }
    }

    private fun evaluacionValidarCrediticiaExterna(
        buroResponse: BuroResponse,
        username: String,
        pais: String,
        documento: String,
        region: String,
        zona: String,
        tipoDocumento: String,
        codSeccion: String = Constant.EMPTY_STRING,
        apellido: String = Constant.EMPTY_STRING,
        origen: Origen
    ): Single<BuroResponse> {
        return when (pais) {
            Pais.PERU.codigoIso,
            Pais.CHILE.codigoIso,
            Pais.COLOMBIA.codigoIso -> {
                crediticiaRepository
                    .validacionCrediticiaExterna(
                        username,
                        pais,
                        documento,
                        region,
                        zona,
                        tipoDocumento,
                        codSeccion,
                        apellido
                    )
                    .map {

                        if (it.segundoNombre.isEmpty() && it.primerNombre.contains(" ")) {
                            buroResponse.primerNombre = it.primerNombre.substringBefore(" ")
                            buroResponse.segundoNombre = it.primerNombre.substringAfter(" ")
                        } else {
                            buroResponse.primerNombre = it.primerNombre
                            buroResponse.segundoNombre = it.segundoNombre
                        }

                        buroResponse.primerApellido = it.primerApellido
                        buroResponse.segundoApellido = it.segundoApellido
                        buroResponse.enumEstadoCrediticioID = it.enumEstadoCrediticio
                        buroResponse.bloqueosExternos = it.bloqueosExternos
                        buroResponse.mensajeError = it.mensaje
                        buroResponse.requiereAprobacionSAC = it.requiereAprobacionSAC
                        buroResponse.respuestaServicio = it.respuestaServicio

                        if (it.bloqueosExternos == true && (origen == Origen.UNETE || !PaisUnete.paisesAllErrors.contains(pais))) {
                            throw PostulanteBloqueadaException(buroResponse)
                        }

                        buroResponse
                    }
            }
            else -> {
                Single.just(buroResponse)
            }
        }
    }

    fun evaluarPostulante(
        useCaseSuscriber: BaseSingleObserver<BuroResponse>,
        tipoDocumento: String,
        documento: String,
        apellido: String,
        secc: String = Constant.EMPTY_STRING
    ) {
        val sesion = sessionUsecase.obtener()

        val strDocument = documento.replace("-", "")

        val username = Constant.CHATBOT
        val pais = sesion.countryIso
        val region = sesion.region.orEmpty()
        val zona = sesion.zona.orEmpty()
        val seccion = if (secc.trim().isEmpty()) sesion.seccion.orEmpty() else secc

        val single = validarPostulanteApta(pais, tipoDocumento, strDocument, Origen.POSTULANTE)
            .flatMap { validarCrediticiaInterna(it, pais, strDocument, Origen.POSTULANTE) }
            .flatMap {
                evaluacionValidarCrediticiaExterna(
                    it,
                    username,
                    pais,
                    strDocument,
                    region,
                    zona,
                    tipoDocumento,
                    seccion,
                    apellido,
                    Origen.POSTULANTE
                )
            }
            .onErrorResumeNext { ex ->
                if (ex is PostulanteBloqueadaException) {
                    Single.create {
                        it.onSuccess(ex.buroResponse)
                    }
                } else {
                    Single.error(ex)
                }
            }

        execute(single, useCaseSuscriber)
    }

    enum class Origen {
        UNETE, POSTULANTE
    }

}

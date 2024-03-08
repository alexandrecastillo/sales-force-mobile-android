package biz.belcorp.salesforce.modules.postulants.core.domain.usecases

import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.AppBuild
import biz.belcorp.salesforce.core.utils.changeDateFormat
import biz.belcorp.salesforce.core.utils.formatLongT
import biz.belcorp.salesforce.core.utils.formatShort
import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.PostulanteProactivoResponse
import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.SolicitudPostulanteEstadosResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.constant.MX
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.*
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.*
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.PostulantesRepository
import io.reactivex.Single
import io.reactivex.SingleObserver

class PostulantesUseCase(
    private val repository: PostulantesRepository,
    private val sessionMain: ObtenerSesionUseCase,
    threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    private val session get() = requireNotNull(sessionMain.obtener())

    fun download(
        useCaseSubscriber: BaseSingleObserver<Int>,
        filtroBusqueda: FiltroBusqueda
    ) {
        val observable =
            repository.download(filtroBusqueda)
        execute(observable, useCaseSubscriber)
    }

    fun downloadPre(
        useCaseSubscriber: BaseSingleObserver<Int>,
        tipoFiltro: Int,
        textoBusqueda: String
    ) {
        val paramRequest = crearRequestDownloadPre(tipoFiltro, textoBusqueda)
        val observable = repository.downloadPre(paramRequest)
        execute(observable, useCaseSubscriber)
    }

    private fun crearRequestDownloadPre(
        tipoFiltro: Int,
        textoBusqueda: String
    ): RequestDownloadPre {
        val request = RequestDownloadPre()
        request.pais = session.pais?.codigoIso
        request.region = session.region
        request.rol = session.codigoRol
        request.seccion = session.seccion
        request.zona = session.zona
        request.tipoFiltro = tipoFiltro
        request.textoBusqueda = textoBusqueda
        return request
    }

    fun list(
        useCaseSubscriber: BaseSingleObserver<List<Postulante>>,
        listado: Int, seccion: String?
    ) {
        val uneteEstados: List<String> = getUneteEstados(listado)
        val observable = repository.list(uneteEstados, seccion)
        execute(observable, useCaseSubscriber)
    }

    fun listPre(
        useCaseSubscriber: BaseSingleObserver<List<PrePostulante>>,
        listado: Int,
        seccion: String?
    ) {
        val uneteEstados: List<String> = getUneteEstados(listado)
        val observable = repository.listPre(uneteEstados, seccion)
        execute(observable, useCaseSubscriber)
    }

    fun add(useCaseSubscriber: BaseSingleObserver<Postulante>, postulante: Postulante) {
        validatePhoneStatus(postulante)
        if (!PostulantQueue.isServiceAdded(UneteServices.CREARPOSTULANTE)) {
            PostulantQueue.addServiceQueue(UneteServices.CREARPOSTULANTE)
            val observable = repository.add(postulante)
            execute(observable, useCaseSubscriber)
        }
    }

    fun agregarPostulanteFirma(
        useCaseSubscriber: BaseSingleObserver<Postulante>,
        postulante: Postulante
    ) {
        validatePhoneStatus(postulante)
        if (!PostulantQueue.isServiceAdded(UneteServices.CREARPOSTULANTE)) {
            PostulantQueue.addServiceQueue(UneteServices.CREARPOSTULANTE)
            val observable = repository.agregarPostulanteFirma(postulante)
            execute(observable, useCaseSubscriber)
        }
    }

    fun update(useCaseSubscriber: BaseSingleObserver<Postulante>, postulante: Postulante) {
        validatePhoneStatus(postulante)
        val observable = repository.update(postulante)
        execute(observable, useCaseSubscriber)
    }

    private fun validatePhoneStatus(postulante: Postulante) {
        if (postulante.estadoTelefonico == UneteEstadoTelefonico.ZERO.value)
            postulante.estadoTelefonico = UneteEstadoTelefonico.POR_VALIDAR.value
    }

    fun update(useCaseSubscriber: BaseSingleObserver<PrePostulante>, prePostulante: PrePostulante) {
        val observable = repository.update(prePostulante)
        execute(observable, useCaseSubscriber)
    }

    fun addOffline(useCaseSubscriber: BaseSingleObserver<Postulante>, postulante: Postulante) {
        val observable = repository.addOffline(postulante)
        execute(observable, useCaseSubscriber)
    }

    fun updateOffline(useCaseSubscriber: BaseSingleObserver<Postulante>, postulante: Postulante) {
        val observable = repository.updateOffline(postulante)
        execute(observable, useCaseSubscriber)
    }

    fun updateOffline(
        useCaseSubscriber: BaseSingleObserver<PrePostulante>,
        prePostulante: PrePostulante
    ) {
        val observable = repository.updateOffline(prePostulante)
        execute(observable, useCaseSubscriber)
    }

    fun get(useCaseSubscriber: BaseSingleObserver<Postulante>, uuid: String, id: Int) {
        val observable = repository.get(uuid, id)
        execute(observable, useCaseSubscriber)
    }

    fun getPre(useCaseSubscriber: BaseSingleObserver<PrePostulante>, uuid: String, id: Int) {
        val observable = repository.getPre(uuid, id)
        execute(observable, useCaseSubscriber)
    }

    fun geo(
        useCaseSubscriber: BaseSingleObserver<GeoZonificacion>,
        pais: String,
        latitud: String,
        longitud: String,
        distrito: String
    ) {
        val observable = repository.geo(pais, latitud, longitud, distrito)
        execute(observable, useCaseSubscriber)
    }

    fun geoBuroCo(
        useCaseSubscriber: BaseSingleObserver<Boolean>,
        pais: String,
        solicitudPostulanteID: Int,
        direccion: String,
        ciudad: String,
        subestadoPostulante: Int
    ) {
        val observable = repository.geoBuroCO(
            pais,
            solicitudPostulanteID,
            direccion,
            ciudad,
            subestadoPostulante
        )
        execute(observable, useCaseSubscriber)
    }

    fun validarBloqueosPaso1(
        useCaseSubscriber: BaseSingleObserver<BuroResponse>,
        pais: String,
        documento: String,
        tipoDocumento: String,
        zona: String
    ) {
        val observable = repository.validarBloqueos(pais, documento, tipoDocumento, zona)
        execute(observable, useCaseSubscriber)
    }

    fun validarBuros(
        useCaseSubscriber: BaseSingleObserver<ValidacionBuroResponse>,
        validacionBuro: ValidacionBuro
    ) {
        val observable = repository.validarBuros(validacionBuro)
        execute(observable, useCaseSubscriber)
    }

    fun updateEstado(
        useCaseSubscriber: BaseSingleObserver<Boolean>,
        pais: String,
        solicitudPostulanteID: Int,
        estadoPostulante: Int,
        subEstadoPostulante: Int?,
        tipoRechazo: String,
        motivoRechazo: String
    ) {
        val observable = repository.updateEstado(
            pais,
            solicitudPostulanteID,
            estadoPostulante,
            subEstadoPostulante,
            tipoRechazo,
            motivoRechazo
        )
        execute(observable, useCaseSubscriber)
    }

    fun updateEstadoPre(
        useCaseSubscriber: BaseSingleObserver<Boolean>,
        pais: String,
        solicitudPrePostulanteID: Int,
        tipoRechazo: String,
        motivoRechazo: String
    ) {
        val observable = repository.updateEstadoPre(
            pais, solicitudPrePostulanteID,
            tipoRechazo, motivoRechazo
        )
        execute(observable, useCaseSubscriber)
    }

    fun getObservacionDevueltoSac(
        useCaseSubscriber: BaseSingleObserver<String>,
        solicitudPostulanteID: Int
    ) {
        val observable = repository.getObservacionDevueltoSac(solicitudPostulanteID)
        execute(observable, useCaseSubscriber)
    }

    fun existeNumeroDocumento(
        useCaseSubscriber: BaseSingleObserver<Boolean>,
        numeroDocumento: String
    ) {
        val observable = repository.existeNumeroDocumento(numeroDocumento)
        execute(observable, useCaseSubscriber)
    }

    fun validarMail(
        useCaseSubscriber: BaseSingleObserver<Boolean>, codigoISO: String,
        mail: String, numeroDocumento: String
    ) {

        val single = if (mail.isEmpty()) {
            Single.just(false)
        } else {
            repository.validarMail(codigoISO, mail, numeroDocumento)
        }
        execute(single, useCaseSubscriber)
    }

    fun validarCelular(
        useCaseSubscriber: BaseSingleObserver<Boolean>, codigoISO: String,
        celular: String, tipoDocumento: String, numeroDocumento: String
    ) {
        val observable =
            repository.validarCelular(codigoISO, celular, tipoDocumento, numeroDocumento)
        execute(observable, useCaseSubscriber)
    }

    fun enviarValidacionSMS(
        useCaseSubscriber: BaseSingleObserver<Boolean>,
        codigoISO: String,
        solicitudPostulanteID: Int,
        celular: String,
        numeroDocumento: String,
        nombreCompleto: String
    ) {
        val observable = repository.enviarValidacionSMSCelular(
            codigoISO,
            solicitudPostulanteID,
            celular,
            numeroDocumento,
            nombreCompleto
        )
        execute(observable, useCaseSubscriber)
    }

    fun validarBloqueosMXPaso2(
        useCaseSubscriber: SingleObserver<String>,
        validacionBloqueosMX: ValidacionBloqueosMX
    ) {

        val tarjeta = if (validacionBloqueosMX.tarjetaDeCredito) MX.TRUE else MX.FALSE
        val hipotecario = if (validacionBloqueosMX.creditoHipotecario) MX.TRUE else MX.FALSE
        val automotriz = if (validacionBloqueosMX.creditoAutomotriz) MX.TRUE else MX.FALSE

        if (validacionBloqueosMX.postulante?.apellidoMaterno.isNullOrEmpty()) {
            validacionBloqueosMX.postulante?.apellidoMaterno = MX.APELLIDO_MATERNO_DEFAULT
        }

        val fechaNacimiento = validacionBloqueosMX.postulante?.fechaNacimiento
            ?.changeDateFormat(formatLongT, formatShort).orEmpty()


        val evaluacionBuroMX = EvaluacionBuroMX()
        validacionBloqueosMX.postulante?.let {
            evaluacionBuroMX.postulante = it
        }
        evaluacionBuroMX.fechaNacimiento = fechaNacimiento
        evaluacionBuroMX.estado = validacionBloqueosMX.estado
        evaluacionBuroMX.ciudad = validacionBloqueosMX.ciudad
        evaluacionBuroMX.direccion = validacionBloqueosMX.direccion
        evaluacionBuroMX.tarjetaDeCredito = tarjeta
        evaluacionBuroMX.creditoHipotecario = hipotecario
        evaluacionBuroMX.creditoAutomotriz = automotriz

        val single = repository.validarBloqueosMXPaso2(evaluacionBuroMX)
        execute(single, useCaseSubscriber)
    }

    fun eliminarPostulante(useCaseSubscriber: SingleObserver<Boolean>, solicitudPostulanteID: Int) {
        val single = repository.eliminarPostulante(solicitudPostulanteID)
        execute(single, useCaseSubscriber)
    }

    fun eliminarPrePostulante(
        useCaseSubscriber: SingleObserver<Boolean>,
        solicitudPrePostulanteID: Int
    ) {
        val single = repository.eliminarPrePostulante(solicitudPrePostulanteID)
        execute(single, useCaseSubscriber)
    }

    private fun getUneteEstados(listado: Int): List<String> {

        return when (listado) {

            UneteListado.EVALUACION.tipo ->
                listOf(
                    UneteEstado.REGISTRADO.estado.toString(),
                    UneteEstado.GESTION_SAC.estado.toString()
                )

            UneteListado.PRE_APROBADOS.tipo ->
                listOf(
                    UneteEstado.EN_APROBACION_FFVV.estado.toString(),
                    UneteEstado.PENDIENTE_CAMBIO_MODELO.estado.toString()
                )

            UneteListado.APROBADOS.tipo ->
                listOf(
                    UneteEstado.YA_CON_CODIGO.estado.toString(),
                    UneteEstado.GENERANDO_CODIGO.estado.toString(),
                    UneteEstado.EN_APROBACION_SAC.estado.toString(),
                    UneteEstado.APROBACION_CAMBIO_MODELO.estado.toString()
                )

            UneteListado.RECHAZADOS.tipo ->
                listOf(UneteEstado.RECHAZADOS.estado.toString())

            UneteListado.PRE_INSCRITAS.tipo ->
                listOf(UneteEstado.PRE_INSCRITO.estado.toString())

            UneteListado.PROACTIVO_POR_FINALIZAR.tipo -> {
                listOf(
                    UneteEstado.PROACTIVO_FINALIZAR_14.estado.toString(),
                    UneteEstado.PROACTIVO_FINALIZAR_15.estado.toString(),
                    UneteEstado.PROACTIVO_FINALIZAR_16.estado.toString()
                )
            }

            UneteListado.PROACTIVO_FINALIZADO.tipo ->
                listOf(
                    UneteEstado.YA_CON_CODIGO.estado.toString()
                )

            UneteListado.PROACTIVO_PRE_APROBADOS.tipo ->
                listOf(
                    UneteEstado.YA_CON_CODIGO.estado.toString()
                )

            else -> emptyList()

        }
    }

    fun validarPin(useCaseSubscriber: BaseSingleObserver<Int>, validarPin: ValidarPin) {
        val single = repository.validarPin(validarPin)
        execute(single, useCaseSubscriber)
    }

    fun actualizarPostulanteProactivo(
        useCaseSubscriber: BaseSingleObserver<PostulanteProactivoResponse?>,
        codigoIso: String,
        estado: Int,
        solicitudPostulanteId: String,
        motivoRechazo: String?
    ) {
        val single = repository.actualizarPostulanteProactivo(
            codigoIso = codigoIso,
            estado = estado,
            solicitudPostulanteId = solicitudPostulanteId,
            motivoRechazo = motivoRechazo
        )

        if (single != null) execute(single, useCaseSubscriber)
    }

    fun verificarEstadoTelefono(
        useCaseSubscriber: BaseSingleObserver<Int>,
        estadoTelefono: EstadoTelefonoZona
    ) {
        val observable = repository.getVerificarEstadoTelefonoZona(
            estadoTelefono.codigoISO,
            estadoTelefono.codigoZona,
            estadoTelefono.codigoSeccion
        )
        execute(observable, useCaseSubscriber)
    }

    fun getCoordinatesByDirection(
        param: ParamGetCoordenadas,
        useCaseSubscriber: BaseSingleObserver<Coordenadas>
    ) {
        val observable = repository.getCoordinatesByDirection(param.toRequest())
        execute(observable, useCaseSubscriber)
    }
    
    fun obtenerSolicitudPostulanteEstados(
        useCaseSubscriber: BaseSingleObserver<SolicitudPostulanteEstadosResponse>,
        codigoISO: String,
        solicitudPostulanteID: String
    ) {
        val observable =
            repository.obtenerSolicitudPostulanteEstados(codigoISO, solicitudPostulanteID)
        execute(observable, useCaseSubscriber)
    }

}

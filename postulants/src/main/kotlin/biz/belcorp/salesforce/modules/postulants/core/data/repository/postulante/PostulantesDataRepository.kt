package biz.belcorp.salesforce.modules.postulants.core.data.repository.postulante


import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.entities.sql.unete.PostulanteEntity
import biz.belcorp.salesforce.core.entities.sql.unete.PrePostulanteEntity
import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.CoordenadasRequest
import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.PostulanteProactivoResponse
import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.SolicitudPostulanteEstadosResponse
import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.toModel
import biz.belcorp.salesforce.modules.postulants.core.data.repository.postulante.datasource.PostulantesCloudDataStore
import biz.belcorp.salesforce.modules.postulants.core.data.repository.postulante.datasource.PostulantesDBDataStore
import biz.belcorp.salesforce.modules.postulants.core.data.repository.postulante.mappers.*
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.*
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteTipoSolicitud
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.InvalidMtoNameException
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.PostulanteRechazadaException
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.PostulantesRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class PostulantesDataRepository(
    private val dbStore: PostulantesDBDataStore,
    private val cloudStore: PostulantesCloudDataStore,
    private val sesionManager: ObtenerSesionUseCase,
    private val mapperPre: PrePostulanteEntityDataMapper,
    private val mapper: PostulanteEntityDataMapper,
    private val mapperBuro: BuroResponseEntityDataMapper,
    private val mapperValidacion: ValidacionBuroResponseEntitiyDataMapper,
    private val validarPinEntityDataMapper: ValidarPinEntityDataMapper,
    private val postulanteAptaMapper: PostulanteAptaDataMapper,
    private val mapperGEO: GeoResponseEntityDataMapper,
    private val evaluacionBuroMapper: EvaluacionBuroDataMapper
) : PostulantesRepository {

    override fun uploadOfflineCreated(pais: String, list: List<Postulante>): Single<Int> {
        return cloudStore.create(pais, mapper.map(list)).flatMap {
            list.forEach {
                it.sincronizado = true
            }
            dbStore.update(mapper.map(list))
        }
    }

    override fun uploadOfflineUpdated(pais: String, list: List<Postulante>): Single<Int> {
        return cloudStore.update(pais, mapper.map(list)).flatMap {
            if (it == 1) {
                list.forEach {
                    it.sincronizado = true
                }
            }
            dbStore.update(mapper.map(list))
        }
    }

    override fun listOffline(): Single<List<Postulante>> {
        return dbStore.listOffline().map {
            mapper.reverseMap(it)
        }
    }

    override fun addOffline(postulante: Postulante): Single<Postulante> {
        return dbStore.create(mapper.map(postulante)).map { postulante }
    }

    override fun updateOffline(postulante: Postulante): Single<Postulante> {
        return dbStore.update(mapper.map(postulante)).map { postulante }
    }

    override fun updateOffline(prePostulante: PrePostulante): Single<PrePostulante> {
        return dbStore.update(mapperPre.map(prePostulante)).map { prePostulante }
    }

    override fun add(postulante: Postulante): Single<Postulante> {
        return cloudStore.create(mapper.map(postulante))
            .onErrorResumeNext {
                if (it is PostulanteRechazadaException) {
                    Single.error(it)
                } else {
                    dbStore.update(mapper.map(postulante))
                }
            }
            .map {
                if (it > Constant.NUMBER_ONE) {
                    postulante.solicitudPostulanteID = it
                    postulante.sincronizado = true
                }
                postulante
            }
    }

    override fun agregarPostulanteFirma(postulante: Postulante): Single<Postulante> {
        return cloudStore.agregarPostulanteFirma(mapper.map(postulante))
            .onErrorResumeNext {
                if (it is PostulanteRechazadaException) {
                    Single.error(it)
                } else {
                    dbStore.update(mapper.map(postulante))
                }
            }
            .map {
                if (it > Constant.NUMBER_ONE) {
                    postulante.solicitudPostulanteID = it
                    postulante.sincronizado = true
                }
                postulante
            }
    }

    override fun update(postulante: Postulante): Single<Postulante> {
        return cloudStore.update(mapper.map(postulante))
            .onErrorResumeNext {
                when (it) {
                    is PostulanteRechazadaException -> {
                        Single.error(it)
                    }
                    is InvalidMtoNameException -> {
                        Single.error(it)
                    }
                    else -> {
                        dbStore.update(mapper.map(postulante))
                    }
                }
            }.map {
                if (it > Constant.NUMBER_ZERO)
                    postulante.sincronizado = true
                postulante
            }
    }

    override fun update(prePostulante: PrePostulante): Single<PrePostulante> {
        return cloudStore.update(mapperPre.map(prePostulante))
            .onErrorResumeNext {
                if (it is PostulanteRechazadaException) {
                    Single.error(it)
                } else {
                    dbStore.update(mapperPre.map(prePostulante))
                }
            }.map {
                if (it == 1)
                    prePostulante.sincronizado = true
                prePostulante
            }
    }

    override fun list(estados: List<String>, seccion: String?): Single<List<Postulante>> {
        return dbStore.list(estados, seccion).map {
            mapper.reverseMap(it)
        }
    }

    override fun listPre(estados: List<String>, seccion: String?): Single<List<PrePostulante>> {
        return dbStore.listPre(estados, seccion).map {
            mapperPre.reverseMap(it)
        }
    }

    override fun listAll(): Single<List<Postulante>> {
        return dbStore.listAll().map { mapper.reverseMap(it) }
    }

    override fun get(uuid: String, id: Int): Single<Postulante> {
        return dbStore.get(uuid, id).map { mapper.reverseMap(it) }
    }

    override fun getPre(uuid: String, id: Int): Single<PrePostulante> {
        return dbStore.getPre(uuid, id).map { mapperPre.reverseMap(it) }
    }

    override fun download(filtroBusqueda: FiltroBusqueda): Single<Int> {
        return cloudStore
            .list(filtroBusqueda)
            .flatMap {
                getPostulanteEntity(it, filtroBusqueda)
                dbStore.create(it)
            }
    }

    private fun getPostulanteEntity(list: List<PostulanteEntity>, filtroBusqueda: FiltroBusqueda) {
        val paisid = Pais.find(filtroBusqueda.pais.orEmpty())?.id
        paisid?.let {
            list.forEach {
                it.pais = filtroBusqueda.pais
                it.paisID = paisid
                it.tipoSolicitud = UneteTipoSolicitud.find(it.tipoSolicitud.orEmpty())?.value
            }
        }
    }

    override fun downloadPre(request: RequestDownloadPre): Single<Int> {

        return cloudStore
            .listPre(
                request.pais.orEmpty(), request.zona.orEmpty(), request.seccion,
                request.tipoFiltro, request.textoBusqueda.orEmpty()
            )
            .flatMap {
                getPrepostulanteEntity(request, it)
                dbStore.createPre(it)
            }
    }

    private fun getPrepostulanteEntity(
        request: RequestDownloadPre, list: List<PrePostulanteEntity>
    ) {
        val paisid = Pais.find(request.pais.orEmpty())?.id
        paisid?.let {
            list.forEach {
                it.pais = request.pais.orEmpty()
                it.paisID = paisid
            }
        }
    }

    override fun geo(
        pais: String,
        latitud: String,
        longitud: String,
        distrito: String
    ): Single<GeoZonificacion> {
        return cloudStore.geo(pais, latitud, longitud, distrito).map { mapperGEO.reverseMap(it) }
    }

    override fun obtenerNombreConsultora(pais: String, codigo: String): Single<String> {
        return cloudStore.obtenerNombreConsultora(pais, codigo)
    }

    override fun validarBloqueos(
        pais: String, documento: String, tipoDocumento: String, zona: String
    ):
        Single<BuroResponse> {
        return cloudStore.validarBloqueos(pais, documento, tipoDocumento, zona)
            .map { mapperBuro.reverseMap(it) }
    }

    override fun updateEstado(
        pais: String, solicitudPostulanteID: Int, estadoPostulante: Int,
        subEstadoPostulante: Int?, tipoRechazo: String, motivoRechazo: String
    ):
        Single<Boolean> {
        return cloudStore.updateEstado(
            pais, solicitudPostulanteID, estadoPostulante, subEstadoPostulante,
            tipoRechazo, motivoRechazo
        )
    }

    override fun updateEstadoPre(
        pais: String, solicitudPrePostulanteID: Int, tipoRechazo: String,
        motivoRechazo: String
    ): Single<Boolean> {
        return cloudStore.updateEstadoPre(
            pais,
            solicitudPrePostulanteID,
            tipoRechazo,
            motivoRechazo
        )
    }

    override fun geoBuroCO(
        pais: String, solicitudPostulanteID: Int, direccion: String, ciudad: String,
        subestadoPostulante: Int
    ): Single<Boolean> {
        return cloudStore.geoBuroCO(
            pais, solicitudPostulanteID, direccion, ciudad, subestadoPostulante
        )
    }

    override fun validarBuros(validacionBuro: ValidacionBuro): Single<ValidacionBuroResponse> {
        return cloudStore.validarBuros(
            validacionBuro
        ).map { mapperValidacion.reverseMap(it) }
    }

    override fun getObservacionDevueltoSac(solicitudPostulanteID: Int): Single<String> {
        return cloudStore.getMensajeDevueltoSac(solicitudPostulanteID)
    }

    override fun validarMail(
        codigoISO: String, mail: String, numeroDocumento: String
    ): Single<Boolean> {
        return cloudStore.validarMail(codigoISO, mail, numeroDocumento)
    }

    override fun validarCelular(
        codigoISO: String, celular: String, tipoDocumento: String,
        numeroDocumento: String
    ): Single<Boolean> {
        return cloudStore.validarCelular(codigoISO, celular, tipoDocumento, numeroDocumento)
    }

    override fun enviarValidacionSMSCelular(
        codigoISO: String, solicitudPostulanteID: Int,
        celular: String, numeroDocumento: String,
        nombreCompleto: String
    ): Single<Boolean> {
        return cloudStore.enviarSMSValidacionTelefonica(
            codigoISO, solicitudPostulanteID, celular, numeroDocumento, nombreCompleto
        )
    }

    override fun existeNumeroDocumento(numeroDocumento: String): Single<Boolean> {
        return dbStore.existeNumeroDocumento(numeroDocumento)
    }

    override fun validarBloqueosMXPaso2(evaluacionBuroMX: EvaluacionBuroMX): Single<String> {

        return cloudStore.validarBloqueosMXPaso2(evaluacionBuroMapper.map(evaluacionBuroMX))
            .map { it.buroResponse.orEmpty() }
    }

    override fun eliminarPostulante(solicitudPostulanteID: Int): Single<Boolean> {
        return cloudStore.eliminarPostulante(solicitudPostulanteID)
    }

    override fun eliminarPrePostulante(solicitudPrePostulanteID: Int): Single<Boolean> {
        return cloudStore.eliminarPrePostulante(solicitudPrePostulanteID)
    }

    override fun postulanteApta(
        pais: String, tipoDocumento: String, documento: String
    ): Single<PostulanteAptaResponse> {
        return cloudStore.obtenerPostulanteApta(pais, tipoDocumento, documento).map {
            postulanteAptaMapper.parse(it)
        }
    }

    override fun sincronizar(): Completable {
        return listOffline()
            .flatMap { filtrarLista(it) }
            .flatMap { uploadOffline(it.first, it.second) }
            .toCompletable()
    }

    private fun filtrarLista(lista: List<Postulante>): Single<Pair<List<Postulante>, List<Postulante>>> {
        return Single.create {

            val listaFiltrada = lista
                .sortedByDescending { p -> p.paso }
                .distinctBy { p -> p.numeroDocumento }

            val created = listaFiltrada.filter { p -> p.solicitudPostulanteID == 0 }
            val updated = listaFiltrada.filter { p -> p.solicitudPostulanteID != 0 }

            it.onSuccess(Pair(created, updated))
        }
    }

    private fun uploadOffline(
        createdList: List<Postulante>, updatedList: List<Postulante>
    ): Single<Int> {

        val pais = sesionManager.obtener().countryIso

        val createdObservable = uploadOfflineCreated(pais, createdList)
        val updatedObservable = uploadOfflineUpdated(pais, updatedList)

        return when {
            createdList.isEmpty() && updatedList.isEmpty() -> Single.just(0)
            createdList.isNotEmpty() && updatedList.isEmpty() -> createdObservable
            updatedList.isNotEmpty() && createdList.isEmpty() -> updatedObservable
            else -> Single.zip(
                createdObservable, updatedObservable, BiFunction<Int, Int, Int> { t1, t2 ->
                    t1 + t2
                })
        }
    }

    override fun validarPin(validarPin: ValidarPin): Single<Int> {
        return cloudStore.validarPin(validarPinEntityDataMapper.map(validarPin))
    }

    override fun actualizarPostulanteProactivo(
        codigoIso: String,
        estado: Int,
        solicitudPostulanteId: String,
        motivoRechazo: String?
    ): Single<PostulanteProactivoResponse?>? {
        return cloudStore.actualizarPostulanteProactivo(
            codigoIso = codigoIso,
            estado = estado,
            solicitudPostulanteId = solicitudPostulanteId,
            motivoRechazo = motivoRechazo
        )
    }

    override fun getVerificarEstadoTelefonoZona(
        codigoISO: String?, codigoZona: String?, codigoSeccion: String?
    ): Single<Int> {
        return cloudStore.getVerificarEstadoTelefonoZona(codigoISO, codigoZona, codigoSeccion)
    }

    override fun getCoordinatesByDirection(request: CoordenadasRequest): Single<Coordenadas> {
        return cloudStore.getCoordinatesByDirection(request).map { it.toModel() }
    }
    
    override fun obtenerSolicitudPostulanteEstados(
        codigoISO: String,
        solicitudPostulanteID: String
    ): Single<SolicitudPostulanteEstadosResponse> {
        return cloudStore.obtenerSolicitudPostulanteEstados(codigoISO, solicitudPostulanteID)
    }

}

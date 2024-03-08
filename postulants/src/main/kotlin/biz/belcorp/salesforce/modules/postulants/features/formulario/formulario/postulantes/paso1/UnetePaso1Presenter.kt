package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso1

import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.utils.ceroSiNull
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.BuroResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.ParametroUnete
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.Postulante
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.PaisUnete
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteEstadoTelefonico
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.UneteTipoParametro
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.DefaultErrorBundle
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ErrorMessageFactory
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.ParametroUneteUseCase
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.PostulantesUseCase
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.TablaLogicaUseCase
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.ValidarBloqueosUseCase
import biz.belcorp.salesforce.modules.postulants.features.base.Presenter
import biz.belcorp.salesforce.modules.postulants.features.entities.ParametroUneteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base.PostulantTextResolver
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.base.UnetePaso1BasePresenter
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.mapper.TablaLogicaModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.mapper.ParametroUneteModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.mapper.PostulantesModelDataMapper
import biz.belcorp.salesforce.modules.postulants.utils.Constant

class UnetePaso1Presenter(
    private val useCase: PostulantesUseCase,
    private val bloqueosUseCase: ValidarBloqueosUseCase,
    private val parametroUneteUseCase: ParametroUneteUseCase,
    private val mapParametroUnete: ParametroUneteModelDataMapper,
    private val mapPostulante: PostulantesModelDataMapper,
    tablaLogicaUseCase: TablaLogicaUseCase,
    mapTablaLogica: TablaLogicaModelDataMapper,
    useCaseSession: ObtenerSesionUseCase,
    errorMessageFactory: ErrorMessageFactory,
    private val textResolver: PostulantTextResolver
) : Presenter,
    UnetePaso1BasePresenter(
        useCase, mapParametroUnete,
        errorMessageFactory, mapTablaLogica,
        useCaseSession, tablaLogicaUseCase, parametroUneteUseCase, textResolver
    ) {

    var postulante: Postulante? = null
    var postulanteModel: PostulanteModel? = null
    var validacionEdadSac: Boolean = false
    var estado: String? = null


    override fun destroy() {
        super.destroyFather()
    }

    private fun created(postulante: Postulante) {
        mView?.created(mapPostulante.map(postulante))
    }

    fun actualizarEstadoTelefonico(postulante: PostulanteModel) {
        val p = mapPostulante.reverseMap(postulante)
        mView?.showLoading()
        useCase.update(UpdateMobileStatusConfirmedSuscriber(), p)
    }

    fun enviarCodigoSMSValidacionTelefonica(
        pais: String, solicitudPostulanteID: Int, celular: String,
        numeroDocumento: String, nombreCompleto: String
    ) {
        mView?.showLoading()
        useCase.enviarValidacionSMS(
            EnviarSMSSubscriber(), pais, solicitudPostulanteID, celular,
            numeroDocumento, nombreCompleto
        )
    }

    private inner class EnviarSMSSubscriber : BaseSingleObserver<Boolean>() {

        override fun onError(exception: Throwable) {
            hideViewLoading()
            mView?.showSMSExitosoDialog()
        }

        override fun onSuccess(t: Boolean) {
            hideViewLoading()
            mView?.showSMSExitosoDialog()
        }
    }

    private inner class UpdateMobileStatusConfirmedSuscriber : BaseSingleObserver<Postulante>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
        }

        override fun onSuccess(t: Postulante) {
            useCase.updateOffline(UpdateOfflineMobileStatusConfirmedSuscriber(), t)
        }
    }

    private inner class UpdateOfflineMobileStatusConfirmedSuscriber :
        BaseSingleObserver<Postulante>() {

        override fun onError(exception: Throwable) {
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
        }

        override fun onSuccess(t: Postulante) {
            if (t.estadoTelefonico == UneteEstadoTelefonico.POR_VALIDAR.value) {
                mView?.showLoading()
                enviarCodigoSMSValidacionTelefonica(
                    t.pais.orEmpty(), t.solicitudPostulanteID, t.celular.orEmpty(),
                    t.numeroDocumento.orEmpty(), t.nombreCompleto
                )
            } else {
                hideViewLoading()
            }

            Logger.loge(javaClass.simpleName, t.toString())
        }
    }


    private inner class UpdatePostulanteSubscriber : BaseSingleObserver<Postulante>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
        }

        override fun onSuccess(t: Postulante) {
            useCase.updateOffline(UpdateOfflinePostulanteSubscriber(), t)
        }
    }

    private inner class UpdateOfflinePostulanteSubscriber : BaseSingleObserver<Postulante>() {

        override fun onError(exception: Throwable) {
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
        }

        override fun onSuccess(t: Postulante) {
            hideViewLoading()
            created(t)
        }
    }

    private inner class CreatePostulanteSubscriber : BaseSingleObserver<Postulante>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
            postulante?.let {
                useCase.addOffline(CreateOfflinePostulanteSubscriber(), it)
            }
        }

        override fun onSuccess(t: Postulante) {
            postulante?.let {
                useCase.addOffline(CreateOfflinePostulanteSubscriber(), it)
            }
            Logger.loge(javaClass.simpleName, t.toString())
        }

    }

    private inner class CreateOfflinePostulanteSubscriber : BaseSingleObserver<Postulante>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
        }

        override fun onSuccess(t: Postulante) {
            hideViewLoading()
            created(t)
            Logger.loge(javaClass.simpleName, t.toString())
        }

    }

    private inner class ValidarCelularSubscriber : BaseSingleObserver<Boolean>() {

        override fun onError(exception: Throwable) {
            hideViewLoading()
            mView?.showValidCelular()
        }

        override fun onSuccess(t: Boolean) {
            hideViewLoading()
            if (t) {
                mView?.showDuplicateCelular()
            } else {
                mView?.showValidCelular()
            }
        }

    }

    override fun devolverDetallesBloqueos(bloqueos: BuroResponse) {
        mView?.createModel(bloqueos)
    }

    fun validarCelular(
        pais: String, celular: String, tipoDocumento: String, numeroDocumento: String
    ) {
        showLoading()
        useCase.validarCelular(
            ValidarCelularSubscriber(), pais, celular, tipoDocumento, numeroDocumento
        )
    }

    fun createPostulante(postulanteModel: PostulanteModel) {
        postulante = mapPostulante.reverseMap(postulanteModel)
        showLoading()
        if (postulante?.offline == true)
            postulante?.let {
                useCase.addOffline(CreateOfflinePostulanteSubscriber(), it)
            }
        else
            postulante?.let {
                useCase.add(CreatePostulanteSubscriber(), it)
            }
    }

    fun updatePostulante(postulante: PostulanteModel) {
        when (postulante.pais) {
            Pais.COSTARICA.codigoIso -> listValidarRangoEdadActualizar(postulante)
            else -> updatePostulanteFinal(postulante)
        }
    }

    fun listValidarRangoEdad(model: PostulanteModel) {
        when (model.pais) {
            Pais.COSTARICA.codigoIso -> listValidarRangoEdadInsertar(model)
            else -> mView?.validarDatos()
        }
    }

    private fun updatePostulanteFinal(postulante: PostulanteModel) {
        val p = mapPostulante.reverseMap(postulante)
        showLoading()
        useCase.update(UpdatePostulanteSubscriber(), p)
    }

    private fun listValidarRangoEdadInsertar(model: PostulanteModel) {
        estado = Constant.INSERTAR
        postulanteModel = model
        parametroUneteUseCase.list(ValidarEdadSubscriber(), UneteTipoParametro.RANGOEDAD.tipo)
    }

    private fun listValidarRangoEdadActualizar(model: PostulanteModel) {
        estado = Constant.ACTUALIZAR
        postulanteModel = model
        parametroUneteUseCase.list(ValidarEdadSubscriber(), UneteTipoParametro.RANGOEDAD.tipo)
    }

    private inner class ValidarEdadSubscriber : BaseSingleObserver<List<ParametroUnete>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
            mView?.validarDatos()
        }

        override fun onSuccess(t: List<ParametroUnete>) {
            val listaRangoEdad = mapParametroUnete.map(t)
            validarRangoDeEdadEnSac(listaRangoEdad, postulanteModel)
        }

    }

    private fun validarRangoDeEdadEnSac(
        listaParametros: List<ParametroUneteModel>, postulante: PostulanteModel?
    ) {

        postulante?.let {
            if (validarAprobacionSacYListaParametros(it, listaParametros)) {
                val valor1 = listaParametros[0].valor.orEmpty()
                val valor2 = listaParametros[1].valor.orEmpty()

                if (ambosValoresNoSonNulos(valor1, valor2)) {
                    val edad = it.edad.ceroSiNull()
                    validarSacSegunRangoDeEdad(valor1, valor2, edad, it)
                }
            }
        }

        when (estado) {
            Constant.INSERTAR -> mView?.validarDatos()
            Constant.ACTUALIZAR -> postulante?.let { updatePostulanteFinal(it) }
        }
    }

    private fun validarAprobacionSacYListaParametros(
        postulante: PostulanteModel, listaParametros: List<ParametroUneteModel>
    ) =
        aunNoTieneAprobacionPorSac(postulante) && existenValoresEnLaPosicion0y1(listaParametros)

    private fun aunNoTieneAprobacionPorSac(postulante: PostulanteModel) =
        !postulante.requiereAprobacionSAC


    fun validarBloqueos(pais: String, documento: String, tipoDocumento: String, zona: String, postulanteActual: PostulanteModel) {
        showLoading(Constant.EMPTY_STRING)
        if (pais in PaisUnete.paisesConValidacionExterna) {
            bloqueosUseCase.validarBloqueosPaso1(
                ValidarBloqueosSubscriber(),
                documento,
                tipoDocumento,
                mapPostulante.reverseMap(postulanteActual)
            )
        } else {
            useCase.validarBloqueosPaso1(
                ValidarBloqueosSubscriber(),
                pais, documento, tipoDocumento, zona
            )
        }
    }

    private fun existenValoresEnLaPosicion0y1(listaParametros: List<ParametroUneteModel>) =
        listaParametros.size > Constant.NUMERO_CERO &&
            listaParametros.getOrNull(Constant.NUMERO_CERO) != null &&
            listaParametros.getOrNull(Constant.NUMERO_UNO) != null


    private fun ambosValoresNoSonNulos(valor1: String?, valor2: String?) =
        valor1?.toIntOrNull() != null && valor2?.toIntOrNull() != null


    private fun validarSacSegunRangoDeEdad(
        valor1: String, valor2: String, edad: Int, postulanteActual: PostulanteModel
    ) {
        if (valor1EsMenorQueValor2(valor1, valor2)) {
            when (estado) {
                Constant.INSERTAR -> validacionEdadSac =
                    valor1.toInt() <= edad && edad <= valor2.toInt()
                Constant.ACTUALIZAR -> postulanteActual.requiereAprobacionSAC =
                    valor1.toInt() <= edad && edad <= valor2.toInt()
            }
        } else if (valor1EsMayorQueValor2(valor1, valor2)) {
            when (estado) {
                Constant.INSERTAR -> validacionEdadSac =
                    valor2.toInt() <= edad && edad <= valor1.toInt()
                Constant.ACTUALIZAR -> postulanteActual.requiereAprobacionSAC =
                    valor2.toInt() <= edad && edad <= valor1.toInt()
            }
        }
    }

    private fun valor1EsMayorQueValor2(valor1: String, valor2: String) =
        valor1.toInt() > valor2.toInt()

    private fun valor1EsMenorQueValor2(valor1: String, valor2: String) =
        valor1.toInt() < valor2.toInt()

    fun showBloqueoExternoDialog() {
        mView?.showModal(textResolver.getValidPostulantTitle(), getMessageBloqueosExternos())
    }

    fun validarCorreoObligatorio(zonaSeccion: String) {
        parametroUneteUseCase.list2(
            UneteTipoParametro.CORREOOBLIGATORIO.tipo,
            zonaSeccion,
            ObtenerCorreoObligatorioSubscriber(zonaSeccion)
        )
    }

    private inner class ObtenerCorreoObligatorioSubscriber(private val zonaSeccion: String) :
        BaseSingleObserver<List<ParametroUnete>>() {

        override fun onSuccess(t: List<ParametroUnete>) {
            var isMandatory = false

            t.forEach { item ->
                if (item.nombre?.contains(zonaSeccion) == true) {
                    isMandatory = true
                }
            }

            mView?.setCorreoObligatorio(isMandatory)
        }

        override fun onError(exception: Throwable) {
            Logger.loge(this.javaClass.simpleName, exception.message)
        }
    }

}


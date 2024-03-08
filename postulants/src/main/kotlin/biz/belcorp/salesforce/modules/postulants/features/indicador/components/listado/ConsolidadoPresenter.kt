package biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado

import biz.belcorp.salesforce.core.base.BasePresenter
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.usecases.campania.ObtenerCampaniasUseCase
import biz.belcorp.salesforce.core.domain.usecases.configuration.ConfigurationUseCase
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.PostulanteProactivoResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.*
import biz.belcorp.salesforce.modules.postulants.core.domain.enums.*
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.DefaultErrorBundle
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ErrorBundle
import biz.belcorp.salesforce.modules.postulants.core.domain.exception.ErrorMessageFactory
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.ParametroUneteUseCase
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.PostulantesUseCase
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.UneteConfiguracionUseCase
import biz.belcorp.salesforce.modules.postulants.features.base.Presenter
import biz.belcorp.salesforce.modules.postulants.features.entities.*
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.mappers.CampaignModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.mapper.ParametroUneteModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.mapper.PostulantesModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.mapper.PrePostulantesModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.mapper.UneteConfiguracionModelDataMapper
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.UneteAlgorithms
import biz.belcorp.salesforce.modules.postulants.utils.toUpperCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConsolidadoPresenter(
    val useCase: PostulantesUseCase,
    private val parametroUneteUseCase: ParametroUneteUseCase,
    private val uneteConfiguracionUseCase: UneteConfiguracionUseCase,
    private val mapParametroUnete: ParametroUneteModelDataMapper,
    private val obtenerSesionUseCase: ObtenerSesionUseCase,
    private val obtenerCampaniasUseCase: ObtenerCampaniasUseCase,
    private val configurationUseCase: ConfigurationUseCase,
    private val mapperPre: PrePostulantesModelDataMapper,
    val mapper: PostulantesModelDataMapper,
    val mapperConfig: UneteConfiguracionModelDataMapper,
    val mapperCampaign: CampaignModelDataMapper,
    private val errorMessageFactory: ErrorMessageFactory
) : BasePresenter() {

    private val sesion by lazy { obtenerSesionUseCase.obtener() }

    var listado: Int = UneteListado.EVALUACION.tipo
    var seccion: String? = null
    var postulanteModel: PostulanteModel? = null
    var estado: String? = null

    var consolidadoListadoView: ConsolidadoView? = null

    fun setView(view: ConsolidadoView) {
        consolidadoListadoView = view
    }

    private fun showErrorMessage(errorBundle: ErrorBundle) {

        val errorMessage = errorMessageFactory.create(
            this.consolidadoListadoView?.context(),
            errorBundle.exception
        )

        consolidadoListadoView?.showError(errorMessage)
    }

    private fun hideViewLoading() {
        consolidadoListadoView?.hideLoading()
    }

    private fun showLoading() {
        consolidadoListadoView?.showLoading()
    }

    override fun onDestroy() {
        useCase.dispose()
        consolidadoListadoView = null
    }

    fun esSocia() = sesion.codigoRol == Rol.SOCIA_EMPRESARIA.codigoRol

    fun getRol() = sesion.codigoRol

    private fun showPostulantes(list: List<Postulante>) {
        val model = mapper.map(list)
        if (model.isNotEmpty()) {
            consolidadoListadoView?.showListado(model)
        } else {
            consolidadoListadoView?.showListadoEmpty()
        }
    }

    private fun showPrePostulantes(list: List<PrePostulante>) {
        val model = mapperPre.map(list)
        if (model.isNotEmpty()) {
            consolidadoListadoView?.showPreListado(model)
        } else {
            consolidadoListadoView?.showPreListadoEmpty()
        }
    }

    fun getConfiguracionUnete() {
        val codigoPais = sesion.pais?.codigoIso.orEmpty()
        val codigoRol = sesion.codigoRol
        uneteConfiguracionUseCase.get(ObtenerUneteConfiguracionSubscriber(), codigoPais, codigoRol)
    }

    private inner class ObtenerUneteConfiguracionSubscriber : BaseSingleObserver<Configuracion>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
            consolidadoListadoView?.uneteConfiguracion(UneteConfiguracionModel())
        }

        override fun onSuccess(t: Configuracion) {
            consolidadoListadoView?.uneteConfiguracion(mapperConfig.map(t))
        }
    }

    private inner class UpdateEstadoAprobadaSubscriber : BaseSingleObserver<Boolean>() {

        override fun onError(exception: Throwable) {
            hideViewLoading()
            Logger.loge(javaClass.simpleName, exception.message)
            showErrorMessage(DefaultErrorBundle(exception as Exception))
        }

        override fun onSuccess(t: Boolean) {
            hideViewLoading()
            if (t) {
                consolidadoListadoView?.postulanteAprobada(true)
            }
        }
    }

    private inner class UpdateEstadoNoInteresadaSubscriber : BaseSingleObserver<Boolean>() {

        override fun onError(exception: Throwable) {
            hideViewLoading()
            Logger.loge(javaClass.simpleName, exception.message)
            showErrorMessage(DefaultErrorBundle(exception as Exception))
        }

        override fun onSuccess(t: Boolean) {
            hideViewLoading()
            if (t) {
                consolidadoListadoView?.postulanteNoInteresada(true)
            }
        }
    }

    private inner class ValidarBurosSubscriber : BaseSingleObserver<ValidacionBuroResponse>() {

        override fun onError(exception: Throwable) {
            hideViewLoading()
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: ValidacionBuroResponse) {
            hideViewLoading()
            if (t.enumEstadoCrediticio == UneteEstadoCrediticio.NO_PUEDE_SER_CONSULTORA.value) {
                val boolPagoDeContado = consolidadoListadoView?.esPagoContado() ?: false
                if (boolPagoDeContado)
                    consolidadoListadoView?.showPagoContadoModal(t)
                else
                    consolidadoListadoView?.showMensajeRechazoBuro(
                        "La postulante",
                        "lamentablemente no cumple con todos los requisitos de inscripción."
                    )
            } else {
                consolidadoListadoView?.validacionExitosa()
            }
        }
    }


    private inner class DownloadSubscriber : BaseSingleObserver<Int>() {

        override fun onError(exception: Throwable) {
            useCase.list(ListadoPostulanteSubscriber(), listado, seccion)
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: Int) {
            useCase.list(ListadoPostulanteSubscriber(), listado, seccion)
        }
    }

    private inner class DownloadPreSubscriber : BaseSingleObserver<Int>() {

        override fun onError(exception: Throwable) {
            hideViewLoading()
            useCase.listPre(ListadoPrePostulanteSubscriber(), listado, seccion)
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: Int) {
            useCase.listPre(ListadoPrePostulanteSubscriber(), listado, seccion)
        }
    }

    private inner class ListadoPostulanteSubscriber : BaseSingleObserver<List<Postulante>>() {

        override fun onError(exception: Throwable) {
            hideViewLoading()
            exception.message?.let { Logger.e(javaClass.simpleName, it) }
        }

        override fun onSuccess(t: List<Postulante>) {
            hideViewLoading()
            showPostulantes(t)
        }
    }

    private inner class ListadoPrePostulanteSubscriber :
        BaseSingleObserver<List<PrePostulante>>() {

        override fun onError(exception: Throwable) {
            hideViewLoading()
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<PrePostulante>) {
            if (!t.isNullOrEmpty()) showPrePostulantes(t)
        }
    }


    private inner class ObtenerZonaRiesgoSubscriber : BaseSingleObserver<ParametroUnete>() {
        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
            consolidadoListadoView?.showZonaRiesgo(false)
        }

        override fun onSuccess(t: ParametroUnete) {
            val codigoPais = sesion.countryIso
            val nivelRiesgo = t.valor ?: Constant.EMPTY_STRING
            consolidadoListadoView?.showZonaRiesgo(
                UneteAlgorithms.esZonaRiesgo(codigoPais, nivelRiesgo)
            )

        }
    }

    private inner class PagoContadoSubscriber : BaseSingleObserver<List<ParametroUnete>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<ParametroUnete>) {

            val y = mapParametroUnete.map(t)
            consolidadoListadoView?.esZonaPagoContado(y.isNotEmpty())

        }
    }

    private inner class ObtenerObservacionDevueltoSacSubscriber : BaseSingleObserver<String>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: String) {
            consolidadoListadoView?.showObservacionDevueltoSac(t)
        }
    }

    fun listPostulantes(
        listado: Int, seccion: String?, zona: String?, textoBusqueda: String = Constant.EMPTY_STRING
    ) {
        this.listado = listado
        this.seccion = seccion

        showLoading()

        if (listado == UneteListado.PRE_INSCRITAS.tipo)
            downloadPrepostulantes(listado, textoBusqueda)
        else downloadPostulantes(seccion, zona, listado, textoBusqueda)
    }

    private fun downloadPostulantes(seccion: String?, zona: String?, listado: Int, textoBusqueda: String) {
        val filtroBusqueda = FiltroBusqueda()
        filtroBusqueda.pais = sesion.pais?.codigoIso
        filtroBusqueda.region = sesion.region
        filtroBusqueda.zona = zona ?: sesion.zona
        filtroBusqueda.rol = sesion.codigoRol
        filtroBusqueda.seccion = seccion
        filtroBusqueda.tipoFiltro = listado
        filtroBusqueda.textoBusqueda = textoBusqueda
        this.useCase.download(DownloadSubscriber(), filtroBusqueda)
    }

    private fun downloadPrepostulantes(listado: Int, textoBusqueda: String) {
        this.useCase.downloadPre(DownloadPreSubscriber(), listado, textoBusqueda)
    }

    fun updateEstadoAprobada(
        pais: String, solicitudPostulanteID: Int, estadoPostulante: Int,
        subEstadoPostulante: Int?, tipoRechazo: String, motivoRechazo: String
    ) {
        showLoading()
        this.useCase.updateEstado(
            UpdateEstadoAprobadaSubscriber(), pais, solicitudPostulanteID, estadoPostulante,
            subEstadoPostulante, tipoRechazo, motivoRechazo
        )
    }

    fun updateEstadoNoInteresada(
        pais: String, solicitudPostulanteID: Int, estadoPostulante: Int,
        subEstadoPostulante: Int?
    ) {
        showLoading()
        this.useCase.updateEstado(
            UpdateEstadoNoInteresadaSubscriber(), pais, solicitudPostulanteID, estadoPostulante,
            subEstadoPostulante, Constant.EMPTY_STRING, Constant.EMPTY_STRING
        )
    }

    fun updatePostulante(postulanteModel: PostulanteModel) {
        val postulante = mapper.reverseMap(postulanteModel)
        consolidadoListadoView?.showLoading()
        useCase.update(UpdatePostulanteSubscriber(), postulante)
    }

    fun updatePostulanteTipoPago(postulanteModel: PostulanteModel) {
        val postulante = mapper.reverseMap(postulanteModel)
        consolidadoListadoView?.showLoading()
        useCase.update(UpdatePostulanteTipoPagoSubscriber(), postulante)
    }

    private inner class UpdatePostulanteTipoPagoSubscriber : BaseSingleObserver<Postulante>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
        }

        override fun onSuccess(t: Postulante) {
            hideViewLoading()
            validacionVerificacionPorZonas()
        }
    }

    private inner class UpdatePostulanteSubscriber : BaseSingleObserver<Postulante>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
        }

        override fun onSuccess(t: Postulante) {
            hideViewLoading()
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
            consolidadoListadoView?.restaurarPantalla()
            Logger.loge(javaClass.simpleName, t.toString())
        }
    }

    fun validarBuros(
        pais: String, documento: String, zona: String, seccion: String,
        tipoDocumento: Int, subestado: Int, postulante: Int
    ) {
        showLoading()

        val validacionBuro = ValidacionBuro()
        validacionBuro.pais = pais
        validacionBuro.documento = documento
        validacionBuro.zona = zona
        validacionBuro.seccion = seccion
        validacionBuro.tipoDocumento = tipoDocumento
        validacionBuro.subestado = subestado
        validacionBuro.postulante = postulante

        this.useCase.validarBuros(ValidarBurosSubscriber(), validacionBuro)
    }


    fun validarZonaRiesgo(zonaSeccion: String) {
        parametroUneteUseCase.list(
            ObtenerZonaRiesgoSubscriber(), UneteTipoParametro.NIVELRIESGO.tipo, zonaSeccion
        )
    }

    fun validarZonasPagoContado() {
        parametroUneteUseCase.getParametroUnete(
            PagoContadoSubscriber(), UneteTipoParametro.ZONAPAGOCONTADO.tipo
        )
    }

    fun getGZOptions() {
        when (sesion.codigoRol) {
            Rol.GERENTE_REGION.codigoRol -> consolidadoListadoView?.showGRzonesFilter()
            Rol.GERENTE_ZONA.codigoRol -> consolidadoListadoView?.showGZzonesFilter()
            Rol.SOCIA_EMPRESARIA.codigoRol -> sesion.seccion?.let {
                consolidadoListadoView?.toAssignValueToSE(it)
            }
        }
    }

    fun getObservacionDevueltoSac(solicitudPostulanteID: Int) {
        useCase.getObservacionDevueltoSac(
            ObtenerObservacionDevueltoSacSubscriber(),
            solicitudPostulanteID
        )
    }

    fun listValidarRangoEdad(postulante: PostulanteModel) {
        postulanteModel = postulante
        when (postulante.pais) {
            Pais.COSTARICA.codigoIso -> listValidarRangoEdadActualizar(postulante)
            else -> consolidadoListadoView?.validarEdadZonaRiesgo(postulante)
        }
    }

    fun listValidarRangoEdadActualizar(model: PostulanteModel) {
        postulanteModel = model
        parametroUneteUseCase.list(ValidarEdadSubscriber(), UneteTipoParametro.RANGOEDAD.tipo)
    }

    fun listTipoConsultoraSpinner() {
        val lst: MutableList<UneteListaModel> = arrayListOf()

        lst.add(agregarOpcion(UneteListado.EVALUACION))
        lst.add(agregarOpcion(UneteListado.PRE_APROBADOS))
        lst.add(agregarOpcion(UneteListado.APROBADOS))
        lst.add(agregarOpcion(UneteListado.RECHAZADOS))
        lst.add(agregarOpcion(UneteListado.INGRESOS_ANTICIPADOS))

        when (sesion.pais) {
            Pais.PUERTORICO -> agregarOpcionPreInscrita(lst)
            Pais.COLOMBIA -> Unit
            else -> lst.add(agregarOpcion(UneteListado.REGULARIZAR_DOCUMENTO))
        }

        lst.add(agregarOpcion(UneteListado.PROACTIVO_POR_FINALIZAR))
        lst.add(agregarOpcion(UneteListado.PROACTIVO_FINALIZADO))
        lst.add(agregarOpcion(UneteListado.PROACTIVO_PRE_APROBADOS))

        consolidadoListadoView?.showTipoPostulanteFilter(lst)
    }

    private fun agregarOpcionPreInscrita(lst: MutableList<UneteListaModel>) {
        lst.add(agregarOpcion(UneteListado.PRE_INSCRITAS))
        lst.add(agregarOpcion(UneteListado.REGULARIZAR_DOCUMENTO))
    }

    private fun agregarOpcion(item: UneteListado): UneteListaModel {
        val m = UneteListaModel()
        m.id = item.tipo
        m.descripcion = item.nombre.toUpperCase()
        return m
    }

    fun validacionVerificacionPorZonas() {
        parametroUneteUseCase.list(
            ValidarVerificacionPorZonasSubscriber(),
            UneteTipoParametro.REVISIONDOCUMENTOSAC.tipo
        )
    }

    private inner class ValidarVerificacionPorZonasSubscriber :
        BaseSingleObserver<List<ParametroUnete>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
            consolidadoListadoView?.showRevisionDocumentoSAC(false)
        }

        override fun onSuccess(t: List<ParametroUnete>) {
            val validarPorZonas = mapParametroUnete.map(t)
            validarPorZonas(validarPorZonas)
        }

    }

    private fun validarPorZonas(listaParametros: List<ParametroUneteModel>) {

        if (existenValores(listaParametros)) {

            var estado: String? = null
            listaParametros
                .filter {
                    it.nombre.orEmpty().contains(postulanteModel?.codigoZona.toString())
                }
                .forEach {
                    estado = it.estado
                }

            when {
                estado?.toInt() == 1 -> consolidadoListadoView?.showRevisionDocumentoSAC(true)
                else -> consolidadoListadoView?.showRevisionDocumentoSAC(false)
            }

        } else consolidadoListadoView?.showRevisionDocumentoSAC(false)

    }


    fun fillApprovedFilter() {
        return obtenerCampaniasUseCase.obtenerCampanias(FilterApproveSubscriber())
    }

    fun fillPaymentTypeFilter() {
        parametroUneteUseCase.list(
            FilterPaymentTypeSubscriber(),
            UneteTipoParametro.OPCIONESPAGOCONTADO.tipo
        )
    }

    fun getPais(): String {
        return sesion.pais?.codigoIso.orEmpty()
    }

    private fun getFilterApproved(list: List<CampaniaModel>) {
        val lst: MutableList<FiltroAprobadoUneteModel> = arrayListOf()

        var f = FiltroAprobadoUneteModel()
        f.id = UneteAprobadaFiltro.TODOS.code
        f.description = UneteAprobadaFiltro.TODOS.description
        f.campaign = Constant.NONE_LEVEL_NUMBER
        lst.add(f)

        for (campaignModel in list) {
            f.id = campaignModel.orden
            f = FiltroAprobadoUneteModel()
            f.description = campaignModel.nombreCorto
            f.campaign = campaignModel.codigo.toInt()
            lst.add(f)
        }

        consolidadoListadoView?.showFilter(lst)
    }

    private fun getFilterPaymentType(list: List<ParametroUneteModel>) {
        val lst: MutableList<FiltroAprobadoUneteModel> = arrayListOf()

        var f = FiltroAprobadoUneteModel()
        f.id = UneteAprobadaFiltro.TODOSTIPODEPAGO.code
        f.description = UneteAprobadaFiltro.TODOSTIPODEPAGO.description
        lst.add(f)

        for (parametroUnete in list) {
            f = FiltroAprobadoUneteModel()
            f.id = parametroUnete.valor.orEmpty().toInt()
            f.description = parametroUnete.nombre
            lst.add(f)

        }
        consolidadoListadoView?.showPaymentTypeFilter(lst)

    }

    private inner class ValidarEdadSubscriber : BaseSingleObserver<List<ParametroUnete>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
            consolidadoListadoView?.validarEdadZonaRiesgo(postulanteModel!!)
        }

        override fun onSuccess(t: List<ParametroUnete>) {
            val listaRangoEdad = mapParametroUnete.map(t)
            validarRangoDeEdadEnSac(listaRangoEdad, postulanteModel)
        }

    }

    private inner class FilterApproveSubscriber : BaseSingleObserver<List<Campania>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<Campania>) {
            getFilterApproved(mapperCampaign.map(t).take(2))
        }

    }

    private inner class FilterPaymentTypeSubscriber : BaseSingleObserver<List<ParametroUnete>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<ParametroUnete>) {
            getFilterPaymentType(mapParametroUnete.map(t))
        }

    }

    private fun validarRangoDeEdadEnSac(
        listaParametros: List<ParametroUneteModel>, postulante: PostulanteModel?
    ) {
        if (validarAprobacionSacYListaParametros(postulante, listaParametros)) {
            val valor1 = listaParametros[0].valor.orEmpty()
            val valor2 = listaParametros[1].valor.orEmpty()
            val edad = postulante?.edad ?: Constant.NUMERO_CERO

            validarSacSegunRangoDeEdad(valor1, valor2, edad)
        }

        postulanteModel?.let {
            consolidadoListadoView?.validarEdadZonaRiesgo(it)
        }

    }

    private fun validarAprobacionSacYListaParametros(
        postulante: PostulanteModel?,
        listaParametros: List<ParametroUneteModel>
    ) =
        aunNoTieneAprobacionPorSac(postulante) && existenValoresEnLaPosicion0y1(listaParametros)

    private fun aunNoTieneAprobacionPorSac(postulante: PostulanteModel?) =
        !postulante!!.requiereAprobacionSAC


    private fun existenValoresEnLaPosicion0y1(listaParametros: List<ParametroUneteModel>) =
        listaParametros.size > Constant.NUMERO_CERO && listaParametros.getOrNull(Constant.NUMERO_CERO) != null && listaParametros.getOrNull(
            Constant.NUMERO_UNO
        ) != null

    private fun existenValores(listaParametros: List<ParametroUneteModel>) =
        listaParametros.size > Constant.NUMERO_CERO


    private fun validarSacSegunRangoDeEdad(valor1: String, valor2: String, edad: Int) {
        if (valor1EsMenorQueValor2(valor1, valor2)) {
            consolidadoListadoView?.obtenerPostulanteSeleccionado()?.requiereAprobacionSAC =
                valor1.toInt() <= edad && edad <= valor2.toInt()
        } else if (valor1EsMayorQueValor2(valor1, valor2)) {
            consolidadoListadoView?.obtenerPostulanteSeleccionado()?.requiereAprobacionSAC =
                valor2.toInt() <= edad && edad <= valor1.toInt()
        }
    }

    private fun valor1EsMayorQueValor2(valor1: String, valor2: String) =
        valor1.toInt() > valor2.toInt()

    private fun valor1EsMenorQueValor2(valor1: String, valor2: String) =
        valor1.toInt() < valor2.toInt()

    fun getCountryiso() = sesion.countryIso

    fun actualizarPostulanteProactivo(
        estado: Int,
        solicitudPostulanteId: String,
        nomPostulante: String,
        motivoRechazo: String? = null
    ) {
        showLoading()
        useCase.actualizarPostulanteProactivo(
            useCaseSubscriber = ActualizarPostulanteProactivoSubscriber(
                codeOperacion = estado,
                nomPostulante = nomPostulante
            ),
            codigoIso = sesion.countryIso,
            estado = estado,
            solicitudPostulanteId = solicitudPostulanteId,
            motivoRechazo = motivoRechazo
        )
    }

    fun getUser() = sesion



    fun getConfiguration() {
        launch(Dispatchers.IO) {
            val config = configurationUseCase.getConfiguration()

            withContext(Dispatchers.Main) {
                consolidadoListadoView?.setConfiguration(config)
            }
        }
    }

    private inner class ActualizarPostulanteProactivoSubscriber(
        private val codeOperacion: Int,
        private val nomPostulante: String
    ) : BaseSingleObserver<PostulanteProactivoResponse?>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
            hideViewLoading()
            showErrorMessage(DefaultErrorBundle(exception as Exception))
        }

        override fun onSuccess(t: PostulanteProactivoResponse?) {
            hideViewLoading()
            if (codeOperacion == UneteProactivaPreAprobadaStatus.APROBAR.value) {
                consolidadoListadoView?.onProactivaAprobada(nomPostulante)
                return
            }
            if (codeOperacion == UneteProactivaPreAprobadaStatus.RECHAZAR.value) {
                consolidadoListadoView?.onProactivaRechazada(nomPostulante)
                return
            }
        }
    }
}

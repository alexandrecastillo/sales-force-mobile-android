package biz.belcorp.salesforce.modules.postulants.features.indicador

import biz.belcorp.salesforce.core.base.BasePresenter
import biz.belcorp.salesforce.core.domain.entities.pais.Pais
import biz.belcorp.salesforce.core.domain.entities.ua.Seccion
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.messaging.core.domain.entities.PostulantsNotification
import biz.belcorp.salesforce.messaging.core.domain.usecases.GetNotificationUseCase
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.IndicadorUnete
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.IndicadorUseCase
import biz.belcorp.salesforce.modules.postulants.features.indicador.entities.BaseGeo
import biz.belcorp.salesforce.modules.postulants.features.indicador.mappers.IndicadorModelDataMapper
import biz.belcorp.salesforce.modules.postulants.features.indicador.mappers.SeccionModelDataMapper
import biz.belcorp.salesforce.modules.postulants.utils.Constant
import biz.belcorp.salesforce.modules.postulants.utils.toUpperCase
import kotlinx.coroutines.launch


class IndicadorPresenter(
    private val useCase: IndicadorUseCase,
    private val obtenerSesionUseCase: ObtenerSesionUseCase,
    private val indicadorModelDataMapper: IndicadorModelDataMapper,
    private val seccionModelDataMapper: SeccionModelDataMapper,
    private val getNotificationUseCase: GetNotificationUseCase
) : BasePresenter() {

    private var indicatorsView: IndicadorView? = null
    private val session by lazy { obtenerSesionUseCase.obtener() }
    private var section: String? = null
    private var zone: String? = null

    fun setView(view: IndicadorView) {
        this.indicatorsView = view
    }

    override fun onDestroy() {
        super.onDestroy()
        indicatorsView = null
        useCase.dispose()
    }

    fun init(code: String?) {
        launch {
            initDeeplink(code)
            initUnete()
        }
    }

    private suspend fun initDeeplink(code: String?) {
        code ?: return

        val notification = getNotificationUseCase.getNotification(code) as? PostulantsNotification ?: return
        val filterMode = notification.data?.step?.toIntOrNull() ?: return
        indicatorsView?.applyFilterMode(filterMode)
    }

    fun initUnete() {
        when (session.codigoRol) {
            Rol.GERENTE_ZONA.codigoRol, Rol.GERENTE_REGION.codigoRol -> loadSeccionesGZ()
            else -> hideCircleBar()
        }
    }

    private fun hideCircleBar() {
        showList(emptyList())
    }

    private fun showList(list: List<BaseGeo>) {
        when (session.codigoRol) {
            Rol.GERENTE_REGION.codigoRol -> {
                zone = formatZone(list)
            }
            else -> Unit
        }

        loadUnete()
        indicatorsView?.showSections(list)
    }

    private fun formatZone(list: List<BaseGeo>): String {
        return list.firstOrNull()
            ?.descripcion?.replace(Constant.PREFIX_ZONE, Constant.EMPTY_STRING)
            ?.substring(Constant.NUMERO_CERO, Constant.NUMERO_CUATRO).orEmpty()
    }

    private fun loadSeccionesGZ() {
        useCase.getSecciones(SeccionesSubscriber())
    }

    fun saveIndicadoresUnete(listaIndicadores: List<IndicadorUnete>, isReloadingHeader: Boolean) {
        useCase.saveIndicadorUnete(listaIndicadores, SaveIndicadorUneteSubscriber(isReloadingHeader))
    }

    fun onChangeSelectedZone(zone: String) {
        this.zone = zone
        loadUnete(isReloadingHeader = true)
    }

    private fun loadUnete(isReloadingHeader: Boolean = false) {
        showLoading()
        this.useCase.getUneteCloud(
            getCountry(), zone ?: getZone(), getRegion(), getRol(), getSeccion(), UneteCloudSubscriber(isReloadingHeader)
        )
    }

    private fun showIndicatorUnete(indicadorUnete: IndicadorUnete?, isReloadingHeader: Boolean) {
        val indicadorUneteModel = indicadorModelDataMapper.transformIndicadorUnete(indicadorUnete)
        this.indicatorsView?.showIndicadorUnete(indicadorUneteModel, isReloadingHeader)
    }

    private fun loadUneteDB(isReloadingHeader: Boolean) {
        section = recuperarUaSegunRol(session.codigoRol)
        useCase.getUneteDB(session.rol.codigoRol, section.orEmpty(), UneteDBSubscriber(isReloadingHeader))
    }

    private fun recuperarUaSegunRol(rol: String): String {
        return when (rol.toUpperCase()) {
            Rol.GERENTE_REGION.codigoRol.toUpperCase() -> session.zona.orEmpty()
            Rol.GERENTE_ZONA.codigoRol.toUpperCase() -> session.seccion.orEmpty()
            Rol.SOCIA_EMPRESARIA.codigoRol.toUpperCase() -> session.seccion.orEmpty()
            else -> Constant.EMPTY_STRING
        }
    }

    private fun getCountry(): String = session.countryIso
    fun getCountryCode() = Pais.find(getCountry())
    fun getZone(): String = session.zona.orEmpty()
    fun getRegion(): String = session.region.orEmpty()
    fun getSeccion(): String = session.seccion.orEmpty()
    fun getRol(): String = session.codigoRol

    private fun hideViewLoading() {
        indicatorsView?.hideLoading()
    }

    private fun showLoading() {
        indicatorsView?.showLoading()
    }

    private fun showSecciones(campaign: Collection<Seccion>) {
        val model = seccionModelDataMapper.transformList(campaign)
        showList(model)
    }

    private inner class SeccionesSubscriber : BaseObserver<List<Seccion>>() {

        override fun onError(exception: Throwable) {
            Logger.loge(javaClass.simpleName, exception.message)
            showList(emptyList())
        }

        override fun onNext(t: List<Seccion>) {
            showSecciones(t)
        }
    }

    private inner class UneteCloudSubscriber(
        private val isReloadingHeader: Boolean
    ) : BaseSingleObserver<List<IndicadorUnete>>() {

        override fun onSuccess(t: List<IndicadorUnete>) {
            saveIndicadoresUnete(t, isReloadingHeader)
        }

        override fun onError(exception: Throwable) {
            hideViewLoading()
            Logger.loge(javaClass.simpleName, exception.message)
            loadUneteDB(isReloadingHeader)
        }
    }

    private inner class SaveIndicadorUneteSubscriber(
        private val isReloadingHeader: Boolean
    ) : BaseObserver<Boolean>() {

        override fun onError(exception: Throwable) {
            hideViewLoading()
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onNext(t: Boolean) {
            loadUneteDB(isReloadingHeader)
        }
    }

    private inner class UneteDBSubscriber(
        private val isReloadingHeader: Boolean
    ) : BaseObserver<IndicadorUnete>() {

        override fun onError(exception: Throwable) {
            hideViewLoading()
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onNext(t: IndicadorUnete) {
            hideViewLoading()
            showIndicatorUnete(t, isReloadingHeader)
        }
    }

}

package biz.belcorp.salesforce.modules.postulants.features.indicador.components.resumen

import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.DetalleIndicador
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.DetalleIndicadorUnete
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.IndicadorUseCase
import biz.belcorp.salesforce.modules.postulants.features.base.Presenter
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.resumen.mappers.DetalleIndicatorModelDataMapper
import biz.belcorp.salesforce.modules.postulants.utils.Constant

class ResumenConsolidadoPresenter(
    val useCase: IndicadorUseCase,
    val obtenerSesionUseCase: ObtenerSesionUseCase,
    private val detalleIndicatorModelDataMapper: DetalleIndicatorModelDataMapper
) : Presenter {

    private var consolidadoView: ResumenConsolidadoView? = null

    private val session get() = obtenerSesionUseCase.obtener()

    fun setView(view: ResumenConsolidadoView) {
        this.consolidadoView = view
    }

    fun loadConsolidado(zone: String?) {
        consolidadoView?.showLoading()
        useCase.detalleIndicadorCloud(
            getCountry(), zone ?: getZone(), getRegion(), getRol(), DetalleIndicadorCloudSubscriber(zone)
        )

    }

    private fun getCountry(): String {
        return session.countryIso
    }

    private fun getZone(): String {
        return session.zona.orEmpty()
    }

    private fun getRegion(): String {
        return session.region.orEmpty()
    }

    fun getRol(): String {
        return session.codigoRol
    }

    private fun saveDetalleIndicadorUnete(list: List<DetalleIndicadorUnete>, zone: String?) {
        useCase.saveDetalleIndicador(list, SaveDetalleIndicadorCloudSubscriber(zone))
    }

    private fun loadDetalleIndicadorUneteDB(zone: String?) {
        useCase.detalleIndicador(
            getCountry(), zone ?: getZone(),
            getRegion(), Constant.EMPTY_STRING,
            DetalleIndicadorDBSubscriber()
        )
    }

    private fun hideViewLoadingConsolidado() {
        consolidadoView?.hideLoading()
    }

    private fun showDetalle(list: Collection<DetalleIndicador>) {
        val model = detalleIndicatorModelDataMapper.parse(list)
        consolidadoView?.showConsolidado(model)
    }

    private fun calculateResume(datos: List<DetalleIndicador>): DetalleIndicadorUnete {
        val resume = DetalleIndicadorUnete()
        var aprobadasSum = 0
        var preAprobadasSum = 0
        var rechazadasSum = 0
        var enEvaluacionSum = 0
        var enIngresoAnticipadoSum = 0
        var enPreInscritasSum = 0
        var enRegularizarDocSum = 0
        var proactivoFinalizar = 0
        var proactivoFinalizados = 0
        var proactivoPreAprobados = 0

        datos.forEach {
            it as DetalleIndicadorUnete
            aprobadasSum += it.aprobadas ?: Constant.NUMERO_CERO
            preAprobadasSum += it.preAprobadas ?: Constant.NUMERO_CERO
            rechazadasSum += it.rechazadas ?: Constant.NUMERO_CERO
            enEvaluacionSum += it.enEvaluacion ?: Constant.NUMERO_CERO
            enIngresoAnticipadoSum += it.ingresosAnticipados ?: Constant.NUMERO_CERO
            enPreInscritasSum += it.preInscritas ?: Constant.NUMERO_CERO
            enRegularizarDocSum += it.regularizarDocumento ?: Constant.NUMERO_CERO
            proactivoFinalizar += it.proactivoFinalizar ?: Constant.NUMERO_CERO
            proactivoFinalizados += it.proactivoFinalizados ?: Constant.NUMERO_CERO
            proactivoPreAprobados += it.proactivoPreAprobados ?: Constant.NUMERO_CERO
        }

        resume.aprobadas = aprobadasSum
        resume.preAprobadas = preAprobadasSum
        resume.rechazadas = rechazadasSum
        resume.enEvaluacion = enEvaluacionSum
        resume.ingresosAnticipados = enIngresoAnticipadoSum
        resume.preInscritas = enPreInscritasSum
        resume.regularizarDocumento = enRegularizarDocSum
        resume.proactivoFinalizar = proactivoFinalizar
        resume.proactivoFinalizados = proactivoFinalizados
        resume.proactivoPreAprobados = proactivoPreAprobados

        return resume
    }

    fun obtenerCodigoPais(): String? {
        return session.pais?.codigoIso.orEmpty()
    }

    fun getGROptions() {
        when (session.codigoRol) {
            Rol.GERENTE_REGION.codigoRol -> consolidadoView?.showGrFilters()
            else -> consolidadoView?.onLoadGROptions()

        }
    }

    private inner class DetalleIndicadorDBSubscriber :
        BaseSingleObserver<List<DetalleIndicador>>() {

        override fun onError(exception: Throwable) {
            hideViewLoadingConsolidado()
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onSuccess(t: List<DetalleIndicador>) {
            hideViewLoadingConsolidado()
            val response = mutableListOf<DetalleIndicador>()
            val first = calculateResume(t)
            first.zona = Constant.EMPTY_STRING
            first.seccion = null
            response.add(Constant.NUMERO_CERO, first)
            response.addAll(t)
            showDetalle(response)
        }
    }


    private inner class DetalleIndicadorCloudSubscriber(
        private val zone: String?
    ) :
        BaseSingleObserver<List<DetalleIndicador>>() {

        override fun onError(exception: Throwable) {
            consolidadoView?.hideLoading()
            Logger.loge(javaClass.simpleName, exception.message)
            loadDetalleIndicadorUneteDB(zone)

        }

        override fun onSuccess(t: List<DetalleIndicador>) {
            saveDetalleIndicadorUnete(t as List<DetalleIndicadorUnete>, zone)
        }
    }

    private inner class SaveDetalleIndicadorCloudSubscriber(
        private val zone: String?
    ) : BaseObserver<Boolean>() {

        override fun onComplete() {
            hideViewLoadingConsolidado()
        }

        override fun onError(exception: Throwable) {
            hideViewLoadingConsolidado()
            Logger.loge(javaClass.simpleName, exception.message)
        }

        override fun onNext(t: Boolean) {
            loadDetalleIndicadorUneteDB(zone)
        }

    }


}

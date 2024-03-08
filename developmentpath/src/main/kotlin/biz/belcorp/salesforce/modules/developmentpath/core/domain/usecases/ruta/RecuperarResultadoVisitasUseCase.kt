package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.utils.aGuionSiEsNullOVacio
import biz.belcorp.salesforce.modules.developmentpath.core.domain.sugerencias.RecuperadorSugerencias
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.ConstructorResultadoPedido
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ruta.ResultadoPedido
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sesion.persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.resultados.ResultadoVisitasRepository
import io.reactivex.Single
import io.reactivex.SingleObserver

class RecuperarResultadoVisitasUseCase(
    private val campaniaRepository: CampaniasRepository,
    private val resultadoVisitasRepository: ResultadoVisitasRepository,
    private val recuperadorSugerencias: RecuperadorSugerencias,
    private val sesionManager: SessionPersonRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    private var resultadosPedido: List<ResultadoPedido> = emptyList()

    private val resultadosFiltrados: List<ResultadoPedido>
        get() {
            return resultadosPedido.filter { it.consultora.match(filtro) }
        }

    private var filtro = ""

    fun recuperarFacturadas(observer: SingleObserver<ResponseRecuperar>) {
        recuperarConsultoras(true, observer)
    }

    fun recuperarNoFacturadas(observer: SingleObserver<ResponseRecuperar>) {
        recuperarConsultoras(false, observer)
    }

    private fun recuperarConsultoras(
        facturadas: Boolean,
        observer: SingleObserver<ResponseRecuperar>
    ) {
        val single = Single.create<ResponseRecuperar> { emitter ->
            val campaniaActual = checkNotNull(campaniaRepository.obtenerCampaniaActual(recuperarLlaveUa()))
            val campaniaAnterior = checkNotNull(campaniaRepository.obtenerPenultimasCampanias(recuperarLlaveUa(), 1))
            val consultoras = obtenerConsultorasDeRepository(facturadas)
            val constructorResultadoPedido = ConstructorResultadoPedido(recuperarPrefijoPais())
            resultadosPedido =
                consultoras.map { constructorResultadoPedido.instanciarSinPedido(it) }

            val response = ResponseRecuperar(
                campania = campaniaActual,
                campaniaAnterior = if (campaniaAnterior.isNotEmpty()) campaniaAnterior[0] else campaniaActual,
                resultadosPedido = resultadosPedido,
                facturada = facturadas,
                sugerencias = recuperadorSugerencias
                    .obtenerSugerencias(consultoras)
            )

            emitter.onSuccess(response)
        }

        execute(single, observer)
    }

    private fun recuperarPrefijoPais() = sesionManager.get()?.pais?.prefijo ?: ""

    private fun obtenerConsultorasDeRepository(facturadas: Boolean): List<ConsultoraRdd> {
        return if (facturadas) resultadoVisitasRepository.recuperarConsultorasFacturadas()
        else resultadoVisitasRepository.recuperarConsultorasNoFacturadas()
    }

    fun filtrar(filtro: String, observer: SingleObserver<ResponseFiltrar>) {
        val single = Single.create<ResponseFiltrar> { emitter ->
            this.filtro = filtro
            val response = ResponseFiltrar(filtro, resultadosFiltrados)

            emitter.onSuccess(response)
        }

        execute(single, observer)
    }

    fun mostrarConsultorasConPedido(observer: SingleObserver<ResponseMostrarPedido>) {
        val single = Single.create<ResponseMostrarPedido> { emitter ->
            val codigoCampania =
                checkNotNull(campaniaRepository.obtenerCampaniaActual(recuperarLlaveUa())?.codigo)
            val llaveUA = checkNotNull(sesionManager.get().persona.llaveUA)

            resultadoVisitasRepository
                .recuperarMontosDeConsultoras(codigoCampania, llaveUA)
                .subscribe({ montos ->
                    aplicarNuevosMontosAResultados(montos)
                    emitter.onSuccess(ResponseMostrarPedido(resultadosFiltrados))
                }, { error -> emitter.onError(error) })
        }

        execute(single, observer)
    }

    private fun aplicarNuevosMontosAResultados(consultorasConPedido: Map<Long, Double>) {
        resultadosPedido.forEach {
            consultorasConPedido[it.consultora.id]?.apply { it.montoPedido = this }
        }
    }

    fun ocultarConsultorasConPedido(observer: SingleObserver<ResponseMostrarPedido>) {

        val single = Single.create<ResponseMostrarPedido> {

            resultadosPedido.forEach { it.quitarPedido() }

            it.onSuccess(ResponseMostrarPedido(resultadosFiltrados))
        }

        execute(single, observer)
    }

    private fun recuperarLlaveUa(): LlaveUA {
        val sesion = requireNotNull(sesionManager.get())

        return LlaveUA(
            codigoRegion = sesion.region.aGuionSiEsNullOVacio(),
            codigoZona = sesion.zona.aGuionSiEsNullOVacio(),
            codigoSeccion = sesion.seccion.aGuionSiEsNullOVacio(),
            consultoraId = null
        )
    }

    data class ResponseRecuperar(
        val facturada: Boolean,
        val campania: Campania,
        val campaniaAnterior: Campania,
        val resultadosPedido: List<ResultadoPedido>,
        val sugerencias: List<String>
    )

    data class ResponseFiltrar(
        val filtro: String,
        val resultadosPedido: List<ResultadoPedido>
    )

    data class ResponseMostrarPedido(val resultadosPedido: List<ResultadoPedido>)
}

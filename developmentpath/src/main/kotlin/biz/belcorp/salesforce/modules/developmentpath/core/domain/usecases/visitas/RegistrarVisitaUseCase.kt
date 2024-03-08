package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.visitas

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.utils.aGuionSiEsNullOVacio
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.Acuerdo
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.kinesis.KinesisTag
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sync.UploadVisitasRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.Visita
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.acuerdos.AcuerdosRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta.IntencionPedidoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.kinesis.KinesisManagerUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.doAsync
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.validator.ValidadorRegistroVisita
import biz.belcorp.salesforce.modules.developmentpath.core.domain.utils.validator.ValidadorTiempos
import io.reactivex.Observable

class RegistrarVisitaUseCase(
    private val visitaRepository: RddPersonaRepository,
    private val uploadVisitasRepository: UploadVisitasRepository,
    private val acuerdosRepository: AcuerdosRepository,
    private val intencionPedidoRepository: IntencionPedidoRepository,
    private val sesionManager: SessionPersonRepository,
    private val campaniaRepository: CampaniasRepository,
    private val kinesisManager: KinesisManagerUseCase,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    private lateinit var sesion: Sesion
    private lateinit var visita: Visita
    private lateinit var validadorTiempos: ValidadorTiempos
    private lateinit var validadorRegistroVisita: ValidadorRegistroVisita
    private lateinit var campania: Campania

    fun registrarVisita(request: Request) {
        val observable = Observable.create<Unit> { emitter ->
            recuperarDatosRegistro(request.visitaId)
            validarRegistro()
            registrar()
            persistirDatos(request)
            emitter.onNext(Unit)
            sincronizarDatos()
            //registrarEnKinesis()
        }
        execute(observable, request.subscriber)
    }

    private fun sincronizarDatos() {
        sincronizarVisitas()
        sincronizarIntencionPedido()
    }

    private fun persistirDatos(request: Request) {
        grabarVisita()
        grabarIntencionPedido(request.pasaPedido)
        grabarAcuerdos(request.acuerdosOrdenados)
    }

    private fun recuperarDatosRegistro(visitaId: Long) {
        visita = recuperarVisita(visitaId)
        campania = recuperarCampania()
        validadorTiempos = ValidadorTiempos(campania)
        validadorRegistroVisita = ValidadorRegistroVisita(visita)
        sesion = recuperarSesion()
    }

    private fun recuperarVisita(visitaId: Long): Visita {
        return requireNotNull(visitaRepository.recuperarVisita(visitaId)) {
            "Visita no encontrada"
        }
    }

    private fun recuperarCampania(): Campania {
        val campania =
            requireNotNull(campaniaRepository.obtenerCampaniaActual(recuperarLlaveUa())) {
                "No se pudo get campaña actual"
            }
        requireNotNull(campania.codigo) { "Código de campaña inválido" }
        return campania
    }

    private fun recuperarSesion(): Sesion {
        return requireNotNull(sesionManager.get()) {
            "Sesión inválida"
        }
    }

    private fun validarRegistro() {
        validadorTiempos.validarHoy()
        validadorRegistroVisita.validarRegistroHoy()
    }

    private fun registrar() {
        addUsuarioRed()
        visita.registrarAhora()
    }

    private fun addUsuarioRed() {
        if (visita.usuarioRed.isEmpty()) {
            visita.usuarioRed = sesion.codeByRole
        }
    }

    private fun grabarVisita() {
        visitaRepository.actualizarVisita(visita)
    }

    private fun grabarAcuerdos(acuerdos: List<Acuerdo.ModeloCreacion>) {
        acuerdosRepository.insertar(acuerdos)
    }

    private fun grabarIntencionPedido(pasaPedido: Boolean) {
        if (visita.persona !is ConsultoraRdd) return
        val requestIntencion = crearRequestIntencion(pasaPedido)
        intencionPedidoRepository.guardar(requestIntencion)
    }

    private fun crearRequestIntencion(pasaPedido: Boolean): IntencionPedidoRepository.RequestGuardado {
        return IntencionPedidoRepository
            .RequestGuardado(
                pasaPedido = pasaPedido,
                llaveUa = visita.persona.llaveUA,
                codigoCampania = campania.codigo,
                usuarioCreacion = sesion.codigoUsuario
            )
    }

    private fun sincronizarVisitas() {
        uploadVisitasRepository
            .syncUpRegisterVisits()
            .doAsync()
    }

    private fun sincronizarIntencionPedido() {
        intencionPedidoRepository
            .sincronizar()
            .doAsync()
    }

    private fun registrarEnKinesis() {
        kinesisManager
            .enviarAsync(KinesisTag.EVENTO_FINALIZAR_VISITA)
            .doAsync()
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


    class Request(
        val visitaId: Long,
        val acuerdos: List<Acuerdo.ModeloCreacion>,
        val pasaPedido: Boolean,
        val subscriber: BaseObserver<Unit>
    ) {
        val acuerdosOrdenados get() = acuerdos.sortedBy { it.fecha }
    }
}

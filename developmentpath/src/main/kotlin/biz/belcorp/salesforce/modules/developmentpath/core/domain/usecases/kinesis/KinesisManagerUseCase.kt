package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.kinesis

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.aGuionSiEsNullOVacio
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.kinesis.KinesisTag
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.SociaEmpresariaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sesion.persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.kinesis.KinesisManagerRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import io.reactivex.Single
import io.reactivex.SingleObserver
import java.text.SimpleDateFormat
import java.util.*

class KinesisManagerUseCase(
    private val sesionManager: SessionPersonRepository,
    private val campaniaRepository: CampaniasRepository,
    private val kinesisManangerRepository: KinesisManagerRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    private val formatoFecha = "yyyy-MM-dd HH:mm:ss"

    fun ejecutarEnvio(request: RequestKinesis) {
        val single = enviarAsync(request.pantalla)
        execute(single, request.observer)
    }

    fun enviarAsync(tag: KinesisTag): Single<String> {
        return Single.create<String> {
            val logString = enviar(tag)
            it.onSuccess(logString)
        }
    }

    private fun enviar(tag: KinesisTag): String {
        val sesion = sesionManager.get()
        val campania =
            campaniaRepository.obtenerCampaniaActual(recuperarLlaveUa())?.codigo.validarCampania()
        val logKinesis = LogKinesis(
            opcionPantalla = tag.nombre,
            fecha = getfechaActual(formatoFecha),
            paisOrigen = sesion?.countryIso,
            paisIngreso = sesion?.countryIso,
            region = sesion?.region,
            zona = sesion?.zona,
            seccion = sesion?.seccion,
            rol = sesion?.rol?.codigoRol,
            usuario = validaeUser(sesion?.rol),
            campania = campania,
            opcionAccion = tag.crearAccion(),
            dispositivoCategoria = "MOBILE",
            dispositivoSo = "Android"
        )
        return kinesisManangerRepository.enviarDatosGrabados(logKinesis)
    }

    private fun KinesisTag.crearAccion(): String {
        return when (this) {
            KinesisTag.EVENTO_FINALIZAR_VISITA -> "FINALIZAR_VISITA"
            KinesisTag.PANTALLA_RDD -> "INGRESAR"
            KinesisTag.PANTALLA_HOME -> "INGRESAR"
            else -> ""
        }
    }

    private fun String?.validarCampania(): String {
        if (this != null && this.length == 6 &&
            this.substring(0, 3).equals("202", ignoreCase = false)
        )
            return this
        else
            throw Throwable("Campania inválida")
    }

    private fun getfechaActual(mstutil: String): String {
        val fechaActual = Date()
        val date = SimpleDateFormat(mstutil, Locale.getDefault())
        return date.format(fechaActual)
    }

    private fun validaeUser(rol: Rol?): String {
        return when (rol) {
            Rol.GERENTE_ZONA -> {
                sesionManager.get().codigoUsuario
            }
            Rol.GERENTE_REGION -> {
                sesionManager.get().codigoUsuario
            }
            Rol.CONSULTORA -> {
                (sesionManager.get().persona as SociaEmpresariaRdd).codigo
            }
            Rol.SOCIA_EMPRESARIA -> {
                (sesionManager.get().persona as SociaEmpresariaRdd).codigo
            }
            else -> Constant.EMPTY_STRING
        }
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


    class RequestKinesis(
        val pantalla: KinesisTag,
        val observer: SingleObserver<String>
    )

    class LogKinesis(
        var fecha: String? = null,
        var aplicacion: String? = null,
        var paisOrigen: String? = null,
        var paisIngreso: String? = null,
        var region: String? = null,
        var zona: String? = null,
        var seccion: String? = null,
        var rol: String? = null,
        var campania: String? = null,
        var usuario: String? = null,
        var opcionPantalla: String? = null,
        var opcionAccion: String? = null,
        var dispositivoCategoria: String? = null,
        var dispositivoSo: String? = null,
        var dispositivoId: String? = null,
        var version: String? = null,
        var offline: Boolean? = null
    )
}

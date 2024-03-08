package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cabecera

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.entities.zonificacion.UnidadAdministrativa
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.utils.WordUtils
import biz.belcorp.salesforce.core.utils.diferenciaDeNivel
import biz.belcorp.salesforce.core.utils.formatHyphenIfNull
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.plan.InfoPlanRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sesion.persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.puntaje.PuntajeReconocimientoRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.unidadadministrativa.UnidadAdministrativaRepository
import io.reactivex.Single

class CabeceraUseCase(
    private val campaniasRepository: CampaniasRepository,
    private val sesionManager: SessionPersonRepository,
    private val planRepository: RddPlanRepository,
    private val puntajeReconoce: PuntajeReconocimientoRepository,
    private val uARepository: UnidadAdministrativaRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    UseCase(threadExecutor, postExecutionThread) {

    private val constructorTitulo = ConstructorTitulo()

    fun ejecutar(planId: Long, subscriber: BaseSingleObserver<Response>) {

        val single = Single.create<Response> {
            val unidadAdministrativa = requireNotNull(uARepository.obtenerUaPorPlan(planId))
            val campaniaActual = recuperarUltimaCampania(unidadAdministrativa.llave)
            val campaniaPenultima = recuperarPenultimaCampania(unidadAdministrativa.llave)
            val sesion = requireNotNull(sesionManager.get())
            val infoPlan = requireNotNull(planRepository.obtenerInfoPlanRdd(planId))

            val response = Response(
                campaniaActual = campaniaActual,
                sesion = sesion
            )

            if (esDueniaDeRuta(sesion, infoPlan)) {
                response.cargarValoresComoDuenia(
                    unidadAdministrativa,
                    campaniaPenultima.codigo,
                    sesion
                )
            } else {
                response.cargarValoresDeHija(
                    unidadAdministrativa,
                    campaniaPenultima.codigo,
                    infoPlan
                )
            }

            it.onSuccess(response)
        }
        execute(single, subscriber)
    }

    private fun recuperarUltimaCampania(llaveUA: LlaveUA): Campania {
        return requireNotNull(campaniasRepository.obtenerCampaniaActual(llaveUA))
    }

    private fun recuperarPenultimaCampania(llaveUA: LlaveUA): Campania {
        return campaniasRepository.obtenerCampaniaInmediataAnterior(llaveUA)
    }

    private fun esDueniaDeRuta(sesion: Sesion, infoPlan: InfoPlanRdd): Boolean {
        return sesion.rol diferenciaDeNivel infoPlan.rol == 0
    }

    private fun Response.cargarValoresComoDuenia(
        unidadAdministrativa: UnidadAdministrativa,
        campaniaAnterior: String?,
        sesion: Sesion
    ) {
        iniciales = sesion.persona.iniciales
        rol = sesion.rol
        planId = obtenerPlanId(sesion)
        esDuenia = true
        coberturada = true
        titulo = constructorTitulo.crearTituloComoDuenia(unidadAdministrativa, sesion)
        mostrarCalificacion = mostrarCalificacion(sesion.rol)
        cargarPuntaje(unidadAdministrativa.llave, campaniaAnterior)
    }

    private fun Response.cargarValoresDeHija(
        unidadAdministrativa: UnidadAdministrativa,
        campaniaAnterior: String?,
        infoPlan: InfoPlanRdd
    ) {

        iniciales = unidadAdministrativa.responsableUA?.iniciales ?: "?"
        rol = infoPlan.rol
        planId = infoPlan.planId
        esDuenia = false
        coberturada = unidadAdministrativa.coberturada
        titulo = constructorTitulo.crearTituloDeHija(unidadAdministrativa, rol)
        mostrarCalificacion = mostrarCalificacion(infoPlan.rol)
        cargarPuntaje(infoPlan.llaveUA, campaniaAnterior)

    }

    private fun Response.cargarPuntaje(llaveUA: LlaveUA, campaniaAnterior: String?) {
        puntajeReconocimiento = puntajeReconoce
            .obtenerPuntaje(llaveUA.formatHyphenIfNull(), campaniaAnterior, rol).puntaje
    }

    private fun obtenerPlanId(sesion: Sesion) = when (sesion.rol) {
        Rol.DIRECTOR_VENTAS -> planRepository.obtenerPlanParaDV()
        Rol.GERENTE_REGION -> planRepository.obtenerPlanParaGR(requireNotNull(sesion.region))
        Rol.GERENTE_ZONA -> planRepository.obtenerPlanParaGZ(requireNotNull(sesion.zona))
        Rol.SOCIA_EMPRESARIA -> planRepository.obtenerPlanParaSE(
            requireNotNull(sesion.zona),
            requireNotNull(sesion.seccion)
        )
        else -> throw Exception("Rol no soportado")
    }

    private fun mostrarCalificacion(rol: Rol) = when (rol) {
        Rol.DIRECTOR_VENTAS -> true
        Rol.GERENTE_REGION -> true
        Rol.GERENTE_ZONA -> true
        else -> false
    }

    class Response(
        val campaniaActual: Campania,
        var iniciales: String = "",
        var titulo: String = "",
        var planId: Long? = null,
        var mostrarCalificacion: Boolean? = false,
        var coberturada: Boolean = true,
        var rol: Rol = Rol.NINGUNO,
        var puntajeReconocimiento: Float? = null,
        var esDuenia: Boolean = false,
        var sesion: Sesion
    )

    class ConstructorTitulo {

        fun crearTituloDeHija(unidadAdministrativa: UnidadAdministrativa, rol: Rol): String {
            val nombre = unidadAdministrativa.run {
                if (coberturada)
                    "${responsableUA?.primerNombre} ${responsableUA?.primerApellido}"
                else
                    "Descoberturada"
            }
            return completarTituloConNombre(nombre, rol, unidadAdministrativa.codigo)
        }

        fun crearTituloComoDuenia(unidadAdministrativa: UnidadAdministrativa, sesion: Sesion) =
            completarTituloConNombre(
                sesion.persona.nombreCorto,
                sesion.rol,
                unidadAdministrativa.codigo
            )

        private fun completarTituloConNombre(nombre: String, rol: Rol, codigoUa: String) =
            "${WordUtils.capitalizeFully(nombre)}"
    }

}

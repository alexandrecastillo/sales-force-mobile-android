package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.analytics

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.domain.entities.analytics.*
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalyticsHelper.construirEvento2
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalyticsHelper.construirEventoPorRol
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalyticsHelper.construirNombrePantalla
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.FocoSeleccionado
import biz.belcorp.salesforce.core.domain.entities.hardware.HardwareInfoRetriever
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.plan.InfoPlanRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.unidadadministrativa.UaRegresable
import biz.belcorp.salesforce.core.domain.repository.firebase.FirebaseAnalyticsRepository
import biz.belcorp.salesforce.core.domain.repository.logs.LogRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.SessionPersonRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import io.reactivex.Single
import io.reactivex.SingleObserver
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.analitycs.TagAnalyticsHelper as LocalTagAnalyticsHelper

class FirebaseAnalyticsUseCase(
    private val sesionManager: SessionPersonRepository,
    private val planRepository: RddPlanRepository,
    private val personaRepository: RddPersonaRepository,
    private val campania: CampaniasRepository,
    private val hardwareInfoRetriever: HardwareInfoRetriever,
    private val analyticsRepository: FirebaseAnalyticsRepository,
    private val logRepository: LogRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : UseCase(threadExecutor, postExecutionThread) {

    fun devolverPantallaString(personaConsultora: PersonaRdd?, pantalla: String?): String? {

        val sesion = requireNotNull(sesionManager.get())
        val campaniaActual = requireNotNull(campania.obtenerCampaniaActual(sesion.llaveUA))
        val hardwareInfo = hardwareInfoRetriever.get()

        pantalla?.let {
            val pantallaModel = PantallaModel(
                campania = campaniaActual,
                sesion = sesion,
                estadoConexion = hardwareInfo.currentNetworkStatus,
                ambiente = hardwareInfo.buildVariant,
                codigoUsuario = obtenerCodigoUsuarioOCubPorRol(sesion),
                screenName = it,
                codgioPersona = obtenerCodigoPersona(personaConsultora)
            )

            analyticsRepository.enviarPantalla(pantallaModel)
            logRepository.guardarPantalla(pantallaModel)
        }

        return pantalla
    }


    fun enviarPantallRDD(requestRdd: RequestPantallaRdd) {
        val completable = Single.create<String> { emiter ->
            val sesion = requireNotNull(sesionManager.get())
            val rolPlan = planRepository.obtenerRolDePlan(requestRdd.planId)
            val campaniaActual = requireNotNull(campania.obtenerCampaniaActual(sesion.llaveUA))
            val pantalla = construirNombrePantalla(requestRdd.tagAnalytics, rolPlan)
            val hardwareInfo = hardwareInfoRetriever.get()

            pantalla?.let {
                val pantallaModel = PantallaModel(
                    campania = campaniaActual,
                    sesion = sesion,
                    estadoConexion = hardwareInfo.currentNetworkStatus,
                    ambiente = hardwareInfo.buildVariant,
                    codigoUsuario = obtenerCodigoUsuarioOCubPorRol(sesion),
                    screenName = it,
                    codgioPersona = ""
                )

                analyticsRepository.enviarPantalla(pantallaModel)
                logRepository.guardarPantalla(pantallaModel)
            }
            emiter.onSuccess(pantalla ?: AnalyticsConstants.NO_SE_PUDO_CREAR_ETIQUETA)
        }
        execute(completable, requestRdd.singleObserver)
    }

    fun enviarPantallPerfil(requestPerfil: RequestPantallaPerfil) {
        val completable = Single.create<String> { emiter ->
            val personaConsultora =
                personaRepository.recuperarPersonaPorId(requestPerfil.personaId, requestPerfil.rol)
            val pantalla = devolverPantallaString(
                personaConsultora,
                construirNombrePantalla(requestPerfil.tagAnalytics, requestPerfil.rol)
            )
            emiter.onSuccess(pantalla ?: AnalyticsConstants.NO_SE_PUDO_CREAR_ETIQUETA)
        }
        execute(completable, requestPerfil.singleObserver)
    }

    fun enviarPantallaMetas(requestMetas: RequestPantallaMetas) {
        val completable = Single.create<String> { emiter ->
            val personaConsultora =
                personaRepository.recuperarPersonaPorId(requestMetas.personaId, requestMetas.rol)
            val pantalla = devolverPantallaString(
                personaConsultora,
                construirNombrePantalla(requestMetas.tagAnalytics, requestMetas.rol)
            )
            emiter.onSuccess(pantalla ?: AnalyticsConstants.NO_SE_PUDO_CREAR_ETIQUETA)
        }
        execute(completable, requestMetas.singleObserver)
    }

    fun enviarPantallaMarcas(requestMarcas: RequestPantallaMarcas) {
        val completable = Single.create<String> { emiter ->

            val personaConsultora =
                personaRepository.recuperarPersonaPorId(requestMarcas.personaId, requestMarcas.rol)
            val pantalla = devolverPantallaString(
                personaConsultora,
                construirNombrePantalla(requestMarcas.tagAnalytics, requestMarcas.rol)
            )

            emiter.onSuccess(pantalla ?: AnalyticsConstants.NO_SE_PUDO_CREAR_ETIQUETA)
        }
        execute(completable, requestMarcas.singleObserver)
    }

    fun enviarPantallaVentas(requestVentas: RequestPantallaVentas) {
        val completable = Single.create<String> { emiter ->
            val personaConsultora =
                personaRepository.recuperarPersonaPorId(requestVentas.personaId, requestVentas.rol)
            val pantalla = devolverPantallaString(
                personaConsultora,
                construirNombrePantalla(requestVentas.tagAnalytics, requestVentas.rol)
            )
            emiter.onSuccess(pantalla ?: AnalyticsConstants.NO_SE_PUDO_CREAR_ETIQUETA)
        }
        execute(completable, requestVentas.singleObserver)
    }

    fun enviarPantallaDigital(requestDigital: RequestPantallaDigital) {
        val completable = Single.create<String> { emiter ->
            val personaConsultora = personaRepository.recuperarPersonaPorId(
                requestDigital.personaId,
                requestDigital.rol
            )
            val pantalla = devolverPantallaString(
                personaConsultora,
                construirNombrePantalla(requestDigital.tagAnalytics, requestDigital.rol)
            )
            emiter.onSuccess(pantalla ?: AnalyticsConstants.NO_SE_PUDO_CREAR_ETIQUETA)
        }
        execute(completable, requestDigital.singleObserver)
    }

    fun enviarPantallaAcuerdos(requestAcuerdos: RequestPantallaAcuerdos) {
        val completable = Single.create<String> { emiter ->
            val personaConsultora = personaRepository.recuperarPersonaPorId(
                requestAcuerdos.personaId,
                requestAcuerdos.rol
            )
            val pantalla = devolverPantallaString(
                personaConsultora,
                construirNombrePantalla(requestAcuerdos.tagAnalytics, requestAcuerdos.rol)
            )
            emiter.onSuccess(pantalla ?: AnalyticsConstants.NO_SE_PUDO_CREAR_ETIQUETA)
        }
        execute(completable, requestAcuerdos.singleObserver)
    }

    fun enviarPantallaVentaGanancia(request: RequestPantallaVentaGanancia) {
        val completable = Single.create<String> { emiter ->
            val personaConsultora =
                personaRepository.recuperarPersonaPorId(request.personaId, request.rol)
            val pantalla = devolverPantallaString(
                personaConsultora,
                construirNombrePantalla(request.tagAnalytics, request.rol)
            )
            emiter.onSuccess(pantalla ?: AnalyticsConstants.NO_SE_PUDO_CREAR_ETIQUETA)
        }
        execute(completable, request.singleObserver)
    }

    fun enviarPantallaAcompaniamientoDigital(request: RequestPantallaAcompaniamientoDigital) {
        val completable = Single.create<String> { emiter ->
            val personaConsultora =
                personaRepository.recuperarPersonaPorId(request.personaId, request.rol)
            val pantalla = devolverPantallaString(
                personaConsultora,
                construirNombrePantalla(request.tagAnalytics, request.rol)
            )
            emiter.onSuccess(pantalla ?: AnalyticsConstants.NO_SE_PUDO_CREAR_ETIQUETA)
        }
        execute(completable, request.singleObserver)
    }

    fun enviarPantallaCaminoBrillante(request: RequestPantallaCaminoBrillante) {
        val completable = Single.create<String> { emiter ->
            val personaConsultora =
                personaRepository.recuperarPersonaPorId(request.personaId, request.rol)
            val pantalla = devolverPantallaString(
                personaConsultora,
                construirNombrePantalla(request.tagAnalytics, request.rol)
            )
            emiter.onSuccess(pantalla ?: AnalyticsConstants.NO_SE_PUDO_CREAR_ETIQUETA)
        }
        execute(completable, request.singleObserver)
    }

    fun enviarPantallaAcompanamientoNovedades(request: RequestPantallaAcompaniamientoNovedades) {
        val completable = Single.create<String> { emiter ->
            val personaConsultora =
                personaRepository.recuperarPersonaPorId(request.personaId, request.rol)
            val pantalla = devolverPantallaString(
                personaConsultora,
                construirNombrePantalla(request.tagAnalytics, request.rol)
            )
            emiter.onSuccess(pantalla ?: AnalyticsConstants.NO_SE_PUDO_CREAR_ETIQUETA)
        }
        execute(completable, request.singleObserver)
    }

    fun enviarPantallaConcursos(request: RequestPantallaConcursos) {
        val completable = Single.create<String> { emiter ->
            val personaConsultora =
                personaRepository.recuperarPersonaPorId(request.personaId, request.rol)
            val pantalla = devolverPantallaString(
                personaConsultora,
                construirNombrePantalla(request.tagAnalytics, request.rol)
            )
            emiter.onSuccess(pantalla ?: AnalyticsConstants.NO_SE_PUDO_CREAR_ETIQUETA)
        }
        execute(completable, request.singleObserver)
    }

    fun enviarPantallaProgramaNuevas(request: RequestPantallaProgramaNuevas) {
        val completable = Single.create<String> { emiter ->
            val personaConsultora =
                personaRepository.recuperarPersonaPorId(request.personaId, request.rol)
            val pantalla = devolverPantallaString(
                personaConsultora,
                construirNombrePantalla(request.tagAnalytics, request.rol)
            )
            emiter.onSuccess(pantalla ?: AnalyticsConstants.NO_SE_PUDO_CREAR_ETIQUETA)
        }
        execute(completable, request.singleObserver)
    }

    fun enviarPantallaProgramaNuevasYTipsEstablecidas(request: RequestPantallaProgramaNuevasYTipsEstablecidas) {
        val completable = Single.create<String> { emiter ->
            val personaConsultora = personaRepository.recuperarPersonaPorId(request.personaId, request.rol)
            val pantalla = devolverPantallaString(personaConsultora, construirNombrePantalla(request.tagAnalytics, request.rol))
            emiter.onSuccess(pantalla ?: AnalyticsConstants.NO_SE_PUDO_CREAR_ETIQUETA)
        }
        execute(completable, request.singleObserver)
    }

    fun enviarPantallaMasVendido(requestMasVendido: RequestPantallaMasVendido) {
        val completable = Single.create<String> { emiter ->
            val personaConsultora = personaRepository.recuperarPersonaPorId(
                requestMasVendido.personaId,
                requestMasVendido.rol
            )
            val pantalla = devolverPantallaString(
                personaConsultora,
                construirNombrePantalla(requestMasVendido.tagAnalytics, requestMasVendido.rol)
            )
            emiter.onSuccess(pantalla ?: AnalyticsConstants.NO_SE_PUDO_CREAR_ETIQUETA)
        }
        execute(completable, requestMasVendido.singleObserver)
    }

    fun enviarPantallaVisita(request: RequestPantallaVisita) {
        val completable = Single.create<String> { emiter ->
            val sesion = requireNotNull(sesionManager.get())
            val rolPersona =
                requireNotNull(personaRepository.recuperarVisita(request.visitaId)?.persona?.rol)
            val campaniaActual = requireNotNull(campania.obtenerCampaniaActual(sesion.llaveUA))
            val pantalla = construirNombrePantalla(request.tagAnalytics, rolPersona)
            val hardwareInfo = hardwareInfoRetriever.get()

            pantalla?.let {
                val pantallaModel = PantallaModel(
                    campania = campaniaActual,
                    sesion = sesion,
                    estadoConexion = hardwareInfo.currentNetworkStatus,
                    ambiente = hardwareInfo.buildVariant,
                    codigoUsuario = obtenerCodigoUsuarioOCubPorRol(sesion),
                    screenName = it,
                    codgioPersona = ""
                )

                analyticsRepository.enviarPantalla(pantallaModel)
                logRepository.guardarPantalla(pantallaModel)
            }
            emiter.onSuccess(pantalla ?: AnalyticsConstants.NO_SE_PUDO_CREAR_ETIQUETA)
        }
        execute(completable, request.singleObserver)
    }

    fun enviarPantallaEliminarCalendario(request: RequestEliminarCalendario) {
        val completable = Single.create<String> { emiter ->
            val sesion = requireNotNull(sesionManager.get())
            val rolPersona =
                requireNotNull(personaRepository.recuperarVisita(request.visitaId)?.persona?.rol)
            val campaniaActual = requireNotNull(campania.obtenerCampaniaActual(sesion.llaveUA))
            val pantalla = construirNombrePantalla(request.tagAnalytics, rolPersona)
            val hardwareInfo = hardwareInfoRetriever.get()

            pantalla?.let {
                val pantallaModel = PantallaModel(
                    campania = campaniaActual,
                    sesion = sesion,
                    estadoConexion = hardwareInfo.currentNetworkStatus,
                    ambiente = hardwareInfo.buildVariant,
                    codigoUsuario = obtenerCodigoUsuarioOCubPorRol(sesion),
                    screenName = it,
                    codgioPersona = ""
                )

                analyticsRepository.enviarPantalla(pantallaModel)
                logRepository.guardarPantalla(pantallaModel)
            }
            emiter.onSuccess(pantalla ?: AnalyticsConstants.NO_SE_PUDO_CREAR_ETIQUETA)
        }
        execute(completable, request.singleObserver)
    }

    private fun obtenerCodigoUsuarioOCubPorRol(sesion: Sesion): String {
        return when (sesion.rol) {
            Rol.SOCIA_EMPRESARIA -> sesion.codigoConsultora
            else -> sesion.cub
        }
    }

    private fun obtenerCodigoPersona(persona: Persona?): String {
        return when (persona) {
            is ConsultoraRdd -> persona.codigo
            is SociaEmpresariaRdd -> persona.codigo
            is GerenteZonaRdd -> persona.usuario
            is GerenteRegionRdd -> persona.usuario
            else -> "La persona no tiene código"
        }
    }

    fun enviarEvento(request: RequestEvento) {
        val single = Single.create<EventoModel> { emiter ->
            val evento = TagAnalyticsHelper.construirEvento(request.tagAnalytics)
            evento.ambiente = hardwareInfoRetriever.get().buildVariant
            analyticsRepository.enviarEvento(evento)
            logRepository.guardarEvento(evento)
            emiter.onSuccess(evento)
        }

        execute(single, request.singleObserver)
    }

    fun enviarEvento2(request: RequestEvento2) {
        val single = Single.create<EventoModel> { emiter ->
            val evento =
                construirEvento2(request.tagAnalytics, request.nombreBoton, request.nombreDocumento)
            evento.ambiente = hardwareInfoRetriever.get().buildVariant
            analyticsRepository.enviarEvento(evento)
            emiter.onSuccess(evento)
        }

        execute(single, request.singleObserver)
    }

    fun enviarEventoCambioNivel(request: RequestEventoCambioNivel) {
        val single = Single.create<EventoModel> { emiter ->
            val evento = TagAnalyticsHelper.construirEventoCambioNivel(
                request.tagAnalytics, request.nivel, request.constancia
            )
            evento.ambiente = hardwareInfoRetriever.get().buildVariant
            analyticsRepository.enviarEvento(evento)
            emiter.onSuccess(evento)
        }

        execute(single, request.singleObserver)
    }

    fun enviarEventoPush(request: RequestEventoPush) {
        val single = Single.create<EventoPushModel> { emiter ->
            val sesion = requireNotNull(sesionManager.get())
            val campaniaActual = requireNotNull(campania.obtenerCampaniaActual(sesion.llaveUA))

            val eventoPush = EventoPushModel(
                campania = campaniaActual,
                sesion = sesion,
                screenName = "",
                tipoIngreso = request.tipoIngreso,
                nombrePushNotification = request.nombrePushNotification,
                ambiente = hardwareInfoRetriever.get().buildVariant
            )

            analyticsRepository.enviarEventoPush(eventoPush)
            logRepository.guardarEventoPush(eventoPush)
            emiter.onSuccess(eventoPush)
        }

        execute(single, request.singleObserver)
    }

    fun enviarEventoPorRol(request: RequestEventoPorRol) {
        val single = Single.create<EventoModel> { emiter ->
            val sesion = checkNotNull(sesionManager.get())
            val evento = construirEventoPorRol(request.tagAnalytics, sesion, request.rol)
            evento.ambiente = hardwareInfoRetriever.get().buildVariant
            analyticsRepository.enviarEvento(evento)
            logRepository.guardarEvento(evento)
            emiter.onSuccess(evento)
        }

        execute(single, request.singleObserver)
    }

    fun enviarEventoMarcador(request: RequestEventoMarcador) {
        val single = Single.create<EventoModel> { emiter ->
            val evento = when (request.role) {
                Rol.CONSULTORA, Rol.SOCIA_EMPRESARIA -> {
                    val persona =
                        personaRepository.recuperarPersonaPorId(request.personaId, request.role)
                    when (persona) {
                        is ConsultoraRdd -> TagAnalyticsHelper.construirEventoPorCodigo(persona.codigo)
                        is SociaEmpresariaRdd -> TagAnalyticsHelper.construirEventoPorCodigo(persona.codigo)
                        else -> TagAnalyticsHelper.construirEventoPorCodigo("Not Available")
                    }
                }
                else -> TagAnalyticsHelper.construirEventoPorCodigo("Not Available")
            }
            evento.ambiente = hardwareInfoRetriever.get().buildVariant
            analyticsRepository.enviarEvento(evento)
            logRepository.guardarEvento(evento)
            emiter.onSuccess(evento)
        }
        execute(single, request.singleObserver)
    }

    fun enviarEventoSalirAvance(request: RequestSalirAvance) {
        val single = Single.create<EventoModel> { emiter ->
            val evento = LocalTagAnalyticsHelper.construirEventopPorAvance(
                request.tagAnalytics,
                request.rol,
                request.tipo
            )
            evento.ambiente = hardwareInfoRetriever.get().buildVariant
            analyticsRepository.enviarEvento(evento)
            logRepository.guardarEvento(evento)
            emiter.onSuccess(evento)
        }

        execute(single, request.singleObserver)
    }

    fun enviarEventoVerRuta(request: RequestVerSalirRuta) {
        val single = Single.create<EventoModel> { emiter ->
            val evento =
                LocalTagAnalyticsHelper.construirEventoVerRuta(request.tagAnalytics, request.model)
            evento.ambiente = hardwareInfoRetriever.get().buildVariant
            analyticsRepository.enviarEvento(evento)
            logRepository.guardarEvento(evento)
            emiter.onSuccess(evento)
        }

        execute(single, request.singleObserver)
    }

    fun enviarEventoAvancePorCodigo(request: RequestAvancePorCodigo) {
        val single = Single.create<EventoModel> { emiter ->
            val evento = TagAnalyticsHelper.construirEventoAvancePorCodigo(
                request.tagAnalytics,
                request.codigo
            )
            evento.ambiente = hardwareInfoRetriever.get().buildVariant
            analyticsRepository.enviarEvento(evento)
            logRepository.guardarEvento(evento)
            emiter.onSuccess(evento)
        }

        execute(single, request.singleObserver)
    }

    fun enviarEventoAsignarCampania(request: RequestFocoCampaniaAsignarEditar) {
        val single = Single.create<EventoModel> { emiter ->
            val evento =
                TagAnalyticsHelper.construirEventoFocosCampana(request.tagAnalytics, request.tipo)
            evento.ambiente = hardwareInfoRetriever.get().buildVariant
            analyticsRepository.enviarEvento(evento)
            logRepository.guardarEvento(evento)
            emiter.onSuccess(evento)
        }

        execute(single, request.singleObserver)
    }

    fun eviarEventoFocoSeleccionado(request: RequestFocoSeleccionado) {
        val single = Single.create<EventoModel> { emiter ->
            val evento = LocalTagAnalyticsHelper.construirEventoMiEquipo(
                request.tagAnalytics,
                request.focoSeleccionado
            )
            evento.ambiente = hardwareInfoRetriever.get().buildVariant
            analyticsRepository.enviarEvento(evento)
            logRepository.guardarEvento(evento)
            emiter.onSuccess(evento)
        }
        execute(single, request.singleObserver)
    }

    fun enviarEventoTagConsultora(request: RequesEventoPerfilConsultora) {
        val single = Single.create<EventoModel> { emiter ->
            val evento =
                TagAnalyticsHelper.construirEventoConsultora(request.tagAnalytics, request.tagName)
            evento.ambiente = hardwareInfoRetriever.get().buildVariant
            analyticsRepository.enviarSeleccionBoton(evento)
            logRepository.guardarSeleccionBoton(evento)
            emiter.onSuccess(evento)
        }

        execute(single, request.singleObserver)
    }

    fun enviarEventoSeleccionOpcion(request: RequesEventoPerfilConsultora) {
        val single = Single.create<EventoModel> { emiter ->
            val evento =
                TagAnalyticsHelper.construirEventoConsultora(request.tagAnalytics, request.tagName)
            evento.ambiente = hardwareInfoRetriever.get().buildVariant
            analyticsRepository.enviarSeleccionOpcion(evento)
            logRepository.guardarSeleccionOption(evento)
            emiter.onSuccess(evento)
        }

        execute(single, request.singleObserver)
    }

    fun enviarEventoSeleccionSwitch(request: RequesEventoPerfilConsultora) {
        val single = Single.create<EventoModel> { emiter ->
            val evento =
                TagAnalyticsHelper.construirEventoConsultora(request.tagAnalytics, request.tagName)
            evento.ambiente = hardwareInfoRetriever.get().buildVariant
            analyticsRepository.enviarSeleccionSwitch(evento)
            logRepository.guardarSeleccionSwitch(evento)
            emiter.onSuccess(evento)
        }

        execute(single, request.singleObserver)
    }

    fun enviarEventoTagTipsDesarrollo(request: RequesEventoTipsDesarrollo) {
        val single = Single.create<EventoModel> { emiter ->
            val evento = TagAnalyticsHelper.construirEventoTipsDesarrollo(
                request.tagAnalytics,
                request.tagName
            )
            evento.ambiente = hardwareInfoRetriever.get().buildVariant
            analyticsRepository.enviarEvento(evento)
            logRepository.guardarEvento(evento)
            emiter.onSuccess(evento)
        }

        execute(single, request.singleObserver)
    }

    class RequestEvento(
        val tagAnalytics: TagAnalytics,
        val singleObserver: SingleObserver<EventoModel>
    )

    class RequestEvento2(
        val tagAnalytics: TagAnalytics,
        val nombreBoton: String,
        val nombreDocumento: String,
        val singleObserver: SingleObserver<EventoModel>
    )

    class RequestEventoCambioNivel(
        val tagAnalytics: TagAnalytics,
        val nivel: String,
        val constancia: String,
        val singleObserver: SingleObserver<EventoModel>
    )

    class RequestEventoPush(
        val tagAnalytics: TagAnalytics,
        val tipoIngreso: String,
        val nombrePushNotification: String,
        val singleObserver: SingleObserver<EventoPushModel>
    )

    class RequestEventoMarcador(
        val tagAnalytics: TagAnalytics,
        val personaId: Long,
        val role: Rol,
        val singleObserver: SingleObserver<EventoModel>
    )

    class RequestEventoPorRol(
        val tagAnalytics: TagAnalytics,
        val rol: Rol,
        val singleObserver: SingleObserver<EventoModel>
    )

    class RequestPantallaRdd(
        val tagAnalytics: TagAnalytics,
        val planId: Long,
        val singleObserver: SingleObserver<String>
    )

    class RequestPantallaPerfil(
        val tagAnalytics: TagAnalytics,
        val rol: Rol,
        val singleObserver: SingleObserver<String>,
        val personaId: Long
    )

    class RequestPantallaMetas(
        val tagAnalytics: TagAnalytics,
        val rol: Rol,
        val singleObserver: SingleObserver<String>,
        val personaId: Long
    )

    class RequestPantallaMarcas(
        val tagAnalytics: TagAnalytics,
        val rol: Rol,
        val singleObserver: SingleObserver<String>,
        val personaId: Long
    )

    class RequestPantallaVentas(
        val tagAnalytics: TagAnalytics,
        val rol: Rol,
        val singleObserver: SingleObserver<String>,
        val personaId: Long
    )

    class RequestPantallaMasVendido(
        val tagAnalytics: TagAnalytics,
        val rol: Rol,
        val singleObserver: SingleObserver<String>,
        val personaId: Long
    )

    class RequestPantallaAcuerdos(
        val tagAnalytics: TagAnalytics,
        val rol: Rol,
        val singleObserver: SingleObserver<String>,
        val personaId: Long
    )

    class RequestPantallaDigital(
        val tagAnalytics: TagAnalytics,
        val rol: Rol,
        val singleObserver: SingleObserver<String>,
        val personaId: Long
    )

    class RequestPantallaVentaGanancia(
        val tagAnalytics: TagAnalytics,
        val rol: Rol,
        val singleObserver: SingleObserver<String>,
        val personaId: Long
    )

    class RequestPantallaAcompaniamientoDigital(
        val tagAnalytics: TagAnalytics,
        val rol: Rol,
        val singleObserver: SingleObserver<String>,
        val personaId: Long
    )

    class RequestPantallaCaminoBrillante(
        val tagAnalytics: TagAnalytics,
        val rol: Rol,
        val singleObserver: SingleObserver<String>,
        val personaId: Long
    )

    class RequestPantallaAcompaniamientoNovedades(
        val tagAnalytics: TagAnalytics,
        val rol: Rol,
        val singleObserver: SingleObserver<String>,
        val personaId: Long
    )

    class RequestPantallaConcursos(
        val tagAnalytics: TagAnalytics,
        val rol: Rol,
        val singleObserver: SingleObserver<String>,
        val personaId: Long
    )

    class RequestPantallaProgramaNuevas(
        val tagAnalytics: TagAnalytics,
        val rol: Rol,
        val singleObserver: SingleObserver<String>,
        val personaId: Long
    )

    class RequestPantallaProgramaNuevasYTipsEstablecidas(
        val tagAnalytics: TagAnalytics,
        val rol: Rol,
        val singleObserver: SingleObserver<String>,
        val personaId: Long
    )

    class RequestPantallaVisita(
        val tagAnalytics: TagAnalytics,
        val visitaId: Long,
        val singleObserver: SingleObserver<String>
    )

    class RequestEliminarCalendario(
        val tagAnalytics: TagAnalytics,
        val visitaId: Long,
        val singleObserver: SingleObserver<String>
    )

    class RequestSalirAvance(
        val tagAnalytics: TagAnalytics,
        val rol: InfoPlanRdd,
        val tipo: Int,
        val singleObserver: SingleObserver<EventoModel>
    )

    class RequestVerSalirRuta(
        val tagAnalytics: TagAnalytics,
        val model: UaRegresable,
        val singleObserver: SingleObserver<EventoModel>
    )

    class RequestAvancePorCodigo(
        val tagAnalytics: TagAnalytics,
        val codigo: String,
        val singleObserver: SingleObserver<EventoModel>
    )

    class RequestFocoCampaniaAsignarEditar(
        val tagAnalytics: TagAnalytics,
        val tipo: Int,
        val singleObserver: SingleObserver<EventoModel>
    )

    class RequestFocoSeleccionado(
        val tagAnalytics: TagAnalytics,
        val focoSeleccionado: FocoSeleccionado,
        val singleObserver: SingleObserver<EventoModel>
    )

    class RequesEventoPerfilConsultora(
        val tagAnalytics: TagAnalytics,
        val tagName: String?,
        val singleObserver: SingleObserver<EventoModel>
    )

    class RequesEventoTipsDesarrollo(
        val tagAnalytics: TagAnalytics,
        val tagName: String?,
        val singleObserver: SingleObserver<EventoModel>
    )
}

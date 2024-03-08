package biz.belcorp.salesforce.core.domain.usecases.firebase

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.analytics.*
import biz.belcorp.salesforce.core.domain.entities.hardware.HardwareInfoRetriever
import biz.belcorp.salesforce.core.domain.entities.session.Sesion
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.repository.firebase.FirebaseAnalyticsRepository
import biz.belcorp.salesforce.core.domain.repository.logs.LogRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import biz.belcorp.salesforce.core.entities.zonificacion.Rol

class FirebaseAnalyticsUseCase(
    private val sessionRepository: SessionRepository,
    private val analyticsRepository: FirebaseAnalyticsRepository,
    private val hardwareInfoRetriever: HardwareInfoRetriever,
    private val logRepository: LogRepository,
    private val campaignRepository: CampaniasRepository
) {

    private val session by lazy { requireNotNull(sessionRepository.getSession()) }


    fun enviarPantallaCalculadora(request: RequestEvento): String {
        val pantalla = TagAnalyticsHelper.construirNombrePantallaCalculadora(request.tagAnalytics)
        pantalla?.let {
            val campaniaActual =
                requireNotNull(campaignRepository.obtenerCampaniaActual(session.llaveUA))
            val hardwareInfo = hardwareInfoRetriever.get()
            val pantallaModel = PantallaModel(
                campania = campaniaActual,
                sesion = session,
                estadoConexion = hardwareInfo.currentNetworkStatus,
                ambiente = hardwareInfo.buildVariant,
                codigoUsuario = obtenerCodigoUsuarioOCubPorRol(session),
                screenName = it,
                codgioPersona = Constant.EMPTY_STRING
            )

            analyticsRepository.enviarPantalla(pantallaModel)
            logRepository.guardarPantalla(pantallaModel)
        }
        return pantalla ?: AnalyticsConstants.NO_SE_PUDO_CREAR_ETIQUETA
    }

    fun enviarEventoCalculadora(request: RequestEvento): EventoModel {
        val evento = TagAnalyticsHelper.construirEventoCalculadora(request.tagAnalytics)
        evento.ambiente = hardwareInfoRetriever.get().buildVariant
        analyticsRepository.enviarEvento(evento)
        logRepository.guardarEvento(evento)
        return evento
    }

    suspend fun enviarPantallaInspira(request: RequestEvento): String {
        val pantalla = TagAnalyticsHelper.construirNombrePantallaInspira(request.tagAnalytics)
        pantalla.let {
            val campaniaActual =
                requireNotNull(campaignRepository.obtenerCampaniaActual(session.llaveUA))
            val hardwareInfo = hardwareInfoRetriever.get()
            val pantallaModel = PantallaModel(
                campania = campaniaActual,
                sesion = session,
                estadoConexion = hardwareInfo.currentNetworkStatus,
                ambiente = hardwareInfo.buildVariant,
                codigoUsuario = obtenerCodigoUsuarioOCubPorRol(session),
                screenName = it.toString(),
                codgioPersona = Constant.EMPTY_STRING
            )

            analyticsRepository.enviarPantalla(pantallaModel)
            logRepository.guardarPantalla(pantallaModel)
        }
        return pantalla ?: AnalyticsConstants.NO_SE_PUDO_CREAR_ETIQUETA
    }

    suspend fun enviarEventoInspira(
        request: RequestEvento,
        label: String,
        screenName: String
    ): EventoModel {
        val evento =
            TagAnalyticsHelper.construirEventoInspira(request.tagAnalytics, label, screenName)
        evento.ambiente = hardwareInfoRetriever.get().buildVariant
        analyticsRepository.enviarEvento(evento)
        logRepository.guardarEvento(evento)
        return evento
    }

    class RequestEvento(val tagAnalytics: TagAnalytics)

    private fun obtenerCodigoUsuarioOCubPorRol(sesion: Sesion): String {
        return when (sesion.rol) {
            Rol.SOCIA_EMPRESARIA -> sesion.codigoConsultora
            else -> sesion.cub
        }
    }
}

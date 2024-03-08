package biz.belcorp.salesforce.core.data.repository.logs

import biz.belcorp.salesforce.core.data.repository.logs.entities.Evento
import biz.belcorp.salesforce.core.data.repository.logs.entities.EventoPush
import biz.belcorp.salesforce.core.data.repository.logs.entities.Pantalla
import biz.belcorp.salesforce.core.domain.entities.analytics.EventoModel
import biz.belcorp.salesforce.core.domain.entities.analytics.EventoPushModel
import biz.belcorp.salesforce.core.domain.entities.analytics.LogTag
import biz.belcorp.salesforce.core.domain.entities.analytics.PantallaModel
import biz.belcorp.salesforce.core.domain.entities.hardware.BuildVariant
import biz.belcorp.salesforce.core.domain.entities.hardware.NetworkStatus
import biz.belcorp.salesforce.core.domain.repository.logs.LogRepository
import com.google.gson.Gson

class LogLocalDataRepository(private val escribirArchivoLocalDataStore: EscribirArchivoLocalDataStore) :
    LogRepository {

    companion object {

        const val TYPE_OPEN_SCREEN = "openScreen"
        const val TYPE_EVENT = "virtualEvent"
        const val TYPE_SELECTION_BUTTON = "selectionButton"
        const val TYPE_SELECTION_OPTION = "selectionOption"
        const val TYPE_SELECTION_SWITCH = "selectionSwitch"

    }

    val gson = Gson()

    override fun guardarEvento(eventoModel: EventoModel): EventoModel {
        val evento = obtenerEventoAGuardar(eventoModel)
        escribirArchivoLocalDataStore.guardarLog(TYPE_EVENT, gson.toJson(evento))
        return eventoModel
    }

    override fun guardarSeleccionBoton(eventoModel: EventoModel): EventoModel {
        val evento = obtenerEventoAGuardar(eventoModel)
        escribirArchivoLocalDataStore.guardarLog(TYPE_SELECTION_BUTTON, gson.toJson(evento))
        return eventoModel
    }

    override fun guardarSeleccionOption(eventoModel: EventoModel): EventoModel {
        val evento = obtenerEventoAGuardar(eventoModel)
        escribirArchivoLocalDataStore.guardarLog(TYPE_SELECTION_OPTION, gson.toJson(evento))
        return eventoModel
    }

    override fun guardarSeleccionSwitch(eventoModel: EventoModel): EventoModel {
        val evento = obtenerEventoAGuardar(eventoModel)
        escribirArchivoLocalDataStore.guardarLog(TYPE_SELECTION_SWITCH, gson.toJson(evento))
        return eventoModel
    }

    override fun guardarPantalla(pantallaModel: PantallaModel) {
        val pantalla = obtenerPantallaAGuardar(pantallaModel)
        escribirArchivoLocalDataStore.guardarLog(TYPE_OPEN_SCREEN, gson.toJson(pantalla))
    }

    override fun guardarEventoPush(eventoPushModel: EventoPushModel): EventoPushModel {
        val eventoPush = obtenerEventoPushAGuardar(eventoPushModel)
        escribirArchivoLocalDataStore.guardarLog(TYPE_EVENT, gson.toJson(eventoPush))
        return eventoPushModel
    }

    private fun mapVariant(buildVariant: BuildVariant): String {
        return when (buildVariant) {
            BuildVariant.RELEASE -> "Produccion"
            else -> "QAs"
        }
    }

    private fun mapNetworkStatus(networkStatus: NetworkStatus): String {
        return when (networkStatus) {
            NetworkStatus.CONNECTED -> "online"
            NetworkStatus.DISCONNECTED -> "offline"
        }
    }

    private fun obtenerEventoAGuardar(eventoModel: EventoModel): Evento {
        return Evento(
                categoria = eventoModel.category,
                action = eventoModel.action,
                label = eventoModel.label,
                screenName = eventoModel.screenName,
                ambiente = mapVariant(eventoModel.ambiente))
    }

    private fun obtenerPantallaAGuardar(pantallaModel: PantallaModel): Pantalla {
        return Pantalla(
                campania = pantallaModel.campania.codigo,
                paisPrincipal = pantallaModel.sesion.countryIso,
                regionPrincipal = pantallaModel.sesion.region ?: LogTag.NO_APLICA,
                zonaPrincipal = pantallaModel.sesion.zona ?: LogTag.NO_APLICA,
                periodo = pantallaModel.campania.periodo?.inicial(),
                cub = pantallaModel.sesion.cub,
                rol = pantallaModel.sesion.codigoRol,
                paisActual = pantallaModel.sesion.countryIso,
                regionActual = pantallaModel.sesion.region ?: LogTag.NO_APLICA,
                zonaActual = pantallaModel.sesion.zona ?: LogTag.NO_APLICA,
                secccionActual = pantallaModel.sesion.seccion ?: LogTag.NO_APLICA,
                estadoConexion = mapNetworkStatus(pantallaModel.estadoConexion),
                codigoConsultora = pantallaModel.codgioPersona,
                ambiente = mapVariant(pantallaModel.ambiente),
                screenName = pantallaModel.screenName)
    }

    private fun obtenerEventoPushAGuardar(eventoPushModel: EventoPushModel): EventoPush {
        return EventoPush(campania = eventoPushModel.campania.codigo,
                pais = eventoPushModel.sesion.countryIso,
                region = eventoPushModel.sesion.region ?: LogTag.NO_APLICA,
                zona = eventoPushModel.sesion.zona ?: LogTag.NO_APLICA,
                rol = eventoPushModel.sesion.codigoRol,
                tipoIngreso = eventoPushModel.tipoIngreso,
                nombrePushNotification = eventoPushModel.nombrePushNotification,
                screenName = eventoPushModel.screenName,
                ambiente = mapVariant(eventoPushModel.ambiente))
    }

}

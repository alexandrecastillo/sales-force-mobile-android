package biz.belcorp.salesforce.core.data.repository.analytics.cloud

import android.content.Context
import android.os.Bundle
import biz.belcorp.salesforce.core.domain.entities.analytics.EventoModel
import biz.belcorp.salesforce.core.domain.entities.analytics.EventoPushModel
import biz.belcorp.salesforce.core.domain.entities.analytics.LogTag
import biz.belcorp.salesforce.core.domain.entities.analytics.PantallaModel
import biz.belcorp.salesforce.core.domain.entities.hardware.BuildVariant
import biz.belcorp.salesforce.core.domain.entities.hardware.NetworkStatus
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseAnalyticsCloudDataStore(val context: Context) {

    private val firebaseClient: FirebaseAnalytics by lazy { FirebaseAnalytics.getInstance(context) }

    fun enviarPantalla(pantallaModel: PantallaModel) {
        val params = Bundle()
        params.insertarDatosPantalla(pantallaModel)
        firebaseClient.aplicarUserProperties(pantallaModel)
        firebaseClient.logEvent(LogTag.LOG_SCREEN_VIEW, params)
    }

    private fun Bundle.insertarDatosPantalla(pantallaModel: PantallaModel) {
        this.apply {
            putString(LogTag.SCREEN_NAME, pantallaModel.screenName)
            putString(LogTag.AMBIENTE, mapVariant(pantallaModel.ambiente))
        }
    }

    fun enviarEvento(eventoModel: EventoModel): EventoModel {
        val params = Bundle()
        params.insertarDatosEvento(eventoModel)
        firebaseClient.logEvent(LogTag.LOG_VIRTUAL_EVENT, params)
        return eventoModel
    }

    fun enviarSeleccionBoton(eventoModel: EventoModel): EventoModel {
        val params = Bundle()
        params.insertarDatosEvento(eventoModel)
        firebaseClient.logEvent(LogTag.LOG_SELECTION_BUTTON, params)
        return eventoModel
    }

    fun enviarSeleccionOpcion(eventoModel: EventoModel): EventoModel {
        val params = Bundle()
        params.insertarDatosEvento(eventoModel)
        firebaseClient.logEvent(LogTag.LOG_SELECTION_OPTION, params)
        return eventoModel
    }

    fun enviarSeleccionSwitch(eventoModel: EventoModel): EventoModel {
        val params = Bundle()
        params.insertarDatosEvento(eventoModel)
        firebaseClient.logEvent(LogTag.LOG_SELECTION_SWITCH, params)
        return eventoModel
    }

    fun enviarEventoPush(eventoPushModel: EventoPushModel): EventoPushModel {
        val params = Bundle()
        params.insertarDatosPush(eventoPushModel)
        firebaseClient.logEvent(LogTag.LOG_VIRTUAL_EVENT, params)
        return eventoPushModel
    }

    private fun Bundle.insertarDatosPush(eventoPushModel: EventoPushModel) {
        this.apply {
            putString(LogTag.CAMPANIA, eventoPushModel.campania.codigo)
            putString(LogTag.PAIS, eventoPushModel.sesion.countryIso)
            putString(LogTag.REGION, eventoPushModel.sesion.region ?: LogTag.NO_APLICA)
            putString(LogTag.ZONA, eventoPushModel.sesion.zona ?: LogTag.NO_APLICA)
            putString(LogTag.ROL, eventoPushModel.sesion.codigoRol)
            putString(LogTag.TIPO_INGRESO, eventoPushModel.tipoIngreso)
            putString(LogTag.NOMBRE_PUSH_NOTIFICACION, eventoPushModel.nombrePushNotification)
            putString(LogTag.SCREEN_NAME, eventoPushModel.screenName)
            putString(LogTag.AMBIENTE, mapVariant(eventoPushModel.ambiente))
        }
    }

    private fun Bundle.insertarDatosEvento(eventoModel: EventoModel) {
        this.apply {
            putString(LogTag.CATEGORIA, eventoModel.category)
            putString(LogTag.ACTION, eventoModel.action)
            putString(LogTag.LABEL, eventoModel.label)
            putString(LogTag.SCREEN_NAME, eventoModel.screenName)
            putString(LogTag.AMBIENTE, mapVariant(eventoModel.ambiente))
        }
    }

    private fun FirebaseAnalytics.aplicarUserProperties(pantallaModel: PantallaModel) {
        setUserProperty(LogTag.CAMPANIA, pantallaModel.campania.codigo)
        setUserProperty(LogTag.PAIS_PRINCIPAL, pantallaModel.sesion.countryIso)
        setUserProperty(LogTag.REGION_PRINCIPAL, pantallaModel.sesion.region ?: LogTag.NO_APLICA)
        setUserProperty(LogTag.ZONA_PRINCIPAL, pantallaModel.sesion.zona ?: LogTag.NO_APLICA)
        setUserProperty(LogTag.CUB, pantallaModel.sesion.cub)
        setUserProperty(LogTag.ROL, pantallaModel.sesion.codigoRol)
        setUserProperty(LogTag.PAIS_ACTUAL, pantallaModel.sesion.countryIso)
        setUserProperty(LogTag.REGION_ACTUAL, pantallaModel.sesion.region ?: LogTag.NO_APLICA)
        setUserProperty(LogTag.ZONA_ACTUAL, pantallaModel.sesion.zona ?: LogTag.NO_APLICA)
        setUserProperty(LogTag.SECCION_ACTUAL, pantallaModel.sesion.seccion ?: LogTag.NO_APLICA)
        setUserProperty(LogTag.AMBIENTE, mapVariant(pantallaModel.ambiente))
        setUserProperty(LogTag.ESTADO_CONEXION, mapNetworkStatus(pantallaModel.estadoConexion))
        setUserProperty(LogTag.CODIGO_CONSULTORA, pantallaModel.codigoUsuario)
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
}

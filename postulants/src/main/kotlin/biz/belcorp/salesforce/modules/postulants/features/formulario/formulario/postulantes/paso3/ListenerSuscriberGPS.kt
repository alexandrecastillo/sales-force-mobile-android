package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso3

/**
 * Created by osequeiros on 3/24/18.
 * Listener para suscribir los listener para la petición de GPS
 */
interface ListenerSuscriberGPS {

    fun suscribirListener(listener: ListenerSolicitudGPS)

    fun desuscribirListener()
}

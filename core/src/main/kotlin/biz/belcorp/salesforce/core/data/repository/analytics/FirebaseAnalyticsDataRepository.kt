package biz.belcorp.salesforce.core.data.repository.analytics

import biz.belcorp.salesforce.core.data.repository.analytics.cloud.FirebaseAnalyticsCloudDataStore
import biz.belcorp.salesforce.core.domain.entities.analytics.EventoModel
import biz.belcorp.salesforce.core.domain.entities.analytics.EventoPushModel
import biz.belcorp.salesforce.core.domain.entities.analytics.PantallaModel
import biz.belcorp.salesforce.core.domain.repository.firebase.FirebaseAnalyticsRepository

class FirebaseAnalyticsDataRepository(
    private val analyticsCloudDataStore: FirebaseAnalyticsCloudDataStore
) : FirebaseAnalyticsRepository {

    override fun enviarEvento(eventoModel: EventoModel): EventoModel {
        return analyticsCloudDataStore.enviarEvento(eventoModel)
    }

    override fun enviarSeleccionBoton(eventoModel: EventoModel): EventoModel {
        return analyticsCloudDataStore.enviarSeleccionBoton(eventoModel)
    }

    override fun enviarSeleccionOpcion(eventoModel: EventoModel): EventoModel {
        return analyticsCloudDataStore.enviarSeleccionOpcion(eventoModel)
    }

    override fun enviarSeleccionSwitch(eventoModel: EventoModel): EventoModel {
        return analyticsCloudDataStore.enviarSeleccionSwitch(eventoModel)
    }

    override fun enviarPantalla(pantallaModel: PantallaModel) {
        analyticsCloudDataStore.enviarPantalla(pantallaModel)
    }

    override fun enviarEventoPush(eventoPushModel: EventoPushModel): EventoPushModel {
        return analyticsCloudDataStore.enviarEventoPush(eventoPushModel)
    }
}

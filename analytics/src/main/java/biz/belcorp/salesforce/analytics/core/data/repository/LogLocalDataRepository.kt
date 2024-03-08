package biz.belcorp.salesforce.analytics.core.data.repository

import biz.belcorp.salesforce.analytics.core.domain.entities.Event
import biz.belcorp.salesforce.analytics.core.domain.entities.Screen
import biz.belcorp.salesforce.analytics.core.domain.repository.LogLocalRepository
import biz.belcorp.salesforce.core.data.repository.logs.EscribirArchivoLocalDataStore
import biz.belcorp.salesforce.core.utils.isNull
import com.google.gson.Gson

class LogLocalDataRepository(private val escribirArchivoLocalDataStore: EscribirArchivoLocalDataStore) :
    LogLocalRepository {

    companion object {

        const val TYPE_EVENT = "Eventos"
        const val TYPE_SCREEN = "Pantalla"
        const val TYPE_SCREEN_TRACK = "Track Pantalla"

    }

    val gson = Gson()

    override fun write(screen: Screen) =
        with(screen) {
            val log = "{ name = $name, variant = $variant }"
            val type = TYPE_SCREEN.takeIf { activity.isNull() } ?: TYPE_SCREEN_TRACK
            escribirArchivoLocalDataStore.guardarLog(type, log)
        }

    override fun write(event: Event) =
        escribirArchivoLocalDataStore.guardarLog(TYPE_EVENT, gson.toJson(event))

}

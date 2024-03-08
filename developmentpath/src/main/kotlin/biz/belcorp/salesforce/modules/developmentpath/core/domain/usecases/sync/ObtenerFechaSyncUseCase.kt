package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.sync

import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sync.SyncRepository
import java.util.*

class ObtenerFechaSyncUseCase(
    private val syncRepository: SyncRepository
) {

    companion object {
        const val TODAY_LABEL = "hoy a las"
        const val YESTERDAY_LABEL = "ayer a las"
    }

    fun obtenerFechaSync(): String {
        val fechaSync = syncRepository.obtenerFechaSync()
        val calendar = Date(fechaSync).toCalendar()
        return when {
            calendar.isToday() -> {
                "$TODAY_LABEL ${calendar.time.string(formatTime) ?: "-"}"
            }
            calendar.isYesterday() -> {
                "$YESTERDAY_LABEL ${calendar.time.string(formatTime) ?: "-"}"
            }
            else -> {
                calendar.time.string(formatLong) ?: "-"
            }
        }
    }
}

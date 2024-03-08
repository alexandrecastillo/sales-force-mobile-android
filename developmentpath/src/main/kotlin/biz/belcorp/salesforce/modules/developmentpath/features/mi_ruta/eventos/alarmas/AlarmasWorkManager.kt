package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.alarmas

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import biz.belcorp.salesforce.core.utils.parseToHoursDescription
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.Alarma
import java.util.*
import java.util.concurrent.TimeUnit

class AlarmasWorkManager(private val context: Context) :
    AlarmasWorker {

    @SuppressLint("RestrictedApi")
    override fun programar(alarma: Alarma.AlarmaEvento): String {
        val notificarWork = OneTimeWorkRequest.Builder(NotificarWorker::class.java)
            .setInputData(construirInputData(alarma))
            .setInitialDelay(calcularDelay(alarma), TimeUnit.MILLISECONDS)
            .addTag(TAG)
            .build()

        WorkManager.getInstance(context).enqueue(notificarWork)

        return notificarWork.stringId
    }

    private fun construirInputData(alarma: Alarma.AlarmaEvento): Data {
        val dataBuilder = Data.Builder().apply {
            putLong(
                "ID_EVENTO_ALARMA_RDD",
                alarma.evento.id
            )
            putString(
                "NOMBRE_PERSONA_ALARMA_RDD",
                alarma.persona.primerNombre
            )
            putString(
                "FECHA_EVENTO_ALARMA_RDD",
                alarma.evento.fechaInicio.time.parseToHoursDescription()
            )
            putString(
                "DESCRIPCION_EVENTO_ALARMA_RDD",
                alarma.evento.descripcion
            )
        }

        return dataBuilder.build()
    }

    private fun calcularDelay(alarma: Alarma): Long {
        return alarma.fecha.time - Date().time
    }

    override fun eliminar(id: String) {
        WorkManager.getInstance(context).cancelUniqueWork(id)
    }

    override fun eliminarTodas() {
        WorkManager.getInstance(context).cancelAllWorkByTag(TAG)
    }

    companion object {
        const val TAG = "EVENTO_RDD"
    }
}

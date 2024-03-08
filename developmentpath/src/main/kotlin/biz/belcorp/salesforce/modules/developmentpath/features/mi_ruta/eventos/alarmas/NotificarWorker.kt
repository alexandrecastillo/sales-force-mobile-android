package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.alarmas

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import biz.belcorp.salesforce.core.utils.WordUtils
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.detalle.view.DetalleEventoActivity
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.sincronizacion.NotificacionEventoHelper
import biz.belcorp.salesforce.modules.developmentpath.utils.setChannel
import java.io.Serializable
import java.util.*

class NotificarWorker(
    private val context: Context,
    params: WorkerParameters
) :
    Worker(context, params) {


    private val notificationManager: NotificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun doWork(): Result {
        crearNotificacion(obtenerModeloDeInputData())

        return Result.success()
    }

    private fun obtenerModeloDeInputData(): NotificacionModel {
        return NotificacionModel(
            eventoId = inputData.getLong("ID_EVENTO_ALARMA_RDD", -1),
            nombrePersona = inputData.getString("NOMBRE_PERSONA_ALARMA_RDD") ?: "",
            fechaEvento = inputData.getString("FECHA_EVENTO_ALARMA_RDD") ?: "",
            descripcionEvento = inputData.getString("DESCRIPCION_EVENTO_ALARMA_RDD") ?: ""
        )
    }

    private fun crearNotificacion(modelo: NotificacionModel) {

        val intent = Intent(context, DetalleEventoActivity::class.java)

        val bundle = Bundle()

        bundle.putLong(DetalleEventoActivity.ID_PARAM, modelo.eventoId)
        intent.putExtras(bundle)

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val notification = NotificationCompat
            .Builder(context, NotificacionEventoHelper.CHANNEL_ID)
            .setChannel(
                NotificacionEventoHelper.CHANNEL_ID,
                NotificacionEventoHelper.CHANNEL_NAME,
                notificationManager
            )
            .setSmallIcon(R.drawable.icon_belcorp)
            .setContentTitle(context.getString(R.string.rdd_notificacion_alarma_titulo))
            .setContentText(construirContenidoNotificacion(modelo))
            .setColor(ContextCompat.getColor(context, R.color.purple))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(Notification.DEFAULT_SOUND)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context)
            .notify(
                Date().time.toInt(),
                notification
            )
    }

    private fun construirContenidoNotificacion(modelo: NotificacionModel): String {
        return context.getString(
            R.string.rdd_notificacion_alarma_contenido,
            WordUtils.capitalizeFully(modelo.nombrePersona),
            modelo.descripcionEvento,
            modelo.fechaEvento
        )
    }

    class NotificacionModel(
        val eventoId: Long,
        val nombrePersona: String,
        val descripcionEvento: String,
        val fechaEvento: String
    ) : Serializable
}

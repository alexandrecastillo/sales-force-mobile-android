package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.sincronizacion

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import biz.belcorp.salesforce.core.utils.toDescriptionDay
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.detalle.view.DetalleEventoActivity
import biz.belcorp.salesforce.modules.developmentpath.utils.setChannel

class NotificacionEventoHelper(private val context: Context) : NotificacionEventosHandler {

    companion object {

        const val CHANNEL_ID = "10002"
        const val CHANNEL_NAME = "Alerts"
    }

    private val notificationManager: NotificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun notificarEventoCreado(modelo: NotificacionesEventosWorker.ModeloNotificacion) {
        crearNotificacion(
            modelo.idEvento, "\uD83D\uDD14 ${modelo.tituloEvento}",
            construirStringCreacion(modelo)
        )
    }

    private fun construirStringCreacion(modelo: NotificacionesEventosWorker.ModeloNotificacion): String {
        return "${modelo.fechaInicio.toDescriptionDay()} " +
            "${obtenerTextoPreFecha()} ${obtenerHoras(modelo)}"
    }

    private fun obtenerTextoPreFecha(): String {
        return context.getString(R.string.rdd_notificacion_cambio_crear_prefecha)
    }

    private fun obtenerHoras(modelo: NotificacionesEventosWorker.ModeloNotificacion): String {
        return if (modelo.todoElDia) context.getString(R.string.rdd_detalleevento_todoeldia)
        else modelo.horas
    }

    override fun notificarEventoActualizado(modelo: NotificacionesEventosWorker.ModeloNotificacion) {
        crearNotificacion(
            modelo.idEvento,
            context.getString(R.string.rdd_notificacion_cambio_editar_titulo),
            context.getString(R.string.rdd_notificacion_cambio_editar_descripcion)
        )
    }

    override fun notificarEventoEliminado(modelo: NotificacionesEventosWorker.ModeloNotificacion) {
        crearNotificacion(
            modelo.idEvento,
            context.getString(R.string.rdd_notificacion_cambio_eliminar_titulo),
            context.getString(R.string.rdd_notificacion_cambio_eliminar_descripcion)
        )
    }

    private fun crearNotificacion(
        eventoId: Long,
        titulo: String,
        detalle: String
    ) {

        val intent = Intent(context, DetalleEventoActivity::class.java)

        val bundle = Bundle()

        bundle.putLong(DetalleEventoActivity.ID_PARAM, eventoId)
        intent.putExtras(bundle)

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val notification = NotificationCompat
            .Builder(context, CHANNEL_ID)
            .setChannel(
                CHANNEL_ID,
                CHANNEL_NAME, notificationManager
            )
            .setSmallIcon(R.drawable.icon_belcorp)
            .setContentTitle(titulo)
            .setContentText(detalle)
            .setColor(ContextCompat.getColor(context, R.color.purple))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(Notification.DEFAULT_SOUND)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context)
            .notify(
                eventoId.toInt(),
                notification
            )
    }
}

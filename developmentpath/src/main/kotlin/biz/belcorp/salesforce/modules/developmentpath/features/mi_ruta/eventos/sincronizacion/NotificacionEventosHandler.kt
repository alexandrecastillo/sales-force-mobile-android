package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.sincronizacion

interface NotificacionEventosHandler {

    fun notificarEventoCreado(modelo: NotificacionesEventosWorker.ModeloNotificacion)
    fun notificarEventoActualizado(modelo: NotificacionesEventosWorker.ModeloNotificacion)
    fun notificarEventoEliminado(modelo: NotificacionesEventosWorker.ModeloNotificacion)
}

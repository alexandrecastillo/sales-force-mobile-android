package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.adicionar

interface AdicionarVisitaView {
    fun mostrarExito(fecha: String, hora: String)
    fun mostrarError(mensaje: String)
    fun notificarCambioPlan()
}

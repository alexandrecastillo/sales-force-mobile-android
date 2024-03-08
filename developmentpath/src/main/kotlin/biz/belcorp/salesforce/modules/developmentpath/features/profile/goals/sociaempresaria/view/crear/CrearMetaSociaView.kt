package biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.view.crear

import biz.belcorp.salesforce.modules.developmentpath.features.profile.goals.sociaempresaria.model.MetaSociaModelo

interface CrearMetaSociaView {
    fun habilitarBotonGuardar()
    fun deshabilitarBotonGuardar()
    fun limpiarDescripcion()
    fun cerrarVentana()
    fun mostrarMensajeErrorAlCrear()
    fun mostrarMensajeErrorAlEditar()
    fun notifcarExito()
    fun asignarDescripcion(descripcion: String)
    fun cargarDatosMeta(modelo: MetaSociaModelo)
}

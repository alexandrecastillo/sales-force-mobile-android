package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.campaniaactual

import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.AcuerdoModel

interface AcuerdosView {
    fun pintarAcuerdos(acuerdos: List<AcuerdoModel>)
    fun mostrarMensaje(mensaje: String)
    fun mostrarAcuerdos()
    fun ocultarAcuerdos()
    fun mostrarMensajeVacio()
    fun ocultarMensajeVacio()
}

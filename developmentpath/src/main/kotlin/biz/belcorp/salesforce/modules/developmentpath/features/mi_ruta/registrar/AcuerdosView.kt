package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.registrar

interface AcuerdosView {
    fun mostrarSwitch()
    fun ocultarSwitch()
    fun pintarNombreEnSwitch(nombre: String)
    fun habilitarSwitch()
    fun deshabilitarSwitch()
    fun mostrarNuevoAcuerdo(acuerdoModel: AcuerdoModel)
    fun quitarAcuerdoEliminado(posicion: Int)
    fun ocultarContenedorNuevoAcuerdo()
    fun limpiarCampoError()
    fun mostrarError(mensaje: String)
    fun ocultarTeclado()
    fun habilitarCreacionAcuerdos()
    fun deshabilitarCreacionAcuerdos()
}

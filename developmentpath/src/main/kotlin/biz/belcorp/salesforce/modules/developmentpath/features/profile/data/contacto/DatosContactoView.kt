package biz.belcorp.salesforce.modules.developmentpath.features.profile.data.contacto

interface DatosContactoView {

    fun pintar(datos: DatosContactoModel)
    fun ocultarDatosEnPosibleConsultora()
    fun ocultarDatosEnGerenteZona()
    fun expandirPorPosibleConsultora()
    fun mostrarContactarConCelular()
    fun mostrarContactarConTelefonoCasa()
    fun mostrarContactarConOtroTelefono()
    fun mostrarContactarConCorreoElectronico()

}

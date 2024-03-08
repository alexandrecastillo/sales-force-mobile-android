package biz.belcorp.salesforce.modules.postulants.features.entities

class UneteDocumentoModel {

    var nombre: String? = null
    var descripcion: String? = null
    var documento: String? = null
    var required: Boolean = false
    var imagenRuta: String? = null
    var requiredMessage: String? = null

    fun isValid(): Boolean {
        if (required)
            return !documento.isNullOrEmpty()
        return true
    }

}

package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso5

import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.UneteDocumentoModel

interface UnetePaso5View {

    fun showLoading()
    fun hideLoading()
    fun showError(message: String) = Unit
    fun showSolicitudRechazada(message: String)
    fun openDocumentDialog(): Boolean
    fun openZoom(imagenRuta: String)
    fun galeria()
    fun pdf()
    fun updated(postulanteModel: PostulanteModel)
    fun getModel(): PostulanteModel
    fun setModel(p: PostulanteModel)
    fun setZonaRiesgo(esZonaRiesgo: Boolean)
    fun setZonaCritica(esCritica: Boolean)
    fun esZonaRiesgo(): Boolean
    fun loadDocumentos(documentos: List<UneteDocumentoModel>)
    fun setTextoEmailSMS(txtEmailSMS: Int)
}

package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso3


import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.GeoZonificacion
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.ValidacionBuroResponse
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import com.google.android.gms.maps.model.LatLng

interface UnetePaso3View {

    fun showLoading()
    fun hideLoading()
    fun showError(message: String)
    fun showSolicitudRechazada(message: String)
    fun updated(postulanteModel: PostulanteModel)
    fun getModel(): PostulanteModel
    fun showZonificacion(zonificacion: GeoZonificacion)
    fun showZonificacion(error: Exception)
    fun validacionExitosa(respuesta: ValidacionBuroResponse)
    fun validarZonaPagoDeContado(respuesta: ValidacionBuroResponse)
    fun notInSection(postulanteModel: PostulanteModel)
    fun checkLocationPermission(cb: () -> Unit)
    fun enableContinueButton(status : Boolean)
    fun showMapRadio(radio: Int)
    fun validateMapRadio()
    fun getCurrentRadius(posicionCentral: LatLng, posicionFinal: LatLng) : Double
}

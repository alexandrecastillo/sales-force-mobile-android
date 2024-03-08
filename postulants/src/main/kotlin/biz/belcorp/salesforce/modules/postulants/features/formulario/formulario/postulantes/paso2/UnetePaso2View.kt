package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso2

import biz.belcorp.salesforce.modules.postulants.core.data.network.dto.unete.SolicitudPostulanteEstadosResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.BuroResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.Localidad
import biz.belcorp.salesforce.modules.postulants.features.entities.*
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso2.entities.PlaceModel


interface UnetePaso2View {

    fun showLoading()
    fun hideLoading()
    fun showError(message: String)
    fun showSolicitudRechazada(message: String)
    fun getConsultoraRecomienda(codigo: String)
    fun showConsultoraRecomienda(consultora: String)
    fun updated(postulanteModel: PostulanteModel) = Unit
    fun updated(prePostulanteModel: PrePostulanteModel) = Unit

    fun getModel(): PostulanteModel?

    fun getPreModel(): PrePostulanteModel?

    fun showListaPostulante() = Unit
    fun showLugarNivel1(lst: List<ParametroUneteModel>) = Unit
    fun showLugarNivel2(lst: List<ParametroUneteModel>) = Unit
    fun showLugarNivel3(lst: List<ParametroUneteModel>) = Unit
    fun showLugarNivel4(lst: List<ParametroUneteModel>) = Unit
    fun showLugarNivel5(lst: List<ParametroUneteModel>) = Unit
    fun showTipoDireccion(lst: List<ParametroUneteModel>) = Unit
    fun getLugarNivel2(id: Int) = Unit
    fun getLugarNivel3(id: Int) = Unit
    fun getLugarNivel4(id: Int) = Unit
    fun getLugarNivel5(id: Int) = Unit
    fun getConfiguracion(): UneteConfiguracionModel?
    fun notInSection()
    fun showDuplicateCelular()
    fun decidirActualizarOCrear(b: BuroResponse? = null) = Unit
    fun showValidacionSMSFields() = Unit
    fun showNextStep()
    fun created(map: PostulanteModel) = Unit
    fun showValidacionCrediticia() = Unit
    fun setRangosZonas(lst: List<ParametroUneteModel>) = Unit
    fun validarZonaPagoDeContado(bloqueos: BuroResponse?) = Unit
    fun showErrorConexion()
    fun showValidacionExitosa()
    fun showSMSExitosoDialog() = Unit
    fun showValidacionPendiente()
    fun saveRepuestaBloqueos(respuesta: String) = Unit
    fun onRegistroCancelado()
    fun onPostulanteRechazada() = Unit
    fun expandirConsultoraRecomendante(codigoConsultoraRecomendante: String?): Boolean
    fun obtenerCodigoODocumentoConsultoraRecomendante(codigoConsultoraRecomendante: String?): String?
    fun errorAlObtenerNombreConsultoraRecomendante()
    fun mostrarPlaces(lugares: List<PlaceModel>) = Unit
    fun buscarPlaces(codigoPais: String, cadenaBusqueda: String, localidad: Localidad? = null) = Unit
    fun validarPin(validarPin: ValidarPinModel) = Unit
    fun showValidacionPINCompleta(value: Int) = Unit
    fun showValidacionErrada() = Unit
    fun showErrorEnLaValidacion() = Unit
    fun deshabilitarValidarPIN() = Unit
    fun updateMobileStatus(status: Int) = Unit
    fun actualizarPrimerEnvio(status: Boolean) = Unit
    fun validarSiCelularDuplicado()
    fun downloadTerms() = Unit
    fun autocompletadoDireccionNuevaExperiencia() = Unit
    fun updateCoordinates(lat: String, lng: String) = Unit
    fun updateCountdownSMS(time: String) = Unit
    fun onFinishCountdownSMS() = Unit
    fun disableReenviarSMS() = Unit
    fun onNotVerifiedPhoneAfterCountdown() = Unit
    fun reenviarSMS() = Unit
    fun decidirEnvioONoEnvioSMS() = Unit
    fun enviarCodigo() = Unit
    fun onGetSolicitudPostulante(t: SolicitudPostulanteEstadosResponse) = Unit

}

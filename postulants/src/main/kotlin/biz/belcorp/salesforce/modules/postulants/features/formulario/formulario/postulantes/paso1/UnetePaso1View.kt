package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso1

import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.BuroResponse
import biz.belcorp.salesforce.modules.postulants.features.entities.*
import biz.belcorp.salesforce.modules.postulants.utils.Constant

interface UnetePaso1View {

    fun showLoading()

    fun hideLoading()

    fun showError(message: String)

    fun showSolicitudRechazada(message: String)

    fun showNacionalidad(model: List<TablaLogicaModel>)

    fun showTipoDocumento(model: List<ParametroUneteModel>)

    fun showRegimenContable(model: List<ParametroUneteModel>)

    fun showGeneros(model: List<SexoModel>)

    fun created(postulanteModel: PostulanteModel) = Unit

    fun getModel(): PostulanteModel {
        TODO(Constant.NOT_IMPLEMENTED)
    }

    fun setModel(p: PostulanteModel) {
        TODO(Constant.NOT_IMPLEMENTED)
    }

    fun created(prePostulanteModel: PrePostulanteModel) {
        TODO(Constant.NOT_IMPLEMENTED)
    }

    fun getPreModel(): PrePostulanteModel {
        TODO(Constant.NOT_IMPLEMENTED)
    }

    fun setModel(p: PrePostulanteModel) = Unit

    fun createModel(buro: BuroResponse): PostulanteModel {
        TODO(Constant.NOT_IMPLEMENTED)
    }

    fun createPreModel(buro: BuroResponse): PrePostulanteModel {
        TODO(Constant.NOT_IMPLEMENTED)
    }

    fun showModal(title: String, msg: String)

    fun validateBloqueos(pais: String, documento: String, tipoDocumento: String, zona: String = Constant.EMPTY_STRING) = Unit

    fun showLoading(msg: String)

    fun showBody(isValid: Boolean)

    fun isStepZero(): Boolean

    fun showDuplicateDocumentNumber(numeroDocumento: String?)

    fun showDuplicateMail()

    fun showValidMail()

    fun showDuplicateCelular()

    fun showValidCelular()

    fun documentoNoDuplicado()

    fun getCodigoRol(): String

    fun getZona(): String?

    fun getSeccion(): String?

    fun mostrarPrimerNombre(primerNombre: String)

    fun mostrarSegundoNombre(segundoNombre: String)

    fun mostrarPrimerApellido(primerApellido: String)

    fun mostrarSegundoApellido(segundoApellido: String)

    fun mostrarfechaNacimiento(fechaNacimiento: String)

    fun resetView()

    fun validarDatos()

    fun nextStep()

    fun showErrorConnectionMessageSV() = Unit

    fun actualizarEstadoTelefonico(status: Int) = Unit

    fun showSMSExitosoDialog() = Unit

    fun validarZonaPagoDeContado(bloqueos: BuroResponse) = Unit

    fun setCorreoObligatorio(esCorreoObligatorio: Boolean) = Unit

    fun showBloqueos(bloqueos: BuroResponse?) = Unit
}

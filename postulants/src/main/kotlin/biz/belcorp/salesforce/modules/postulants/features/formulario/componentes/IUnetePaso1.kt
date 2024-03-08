package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes

import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.BuroResponse
import biz.belcorp.salesforce.modules.postulants.features.entities.*

interface IUnetePaso1 : IUnetePaso {

    fun createModelDocument(): PostulanteModel {
        TODO("not implemented")
    }

    fun createModelPreDocument(): PrePostulanteModel {
        TODO("not implemented")
    }

    fun validateDocument(): Boolean

    fun showTipoDocumento(model: List<ParametroUneteModel>) = Unit

    fun showRegimenContable(model: List<ParametroUneteModel>) = Unit

    fun showGeneros(model: List<SexoModel>) = Unit

    fun showNacionalidad(model: List<TablaLogicaModel>) = Unit

    fun showModal(isValid: Boolean) = Unit

    fun showBody(isBodyVisible: Boolean) = Unit

    fun showDuplicateMailError(message: String) = Unit

    fun showDuplicateCelularError(message: String) = Unit

    fun showPrimerNombre(primerNombre: String) = Unit

    fun showSegundoNombre(segundoNombre: String) = Unit

    fun showPrimerApellido(primerApellido: String) = Unit

    fun showSegundoApellido(segundoApellido: String) = Unit

    fun mostrarFechaNacimiento(fechaNacimiento: String) = Unit

    fun showContactPostulant() = Unit

    fun setCorreoObligatorio(esCorreoObligatorio: Boolean) = Unit

    fun showBloqueos(bloqueos: BuroResponse?) = Unit
}

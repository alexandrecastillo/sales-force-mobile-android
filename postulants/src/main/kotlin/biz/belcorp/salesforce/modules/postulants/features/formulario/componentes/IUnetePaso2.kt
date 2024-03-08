package biz.belcorp.salesforce.modules.postulants.features.formulario.componentes

import biz.belcorp.salesforce.modules.postulants.features.entities.ParametroUneteModel
import biz.belcorp.salesforce.modules.postulants.features.entities.PostulanteModel
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso2.entities.PlaceModel

interface IUnetePaso2 : IUnetePaso {

    fun showConsultoraRecomienda(consultora: String) = Unit

    fun showLugarNivel1(lst: List<ParametroUneteModel>) = Unit

    fun showLugarNivel2(lst: List<ParametroUneteModel>) = Unit

    fun showLugarNivel3(lst: List<ParametroUneteModel>) = Unit

    fun showLugarNivel4(lst: List<ParametroUneteModel>) = Unit

    fun showLugarNivel5(lst: List<ParametroUneteModel>) = Unit

    fun showTipoDireccion(lst: List<ParametroUneteModel>) = Unit

    fun showDuplicateCelularError(message: String) = Unit

    fun showPINSMSFields() = Unit

    fun showValidacionCrediticia() = Unit

    fun saveRepuestaCC(respuesta: String) = Unit

    fun esCirculoCredito(): Boolean { TODO() }

    fun setRangosZonas(lst: List<ParametroUneteModel>) = Unit

    fun getClaveEstado(): String { TODO() }

    fun getCiudad() : String { TODO() }

    fun getDireccion() : String { TODO() }

    fun getTarjetaCredito(): Boolean { TODO() }

    fun getCreditoHipotecario() : Boolean { TODO() }

    fun getCreditoAutomotriz() : Boolean { TODO() }

    fun errorAlObtenerNombreConsultoraRecomendante() = Unit

    fun mostrarPlaces(lugares: List<PlaceModel>) = Unit

    fun buscarPlaces(cadenaBusqueda:String) = Unit

    fun onPlaceSelected(place: PlaceModel) = Unit

    fun desactivarValidarPIN() = Unit

    fun mostrarErrorPIN() = Unit

    fun validarCelular(model: PostulanteModel) = Unit

    fun showNewExperienceDireccion() = Unit

    fun activarBotonEnviarSMS() = Unit

    fun desactivarBotonEnviarSMS() = Unit

    fun actualizarCuentaAtras(tiempo: String) = Unit

    fun resetTextoBotonReenviarSMS() = Unit
}

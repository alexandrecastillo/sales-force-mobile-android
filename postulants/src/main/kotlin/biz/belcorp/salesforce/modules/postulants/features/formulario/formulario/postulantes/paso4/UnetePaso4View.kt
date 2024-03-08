package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso4

import biz.belcorp.salesforce.modules.postulants.features.entities.*

interface UnetePaso4View {
    fun showLoading()
    fun hideLoading()
    fun showError(message: String)
    fun showSolicitudRechazada(message: String)
    fun getConsultoraRecomienda(codigo: String)
    fun showConsultoraRecomienda(consultora: String)
    fun showGeneros(generos: List<SexoModel>)
    fun showTipoMeta(tiposMetas: List<TipoMetaModel>)
    fun showNivelEducativo(nivelEducativo: List<TablaLogicaModel>)
    fun showEstadoCivil(estadoCivil: List<TablaLogicaModel>)
    fun showTipoVinculoAval(tipoVinculoAval: List<TablaLogicaModel>)
    fun showTipoVinculoFamiliar(tipoVinculoFamiliar: List<TablaLogicaModel>)
    fun showTipoVinculoNoFamiliar(tipoVinculoNoFamiliar: List<TablaLogicaModel>)
    fun showOtrasMarcas(tipoVinculo: List<TablaLogicaModel>)
    fun updated(postulanteModel: PostulanteModel)
    fun getModel(): PostulanteModel
    fun showTipoPersona(tiposPersona: List<TablaLogicaModel>)
    fun showOrigenIngreso(origenesIngreso: List<TablaLogicaModel>)
    fun setZonaRiesgo(esZonaRiesgo: Boolean)
    fun esZonaRiesgo(): Boolean
    fun getCodigoRol(): String
    fun expandirConsultoraRecomendante(codigoConsultoraRecomendante: String?): Boolean
    fun obtenerCodigoODocumentoConsultoraRecomendante(codigoConsultoraRecomendante: String?): String?
    fun errorAlObtenerNombreConsultoraRecomendante()
}

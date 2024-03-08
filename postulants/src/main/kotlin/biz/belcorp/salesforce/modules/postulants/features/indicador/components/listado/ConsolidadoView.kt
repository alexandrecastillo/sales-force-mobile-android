package biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado

import biz.belcorp.salesforce.core.domain.entities.configuration.Configuration
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.ValidacionBuroResponse
import biz.belcorp.salesforce.modules.postulants.features.entities.*
import biz.belcorp.salesforce.modules.postulants.features.indicador.base.LoadDataView

interface ConsolidadoView : LoadDataView {

    fun showListado(list: List<BasePostulante>)
    fun showPreListado(list: List<BasePostulante>)
    fun showListadoEmpty()
    fun showPreListadoEmpty()
    fun showZonaRiesgo(esZonaRiesgo: Boolean)
    fun postulanteAprobada(success: Boolean)
    fun postulanteNoInteresada(success: Boolean)
    fun validacionExitosa()
    fun showMensajeRechazoBuro(title: String, message: String)
    fun showObservacionDevueltoSac(observacion: String)
    fun validarEdadZonaRiesgo(postulanteModel: PostulanteModel)
    fun obtenerPostulanteSeleccionado(): PostulanteModel?
    fun showFilter(list: List<FiltroAprobadoUneteModel>)
    fun showPaymentTypeFilter(list: List<FiltroAprobadoUneteModel>)
    fun validarZonaRiesgo()
    fun showRevisionDocumentoSAC(revisionDocumentoSac: Boolean)
    fun showTipoPostulanteFilter(list: MutableList<UneteListaModel>)
    fun showGZzonesFilter()
    fun showGRzonesFilter()
    fun toAssignValueToSE(section: String)
    fun uneteConfiguracion(uneteConfiguracion: UneteConfiguracionModel?)
    fun restaurarPantalla()
    fun esPagoContado() : Boolean
    fun showPagoContadoModal(bloqueos: ValidacionBuroResponse)
    fun esZonaPagoContado(esZona: Boolean)
    fun onProactivaRechazada(nomPostulante: String) = Unit
    fun onProactivaAprobada(nomPostulante: String) = Unit
    fun setConfiguration(config: Configuration)
}

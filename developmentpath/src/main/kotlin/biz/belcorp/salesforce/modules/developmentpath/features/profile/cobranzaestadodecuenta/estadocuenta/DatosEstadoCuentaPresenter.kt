package biz.belcorp.salesforce.modules.developmentpath.features.profile.cobranzaestadodecuenta.estadocuenta

import biz.belcorp.salesforce.core.utils.ui
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.estadocuenta.ObtenerEstadoDeCuentaUseCase

class DatosEstadoCuentaPresenter(
    private val view: DatosEstadoCuentaContract.View,
    private val useCase: ObtenerEstadoDeCuentaUseCase,
    private val mapper: EstadoCuentaModelMapper
) : DatosEstadoCuentaContract.Presenter {

    override fun obtener(personaId: Long) {
        ui {
            val listCuentaCorriente = useCase.obtener(personaId)
            val cuentasCorrientes = listCuentaCorriente.map { mapper.parse(it) }
            view.mostrarData(cuentasCorrientes)
        }
    }
}

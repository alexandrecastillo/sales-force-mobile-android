package biz.belcorp.salesforce.modules.developmentmaterial.features.materials

import biz.belcorp.salesforce.core.base.BasePresenter
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.usecases.campania.ObtenerCampaniasUseCase
import biz.belcorp.salesforce.modules.developmentmaterial.core.domain.usecases.GetDocumentsUseCase
import biz.belcorp.salesforce.modules.developmentmaterial.features.materials.mappers.DocumentMapper


class MaterialesDesarrolloPresenter(
    private val view: MaterialesDesarrolloView,
    private val campaniaUsecase: ObtenerCampaniasUseCase,
    private val obtenerDocumentosUseCase: GetDocumentsUseCase,
    private val documentMapper: DocumentMapper
) : BasePresenter() {

    fun cargarCabecera() {
        doAsync {
            val campania = campaniaUsecase.obtenerCampaniaActual()
            uiThread {
                procesarCampaniaActual(campania)
            }
        }
    }

    private fun procesarCampaniaActual(campania: Campania) {
        val nombre = campania.nombreCorto
        when (campania.periodo) {
            Campania.Periodo.VENTA -> view.mostrarCampaniaVenta(nombre)
            Campania.Periodo.FACTURACION -> view.mostrarCampaniaFacturacion(nombre)
        }
    }

    fun cargarDocumentos() {
        doAsync {
            val result = obtenerDocumentosUseCase.get()
            val documents = documentMapper.map(result)
            uiThread {
                view.pintarDocumentos(documents)
            }
        }
    }

}

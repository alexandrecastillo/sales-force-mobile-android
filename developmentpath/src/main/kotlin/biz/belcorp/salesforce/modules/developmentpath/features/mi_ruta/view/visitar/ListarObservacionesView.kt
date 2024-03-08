package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.visitar

import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.ObservacionVisitaModel

interface ListarObservacionesView {
    fun cargarObservaciones(modelos: List<ObservacionVisitaModel>)
}

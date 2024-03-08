package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.calendario

import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.CalendarioViewModel

interface CalendarioView {
    fun cargar(modelo: CalendarioViewModel)
}

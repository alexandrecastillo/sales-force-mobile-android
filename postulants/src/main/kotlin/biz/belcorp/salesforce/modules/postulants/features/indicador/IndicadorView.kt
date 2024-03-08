package biz.belcorp.salesforce.modules.postulants.features.indicador

import biz.belcorp.salesforce.modules.postulants.features.indicador.base.LoadDataView
import biz.belcorp.salesforce.modules.postulants.features.indicador.entities.BaseGeo
import biz.belcorp.salesforce.modules.postulants.features.indicador.entities.IndicadorUneteModel

interface IndicadorView : LoadDataView {

    fun showIndicadorUnete(model: IndicadorUneteModel?, isReloadingHeader: Boolean)
    fun showSections(sections: List<BaseGeo>)
    fun applyFilterMode(filterMode: Int)
}

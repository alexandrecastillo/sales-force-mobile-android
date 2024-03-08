package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gz.view

import biz.belcorp.salesforce.modules.developmentpath.features.focos.model.SeccionFocoModel

interface FocosView {
    fun cargarSecciones(modelos: List<SeccionFocoModel>)
}

package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.se.view

import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.se.model.FocoModel

/** Created by Soporte on 1/06/2018. */

interface FocosSEView {

    fun pintarFocos(focos: List<FocoModel>)

    fun mostrarPlaceHolder()
}
